package com.example.bloold.specs

import android.util.Log
import java.util.*

/**
 * Created by bloold on 07.10.17.
 */

class Algorithm(private val startState: State) {
    private var solveTree = PriorityQueue<State>()
    private var closed = PriorityQueue<State>()

    fun solve(): State? {
        solveTree.add(startState)
        var steps: Vector<State>

        while(!solveTree.isEmpty()){
            //получить множество состояний
            var cur_step = solveTree.remove()
            steps = cur_step.getPossibleSteps()

            closed.add(cur_step)
            //посчитать для каждого расстояние
            //положить на стек
            for(step: State in steps) {
                if(closed.contains(step))
                    continue
                if(step.isSolve())
                    return step

                solveTree.add(step)
            }

            Log.d("TAG", steps.size.toString() + " : " + closed.size)
        }

        return null
    }
}