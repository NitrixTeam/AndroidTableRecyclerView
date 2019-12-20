package io.nitrix.tablerecyclerview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.nitrix.tablerecyclerview.utils.ITableHeaderItemAdapter

class TableHeaderItemsAdapter<H : RecyclerView.ViewHolder>(
    private val adapterTable: ITableHeaderItemAdapter<H>
) : RecyclerView.Adapter<H>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = adapterTable.onCreateHeader(parent, viewType)

    override fun getItemCount() = adapterTable.getHeaderItemCount()

    override fun onBindViewHolder(holder: H, position: Int) = adapterTable.onBindHeader(holder, position)
}