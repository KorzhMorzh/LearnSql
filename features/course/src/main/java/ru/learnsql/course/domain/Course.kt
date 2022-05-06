package ru.learnsql.course.domain

import ru.learnsql.course.data.CourseDto

data class Course(
    val id: Int,
    val difficulty: Int,
    val title: String,
    val isMy: Boolean
) {
    constructor(course: CourseDto, isMy: Boolean) : this(course.id, course.difficulty, course.title, isMy)
}