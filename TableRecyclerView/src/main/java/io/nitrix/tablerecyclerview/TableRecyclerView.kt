package io.nitrix.tablerecyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.nitrix.tablerecyclerview.adapter.TableHeaderItemsAdapter
import io.nitrix.tablerecyclerview.adapter.TableRowsAdapter
import io.nitrix.tablerecyclerview.utils.*
import io.nitrix.tablerecyclerview.utils.ScrollSyncUtils.Companion.init
import io.nitrix.tablerecyclerview.viewholder.AbsRowViewHolder
import io.nitrix.tablerecyclerview.viewholder.TableHeaderViewHolder
import kotlinx.android.synthetic.main.table_recycler_view.view.*

class TableRecyclerView(
    context: Context?, attrs: AttributeSet?
) : LinearLayout(context, attrs), ScrollSyncUtils.ISyncable {

    var totalScrollListener: (dx: Int, dy: Int) -> Unit = { _, _ -> }

    private val horizontalSyncUtils = ScrollSyncUtils()

    private val verticalSyncUtils = ScrollSyncUtils()

    private val persistenceList = mutableListOf<AbsRowViewHolder>()

    override var scrollListener: (view: ScrollSyncUtils.ISyncable, dx: Int, dy: Int) -> Unit = { _, _, _ -> }

    override var scrollStateListener: (view: ScrollSyncUtils.ISyncable, newState: Int) -> Unit = { _, _ -> }

    override var touchListener: (ScrollSyncUtils.ISyncable, MotionEvent) -> Boolean = { _, _ -> false }

    override var dx: Int = 0

    override var dy: Int = 0

    init {
        View.inflate(context, R.layout.table_recycler_view, this)
        recycle_view.layoutManager = LinearLayoutManager(context)
        init(this, recycle_view)
        verticalSyncUtils.add(this) //this line fixes everything
        verticalSyncUtils.onBind(this)

        horizontalSyncUtils.totalScrollListener = ::onScrollChanged
        verticalSyncUtils.totalScrollListener = ::onScrollChanged
    }

    fun updateData() {
        recycle_view.adapter?.notifyDataSetChanged()
        verticalSyncUtils.onBind(this)
    }

    fun updateTable() {
        verticalSyncUtils.reset()
        horizontalSyncUtils.reset()
        recycle_view.adapter?.notifyDataSetChanged()
        verticalSyncUtils.onBind(this)
        persistenceList.forEach { horizontalSyncUtils.add(it); horizontalSyncUtils.onBind(it) }
    }

    fun <L : RecyclerView.ViewHolder, I : RecyclerView.ViewHolder> setItemAdapter(
        tableAdapter: ITableItemAdapter<I>,
        rowsAdapter: ITableRowsAdapter<L>
    ) {
        recycle_view.adapter = TableRowsAdapter(rowsAdapter, tableAdapter, horizontalSyncUtils)
    }

    fun <C : RecyclerView.ViewHolder, H : RecyclerView.ViewHolder> setHeaderAdapter(
        tableCornerProvider: ITableCornerViewProvider<C>,
        adapterTable: ITableHeaderItemAdapter<H>
    ) {
        val headerAdapter = TableHeaderItemsAdapter(adapterTable)
        val headerViewHolder =
            TableHeaderViewHolder(LayoutInflater.from(context), this, headerAdapter, tableCornerProvider)
        horizontalSyncUtils.add(headerViewHolder)
        persistenceList.clear()
        persistenceList.add(headerViewHolder)
        header_container.removeAllViews()
        header_container.addView(headerViewHolder.itemView)
    }

    private fun onScrollChanged(dx: Int, dy: Int) {
        totalScrollListener(horizontalSyncUtils.totalDx, verticalSyncUtils.totalDy)
    }

    override fun scrollBy(dx: Int, dy: Int) {
        recycle_view.scrollBy(dx, dy)
    }

    override fun resetScroll() {
        dx = 0
        dy = 0
        (recycle_view.layoutManager as LinearLayoutManager)
            .scrollToPositionWithOffset(0, 0)
    }
}