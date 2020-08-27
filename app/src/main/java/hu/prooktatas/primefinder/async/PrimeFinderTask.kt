package hu.prooktatas.primefinder.async

import android.os.AsyncTask
import android.util.Log
import hu.prooktatas.primefinder.TAG
import java.lang.Exception

interface PrimeConsumer {
    fun presentResult(someResult: Pair<Int, Boolean>)
    fun allTasksDone(count: Int)
}

class PrimeFinderTask(private val consumer: PrimeConsumer): AsyncTask<Int, Pair<Int, Boolean>, Int>() {

    override fun doInBackground(vararg params: Int?): Int {
        var numberOfPrimes = 0

        params.forEach { someNumber ->

            try {
                Thread.sleep(250)
            } catch(e: Exception) {
                e.printStackTrace()
            }

            someNumber?.let {
                val boolValue = isPrime(it)
                publishProgress(Pair(it, boolValue))

                if (boolValue) {
                    numberOfPrimes++
                }
            }
        }


        return numberOfPrimes
    }

    override fun onPreExecute() {
        Log.d(TAG, "onPreExecute")
    }

    override fun onPostExecute(result: Int) {
        Log.d(TAG, "onPostExecute")
        consumer.allTasksDone(result)
    }

    override fun onProgressUpdate(vararg values: Pair<Int, Boolean>) {
        Log.d(TAG, "onPostExecute")
        consumer.presentResult(values[0])
    }

    private fun isPrime(num: Int): Boolean {

        when(num) {
            in Int.MIN_VALUE..1 -> return false
            2 -> return true
            else -> {
                for (i in 2 until num) {
                    if (num % i == 0) {
                        return false
                    }
                }

                return true
            }
        }
    }

}