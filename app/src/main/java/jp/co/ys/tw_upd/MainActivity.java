package jp.co.ys.tw_upd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    WebView  myWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //レイアウトで指定したWebViewのIDを指定する。
        myWebView = (WebView)findViewById(R.id.webView1);

        //リンクをタップしたときに標準ブラウザを起動させない
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        //最初にアップデートのページを表示する。
        myWebView.loadUrl("http://XXXXXXXXX");

        //jacascriptを許可する
        myWebView.getSettings().setJavaScriptEnabled(true);

        // プログレスバーの表示
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                View pBarWrapper = findViewById(R.id.progress_bar_wrapper);
                ProgressBar pBar = (ProgressBar) findViewById(R.id.progress_bar);
                pBar.setProgress(newProgress);

                if (newProgress == 100) {
                    pBarWrapper.setVisibility(View.GONE);
                } else {
                    pBarWrapper.setVisibility(View.VISIBLE);
                }
            }
        });

        // ファイルのダウンロード
        myWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype, long contentLength) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType(mimetype);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    public void onClickReloadBtn(View v) {
        myWebView.reload();
    }
}
