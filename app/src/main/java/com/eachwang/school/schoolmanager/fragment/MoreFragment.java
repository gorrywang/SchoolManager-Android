package com.eachwang.school.schoolmanager.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eachwang.school.schoolmanager.LoginActivity;
import com.eachwang.school.schoolmanager.R;
import com.eachwang.school.schoolmanager.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 更多
 * Created by iswgr on 2017/11/7.
 */

public class MoreFragment extends Fragment {
    @BindView(R.id.frag_more_txt_login)
    TextView mTxtLogin;
    @BindView(R.id.frag_more_txt_cj)
    TextView mTxtCj;
    @BindView(R.id.frag_more_txt_clear)
    TextView mTxtClear;
    @BindView(R.id.frag_more_txt_out)
    TextView mTxtOut;
    Unbinder unbinder;
    private View mView;
    String mName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_more, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取数据
        SharedPreferences sp = getActivity().getSharedPreferences(Utils.CONFIG_FILE_NAME, getContext().MODE_PRIVATE);
        mName = sp.getString("name", null);
        if (mName == null || "".equals(mName)) {
            mTxtOut.setVisibility(View.GONE);
        } else {
            mTxtLogin.setText(mName + ": 登录成功");
            mTxtOut.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.frag_more_txt_login, R.id.frag_more_txt_cj, R.id.frag_more_txt_clear, R.id.frag_more_txt_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.frag_more_txt_login:
                //获取数据
                SharedPreferences sp = getActivity().getSharedPreferences(Utils.CONFIG_FILE_NAME, getContext().MODE_PRIVATE);
                mName = sp.getString("name", null);
                if (mName == null || "".equals(mName)) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    this.startActivityForResult(intent, 1);
                }
                break;
            case R.id.frag_more_txt_cj:
                break;
            case R.id.frag_more_txt_clear:
                if (mName != null || "".equals(mName))
                    Toast.makeText(getContext(), "清理缓存成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "暂无缓存", Toast.LENGTH_SHORT).show();
                break;
            case R.id.frag_more_txt_out:
                SharedPreferences sp1 = getActivity().getSharedPreferences(Utils.CONFIG_FILE_NAME, getContext().MODE_PRIVATE);
                mName = sp1.getString("name", null);
                if (mName != null || "".equals(mName)) {
                    SharedPreferences.Editor edit = sp1.edit();
                    edit.putString("name", "");
                    edit.putString("studentNum", "");
                    edit.commit();
                    mTxtLogin.setText("立即登录");
                    Toast.makeText(getContext(), "退出成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);   //this
        Toast.makeText(getContext(), "11", Toast.LENGTH_SHORT).show();

        if (requestCode == 1) {
            //登录
            //获取数据
            SharedPreferences sp = getActivity().getSharedPreferences(Utils.CONFIG_FILE_NAME, getContext().MODE_PRIVATE);
            mName = sp.getString("name", null);
            if (mName == null || "".equals(mName)) {
                mTxtOut.setVisibility(View.GONE);
            } else {
                mTxtLogin.setText(mName + ": 登录成功");
                mTxtOut.setVisibility(View.VISIBLE);
            }
        }
    }
}
