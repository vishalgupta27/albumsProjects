package com.example.albums.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.albums.InforWeat
import com.example.albums.R
import com.example.albums.WeathersAPI


class WeathersAdapter(
    private val mList: List<InforWeat>,
    private val context: Context,
    private val btnlistener: BtnClickListener

) : RecyclerView.Adapter<WeathersAdapter.ViewHolder>() {
    companion object {
        var mClickListener: BtnClickListener? = null
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weathersitems, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        mClickListener = btnlistener
        val itemModelList = mList[position]
  /*      holder.id.text = itemModelList.city.id.toString()
        holder.main.text = itemModelList.weaList.dtTxt
        holder.main.text = itemModelList.weather.main
        holder.description.text = itemModelList.weather.description
        holder.speed.text = itemModelList.wind.speed.toString()
        holder.visibility.text = itemModelList.weaList.visibility.toString()*/
        Log.d("TAG", "name>>>>>>>>>>>>"+itemModelList.city.name)
        holder.name.text = itemModelList.city.name
        holder.sunrise.text = itemModelList.city.sunrise.toString()
        holder.sunset.text = itemModelList.city.sunset.toString()
//        (context as WeathersAPI).convertTimestampToDateTime(itemModelList.city.sunrise, itemModelList.city.sunset)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

   open interface BtnClickListener {
        fun onBtnClick(position: Int, type: String)

    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView =itemView.findViewById(R.id.tv_Weaname)
        val sunrise: TextView =itemView.findViewById(R.id.tv_sunrise)
        val sunset: TextView =itemView.findViewById(R.id.tv_sunset)


    }

}

