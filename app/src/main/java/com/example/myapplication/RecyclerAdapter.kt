package com.example.myapplication


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class RecyclerAdapter(private val listdata: List<Model>,private val context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = listdata[position].title
        Picasso.get().load(listdata[position].image).into(holder.imageView)
        holder.category.text = listdata[position].category
        holder.price.text=listdata[position].price.toString()
        holder.constraintLayout.setOnClickListener{
            var arraylist=ArrayList<Model>()
            arraylist.addAll(listdata)
            val intent = Intent(context,MainActivity3::class.java)
            intent.putExtra("price",listdata[position].price.toString())
            intent.putExtra("category",listdata[position].category)
            intent.putExtra("description",listdata[position].description)
            intent.putExtra("id",listdata[position].id)
            intent.putExtra("image",listdata[position].image)
            intent.putExtra("title",listdata[position].title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listdata.size

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var title: TextView
        var price: TextView
        var category: TextView
        var constraintLayout: ConstraintLayout

        init {
            imageView = itemView.findViewById(R.id.image) as ImageView
            title = itemView.findViewById(R.id.title)
            price=itemView.findViewById(R.id.price)
            category=itemView.findViewById(R.id.category)
            constraintLayout = itemView.findViewById(R.id.constrainLayout)
        }
    }



}