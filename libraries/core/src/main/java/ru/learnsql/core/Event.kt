package ru.learnsql.core

open class Event<out T>(private val content: T) {
    var consumed = false
        private set

    fun take() = if (consumed) {
        null
    } else {
        consumed = true
        content
    }

    fun reset() {
        consumed = false
    }

    fun peek(): T = content

    fun poll(): T? = content.takeUnless { consumed }

    override fun toString() = "Event: ${content.toString()} [consumed: $consumed]"

    override fun equals(other: Any?) =
        this === other || other is Event<*> && content == other.content && consumed == other.consumed

    override fun hashCode(): Int {
        var result = content?.hashCode() ?: 0
        result = 31 * result + consumed.hashCode()
        return result
    }
}