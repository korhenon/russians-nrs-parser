fun main(args: Array<String>) {
    val rsfRecords = RSFRecords()
    rsfRecords.getAverageRecords().forEach {
        println("${it.discipline.discipline} ${it.time}")
    }
}