package com.example.coursework

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class
NewGame : AppCompatActivity() {


    private var firstArithmetic = 0
    private var firstArithmetic_label = ""
    private var secondArithmetic = 0
    private var secondArithmetic_label = ""
    private var timerValue=50
    private var validAnswer=0
    private var invalidAnswer=0
    private var increasedTimer=0 // if time given needed to be increased based on if the user answers 5 questions correctly.

    //User will click on the ok button to go back to the main screen.
    val goHomeButtonClick = { dialog: DialogInterface, which: Int ->
        dialog.dismiss()
    }

    //When time runs out the user is shown their correct and wrong answers.
    fun finalMessageAlert() {

        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            val dialogView : View = LayoutInflater.from(this@NewGame).inflate(R.layout.resultdialog, null)
            val answerCorrect : TextView = dialogView.findViewById<TextView>(R.id.idCorrect)
            val answerWrong : TextView = dialogView.findViewById<TextView>(R.id.idWrong)
            answerCorrect.text = "Correct Answers: $validAnswer"
            answerWrong.text = "Wrong Answers: $invalidAnswer"
            setView(dialogView)

            setNegativeButton( "Ok",goHomeButtonClick)
            show()
        }
    }

    var maxTimer = 51000 - ( (50 - timerValue) * 1000)
    var minTimer = 1000

    //Setting the timer value.
    private var countTimer: CountDownTimer? = null
    private var answerLabel: TextView? = null
    private var imgViewSign: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        answerLabel = findViewById(R.id.idAnswer)
        imgViewSign = findViewById(R.id.imageView)

        reCreateArithmetic()


//        println(" (NewGame.timerValue * 1000) "+ (51000 - (timerValue * 1000)))

        /**
         *  We are setting the timer value
         */
        countTimer = counterRecord(maxTimer.toLong(),minTimer.toLong())
        //Starting the timer.
        countTimer!!.start()
        var isCorrect = false

        //We are defining a button to a variable to setup action listener.
        val btnGreater: Button = findViewById(R.id.btnGreater)
        val btnEqual: Button = findViewById(R.id.btnEqual)
        val btnLess: Button = findViewById(R.id.btnLess)
        btnGreater.setOnClickListener {
            //calling refresher method based on the click function
            refreshActivity(true,false,false)
            //We are refreshing the new arithmetic logic
        }
        btnEqual.setOnClickListener {
            //calling refresher method based on the click function
            refreshActivity(false,true,false)
        }
        btnLess.setOnClickListener {
            //Comparing whether firstArithmetic is less than secondArithmetic.
            refreshActivity(false,false,true)
        }
    }

    override fun onBackPressed() {
        countTimer!!.cancel()
        timerValue = 50
        validAnswer = 0
        invalidAnswer = 0
        intent = Intent(
            this,
            MainActivity::class.java
        )
        startActivity(intent)

        countTimer = counterRecord(51000,minTimer.toLong())
    }

    /*
    Detecting phone rotation.
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        var textTimer: TextView = findViewById(R.id.idTimer)
        val paramTimer = textTimer.layoutParams as ViewGroup.MarginLayoutParams
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            paramTimer.setMargins(paramTimer.leftMargin,65,paramTimer.rightMargin,paramTimer.bottomMargin)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            paramTimer.setMargins(paramTimer.leftMargin,200,paramTimer.rightMargin,paramTimer.bottomMargin)
        }
    }


    fun reCreateArithmetic()
    {
        //Getting random character for left hand arithmetic calculation
        var leftArithmeticChars = (2..4).random()

        //We are calling methodExpression to generate the arithmetic logic.
        val returnOut = methodExpression(leftArithmeticChars)

        //We are splitting the data returning by the methodExpression.
        val splitData = returnOut.split("|").toTypedArray()

        //println("splitData[0] "+splitData[0].toDouble().toInt())

        //We are assigning the splitData Array values to the label values
        firstArithmetic = splitData[0].toDouble().toInt()
        firstArithmetic_label = splitData[1]

        //Getting random character for right hand arithmetic calculation
        var rightArithmeticChars = (2..4).random()

        //We are calling methodExpression to generate the arithmetic logic.
        val returnOut_2 = methodExpression(rightArithmeticChars)

        //We are splitting the data returning by the methodExpression.
        val splitData_2 = returnOut_2.split("|").toTypedArray()

        //We are assigning the splitData Array values to the label values
        secondArithmetic = splitData_2[0].toDouble().toInt()
        secondArithmetic_label = splitData_2[1]

        //We are calling the label id to show the arithmetic operator.
        val idFirstArithmetic: TextView = findViewById(R.id.idfirstArihmetic)
        val idSecondArithmetic: TextView = findViewById(R.id.idsecondArithmetic)

        idFirstArithmetic.text = firstArithmetic_label
        idSecondArithmetic.text = secondArithmetic_label
    }


    fun refreshActivity(isGreaterClick : Boolean, isEqualClick : Boolean, isLessClick : Boolean)
    {
        var isCorrect = false

        isCorrect = when {
            isGreaterClick -> {
                firstArithmetic > secondArithmetic
            }
            isEqualClick -> {
                firstArithmetic == secondArithmetic
            }
            else -> {
                firstArithmetic < secondArithmetic
            }
        }
        msgDisplayCount(isCorrect)
        /**
         * checking whether we have to re-initiate the timer object with additional time
         */
//        println(" increasedTimer ??$increasedTimer")
        if(increasedTimer>0) {
            countTimer!!.cancel()
            countTimer = null


            //Starting timer based o if user answered 5 questions correctly.
            maxTimer = 51000 - ((50 - timerValue) * 1000) + (increasedTimer * 1000)
            //println(" maxTimer :: $maxTimer")
            timerValue += 10
            //Re-initiation of the timer
            countTimer = counterRecord(maxTimer.toLong(), minTimer.toLong())
            countTimer!!.start()
            increasedTimer = 0
        }
        Handler(Looper.getMainLooper()).postDelayed(
            {
                // Re-initiation of the new arithmetic operation.
                reCreateArithmetic()
                answerLabel!!.text = ""
                imgViewSign!!.visibility = View.GONE
            },
            500 // value in milliseconds
        )
    }



    /**
    This method refreshes a new arithmetic expression to the user.
     */
    fun msgDisplayCount(answerCorrect : Boolean)
    {
        imgViewSign!!.visibility = View.VISIBLE
        //If the answer is correct we will display "CORRECT!".
        if(answerCorrect)
        {
            imgViewSign!!.setImageResource(R.mipmap.right_icon)
            answerLabel!!.setTextColor(Color.GREEN)
            answerLabel!!.text = "CORRECT!"

            //Increasing the valid answer counter value by 1
            validAnswer++
            //If the user successfully answers 5 questions correctly.
            if(validAnswer % 5 == 0) {
                //The time allotted to the user is increased by 10 seconds.
                increasedTimer = 10
            }
        }else{

            //Increasing the invalid answer counter value by 1
            imgViewSign!!.setImageResource(R.mipmap.wrong_icon)
            invalidAnswer++
            answerLabel!!.text = "WRONG!"
            answerLabel!!.setTextColor(Color.RED)
        }
    }

    /**
    This is the timer function.
     */
    fun counterRecord(startTimer : Long, endTimer : Long) : CountDownTimer
    {
        val countTime: TextView = findViewById(R.id.idTimer)
        val countTimer = object : CountDownTimer(startTimer.toLong(), endTimer.toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                countTime.text = "Remaining time "+(millisUntilFinished / 1000).toString()+"s"
                timerValue --

                if(timerValue<0)
                {
                    this.onFinish()
                }
            }

            override fun onFinish() {
                cancel()
                countTime.text = "Finished"
                finalMessageAlert()
                timerValue = 50
                //When the time is finished the buttons will be disabled.
                val btnGreater: Button = findViewById(R.id.btnGreater)
                val btnEqual: Button = findViewById(R.id.btnEqual)
                val btnLess: Button = findViewById(R.id.btnLess)
                btnGreater.isEnabled = false
                btnEqual.isEnabled = false
                btnLess.isEnabled = false
            }
        }

        return countTimer
    }

    /**
     * Generate the method expression.
     */
    fun methodExpression(numberOfChar: Int): String {
        val operators = arrayOf("+", "/", "-", "*")
        var arithmeticExpression = 0.0
        var arithmeticExpression_label = ""

        //Number of characters versus number of arithmetic operators.
        if (numberOfChar.toInt() == 2) {
            //Randomly selecting the operator type.
            val operatorIndex = (0..3).random()
            val operatorType = operators[operatorIndex]
            //Randomly selecting first value between 1 to 20.
            val firstValue = (1..20).random()
            //Randomly selecting second value between 1 to 20.
            val secondValue = (1..20).random()

            //Calling the computeOutcome method to generate the arithmetic expression.
            var returnOut = computeOutcome(operatorType, firstValue.toDouble(), secondValue.toDouble())
            val splitData = returnOut.split("|").toTypedArray()
            //Assigning the values from the computeOutcome method.
            arithmeticExpression = splitData.get(0).toDouble()
            arithmeticExpression_label = splitData.get(1)

        } else if (numberOfChar.toInt() == 3) {

            var firstValue = (1..20).random() // selecting the first value
            var secondValue = (1..20).random() // selecting the second value
            var thirdValue = (1..20).random() // selecting the third value

            /**
             * selecting the operator type
             */
            val operatorType = operators[(0..3).random()] //First operator type
            val operatorType_2 = operators[(0..3).random()] //Second operator type

            // get first the computed value
            var returnOut = computeOutcome(operatorType, firstValue.toDouble(), secondValue.toDouble())
            var splitData = returnOut.split("|").toTypedArray()


            arithmeticExpression = splitData.get(0).toDouble()
            arithmeticExpression_label = "(" + splitData.get(1) + ")" //Adding the braces.

            returnOut = computeOutcome(operatorType_2, arithmeticExpression.toDouble(), thirdValue.toDouble())
            splitData = returnOut.split("|").toTypedArray()

            // assign final value
            arithmeticExpression = splitData.get(0).toDouble()
            arithmeticExpression_label = arithmeticExpression_label + operatorType_2 + thirdValue

        } else {

            var firstValue = (1..20).random()
            var secondValue = (1..20).random()
            var thirdValue = (1..20).random()
            var fourthValue = (1..20).random()

            /**
             * selecting the operator type
             */

            // first operator
            val operatorType = operators[(0..3).random()]
            val operatorType_2 = operators[(0..3).random()]
            val operatorType_3 = operators[(0..3).random()]

            // get first the computed value
            var returnOut = computeOutcome(operatorType, firstValue.toDouble(), secondValue.toDouble())
            var splitData = returnOut.split("|").toTypedArray()


            arithmeticExpression = splitData.get(0).toDouble()
            arithmeticExpression_label = "(" + splitData.get(1) + ")"

            returnOut = computeOutcome(operatorType_2, arithmeticExpression.toDouble(), thirdValue.toDouble())
            splitData = returnOut.split("|").toTypedArray()

            // assign final value
            arithmeticExpression = splitData.get(0).toDouble()
            arithmeticExpression_label = "(" + arithmeticExpression_label + operatorType_2 + thirdValue + ")"

            // adding the 4th arithmetic
            returnOut = computeOutcome(operatorType_2, arithmeticExpression.toDouble(), fourthValue.toDouble())
            splitData = returnOut.split("|").toTypedArray()

            // assign final value
            arithmeticExpression = splitData.get(0).toDouble()
            arithmeticExpression_label = arithmeticExpression_label + operatorType_3 + fourthValue
        }


        /**
         * checking the factors in the given results if yes recurring same method to get the details
         */
        if (arithmeticExpression % 1 != 0.0) { //6
            return methodExpression(numberOfChar)
        } else if (arithmeticExpression > 100) {
            //If the arithmetic expression output is greater than 100 then the program will create another random arithmetic expression.
            return methodExpression(numberOfChar)
        }

        return arithmeticExpression.toString() + "|" + arithmeticExpression_label
    }


    fun computeOutcome(operatorType: String, firstValue: Double, secondValue: Double): String {

        var computation = 0.0
        var computation_label = ""

        if (operatorType == "+") {
            computation = firstValue + secondValue
            computation_label = firstValue.toInt().toString() + "+" + secondValue.toInt().toString()
        } else if (operatorType == "/") {
            computation = firstValue / secondValue
            computation_label = firstValue.toInt().toString() + "/" + secondValue.toInt().toString()
        } else if (operatorType == "-") {
            computation = firstValue - secondValue
            computation_label = firstValue.toInt().toString() + "-" + secondValue.toInt().toString()
        } else if (operatorType == "*") {
            computation = firstValue * secondValue
            computation_label = firstValue.toInt().toString() + "*" + secondValue.toInt().toString()
        }


        return computation.toString() + "|" + computation_label
    }

}