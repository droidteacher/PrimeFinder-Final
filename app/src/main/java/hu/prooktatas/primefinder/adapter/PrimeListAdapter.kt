package hu.prooktatas.primefinder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.prooktatas.primefinder.model.NumberWithFlag
import hu.prooktatas.primefinder.R

class PrimeListAdapter(var items: List<NumberWithFlag>): RecyclerView.Adapter<PrimeItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrimeItemViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return PrimeItemViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PrimeItemViewHolder, position: Int) {
        val nwf = items[position]
        holder.tvLeft.text = nwf.number.toString()

        if (nwf.flag == null) {
            holder.imageView.visibility = View.GONE
            holder.progressBar.visibility = View.VISIBLE
        }

        nwf.flag?.let {
            holder.imageView.visibility = View.VISIBLE
            holder.progressBar.visibility = View.GONE

            when(it) {
                true -> holder.imageView.setImageResource(R.drawable.check_mark)
                false -> holder.imageView.setImageResource(R.drawable.x_mark)
            }

        }

    }

}

class PrimeItemViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val tvLeft: TextView = itemView.findViewById(R.id.tvNumber)
    val imageView: ImageView = itemView.findViewById(R.id.imgResult)
    val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
}