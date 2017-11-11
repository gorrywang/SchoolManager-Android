package com.eachwang.school.schoolmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eachwang.school.schoolmanager.bean.UserBean;
import com.eachwang.school.schoolmanager.http.HttpUtils;
import com.eachwang.school.schoolmanager.utils.MD5Utils;
import com.eachwang.school.schoolmanager.utils.Utils;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.eachwang.school.schoolmanager.http.HttpUtils.LOGIN_URL;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.ac_login_edit_num)
    EditText mEditNum;
    @BindView(R.id.ac_login_edit_pwd)
    EditText mEditPwd;
    @BindView(R.id.ac_login_btn_login)
    Button mBtnLogin;

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        setTitle("登录");
    }


    @OnClick(R.id.ac_login_btn_login)
    public void onViewClicked() {
        String num = mEditNum.getText().toString().trim();
        String pwd = mEditPwd.getText().toString().trim();
        if (TextUtils.isEmpty(num) && TextUtils.isEmpty(pwd)) {
            Toast.makeText(LoginActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
            return;
        }
        //发送数据
        sendData(num, pwd);
    }

    /**
     * 发送数据
     *
     * @param num 学号
     * @param pwd 密码
     */
    private void sendData(String num, String pwd) {
        RequestParams params = new RequestParams();
        params.put("username", num);
        params.put("password", MD5Utils.MD5(pwd));
        HttpUtils.getJsonByPostBack(LOGIN_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                UserBean userBean = new Gson().fromJson(responseString, UserBean.class);
                int result = userBean.getResult();
                if (result == 1) {
                    //成功
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //保存数据
                    SharedPreferences sp = getSharedPreferences(Utils.CONFIG_FILE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("name", userBean.getName());
                    edit.putString("studentNum", userBean.getStudentNum());
                    edit.commit();
                    setResult(RESULT_OK,getIntent());
                    finish();
                } else {
                    //失败
                    Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
