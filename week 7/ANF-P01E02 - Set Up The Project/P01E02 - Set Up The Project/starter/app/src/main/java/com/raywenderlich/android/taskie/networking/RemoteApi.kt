/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.taskie.networking

import com.google.gson.Gson
import com.raywenderlich.android.taskie.App
import com.raywenderlich.android.taskie.model.Task
import com.raywenderlich.android.taskie.model.UserProfile
import com.raywenderlich.android.taskie.model.request.AddTaskRequest
import com.raywenderlich.android.taskie.model.request.UserDataRequest
import com.raywenderlich.android.taskie.model.response.GetTasksResponse
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.NullPointerException
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

/**
 * Holds decoupled logic for all the API calls.
 */

const val BASE_URL = "https://taskie-rw.herokuapp.com"

class RemoteApi(private val remoteApiService: RemoteApiService) {
    val gson = Gson()
    fun loginUser(userDataRequest: UserDataRequest, onUserLoggedIn: (String?, Throwable?) -> Unit) {
        val body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(userDataRequest))

        remoteApiService.loginUser(body).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, error: Throwable) {
                onUserLoggedIn(null, error)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val message = response.body()?.string()
                if (message == null) {
                    onUserLoggedIn(null, NullPointerException("No response body!"))
                    return
                }
                onUserLoggedIn(message, null)
            }

        })
    }

    fun registerUser(userDataRequest: UserDataRequest, onUserCreated: (String?, Throwable?) -> Unit) {
        val body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(userDataRequest))

        remoteApiService.registerUser(body).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, error: Throwable) {
                onUserCreated(null, error)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val message = response.body()?.string()
                if (message == null) {
                    onUserCreated(null, NullPointerException("No response body!"))
                    return
                }
                onUserCreated(message, null)
            }

        })
    }

    fun getTasks(onTasksReceived: (List<Task>, Throwable?) -> Unit) {
        Thread(Runnable {
            val connection = URL("$BASE_URL/api/note").openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.setRequestProperty("Authorization", App.getToken())
            connection.readTimeout = 10000
            connection.connectTimeout = 10000
            connection.doInput = true

            /** Get the list of notes from the server */
            try {
//Read the response
                val reader = InputStreamReader(connection.inputStream)
                reader.use { input ->
                    val response = StringBuilder()
                    val bufferedReader = BufferedReader(input)

                    bufferedReader.useLines { lines ->
                        lines.forEach {
                            response.append(it.trim())
                        }

                    }
                    //Send the data back to the callback
                    val taskResponse = gson.fromJson(response.toString(), GetTasksResponse::class.java)
                    onTasksReceived(taskResponse.notes.filter { it.isCompleted }, null)

                }
            } catch (error: Throwable) {
                onTasksReceived(emptyList(), error)
            }

            connection.disconnect()
        }).start()
    }

    fun deleteTask(onTaskDeleted: (Throwable?) -> Unit) {
        onTaskDeleted(null)
    }

    fun completeTask(taskId: String, onTaskCompleted: (Throwable?) -> Unit) {

        Thread(Runnable {
            val connection = URL("$BASE_URL/api/note/complete?id=$taskId").openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.setRequestProperty("Authorization", App.getToken())
            connection.readTimeout = 10000
            connection.connectTimeout = 10000
            connection.doOutput = true
            connection.doInput = true

            val body = gson.toJson(onTaskCompleted)
            val bytes = body.toByteArray()

            try {
                connection.outputStream.use {
                    it.write(bytes)
                }
                //Read the response
                val reader = InputStreamReader(connection.inputStream)
                reader.use { input ->
                    val response = StringBuilder()
                    val bufferedReader = BufferedReader(input)

                    bufferedReader.useLines { lines ->
                        lines.forEach {
                            response.append(it.trim())
                        }

                    }

                    onTaskCompleted(null)

                }
            } catch (error: Throwable) {
                onTaskCompleted(error)
            }

            connection.disconnect()
        }).start()
    }

    fun addTask(addTaskRequest: AddTaskRequest, onTaskCreated: (Task?, Throwable?) -> Unit) {
        Thread(Runnable {
            val connection = URL("$BASE_URL/api/note").openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.setRequestProperty("Authorization", App.getToken())
            connection.readTimeout = 10000
            connection.connectTimeout = 10000
            connection.doOutput = true
            connection.doInput = true

//            val requestJson = JSONObject()
//            requestJson.put("title", addTaskRequest.title)
//            requestJson.put("content", addTaskRequest.content)
//            requestJson.put("taskPriority", addTaskRequest.taskPriority)
            val body = gson.toJson(addTaskRequest)
//            val body = requestJson.toString()
            val bytes = body.toByteArray()

            try {
                connection.outputStream.use {
                    it.write(bytes)
                }
                //Read the response
                val reader = InputStreamReader(connection.inputStream)
                reader.use { input ->
                    val response = StringBuilder()
                    val bufferedReader = BufferedReader(input)

                    bufferedReader.useLines { lines ->
                        lines.forEach {
                            response.append(it.trim())
                        }

                    }
                    //Send the data back to the callback
                    val jsonResponse = JSONObject(response.toString())

                    val id = jsonResponse.getString("id")
                    val userId = jsonResponse.getString("userId")
                    val title = jsonResponse.getString("title")
                    val content = jsonResponse.getString("content")
                    val isCompleted = jsonResponse.getBoolean("isCompleted")
                    val taskPriority = jsonResponse.getInt("taskPriority")
                    val task = Task(id, title, content, isCompleted, taskPriority)
                    onTaskCreated(task, null)
                }
            } catch (error: Throwable) {
                onTaskCreated(null, error)
            }

            connection.disconnect()
        }).start()
        onTaskCreated(
                Task("id3",
                        addTaskRequest.title,
                        addTaskRequest.content,
                        false,
                        addTaskRequest.taskPriority
                ), null
        )
    }

    fun getUserProfile(onUserProfileReceived: (UserProfile?, Throwable?) -> Unit) {
        onUserProfileReceived(UserProfile("mail@mail.com", "Filip", 10), null)
    }
}