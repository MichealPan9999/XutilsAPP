package com.example.panzq.xutilsapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    //http://api.m.mtime.cn/PageSubArea/TrailerList.api 返回json格式
    //http://vfx.mtime.cn/Video/2016/09/15/mp4/160915092608935956_480.mp4 测试下载
    @ViewInject(R.id.btn1)
    private Button btn1;
    @ViewInject(R.id.btn2)
    private Button btn2;
    @ViewInject(R.id.textView)
    private TextView mTvText;
    @ViewInject(R.id.imageView)
    private ImageView mImage;
    ImageOptions mImageOptions;
    @ViewInject(R.id.btn3)
    private Button btn3;
    @ViewInject(R.id.btn4)
    private Button btn4;
    @ViewInject(R.id.btn5)
    private Button btn5;
    private String sdPath = null;
    //创建SD卡映射  mksdcard -l sdcard 128M "D:\sdcard.img"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        btn1.setText("btn1发送get请求");
        btn2.setText("btn2发送post请求");

        sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        verifyStoragePermissions(MainActivity.this);
        mImageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(300),DensityUtil.dip2px(300))
                .setRadius(DensityUtil.dip2px(25))
                    // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true)
                    // 加载中或错误图片的ScaleType
                    //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    //设置加载过程中的图片
                .setLoadingDrawableId(R.mipmap.button_load)
                    //设置加载失败后的图片
                .setFailureDrawableId(R.mipmap.sign_error_icon)
                    //设置使用缓存
                .setUseMemCache(true)
                    //设置支持gif
                .setIgnoreGif(false)
                .setFadeIn(true)
                    //设置显示圆形图片
                //.setCircular(true)
                .build();
        /**
         //通过ImageOptions.Builder().set方法设置图片的属性
         ImageOptions imageOptions= new ImageOptions.Builder().setFadeIn(true).build(); //淡入效果
         //ImageOptions.Builder()的一些其他属性：
         .setCircular(true) //设置图片显示为圆形
         .setSquare(true) //设置图片显示为正方形
         .setCrop(true).setSize(200,200) //设置大小
         .setAnimation(animation) //设置动画
         .setFailureDrawable(Drawable failureDrawable) //设置加载失败的动画
         .setFailureDrawableId(int failureDrawable) //以资源id设置加载失败的动画
         .setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
         .setLoadingDrawableId(int loadingDrawable) //以资源id设置加载中的动画
         .setIgnoreGif(false) //忽略Gif图片
         .setParamsBuilder(ParamsBuilder paramsBuilder) //在网络请求中添加一些参数
         .setRaduis(int raduis) //设置拐角弧度
         .setUseMemCache(true) //设置使用MemCache，默认true
         */

    }

    @Event(value = {R.id.btn1, R.id.btn2,R.id.btn4,R.id.btn5})
    private void showBtnContent(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Toast.makeText(MainActivity.this, btn1.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                LogUtil.d("------"+btn1.getText());
                doGET("http://192.168.12.80:8080/code_2/servlet/LoginServlet");
                break;
            case R.id.btn2:
                Toast.makeText(MainActivity.this, btn2.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                LogUtil.d("------"+btn2.getText());
                doPost("http://192.168.12.80:8080/code_2/servlet/LoginServlet");
                break;
            case R.id.btn4:
                Toast.makeText(MainActivity.this, btn4.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                String uploadHost="http://192.168.12.80:8080/FileUpload/FileUploadServlet";
                unloadFile(uploadHost);
                break;
            case R.id.btn5:
                Toast.makeText(MainActivity.this, btn5.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                downloadFile("http://192.168.12.80:80/","0.jpg");
                break;
        }
    }

    @Event(value = {R.id.btn3})
    private void loadImage(View view)
    {
        //x.image().bind(mImage,"http://img2.3lian.com/2014/f2/164/d/17.jpg",mImageOptions);
         //assets file
        x.image().bind(mImage, "assets://1.jpg", mImageOptions);
        //x.image().bind(mImage, sdPath+"/test.gif", mImageOptions);
    }

    private void doGET(String url)
    {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("username","zhangsan");
        params.addBodyParameter("password","123456");
        Log.d("panzq","get .... uri"+params.getUri());
        Log.d("panzq","get .... uri_all"+params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("panzq","success get....."+result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("panzq","onError get.....");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("panzq","onCancelled get.....");
            }

            @Override
            public void onFinished() {
                Log.d("panzq","onFinished get.....");
            }
        });
    }
    private void doPost(String url)
    {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("username","zhangsan");
        params.addBodyParameter("password","123456");
        Log.d("panzq","post .... uri"+params.getUri());
        Log.d("panzq","post .... uri_all"+params.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.d("panzq","success post.....");
                if (!TextUtils.isEmpty(result))
                {
                    Log.d("panzq","result = "+result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("panzq","onError post.....");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("panzq","onCancelled post.....");
            }

            @Override
            public void onFinished() {
                Log.d("panzq","onFinished post.....");
            }
        });
    }
    private void unloadFile(String url)
    {


        //String path = sdPath+"/test.gif";
        String path = Environment.getDataDirectory().getAbsolutePath()+"/test.gif";

        Log.d("panzq","sdpath ="+path);
       /* final ProgressDialog dia = new ProgressDialog(this);
        dia.setMessage("加载中....");
        dia.show();*/
        RequestParams params = new RequestParams(url);
        Log.d("panzq","url---"+url);
        params.setMultipart(true);
        params.addBodyParameter("File",new File(path),null,"testdown.gif");

        x.http().post(params,new Callback.ProgressCallback<ResponseEntity>()
        {
            @Override
            public void onSuccess(ResponseEntity result) {
                Log.d("panzq","unload ----- onSuccess "+result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("panzq","unload ----- onError"+isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("panzq","unload ----- onCancelled");
            }

            @Override
            public void onFinished() {
                Log.d("panzq","unload ----- onFinished");
                //dia.dismiss();
            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {

            }
        });
    }

    private void downloadFile(String url,String file)
    {
        verifyStoragePermissions(MainActivity.this);
        String path = sdPath+"/"+file;
        RequestParams params = new RequestParams(url);
        params.setSaveFilePath(path);
        params.setAutoRename(true);
        Log.d("panzq","sdpath ="+path);
        x.http().post(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
                Log.d("panzq","download ----onWaiting ----");
            }

            @Override
            public void onStarted() {
                Log.d("panzq","download ----onStarted ----");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                Log.d("panzq","download ----onLoading ----"+current);
            }

            @Override
            public void onSuccess(File result) {
                Log.d("panzq","download ----onSuccess ----"+result.getAbsolutePath());
                //apk下载完成后，调用系统的安装方法
                if (result.getAbsolutePath().endsWith("apk")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                    MainActivity.this.startActivity(intent);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("panzq","download ----onError ----"+isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("panzq","download ----onCancelled ----");
            }

            @Override
            public void onFinished() {
                Log.d("panzq","download ----onFinished ----");
            }
        });
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

}
