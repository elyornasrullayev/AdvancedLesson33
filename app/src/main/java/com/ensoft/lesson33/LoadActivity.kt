package com.ensoft.lesson33

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ensoft.lesson33.databinding.ActivityLoadBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoadBinding
    private lateinit var rvAdapter: RvAdapter
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userList: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Users"

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Users")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList = ArrayList()

                val children = snapshot.children
                for (child in children){
                    val user = child.getValue(User::class.java)
                    if (user != null){
                        userList.add(user)
                    }
                }
                rvAdapter = RvAdapter(userList)
                binding.recyclerview.layoutManager = LinearLayoutManager(this@LoadActivity)
                binding.recyclerview.adapter = rvAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}