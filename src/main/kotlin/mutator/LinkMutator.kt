package mutator

abstract class LinkMutator {
    protected abstract val regexp: Regex
    abstract fun mutate(link: String): String?
    fun findLink(text: String): String? {
        val results = regexp.findAll(text)
        if (results.count() == 0) return null
        val result = results.joinToString("") { it.value }
        return when {
            result.startsWith("https://www.") -> result.removePrefix("https://www.")
            result.startsWith("www.") -> result.removePrefix("www.")
            result.startsWith("https://") -> result.removePrefix("https://")
            else -> result
        }
    }
}