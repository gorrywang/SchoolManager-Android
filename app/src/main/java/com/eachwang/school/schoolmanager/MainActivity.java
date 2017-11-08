package com.eachwang.school.schoolmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eachwang.school.schoolmanager.fragment.IndexFragment;
import com.eachwang.school.schoolmanager.fragment.MessageFragment;
import com.eachwang.school.schoolmanager.fragment.MoreFragment;
import com.eachwang.school.schoolmanager.fragment.ThroughFragment;
import com.eachwang.school.schoolmanager.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.ac_main_img_index)
    ImageView mImgIndex;
    @BindView(R.id.ac_main_txt_index)
    TextView mTxtIndex;
    @BindView(R.id.ac_main_img_through)
    ImageView mImgThrough;
    @BindView(R.id.ac_main_txt_through)
    TextView mTxtThrough;
    @BindView(R.id.ac_main_linear_through)
    LinearLayout mLinearThrough;
    @BindView(R.id.ac_main_img_message)
    ImageView mImgMessage;
    @BindView(R.id.ac_main_txt_message)
    TextView mTxtMessage;
    @BindView(R.id.ac_main_linear_message)
    LinearLayout mLinearMessage;
    @BindView(R.id.ac_main_img_more)
    ImageView mImgMore;
    @BindView(R.id.ac_main_txt_more)
    TextView mTxtMore;
    @BindView(R.id.ac_main_linear_more)
    LinearLayout mLinearMore;
    @BindView(R.id.ac_main_linear_index)
    LinearLayout mLinearIndex;
    private FragmentManager mManager;
    private Fragment mFragBase, mFragIndex, mFragThrough, mFragMessage, mFragMore;
    private FragmentTransaction mTrain;

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        setContentViewId(R.layout.activity_main);
        ButterKnife.bind(this);
        if (Utils.firstRun(this)) {
            //第一次运行, 跳转到firstActivity
            Intent intent = new Intent(this, FirstRunActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //非第一次运行
        mManager = getSupportFragmentManager();
        //加载第一个布局fragment
        addFirstFragment();

    }

    /**
     * 实例化fragment
     */
    private void addFirstFragment() {
        mTrain = mManager.beginTransaction();
        if (mFragIndex == null) {
            mFragIndex = new IndexFragment();
        }
        mFragBase = mFragIndex;
        mTrain.add(R.id.ac_main_frame_load, mFragIndex);
        mTrain.commit();
    }

    @Override
    protected void initView() {
        setTitle("首页");
    }


    @OnClick({R.id.ac_main_linear_through, R.id.ac_main_linear_message, R.id.ac_main_linear_more, R.id.ac_main_linear_index})
    public void onViewClicked(View view) {
        //重置颜色
        resetBottom();
        switch (view.getId()) {
            case R.id.ac_main_linear_through:
                //想法
                setTitle("想法");
                //更改颜色
                setBottom(mImgThrough, mTxtThrough, 2);
                //改变布局
                if (mFragThrough == null) {
                    mFragThrough = new ThroughFragment();
                }
                switchContent(mFragThrough);
                break;
            case R.id.ac_main_linear_message:
                //消息
                setTitle("消息");
                //更改颜色
                setBottom(mImgMessage, mTxtMessage, 3);
                //改变布局
                if (mFragMessage == null) {
                    mFragMessage = new MessageFragment();
                }
                switchContent(mFragMessage);
                break;
            case R.id.ac_main_linear_more:
                //更多
                setTitle("更多");
                //更改颜色
                setBottom(mImgMore, mTxtMore, 4);
                //改变布局
                if (mFragMore == null) {
                    mFragMore = new MoreFragment();
                }
                switchContent(mFragMore);
                break;
            case R.id.ac_main_linear_index:
                //首页
                setTitle("首页");
                //更改颜色
                setBottom(mImgIndex, mTxtIndex, 1);
                //改变布局
                if (mFragIndex == null) {
                    mFragIndex = new IndexFragment();
                }
                switchContent(mFragIndex);
                break;
        }
    }

    /**
     * fragment 切换
     *
     * @param to
     */
    public void switchContent(Fragment to) {
        mTrain = mManager.beginTransaction();
        if (!to.isAdded()) { // 先判断是否被add过
            mTrain.hide(mFragBase)
                    .add(R.id.ac_main_frame_load, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            mTrain.hide(mFragBase).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        mFragBase = to;
    }

    /**
     * 重置底部颜色
     */
    private void resetBottom() {
        mTxtIndex.setTextColor(getResources().getColor(R.color.bottom));
        mTxtThrough.setTextColor(getResources().getColor(R.color.bottom));
        mTxtMessage.setTextColor(getResources().getColor(R.color.bottom));
        mTxtMore.setTextColor(getResources().getColor(R.color.bottom));
        mImgIndex.setImageResource(R.drawable.index);
        mImgThrough.setImageResource(R.drawable.thought);
        mImgMessage.setImageResource(R.drawable.message);
        mImgMore.setImageResource(R.drawable.more);
    }

    /**
     * 设置底部颜色
     *
     * @param img  控件
     * @param text 控件
     * @param i    标识符
     */
    private void setBottom(ImageView img, TextView text, int i) {
        text.setTextColor(getResources().getColor(R.color.bottom_fill));
        switch (i) {
            case 1:
                img.setImageResource(R.drawable.index_fill);
                break;
            case 2:
                img.setImageResource(R.drawable.thought_fill);
                break;
            case 3:
                img.setImageResource(R.drawable.message_fill);
                break;
            case 4:
                img.setImageResource(R.drawable.more_fill);
                break;
        }
    }

}
