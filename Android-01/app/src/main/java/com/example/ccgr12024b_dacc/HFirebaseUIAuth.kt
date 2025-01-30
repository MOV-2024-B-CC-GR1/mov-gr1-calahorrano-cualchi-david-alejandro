package com.example.ccgr12024b_dacc

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class HFirebaseUIAuth : AppCompatActivity() {
    private val respuestaLoginUiAuth = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        if (res.resultCode == RESULT_OK) {
            res.idpResponse?.let {
                Log.d("Auth", "Usuario autenticado correctamente")
                seLogeo(it)
            }
        } else {
            Log.e("Auth", "Error en autenticación: ${res.idpResponse?.error?.message}")
        }
    }

    private fun seLogeo(res: IdpResponse) {
        val usuario = FirebaseAuth.getInstance().currentUser
        usuario?.let {
            cambiarInterfaz(View.INVISIBLE, View.VISIBLE, it.displayName ?: "Bienvenido")
            if (res.isNewUser) {
                registrarUsuarioPorPrimeraVez(res)
            }
        } ?: Log.e("Auth", "Error: Usuario es nulo después de iniciar sesión")
    }

    private fun cambiarInterfaz(
        visibilidadLogin: Int = View.VISIBLE,
        visibilidadLogout: Int = View.INVISIBLE,
        textoTextView: String = "Bienvenido"
    ) {
        runOnUiThread {
            findViewById<Button>(R.id.btn_login_firebase).visibility = visibilidadLogin
            findViewById<Button>(R.id.btn_logout_firebase).visibility = visibilidadLogout
            findViewById<TextView>(R.id.tv_bienvenido).text = textoTextView
        }
    }

    // Registramos en nuestro sistema y el enviamos correco, etc...
    private fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse) {
        Log.d("Auth", "Registrando usuario por primera vez: ${usuario.email}")
        // Aquí podrías enviar un correo de bienvenida o guardar datos en Firestore
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hfirebase_uiauth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
            val logearseIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false) // Evita problemas con Smart Lock
                .build()
            respuestaLoginUiAuth.launch(logearseIntent)
        }

        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            cambiarInterfaz()
            Log.d("Auth", "Usuario cerró sesión")
        }

        val usuario = FirebaseAuth.getInstance().currentUser
        if(usuario !=null) {
            cambiarInterfaz(View.INVISIBLE, View.VISIBLE, usuario.displayName!!)
        }

        // Verificar si el usuario ya está autenticado
        FirebaseAuth.getInstance().currentUser?.let {
            cambiarInterfaz(View.INVISIBLE, View.VISIBLE, it.displayName ?: "Bienvenido")
        }
    }
}