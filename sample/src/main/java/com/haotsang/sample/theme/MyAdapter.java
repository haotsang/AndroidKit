package com.haotsang.sample.theme;


import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.haotsang.sample.R;
import com.haotsang.sample.databinding.ItemDatabindBinding;

public class MyAdapter extends BaseQuickAdapter<String, BaseDataBindingHolder<ItemDatabindBinding>> {

    public MyAdapter() {
        super(R.layout.item_databind);
    }

    @Override
    protected void convert(@NonNull BaseDataBindingHolder<ItemDatabindBinding> itemDatabindBindingBaseDataBindingHolder, String s) {
        ItemDatabindBinding dataBinding = itemDatabindBindingBaseDataBindingHolder.getDataBinding();
//        dataBinding.tvTitle.setTextColor(AppTheme.INSTANCE.getContent().getValue());
//        dataBinding.setColor(AppTheme.INSTANCE.getContent().getValue());
        dataBinding.tvTitle.setText(s);
        dataBinding.executePendingBindings();

    }
}

