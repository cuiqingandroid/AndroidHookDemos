package com.okex.net;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClientUtils {


    private static final int TIMEOUT = 20000;

    private static final String USER_AGENT = System.getProperty("http.agent");
    private static final String CONNECTION = "keep-alive";
    private static final String Accept_Encoding = "gzip";
    private static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    private static final Charset UTF8 = StandardCharsets.UTF_8;


    private static class Method {
        private static final String GET = "GET";
        private static final String POST = "POST";
    }


    public static void get(final String requestUrl, final HttpClientUtils.OnRequestCallBack callBack) {
        new Thread() {
            public void run() {
                getRequest(requestUrl, callBack);
            }
        }.start();
    }

    public static void post(final String requestUrl, final Map<String, String> postParams, final HttpClientUtils.OnRequestCallBack callBack) {
        new Thread() {
            public void run() {
                if (postParams != null && !postParams.isEmpty()) {
                    postRequest(requestUrl, postParams, callBack);
                } else {
                    getRequest(requestUrl, callBack);
                }
            }
        }.start();
    }

    private static void getRequest(String requestUrl, HttpClientUtils.OnRequestCallBack callBack) {
        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(Method.GET);
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Accept-Encoding", Accept_Encoding);
            connection.setRequestProperty("Connection", CONNECTION);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();
            int respCode = connection.getResponseCode();
            if (respCode == 200) {
                // 会隐式调用connect()
                inputStream = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                int readLen;
                byte[] bytes = new byte[1024];
                while ((readLen = inputStream.read(bytes)) != -1) {
                    baos.write(bytes, 0, readLen);
                }
                String backStr = baos.toString();

                try {
                    callBack.onSuccess(new JSONObject(backStr));
                } catch (JSONException e) {
                    callBack.onError(ErrorStatus.dataError());
                }
            } else {
                callBack.onError(ErrorStatus.networkError(respCode));
            }
        } catch (IOException e) {
            e.printStackTrace();
            callBack.onError(new ErrorStatus(ErrorStatus.IO_EXCEPTION, e.getMessage()));
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void postRequest(String requestUrl, final Map<String, String> postParams, HttpClientUtils.OnRequestCallBack callBack) {
        java.io.InputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(Method.POST);
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Accept-Language", Accept_Encoding);
            connection.setRequestProperty("Connection", CONNECTION);
            connection.setRequestProperty("Content-Type", CONTENT_TYPE_FORM);

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            String params = buildPostParams(postParams);
            connection.setRequestProperty("Content-Length", String.valueOf(params.getBytes(UTF8).length));
            // set  params three way  OutputStreamWriter
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), StandardCharsets.UTF_8);
            out.write(params);
            out.flush();
            connection.connect();

            int respCode = connection.getResponseCode();
            if (respCode == 200) {
                // 会隐式调用connect()
                inputStream = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                int readLen;
                byte[] bytes = new byte[1024];
                while ((readLen = inputStream.read(bytes)) != -1) {
                    baos.write(bytes, 0, readLen);
                }
                String backStr = baos.toString();

                try {
                    callBack.onSuccess(new JSONObject(backStr));
                } catch (JSONException e) {
                    callBack.onError(ErrorStatus.dataError());
                }
            } else {
                callBack.onError(ErrorStatus.networkError(respCode));
            }
        } catch (IOException e) {
            e.printStackTrace();
            callBack.onError(ErrorStatus.networkError(ErrorStatus.IO_EXCEPTION));
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String buildPostParams(Map<String, String> postParams){
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String > entry: postParams.entrySet()) {
            if (stringBuilder.length() > 0){
                stringBuilder.append('&');
            }
            stringBuilder.append(Uri.encode(entry.getKey()));
            stringBuilder.append('=');
            stringBuilder.append(Uri.encode(entry.getValue()));
        }
        //    for (int i = 0, size = encodedNames.size(); i < size; i++) {
        //      if (i > 0) buffer.writeByte('&');
        //      buffer.writeUtf8(encodedNames.get(i));
        //      buffer.writeByte('=');
        //      buffer.writeUtf8(encodedValues.get(i));
        //    }
        return stringBuilder.toString();
    }

    public interface OnRequestCallBack {
        void onSuccess(JSONObject jsonObject);
        void onError(ErrorStatus errorStatus);
    }

}