package com.eachwang.school.schoolmanager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eachwang.school.schoolmanager.R;

/**
 * 更多
 * Created by iswgr on 2017/11/7.
 */

public class MoreFragment extends Fragment {
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_more, container, false);
        return mView;
    }
}
