package ru.learnsql.task.data

import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import ru.learnsql.task.data.dto.Task
import ru.learnsql.task.data.dto.TaskAnswerDto

interface TaskNetworkApi {
    @Multipart
    @PUT("api/student-course/do-task/")
    suspend fun doTask(
        @Part("solution") solution: RequestBody,
        @Part("task_id") taskId: RequestBody,
        @Part("status") status: RequestBody,
        @Part("id") id: RequestBody
    ): TaskAnswerDto

    @GET("api/tasks/{taskId}/")
    suspend fun loadTask(@Path("taskId") taskId: Int): Task
}