package com.sbs.sbsattend;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WatchTableActivity extends Activity {
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);
		init();
	}

	private void init() {
		webView = (WebView) findViewById(R.id.webView);
		// WebView加载web资源
		webView.loadUrl("http://192.168.50.88:8890/jsjwork/");
		//设置WebView属性，能够执行Javascript脚本 
		webView.getSettings().setJavaScriptEnabled(true);
		//设置加载进来的页面自适应手机屏幕 
		webView.getSettings().setUseWideViewPort(true); 
		webView.getSettings().setLoadWithOverviewMode(true); 
		//优先使用缓存
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});
	}
}
