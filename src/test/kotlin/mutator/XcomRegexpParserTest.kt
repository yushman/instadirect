package mutator

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class XcomRegexpParserTest{
    private val parser = XcomRegexpParser()

    @Test
    fun `xcom links test`(){
        val xcom = "x.com/alshd/as"
        val httpsxcom = "https://x.com/alshd/as"
        val wwwxcom = "https://www.x.com/alshd/as"


        val expected = "x.com/alshd/as"

        assertEquals(expected, parser.findLink(xcom))
        assertEquals(expected, parser.findLink(httpsxcom))
        assertEquals(expected, parser.findLink(wwwxcom))
    }

}