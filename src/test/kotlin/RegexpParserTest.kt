import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RegexpParserTest {

    private val parser = RegexpParser()

    @Test
    fun `test different strings`(){
        assertEquals("https://www.instagram.com/123", parser.parseInstaLink("https://www.instagram.com/123"))
        assertEquals("https://www.instagram.com/123", parser.parseInstaLink("some text https://www.instagram.com/123"))
        assertEquals("https://www.instagram.com/123", parser.parseInstaLink("some text https://www.instagram.com/123 some text"))
        assertEquals("", parser.parseInstaLink("some text https://www.google.com/123 some text"))
    }
}