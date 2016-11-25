package com.example.administrator.weiraoeducationinstitution.activity.base;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.fragment.base.WebViewFragment;
import com.example.administrator.weiraoeducationinstitution.utils.ClipboardUtils;
import com.example.administrator.weiraoeducationinstitution.utils.SystemShareUtils;


/**
 * Created by _SOLID
 * Date:2016/4/19
 * Time:13:03
 */
public class WebViewActivity extends BaseActivity {

//    private static String TAG = "WebViewActivity";
//    public static String WEB_URL = "webViewUrl";
//    public static String TITLE = "webViewTitle";
//
//    private Toolbar mToolbar;
//    private String mUrl;
//    private static String mTitle;
//
//    private FragmentManager mFragmentManager;
//    private WebViewFragment mWebViewFragment;
//
//
    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_webview;
    }
//
//    @Override
//    protected void init() {
//        mUrl = getIntent().getExtras().getString(WEB_URL);
//        mTitle = getIntent().getExtras().getString(TITLE);
//        mFragmentManager = getSupportFragmentManager();
//    }
//

    @Override
    protected void initView() {

    }

//    @Override
//    protected void initView() {
//        //设置Toolbar
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setTitle(mTitle);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
//        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头
//        setSupportActionBar(mToolbar);
//        mToolbar.setNavigationIcon(R.mipmap.ic_back);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//        dynamicAddSkinEnableView(mToolbar, "background", R.color.colorPrimary);
//
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_webview_toolbar, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_copy) {
//            ClipboardUtils.setText(this, mUrl);
//            Snackbar.make(mToolbar, "已复制到剪切板", Snackbar.LENGTH_SHORT).show();
//            return true;
//        } else if (id == R.id.action_share) {
//            SystemShareUtils.shareText(this, "【" + mTitle + "】链接:" + mUrl);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    protected void initData() {
//        mWebViewFragment = new WebViewFragment() {
//            @Override
//            protected String getLoadUrl() {
//                return mUrl;
//            }
//        };
//        mFragmentManager.beginTransaction().replace(R.id.fl_content, mWebViewFragment).commit();
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mWebViewFragment.canGoBack()) {
//            mWebViewFragment.goBack();
//        } else {
//            finish();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        mTitle=null;
//        super.onDestroy();
//    }
}
