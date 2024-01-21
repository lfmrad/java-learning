package com.example.calculadora

import android.util.Log

interface OperationToken {}
interface Calculation : OrderedToken {
    fun calculate(firstOperand: Double, secondOperand: Double): Double
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

        fun computeBuffer(): Number? {
            Log.d("CUSTOM", "ENTERED computeBufer(), size: ${operationBuffer.size}")
            operationBuffer.forEach {
                Log.d("CUSTOM", "<insideBuffer: ${it.javaClass.simpleName} >")
            }

            // TODO computeInsideParenthesisFirst(operationBuffer) WIP
            while (operationBuffer.size > 3) { // minimum computable chunk: X op X op2
                val firstNum = (operationBuffer[chunkPointer] as Number)
                Log.d("CUSTOM", "NUM1: ${firstNum.value}")
                val firstOp = (operationBuffer[chunkPointer + 1] as Calculation)
                Log.d("CUSTOM", "OP1: ${firstOp.javaClass.simpleName} & PRIORITY: ${firstOp.priority}")
                val secondNum = (operationBuffer[chunkPointer + 2] as Number)
                Log.d("CUSTOM", "NUM2: ${secondNum.value}")
                val secondOp = (operationBuffer[chunkPointer + 3] as OrderedToken)
                Log.d("CUSTOM", "OP2: ${secondOp.javaClass.simpleName} & PRIORITY: ${secondOp.priority}")


                if (firstOp.priority.value < secondOp.priority.value) { // if second op. is higher order...
                    // ie: >2 + 3 x<
                    if (moveAhead(operationBuffer)) { // see what's ahead (to group higher order op. with next number and compute that first)
                        Log.d("CUSTOM", "CONTINUE 1: SEE WHAT'S AHEAD") // ie >2 + 3 x< ...?? maybe "x 4 -" *
                        continue
                    } else { // nothing ahead; keep showing last number and keep waiting for more numbers; there isn't a result or update to provide
                        Log.d("CUSTOM", "RETURN 0: NOTHING TO DO YET. WAITING for next number+op.")
                        return Number(secondNum.value) // ** (see CASE3 below)
                    }
                } else if (firstOp.priority.value >= secondOp.priority.value) { // if second op. is same or lower order, compute this chunk
                    // here first or * after moving ahead: 2 + >3 x 4 -<
                    // computes operation >3x4 -< and updates the buffer >12 - _<
                    val newFirstNum: Number = Number(firstOp.calculate(firstNum.value, secondNum.value))
                    operationBuffer.set(operationBuffer.indexOf(firstNum), newFirstNum)
                    operationBuffer.remove(firstOp)
                    operationBuffer.remove(secondNum)
                    // NOW 3 THINGS CAN HAPPEN
                    if (chunkPointer == 0) { //
                        if (secondOp is Equal) {
                            // CASE 1: if NOT looking ahead AND secondOp is Equal... THIS IS END OF THE BUFFER
                            operationBuffer.clear() // clears it and then returns the final result after the if
                            val result = Result(newFirstNum.value)
                            history.add(result)
                            Log.d("CUSTOM", "RETURN 1: Computed LAST chunk (EQUAL) and cleared the buffer. FINAL RESULT.")
                            return result
                        } else { //
                            // CASE 2: if NOT looking and secondOp is not Equal -> uncompleted block >2 - _<
                            Log.d("CUSTOM", "RETURN 2: Computed base chunk (op.A>op.B). NEED more number+op. to compute more.")
                            // returns the update for the display and keeps waiting for next number to compute the operation (in the example '-')
                            return Number(newFirstNum.value)
                        }
                     } else if (moveBack()) {
                        // CASE 3: we're looking ahead & moveBack gets executed
                        // we'd be back to previous chunk >firstNum(prev.) firstOp(prev.) secondNum(this.firstNum) secondOp(this.secondOp)<;
                         // ** notice that the 'update' to the computed operation would get returned in there
                        Log.d("CUSTOM", "CONTINUE 2: Computed chunk ahead (op.A>op.B) and moved back.")
                        continue
                    }
                }
            }

            // prevents a final operation with no effect (ie >2 = _<) from filling the buffer (logic breaking)
            if (operationBuffer[1] is Equal && operationBuffer[0] is Number) {
                Log.d("CUSTOM", "RETURN 3: Computed meaningless op. + cleared the buffer ")
                val meaninglessResult = (operationBuffer[0] as Result)
                val lastIndex = history.lastIndex
                history.removeAt(lastIndex)
                history.removeAt(lastIndex - 1)
                operationBuffer.clear()
                return meaninglessResult
            }

            Log.d("CUSTOM", "RETURN NULL: Not enough op. in the buffer to compute anything... ")
            return null
        }

        fun moveAhead(operationBuffer: MutableList<OperationToken>): Boolean {
            return if (operationBuffer.size > 4) {
                movedBackHappened = true
                chunkPointer += 2
                Log.d("CUSTOM", "MOVED AHEAD!")
                true
            } else {
                false
            }
        }
        private var movedBackHappened = false
        fun moveBack(): Boolean {
            return if (chunkPointer == 2) {
                movedBackHappened = true
                chunkPointer -= 2
                Log.d("CUSTOM", "MOVED BACK!")
                true
            } else {
                false
            }
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
                history.forEach {
                    if (it is OrderedToken) {
                        parsedHistory.append(it.symbol)
                    } else if (it is Number) {
                        parsedHistory.append(it.parseToString())
                        if (it is Result) {
                            parsedHistory.append("; ")
                        }
                    }
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
