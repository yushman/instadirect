import org.junit.jupiter.api.Test
import mutator.InstaRegexpParser
import kotlin.test.assertEquals

class InstaRegexpParserTest {

    private val parser = InstaRegexpParser()

    @Test
    fun `test different strings`(){
        assertEquals("instagram.com/123", parser.findLink("https://www.instagram.com/123"))
        assertEquals("instagram.com/123", parser.findLink("some text https://www.instagram.com/123"))
        assertEquals("instagram.com/123", parser.findLink("some text https://www.instagram.com/123 some text"))
        assertEquals(null, parser.findLink("some text https://www.google.com/123 some text"))
    }
}