package com.example.taller3_nicolasvargas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LogActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var txtEmail: EditText
    private lateinit var txtContra: EditText
    private lateinit var btnIniciar: Button
    private lateinit var btnCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        mAuth = FirebaseAuth.getInstance();
        btnIniciar=findViewById(R.id.iniciar)
        btnCancelar=findViewById(R.id.retroceder)
        txtEmail=findViewById(R.id.email)
        txtContra=findViewById(R.id.contra)

        btnIniciar.setOnClickListener{
            authenticateWithFB()
        }

        btnCancelar.setOnClickListener(){
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
    }

    private fun authenticateWithFB() {
        val email = txtEmail.text.toString()
        val pass = txtContra.text.toString()
        if(validateForm(email,pass)){
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(OnCompleteListener {
                if(it.isSuccessful){
                    updateUI(mAuth.currentUser?.email.toString())
                }else{
                    var message = it.exception?.message.toString()
                    var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            })
        }else{
            val toast = Toast.makeText(this,"Por favor ingrese el correo y la contraseña correctamente",
                Toast.LENGTH_SHORT).show()
        }

    }

    private fun validateForm(email:String, pass:String): Boolean {
        var valid:Boolean=false
        if(email.contains('@') and email.contains('.')){
            if(pass.length>=6){
                valid = true;
            }
        }
        return valid;
    }

    override fun onStart() {
        super.onStart()
        if(mAuth.currentUser!=null){
            updateUI(mAuth.currentUser!!.email.toString())
        }
    }

    private fun updateUI(email:String) {
        if(email!=null){
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("user",email)
            startActivity(intent);
        }else{
            txtEmail.hint="Ingrese su email"
            txtContra.hint="ingrese su contra"
        }
    }
}