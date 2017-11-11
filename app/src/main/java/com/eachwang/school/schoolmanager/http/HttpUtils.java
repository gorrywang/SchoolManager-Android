package com.eachwang.school.schoolmanager.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * 网络连接辅助类
 * Created by iswgr on 2017/11/8.
 */

public abstract class HttpUtils {
    /**
     * 天气连接
     */
    public static final String WEATHER_URL = "https://free-api.heweather.com/s6/weather/now?key=aac11d46b15448b5984151cb5e1f4814&location=济南";

    /**
     * 网站
     */
    public static final String URL = "http://school.eachwang.com/";

    /**
     * 获取失物招领
     */
    public static final String GET_SW_URL = URL + "getSw";

    /**
     * 获取表白墙
     */
    public static final String GET_LOVE_URL = URL + "getLove";

    /**
     * 发布表白墙
     */
    public static final String REL_LOVE_URL = URL + "releaseLove";

    /**
     * 发布失物招领
     */
    public static final String REL_Sw_URL = URL + "releaseSw";

    /**
     * 获取通知
     */
    public static final String GET_NOTICE_URL = URL + "getNotice";

    /**
     * 获取最新的表白墙与失物招领
     */
    public static final String GET_NEW_LOVE_AND_SW_URL = URL + "getNew";

    /**
     * 登录
     */
    public static final String LOGIN_URL = URL + "login";

    /**
     * Get请求返回Json数据
     */
    public static void getJsonByGetBack(String url, TextHttpResponseHandler handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, handler);
    }

    /**
     * POST请求返回Json数据
     */
    public static void getJsonByPostBack(String url, RequestParams params, TextHttpResponseHandler handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, handler);
    }

}
