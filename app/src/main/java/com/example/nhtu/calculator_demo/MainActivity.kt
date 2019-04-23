package com.example.nhtu.calculator_demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    lateinit var textInput:TextView
    lateinit var textOutput:TextView

    var isErrorState:Boolean=false
    var isEmptyInput:Boolean=true
    var lastIsDot:Boolean=false
    var lastIsNumber:Boolean=true
    var lastIsOperator:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapping()
    }

    //Number button click
    fun onDigitClick(view:View){
        if(isErrorState){
            textInput.text=(view as Button).text
            isErrorState=false;
        }
        else{
            textInput.append((view as Button).text)

        }
        lastIsNumber=true;
        isEmptyInput=false;
        lastIsDot=false;
        lastIsOperator=false;

        if (lastIsNumber&&!isErrorState){
            val equation=textInput.text.toString()
            val expression = ExpressionBuilder(equation).build()
            try {
                // Calculate the result and display
                val result = expression.evaluate()
                if (result.compareTo(result.toInt())!=0){
                    textOutput.text = result.toString()
                }
                else{
                    textOutput.text=result.toInt().toString()
                }
            } catch (ex: ArithmeticException) {
                // Display an error message
                textOutput.text = "Error"
                isErrorState = true
                lastIsNumber = false
            }
        }
    }

    fun onOperatorClick(view:View){
        if (!isEmptyInput&&lastIsNumber&&!isErrorState){
            textInput.append((view as Button).text)
            lastIsNumber=false;
            lastIsOperator=true;
            lastIsDot=false;
            isEmptyInput=false;
        }
    }

    fun onEqualClick(view:View){
        if (lastIsNumber&&!isErrorState){
            val equation=textInput.text.toString()
            val expression = ExpressionBuilder(equation).build()
            try {
                // Calculate the result and display
                val result = expression.evaluate()
                if (result.compareTo(result.toInt())!=0){
                    textOutput.text = result.toString()
                }
                else{
                    textOutput.text=result.toInt().toString()
                }

            } catch (ex: ArithmeticException) {
                // Display an error message
                textOutput.text = "Error"
                isErrorState = true
                lastIsNumber = false
            }
        }
    }

    fun onDelClick(view:View){
        textInput.text=""
        lastIsOperator=false;
        lastIsDot=false
        lastIsNumber=false
        isEmptyInput=true
        isErrorState=false
        textOutput.text=""
    }

    fun onDotClick(view:View){
        if(lastIsNumber&&!isErrorState&&!lastIsDot){
            textInput.append((view as Button).text)
            lastIsNumber=false;
            lastIsDot=true;
            lastIsOperator=false;
        }
    }

    fun mapping(){
        textInput=findViewById(R.id.editText_equation)
        textOutput=findViewById(R.id.editText_result)
    }
}
