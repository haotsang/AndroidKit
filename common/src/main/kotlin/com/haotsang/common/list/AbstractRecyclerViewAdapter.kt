package com.haotsang.common.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.haotsang.common.R

abstract class AbstractRecyclerViewAdapter<T : ListItem> : RecyclerView.Adapter<VBViewHolder>() {

    private val viewTypeCache = mutableListOf<Int>()

    private var mOnItemClickListener: ((adapter: AbstractRecyclerViewAdapter<T>, v: View, position: Int) -> Unit)? = null
    private var mOnItemLongClickListener: ((adapter: AbstractRecyclerViewAdapter<T>, v: View, position: Int) -> Boolean)? = null
    private var mOnItemChildClickListener: MutableList<OnItemChildClickListener>? = null

    fun setOnItemClickListener(listener: ((AbstractRecyclerViewAdapter<T>, View, Int) -> Unit)) {
        mOnItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: ((AbstractRecyclerViewAdapter<T>, View, Int) -> Boolean)) {
        mOnItemLongClickListener = listener
    }

    fun addOnItemChildClickListener(listener: OnItemChildClickListener) {
        if (mOnItemChildClickListener == null) {
            mOnItemChildClickListener = mutableListOf()
        }
        if (!mOnItemChildClickListener!!.contains(listener)) {
            mOnItemChildClickListener?.add(listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VBViewHolder {
        val layoutId = viewTypeCache[viewType]
        if (layoutId <= 0) {
            throw IllegalArgumentException("Illegal layoutId!")
        }
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        val viewHolder = VBViewHolder(binding)
        binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        bindViewClickListener(viewHolder, viewType)
        return viewHolder
    }

    override fun onBindViewHolder(holder: VBViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun bindViewClickListener(viewHolder: VBViewHolder, viewType: Int) {
        viewHolder.itemView.setOnClickListener { v ->
            val position = viewHolder.bindingAdapterPosition
            if (position == RecyclerView.NO_POSITION) {
                return@setOnClickListener
            }
            mOnItemClickListener?.invoke(this, v, position)
        }

        viewHolder.itemView.setOnLongClickListener { v ->
            val position = viewHolder.bindingAdapterPosition
            if (position == RecyclerView.NO_POSITION) {
                return@setOnLongClickListener false
            }
            mOnItemLongClickListener?.invoke(this, v, position) ?: false
        }

        mOnItemChildClickListener?.forEach { childView ->
            val view = viewHolder.itemView.findViewById<View>(childView.viewId)
            view?.setOnClickListener {
                val position = viewHolder.bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                childView.onItemChildClick(view, position)
            }
        }

    }

    abstract override fun getItemCount(): Int

    abstract fun getItem(position: Int): ListItem?

    abstract fun getData():List<ListItem>?

    override fun getItemViewType(position: Int): Int {
        val item: ListItem? = getItem(position)
        val identifier = item?.layoutId ?: R.layout.item_payload
        if (!viewTypeCache.contains(identifier)) {
            viewTypeCache.add(identifier)
        }
        return viewTypeCache.indexOf(identifier)
    }

}

open class VBViewHolder(open val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(listItem: ListItem?) {
        binding.setVariable(com.haotsang.common.BR.data, listItem)
        if (binding.hasPendingBindings()) {
            binding.executePendingBindings()
        }
    }
}

abstract class OnItemChildClickListener(@IdRes val viewId: Int) {
    abstract fun onItemChildClick(view: View, position: Int)
}