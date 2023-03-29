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
import com.example.retroproject.model.Data
import com.example.retroproject.R


class Recyclerviewadapters(
    private val mList: List<Data>,
    private val context: Context,
    private val btnlistener: BtnClickListener

) : RecyclerView.Adapter<Recyclerviewadapters.ViewHolder>() {
    companion object {
        var mClickListener: BtnClickListener? = null
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.desgibi, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        mClickListener = btnlistener
        val itemModelList = mList[position]
        holder.user_name.text = itemModelList.user_name
        holder.distance.text = itemModelList.distance  +" km"
        /*Glide.with(context)
            .load(itemModelList.meet_profile_image[itemModelList.meet_profile_image.size-1)for last images print
            .into(holder.meet_profile_images)*/
        if (itemModelList.meet_profile_image.isNotEmpty()) {
            Glide.with(context)
                .load(itemModelList.meet_profile_image[0])
                .into(holder.meet_profile_images);
        }
        holder.rlParent.setOnClickListener{
            if(mClickListener != null) {
                mClickListener?.onBtnClick(position, "moveToOtherScreen")
            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

   open interface BtnClickListener {
        fun onBtnClick(position: Int, type: String)

    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val user_name: TextView = itemView.findViewById(R.id.left_text_view)
        val distance: TextView = itemView.findViewById(R.id.right_text_view)
        val meet_profile_images: ImageView = itemView.findViewById(R.id.rl_images)
        val rlParent: ImageView = itemView.findViewById(R.id.rl_images)

    }

}

