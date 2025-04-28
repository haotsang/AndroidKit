package com.haotsang.common.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindingAdapter<T : Any, M : ViewDataBinding> constructor(
    private val mContext: Context,
    private val mLifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<BaseBindingViewHolder>() {

    private val mData: MutableList<T> = mutableListOf()

    private var mOnItemClickListener: ((T, Int) -> Unit)? = null
    private var mOnItemChildClickListener: MutableList<OnItemChildClickListener>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        val layout = getLayout(viewType)
        if (layout <= 0) {
            throw IllegalArgumentException("illegal layout id, check getLayout() function is implemented correctly.")
        }
        val binding: M =
            DataBindingUtil.inflate(LayoutInflater.from(mContext), layout, parent, false)
        val holder = BaseBindingViewHolder(binding)
        return holder
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {
        val binding: M? = holder.binding as M?
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.invoke(mData.get(position), position)
        }
        mOnItemChildClickListener?.forEach { l ->
            try {
                val view = holder.itemView.findViewById<View>(l.viewId)
                view.setOnClickListener {
                    if (position != -1) {
                        l.onViewClick(view, position)
                    }
                }
            } catch (e: Exception) {
                //ignore
            }
        }
        binding?.let {
            binding.lifecycleOwner = mLifecycleOwner
            onBindData(binding, holder, mData[position], position)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    abstract fun getLayout(viewType: Int): Int

    abstract fun onBindData(binding: M, holder: BaseBindingViewHolder, data: T, position: Int)

    fun setData(data: List<T>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: ((T, Int) -> Unit)) {
        mOnItemClickListener = listener
    }

    fun addOnItemChildClickListener(listener: OnItemChildClickListener) {
        if (mOnItemChildClickListener == null) {
            mOnItemChildClickListener = mutableListOf()
        }
        if (mOnItemChildClickListener?.contains(listener) != true) {
            mOnItemChildClickListener?.add(listener)
        }
    }

}

open class BaseBindingViewHolder(open val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

}

abstract class OnItemChildClickListener(@IdRes val viewId: Int) {
    abstract fun onViewClick(view: View, position: Int)
}