package com.jindvir.stopwatch

//import android.R
import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.Locale


open class RecordsFragment : Fragment() {

    private lateinit var view: View
    private lateinit var recordAdapter : RecordAdapter
    var txtData: TextView? = null
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter : RecordAdapter
    private var recordList = ArrayList<LapModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        view = inflater.inflate(R.layout.fragment_records, container, false)

        // Inflate the layout for this fragment
        val searchView = view.findViewById<SearchView>(R.id.searchRec)
        val db = DbHelper(view.context, null)

        val cursor = db.getName()

        cursor!!.moveToFirst()
        var recName = cursor.getString(cursor.getColumnIndex(DbHelper.RECORD_NAME))
        var recValue = cursor.getString(cursor.getColumnIndex(DbHelper.RECORD_VALUE))


        recordList.add(LapModel(recName , recValue))
        while(cursor.moveToNext()){
            recName = cursor.getString(cursor.getColumnIndex(DbHelper.RECORD_NAME))
            recValue = cursor.getString(cursor.getColumnIndex(DbHelper.RECORD_VALUE))
            recordList.add(LapModel(recName , recValue))

        }

        // at last we close our cursor

        recyclerView = view.findViewById<RecyclerView>(R.id.rv_lap_record)

        recyclerView.layoutManager = LinearLayoutManager(view.context)
        adapter = RecordAdapter(recordList)
        recyclerView.adapter = adapter




        val swipeToDeleteCall =  object : SwipeToDeleteItem() {
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            view.context,
                            R.color.red
                        )
                    )
                    .addActionIcon(R.drawable.baseline_delete_outline_24)
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position  = viewHolder.adapterPosition
                val itemDelete  = recyclerView.findViewHolderForAdapterPosition(position)?.itemView?.findViewById<TextView>(R.id.lap_name)?.text.toString()

//                Toast.makeText(view.context , itemDelete , Toast.LENGTH_SHORT).show()

                db.deleteCourse(itemDelete)
                recordList.removeAt(position)

                recyclerView.adapter?.notifyItemRemoved(position)
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCall)
        itemTouchHelper.attachToRecyclerView(recyclerView)
//        cursor.close()


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return true
            }
        })
        return view
    }

    private fun filter(query: String?) {
        val filteredlist: ArrayList<LapModel> = ArrayList()
        if (query != null){
            val filterList = ArrayList<LapModel>()
            for (i in recordList){
                if(i.lapNum.lowercase(Locale.ROOT).contains(query)){
                    filterList.add(i)
                }
            }
            if (filterList.isEmpty())
            {
                Toast.makeText(view.context , "Empty" ,Toast.LENGTH_SHORT)
                    .show()            }
            else{
               adapter.setFilteredList(filterList)
            }
        }
    }
}