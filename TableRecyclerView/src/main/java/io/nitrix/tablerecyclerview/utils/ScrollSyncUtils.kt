package io.nitrix.tablerecyclerview.utils

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class ScrollSyncUtils {

    var totalScrollListener: (dx: Int, dy: Int) -> Unit = { _, _ -> }

    var totalDx = 0
        private set

    var totalDy = 0
        private set

    private var scrollProvider: ISyncable? = null

    private val holders = mutableSetOf<ISyncable>()

    fun add(holder: ISyncable) = holder.run {
        holders.add(holder)
        scrollListener = { holder, dx, dy ->
            if (scrollProvider == holder) {
                totalDx += dx
                totalDy += dy
                totalScrollListener(totalDx, totalDy)
                holders.filterNot { it == this }.forEach { it.scrollBy(dx, dy) }
                Logger.d("dx = $dx, total = $totalDx, $this")
            }
        }
        scrollStateListener = { view, newState ->
            scrollProvider = when (newState) {
                RecyclerView.SCROLL_STATE_IDLE -> null
                else -> view
            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                invalidatePosition()
            }
            Logger.d("Scroll: $newState, $view scrollProvider = $scrollProvider")
        }
        touchListener = { view, event ->
            (scrollProvider == null || scrollProvider == view).not()
        }
    }

    fun onBind(view: ISyncable) {
        holders.add(view)
        Logger.d("onBind $view")
        view.resetScroll()
        view.scrollBy(totalDx, totalDy)
    }

    fun invalidatePosition() {
        holders.filter { it.dx != totalDx || it.dy != totalDy }.forEach {
            it.scrollBy(totalDx - it.dx, totalDy - it.dy)
        }
    }

    fun reset() {
        holders.clear()
        totalDx = 0
        totalDy = 0
        scrollProvider = null
    }

    interface ISyncable {
        var dx: Int
        var dy: Int
        var scrollListener: (item: ISyncable, dx: Int, dy: Int) -> Unit
        var scrollStateListener: (item: ISyncable, newState: Int) -> Unit
        var touchListener: (item: ISyncable, event: MotionEvent) -> Boolean

        fun resetScroll()
        fun scrollBy(dx: Int, dy: Int)
    }

    companion object {
        fun init(iSyncable: ISyncable, recyclerView: RecyclerView) = with(recyclerView) {
            setOnTouchListener { v, event -> iSyncable.touchListener(iSyncable, event) }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    iSyncable.scrollStateListener(iSyncable, newState)
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    iSyncable.dx += dx
                    iSyncable.dy += dy
                    iSyncable.scrollListener(iSyncable, dx, dy)
                }
            })
        }
    }
}