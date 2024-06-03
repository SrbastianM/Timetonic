package com.srbastian.timetonic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.srbastian.timetonic.databinding.ActivityLoginBinding
import com.srbastian.timetonic.network.RetrofitClient
import com.srbastian.timetonic.network.TimetonicInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.create
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var instance: TimetonicInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        instance = RetrofitClient.instance.create(TimetonicInterface::class.java)

        loginBinding.btnLogin.setOnClickListener {
            val email = loginBinding.etEmailLogin.text.toString().trim()
            val password = loginBinding.etPasswordLogin.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Something is wrong, check the fields",
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
    }

    private fun login(
        email: String,
        password: String,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val appKeyResponse = instance.createAppKey()
//                val appKey = appKeyResponse.body()?.appkey ?: return@launch
                if (!appKeyResponse.isSuccessful) {
                    handleError("Failed to get AppKey")
                    return@launch
                }
                val appKey =
                    appKeyResponse.body()?.appkey ?: run {
                        handleError("AppKey response is null")
                        return@launch
                    }

                val oauthKeyResponse =
                    instance.createOauthkey(login = email, pwd = password, appkey = appKey)
//                val oauthKey = oauthKeyResponse.body()?.oauthkey ?: return@launch
                Log.d("TAG", "response: $oauthKeyResponse")
                if (!oauthKeyResponse.isSuccessful) {
                    handleError("Failed to get OauthKey")
                    return@launch
                }

                val oauthkey =
                    oauthKeyResponse.body()?.oauthkey ?: run {
                        handleError("OauthKey response is null")
                        return@launch
                    }
                val ou =
                    oauthKeyResponse.body()?.o_u ?: run {
                        handleError("O_u response is null")
                        return@launch
                    }
                Log.d("TAG", "response: $oauthkey")

                // No hacemos el envio de dicho post

                val sessKeyResponse = instance.createSesskey(o_u = ou, oauthkey = oauthkey)
                if (!sessKeyResponse.isSuccessful) {
                    handleError("Failed to get SessKey")
                    return@launch
                }
//                val sessKey = sessKeyResponse.body()?.sesskey ?: return@launch
                val sessKey =
                    sessKeyResponse.body()?.sesskey ?: run {
                        handleError("SessKey response is null")
                        return@launch
                    }
                Log.d("TAG", "response: sessKeyValue: $sessKey")

                getSharedPreferences("Timetonic", Context.MODE_PRIVATE).edit()
                    .putString("sess_key", sessKey).apply()

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)

                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login Failed: ${e.message}",
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
        }
    }

    private suspend fun handleError(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(applicationContext, "Login Failed: $message", Toast.LENGTH_LONG).show()
        }
    }
}
