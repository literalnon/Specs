package com.example.bloold.specs

import android.util.Log
import android.view.Display
import java.lang.Math.abs
import java.lang.Math.sqrt
import java.util.*

/**
 * Created by bloold on 18.09.17.
 */
interface onScreenUpdate{
    fun screenUpdate(gameModel: Vector<Button>)
}

class Model {
    private var size: Int = 16
    private var halfSize: Int = 4

    var gameModel = Vector<Button>()
    private lateinit var listener: onScreenUpdate

    constructor(size: Int = 9, listener: onScreenUpdate){
        this.size = size - 1
        this.listener = listener
        halfSize = sqrt(size.toDouble()).toInt()
        randomFill()
    }

    fun randomFill(){
        var visible = true

        /*gameModel.addElement(Button(getX(0), getY(0), 7, visible))
        gameModel.addElement(Button(getX(1), getY(1), 5, visible))
        gameModel.addElement(Button(getX(2), getY(2), 10, visible))
        gameModel.addElement(Button(getX(3), getY(3), 11, visible))
        gameModel.addElement(Button(getX(4), getY(4), 2, visible))
        gameModel.addElement(Button(getX(5), getY(5), 12, visible))
        gameModel.addElement(Button(getX(6), getY(6), 4, visible))
        gameModel.addElement(Button(getX(7), getY(7), 1, visible))
        gameModel.addElement(Button(getX(8), getY(8), 6, visible))
        gameModel.addElement(Button(getX(9), getY(9), 13, visible))
        gameModel.addElement(Button(getX(10), getY(10), 3, visible))
        gameModel.addElement(Button(getX(11), getY(11), 8, visible))
        gameModel.addElement(Button(getX(12), getY(12), 9, visible))
        gameModel.addElement(Button(getX(13), getY(13), 15, visible))
        gameModel.addElement(Button(getX(14), getY(14), 16, false))
        gameModel.addElement(Button(getX(15), getY(15), 14, visible))*/

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
            //Log.d("${i}", gameModel[i].value.toString())
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
        move(position)

        listener.screenUpdate(gameModel)
    }

    fun screenMove(){
        listener.screenUpdate(gameModel)
    }

    private fun move(position: Int): Model{
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
            //Log.d("moveb", "1")
        }
        return this
    }

    fun countDistance(): Int{
        var distanceM = 0
        var distanceL = 0

        for (i: Int in 0..size) {
            if(gameModel[i].visible) {
                distanceM += distanceOneButton(i, gameModel[i].value - 1)

                /*//x
                for (j: Int in (getY(i) + 1)..(halfSize - 1)) {
                    //Log.d("tagX $i : $j : ${getY(i)}", (i + halfSize  - j).toString())
                    val pos = i + halfSize - j
                    if ( gameModel[pos].value != size && (getX(gameModel[i].value - 1) == getX(i)) && (getX(gameModel[pos].value - 1) == getX(i)) && gameModel[i].value > gameModel[pos].value) { //j * halfSize + getY(i)
                        distanceL += 2
                    }
                }
                //y
                for (j: Int in (getX(i) + 1)..(halfSize - 1)) {
                    //Log.d("tagY $i : $j : ${getX(i)}", (i + (halfSize  - j) * j).toString())
                    val pos = i + (halfSize - j) * j
                    if (gameModel[pos].value != size && (getY(gameModel[i].value - 1) == getY(i)) && (getY(gameModel[i + (halfSize - j) * j].value - 1) == getY(i)) && gameModel[i].value > gameModel[i + (halfSize - j) * j].value) { //getX(i) * halfSize + j
                        distanceL += 2
                    }
                }*/
            }
        }

        //Log.d("distance", "l: $distanceL m: $distanceM")
        return distanceL + distanceM
    }

    fun distanceOneButton(curState: Int, realState: Int): Int{
        return  abs(getX(curState) - getX(realState)) + abs(getY(curState) - getY(realState))
    }

    fun print(){
        for(i: Int in 0..15){
            Log.d("Solve ${i}", gameModel[i].value.toString())
        }
    }


    fun getPossibleSteps(lastSte: Int?, prevState: State?, h: Int): Vector<State>{
        val x: Int = getX(getButton(size + 1))
        val y: Int = getY(getButton(size + 1))
/*
        Log.d("insteps", "${lastStep}")
        print()
*/
        var curStep = x * halfSize + y
        var possibleSteps = Vector<State>()
        var copyModel: Model

        val lastStep = lastSte
        //x + 1, y
        var newStep = (x + 1) * halfSize + y
        if(newStep != lastStep && newStep >= 0 && newStep <= size && getY(newStep) == y){
            copyModel = Model(size + 1, listener)
            for(i: Int in 0..15)
                copyModel.gameModel[i] = Button(gameModel[i].getCoordinates().first,
                        gameModel[i].getCoordinates().second,
                        gameModel[i].value,
                        gameModel[i].visible)
            possibleSteps.add(State(copyModel.move(newStep), curStep, prevState, h + 1))
        }

        //x - 1, y
        newStep = (x - 1) * halfSize + y
        if(newStep != lastStep && newStep >= 0 && newStep <= size && getY(newStep) == y){
            copyModel = Model(size + 1, listener)
            for(i: Int in 0..15)
                copyModel.gameModel[i] = Button(gameModel[i].getCoordinates().first,
                        gameModel[i].getCoordinates().second,
                        gameModel[i].value,
                        gameModel[i].visible)
            possibleSteps.add(State(copyModel.move(newStep), curStep, prevState, h + 1))
        }

        //x, y + 1
        newStep = x * halfSize + y + 1
        if(newStep != lastStep && newStep >= 0 && newStep <= size && getX(newStep) == x){
            copyModel = Model(size + 1, listener)
            for(i: Int in 0..15)
                copyModel.gameModel[i] = Button(gameModel[i].getCoordinates().first,
                        gameModel[i].getCoordinates().second,
                        gameModel[i].value,
                        gameModel[i].visible)
            possibleSteps.add(State(copyModel.move(newStep), curStep, prevState, h + 1))
        }

        //x, y - 1
        newStep = x * halfSize + y - 1
        //Log.d("TAG", "newStep: ${newStep} $x $halfSize $y $lastStep ${getX(newStep)}")
        if(newStep != lastStep && newStep >= 0 && newStep <= size && getX(newStep) == x){
            //Log.d("TAG", "newStep: true")
            copyModel = Model(size + 1, listener)
            for(i: Int in 0..15)
                copyModel.gameModel[i] = Button(gameModel[i].getCoordinates().first,
                        gameModel[i].getCoordinates().second,
                        gameModel[i].value,
                        gameModel[i].visible)
            possibleSteps.add(State(copyModel.move(newStep), curStep, prevState, h + 1))
        }

        return possibleSteps
    }
}