package com.example.taller3_nicolasvargas

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_available.*
import java.lang.invoke.ConstantCallSite
import java.security.AuthProvider
import java.util.*
import kotlin.collections.ArrayList

private val PATH_USERS:String ="users/"
private lateinit var mAuth:FirebaseAuth
private lateinit var database:FirebaseDatabase
private lateinit var myRef:DatabaseReference
private lateinit var vel : ValueEventListener
private lateinit var contactList : ArrayList<Usuario>

class availableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available)
        database = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
        connect()

        lista.setOnItemClickListener{parent,view,position,id->

            val intent = Intent(this,HomeActivity::class.java)
            intent.putExtra("lat",contactList[position].lat)
            intent.putExtra("lon", contactList[position].lon)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        connect()
    }

    override fun onPause() {
        super.onPause()
        if(myRef!=null){
            myRef.removeEventListener(vel)
        }
    }

    @SuppressLint("Range")
    fun connect(){
        contactList = ArrayList()

        myRef = database.getReference(PATH_USERS)
        vel = myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                contactList.clear()
                for(single : DataSnapshot in snapshot.children ){
                    var usuario : Usuario? = single.getValue(Usuario::class.java)
                    if (usuario != null && usuario.email != mAuth.currentUser?.email && usuario.disponible == true ) {
                        contactList.add(usuario)
                    }
                }

                val adapter = UsuariosAdapter(this@availableActivity,contactList)
                lista.adapter=adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}