package com.eachwang.school.schoolmanager;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 第一次运行
 */
public class FirstRunActivity extends AppCompatActivity {

    @BindView(R.id.ac_first_surfaceView_view)
    SurfaceView mSurfaceView;
    @BindView(R.id.ac_first_btn_go)
    Button mBtnGo;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mMediaPlayer = new MediaPlayer();
        mSurfaceView.getHolder().setKeepScreenOn(true);
        mSurfaceView.getHolder().addCallback(new surface());
    }

    @OnClick(R.id.ac_first_btn_go)
    public void onViewClicked() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private class surface implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                play();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

    }

    // 播放
    private void play() throws IllegalArgumentException, IllegalStateException, IOException {
        // 设置流媒体类型
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // 读取assets下的视频
        AssetFileDescriptor fd = this.getAssets().openFd("start.mp4");
        // 设置数据来源
        mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
        // 循环播放
        mMediaPlayer.setLooping(true);
        // 将所播放的视频图像输出到指定的SurfaceView组件
        mMediaPlayer.setDisplay(mSurfaceView.getHolder());
        // 通过异步的方式装载媒体资源
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 装载完毕回调
                mMediaPlayer.start();
            }
        });
    }
}
