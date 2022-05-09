package ru.learnsql.course.domain.model

import ru.learnsql.course.data.dto.CourseDto

data class Course(
    val id: Int,
    val difficulty: Int,
    val title: String,
    val isMy: Boolean
) {
    constructor(course: CourseDto, isMy: Boolean) : this(course.id, course.difficulty, course.title, isMy)
}