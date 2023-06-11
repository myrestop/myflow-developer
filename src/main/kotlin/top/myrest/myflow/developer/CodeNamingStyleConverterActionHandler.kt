package top.myrest.myflow.developer

import java.lang.Integer.min
import cn.hutool.core.util.RandomUtil
import top.myrest.myflow.action.ActionParam
import top.myrest.myflow.action.ActionRequireArgHandler
import top.myrest.myflow.action.ActionResult
import top.myrest.myflow.action.RefreshableActionResult
import top.myrest.myflow.action.asSuggestionResult
import top.myrest.myflow.action.basicCopyResult
import top.myrest.myflow.enumeration.ActionArgMode
import top.myrest.myflow.enumeration.ActionArgType
import top.myrest.myflow.util.singleList

class CodeNamingStyleConverterActionHandler : ActionRequireArgHandler() {

    private val baseLetters = (RandomUtil.BASE_CHAR + RandomUtil.BASE_CHAR.uppercase()).toSet()

    private val baseChars = (RandomUtil.BASE_CHAR + RandomUtil.BASE_CHAR.uppercase() + RandomUtil.BASE_NUMBER + "-_").toSet()

    override val argRequireMode = ActionArgMode.REQUIRE_NOT_EMPTY to ActionArgType.STRING.singleList()

    override fun queryArgAction(param: ActionParam): List<ActionResult> {
        val value = param.args.first().strValue
        if (param.args.size != 1 || !baseLetters.contains(value[0])) {
            return emptyList()
        }

        var hasSepChar = false
        var upperIdx = 0
        value.forEachIndexed { i, c ->
            if (!baseChars.contains(c)) {
                return emptyList()
            }
            if (c == '_' || c == '-') {
                hasSepChar = true
            }
            if (c.isUpperCase()) {
                upperIdx = i
            }
        }

        if (!hasSepChar && upperIdx < 1) {
            return emptyList()
        }

        val results = convert(value, hasSepChar).filter { it != value }.distinct()
        return packResult(value, results)
    }

    private fun packResult(value: String, results: List<String>): List<ActionResult> {
        return if (results.isEmpty()) emptyList() else listOf(
            RefreshableActionResult(
                actionId = "style.code.naming",
                score = min(100, 60 + 2 * value.length),
                value = results,
                refreshResult = { s, cnt ->
                    val result = s[cnt % s.size]
                    basicCopyResult(actionId = "", logo = "./logos/code_naming.png", result = result.asSuggestionResult())
                },
            ),
        )
    }

    private fun convert(value: String, hasSepChar: Boolean): MutableList<String> {
        val results = mutableListOf<String>()
        if (hasSepChar) {
            var i = 0
            var upper = false
            val lowerCamel = StringBuilder()
            val upperCamel = StringBuilder()
            while (i < value.length) {
                val c = if (upper) value[i].uppercaseChar() else value[i].lowercaseChar()
                upper = if (c == '_' || c == '-') true else {
                    lowerCamel.append(c)
                    upperCamel.append(if (i == 0) c.uppercaseChar() else c)
                    false
                }
                i++
            }

            results.add(value.replace('-', '_').lowercase())
            results.add(value.replace('_', '-').lowercase())
            results.add(value.replace('-', '_').uppercase())
            results.add(upperCamel.toString())
            results.add(lowerCamel.toString())
            return results
        }

        val snake = StringBuilder()
        val lowerUnderScore = StringBuilder()
        val upperUnderScore = StringBuilder()
        val lowerCamel = value[0].lowercase() + value.substring(1)
        lowerCamel.forEach {
            if (it.isUpperCase()) {
                snake.append('-').append(it.lowercase())
                lowerUnderScore.append('_').append(it.lowercase())
                upperUnderScore.append('_').append(it)
            } else {
                snake.append(it)
                lowerUnderScore.append(it)
                upperUnderScore.append(it.uppercaseChar())
            }
        }

        results.add(lowerUnderScore.toString())
        results.add(snake.toString())
        results.add(upperUnderScore.toString())
        results.add(value[0].uppercaseChar() + value.substring(1))
        results.add(lowerCamel)
        return results
    }
}
