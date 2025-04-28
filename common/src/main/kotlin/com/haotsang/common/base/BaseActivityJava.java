package com.haotsang.common.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

@Deprecated(since = "Use BaseVmActivity instead")
public abstract class BaseActivityJava<VB extends ViewBinding> extends AppCompatActivity {

    protected VB mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = getViewBinding();
        setContentView(mBinding.getRoot());
        initialize(savedInstanceState);
    }

    protected abstract VB getViewBinding();

    protected abstract void initialize(Bundle savedInstanceState);

}