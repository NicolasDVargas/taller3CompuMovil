package com.example.taller3_nicolasvargas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var iniciarBtn : Button
    private lateinit var registrarBtn : Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniciarBtn = findViewById(R.id.Iniciar)
        registrarBtn = findViewById(R.id.Registrar)
        mAuth = FirebaseAuth.getInstance()

        iniciarBtn.setOnClickListener(){
            val aIniciarSesion = Intent(this, LogActivity::class.java)
            startActivity(aIniciarSesion)
        }

        registrarBtn.setOnClickListener(){
            val aRegistroActivity = Intent(this, RegistrarActivity::class.java)
            startActivity(aRegistroActivity)
        }

    }

    override fun onStart() {
        super.onStart()
        if(mAuth.currentUser!=null){
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("user", mAuth.currentUser!!.email.toString())
            startActivity(intent);
        }
    }
}