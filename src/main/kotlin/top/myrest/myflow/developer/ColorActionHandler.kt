package top.myrest.myflow.developer

import androidx.compose.ui.graphics.Color
import top.myrest.myflow.action.ActionParam
import top.myrest.myflow.action.ActionRequireArgHandler
import top.myrest.myflow.action.ActionResult
import top.myrest.myflow.action.asResult
import top.myrest.myflow.enumeration.ActionArgMode
import top.myrest.myflow.enumeration.ActionArgType
import top.myrest.myflow.util.singleList

private val validHexChars = "0123456789abcdefABCDEF".toSet()

private fun String.isHex(): Boolean = all { validHexChars.contains(it) }

class ColorActionHandler : ActionRequireArgHandler() {

    override val argRequireMode = ActionArgMode.REQUIRE_NOT_EMPTY to ActionArgType.STRING.singleList()

    override fun queryArgAction(param: ActionParam): List<ActionResult> {
        val value = param.args.first().strValue
        val color: Color? = if (value.startsWith("0x")) {
            parseHex(value.substring(2))
        } else if (value.equals("rgb", true)) {
            parseRgb(param.args.drop(1).map { it.strValue })
        } else if (value.startsWith("rgb(", true) && value.endsWith(')')) {
            val rgb = value.substring(4, value.length - 1).split(',').map { it.trim() }
            parseRgb(rgb)
        } else if (value.isHex()) {
            parseHex(value)
        } else null

        return if (color == null) emptyList() else listOf(color.asResult(98))
    }

    private fun parseRgb(rgb: List<String>): Color? {
        if (rgb.size !in 3..4) {
            return null
        }
        if (!rgb.all { it.isNotEmpty() && it.all { c -> c.isDigit() } }) {
            return null
        }
        return Color(rgb.getInt(0), rgb.getInt(1), rgb.getInt(2), rgb.getOrNull(3)?.toInt() ?: 255)
    }

    private fun parseHex(hex: String): Color? {
        if (hex.length == 6) {
            return parseHex("ff$hex")
        }
        return if (hex.length == 8) Color(hex.toLong(16)) else null
    }

    private fun List<String>.getInt(idx: Int): Int = get(idx).toInt()
}
