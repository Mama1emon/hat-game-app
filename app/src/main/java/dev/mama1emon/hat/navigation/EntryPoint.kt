package dev.mama1emon.hat.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import java.net.URLEncoder

open class EntryPoint(
    private val name: String,
    val arguments: List<NamedNavArgument> = listOf(),
    val deepLinks: List<NavDeepLink> = listOf()
) {

    fun value() = if (arguments.isNotEmpty()) {
        val route = "$name?"
        val args = arguments.joinToString(separator = "&") { "${it.name}={${it.name}}" }
        route + args
    } else {
        name
    }

    inner class Route {

        private val addedArgs: MutableMap<String, Any> = mutableMapOf()

        fun addValue(key: String, value: Any?): Route {
            if (arguments.isEmpty()) {
                throw IllegalStateException(
                    "Точка входа ${this@EntryPoint.name} не имеет аргументов"
                )
            }

            val registeredArg = arguments.firstOrNull { it.name == key }
                ?: throw IllegalArgumentException(
                    "Точка входа ${this@EntryPoint.name} не имеет аргумента с ключом $key"
                )

            if (!registeredArg.argument.isNullable && value == null) {
                throw IllegalArgumentException(
                    "Аргумент с ключом $key точки входа ${this@EntryPoint.name} " +
                        "имеет not null тип"
                )
            }

            if (value is String) {
                addedArgs[key] = URLEncoder.encode(value, "utf-8")
            } else if (value != null) {
                addedArgs[key] = value
            }

            return this
        }

        fun destination(): String {
            val requiredArgNames = arguments
                .filterNot { it.argument.isNullable }
                .map(NamedNavArgument::name)

            val condition1 = addedArgs.count() < requiredArgNames.count()
            val condition2 = requiredArgNames.any { it !in addedArgs.keys }
            if (condition1 || condition2) {
                throw IllegalStateException(
                    "Путь до точки входа ${this@EntryPoint.name} " +
                        "должен обязательно включать аргументы: " +
                        requiredArgNames.filter { it !in addedArgs }.joinToString()
                )
            }
            return if (addedArgs.isNotEmpty()) {
                val args = addedArgs.entries
                    .joinToString(separator = "&") { "${it.key}=${it.value}" }
                "$name?$args"
            } else {
                name
            }
        }
    }
}