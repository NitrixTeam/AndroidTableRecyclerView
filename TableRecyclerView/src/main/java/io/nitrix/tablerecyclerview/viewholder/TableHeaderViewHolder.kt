package io.nitrix.tablerecyclerview.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.nitrix.tablerecyclerview.adapter.TableHeaderItemsAdapter
import io.nitrix.tablerecyclerview.utils.ITableCornerViewProvider
import kotlinx.android.synthetic.main.table_row_layout.view.*

class TableHeaderViewHolder<C : RecyclerView.ViewHolder, H : RecyclerView.ViewHolder>(
    inflater: LayoutInflater,
    parent: ViewGroup,
    headerAdapter: TableHeaderItemsAdapter<H>,
    tableCornerProvider: ITableCornerViewProvider<C>
) : AbsRowViewHolder(inflater, parent) {
    init {
        with(itemView) {
            horizontal_layout.addView(tableCornerProvider.getCornerView(horizontal_layout).itemView, 0)
            horizontal_recycle_view.adapter = headerAdapter
        }
    }
}