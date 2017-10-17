package com.example.bloold.specs

import android.graphics.ColorSpace
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.GridView
import android.widget.Toast
import java.lang.Math.sqrt
import java.util.*

class MainActivity : AppCompatActivity(), Adapter.onButtonListener, onScreenUpdate {
    private lateinit var gvField: GridView
    private lateinit var btnStart: android.widget.Button
    private lateinit var algorithm: Algorithm
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private var size = 16

    private var model: Model = Model(size, this)
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gvField = findViewById(R.id.gvField)

        gvField.numColumns = sqrt(size.toDouble()).toInt()

        adapter = Adapter(this, model.gameModel)

        gvField.adapter = adapter
        gvField.horizontalSpacing = 24
        gvField.verticalSpacing = 24

        btnStart = findViewById(R.id.btnStart)
        btnStart.setOnClickListener( { v ->
            val startState = com.example.bloold.specs.State(model, null, null,0)
            var state: State?

            btnStart.text = "Wait..."

            if(startState.isSolve()) {
                state = startState
            } else {
                algorithm = Algorithm(startState)
                state = algorithm.solve()
            }

            btnStart.text = "Start"

            val vector = Vector<State>()

            while(state?.prevState != null){
                vector.add(state)
                state = state.prevState
            }
            vector.add(state)

            handler = Handler()
            runnable = Runnable {
                if(!vector.isEmpty()){
                    var i = vector.removeAt(vector.size - 1)
                    if(i != null) {
                        i.screenMove()
                        model = i.model
                    }

                    handler.postDelayed(runnable, 700)
                }
            }

            handler.postDelayed(runnable, 700)
        } )
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
