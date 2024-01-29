import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import kotlinx.datetime.Clock
import mutator.InstaRegexpParser
import mutator.MediumRegexpParser
import mutator.XcomRegexpParser

internal class InstaBot(
    private val botToken: String
) {
    private val instaRegexpParser = InstaRegexpParser()
    private val xcomRegexpParser = XcomRegexpParser()
    private val mediumRegexpParser = MediumRegexpParser()

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
                        .replaceLink {
                            bot.send(it, message.chat.id)
                        }

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

    private fun String.replaceLink(onError: (String) -> Unit): String {
        if (this.isCommand()) return Templates.Empty
        val instaLink = instaRegexpParser.findLink(this)
        val xcomLink = xcomRegexpParser.findLink(this)
        val mediumLink = mediumRegexpParser.findLink(this)
        val result = instaLink?.let{ instaRegexpParser.mutate(it) }
            ?: xcomLink?.let{ xcomRegexpParser.mutate(it) }
            ?: mediumLink?.let{ mediumRegexpParser.mutate(it)}

        if (result.isNullOrEmpty()) {
            onError(Templates.noInstalink)
        }
        return result ?: Templates.Empty
    }

    private fun String.isCommand(): Boolean {
        return this.startsWith("/")
    }

    private fun String.log(name: String, id: Long?): String {
        val time = Clock.System.now().toString()
        if (this.isNotEmpty()) println("$time: message $this from $name with id $id")
        return this
    }
}