package io.nitrix.tablerecyclerview.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface ITableRowsAdapter<L : RecyclerView.ViewHolder> {
    fun getRowCount(): Int
    fun getLeftView(parent: ViewGroup): L
    fun onLeftViewUpdated(holder: L, rowNumber: Int)
}

interface ITableItemAdapter<I : RecyclerView.ViewHolder> {
    fun onCreateRowItemViewHolder(parent: ViewGroup, viewType: Int): I
    fun getRowItemCount(rowNumber: Int): Int
    fun onBindRowItemViewHolder(holder: I, row: Int, column: Int)
}

interface ITableHeaderItemAdapter<H : RecyclerView.ViewHolder> {
    fun getHeaderItemCount(): Int
    fun onCreateHeader(parent: ViewGroup, viewType: Int): H
    fun onBindHeader(holder: H, column: Int)
}

interface ITableCornerViewProvider<C : RecyclerView.ViewHolder> {
    fun getCornerView(parent: ViewGroup): C
}