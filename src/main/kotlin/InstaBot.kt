import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import kotlinx.datetime.Clock

internal class InstaBot(
    private val botToken: String
) {
    private val regexpParser = RegexpParser()

    fun start(){
        val bot = bot {
            token = botToken
            dispatch {
                command("start") {
                    bot.send(Templates.greatings, update.message!!.chat.id)
                }
                text {
                    val result = text
                        .log(message.from?.username.orEmpty(), message.from?.id)
                        .getUrl {
                            bot.send(it, message.chat.id)
                        }
                        .replaceHost()

                    if (result.isNotEmpty()) {
                        println(result)
                        bot.send(Templates.goodJob, message.chat.id)
                        bot.send(result, message.chat.id)
                    }
                }
            }
        }
        println("Bot stared")
        bot.startPolling()
    }

    private fun Bot.send(text: String, id: Long) {
        if (text.isNotEmpty()) this.sendMessage(ChatId.fromId(id), text = text, parseMode = ParseMode.MARKDOWN)
    }

    private fun String.getUrl(onError: (String) -> Unit): String {
        if (this.isCommand()) return Templates.Empty
        val result = regexpParser.parseInstaLink(this)
        if (result.isEmpty()) {
            onError(Templates.noInstalink)
        }
        return result
    }

    private fun String.isCommand(): Boolean {
        return this.startsWith("/")
    }

    private fun String.log(name: String, id: Long?): String {
        val time = Clock.System.now().toString()
        if (this.isNotEmpty()) println("$time: message $this from $name with id $id")
        return this
    }

    private fun String.replaceHost(): String{
        val result = this.replaceFirst("instagram", "ddinstagram")
        return "[$result]($result)"
    }
}