package mutator

internal class XcomRegexpParser: LinkMutator() {
    override val regexp = "(^(https://)?x.com/\\S*)|((www.)?x.com/\\S*)|(^(https://www.)?x.com/\\S*)|(^(https://g.)?x.com/\\S*)|(^(https://d.)?x.com/\\S*)|(^(https://i.)?x.com/\\S*)".toRegex()

    override fun mutate(link: String): String? {
        if (link.isEmpty()) return null
        val result = link.replaceFirst("x.com", "fixupx.com")
        return "[https://$result](https://$result)"
    }
}