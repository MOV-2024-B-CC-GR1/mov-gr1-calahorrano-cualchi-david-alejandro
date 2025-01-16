package com.example.ccgr12024b_dacc

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    fun mostrarSnackbar(texto: String){
        val snack = Snackbar.make(
            findViewById(R.id.cl_ciclo_vida),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    val callbackContenidoIntentExplicito = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data?.getStringExtra("nombreModificado")
                mostrarSnackbar("$data")
            }
        }
    }

    val callbackContenidoIntentImplicito = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result -> if (result.resultCode == Activity.RESULT_OK){
            if (result.data != null){
                // Validación de contactos
                if (result.data!!.data != null){
                    var uri: Uri = result.data!!.data!!
                    val cursor = contentResolver.query(
                        uri, null, null, null, null, null
                    )
                    cursor?.moveToFirst()
                    val indiceTelefono = cursor?.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )

                    val telefono = cursor?.getString(indiceTelefono!!)
                    cursor?.close()
                    mostrarSnackbar("Teléfono: $telefono")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.textView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Thursday, 28th November, 2024. Topic: Introducción a la creación de Interfaces Básicas en Android Studio
        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida);
        botonCicloVida.setOnClickListener {
            irActividad(ACicloVida::class.java)
        }

        // Friday, 29th November, 2024. Topic: Parte 2
        val botonIrListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonIrListView.setOnClickListener{
            irActividad(BListView::class.java)
        }
    }

    fun irActividad(clase: Class<*>){
        startActivity(Intent(this, clase))
    }
}