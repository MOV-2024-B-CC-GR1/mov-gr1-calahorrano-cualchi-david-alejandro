package com.example.ccgr12024b_dacc

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import android.Manifest

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
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            if (result.data != null) {
                val data = result.data?.getStringExtra("nombreModificado")
                Log.d("MainActivity", "Respuesta recibida: $data")
                mostrarSnackbar("Respuesta recibida: $data")
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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.textView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar BDD
        EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(this)

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

        val botonImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonImplicito.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {

                val intentConRespuesta = Intent(
                    Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                )
                callbackContenidoIntentImplicito.launch(intentConRespuesta)

            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS), 1)
            }
        }

        val botonExplicito = findViewById<Button>(R.id.btn_ir_intent_explicito)
        botonExplicito
            .setOnClickListener {
                val intentExplicito = Intent(this, CIntentExplicitoParametros::class.java)
                intentExplicito.putExtra("nombre", "David")
                intentExplicito.putExtra("apellido", "Calahorrano")
                intentExplicito.putExtra("edad", 22)
                intentExplicito.putExtra("entrenador", BEntrenador(1, "Adrian", "Ejemplo")) // Asegurar que sea Parcelable
                callbackContenidoIntentExplicito.launch(intentExplicito)
            }

        val botonIrSqlite = findViewById<Button>(R.id.btn_sqlite)
        botonIrSqlite
            .setOnClickListener {
                Snackbar.make(it, "Abriendo SQLite", Snackbar.LENGTH_SHORT).show()
                irActividad(ECrudEntrenador::class.java)
            }

        val botonIrRecyclerView = findViewById<Button>(R.id.btn_recycler_view)
        botonIrRecyclerView
            .setOnClickListener {
                Snackbar.make(it, "Abriendo RecyclerView", Snackbar.LENGTH_SHORT).show()
                irActividad(FRecyclerView::class.java)
            }

        val botonGMaps = findViewById<Button>(R.id.btn_google_maps)
        botonGMaps
            .setOnClickListener {
                irActividad(GGoogleMaps::class.java)
            }

        val botonAuth = findViewById<Button>(R.id.btn_intent_firebase_ui)
        botonAuth
            .setOnClickListener {
                irActividad(HFirebaseUIAuth::class.java)
            }
    }

    fun irActividad(clase: Class<*>){
        startActivity(Intent(this, clase))
    }
}