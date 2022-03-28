package com.example.coursework

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val aboutButton: Button = findViewById(R.id.btnLess)
        aboutButton.setOnClickListener {
            val ok: Button
            val cancel: ImageView
            //will create a view of our custom dialog layout
            val alertCustomdialog: View =
                LayoutInflater.from(this@MainActivity).inflate(R.layout.custom_dialog, null)
            //initialize alert builder.
            val alert: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)

            //set the custom alert dialog to the alertdialog builder
            alert.setView(alertCustomdialog)
            cancel = alertCustomdialog.findViewById(R.id.cancel_button)
            ok = alertCustomdialog.findViewById(R.id.btnOk)
            val dialog: AlertDialog = alert.create()
            //this line removed app bar from dialog and make it transparent and you see the image is like floating outside dialog box.
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //finally show the dialog box in android
            dialog.show()
            cancel.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    dialog.dismiss()
                }
            }
            )
            ok.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    dialog.dismiss()
                }
            }
            )
        }
    }
    fun switchToGameMode(view : View) {
        if (view.id == R.id.btnEqual) intent = Intent(
            this,
            NewGame::class.java
        )
        startActivity(intent)
    }
}