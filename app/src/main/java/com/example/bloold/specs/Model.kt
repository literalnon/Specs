package com.example.bloold.specs

import android.util.Log
import java.util.*

/**
 * Created by bloold on 18.09.17.
 */
interface onScreenUpdate{
    fun screenUpdate(gameModel: Vector<Button>)
}

class Model {
    private var size: Int = 8
    private var halfSize: Int = 3

    var gameModel = Vector<Button>()
    private lateinit var listener: onScreenUpdate

    constructor(size: Int = 8, listener: onScreenUpdate){
        this.size = size
        this.listener = listener
        rendomFill()
    }

    fun rendomFill(){
        var visible = true

        for(i: Int in 0..size){
            if(i == size)
                visible = false
            gameModel.addElement(Button(getX(i), getY(i), i + 1, visible))
        }

    }

    fun getX(position: Int): Int{
        return position / halfSize
    }

    fun getY(position: Int): Int{
        return position % halfSize
    }

    fun isWin(): Boolean {
        for (i: Int in 0..size) {
            if(gameModel[i].value != i + 1)
                return false
        }
        return true
    }

    fun getButton(value: Int): Int {
        for (i: Int in 0..size) {
            if(gameModel[i].value == value)
                return i
        }
        return size
    }

    fun tryToMove(position: Int){
        val tryX: Int = getX(position)
        val tryY: Int = getY(position)

        var button = gameModel[getButton(size + 1)]

        if(button.getCoordinates().equals(Pair(tryX - 1, tryY)) ||
                button.getCoordinates().equals(Pair(tryX + 1, tryY)) ||
                button.getCoordinates().equals(Pair(tryX, tryY - 1)) ||
                button.getCoordinates().equals(Pair(tryX, tryY + 1))) {
            var value = button.value
            gameModel[getButton(size + 1)].visible = true
            gameModel[getButton(size + 1)].value = gameModel[position].value
            gameModel[position].value = value
            gameModel[position].visible = false
            Log.d("moveb", "1")
        }
        Log.d("move", position.toString() + " " + gameModel[getButton(size + 1)].getCoordinates().toString() + " " + Pair(tryX, tryY).toString())
        listener.screenUpdate(gameModel)
    }
}