package com.haotsang.common_java.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

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