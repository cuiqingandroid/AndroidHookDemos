package com.okex.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.okex.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载工具，基于安卓系统网络模块
 */
public class DownloadFileTool {

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String filepath);

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

    private static final int MSG_SUCCESS = 1;
    private static final int MSG_PROGRESS = 2;
    private static final int MSG_FAIL = 3;

    private static Handler sMainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    // 获得存储卡的路径
    private static File filePath = null;


    public static void init(Context context){
        filePath = context.getExternalCacheDir();
    }

    public static void installApk(Context context, String apkPath) {
        if (apkPath.endsWith(".apk")) {
            Intent install = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//增加读权限
                Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".okexfileProvider", new File(apkPath));
                install.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                install.setDataAndType(Uri.parse("file://" + apkPath), "application/vnd.android.package-archive");
            }
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(install);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void doDownloadThread(String url, OnDownloadListener listener) {
        File file = getFile(url);
        if (file.exists()) {
            if (listener != null) {
                listener.onDownloadSuccess(file.getAbsolutePath());
            }
        } else {
            new Thread(new DownloadFileThread(url, listener)).start();
        }
    }


    private static File getTmpFile(String url) {
        return new File(filePath, MD5Tools.toMD5(url)+".apk.tmp");
    }

    private static File getFile(String url){
        return new File(filePath, MD5Tools.toMD5(url)+".apk");
    }

    private static class DownloadFileThread implements Runnable {

        private String mUrl;
        private OnDownloadListener mListener;

        public DownloadFileThread(String url, OnDownloadListener listener) {
            mUrl = url;
            mListener = listener;
        }

        @Override
        public void run() {
            FileOutputStream fileOutputStream = null;
            InputStream inputStream = null;
            //
            try {
                URL url = new URL(mUrl);
                //获取连接
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(30000);
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Charset", "UTF-8");
                connection.setDoOutput(false);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                //打开连接
                connection.connect();
                //获取内容长度
                int contentLength = connection.getContentLength();

                if (filePath ==null){
                    if (mListener != null) {
                        sMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mListener.onDownloadFailed();
                            }
                        });
                    }
                    return;
                }
                //输入流
                inputStream = connection.getInputStream();

                final File saveFilePath = getTmpFile(mUrl);

                //输出流
                fileOutputStream = new FileOutputStream(saveFilePath);

                byte[] bytes = new byte[1024];
                // int  index=0;
                long totalReaded = 0;
                int temp_Len;
                while ((temp_Len = inputStream.read(bytes)) != -1) {
                    // bytes[index]= (byte) temp_Len;
                    // index++;
                    totalReaded += temp_Len;
                    final int progress = (int) (totalReaded * 100 / contentLength);
                    LogUtil.d("download run: totalReaded:" + totalReaded + "  progress:" + progress);
                    fileOutputStream.write(bytes, 0, temp_Len);
                    if (mListener != null) {
//                        mListener.onDownloading(progress);
//                        Message message = sMainHandler.obtainMessage();
//                        message.arg1 = progress;
//                        message.what = MSG_PROGRESS;
//                        message.obj = mListener;
//                        sMainHandler.sendMessage(message);

                        sMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mListener.onDownloading(progress);
                            }
                        });
                    }
                }

                // 下载成功，修改文件名
                boolean renamed = saveFilePath.renameTo(getFile(mUrl));
                if (renamed){
                    if (mListener != null) {
                        sMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mListener.onDownloadSuccess(saveFilePath.getAbsolutePath());
                            }
                        });
                    }
                } else {
                    if (mListener != null) {
                        sMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mListener.onDownloadFailed();
                            }
                        });
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                if (mListener != null) {
                    sMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onDownloadFailed();
                        }
                    });
                }
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
