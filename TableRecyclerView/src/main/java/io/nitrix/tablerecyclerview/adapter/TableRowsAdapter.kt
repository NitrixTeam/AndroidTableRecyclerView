package io.nitrix.tablerecyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.nitrix.tablerecyclerview.utils.ITableItemAdapter
import io.nitrix.tablerecyclerview.utils.ITableRowsAdapter
import io.nitrix.tablerecyclerview.utils.ScrollSyncUtils
import io.nitrix.tablerecyclerview.viewholder.TableRowViewHolder

class TableRowsAdapter<L : RecyclerView.ViewHolder, I : RecyclerView.ViewHolder>(
    private val rowsAdapter: ITableRowsAdapter<L>,
    private val itemsAdapter: ITableItemAdapter<I>,
    private val scrollSyncUtils: ScrollSyncUtils
) : RecyclerView.Adapter<TableRowViewHolder<L, I>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TableRowViewHolder<L, I>(
        LayoutInflater.from(parent.context),
        parent,
        TableRowItemsAdapter(itemsAdapter),
        rowsAdapter
    ).apply {
        scrollSyncUtils.add(this)
    }

    override fun getItemCount() = rowsAdapter.getRowCount()

    override fun onBindViewHolder(holder: TableRowViewHolder<L, I>, position: Int) = with(holder) {
        rowsAdapter.onLeftViewUpdated(holder.leftViewHolder, position)
        setRowNumber(position)
        scrollSyncUtils.onBind(holder)
    }


}

