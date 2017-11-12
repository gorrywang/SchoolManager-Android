package com.eachwang.school.schoolmanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 主
 * Created by iswgr on 2017/11/7.
 */

public abstract class BaseActivity extends AppCompatActivity {

    //    @BindView(R.id.ac_base_bar_title)
    Toolbar mBaseBarTitle;
    ActionBar mActionBar;
    //    @BindView(R.id.ac_base_linear_content)
    LinearLayout mLinearContent;
    //网络状态
    boolean mBoolNetWork = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
//        ButterKnife.bind(this);
        bindID();
        setSupportActionBar(mBaseBarTitle);
        mActionBar = getSupportActionBar();
        onBaseCreate(savedInstanceState);
        initView();
    }

    private void bindID() {
        mBaseBarTitle = findViewById(R.id.ac_base_bar_title);
        mLinearContent = findViewById(R.id.ac_base_linear_content);
    }

    protected abstract void onBaseCreate(Bundle savedInstanceState);

    protected abstract void initView();

    /**
     * 设置布局
     *
     * @param layoutId 布局文件
     */
    protected void setContentViewId(int layoutId) {
        View contentView = getLayoutInflater().inflate(layoutId, null);
        if (mLinearContent.getChildCount() > 0) {
            mLinearContent.removeAllViews();
        }
        if (contentView != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            mLinearContent.addView(contentView, params);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    /**
     * 开启返回按钮
     *
     */
    protected void startFinish() {
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 设置标题栏
     *
     * @param title 标题内容
     */
    protected void setTitle(String title) {
        mActionBar.setTitle(title);
    }

    /**
     * 提示消息
     *
     * @param msg 文本消息
     */
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
