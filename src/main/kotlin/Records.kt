import org.jsoup.Jsoup
import org.jsoup.nodes.Document

data class SingleRecord(
    val discipline: Disciplines,
    val competitorName: String,
    val time: Double,
    val competition: String
)

data class AverageRecord(
    val discipline: Disciplines,
    val competitorName: String,
    val time: Double,
    val attempts: List<Double>,
    val competition: String
)

data class MultiBlindRecord(
    val competitorName: String,
    val time: Double,
    val competition: String,
    val solved: Int,
    val all: Int
)

interface Records {
    val url: String

    fun getPage(): Document {
        return Jsoup.connect(url).get()
    }
    fun getSingleRecords(): List<SingleRecord>
    fun getAverageRecords(): List<AverageRecord>
    fun getMultiBlindSingleRecord(): MultiBlindRecord
}
