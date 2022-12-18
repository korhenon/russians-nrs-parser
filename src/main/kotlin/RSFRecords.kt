import kotlin.math.roundToInt

class RSFRecords : Records {
    override val url: String
        get() = "https://funcubing.org/competitions/rankings"

    override fun getSingleRecords(): List<SingleRecord> {
        val recordRows = getPage().select("table.thead_stable").select("tr")
        val singleRecords = mutableListOf<SingleRecord>()
        for (recordRow in recordRows) {
            val columnsElements = recordRow.select("td")
            val columns = mutableListOf<String>()
            if (columnsElements.first() == null) continue
            for (column in columnsElements) {
                var ownText = column.ownText()
                val link = column.select("a").first()
                if (link != null) {
                    ownText = link.ownText()
                }
                if (ownText == "") continue
                columns.add(ownText)
            }
            if (columns.size == 5 && columns[0] != "Мульти-блайнд" && columns[0] != "3x3x3 ногами") {
                var time = if (columns[3].contains(":")) {
                    val split = columns[3].split(":")
                    split[0].toDouble() * 60 + split[1].toDouble()
                } else {
                    columns[3].toDouble()
                }
                time = (time * 100).roundToInt() / 100.0
                singleRecords.add(
                    SingleRecord(
                        getDiscipline(columns[0]),
                        columns[2].trim(),
                        time,
                        columns[4].trim()
                    )
                )
            }
        }
        return singleRecords
    }

    override fun getAverageRecords(): List<AverageRecord> {
        val recordRows = getPage().select("table.thead_stable").select("tr")
        val singleRecords = mutableListOf<AverageRecord>()
        for (recordRow in recordRows) {
            val columnsElements = recordRow.select("td")
            val columns = mutableListOf<String>()
            if (columnsElements.first() == null) continue
            for (column in columnsElements) {
                var ownText = column.ownText()
                val link = column.select("a").first()
                if (link != null) {
                    ownText = link.ownText()
                }
                if (ownText == "") continue
                columns.add(ownText)
            }
            if (columns.size == 6 && columns[0] != "3x3x3 ногами") {
                var time = if (columns[3].contains(":")) {
                    val split = columns[3].split(":")
                    split[0].toDouble() * 60 + split[1].toDouble()
                } else {
                    columns[3].toDouble()
                }
                time = (time * 100).roundToInt() / 100.0
                val attemptsSplit = columns[5].split(" ")
                val attempts = mutableListOf<Double>()

                attemptsSplit.forEach {
                    val timeString = it.replace("(", "").replace(")", "")
                    if (timeString == "DNF") {
                        attempts.add(0.0)
                    }
                    else {
                        var attemptTime = if (timeString.contains(":")) {
                            val split = timeString.split(":")
                            split[0].toDouble() * 60 + split[1].toDouble()
                        } else {
                            timeString.toDouble()
                        }
                        attempts.add((attemptTime * 100).roundToInt() / 100.0)
                    }
                }
                singleRecords.add(
                    AverageRecord(
                        getDiscipline(columns[0]),
                        columns[2].trim(),
                        time,
                        attempts,
                        columns[4].trim()
                    )
                )
            }
        }
        return singleRecords
    }

    override fun getMultiBlindSingleRecord(): MultiBlindRecord {
        val recordRow = getPage().select("table.thead_stable").select("tr").last()
        val columnsElements = recordRow!!.select("td")
        val columns = mutableListOf<String>()
        for (column in columnsElements) {
            var ownText = column.ownText()
            val link = column.select("a").first()
            if (link != null) {
                ownText = link.ownText()
            }
            if (ownText == "") continue
            columns.add(ownText)
        }
        val splitResult = columns[3].split(" ")
        var time = if (splitResult[1].contains(":")) {
            val split = splitResult[1].split(":")
            split[0].toDouble() * 60 + split[1].toDouble()
        } else {
            splitResult[1].toDouble()
        }
        time = (time * 100).roundToInt() / 100.0
        val splitSolved = splitResult[0].split("/")
        return MultiBlindRecord(
            columns[2].trim(),
            time,
            columns[4].trim(),
            splitSolved[0].toInt(),
            splitSolved[1].toInt()
        )
    }

}