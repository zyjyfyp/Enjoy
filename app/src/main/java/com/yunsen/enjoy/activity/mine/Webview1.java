package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.AppManager;
import com.yunsen.enjoy.http.URLConstants;


public class Webview1 extends AppCompatActivity implements OnClickListener {
    private WebView webview;
    private TextView tv_title;
    private LinearLayout common_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview1);
        try {

            webview = (WebView) findViewById(R.id.webview);
            tv_title = (TextView) findViewById(R.id.tv_title);

            common_back = (LinearLayout) findViewById(R.id.common_back);
            common_back.setOnClickListener(this);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.addJavascriptInterface(new JavascriptHandler(), "handler");

            //			//支持javascript
            //			webview.getSettings().setJavaScriptEnabled(true);
            //			// 设置可以支持缩放
            //			webview.getSettings().setSupportZoom(true);
            //			// 设置出现缩放工具
            //			webview.getSettings().setBuiltInZoomControls(true);
            //			//扩大比例的缩放
            //			webview.getSettings().setUseWideViewPort(true);
            //			//自适应屏幕
            //			webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
            //			webview.getSettings().setLoadWithOverviewMode(true);

            try {
                // 首页广告
                String link_url = getIntent().getStringExtra("link_url");
                System.out.println("link_url==============" + link_url);
                if (link_url != null) {
                    webview.loadUrl(link_url);
                }

                // 统一网页id
                String web_id = getIntent().getStringExtra("web_id");
                System.out.println("web_id==============" + web_id);
                if (web_id != null) {
                    webview.loadUrl(URLConstants.REALM_NAME_WEB
                            + "/mobile/news/conent-" + web_id + ".html");
                }

                // 养老商城
                String ylsc_id = getIntent().getStringExtra("ylsc_id");
                if (ylsc_id != null) {
                    webview.loadUrl(URLConstants.REALM_NAME_WEB + "/doc/conent-" + ylsc_id + ".html");
                }

                // 注册协议web网页
                String zhuce_id = getIntent().getStringExtra("zhuce_id");
                if (zhuce_id != null) {
                    webview.loadUrl(URLConstants.REALM_NAME_WEB
                            + "/mobile/news/conent-" + zhuce_id + ".html");
                }

                // 服务顾问协议web网页
                String chuanke_id = getIntent().getStringExtra("chuanke_id");
                if (chuanke_id != null) {
                    webview.loadUrl(URLConstants.REALM_NAME_WEB
                            + "/mobile/doc/show-" + chuanke_id + ".html");
                }

                // 养老银行的魅力web网页
                String ylyh_id = getIntent().getStringExtra("ylyh_id");
                if (ylyh_id != null) {
                    webview.loadUrl(URLConstants.REALM_NAME_WEB
                            + "/mobile/news/conent-" + ylyh_id + ".html");
                }

                // 服务顾问业务说明web网页
                String ck_id = getIntent().getStringExtra("ck_id");
                if (ck_id != null) {
                    webview.loadUrl(URLConstants.REALM_NAME_WEB
                            + "/mobile/news/conent-" + ck_id + ".html");
                }

                // 首页广告web网页
                // String gg_id = getIntent().getStringExtra("gg_id");
                // if (gg_id != null) {
                // int sygg_id = Integer.parseInt(gg_id);
                // if (sygg_id > 0) {
                // //
                // webview.loadUrl("http://183.62.138.31:1010/mobile/news/show-"+sygg_id+".html");
                // }
                // }

                // 聚头条web网页
                // String list = getIntent().getStringExtra("list");
                // if (list != null) {
                // int remen_id = Integer.parseInt(list);
                // if (remen_id > 0) {
                // //
                // webview.loadUrl("http://183.62.138.31:1010/mobile/news/show-"+remen_id+".html");
                // webview.loadUrl("http://zams.cn/mobile/news/conent-"+remen_id+".html");
                // }
                // }

                // http://zams.cn/mobile/news/show-5740.html
                // 新手攻略web网页
                String xsgl = getIntent().getStringExtra("list_xsgy");
                if (xsgl != null) {
                    int xsgy_id = Integer.parseInt(xsgl);
                    if (xsgy_id > 0) {
                        webview.loadUrl(URLConstants.REALM_NAME_WEB
                                + "/mobile/news/conent-" + xsgy_id + ".html");
                    }
                }

                // 商品详情web网页
                String spjs = getIntent().getStringExtra("spjs");
                System.out.println("spjs==============" + spjs);
                if (spjs != null) {
                    int spjs_id = Integer.parseInt(spjs);
                    if (spjs_id > 0) {
                        webview.loadUrl(URLConstants.REALM_NAME_WEB
                                + "/mobile/goods/conent-" + spjs_id + ".html");
                    }
                }

            } catch (Exception e) {

                e.printStackTrace();
            }

            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    tv_title.setText(view.getTitle());
                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
        }
    }

    class JavascriptHandler {
        @JavascriptInterface
        public void getContent(String htmlContent) {
        }
    }

    public void onPageFinished(WebView view, String url) {
        view.loadUrl("javascript:window.handler.getContent(document.body.innerHTML);");
    }
}
