package com.eachwang.school.schoolmanager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eachwang.school.schoolmanager.R;
import com.eachwang.school.schoolmanager.bean.SwBean;
import com.eachwang.school.schoolmanager.http.HttpUtils;
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

import static com.eachwang.school.schoolmanager.http.HttpUtils.GET_SW_URL;

/**
 * 失物招领
 * Created by iswgr on 2017/11/7.
 */

public class SwFragment extends Fragment {
    @BindView(R.id.frag_sw_recycler_load)
    RecyclerView mRecycler;
    @BindView(R.id.frag_sw_swipe_refresh)
    SwipeRefreshLayout mRefresh;
    Unbinder unbinder;
    private View mView;
    private List<SwBean> mList = new ArrayList<>();
    private int[] mColorList = new int[]{R.color.colorPrimary};
    private MyAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sw, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取数据
        getData();
        //设置适配器
        setAdapter();
        //设置下拉刷新
        setRefresh();
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
        HttpUtils.getJsonByPostBack(GET_SW_URL, new RequestParams(), new TextHttpResponseHandler() {
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
                    SwBean userBean = gson.fromJson(user, SwBean.class);
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
    private class MyAdapter extends BaseQuickAdapter<SwBean, MyAdapter.ViewHolder> {


        public MyAdapter(int layoutResId, List<SwBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(MyAdapter.ViewHolder helper, SwBean item) {
            Toast.makeText(getContext(),item.getTitle()+"a",Toast.LENGTH_SHORT).show();
            helper.title.setText(item.getTitle());
            helper.content.setText(item.getContent());
            helper.time.setText(item.getCreateTime());
            helper.name.setText(item.getStudentNum());
            helper.tel.setText(item.getTel());
            helper.addr.setText(item.getAddr());
            if (item.getStatus() == 1) {
                helper.img.setVisibility(View.VISIBLE);
            } else {
                helper.img.setVisibility(View.GONE);
            }
            helper.cardView.setCardBackgroundColor(getResources().getColor(mColorList[0]));
        }

        class ViewHolder extends BaseViewHolder {

            TextView title, content, time, name, tel, addr;
            CardView cardView;
            ImageView img;

            public ViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.item_sw_txt_title);
                content = view.findViewById(R.id.item_sw_txt_content);
                time = view.findViewById(R.id.item_sw_txt_date);
                name = view.findViewById(R.id.item_sw_txt_name);
                tel = view.findViewById(R.id.item_sw_txt_tel);
                addr = view.findViewById(R.id.item_sw_txt_addr);
                img = view.findViewById(R.id.item_sw_img_ok);
                cardView = view.findViewById(R.id.item_sw_card_show);
            }
        }
    }
}
