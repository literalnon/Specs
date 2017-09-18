package com.example.bloold.specs

/**
 * Created by bloold on 18.09.17.
 */
class Button(private var x: Int, private var y: Int, var value: Int, var visible: Boolean) {

    fun getCoordinates(): Pair<Int, Int>{
        return Pair(x, y)
    }
}