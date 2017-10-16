package com.example.bloold.specs

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by bloold on 07.10.17.
 */

class Algorithm(private val startState: State) {
    private var solveTree = PriorityQueue<State>()
    private var closed = ArrayList<State>()

    fun solve(): State? {
        solveTree.add(startState)
        var steps: Vector<State>

        while(!solveTree.isEmpty()){
            /*for (i: Int in 0..solveTree.size - 1) {
                Log.d("solve_tree", solveTree.toList().get(i).countDistance().toString())
                solveTree.toList().get(i).printState()
            }*/
            //получить множество состояний
            var cur_step = solveTree.poll()
            //Log.d("TAG", (cur_step.countDistance()).toString())
            //cur_step.printState()


            steps = cur_step.getPossibleSteps()

            closed.add(cur_step)
            //посчитать для каждого расстояние
            //положить на стек
            for (step: State in steps) {
                if(contains(step))
                    continue
                if(step.isSolve())
                    return step

                solveTree.add(step)
                //Log.d("stateadd", ":")
                //step.printState()
            }

            //Log.d("TAG", solveTree.size.toString() + " : " + closed.size)
        }

        return null
    }

    fun contains(state: State): Boolean{
        for(i: State in closed){
            var iVec = i.getGameModel()
            for(v: Int in 0..15){
                if(state.getGameModel()[v].value != iVec[v].value)
                    return false
            }
        }
        return true
    }
}