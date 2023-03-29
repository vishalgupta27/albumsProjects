package com.example.retroproject.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.albums.AlbumsModelsItem
import com.example.albums.R


class AlbumsAdapters(
    private val albumsList: List<AlbumsModelsItem>,
    private val context: Context,


    ) : RecyclerView.Adapter<AlbumsAdapters.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.albumsfielsd, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val itemModelList = albumsList[position]
        holder.ids.text = itemModelList.id.toString()
        holder.albumsTitile.text = itemModelList.title
        Glide.with(context)
            .load(albumsList.get(position).thumbnailUrl)
            .into(holder.albumsImage)



    }

    override fun getItemCount(): Int {
        return albumsList.size
    }



    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ids: TextView = itemView.findViewById(R.id.albums_id)
        val albumsTitile: TextView = itemView.findViewById(R.id.albums_title)
        val albumsImage: ImageView = itemView.findViewById(R.id.iv_albums)


    }

}

