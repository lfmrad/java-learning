package com.example.calculadora

import android.util.Log
import kotlin.math.pow

interface OperationToken {}
interface Calculation : OrderedToken {
    fun calculate(firstOperand: Double, secondOperand: Double): Double
}

interface SingleOperandCalculation : OrderedToken {
    fun calculate(opValue: Double): Double
}

interface OrderedToken : OrderOfOperations, OperationToken {
    val symbol: String
}

open class Number(var value: Double = 0.0) : OperationToken {
    // TODO property to handle self applying operations such as sin, tan, fact, etc. + interface to provide a method to compute them
    // ie MetaCalculation : Calculation; Number(new MetaCalculation to apply); number.computeMeta etc etc
    constructor(textToParse: String) : this(parseStringToNumber(textToParse))
    fun parseToString(num: Double = value) : String {
        return num.toInt().toString()
    }
    companion object {
        fun parseStringToNumber(textToParse: String) : Double {
            val parsedNumber = Integer.parseInt(textToParse).toDouble()
            return parsedNumber
        }
    }
}
class Result(value: Double = 0.0) : Number(value)
interface OrderOfOperations {
    enum class Priority(val value: Int) {
        PARENTHESES(4),
        EXPONENTIATION(3),
        PROD_AND_DIV(2),
        ADD_AND_SUB(1),
        EQUAL(0)
    }
    val priority: Priority
}

class Sum() : Calculation {
    override val symbol: String = "+"
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.ADD_AND_SUB
    override fun calculate(firstOperand: Double, secondOperand: Double): Double = firstOperand + secondOperand
}

class Subtraction() : Calculation {
    override val symbol: String = "-"
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.ADD_AND_SUB
    override fun calculate(firstOperand: Double, secondOperand: Double): Double = firstOperand - secondOperand
}

class Division() : Calculation {
    override val symbol: String = "/"
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.PROD_AND_DIV
    override fun calculate(firstOperand: Double, secondOperand: Double): Double = firstOperand / secondOperand
}

class Product() : Calculation {
    override val symbol: String = "x"
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.PROD_AND_DIV
    override fun calculate(firstOperand: Double, secondOperand: Double): Double = firstOperand * secondOperand
}

class Exponentiation() : Calculation {
    override val symbol: String = "^"
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.EXPONENTIATION
    override fun calculate(firstOperand: Double, secondOperand: Double): Double = firstOperand * secondOperand
}
class PowerOf(val powerValue: Double) : SingleOperandCalculation {
    override val symbol: String = "^2"
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.EXPONENTIATION
    override fun calculate(opValue: Double): Double = opValue.pow(powerValue)
}
class Sin() : SingleOperandCalculation {
    override val symbol: String = "sin"
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.EXPONENTIATION
    override fun calculate(opValue: Double): Double = Math.sin(opValue)
}

class Equal() : OrderedToken {
    override val symbol: String = "="
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.EQUAL
}

class Parenthesis(val open: Boolean) : OrderedToken {
    override val symbol: String = "()"
    override val priority: OrderOfOperations.Priority = OrderOfOperations.Priority.PARENTHESES
}

class Calculator {
    companion object {
        private var chunkPointer = 0
        val operationBuffer: MutableList<OperationToken> = mutableListOf()

        // TODO: FIX / MISSING IMPLEMENTATION (CHANGE OF MIND; from x to + mid operating)
        // apple: 2 + 3 x 3 x (display 9) + 1 (display 11) = 12
        // mine: 2 + 3 x 3 x (display 9) + 1 (missing display update) = 11
        // mine: 2 + 3 x 3 + (display 11) 1 = 12

        private var token1: OperationToken? = null
        private var token2: OperationToken? = null
        private var token3: OperationToken? = null
        private var token4: OperationToken? = null
        private fun moveAndRefresh(moveAhead: Boolean): Boolean {
            if (moveAhead && operationBuffer.size > 4) {
                Log.d("CUSTOM", "MOVED AHEAD!")
                chunkPointer += 2
                return true
            } else if (!moveAhead && chunkPointer == 2) {
                android.util.Log.d("CUSTOM", "MOVED BACK!")
                chunkPointer -= 2
                return true
            }
            refreshChunkHead()
            return false
        }

        private fun refreshChunkHead() {
            token1 = operationBuffer.getOrNull(chunkPointer)
            token2 = operationBuffer.getOrNull(chunkPointer + 1)
            token3 = operationBuffer.getOrNull(chunkPointer + 2)
            token4 = operationBuffer.getOrNull(chunkPointer + 3)
        }

        fun computeBuffer(): Number? {
            Log.d("CUSTOM", "ENTERED computeBufer(), size: ${operationBuffer.size}")
            operationBuffer.forEach {
                Log.d("CUSTOM", "<insideBuffer: ${it.javaClass.simpleName} >")
            }

            for ((i, operation) in operationBuffer.withIndex()) {
                if (operation is SingleOperandCalculation) {
                    val precedingNumber = (operationBuffer.getOrNull(i - 1) as? Number) ?: null
                    precedingNumber?.let {
                        precedingNumber.value = operation.calculate(precedingNumber.value)
                        operationBuffer.remove(operation)
                        Log.d("CUSTOM", "APPLIED SingleOperandCalculation")
                    }
                }
            }

            refreshChunkHead()
            // TODO computeInsideParenthesisFirst(operationBuffer) WIP

            // 2+>3x+9<
            if (token1 is Number && token2 is Calculation && token3 is Number && token4 is OrderedToken) {
                while (operationBuffer.size > 3) { // minimum computable chunk: X op X op2
                    refreshChunkHead()
                    val firstNum = token1 as Number
                    val firstOp = token2 as Calculation
                    val secondNum = token3 as Number
                    val secondOp = token4 as OrderedToken

                    Log.d("CUSTOM", "NUM1: ${firstNum.value}")
                    Log.d(
                        "CUSTOM",
                        "OP1: ${firstOp.javaClass.simpleName} & PRIORITY: ${firstOp.priority}"
                    )
                    Log.d("CUSTOM", "NUM2: ${secondNum.value}")
                    Log.d(
                        "CUSTOM",
                        "OP2: ${secondOp.javaClass.simpleName} & PRIORITY: ${secondOp.priority}"
                    )

                    if (firstOp.priority.value < secondOp.priority.value) { // if second op. is higher order...
                        Log.d("CUSTOM", "PRIORITY < PRIORITY")
                        // ie: >2 + 3 x<
                        if (moveAndRefresh(true)) { // see what's ahead (to group higher order op. with next number and compute that first)
                            Log.d(
                                "CUSTOM",
                                "CONTINUE 1: SEE WHAT'S AHEAD"
                            ) // ie >2 + 3 x< ...?? maybe "x 4 -" *
                            if (token4 == null) {
                                Log.d(
                                    "CUSTOM",
                                    "CONTROL: BREAK"
                                )
                                val result = Number((token3 as Number).value)
                                history.add(result)
                                return Result(result.value)
                            } else {
                                continue
                            }
                        } else { // nothing ahead; keep showing last number and keep waiting for more numbers; there isn't a result or update to provide
                            Log.d(
                                "CUSTOM",
                                "RETURN 0: NOTHING TO DO YET. WAITING for next number+op."
                            )
                            return Number(secondNum.value) // ** (see CASE3 below)
                        }
                    } else if (firstOp.priority.value >= secondOp.priority.value) { // if second op. is same or lower order, compute this chunk
                        Log.d("CUSTOM", "PRIORITY >= PRIORITY")
                        // here first or * after moving ahead: 2 + >3 x 4 -<
                        // computes operation >3x4 -< and updates the buffer >12 - _<
                        val newFirstNum: Number =
                            Number(firstOp.calculate(firstNum.value, secondNum.value))
                        operationBuffer[operationBuffer.indexOf(firstNum)] = newFirstNum
                        operationBuffer.remove(firstOp)
                        operationBuffer.remove(secondNum)
                        Log.d("CUSTOM", "CONTROL CHECK")
                        // NOW 3 THINGS CAN HAPPEN
                        if (chunkPointer == 0) { //
                            if (secondOp is Equal) {
                                // CASE 1: if NOT looking ahead AND secondOp is Equal... THIS IS END OF THE BUFFER
                                operationBuffer.clear() // clears it and then returns the final result after the if
                                val result = Result(newFirstNum.value)
                                history.add(result)
                                Log.d(
                                    "CUSTOM",
                                    "RETURN 1: Computed LAST chunk (EQUAL) and cleared the buffer. FINAL RESULT."
                                )
                                return result
                            } else { //
                                // CASE 2: if NOT looking and secondOp is not Equal -> uncompleted block >2 - _<
                                Log.d(
                                    "CUSTOM",
                                    "RETURN 2: Computed base chunk (op.A>op.B). NEED more number+op. to compute more."
                                )
                                // returns the update for the display and keeps waiting for next number to compute the operation (in the example '-')
                                return Number(newFirstNum.value)
                            }
                        } else if (moveAndRefresh(false)) {
                            // CASE 3: we're looking ahead & moveBack gets executed
                            // we'd be back to previous chunk >firstNum(prev.) firstOp(prev.) secondNum(this.firstNum) secondOp(this.secondOp)<;
                            // ** notice that the 'update' to the computed operation would get returned in there
                            Log.d(
                                "CUSTOM",
                                "CONTINUE 2: Computed chunk ahead (op.A>op.B) and moved back."
                            )
                            continue
                        }
                    }
                }

            } else if (token1 is Number && token2 is Calculation && token3 is Number) {
                moveAndRefresh(false)
                Log.d("CUSTOM", "RETURN 0: NOTHING TO DO YET. WAITING for next operation...")
                history.add(token3 as Number)
                operationBuffer.remove(token3)
                return Result((token3 as Number).value) // count as next number
            } else if (token1 is Number && token2 is Equal) {
                Log.d("CUSTOM", "RETURN 3: Computed meaningless op. + cleared the buffer")
                val lastIndex = history.lastIndex
                history.removeAt(lastIndex)
                history.removeAt(lastIndex - 1)
                operationBuffer.clear()
                return Result((token1 as Number).value)
            } else if (token1 is Number && token2 == null) {
                Log.d("CUSTOM", "RETURN 4: Computed SingleOperandCalculation update + cleared the buffer")
                val result = Result((token1 as Number).value)
                operationBuffer.clear()
                history.add(result)
                return result
            }
            Log.d("CUSTOM", "RETURN NULL: Not enough op. in the buffer to compute anything... ")
            return null
        }


        // calls a computeBuffer for what's inside the parenthesis after removing them so that
        // in computeBuffer we don't have to worry about processing parenthesis
        /*private fun computeInsideParenthesisFirst(operationBuffer: MutableList<OperationToken>) {
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
        }*/

        val history: MutableList<OperationToken> = mutableListOf()
        fun getHistory(): String? {
            return if (history.isNotEmpty()) {
                val parsedHistory: StringBuilder = StringBuilder()

                for ((i, token) in history.withIndex()) {
                    if (token is OrderedToken) {
                        parsedHistory.append(token.symbol)
                        if (token is SingleOperandCalculation) {
                            parsedHistory.append("=]")
                        }
                    } else if (token is Number) {
                        if (history.getOrNull(i + 1) is SingleOperandCalculation) {
                            parsedHistory.append("[")
                        }
                        parsedHistory.append(token.parseToString())
                        if (token is Result) {
                            parsedHistory.append("; ")
                        }
                    }
                }

                history.forEach {

                }

                parsedHistory.toString()
            } else {
                null
            }
        }

        // last: 2+3x3x
        // buffer: 2+9x >rem.> 2+
        // history: 2+3x3x >rem.> 2+3x3
        // buffer + history(last3) = 2+3x3
        // +newOp 2+3x3+ >compute> 11+
        fun unDo() {
            operationBuffer.removeLast()
            operationBuffer.removeLast()
            history.removeLast()
            val lastSavedIndex = history.lastIndex
            operationBuffer.add(history.get(lastSavedIndex - 2)) // prev. number
            operationBuffer.add(history.get(lastSavedIndex - 1)) // prev. op
            operationBuffer.add(history.get(lastSavedIndex)) // prev. number
        }
    }
}

// LEGACY DESIGN NOTES TO UNDERSTAND THE REQUIREMENTS AND DEVELOP computeBuffer()
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
// (1 + 3) - 2 x 3 x (4 + 3) x 5 + 3 x

// 0 1 2 3 4 5 6
// 2 + 2 x -> !moveAhead -> return 2 OK; now users add 3 x
// 2 + 2 x -> moveAhead -> continue
// 2 + >2 x 3 x< -> else if... -> >2 replaced by: 2x3=6
// removal of x, 3
// 2 + 6 x -> return 6 OK

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

// 2 + >2 x 3 exp<
