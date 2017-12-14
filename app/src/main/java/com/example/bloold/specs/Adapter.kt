package com.example.bloold.specs

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView.ScaleType
import android.widget.GridView
import java.util.*
import android.view.LayoutInflater




/**
 * Created by bloold on 13.09.17.
 */
class Adapter(private val context: Context, private var btnList: Vector<com.example.bloold.specs.Button>) : BaseAdapter() {
    interface onButtonListener {
        fun onButtonClick(position: Int)
    }

    private lateinit var listener: onButtonListener

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var btnDigit: Button

        if (convertView == null) {
            btnDigit = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.item_button, parent, false) as Button;
        } else {
            btnDigit = convertView as Button
        }

        btnDigit.text = btnList[position].value.toString()

        try {
            listener = context as onButtonListener
        }catch (e: ClassCastException){
            e.printStackTrace()
        }

        btnDigit.setOnClickListener({
            listener.onButtonClick(position)
            //Log.d("click", position.toString())
        })

        btnDigit.background = context.getDrawable(R.drawable.button_shape)

        if(btnList[position].visible)
            btnDigit.visibility = View.VISIBLE
        else{
            btnDigit.visibility = View.INVISIBLE
        }

        return btnDigit
    }

    override fun getItem(p0: Int): Any {
        return btnList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return btnList.size
    }

    fun replaceAll(gameModel: Vector<com.example.bloold.specs.Button>){
        btnList = gameModel
        notifyDataSetChanged()
    }
}