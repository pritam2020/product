package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            changeDisplay()
        }
    }

    fun signIn( view:View) {
         val auth = FirebaseAuth.getInstance()
         if (auth.currentUser != null) {
             Toast.makeText(applicationContext,
                 "User already sign in signout first",
                 Toast.LENGTH_SHORT).show()
         } else {
             //Choosing Authentication provider
             val providers: List<IdpConfig> = Arrays.asList(EmailBuilder().build())

             //Creating and launching sign-in intent
             startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                 .setAvailableProviders(providers).build(), 12345)
         }
     }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12345) {
            if (resultCode == RESULT_OK) {
                FirebaseAuth.getInstance().currentUser
                Toast.makeText(applicationContext, "Successfully signed-in", Toast.LENGTH_SHORT)
                    .show()
                changeDisplay()
            }
        } else {
            Toast.makeText(applicationContext, "Unable to sign in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeDisplay(){
        val intent = Intent(this,MainActivity2::class.java)
        startActivity(intent)
        finish()
    }


}