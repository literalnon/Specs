package com.example.bloold.specs

import android.util.Log
import java.text.FieldPosition
import java.util.*

/**
 * Created by bloold on 07.10.17.
 */
class State(public var model: Model, public var lastStep: Int?, public var prevState: State?, var height: Int): Comparable<State> {
    override fun compareTo(other: State): Int {
        return countDistance() - other.countDistance()
    }

    fun isSolve(): Boolean{
        return model.isWin()
    }

    fun getPossibleSteps(): Vector<State>{
        return model.getPossibleSteps(lastStep, this, height)
    }

    fun countDistance(): Int{
        return model.countDistance() + height
    }

    fun getGameModel(): Vector<Button>{
        return model.gameModel
    }

    fun printState(){
        /*for(i: Int in 15 downTo 0){
            Log.d("Solve ${i}", model.gameModel[i].value.toString())
        }*/

        for (i: Int in 0..3) {
            Log.w("Solve", "${model.gameModel[4 * i].value} " +
                                    "${model.gameModel[4 * i + 1].value} " +
                                    "${model.gameModel[4 * i + 2].value} " +
                                    "${model.gameModel[4 * i + 3].value}")
        }

        Log.w("Solve", " --------------- ")
    }

    fun screenMove(){
        model.screenMove()
    }

    fun screenMovePos(position: Int){
        model.tryToMove(position)
    }
}