package com.eachwang.school.schoolmanager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eachwang.school.schoolmanager.R;
import com.eachwang.school.schoolmanager.bean.LoveBean;
import com.eachwang.school.schoolmanager.http.HttpUtils;
import com.eachwang.school.schoolmanager.view.FlowLikeView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

import static com.eachwang.school.schoolmanager.http.HttpUtils.GET_LOVE_URL;

/**
 * 表白墙
 * Created by iswgr on 2017/11/7.
 */

public class LoveFragment extends BaseLazyFragment {
    @BindView(R.id.frag_love_recycler_load)
    RecyclerView mRecycler;
    @BindView(R.id.frag_love_swipe_refresh)
    SwipeRefreshLayout mRefresh;
    Unbinder unbinder;
    private View mView;
    private List<LoveBean> mList = new ArrayList<>();
    private int[] mColorList = new int[]{R.color.colorPrimary, R.color.s1, R.color.s2, R.color.s3};
    private String[] mNameList = new String[]{"爱不单", "美人如歌", "短发郁夏天", "请内心温暖", "暗似黛绿", "阳光的暖冬", "大妹子", "杨家千金", "我以为我们会到老i", "笑尽往事", "森很绿却致人迷途", "丸子头丶小招手", "木槿暧夏七纪年", "雨點落得太敷衍"};
    private MyAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_love, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {
        //设置适配器
        setAdapter();
        //获取数据
        getData();
        //设置下拉刷新
        setRefresh();
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 设置下拉刷新
     */
    private void setRefresh() {
        // 设置下拉进度的背景颜色，默认就是白色的
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh.setRefreshing(true);
                //刷新数据
                getData();
            }
        });
    }

    /**
     * 获取数据
     */
    private void getData() {
        HttpUtils.getJsonByPostBack(GET_LOVE_URL, new RequestParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getContext(), "数据获取失败, 请检查", Toast.LENGTH_SHORT).show();
                mRefresh.setRefreshing(false);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //清理
                mList.clear();
                //Json的解析类对象
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(responseString).getAsJsonArray();
                //解析
                Gson gson = new Gson();
                //加强for循环遍历JsonArray
                for (JsonElement user : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    LoveBean userBean = gson.fromJson(user, LoveBean.class);
                    mList.add(userBean);
                }
                //刷新适配器
                mAdapter.notifyDataSetChanged();
                mRefresh.setRefreshing(false);
            }
        });
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new MyAdapter(R.layout.item_message_load, mList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(manager);
        mRecycler.setAdapter(mAdapter);
    }

    /**
     * 适配器
     */
    private class MyAdapter extends BaseQuickAdapter<LoveBean, MyAdapter.ViewHolder> {


        public MyAdapter(int layoutResId, List<LoveBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final MyAdapter.ViewHolder helper, LoveBean item) {
            helper.love.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.flowLikeView.addLikeView();
                }
            });
            helper.title.setText(item.getTitle());
            helper.content.setText(item.getContent());
            helper.time.setText(item.getCreateTime());
            helper.name.setText(mNameList[new Random().nextInt(13) + 0]);
            helper.cardView.setCardBackgroundColor(getResources().getColor(mColorList[new Random().nextInt(3) + 0]));
        }

        public class ViewHolder extends BaseViewHolder {

            TextView title, content, time, name, love;
            CardView cardView;
            FlowLikeView flowLikeView;

            public ViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.item_message_txt_title);
                name = view.findViewById(R.id.item_message_txt_name);
                content = view.findViewById(R.id.item_message_txt_content);
                time = view.findViewById(R.id.item_message_txt_date);
                cardView = view.findViewById(R.id.item_message_card_show);
                flowLikeView = view.findViewById(R.id.item_message_flow_flowLikeView);
                love = view.findViewById(R.id.item_message_txt_flowLikeView);
            }
        }
    }
}
