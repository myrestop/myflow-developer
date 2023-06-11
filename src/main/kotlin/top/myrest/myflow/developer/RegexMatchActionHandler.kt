package top.myrest.myflow.developer

import java.util.regex.Pattern
import org.slf4j.LoggerFactory
import top.myrest.myflow.action.ActionParam
import top.myrest.myflow.action.ActionRequireArgHandler
import top.myrest.myflow.action.ActionResult
import top.myrest.myflow.action.plain
import top.myrest.myflow.clipboard.Clipboards
import top.myrest.myflow.enumeration.ActionArgMode
import top.myrest.myflow.enumeration.ActionArgType

class RegexMatchActionHandler : ActionRequireArgHandler() {

    private val log = LoggerFactory.getLogger(RegexMatchActionHandler::class.java)

    private val threshold = 50

    override val argRequireMode = ActionArgMode.REQUIRE_NOT_EMPTY to ActionArgType.exclude(ActionArgType.IMAGE)

    override fun queryArgAction(param: ActionParam): List<ActionResult> {
        val regex = param.args.first().strValue
        val content = if (param.args.size > 1) param.args[1].strValue else Clipboards.getText() ?: ""
        if (content.isBlank()) {
            return emptyList()
        }

        val pattern = try {
            Pattern.compile(regex)
        } catch (e: Exception) {
            log.info("compile regex error", e)
            return emptyList()
        }

        val result = ArrayList<String>()
        val matcher = pattern.matcher(content)
        var len = 0
        while (len < threshold && matcher.find()) {
            val s = matcher.group(0).trim()
            if (s.isEmpty()) {
                continue
            }
            len += s.length
            result.add(s)
        }

        if (len < 1) {
            return emptyList()
        }
        if (len > threshold) {
            result.add("...")
        }

        return listOf(
            ActionResult(
                actionId = "regex",
                logo = "./logos/regex.png",
                score = 100,
                title = listOf(result.joinToString(separator = " ").plain),
                subtitle = result.joinToString(separator = ""),
            ),
        )
    }
}
