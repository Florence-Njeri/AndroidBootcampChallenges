package com.raywenderlich.android.taskie.networking

import com.raywenderlich.android.taskie.model.Task
import com.raywenderlich.android.taskie.model.request.AddTaskRequest
import com.raywenderlich.android.taskie.model.request.UserDataRequest
import com.raywenderlich.android.taskie.model.response.*
import retrofit2.Call
import retrofit2.http.*

interface RemoteApiService {

    @POST("/api/register")
    fun registerUser(@Body request:UserDataRequest): Call<RegisterResponse>

    @POST("/api/login")
    fun loginUser(@Body request: UserDataRequest): Call<LoginResponse>

    @GET("/api/note")
    fun getNotes(): Call<GetTasksResponse>

    @GET("/api/user/profile")
    fun getUserProfile(): Call<UserProfileResponse>

    @GET("/api/note/complete")
    fun completeTask(@Query("id") noteId: String): Call<CompleteNoteResponse>

    @POST("/api/note")
    fun addTask(@Body request: AddTaskRequest): Call<Task>

    @DELETE("/api/note")
    fun deleteNote(@Query("id") noteId: String): Call<DeleteNoteResponse>
}