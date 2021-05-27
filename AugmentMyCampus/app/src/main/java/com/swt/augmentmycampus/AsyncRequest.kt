package com.swt.augmentmycampus

import android.os.AsyncTask
import retrofit2.Call

class AsyncRequest<T> : AsyncTask<Call<T>, Void, T?>() {
    override fun doInBackground(vararg calls: Call<T>): T? {
        return try {
            val response = calls[0].execute()
            if(response.isSuccessful) {
                response.body()
            }
            else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override fun onPostExecute(userInfo: T?) {
        // Do nothing
    }
}
