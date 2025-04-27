package com.haotsang.sample.module.collapsingRecyclerview

import android.util.SparseArray
import androidx.recyclerview.widget.DiffUtil
import kotlin.math.min
import androidx.core.util.size

class DiffCallback<T>(private val oldList: List<Section<T>>, private val newList: List<Section<T>>) : DiffUtil.Callback() {

    private val mOldSectionIndex: SparseArray<Int> = SparseArray()
    private val mOldItemIndex: SparseArray<Int> = SparseArray()

    private val mNewSectionIndex: SparseArray<Int> = SparseArray()
    private val mNewItemIndex: SparseArray<Int> = SparseArray()

    init {
        generateIndex(oldList, mOldSectionIndex, mOldItemIndex)
        generateIndex(newList, mNewSectionIndex, mNewItemIndex)

    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        val oldSectionIndex = mOldSectionIndex[oldItemPosition]
        val oldItemIndex = mOldItemIndex[oldItemPosition]
        val oldModel = oldList[oldSectionIndex]

        val newSectionIndex = mNewSectionIndex[newItemPosition]
        val newItemIndex = mNewItemIndex[newItemPosition]
        val newModel = newList[newSectionIndex]

        if (oldModel.headerTitle != newModel.headerTitle) {
            return false
        }

        if (oldItemIndex < 0 && oldItemIndex == newItemIndex) {
            return true
        }

        if (oldItemIndex < 0 || newItemIndex < 0) {
            return false
        }
        return oldModel.list[oldItemIndex] == newModel.list[newItemIndex]
    }

    override fun getOldListSize() = mOldSectionIndex.size

    override fun getNewListSize() = mNewSectionIndex.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        val oldSectionIndex = mOldSectionIndex[oldItemPosition]
        val oldItemIndex = mOldItemIndex[oldItemPosition]
        val oldModel = oldList[oldSectionIndex]

        val newSectionIndex = mNewSectionIndex[newItemPosition]
        val newModel = newList[newSectionIndex]

        if (oldItemIndex == ITEM_INDEX_SECTION_HEADER) {
            return oldModel.isFold == newModel.isFold
        }


        return true
    }

    /**
     * 防止header item刷新闪烁
     */
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldSectionIndex = mOldSectionIndex[oldItemPosition]
        val oldItemIndex = mOldItemIndex[oldItemPosition]
        val oldModel = oldList[oldSectionIndex]

        if (oldItemIndex == ITEM_INDEX_SECTION_HEADER) {
            return oldModel.isFold
        }

        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    companion object {
        const val ITEM_INDEX_SECTION_HEADER = -1
        const val LIST_SPAN_LIMIT_COUNT = 3

        fun <T> generateIndex(
            list: List<Section<T>>,
            sectionIndex: SparseArray<Int>,
            itemIndex: SparseArray<Int>
        ) {
            sectionIndex.clear()
            itemIndex.clear()
            var i = 0
            list.forEachIndexed { index, it ->
                sectionIndex.append(i, index)
                itemIndex.append(i, ITEM_INDEX_SECTION_HEADER)
                i++

                val count = if (!it.isFold) it.count() else min(LIST_SPAN_LIMIT_COUNT, it.count())
                for (j in 0 until count) {
                    sectionIndex.append(i, index)
                    itemIndex.append(i, j)
                    i++
                }
            }
        }
    }
}