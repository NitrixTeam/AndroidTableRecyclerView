package io.nitrix.tablerecyclerview.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.nitrix.tablerecyclerview.utils.ITableItemAdapter

class TableRowItemsAdapter<T : RecyclerView.ViewHolder>(
    private val tableItemsAdapter: ITableItemAdapter<T>
) : RecyclerView.Adapter<T>() {

    var rowNumber: Int = 0
        set(value) {
            try {
                field = value
                notifyDataSetChanged()
            } catch (e : IllegalStateException) {
                Log.e(this.javaClass.name, e.toString())
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        tableItemsAdapter.onCreateRowItemViewHolder(parent, viewType)

    override fun getItemCount() = tableItemsAdapter.getRowItemCount(rowNumber)

    override fun onBindViewHolder(holder: T, position: Int) {
        tableItemsAdapter.onBindRowItemViewHolder(holder, rowNumber, position)
    }


}