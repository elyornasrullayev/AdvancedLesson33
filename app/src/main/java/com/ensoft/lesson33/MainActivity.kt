package com.ensoft.lesson33

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ensoft.lesson33.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    private var user_id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Users")

        //latest id before button click
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user_id = snapshot.childrenCount
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        binding.btnSave.setOnClickListener {

//            val key = databaseReference.push().key
//            databaseReference.child(key!!).setValue(user)

            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        user_id = snapshot.childrenCount
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

            var user = User(binding.etUsername.text.toString(), binding.etPassword.text.toString())
            databaseReference.child("user${++user_id}").setValue(user)

            Toast.makeText(applicationContext, "User saved", Toast.LENGTH_SHORT).show()

        }
        binding.navigateToLoad.setOnClickListener {
            startActivity(Intent(applicationContext, LoadActivity::class.java))

        }
    }
}