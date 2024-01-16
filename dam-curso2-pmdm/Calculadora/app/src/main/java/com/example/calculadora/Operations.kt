package com.example.calculadora

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
            return String.format("%f", num)
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
        PARENTHESES(1),
        EXPONENTIATION(2),
        PROD_AND_DIV(3),
        ADD_AND_SUB(4)
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

class Calculator {
    companion object {
        val history: MutableList<OperationToken> = mutableListOf()

        fun computeBuffer(operationBuffer: MutableList<OperationToken>): Double {
            // SUPER WIP
            var result = 0.0
            for ((n, operation) in operationBuffer.withIndex()) {
                if (operation is Number) {
                    result =+ operation.num
                } else if (operation is Calculation) {
                    val op1 = result
                    if (operationBuffer[n+1] != null) {
                        val op2 = (operationBuffer[n+1] as Number).num
                        result = operation.calculate(op1, op2)
                    }
                }
            }
        }

        fun computeBufferToString(operationBuffer: MutableList<OperationToken>): String {
           return Number.parseNumberToString(computeBuffer(operationBuffer))
        }
    }
}