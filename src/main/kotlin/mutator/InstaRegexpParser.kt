package mutator

internal class InstaRegexpParser: LinkMutator() {
    override val regexp = "(^(https://)?instagram.com/\\S*)|((www.)?instagram.com/\\S*)|(^(https://www.)?instagram.com/\\S*)".toRegex()

    override fun mutate(link: String): String? {
        if (link.isEmpty()) return null
        val result = link.replaceFirst("instagram", "https://www.ddinstagram")
        return "[$result]($result)"
    }
}