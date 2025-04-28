package com.haotsang.common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

@Deprecated(since = "Use BaseVmFragment instead")
public abstract class BaseFragmentJava<VB extends ViewBinding> extends Fragment {

    protected VB mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = getViewBinding();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(savedInstanceState);
    }

    protected abstract VB getViewBinding();

    protected abstract void initialize(Bundle savedInstanceState);


    @Override
    public void onDestroy() {
        mBinding = null;
        super.onDestroy();
    }
}
