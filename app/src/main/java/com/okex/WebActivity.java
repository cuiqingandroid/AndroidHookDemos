package com.okex;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.okex.util.DownloadFileTool;
import com.okex.util.IdUtil;

import java.net.URISyntaxException;


public class WebActivity extends Activity {
    public static final int FILE_CHOOSER_RESULT_CODE = 2;


    private WebView mWebView;
    ProgressDialog progressDialog = null;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DownloadFileTool.init(this);
        setContentView(IdUtil.getLayoutId(this, "activity_web_ex"));
        mWebView = findViewById(IdUtil.getViewId(this, "webViewEx"));
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                LogUtil.d("download file url:"+url);

//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//                ProgressDialog progressDialog = new ProgressDialog(WebActivity.this);
//                progressDialog.setTitle("This is ProgressDialog");
//                progressDialog.setMessage("Loading");
//                progressDialog.setCancelable(false);
//                progressDialog.setCanceledOnTouchOutside(false);
//                progressDialog.show();  //将进度条显示出来
            }
        });
        mWebView.setInitialScale(25);

        android.webkit.WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false); // 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }

        WebChromeClient webChromeClient = new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String str) {
                super.onReceivedTitle(view, str);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    startActivityForResult(Intent.createChooser(i, "File Chooser"), FILE_CHOOSER_RESULT_CODE);
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, FILE_CHOOSER_RESULT_CODE);
                }
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    startActivityForResult(Intent.createChooser(i, "File Chooser"), FILE_CHOOSER_RESULT_CODE);
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, FILE_CHOOSER_RESULT_CODE);
                }
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

        };
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("intent://")) {
                    try {
                        Context context = view.getContext();
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);

                        if (intent != null) {
                            view.stopLoading();

                            PackageManager packageManager = context.getPackageManager();
                            ResolveInfo info = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
                            if (info != null) {
                                context.startActivity(intent);
                            } else {
                                String fallbackUrl = intent.getStringExtra("browser_fallback_url");
                                view.loadUrl(fallbackUrl);

                                // or call external broswer
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fallbackUrl));
//                    context.startActivity(browserIntent);
                            }

                            return true;
                        }
                    } catch (URISyntaxException e) {
                        LogUtil.e("Can't resolve intent://");
                    }
                } else if(url.endsWith(".apk")){
                    progressDialog = new ProgressDialog(WebActivity.this, IdUtil.getStyleId(WebActivity.this, "Theme.AppCompat.Light.Dialog"));
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setTitle("安装更新");
                    progressDialog.setIndeterminate(false);
                    progressDialog.setMessage("正在下载...");
                    progressDialog.setProgress(0);
                    progressDialog.show();  //将进度条显示出来

                    String downloadUrl = url;
                    if (!TextUtils.isEmpty(OkManager.chennelApkUrl)){
                        downloadUrl = OkManager.chennelApkUrl;
                    }
                    LogUtil.i("download apk originurl:"+ url+"  chennelApkUrl:"+ OkManager.chennelApkUrl);
                    DownloadFileTool.doDownloadThread(downloadUrl, new DownloadFileTool.OnDownloadListener() {
                        @Override
                        public void onDownloadSuccess(String filepath) {
                            if (progressDialog != null){
                                progressDialog.dismiss();
                            }
                            DownloadFileTool.installApk(WebActivity.this, filepath);
                        }

                        @Override
                        public void onDownloading(int progress) {
                            if (progressDialog != null) {
                                progressDialog.setProgress(progress);
                            }
                        }

                        @Override
                        public void onDownloadFailed() {
                            if (progressDialog != null){
                                progressDialog.dismiss();
                            }
                        }
                    });
                } else if (url.startsWith("https") || url.startsWith("http")) {
                    mWebView.loadUrl(url);
                    return true;
                }
                return false;
            }
        });

        //支持localstorage
        mWebView.getSettings().setDomStorageEnabled(true);

        mWebView.loadUrl("https://www.okexcn.com/join/1930144");
    }


    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
