package com.example.batterymanager.activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.batterymanager.R
import com.example.batterymanager.utils.BatteryUsage
import com.example.batterymanager.databinding.ActivityMainBinding
import com.example.batterymanager.helper.SpManager
import com.example.batterymanager.model.BatteryModel
import com.example.batterymanager.service.BatteryAlarmService
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        initDrawer()
        serviceConfig()

        registerReceiver(batteryInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))


    }


    private fun initDrawer() {

        binding.imgMenu.setOnClickListener {
            binding.drawer.openDrawer(Gravity.RIGHT)
        }
        binding.incDrawer.txtAppUsage.setOnClickListener {
            startActivity(Intent(this@MainActivity, UsageBatteryActivity::class.java))
            binding.drawer.closeDrawer(Gravity.RIGHT)

        }


    }

    private fun serviceConfig() {
        if (SpManager.isServiceOn(this@MainActivity) == true) {
            binding.incDrawer.serviceSwitchTxt.text = "service is on"
            binding.incDrawer.serviceSwitch.isChecked = true
            startService()
        } else {
            binding.incDrawer.serviceSwitchTxt.text = "service is off"
            binding.incDrawer.serviceSwitch.isChecked = false
            stopService()
        }

        binding.incDrawer.serviceSwitch.setOnCheckedChangeListener { switch, isCheck ->

            SpManager.setServiceState(this@MainActivity, isCheck)
            if (isCheck) {
                startService()
                binding.incDrawer.serviceSwitchTxt.text = "service is on"
                Toast.makeText(applicationContext, "service is turn on", Toast.LENGTH_SHORT).show()
            } else {
                stopService()
                binding.incDrawer.serviceSwitchTxt.text = "service is off"
                Toast.makeText(applicationContext, "service is turn off", Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun startService() {
        val serviceIntent = Intent(this, BatteryAlarmService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun stopService() {
        val serviceIntent = Intent(this, BatteryAlarmService::class.java)
        stopService(serviceIntent)
    }

    private var batteryInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            var batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            if (intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) == 0) {
                binding.txtPlug.text = "plug-out"
            } else {
                binding.txtPlug.text = " plug-in "
            }


            binding.txtTemp.text =
                (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10).toString() + " °C"
            binding.txtVoltage.text =
                (intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) / 1000).toString() + " Volt"
            binding.txtTechnology.text = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)


            binding.circularProgressBar.progressMax = 100f
            binding.circularProgressBar.setProgressWithAnimation(batteryLevel.toFloat())
            binding.txtCharge.text = batteryLevel.toString() + "%"

            val health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)
            when (health) {
                BatteryManager.BATTERY_HEALTH_DEAD -> {
                    binding.txtHealth.text =
                        "Your battery is fully dead , please change your battery"
                    binding.txtHealth.setTextColor(Color.parseColor("#000000"))
                    binding.imgHealth.setImageResource(R.drawable.health_dead)
                }

                BatteryManager.BATTERY_HEALTH_GOOD -> {
                    binding.txtHealth.text =
                        "Your battery is good , please take care of your battery"
                    binding.txtHealth.setTextColor(Color.GREEN)
                    binding.imgHealth.setImageResource(R.drawable.health_good)

                }

                BatteryManager.BATTERY_HEALTH_COLD -> {
                    binding.txtHealth.text = "Your battery is cold , it's ok"
                    binding.txtHealth.setTextColor(Color.BLUE)
                    binding.imgHealth.setImageResource(R.drawable.health_cold)

                }

                BatteryManager.BATTERY_HEALTH_OVERHEAT -> {
                    binding.txtHealth.text =
                        "Your battery is overheat , please don't work with your phone"
                    binding.txtHealth.setTextColor(Color.RED)
                    binding.imgHealth.setImageResource(R.drawable.health_overheat)

                }

                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> {
                    binding.txtHealth.text =
                        "Your battery is overVoltage , please don't work with your phone"
                    binding.txtHealth.setTextColor(Color.YELLOW)
                    binding.imgHealth.setImageResource(R.drawable.health_voltage)

                }

                else -> {
                    binding.txtHealth.text =
                        "Your battery is fully dead , please change your battery"
                    binding.txtHealth.setTextColor(Color.parseColor("#000000"))
                    binding.imgHealth.setImageResource(R.drawable.health_dead)
                }
            }


        }


    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext, "back", Toast.LENGTH_SHORT).show()

        val dialogBuilder = AlertDialog.Builder(this)
            .setMessage("Do you want to close the application?")
            .setCancelable(true)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                finish()
            })
            .setNegativeButton("cancel" , DialogInterface.OnClickListener{
                dialog , id -> dialog.cancel()
            })

        val alert = dialogBuilder.create()
            alert.setTitle("Exit App")
            alert.show()

    }


}