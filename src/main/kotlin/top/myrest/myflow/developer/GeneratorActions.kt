package top.myrest.myflow.developer

import cn.hutool.core.util.IdUtil
import top.myrest.myflow.action.ActionParam
import top.myrest.myflow.action.ActionRequireArgHandler
import top.myrest.myflow.action.ActionResult
import top.myrest.myflow.action.RefreshableActionResult
import top.myrest.myflow.action.basicCopyResult
import top.myrest.myflow.enumeration.ActionArgMode

class UuidGeneratorActionHandler : BaseUuidGeneratorActionHandler("uuid", false)
class Uuid0GeneratorActionHandler : BaseUuidGeneratorActionHandler("simple-uuid", true)
open class BaseUuidGeneratorActionHandler(private val actionId: String, private val simple: Boolean) : ActionRequireArgHandler() {
    override val argRequireMode = ActionArgMode.emptyArgMode
    override fun queryArgAction(param: ActionParam): List<ActionResult> {
        return listOf(
            RefreshableActionResult(
                actionId = actionId,
                score = 100,
                value = "",
                refreshResult = { _, _ ->
                    val result = if (simple) IdUtil.simpleUUID() else IdUtil.randomUUID()
                    basicCopyResult(actionId = "", logo = "./logos/uuid.png", result = result)
                },
            ),
        )
    }
}
