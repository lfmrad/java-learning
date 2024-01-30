package com.example.calculadora

import android.media.audiofx.DynamicsProcessing.Eq
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.calculadora.Calculator.Companion.getHistory
import com.example.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListenersToButtons(binding.mainButtonGrid)
        setListenersToButtons(binding.testButtonGrid)
        binding.secondaryDisplay.movementMethod = ScrollingMovementMethod()
    }

    /*  Instead of mapping the class for each listener doing:
        binding.button1.setOnClickListener(this);
        we can automate the process for any container using a loop:
    */
    private fun setListenersToButtons(container: ViewGroup) {
        for (i in 0 until container.childCount) {
            val child = container.getChildAt(i)
            if (child is Button) {
                child.setOnClickListener(this)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            // NUMERIC PAD
            binding.button0.id -> { inputDigit('0') }
            binding.button1.id -> { inputDigit('1') }
            binding.button2.id -> { inputDigit('2') }
            binding.button3.id -> { inputDigit('3') }
            binding.button4.id -> { inputDigit('4') }
            binding.button5.id -> { inputDigit('5') }
            binding.button6.id -> { inputDigit('6') }
            binding.button7.id -> { inputDigit('7') }
            binding.button8.id -> { inputDigit('8') }
            binding.button9.id -> { inputDigit('9') }
            // OPERATIONS
            binding.buttonSum.id -> { loadOperation(Sum()) }
            binding.buttonSubtraction.id -> { loadOperation(Subtraction()) }
            binding.buttonProduct.id -> { loadOperation(Product()) }
            binding.buttonDivision.id -> { loadOperation(Division()) }
            binding.buttonEqual.id -> { loadOperation(Equal()) }
            binding.buttonClear.id -> { clear() }
            // OTHERS
            binding.buttonPercentage.id -> {}
            binding.buttonDecimal.id -> { inputDigit(Number.decimalSeparator) }
            binding.buttonPlusMinus.id -> {}
            // TEST
            binding.buttonTest1.id -> {
                numberCreationInProcess = false
                decimalSeparatorAdded = false
                digits.clear()
                clearMainDisplay()
                clearSecondaryDisplay()
                Calculator.history.clear()
            }
            binding.buttonTest2.id -> {
                loadOperation(Sin())
            }
            binding.buttonTest3.id -> {
                loadOperation(Exponentiation())
            }
            binding.buttonTest4.id -> {
                loadOperation(PowerOf(2.toDouble()))
            }
        }
    }

    private val digits: StringBuilder = StringBuilder()
    private var numberCreationInProcess = false
    private var decimalSeparatorAdded = false

    private fun inputDigit(digit: Char) {
        // makes sure that the decimal operator can be added only once
        var nextDigit: String? = null
        if (digit != Number.decimalSeparator) {
            numberCreationInProcess = true
            nextDigit = digit.toString()
        } else if (digit == Number.decimalSeparator && !decimalSeparatorAdded) {
            // allows to add a decimal separator to a previous result (if there is one)
            // TODO works well but might benefit from some refactoring for clarity
            if (!numberCreationInProcess) {
                val numberOnTheScreen = parseScreen()
                // if the number was already decimal; prevents adding another decimal separator
                if (!Number.isDecimal(Number.parseStringToNum(numberOnTheScreen))) {
                    digits.append(numberOnTheScreen)
                    nextDigit = digit.toString()
                    numberCreationInProcess = true
                    decimalSeparatorAdded = true
                }
            } else { // generic case: when a new number is being created and the decimal hasn't not been added yet
                nextDigit = digit.toString()
                decimalSeparatorAdded = true
            }
        }
        nextDigit?.let {
            digits.append(it)
            val processedInput = Number.prettifyInput(digits.toString())
            refreshDisplay(processedInput)
        }
    }

    private fun loadOperation(setOperation: OperationToken) {
        decimalSeparatorAdded = false
        Log.d("CUSTOM", "IN loadOperation()")
        val frozenNumber = Number(flushScreen())
        val lastTokenInHistory = Calculator.history.lastOrNull()
        if (numberCreationInProcess || Calculator.history.isEmpty()) {
            Log.d("CUSTOM", "loadOp() CASE 1")
            // + OR _ & user has entered a number
            Calculator.history.add(Number(frozenNumber.value))
            Calculator.history.add(setOperation)
            numberCreationInProcess = false
        } else if (lastTokenInHistory is Calculation) {
            // CHANGE OF MIND
            Log.d("CUSTOM", "loadOp() CASE; CHANGE OF MIND")
            Calculator.history.set(Calculator.history.indexOf(lastTokenInHistory), setOperation)
        } else if (lastTokenInHistory is Result && !numberCreationInProcess) {
            Log.d("CUSTOM", "loadOp() CASE 2")
            // =2 && user hasn't overwritten the output
            Calculator.history.add(Number(lastTokenInHistory.value))
            Calculator.history.add(setOperation)
        } else if (lastTokenInHistory is Number || lastTokenInHistory is SingleOperandCalculation) {
            Log.d("CUSTOM", "loadOp() CASE 3")
            Calculator.history.add(setOperation)
        }

        val resultProvided = Calculator.computeBuffer(Calculator.getBufferFromLastResult())
        resultProvided?.let {
            refreshDisplay(Number.parseNumToString(resultProvided.value))
        }

        val historyProvided = Calculator.getHistory()
        historyProvided?.let {
            refreshSecondaryDisplay(it)
        }

        Log.d("CUSTOM", "OUT OF... loadOperation()")
    }

    private fun parseScreen(): String {
        return binding.mainDisplay.text.toString()
    }

    private fun flushScreen(): String {
        digits.clear()
        return binding.mainDisplay.text.toString()
    }


    private fun refreshDisplay(input: String) {
        binding.mainDisplay.text = input
    }
    private fun refreshSecondaryDisplay(input: String) {
        binding.secondaryDisplay.text = input
    }

    private fun clearMainDisplay() {
        binding.mainDisplay.text = 0.toString()
    }

    private fun clearSecondaryDisplay() {
        binding.secondaryDisplay.text = 0.toString()
    }

    var firstPassFlushCurrent = false
    private fun clear() {
        digits.clear()
        clearMainDisplay()
        firstPassFlushCurrent = if (!firstPassFlushCurrent) {
            true
        } else {
            // TODO must clear from history as well or it will mess it up
            Calculator.history.clear()
            false
        }
    }
}