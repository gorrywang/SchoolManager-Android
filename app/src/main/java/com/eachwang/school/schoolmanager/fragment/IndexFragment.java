package com.eachwang.school.schoolmanager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eachwang.school.schoolmanager.R;
import com.eachwang.school.schoolmanager.bean.LoveAndSwBean;
import com.eachwang.school.schoolmanager.bean.WeatherBean;
import com.eachwang.school.schoolmanager.http.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

import static com.eachwang.school.schoolmanager.http.HttpUtils.GET_NEW_LOVE_AND_SW_URL;

/**
 * 首页
 * Created by iswgr on 2017/11/7.
 */

public class IndexFragment extends Fragment {
    @BindView(R.id.frag_index_pager_load)
    ViewPager mPager;
    Unbinder unbinder;
    @BindView(R.id.frag_index_txt_status)
    TextView mTxtStatus;
    @BindView(R.id.frag_index_txt_f)
    TextView mTxtF;
    @BindView(R.id.frag_index_txt_time)
    TextView mTxtTime;
    @BindView(R.id.frag_index_txt_wd)
    TextView mTxtWd;
    @BindView(R.id.frag_index_txt_love_title)
    TextView mTxtLoveTitle;
    @BindView(R.id.frag_index_txt_love_content)
    TextView mTxtLoveContent;
    @BindView(R.id.frag_index_txt_zl_title)
    TextView mTxtZlTitle;
    @BindView(R.id.frag_index_txt_zl_content)
    TextView mTxtZlContent;
    @BindView(R.id.frag_index_txt_zl_tel)
    TextView mTxtZlTel;
    @BindView(R.id.frag_index_txt_zl_addr)
    TextView mTxtZlAddr;
    @BindView(R.id.frag_index_swipe_refresh)
    SwipeRefreshLayout mRefresh;
    private View mView;
    private List<View> mPagerList = new ArrayList<>();
    private MyPagerAdapter mMyPagerAdapter;
    private int[] mImgList = new int[]{R.drawable.banner01, R.drawable.banner02,
            R.drawable.banner03, R.drawable.banner04, R.drawable.banner05};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_index, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //添加图片
        addPic();
        //初始化适配器
        initPagerAdapter();
        //下拉刷新
        refresh();
        //获取天气信息
        getWeather();
        //获取最新的表白与失物招领
        getNew();
    }

    /**
     * 下拉刷新
     */
    private void refresh() {
        // 设置下拉进度的背景颜色，默认就是白色的
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                mRefresh.setRefreshing(true);
                getWeather();
                getNew();
            }
        });
    }

    /**
     * 获取最新的表白
     */
    private void getNew() {
        HttpUtils.getJsonByPostBack(GET_NEW_LOVE_AND_SW_URL, new RequestParams(),
                new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                Toast.makeText(getContext(), "最新数据获取失败, 请检查网络状态", Toast.LENGTH_SHORT).show();
                mRefresh.setRefreshing(false);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //Json的解析类对象
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(responseString).getAsJsonArray();
                //解析
                Gson gson = new Gson();
                ArrayList<LoveAndSwBean> userBeanList = new ArrayList<>();
                //加强for循环遍历JsonArray
                for (JsonElement user : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    LoveAndSwBean userBean = gson.fromJson(user, LoveAndSwBean.class);
                    userBeanList.add(userBean);
                }
                //失物招领
                if (userBeanList.get(1) != null) {
                    mTxtZlTitle.setText(userBeanList.get(1).getTitle());
                    mTxtZlContent.setText(userBeanList.get(1).getContent());
                    mTxtZlTel.setText("联系电话: " + userBeanList.get(1).getTel());
                    mTxtZlAddr.setText("联系地址: " + userBeanList.get(1).getAddr());
                }
                //表白墙
                if (userBeanList.get(0) != null) {
                    mTxtLoveTitle.setText(userBeanList.get(0).getTitle());
                    mTxtLoveContent.setText(userBeanList.get(0).getContent());
                }
                // 开始刷新，设置当前为刷新状态
                mRefresh.setRefreshing(false);
            }
        });
    }

    /**
     * 获取天气信息
     */
    private void getWeather() {
        HttpUtils.getJsonByGetBack(HttpUtils.WEATHER_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                Toast.makeText(getContext(), "天气信息获取失败, 请检查", Toast.LENGTH_SHORT).show();
                mRefresh.setRefreshing(false);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //解析
                Gson gson = new Gson();
                WeatherBean bean = gson.fromJson(responseString, WeatherBean.class);
                //显示数据
                //更新时间
                mTxtTime.setText("更新时间: " + bean.getHeWeather6().get(0).getUpdate().getLoc());
                //温度
                mTxtWd.setText(bean.getHeWeather6().get(0).getNow().getTmp() + "℃");
                //天气状况
                mTxtStatus.setText(bean.getHeWeather6().get(0).getNow().getCond_txt());
                //风 风速
                mTxtF.setText(bean.getHeWeather6().get(0).getNow().getWind_sc() + "     " + bean.getHeWeather6().get(0).getNow().getWind_dir());
            }
        });
    }

    /**
     * 初始化滑动适配器
     */
    private void initPagerAdapter() {
        mMyPagerAdapter = new MyPagerAdapter();
        mPager.setAdapter(mMyPagerAdapter);
    }

    /**
     * 添加图片
     */
    private void addPic() {
        for (int i = 0; i < 5; i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_index_img, null);
            ImageView imageView = view.findViewById(R.id.item_index_img_img);
            Glide.with(getContext()).load(mImgList[i]).into(imageView);
            mPagerList.add(view);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 滑动适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mPagerList.get(position));
            return mPagerList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
