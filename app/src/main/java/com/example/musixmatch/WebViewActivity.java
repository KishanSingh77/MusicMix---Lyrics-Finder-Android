package com.example.musixmatch;


import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView=findViewById(R.id.webView);
        String url= (String) getIntent().getExtras().getCharSequence(MainActivity.URL_TAG );
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(url);

    }
}
