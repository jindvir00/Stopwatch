package com.jindvir.stopwatch

import android.R.id
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity(){

    private var color = Color.RED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timer = findViewById<TextView>(R.id.timer)
        val records = findViewById<TextView>(R.id.records)
        replaceFragment(TimerFragment())

        timer.setOnClickListener {
            replaceFragment(TimerFragment())

            timer.setTextColor(ColorStateList.valueOf(Color.WHITE))
            records.setTextColor(ColorStateList.valueOf(Color.BLACK))

            timer.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK))
            records.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT))

        }
        records.setOnClickListener {
            replaceFragment(RecordsFragment())
            records.setTextColor(ColorStateList.valueOf(Color.WHITE))
            timer.setTextColor(ColorStateList.valueOf(Color.BLACK))

            records.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK))
            timer.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT))
        }
    }
    private fun AppCompatActivity.replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.ll_swap,fragment , "FRAGMENT_1")
        transaction.addToBackStack(null)
        transaction.commit()
    }



}