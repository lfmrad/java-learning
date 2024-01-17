package com.example.calculadora

import android.util.Log

interface OperationToken {}
interface Calculation : OrderOfOperations, OperationToken {
    fun calculate(firstOperand: Double, secondOperand: Double): Double
}

class Number(var num: Double = 0.0) : OperationToken {
    constructor(textToParse: String) : this(parseStringToNumber(textToParse))
    companion object {
        fun parseStringToNumber(textToParse: String) : Double {
            val parsedNumber = Integer.parseInt(textToParse).toDouble()
            return parsedNumber
        }

        fun parseNumberToString(num: Double) : String {
            return num.toInt().toString()
        }
    }
}
enum class Value(val number: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4)
}
interface OrderOfOperations {
    enum class Priority(val value: Int) {
        PARENTHESES(4),
        EXPONENTIATION(3),
        PROD_AND_DIV(2),
        ADD_AND_SUB(1)
    }
    val priority: Priority
}

class Sum() : Calculation {
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.ADD_AND_SUB
    override fun calculate(firstOperand: Double, secondOperand: Double): Double = firstOperand + secondOperand
}

class Subtraction() : Calculation {
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.ADD_AND_SUB
    override fun calculate(firstOperand: Double, secondOperand: Double): Double = firstOperand - secondOperand
}

class Division() : Calculation {
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.PROD_AND_DIV
    override fun calculate(firstOperand: Double, secondOperand: Double): Double = firstOperand / secondOperand
}

class Product() : Calculation {
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.PROD_AND_DIV
    override fun calculate(firstOperand: Double, secondOperand: Double): Double = firstOperand * secondOperand
}

class Equal() : OperationToken

class Parenthesis(val open: Boolean) : OperationToken, OrderOfOperations {
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.PARENTHESES
}

class Calculator {
    companion object {
        val history: MutableList<OperationToken> = mutableListOf()
        var chunkPointer = 0
        var resultPending: Boolean = false
        // SUPER WIP
        // 2 + 2 x 3 x (4 + 3) x 5 + 3 x 2
        // 2 .+ 2 x. // (+ < x), bring more
        // 2 + 2 .x 3 x. // (x = x), operate
        // 2 .+ 6 x. // (+ < x), bring more
        // 2 + 6 x ( 4 // get full () and recursive for computeBuffer()
        // 2 + 6 .x 7 x. // (x >= x), operate
        // 2 .+ 42 x. // (+ < x), bring more
        // 2 + 42 .x 5 +. // (x >= +), operate
        // 2 +. 210 +. // (+ => x), operate
        // 212 + 3 x // (+ < x), bring more
        // 212 .+ 3 x. 2 // (x > null), operate
        // 212 .+ 6 // (+ > null), operate
        // 218
        // (1 + 3) - 2 x 3 x (4 + 3) x 5 + 3 x 2

        fun computeBuffer(operationBuffer: MutableList<OperationToken>): Double? {
            Log.d("CUSTOM", "ENTERED computeBuffer, size: ${operationBuffer.size}")
            operationBuffer.forEach {
                Log.d("CUSTOM", "(inside: ${it.javaClass.simpleName} ")
            }
            // computeInsideParenthesisFirst(operationBuffer) // process parenthesis first so
            while (operationBuffer.size > 3) { // minimum computable chunk: X op X op2
                val firstNum = (operationBuffer[chunkPointer] as Number)
                Log.d("CUSTOM", "NUM1: ${firstNum.num}")
                val firstOp = (operationBuffer[chunkPointer + 1] as Calculation)
                Log.d("CUSTOM", "OP1: ${firstOp.javaClass.simpleName} & PRIORITY: ${firstOp.priority}")
                val secondNum = (operationBuffer[chunkPointer + 2] as Number)
                Log.d("CUSTOM", "NUM2: ${secondNum.num}")
                var secondOp = operationBuffer[chunkPointer + 3]
                Log.d("CUSTOM", "OP2: ${secondOp.javaClass.simpleName}")

                // DESIGN NOTES
                // 0 1 2 3 4 5 6
                // 2 + 2 x -> !moveAhead -> return 2 OK; now users add 3 x
                // 2 + 2 x -> moveAhead -> continue
                // 2 + >2 x 3 x< -> else if... -> >2 replaced by: 2x3=6
                // removal of x, 3
                // 2 + 6 x -> return 6 OK

                // DESIGN NOTES
                // 0 1 2 3 4 5 6
                // 2 + 3 x -> !moveAhead -> return 2 OK; now users add 3 x
                // 2 + 3 x -> moveAhead -> continue
                // 2 + >3 x 4 x< -> else if... -> >3 replaced by: 3x4=12
                // removal of >(12) (x 4) x< -> moveBack
                // 2 + 12 x -> return 12 OK
                // 2 + 12 x 1 = -> this is true, now what happens?
                // >2 + 12< x 1 = -> moveAhead
                // 2 + >12 x 1 =< -> secondOp is not calc so else if...
                // 2 + 12 =

                if (secondOp is Calculation) {
                    if (firstOp.priority.value < secondOp.priority.value) {
                        if (moveAhead(operationBuffer)) {
                            continue
                        } else {
                            Log.d("CUSTOM", "RETURN: 1")
                            return secondNum.num
                        }
                    } else if (firstOp.priority.value >= secondOp.priority.value) {
                        firstNum.num = firstOp.calculate(firstNum.num, secondNum.num)
                        operationBuffer.remove(firstOp)
                        operationBuffer.remove(secondNum)
                        moveBack() // only if moveAhead happened
                        Log.d("CUSTOM", "RETURN: 2")
                        return firstNum.num
                    }
                } else if (secondOp is Equal && chunkPointer == 0) {
                    operationBuffer.clear()
                    return firstOp.calculate(firstNum.num, secondNum.num)
                } else if (secondOp is Equal) {
                    Log.d("CUSTOM", "ENTERED ELSE! pointer $chunkPointer")
                    firstNum.num = firstOp.calculate(firstNum.num, secondNum.num)
                    operationBuffer.remove(firstOp)
                    operationBuffer.remove(secondNum)
                    moveBack() // only if moveAhead happened
                }
            }
            Log.d("CUSTOM", "RETURN: NULL")
            return null
        }

        fun moveAhead(operationBuffer: MutableList<OperationToken>): Boolean {
            return if (operationBuffer.size > 4) {
                chunkPointer += 2
                Log.d("CUSTOM", "MOVE AHEAD!")
                true
            } else {
                false
            }
        }
        fun moveBack(): Boolean {
            return if (chunkPointer == 2) {
                chunkPointer -= 2
                Log.d("CUSTOM", "MOVED BACK!")
                true
            } else {
                false
            }
        }

        // calls a computeBuffer for what's inside the parenthesis after removing them so that
        // in computeBuffer we don't have to worry about processing parenthesis
        private fun computeInsideParenthesisFirst(operationBuffer: MutableList<OperationToken>) {
            operationBuffer.forEach {
                if (it is Parenthesis && (it as Parenthesis).open) {
                    operationBuffer.remove(it)
                    operationBuffer.forEach {
                        if (it is Parenthesis) {
                            val upTo = operationBuffer.indexOf(it)
                            operationBuffer.remove(it)
                            computeBuffer(operationBuffer.subList(0, upTo))
                        } else {
                            computeBuffer(operationBuffer) // till the end if there is no closure
                        }
                    }
                } else if (it is Parenthesis && !(it as Parenthesis).open) {
                    operationBuffer.remove(it) // discards it if first is )
                }
            }
        }

        fun computeBufferToString(operationBuffer: MutableList<OperationToken>): String? {
            return computeBuffer(operationBuffer)?.let { Number.parseNumberToString(it) }
        }
    }
}