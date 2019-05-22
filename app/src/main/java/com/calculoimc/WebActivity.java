package com.calculoimc;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

public class WebActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.miweb);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            //este método se llama cada vez que cambiamos de página
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("MIAPP", "Vistando ... " + url);
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl("https://es.wikipedia.org/wiki/%C3%8Dndice_de_masa_corporal");


    }
}
