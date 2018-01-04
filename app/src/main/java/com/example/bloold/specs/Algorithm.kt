package com.example.bloold.specs

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashSet

/**
 * Created by bloold on 07.10.17.
 */

class Algorithm(private val startState: State) {
    private var solveTree = PriorityQueue<State>()
    private var closed = HashSet<State>()

    fun solve(): State? {
        if(!startState.isSolve())
            solveTree.add(startState)

        var steps: Vector<State>

        while(!solveTree.isEmpty()){

            /*for (i: Int in 0..solveTree.size - 1) {
                Log.d("solve_tree", solveTree.toList().get(i).countDistance().toString())
                solveTree.toList().get(i).printState()
            }*/
            //получить множество состояний
            val cur_step = solveTree.peek()//solveTree.min()!!
            solveTree.remove(cur_step)

            //Log.d("TAG", (cur_step.countDistance()).toString())

            closed.add(cur_step)

            //Log.d("TAG", "${cur_step.countDistance()}")
            //cur_step.printState()

            steps = cur_step.getPossibleSteps()

            //Log.d("possible", steps.size.toString())

            /*for (i: Int in 0..steps.size - 1) {
                Log.d("possible", (steps[i].countDistance() - steps[i].height).toString())
                steps[i].printState()
            }*/
            //посчитать для каждого расстояние
            //положить на стек
            for (step: State in steps) {
                //Log.d("stateinadd", ":")
                //step.printState()
                if(contains(step))
                    continue
                if(step.isSolve())
                    return step

                //if (!stContains(step))
                solveTree.add(step)

                //Log.d("stateadd", ":")

                //step.printState()
            }

            //Log.d("TAG", solveTree.size.toString() + " : " + closed.size)

            /*for (k: Int in 0..10000) {

            }*/
        }

        return null
    }

    fun contains(state: State): Boolean{
        for(i: State in closed){
            val iVec = i.getGameModel()
            var res = true
            for(v: Int in 15 downTo 0){
                //Log.d("${i}", "${state.getGameModel()[v].value} : ${iVec[v].value}")
                res = (state.getGameModel()[v].value == iVec[v].value) && res
                if(!res)
                    continue
            }
            if(res)
                return res
        }
        return false
    }

    fun stContains(state: State): Boolean{
        for(i: State in solveTree){
            val iVec = i.getGameModel()
            var res = true
            for(v: Int in 15 downTo 0){
                //Log.d("${i}", "${state.getGameModel()[v].value} : ${iVec[v].value}")
                res = (state.getGameModel()[v].value == iVec[v].value) && res
                if(!res)
                    continue
            }
            if(res)
                return res
        }
        return false
    }
}