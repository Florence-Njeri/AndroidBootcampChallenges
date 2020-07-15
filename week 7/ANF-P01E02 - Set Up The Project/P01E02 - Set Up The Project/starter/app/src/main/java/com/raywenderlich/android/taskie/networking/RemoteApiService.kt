package com.raywenderlich.android.taskie.networking

import com.raywenderlich.android.taskie.model.Task
import com.raywenderlich.android.taskie.model.request.AddTaskRequest
import com.raywenderlich.android.taskie.model.request.UserDataRequest
import com.raywenderlich.android.taskie.model.response.*
import retrofit2.http.*

interface RemoteApiService {

    @POST("/api/register")
    suspend fun registerUser(@Body request: UserDataRequest): RegisterResponse

    @POST("/api/login")
    suspend fun loginUser(@Body request: UserDataRequest): LoginResponse

    @GET("/api/note")
    suspend fun getNotes(): GetTasksResponse

    @GET("/api/user/profile")
    suspend fun getUserProfile(): UserProfileResponse

    @GET("/api/note/complete")
    suspend fun completeTask(@Query("id") noteId: String): CompleteNoteResponse

    @POST("/api/note")
    suspend fun addTask(@Body request: AddTaskRequest): Task

    @DELETE("/api/note")
    suspend fun deleteNote(@Query("id") noteId: String): DeleteNoteResponse
}