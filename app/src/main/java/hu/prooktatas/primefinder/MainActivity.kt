package hu.prooktatas.primefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.prooktatas.primefinder.adapter.PrimeListAdapter
import hu.prooktatas.primefinder.async.PrimeConsumer
import hu.prooktatas.primefinder.async.PrimeFinderTask
import hu.prooktatas.primefinder.model.NumberWithFlag


class MainActivity : AppCompatActivity(), PrimeConsumer {

    private lateinit var recyclerView: RecyclerView
    private lateinit var button: Button

    private lateinit var numbers: List<NumberWithFlag>

    private var primeListAdapter: PrimeListAdapter? = null

    private var needsGenerating = false


    init {
        generateNumbers()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.btnCalculate)
        button.setOnClickListener {

            if (needsGenerating) {
                generateNumbers()
            }

            val listOfNumbers = numbers.map { nwf ->
                nwf.number
            }

            val task = PrimeFinderTask(this)
            task.execute(*(listOfNumbers.toTypedArray()))

            button.isEnabled = false

        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = primeListAdapter
    }

    private fun generateNumbers() {
        numbers = List<NumberWithFlag>(ITEM_COUNT) {
            val num = kotlin.random.Random.nextInt(100, 10000)
            NumberWithFlag(num)
        }

        if (primeListAdapter == null) {
            primeListAdapter = PrimeListAdapter(numbers)
        } else {
            primeListAdapter!!.items = numbers
            primeListAdapter!!.notifyDataSetChanged()
        }
    }

    override fun presentResult(someResult: Pair<Int, Boolean>) {

        val matches = numbers.filter {
            it.number == someResult.first
        }

        matches.forEach {
            it.flag = someResult.second
        }

        recyclerView.adapter?.notifyDataSetChanged()

    }

    override fun allTasksDone(count: Int) {
        button.isEnabled = true
        needsGenerating = true
    }
}

const val TAG = "KZs"
const val ITEM_COUNT = 100