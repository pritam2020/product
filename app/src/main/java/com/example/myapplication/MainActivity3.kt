package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import java.io.Serializable

class MainActivity3 : AppCompatActivity() {
    var TAG:String="getting data from intent"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        findViewById<TextView>(R.id.price).text = intent.getStringExtra("price")
        findViewById<TextView>(R.id.title).text = intent.getStringExtra("title")
        findViewById<TextView>(R.id.category).text = intent.getStringExtra("category")
        Picasso.get().load(intent.getStringExtra("image")).into(findViewById<ImageView>(R.id.image))
        findViewById<TextView>(R.id.description).text = intent.getStringExtra("description")
        findViewById<TextView>(R.id.username).text=FirebaseAuth.getInstance().currentUser?.displayName.toString()
    }
}