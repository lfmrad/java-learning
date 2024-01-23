package com.example.calculadora

import android.util.Log
import kotlin.math.absoluteValue
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
    var tag = 0
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
        val operationBuffer: MutableList<OperationToken> = mutableListOf()
        fun computeBuffer(operationBuffer: MutableList<OperationToken>): Number? {
            Log.d("CUSTOM", ">> ENTERED computeBuffer()")
            Log.d("CUSTOM", "[operationBuffer.size: ${operationBuffer.size}\"]")
            operationBuffer.forEach {
                Log.d("CUSTOM", "<insideOperationBuffer: ${it.javaClass.simpleName} >")
            }
            var token1: OperationToken? = null
            var token2: OperationToken? = null
            var token3: OperationToken? = null
            var token4: OperationToken? = null
            while (operationBuffer.size > 2) {
                for (i in operationBuffer.indices) {
                    token1 = operationBuffer.getOrNull(i)
                    token2 = operationBuffer.getOrNull(i + 1)
                    token3 = operationBuffer.getOrNull(i + 2)
                    token4 = operationBuffer.getOrNull(i + 3)
                    if (token1 != null) {
                        Log.d("CUSTOM", "<token1: ${token1.javaClass.simpleName} >")
                    }
                    if (token2 != null) {
                        Log.d("CUSTOM", "<token2: ${token2.javaClass.simpleName} >")
                    }
                    if (token3 != null) {
                        Log.d("CUSTOM", "<token3: ${token3.javaClass.simpleName} >")
                    }
                    if (token4 != null) {
                        Log.d("CUSTOM", "<token4: ${token4.javaClass.simpleName} >")
                    }

                    // applies SingleOperandCalculations first
                    if (token1 is Number && token2 is SingleOperandCalculation) {
                        val precedingNumber = token1 as Number
                        val singleOpCalc = token2 as SingleOperandCalculation
                        val result = singleOpCalc.calculate(precedingNumber.value)
                        Log.d("CUSTOM", "CONTINUE; CALCULATION: SingleOperandCalculation <${precedingNumber.value}${singleOpCalc.symbol}=$result>")
                        precedingNumber.value = result
                        operationBuffer.remove(token2)
                        Log.d("CUSTOM", "[operationBuffer.size: ${operationBuffer.size}\"]")
                        continue
                    } else if (token1 is OrderedToken && token2 is OrderedToken) {
                        // only last operation prevails (ie change of mind): (+,=) -> (=), (+,sin) -> sin, etc
                        val prevOp = token1 as OrderedToken
                        val nextOp = token2 as OrderedToken
                        Log.d("CUSTOM", "CONTINUE; Change of operation: ${prevOp.symbol},${nextOp.symbol} -> ${nextOp.symbol} ")
                        operationBuffer.remove(prevOp)
                        Log.d("CUSTOM", "[operationBuffer.size: ${operationBuffer.size}\"]")
                        continue
                    } else if (token1 is Number && token2 is Calculation && token3 is Number) {
                        // process calculations according to priority
                        // any of: 2+3x, 2x3+, 3x2(=/empty) (before... 2+3x
                        val firstOperand = token1 as Number
                        val firstCalc = token2 as Calculation
                        val secondOperand = token3 as Number
                        if (token4 is Calculation) {
                            var secondCalc = token4 as Calculation
                            if (firstCalc.priority.value < secondCalc.priority.value) { // 2+3x -> 3x...?
                                Log.d("CUSTOM", "CONTINUE; (LOOK AHEAD); DID NOTHING; CASE: number1,op1,number1,op2 where op1<op2")
                                Log.d("CUSTOM", "[operationBuffer.size: ${operationBuffer.size}\"]")
                                continue
                            } else if (firstCalc.priority.value >= secondCalc.priority.value) { // 2x3x -> 6x
                                var result = firstCalc.calculate(firstOperand.value, secondOperand.value)
                                Log.d("CUSTOM", "CONTINUE; CALCULATION: <${firstOperand.value}${firstCalc.symbol}${secondOperand.value}${secondCalc.symbol}\$=$result${secondCalc.symbol}\$>")
                                firstOperand.value = result
                                operationBuffer.remove(token2)
                                operationBuffer.remove(token3)
                                Log.d("CUSTOM", "CONTINUE; CALCULATION: number1,op1,number1,op2 where op1>=op2")
                                Log.d("CUSTOM", "[operationBuffer.size: ${operationBuffer.size}\"]")
                                continue
                            }
                        } else {
                            val result = firstCalc.calculate(firstOperand.value, secondOperand.value) // 2x3 -> 6=
                            Log.d("CUSTOM", "CONTINUE; CALCULATION: <${firstOperand.value}${firstCalc.symbol}${secondOperand.value}=$result>")
                            firstOperand.value = result
                            firstOperand.tag = 1
                            operationBuffer.remove(token2)
                            operationBuffer.remove(token3)
                            Log.d("CUSTOM", "[operationBuffer.size: ${operationBuffer.size}\"]")
                            continue
                        }
                    }
                }
            }

            if (token1 is Number && token2 is Calculation) { // FROM HERE: ONLY HAPPENS WHEN operationBuffer.size == 2
                // 2+ -> missing more op.
                Log.d("CUSTOM", "<< EXIT computerBuffer: RETURN NULL <number+op>")
                Log.d("CUSTOM", "[operationBuffer.size: ${operationBuffer.size}\"]")
                operationBuffer.clear()
                return null // requires more numbers
            } else if (token1 is Number && token2 is Equal) { // FINAL RESULT, BREAKS LOOP
                val finalResult = token1 as Number
                Log.d("CUSTOM", "<< EXIT computerBuffer: RETURN VALUE **finalResult** <${finalResult.value}${token2.symbol}${finalResult.value}>")
                Log.d("CUSTOM", "CHECK: tag: ${finalResult.tag}")
                Log.d("CUSTOM", "[operationBuffer.size: ${operationBuffer.size}\"]")
                operationBuffer.clear()
                return Result(finalResult.value)
            } else if (token1 is Number && token2 == null) { // FROM HERE: ONLY HAPPENS WHEN operationBuffer.size == 1
                Log.d("CUSTOM", "<< EXIT computerBuffer: RETURN NULL <number ...?>")
                Log.d("CUSTOM", "[operationBuffer.size: ${operationBuffer.size}\"]")
                operationBuffer.clear()
                return token1 // requires more op.
            } else if (token1 is OrderedToken && token2 == null) {
                Log.d("CUSTOM", "<< EXIT computerBuffer: RETURN NULL <op ...?>")
                Log.d("CUSTOM", "[operationBuffer.size: ${operationBuffer.size}\"]")
                operationBuffer.clear()
                return null // requires more numbers
            }
            Log.d("CUSTOM", "<< EXIT computerBuffer: RETURN MAIN NULL")
            Log.d("CUSTOM", "[operationBuffer.size: ${operationBuffer.size}\"]")
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

        fun getBuffer(): MutableList<OperationToken> {
            val operationBuffer: MutableList<OperationToken> = mutableListOf()
            history.forEach {
                if (it is Number) {
                    operationBuffer.add(Number(it.value))
                } else {
                    operationBuffer.add(it)
                }
            }
            return operationBuffer
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
