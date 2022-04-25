package fr.synchrone.glpisupport.core.http

import fr.synchrone.glpisupport.data.repository.TokenRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HttpInterceptor @Inject constructor(private val tokenRepository: TokenRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        if(chain.call().request().url.encodedPath.contains("initSession")){
            return chain.proceed(original)
        }

        val token = runBlocking { tokenRepository.userToken.first() }

        val builder = original.newBuilder()
                .header("Session-Token", token)

        val request = builder.build()

        return chain.proceed(request)
    }

}
