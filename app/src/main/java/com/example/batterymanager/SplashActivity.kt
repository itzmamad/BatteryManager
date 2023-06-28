package com.example.batterymanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.batterymanager.databinding.ActivitySplashBinding
import java.util.Timer
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {

   // private lateinit var helpTxt: TextView // for global varriable we use lateinit var to use this var in everyWhere
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        helpTxt = findViewById<TextView>(R.id.help_txt)



       /* helpTextGenerator(1000, "t1")
        helpTextGenerator(2000, "t2")
        helpTextGenerator(3000, "t3")
        helpTextGenerator(4000, "t4")
        helpTextGenerator(5000, "t5")
        helpTextGenerator(6000, "t6")*/
        //halghe for kar har do comment ro anjam mide


        var textArray = arrayOf(
            "Make Your Battery Powerful",
            "Make Your Battery safe",
            "Make Your Battery faster",
            "Make Your Battery Powerful",
            "Manage Your Phone Battery",
            "Notify when your phone is full charge"
        )
        var i = 1
        for (i in 1..6){
            helpTextGenerator((i * 1000).toLong(), textArray[i - 1])
        }

        /* findViewById<TextView>(R.id.help_txt) // albate in kar eshtebah va bayad az binding estefade konim

     Timer().schedule(timerTask {
         runOnUiThread(timerTask {
             helpTxt.text = "t1"
         })
     }, 1000)
     Timer().schedule(timerTask {
         runOnUiThread(timerTask {
             helpTxt.text = "t2"
         })
     }, 2000)
     Timer().schedule(timerTask {
         runOnUiThread(timerTask {
             helpTxt.text = "t3"
         })
     }, 3000)
     Timer().schedule(timerTask {
         runOnUiThread(timerTask {
             helpTxt.text = "t4"
         })
     }, 4000)
     Timer().schedule(timerTask {
         runOnUiThread(timerTask {
             helpTxt.text = "t5"
         })
     }, 5000)
     Timer().schedule(timerTask {
         runOnUiThread(timerTask {
             helpTxt.text = "t6"
         })
     }, 6000)*/



        Timer().schedule(timerTask {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }, 7000)
    }


    private fun helpTextGenerator(delayTime: Long, helpText: String) {
        Timer().schedule(timerTask {
            runOnUiThread(timerTask {
//                helpTxt.text = helpText
                binding.helpTxt.text = helpText
            })
        }, delayTime)
    }
}