package cn.weirao.wheelview.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.OrganizationSettingActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.weirao.wheelview.OnWheelChangedListener;
import cn.weirao.wheelview.WheelView;
import cn.weirao.wheelview.adapters.ArrayWheelAdapter;


public class LocationActivity extends Location_BaseActivity implements OnClickListener, OnWheelChangedListener, OnGetGeoCoderResultListener {
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private ImageView back;
    private Button mBtnConfirm;
    private TextView addr, addrDetail;
    private LinearLayout provinceLL, bottomLL, current_addrLL;
    private String the_location_addr;
    //定位参数
    private GeoCoder mSearch = null;
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private boolean isFirstLoc = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_location);
        setUpViews();
        setUpListener();
        setUpData();
        getLocation();
    }


    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
        addr = (TextView) findViewById(R.id.id_locaation_addr);
        current_addrLL = (LinearLayout) findViewById(R.id.id_locaation_current_addrLL);
        provinceLL = (LinearLayout) findViewById(R.id.id_loaction_provinceLL);
        bottomLL = (LinearLayout) findViewById(R.id.id_locaation_bottomLL);
        addrDetail = (TextView) findViewById(R.id.id_location_addrDetail);
        back = (ImageView) findViewById(R.id.id_loaction_back);
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        current_addrLL.setOnClickListener(this);
    }

    private void setUpListener() {
        // ���change�¼�
        mViewProvince.addChangingListener(this);
        // ���change�¼�
        mViewCity.addChangingListener(this);
        // ���change�¼�
        mViewDistrict.addChangingListener(this);
        // ���onclick�¼�
        mBtnConfirm.setOnClickListener(this);
        provinceLL.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<>(LocationActivity.this, mProvinceDatas));
        // ���ÿɼ���Ŀ����
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * ���ݵ�ǰ���У�������WheelView����Ϣ
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     * ���ݵ�ǰ��ʡ��������WheelView����Ϣ
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                showSelectedResult();
                break;
            case R.id.id_loaction_provinceLL:
                bottomLL.setVisibility(View.VISIBLE);
                break;
            case R.id.id_loaction_back:
                finish();
                break;
            case R.id.id_locaation_current_addrLL:
                changeAddr(BaseApplication.user_info.getId(), "1", the_location_addr, the_location_addr);
                break;
            default:
                break;
        }
    }

    private void showSelectedResult() {
//        Toast.makeText(LocationActivity.this, "��ǰѡ��:" + mCurrentProviceName + "," + mCurrentCityName + ","
//                + mCurrentDistrictName + "," + mCurrentZipCode, Toast.LENGTH_SHORT).show();
        addrDetail.setText(mCurrentProviceName + "-" + mCurrentCityName + "-"
                + mCurrentDistrictName);
        bottomLL.setVisibility(View.INVISIBLE);
        changeAddr(BaseApplication.user_info.getId(), "1",  mCurrentProviceName + mCurrentCityName + mCurrentDistrictName, mCurrentProviceName + mCurrentCityName + mCurrentDistrictName);
    }

    class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;
            if (isFirstLoc) {
                isFirstLoc = false;
            }

            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            if (longitude > 0 && latitude > 0) {
                LatLng ptCenter = new LatLng(latitude, longitude);
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(ptCenter));
            }
            mLocClient.stop();
        }

        public void onReceivePoi(BDLocation poiLocation) {

        }
    }

    private void getLocation() {
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(5000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult arg0) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
        String city = result.getAddressDetail().city;
        if (result.getAddressDetail().city != null) {
            Log.e("onLocationChanged", "city: " + city);
            addr.setText(result.getAddress());
            the_location_addr = result.getAddress();
        } else {
            //定位失败
        }
    }

    private void changeAddr(String id, String type, String content, final String addr) {
        String reqUrl = Apis.GetEditInfo(id, type, content);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        OrganizationSettingActivity.addr.setText(addr);
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
                        BaseApplication.user_info.setLocation(addr);
                        finish();
                    } else {
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }


    @Override
    protected void onStop() {
        mLocClient.stop();
        super.onStop();
    }
}
