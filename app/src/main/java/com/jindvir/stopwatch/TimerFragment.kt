package com.jindvir.stopwatch

//import android.R
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.util.Locale


class TimerFragment : Fragment() {

//    private lateinit
    private lateinit var view: View
    var counter: Int = 0
    private var seconds = 0

    // Is the stopwatch running?
    private var running = false

    private var wasRunning = false

//    private var SM: SendMessage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_timer, container, false)


        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds = savedInstanceState
                .getInt("seconds");
            running = savedInstanceState
                .getBoolean("running");
            wasRunning = savedInstanceState
                .getBoolean("wasRunning");
        }
        runTimer()


    val rv = view.findViewById<RecyclerView>(R.id.rv_lap)
        val llSave = view.findViewById<LinearLayout>(R.id.linearLayout2)
        val recEt = view.findViewById<EditText>(R.id.record_et)
        val llSaveBtn = view.findViewById<Button>(R.id.saveBtn)
        val lap = view.findViewById<TextView>(R.id.lapTv)
        val lapDis = view.findViewById<TextView>(R.id.time_display)

        val start = view.findViewById<TextView>(R.id.startTv)
        val stop = view.findViewById<TextView>(R.id.stopTv)
        val reset = view.findViewById<TextView>(R.id.resetTv)

//        lap.isClickable = false
        var lapList = ArrayList<LapModel>()
        var recList = ArrayList<LapModel>()

        start.setOnClickListener {
            stop.visibility = View.VISIBLE
            reset.visibility = View.VISIBLE
            lap.isEnabled = true

//            lap.i//sClickable = false

            onClickStart(it)

            lap.setOnClickListener {
                ++counter
                val lapNum = "Lap ${counter}"
                val lapVal : String = lapDis.text.toString()

//                Toast.makeText(view.context , "${lapVal}" , Toast.LENGTH_SHORT).show()

                lapList.add(LapModel(lapNum , lapVal))

                rv.layoutManager = LinearLayoutManager(view.context)
                val adapter = LapAdapter(lapList)
                rv.adapter = adapter

                adapter.setOnClickListener(object : LapAdapter.OnClickListener{
                    override fun onClick(position: Int, model: LapModel) {


                        llSave.visibility = View.VISIBLE
                        llSaveBtn.setOnClickListener {
                            val recName = recEt.text.toString()

                            val db = DbHelper(view.context, null)

                            // creating variables for values
                            // in name and age edit texts
                            val name = recName
                            val value = model.lapVal

                            // calling method to add
                            // name to our database
                            db.addName(name, value)

//                            Toast.makeText(view.context , name + value, Toast.LENGTH_SHORT).show()

                            llSave.visibility = View.GONE
                        }
                    }


                })
            }
        }
            stop.setOnClickListener {
            onClickStop(it)
                lap.isEnabled = false
        }
            reset.setOnClickListener {
                onClickReset(it)
                stop.visibility = View.GONE
                reset.visibility = View.GONE
                lap.isEnabled = false
//                lapList.removeAll(lapList)

                rv.removeAllViews()
            }
//        val reset = view.findViewById<TextView>(R.id.resetTv)

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState
            .putInt("seconds", seconds);
        outState
            .putBoolean("running", running);
        outState
            .putBoolean("wasRunning", wasRunning);
    }

    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true;
        }
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running;
        running = false;

    }

    fun onClickStart(view: View?) {
        running = true
    }


    fun onClickStop(view: View?) {
        running = false
    }

    fun onClickReset(view: View?) {
        running = false
        seconds = 0
    }
    private fun runTimer() {
        // Get the text view.
        // Get the text view.
        val timeView = view.findViewById<TextView>(R.id.time_display)


        val handler = Handler()

        handler.post(object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60


                // Format the seconds into hours, minutes,
                // and seconds.
                val time = String.format(
                    Locale.getDefault(),
                    "%d:%02d:%02d", hours,
                    minutes, secs
                )

                timeView.text = time


                if (running) {
                    seconds++
                }


                handler.postDelayed(this, 1000)
            }
        })
    }



}