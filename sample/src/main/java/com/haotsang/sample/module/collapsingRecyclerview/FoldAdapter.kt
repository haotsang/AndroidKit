package com.haotsang.sample.module.collapsingRecyclerview

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.haotsang.sample.databinding.ItemDataBinding
import com.haotsang.sample.databinding.ItemHeaderBinding


class FoldAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val ITEM_TYPE_SECTION_HEADER = 0
        const val ITEM_TYPE_SECTION_ITEM = 1
    }

    private val mLastData: MutableList<Section<Data>> = ArrayList()
    private val mData: MutableList<Section<Data>> = ArrayList()

    private val mSectionIndex: SparseArray<Int> = SparseArray()
    private val mItemIndex: SparseArray<Int> = SparseArray()


    private var onItemClickListener: ((view: View, position: Int, item: Data) -> Unit)? = null
    fun setOnItemClickListener(onItemClickListener: ((view: View, position: Int, item: Data) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }

    private var onItemChildClickListener: ((viewId: Int, item: Data) -> Unit)? = null
    fun setOnItemChildClickListener(onItemChildClickListener: ((viewId: Int, item: Data) -> Unit)?) {
        this.onItemChildClickListener = onItemChildClickListener
    }

    fun setData(list: MutableList<Section<Data>>) {
        mData.clear()
        mData.addAll(list)
        diff(true)
    }

    private fun diff(reValue: Boolean) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(mLastData, mData), false)
        DiffCallback.generateIndex(mData, mSectionIndex, mItemIndex)
        diffResult.dispatchUpdatesTo(this)

        if (reValue) {
            mLastData.clear()
            mData.forEach { mLastData.add(it.clone()) }
        } else {
            // clone status 避免大量创建对象
            mData.forEachIndexed { index, it ->
                it.cloneStatusTo(mLastData[index])
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sectionIndex = mSectionIndex[position]
        val itemIndex = mItemIndex[position]
        val section = mData[sectionIndex]
        when (itemIndex) {
            DiffCallback.ITEM_INDEX_SECTION_HEADER -> (holder as HeaderViewHolder).bind(section)
            else -> {
                val item = section.list[itemIndex]
                (holder as ItemViewHolder).bind(item)
            }
        }
    }

    /**
     * 防止header item刷新闪烁
     */
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        val itemIndex = mItemIndex[position]
        if (itemIndex == DiffCallback.ITEM_INDEX_SECTION_HEADER) {
            when (val latestPayload = payloads.lastOrNull()) {
                is Boolean -> {
                    (holder as HeaderViewHolder).bindState(latestPayload)
                }
            }
        }
    }

    private fun onItemClick(holder: RecyclerView.ViewHolder, pos: Int) {
        val itemIndex = mItemIndex[pos]
        if (itemIndex == DiffCallback.ITEM_INDEX_SECTION_HEADER) {
            toggleFold(pos)
        } else {
            val sectionIndex = mSectionIndex[pos]
            val section = mData[sectionIndex]
            val item = section.list[itemIndex]

            onItemClickListener?.invoke(holder.itemView, pos, item)
        }
    }


    private fun toggleFold(pos: Int) {
        val section = mData[mSectionIndex[pos]]
        section.isFold = !section.isFold
        diff(false)
    }


    fun getRelativeFixedItemPosition(pos: Int): Int {
        var position = pos
        while (getItemViewType(position) != ITEM_TYPE_SECTION_HEADER) {
            position--
            if (position < 0) {
                return RecyclerView.NO_POSITION
            }
        }
        return position
    }

    override fun getItemCount(): Int = mItemIndex.size()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = when (viewType) {
            ITEM_TYPE_SECTION_HEADER -> {
                HeaderViewHolder(
                    ItemHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
            ITEM_TYPE_SECTION_ITEM -> {
                ItemViewHolder(
                    ItemDataBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
            else -> throw IllegalStateException("unknown viewType")
        }

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(viewHolder, position)
            }
        }

        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        val itemIndex = mItemIndex[position]
        return when (itemIndex) {
            DiffCallback.ITEM_INDEX_SECTION_HEADER -> ITEM_TYPE_SECTION_HEADER
            else -> ITEM_TYPE_SECTION_ITEM
        }
    }



    inner class HeaderViewHolder(val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Section<Data>) {
            binding.item = item
            binding.executePendingBindings()
        }

        fun bindState(isFold: Boolean) {
            binding.isFold = isFold
        }
    }

    inner class ItemViewHolder(val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data) {
            binding.item = item
            binding.onClick = View.OnClickListener { v -> onItemChildClickListener?.invoke(v.id, item) }
            binding.executePendingBindings()
        }
    }
}