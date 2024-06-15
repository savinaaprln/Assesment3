package org.d3if3120.mobpro1assesment3.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3120.mobpro1assesment3.model.Makeup
import org.d3if3120.mobpro1assesment3.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://unspoken.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MakeupApiService {
    @GET("cosmetic.php")
    suspend fun getMakeup(
        @Header("Authorization") userId: String
    ):List<Makeup>
    @Multipart
    @POST("cosmetic.php")
    suspend fun postMakeup(
        @Header("Authorization") userId: String,
        @Part("namaCosmetic") namaCosmetic: RequestBody,
        @Part("jenisCosmetic") jenisCosmetic: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus
    @DELETE("cosmetic.php")
    suspend fun deleteMakeup(
        @Header("Authorization") userId: String,
        @Query("id") MakeupId: String
    ) : OpStatus
}


object MakeupApi {
    val service: MakeupApiService by lazy {
        retrofit.create(MakeupApiService::class.java)
    }

    fun getMakeupUrl(imageId: String): String {
        return "${BASE_URL}image.php?id=$imageId"
    }
}

enum class ApiStatus {LOADING, SUCCESS, FAILED}
