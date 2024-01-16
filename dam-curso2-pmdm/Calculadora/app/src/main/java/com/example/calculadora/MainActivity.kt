package com.example.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListenersToButtons(binding.mainButtonGrid)
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
            binding.button0.id -> { inputNumber(0) }
            binding.button1.id -> { inputNumber(1) }
            binding.button2.id -> { inputNumber(2) }
            binding.button3.id -> { inputNumber(3) }
            binding.button4.id -> { inputNumber(4) }
            binding.button5.id -> { inputNumber(5) }
            binding.button6.id -> { inputNumber(6) }
            binding.button7.id -> { inputNumber(7) }
            binding.button8.id -> { inputNumber(8) }
            binding.button9.id -> { inputNumber(9) }
            // OPERATIONS
            binding.buttonSum.id -> { operationBuffer.add(Sum()) }
            binding.buttonSubtraction.id -> { operationBuffer.add(Sum()) }
            binding.buttonProduct.id -> {}
            binding.buttonDivision.id -> {}
            binding.buttonEqual.id -> {}
            binding.buttonClear.id -> { clearDisplay() }
            // OTHERS
            binding.buttonPercentage.id -> {}
            binding.buttonDecimal.id -> {}
            binding.buttonPlusMinus.id -> {}
        }
    }

    private val digits: StringBuilder = StringBuilder()
    private val operationBuffer: MutableList<OperationToken> = mutableListOf()
    private var operationPending = false

    private fun inputNumber(number: Int) {
        if (operationPending) {
            refreshDisplay(Calculator.computeBufferToString(operationBuffer))
            operationPending = false
        }
        digits.append(number.toString())
        refreshDisplay(digits.toString())
    }

    private fun loadOperation(setOperation: OperationToken) {
        val frozenNumber = Number(binding.mainDisplay.toString())
        operationBuffer.add(frozenNumber)
        operationBuffer.add(setOperation)
        operationPending = true
        Calculator.history.add(frozenNumber)
        Calculator.history.add(setOperation)
    }

    private fun refreshDisplay(input: String) {
        binding.mainDisplay.text = input
    }

    private fun clearDisplay() {
        binding.mainDisplay.text = 0.toString()
    }
}