package com.example.coursework

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class NewGame : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        var firstarithmatic = 0;
        var firstarithmatic_label = "";
        var secondone = 0;
        var secondone_output = "";
        val operatorrs = arrayOf("+","/","-","*")

        var numberofchar =   (2..3).random()//Math.random() * (4-1) + 1; // Math.random() //(1..3).random()
        numberofchar = 3;
        if(numberofchar.toInt() ==2){
            val operator_= (0..3).random()
            val operatorType = operatorrs[operator_]
            val firstValue = (1..20).random()
            val secondValue = (1..20).random()


            var returnout  = computeoutcome(operatorType,firstValue,secondValue)
            val splitdata = returnout.split("|").toTypedArray()

            firstarithmatic = splitdata.get(0).toInt()
            firstarithmatic_label =splitdata.get(1)

//            if (operatorType == "+"){
//                firstarithmatic = firstValue + secondValue
//                firstarithmatic_label = firstValue.toString() + "+" + secondValue.toString()
//            }else if (operatorType == "/"){
//                firstarithmatic = firstValue / secondValue
//                firstarithmatic_label = firstValue.toString() + "/" + secondValue.toString()
//            }else if (operatorType == "-"){
//                firstarithmatic = firstValue - secondValue
//                firstarithmatic_label = firstValue.toString() + "-" + secondValue.toString()
//            }else if (operatorType == "*"){
//                firstarithmatic = firstValue * secondValue
//                firstarithmatic_label = firstValue.toString() + "*" + secondValue.toString()
//            }
        }else if(numberofchar.toInt() == 3){
            var firstValueOperator = ""
            var secondValueOperator = ""
            var firstValue = 0
            var secondValue = 0
            var thirdValue = 0

            for (i in 1..3){
                val operator_= (0..3).random()
                val operatorType = operatorrs[operator_]
                if (i==1){
                    firstValue = (1..20).random()
                }else {
                    if (i==2){
                        secondValue = (1..20).random()

                        var returnout  = computeoutcome(operatorType,firstValue,secondValue)
                        val splitdata = returnout.split("|").toTypedArray()

                        firstarithmatic = splitdata.get(0).toInt()
                        firstarithmatic_label = "("+splitdata.get(1)+")"

                    }else{
                        thirdValue = (1..20).random()

                        var returnout  = computeoutcome(operatorType,firstarithmatic,thirdValue)
                        val splitdata = returnout.split("|").toTypedArray()

                        firstarithmatic = splitdata.get(0).toInt()
                        firstarithmatic_label = firstarithmatic_label +  " "+operatorType+" "+thirdValue


                    }


                }

            }

        }else {

        }
        println("This is the first value."+firstarithmatic)
        println("This is the first value."+firstarithmatic_label)

        var numberofchar2 =   (1..3).random()//Math.random() * (4-1) + 1; // Math.random() //(1..3).random()
        numberofchar2 = 2;

        if(numberofchar2.toInt() ==2){
            val operator_= (0..3).random()
            val operatorType = operatorrs[operator_]
            val firstValue = (1..20).random()
            val secondValue = (1..20).random()
            var returnout  = computeoutcome(operatorType,firstValue,secondValue)
            val splitdata = returnout.split("|").toTypedArray()

            secondone = splitdata.get(0).toInt()
            secondone_output =splitdata.get(1)

        }else if(numberofchar2.toInt() == 3){

            var firstValueOperator = ""
            var secondValueOperator = ""
            var firstValue = 0
            var secondValue = 0
            var thirdValue = 0

            for (i in 1..3){
                val operator_= (0..3).random()
                val operatorType = operatorrs[operator_]
                if (i==1){
                    firstValue = (1..20).random()
                }else {
                    if (i==2){
                        secondValue = (1..20).random()

                        var returnout  = computeoutcome(operatorType,firstValue,secondValue)
                        val splitdata = returnout.split("|").toTypedArray()

                        secondone = splitdata.get(0).toInt()
                        secondone_output = "("+splitdata.get(1)+")"

                    }else{
                        thirdValue = (1..20).random()

                        var returnout  = computeoutcome(operatorType,firstarithmatic,thirdValue)
                        val splitdata = returnout.split("|").toTypedArray()

                        secondone = splitdata.get(0).toInt()
                        secondone_output = firstarithmatic_label +  " "+operatorType+" "+thirdValue


                    }


                }

            }


        }else {

        }

        println("This is the second value."+secondone)
        println("This is the second value."+secondone_output)
        val idfirstArithmetic: TextView = findViewById(R.id.idfirstArihmetic)
        val idsecondArithmetic: TextView = findViewById(R.id.idsecondArithmetic)
        val idAnswer: TextView = findViewById(R.id.idAnswer)
        idfirstArithmetic.setText(firstarithmatic_label)
        idsecondArithmetic.setText(secondone_output)

        val btnGreater: Button = findViewById(R.id.btnGreater)
        val btnEqual: Button = findViewById(R.id.btnEqual)
        val btnLess: Button = findViewById(R.id.btnLess)
        btnGreater.setOnClickListener {
//            val alertbuild : AlertDialog.Builder? = activity? .let


            if (firstarithmatic>secondone){
//                AlertDialog alt =
                idAnswer.setText("Correct")
            }else{
                idAnswer.setText("Wrong")
            }
        }

        btnEqual.setOnClickListener {
//            val alertbuild : AlertDialog.Builder? = activity? .let


            if (firstarithmatic==secondone){
//                AlertDialog alt =
                idAnswer.setText("Correct")
            }else{
                idAnswer.setText("Wrong")
            }
        }

        btnLess.setOnClickListener {
//            val alertbuild : AlertDialog.Builder? = activity? .let


            if (firstarithmatic<secondone){
//                AlertDialog alt =
                idAnswer.setText("Correct")
            }else{
                idAnswer.setText("Wrong")
            }
        }

    }

    fun computeoutcome(operatorType: String, firstValue : Int, secondValue : Int) : String
    {

        var computation=0;
        var computation_label="";

        if (operatorType == "+"){
            computation = firstValue + secondValue
            computation_label = firstValue.toString() + "+" + secondValue.toString()
        }else if (operatorType == "/"){
            computation = firstValue / secondValue
            computation_label = firstValue.toString() + "/" + secondValue.toString()
        }else if (operatorType == "-"){
            computation = firstValue - secondValue
            computation_label = firstValue.toString() + "-" + secondValue.toString()
        }else if (operatorType == "*"){
            computation = firstValue * secondValue
            computation_label = firstValue.toString() + "*" + secondValue.toString()
        }


        return computation.toString() +"|"+computation_label;
    }

}