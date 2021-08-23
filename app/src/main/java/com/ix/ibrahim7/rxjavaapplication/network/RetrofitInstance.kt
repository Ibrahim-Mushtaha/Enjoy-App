package com.ix.ibrahim7.rxjavaapplication.network


import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {


    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        var api: Api? = null

        init {

            val client = OkHttpClient.Builder()
                .build()

            api = getInstantRetrofit(BASE_URL, client).create(Api::class.java)


        }

        private fun getInstantRetrofit(url: String, client: OkHttpClient) =
            Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build()


    }

    /*fun loadUserData():Observable<News>{

        return api!!.getNews()
    }*/

}