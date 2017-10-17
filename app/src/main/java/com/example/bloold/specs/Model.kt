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

        /*gameModel.addElement(Button(getX(0), getY(0), 1, visible))
        gameModel.addElement(Button(getX(1), getY(2), 1 + 1, visible))
        gameModel.addElement(Button(getX(2), getY(3), 2 + 1, visible))
        gameModel.addElement(Button(getX(3), getY(4), 3 + 1, visible))
        gameModel.addElement(Button(getX(4), getY(4), 4 + 1, false))
        gameModel.addElement(Button(getX(5), getY(5), 5 + 1, visible))
        gameModel.addElement(Button(getX(6), getY(6), 6 + 1, visible))
        gameModel.addElement(Button(getX(7), getY(7), 7 + 1, visible))
        gameModel.addElement(Button(getX(8), getY(8), 8 + 1, visible))
        gameModel.addElement(Button(getX(9), getY(9), 9 + 1, visible))
        gameModel.addElement(Button(getX(10), getY(10), 10 + 1, visible))
        gameModel.addElement(Button(getX(15), getY(15), 15 + 1, false))
        gameModel.addElement(Button(getX(12), getY(12), 12 + 1, visible))
        gameModel.addElement(Button(getX(13), getY(13), 13 + 1, visible))
        gameModel.addElement(Button(getX(14), getY(14), 14 + 1, visible))
        gameModel.addElement(Button(getX(11), getY(11), 11 + 1, visible))*/

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
        var distance = 0

        for (i: Int in 0..size) {
            distance += distanceOneButton(i, gameModel[i].value - 1)
        }

        return distance
    }

    fun distanceOneButton(curState: Int, realState: Int): Int{
        return  abs(getX(curState) - getX(realState)) + abs(getY(curState) - getY(realState))
    }

    fun print(){
        for(i: Int in 0..15){
            Log.d("Solve ${i}", gameModel[i].value.toString())
        }
    }


    fun getPossibleSteps(lastStep: Int?, prevState: State?, h: Int): Vector<State>{
        val x: Int = getX(getButton(size + 1))
        val y: Int = getY(getButton(size + 1))
/*
        Log.d("insteps", "${lastStep}")
        print()
*/
        var curStep = x * halfSize + y
        var possibleSteps = Vector<State>()
        var copyModel: Model

        //x + 1, y
        var newStep = (x + 1) * halfSize + y
        if(newStep != lastStep && newStep > 0 && newStep <= size && getY(newStep) == y){
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
        if(newStep != lastStep && newStep > 0 && newStep <= size && getY(newStep) == y){
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
        if(newStep != lastStep && newStep > 0 && newStep <= size && getX(newStep) == x){
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
        if(newStep != lastStep && newStep > 0 && newStep <= size && getX(newStep) == x){
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