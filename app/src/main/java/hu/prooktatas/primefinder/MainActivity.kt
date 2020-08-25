package hu.prooktatas.primefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.prooktatas.primefinder.adapter.PrimeListAdapter
import hu.prooktatas.primefinder.model.NumberWithFlag


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var button: Button

    private lateinit var numbers: List<NumberWithFlag>

    init {
        generateNumbers()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.btnCalculate)
        button.setOnClickListener {
            // TODO: implement this
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PrimeListAdapter(numbers)
    }



    private fun generateNumbers() {
        numbers = List<NumberWithFlag>(ITEM_COUNT) {
            val num = kotlin.random.Random.nextInt(1, 100)
            NumberWithFlag(num)
        }
    }
}

const val TAG = "KZs"
const val ITEM_COUNT = 20