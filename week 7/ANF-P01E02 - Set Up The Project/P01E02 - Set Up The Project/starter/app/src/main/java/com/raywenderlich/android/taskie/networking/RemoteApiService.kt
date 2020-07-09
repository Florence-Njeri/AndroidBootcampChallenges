package com.raywenderlich.android.taskie.networking

import com.raywenderlich.android.taskie.model.Task
import com.raywenderlich.android.taskie.model.request.AddTaskRequest
import com.raywenderlich.android.taskie.model.request.UserDataRequest
import com.raywenderlich.android.taskie.model.response.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RemoteApiService {

    @POST("/api/register")
    fun registerUser(@Body request:UserDataRequest): Call<RegisterResponse>

    @POST("/api/login")
    fun loginUser(@Body request: UserDataRequest): Call<LoginResponse>

    @GET("/api/note")
    fun getNotes(@Header("Authorization") token:String): Call<GetTasksResponse>

    @GET("/api/user/profile")
    fun getUserProfile(@Header("Authorization") token:String): Call<UserProfileResponse>

    @GET("/api/note")
    fun completeTask(@Header("Authorization") token:String,@Query("id") noteId:String): Call<CompleteNoteResponse>

    @POST("/api/note/complete")
    fun addTask(@Header("Authorization") token:String,@Body request: AddTaskRequest): Call<Task>
}