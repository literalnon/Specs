package com.example.bloold.specs

import java.util.*

/**
 * Created by bloold on 07.10.17.
 */
class State(private var model: Model, private var lastStep: Int?, var height: Int): Comparable<State> {
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
}