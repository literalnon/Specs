package com.example.bloold.specs

import android.graphics.ColorSpace
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity(), Adapter.onButtonListener, onScreenUpdate {
    private lateinit var gvField: GridView
    private var size = 8

    private var model: Model = Model(size, this)
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gvField = findViewById(R.id.gvField) as GridView

        gvField.numColumns = 3

        adapter = Adapter(this, model.gameModel)

        gvField.adapter = adapter
        gvField.horizontalSpacing = 24
        gvField.verticalSpacing = 24
    }

    override fun onButtonClick(position: Int) {
        model.tryToMove(position)
    }

    override fun screenUpdate(gameModel: Vector<Button>) {
        adapter.replaceAll(gameModel)
        if(model.isWin())
            Toast.makeText(this, "Поздравляем!", Toast.LENGTH_LONG).show()
    }
}
