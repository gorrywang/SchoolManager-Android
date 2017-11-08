package com.eachwang.school.schoolmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 工具类
 * Created by iswgr on 2017/11/7.
 */

public abstract class Utils {
    /**
     * 公用配置文件名称
     */
    public static final String CONFIG_FILE_NAME = "config.data";

    /**
     * 程序是否第一次运行
     *
     * @param context 上下文
     * @return 返回布尔值
     */
    public static boolean firstRun(Context context) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE);
        boolean firstBool = sp.getBoolean("firstRun", true);
        if (firstBool) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("firstRun", false);
            edit.commit();
        }
        return firstBool;
    }
}
