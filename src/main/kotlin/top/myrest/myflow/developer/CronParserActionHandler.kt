package top.myrest.myflow.developer

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.jvm.optionals.getOrNull
import com.cronutils.model.CronType
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.time.ExecutionTime
import com.cronutils.parser.CronParser
import top.myrest.myflow.action.ActionParam
import top.myrest.myflow.action.ActionRequireArgHandler
import top.myrest.myflow.action.ActionResult
import top.myrest.myflow.action.plain
import top.myrest.myflow.enumeration.ActionArgMode
import top.myrest.myflow.enumeration.ActionArgType
import top.myrest.myflow.language.LanguageBundle
import top.myrest.myflow.util.singleList

class CronParserActionHandler : ActionRequireArgHandler() {

    private val cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.SPRING)

    override val argRequireMode = ActionArgMode.REQUIRE_NOT_EMPTY to ActionArgType.STRING.singleList()

    override fun queryArgAction(param: ActionParam): List<ActionResult> {
        if (param.args.size != 6) {
            return emptyList()
        }

        val cronParser = CronParser(cronDefinition)
        val parsedCron = cronParser.parse(param.resolvedArguments)
        parsedCron.validate()
        val executionTime = ExecutionTime.forCron(parsedCron)
        val nextExecution = executionTime.nextExecution(ZonedDateTime.now()).getOrNull() ?: return emptyList()

        return listOf(
            ActionResult(
                actionId = "cron",
                score = 100,
                title = listOf(nextExecution.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).plain),
                subtitle = LanguageBundle.getBy(Constants.PLUGIN_ID, "next-exe-time"),
                result = nextExecution,
            )
        )
    }
}