package com.eachwang.school.schoolmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eachwang.school.schoolmanager.http.HttpUtils;
import com.eachwang.school.schoolmanager.utils.Utils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.eachwang.school.schoolmanager.utils.Utils.CONFIG_FILE_NAME;

/**
 * 发布Activity
 */
public class ReleaseActivity extends BaseActivity {

    private int data;

    @BindView(R.id.ac_release_edit_title)
    EditText mEditTitle;
    @BindView(R.id.ac_release_edit_text)
    EditText mEditText;
    @BindView(R.id.ac_release_edit_tell)
    EditText mEditTell;
    @BindView(R.id.ac_release_edit_address)
    EditText mEditAddress;
    @BindView(R.id.ac_release_btn_submit)
    Button mBtnSubmit;

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        setContentViewId(R.layout.activity_release);
        ButterKnife.bind(this);
        data = getIntent().getIntExtra("data", 0);
    }

    @Override
    protected void initView() {
        if (data == 1) {
            setTitle("发布表白");
            mEditTell.setVisibility(View.GONE);
            mEditAddress.setVisibility(View.GONE);
        } else if (data == 2) {
            setTitle("发布失物招领");
        }
    }

    @OnClick({R.id.ac_release_btn_submit})
    public void onViewClicked(View view) {
        String title = mEditTitle.getText().toString();
        String text = mEditText.getText().toString();
        if (title.equals("")) {
            Toast.makeText(ReleaseActivity.this, "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        } else if (text.equals("")) {
            Toast.makeText(ReleaseActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestParams params = new RequestParams();
        String url = HttpUtils.REL_LOVE_URL;
        SharedPreferences sharedPreferences = getSharedPreferences(CONFIG_FILE_NAME, MODE_PRIVATE);
        String studentNum = sharedPreferences.getString("studentNum", null);
        if (studentNum == null && "".equals(studentNum)) {
            Toast.makeText(ReleaseActivity.this, "请返回登录", Toast.LENGTH_SHORT).show();
            return;
        }
        params.put("title", title);
        params.put("content", text);
        params.put("studentNum", studentNum);

        if (data == 2) {
            String tel = mEditTell.getText().toString().trim();
            String addr = mEditAddress.getText().toString();
            if (tel.equals("") && Utils.checkPhoneNumber(tel)) {
                Toast.makeText(ReleaseActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            } else if (addr.equals("")) {
                Toast.makeText(ReleaseActivity.this, "请输入联系地址", Toast.LENGTH_SHORT).show();
                return;
            }
            params.put("addr", addr);
            params.put("tel", tel);
            url = HttpUtils.REL_Sw_URL;
        }

        HttpUtils.getJsonByPostBack(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(ReleaseActivity.this, "联网失败, 请检查", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (responseString.contains("1")) {
                    Toast.makeText(ReleaseActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    ReleaseActivity.this.finish();
                }
            }
        });
    }

}
