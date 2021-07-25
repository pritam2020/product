package com.example.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Response


class MainActivity2 : AppCompatActivity() {
    var TAG:String="getting data frm api"
    var recyclerAdapter:RecyclerAdapter?=null
    var recyclerView: RecyclerView?=null
    var progressBar:ProgressBar?=null
    var userName:TextView?=null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        recyclerView=findViewById(R.id.recycler)
        progressBar=findViewById(R.id.progressBar)
        userName=findViewById(R.id.username)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title =""
        userName?.setText(FirebaseAuth.getInstance().currentUser?.displayName)
        api()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
           R.id.SignOut -> AuthUI.getInstance().signOut(this)
               .addOnCompleteListener { //User is now signed out
                   Toast.makeText(applicationContext, "User is signed out", Toast.LENGTH_SHORT)
                       .show()
                   finish()
               }
           R.id.Delete -> AuthUI.getInstance().delete(this).addOnCompleteListener { task ->
               if (task.isSuccessful) {
                   Toast.makeText(applicationContext,
                       "User is deleted successfully",
                       Toast.LENGTH_SHORT).show()
                   finish()
               } else {
                   Toast.makeText(applicationContext, "Unsuccessful", Toast.LENGTH_SHORT).show()
               }
           }

            else -> print("otherwise")
        }

        return super.onOptionsItemSelected(item)
    }
    fun api(){
        progressBar?.visibility= View.VISIBLE
        if(isNetworkConnected(this)) {
            val request = Retrofit.buildService(Query::class.java)
            val call = request.getProducts()
            call.enqueue(object : retrofit2.Callback<List<Model>> {

                override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {
                    Log.i(TAG, "" + response.body())
                    recyclerView?.layoutManager = LinearLayoutManager(this@MainActivity2)
                    recyclerAdapter= response.body()?.let { RecyclerAdapter(it,this@MainActivity2) }
                    recyclerView?.adapter=recyclerAdapter
                    progressBar?.visibility= View.INVISIBLE
                }

                override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                    Log.i(TAG, "" + t)
                    Toast.makeText(this@MainActivity2,"unable to load data",Toast.LENGTH_SHORT).show()
                    progressBar?.visibility= View.INVISIBLE
                }
            })
        }
        else {
            Toast.makeText(this, "no connection", Toast.LENGTH_SHORT).show()
            progressBar?.visibility= View.INVISIBLE
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}