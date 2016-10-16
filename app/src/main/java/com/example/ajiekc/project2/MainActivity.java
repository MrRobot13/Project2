package com.example.ajiekc.project2;

import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    int stage = 0;
    WebView webView;
    Button button;
    Button button2;
    String user = "",password = "";
    Context context;

    public void onClick(View view) {
        // webView.loadUrl("https://sts.urfu.ru/adfs/OAuth2/authorize?resource=https%3A%2F%2Fistudent.urfu.ru&type=web_server&client_id=https%3A%2F%2Fistudent.urfu.ru&redirect_uri=https%3A%2F%2Fistudent.urfu.ru%3Fauth&response_type=code&scope=");
        //webView.loadUrl("https://istudent.urfu.ru/s/http-urfu-ru-ru-students-study-brs/");
        webView.loadUrl("https://istudent.urfu.ru/s/schedule/");
    }


    public void onClick2(View view) {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if(appDir.exists()){

            String[] children = appDir.list();
            for(String s : children){
                if(s.equals("app_webview")){
                    deleteDir(new File(appDir, s));
                    Log.i("test", "File /data/data/APP_PACKAGE/" + s +" DELETED");
                }
            }
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    class MyJavaScriptInterface {

       /* @JavascriptInterface
        public void GetBRS(String html) throws IOException {
            Parsing p = new Parsing(html,context);
            if(p.ParsBRS()) p.LoadData();
        }*/

        @JavascriptInterface
        public void GetTimetable(String html) throws IOException {
            Parsing p = new Parsing(html,context);

            ArrayList<Timetable_Model> list = p.ParsTimetable();

            ArrayList<String> lol = list.get(0).timetable;
            String pzdc = lol.get(0);
            Log.e("test",pzdc.split("\n")[5]);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        webView = (WebView)findViewById(R.id.webView);
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);

        WebSettings webSettings =  webView.getSettings();

        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("test",view.getUrl());
                switch (stage)
                {
                    case 0:
                        if (view.getUrl().contains("sts.urfu.ru"))
                        {
                            user = "Andrei.Kudriavtsev@at.urfu.ru";
                            password = "8z4rjvVZ";

                            view.loadUrl("javascript:(function() { document.getElementById('userNameInput').value = '" + user + "'; })()");
                            view.loadUrl("javascript:(function() { document.getElementById('passwordInput').value = '" + password + "'; })()");
                            //view.loadUrl("javascript:(function() { document.getElementById('submitButton').click(); })()");
                            view.loadUrl("javascript: document.forms['loginForm'].submit()");
                            stage++;
                        }
                        else if(view.getUrl().contains("https://istudent.urfu.ru/s/schedule/"))
                        {
                            view.loadUrl("javascript:window.HTMLOUT.GetTimetable(document.getElementsByTagName('body')[0].innerHTML);");
                            stage+=2;
                        }
                        else Log.e("test","Error!");
                        break;
                    case 1:
                        view.loadUrl("javascript:window.HTMLOUT.GetTimetable(document.getElementsByTagName('body')[0].innerHTML);");
                        stage++;
                        break;
                    default:break;

                }
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

        });
    }

}