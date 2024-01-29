package mutator

internal class MediumRegexpParser : LinkMutator(){
    override val regexp = "(^(https://)?medium.com/\\S*)|((www.)?medium.com/\\S*)|(^(https://www.)?medium.com/\\S*)".toRegex()

    override fun mutate(link: String): String? {
        if (link.isEmpty()) return null
        val result = link.replaceFirst("medium.com", "https://medium.rip")
        return "[$result]($result)"
    }
}