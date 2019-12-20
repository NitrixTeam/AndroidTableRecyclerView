package io.nitrix.tablerecyclerview.viewholder

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.nitrix.tablerecyclerview.R
import io.nitrix.tablerecyclerview.utils.Logger
import io.nitrix.tablerecyclerview.utils.ScrollSyncUtils
import io.nitrix.tablerecyclerview.utils.ScrollSyncUtils.Companion.init
import kotlinx.android.synthetic.main.table_row_layout.view.*

abstract class AbsRowViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.table_row_layout, parent, false)), ScrollSyncUtils.ISyncable {

    override var scrollListener: (view: ScrollSyncUtils.ISyncable, dx: Int, dy: Int) -> Unit = { _, _, _ -> }

    override var scrollStateListener: (view: ScrollSyncUtils.ISyncable, newState: Int) -> Unit = { _, _ -> }

    override var touchListener: (ScrollSyncUtils.ISyncable, MotionEvent) -> Boolean = { _, _ -> false }

    override var dx: Int = 0

    override var dy: Int = 0

    init {
        with(itemView.horizontal_recycle_view) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            init(this@AbsRowViewHolder, this)
        }
    }

    override fun scrollBy(dx: Int, dy: Int) {
        try {
            itemView.horizontal_recycle_view.scrollBy(dx, dy)
        } catch (e: Exception) {
            Logger.d("exception $e")
        }
    }

    override fun resetScroll() {
        dx = 0
        dy = 0
        (itemView.horizontal_recycle_view.layoutManager as LinearLayoutManager)
            .scrollToPositionWithOffset(0, 0)
    }
}