package com.example.administrator.weiraoeducationinstitution;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.activity.AboutActivity;
import com.example.administrator.weiraoeducationinstitution.activity.AssociationManageSystemActivity;
import com.example.administrator.weiraoeducationinstitution.activity.BankCardActivity;
import com.example.administrator.weiraoeducationinstitution.activity.FeedBackActivity;
import com.example.administrator.weiraoeducationinstitution.activity.OrganizationSettingActivity;
import com.example.administrator.weiraoeducationinstitution.activity.SettingActivity;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.fragment.MainFragment;
import com.example.administrator.weiraoeducationinstitution.fragment.ManagePageFragment;
import com.example.administrator.weiraoeducationinstitution.fragment.RankingPageFragment;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.StatusBarUtil;
import com.example.administrator.weiraoeducationinstitution.utils.ViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static String TAG = "MainActivity";
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.main_bottom_Re1)
    RelativeLayout mainBottomRe1;
    @Bind(R.id.main_bottom_Re2)
    RelativeLayout mainBottomRe2;
    @Bind(R.id.main_bottom_Re3)
    RelativeLayout mainBottomRe3;

    private TextView[] tvArray = new TextView[3];// 底部3个textview集合
    private ImageView[] ivArray = new ImageView[3];// 底部3个imageview集合
    private int selectedIndex;// 选择下标
    int currentIndex = 0;//当前显示的fragment

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private MenuItem mPreMenuItem;
    private long lastBackKeyDownTick = 0;
    public static final long MAX_DOUBLE_BACK_DURATION = 1500;

    private int mStatusBarColor;
    private int mAlpha = StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA;

    private View nav_headView;
    private ImageView nav_head;//侧滑机构头像
    private TextView nav_name, nav_brif;

    private LinearLayout headLL;
    private CircleImageView headCircle;
    private Button loginWeb;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mNavigationView.setNavigationItemSelectedListener(this);
//        if (savedInstanceState == null)
//            Log.i(TAG, "NULL");
//        else {
//            Log.i(TAG, "NOT NULL");
//        }
//    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {//当前抽屉是打开的，则关闭
            drawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }

//        if (mCurrentFragment instanceof WebViewFragment) {//如果当前的Fragment是WebViewFragment 则监听返回事件
//            WebViewFragment webViewFragment = (WebViewFragment) mCurrentFragment;
//            if (webViewFragment.canGoBack()) {
//                webViewFragment.goBack();
//                return;
//            }
//        }

        long currentTick = System.currentTimeMillis();
        if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
            MySnackbar.getmInstance().showMessage(drawerLayout, "再按一次退出");
            lastBackKeyDownTick = currentTick;
        } else {
            finish();
            System.exit(0);
        }
    }

    /**
     * 获取数据
     */
    protected void initData() {
        if (!BaseApplication.user_info.getImg().equals("")) {
            HttpUtils.getInstance().loadImage(BaseApplication.user_info.getImg(), nav_head);
            HttpUtils.getInstance().loadImage(BaseApplication.user_info.getImg(), headCircle);
        }
        nav_name.setText(BaseApplication.user_info.getName());
        nav_brif.setText(BaseApplication.user_info.getBrief());
    }

    /***
     * 用于在初始化View之前做一些事
     */
    @Override
    protected void init() {
        FinishUitl.clearActivity();
        FinishUitl.activityList.add(this);
        mFragmentManager = getSupportFragmentManager();
    }

    /**
     * 初始化layout的id
     *
     * @return
     */
    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }


    /**
     * google官方插件ButterKnife绑定控件id
     */
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        /* 底部标签栏文字 */
        tvArray[0] = (TextView) customFindViewById(R.id.main_bottom_txt1);
        tvArray[1] = (TextView) customFindViewById(R.id.main_bottom_txt2);
        tvArray[2] = (TextView) customFindViewById(R.id.main_bottom_txt3);
        /* 底部标签栏图标 */
        ivArray[0] = (ImageView) customFindViewById(R.id.main_bottom_img1);
        ivArray[1] = (ImageView) customFindViewById(R.id.main_bottom_img2);
        ivArray[2] = (ImageView) customFindViewById(R.id.main_bottom_img3);
        tvArray[0].setSelected(true);
        ivArray[0].setSelected(true);

        initDefaultFragment();
        mainBottomRe1.setOnClickListener(this);
        mainBottomRe2.setOnClickListener(this);
        mainBottomRe3.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        StatusBarUtil.setTranslucentForDrawerLayout(MainActivity.this, drawerLayout, 0);

        nav_headView = mNavigationView.getHeaderView(0);
        nav_head = (ImageView) nav_headView.findViewById(R.id.nav_main_head_imageView);
        nav_name = (TextView) nav_headView.findViewById(R.id.nav_main_organization_name);
        nav_brif = (TextView) nav_headView.findViewById(R.id.nav_main_content);
        headLL = customFindViewById(R.id.content_main_headLL);
        headCircle = customFindViewById(R.id.content_main_head);
        loginWeb = customFindViewById(R.id.content_main_webBtn);
        headCircle.setOnClickListener(this);
        loginWeb.setOnClickListener(this);
    }

    /**
     * 标题栏
     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * 防止没有找到fragment，默认一个
     */
    private void initDefaultFragment() {
        Log.i(TAG, "initDefaultFragment");
        mCurrentFragment = ViewUtils.createFragment(MainFragment.class);
        mFragmentManager.beginTransaction().add(R.id.frame_content, mCurrentFragment).commit();
        mPreMenuItem = mNavigationView.getMenu().getItem(0);
        mPreMenuItem.setChecked(false);
//        mNavigationView.getHeaderView(R.id.nav_main_head_imageView).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "asda", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /**
     * fragment切换
     */
    private void switchFragment(Class<?> clazz) {
        Fragment to = ViewUtils.createFragment(clazz);
        if (to.isAdded()) {
            Log.i(TAG, "Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commitAllowingStateLoss();
        } else {
            Log.i(TAG, "Not Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.frame_content, to).commitAllowingStateLoss();
        }
        mCurrentFragment = to;
    }

    /**
     * 抽屉item的Onclick
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (null != mPreMenuItem) {
            mPreMenuItem.setChecked(false);
        }
        switch (item.getItemId()) {
            case R.id.nav_organization_setting:
                startActivityWithoutExtras(OrganizationSettingActivity.class);
                break;
            case R.id.nav_myBankCard:
                startActivityWithoutExtras(BankCardActivity.class);
                break;
            case R.id.nav_manage:
                startActivityWithoutExtras(AssociationManageSystemActivity.class);
                break;
            case R.id.nav_setting:
                startActivityWithoutExtras(SettingActivity.class);
                break;
            case R.id.nav_feedback:
                startActivityWithoutExtras(FeedBackActivity.class);
                break;
            case R.id.nav_about:
                startActivityWithoutExtras(AboutActivity.class);
                break;
        }
        item.setChecked(true);
        drawerLayout.closeDrawer(Gravity.LEFT);
        mPreMenuItem = item;
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //首页
            case R.id.main_bottom_Re1:
                switchFragment(MainFragment.class);
                selectedIndex = 0;
                tvArray[currentIndex].setSelected(false);
                tvArray[selectedIndex].setSelected(true);
                ivArray[currentIndex].setSelected(false);
                ivArray[selectedIndex].setSelected(true);
                currentIndex = selectedIndex;
                headLL.setVisibility(View.VISIBLE);
                break;
            //管理
            case R.id.main_bottom_Re2:
                switchFragment(ManagePageFragment.class);
                selectedIndex = 1;
                tvArray[currentIndex].setSelected(false);
                tvArray[selectedIndex].setSelected(true);
                ivArray[currentIndex].setSelected(false);
                ivArray[selectedIndex].setSelected(true);
                currentIndex = selectedIndex;
                headLL.setVisibility(View.GONE);
                break;
            //排名
            case R.id.main_bottom_Re3:
                switchFragment(RankingPageFragment.class);
                selectedIndex = 2;
                tvArray[currentIndex].setSelected(false);
                tvArray[selectedIndex].setSelected(true);
                ivArray[currentIndex].setSelected(false);
                ivArray[selectedIndex].setSelected(true);
                currentIndex = selectedIndex;
                headLL.setVisibility(View.GONE);
                break;
            case R.id.content_main_head:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {//当前抽屉是打开的，则关闭
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.content_main_webBtn:
                //网页登录
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(Apis.WeiRao_WebHttp);
                intent.setData(content_url);
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
                break;
        }
    }


    @Override
    protected void setStatusBar() {
        mStatusBarColor = getResources().getColor(R.color.weirao_title_color);
        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer_layout), mStatusBarColor, mAlpha);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }

}
