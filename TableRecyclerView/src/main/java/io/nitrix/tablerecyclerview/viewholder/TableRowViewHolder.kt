package io.nitrix.tablerecyclerview.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.nitrix.tablerecyclerview.adapter.TableRowItemsAdapter
import io.nitrix.tablerecyclerview.utils.ITableRowsAdapter
import kotlinx.android.synthetic.main.table_row_layout.view.*

class TableRowViewHolder<L : RecyclerView.ViewHolder, T : RecyclerView.ViewHolder>(
    inflater: LayoutInflater,
    parent: ViewGroup,
    private val rowsItemAdapter: TableRowItemsAdapter<T>,
    val rowsAdapter: ITableRowsAdapter<L>
) : AbsRowViewHolder(inflater, parent) {

    val leftViewHolder = rowsAdapter.getLeftView(itemView.horizontal_layout)

    init {
        with(itemView) {
            horizontal_layout.addView(leftViewHolder.itemView, 0)
            horizontal_recycle_view.adapter = rowsItemAdapter
        }
    }

    fun setRowNumber(number: Int) {
        rowsItemAdapter.rowNumber = number
    }
}