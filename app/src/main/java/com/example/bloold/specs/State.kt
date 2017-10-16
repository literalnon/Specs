package com.example.bloold.specs

import android.util.Log
import java.text.FieldPosition
import java.util.*

/**
 * Created by bloold on 07.10.17.
 */
class State(private var model: Model, public var lastStep: Int?, var height: Int): Comparable<State> {
    override fun compareTo(other: State): Int {
        return countDistance() + height - other.countDistance() - other.height
    }

    fun isSolve(): Boolean{
        return model.isWin()
    }

    fun getPossibleSteps(): Vector<State>{
        return model.getPossibleSteps(lastStep, height)
    }

    fun countDistance(): Int{
        return model.countDistance()
    }

    fun getGameModel(): Vector<Button>{
        return model.gameModel
    }

    fun printState(){
        for(i: Int in 0..15){
            Log.d("Solve ${i}", model.gameModel[i].value.toString())
        }
    }

    fun screenMove(){
        model.screenMove()
    }

    fun screenMovePos(position: Int){
        model.tryToMove(position)
    }
}