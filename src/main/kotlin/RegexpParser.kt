internal class RegexpParser {
    private val regexp = "(https://www.instagram.com/\\S*)?".toRegex()

    fun parseInstaLink(string: String): String{
        return regexp.findAll(string).joinToString("") { it.value }
    }
}