package com.toufiq.virtualCourseAssistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class ActivityShowLecture extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lecture);

        WebView webView= findViewById(R.id.webviewid);
        String fileUrl = "https://towfiqalahesohel.com/files/2019/12/L-1.pdf";
        String finalUrl = "https://docs.google.com/gview?embedded=true&url=" + fileUrl;
        webView.getSettings().setSupportZoom(true);
        //webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(finalUrl);
    }
}
