package com.example.administrator.weiraoeducationinstitution.activity;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.course_pop.Association_PopAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AssociationBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.photo.util.Bimp;
import com.example.administrator.weiraoeducationinstitution.photo.util.ImageItem;
import com.example.administrator.weiraoeducationinstitution.utils.FileUtils;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import timeselector.Utils.TextUtil;


public class PhotoActivity extends BaseActivity implements View.OnClickListener {
    public static final int SET_DYnamic_GALLERY = 2;// 从相册中选择
    private static final int TAKE_PICTURE = 0x000001;
    public static final String COVER_IMG = "weirao_dynamic_cover";
    public static final String IMG1 = "weirao_dynamic_img1";
    public static final String IMG2 = "weirao_dynamic_img2";
    public static final String IMG3 = "weirao_dynamic_img3";
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private View parentView;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private ImageView coverimg, img1, img2, img3;
    private ImageView r1, r2, r3, r4;
    private static String index = null;
    private String coverPath, img1Path, img2Path, img3Path;
    private Button up, add, change;

    private EditText title, intro1, intro2, intro3, intro4;
    private LinearLayout type;

    private PopupWindow popWindow;// 弹窗
    private ListView pop_list;// 弹窗list
    private Association_PopAdapter mAdapter;
    private TextView association_name;
    private String association_Id;

    private String isManageSysOrTrPlan = null;//判断是管理制度还是课程规划

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_photo;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
        parentView = getLayoutInflater().inflate(R.layout.activity_photo, null);
        mAdapter = new Association_PopAdapter(PhotoActivity.this, new ArrayList<AssociationBean>());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isManageSysOrTrPlan = bundle.getString("bundle_value");
        }

    }

    @Override
    protected void initData() {
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//位于Title的颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);//扩张的颜色
        mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.weirao_title_color));
        getAssociation();
        if (isManageSysOrTrPlan != null) {
            if (isManageSysOrTrPlan.equals("this_is_manage_sys")) {
//            mCollapsingToolbarLayout.setCo
                intro1.setVisibility(View.GONE);
            } else if (isManageSysOrTrPlan.equals("this_is_training_plan")) {
                title.setVisibility(View.GONE);
                intro1.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void initView() {
        mToolbar = customFindViewById(R.id.photo_dynamic_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mCollapsingToolbarLayout = customFindViewById(R.id.photo_dynamic_collapsing_toolbar_layout);
        if (isManageSysOrTrPlan == null) {
            mCollapsingToolbarLayout.setTitle("动态编辑");
        } else {
            if (isManageSysOrTrPlan.equals("this_is_manage_sys")) {
                mCollapsingToolbarLayout.setTitle("管理制度编辑");
            } else if (isManageSysOrTrPlan.equals("this_is_training_plan")) {
                mCollapsingToolbarLayout.setTitle("课程计划编辑");
            }
        }
        pop = new PopupWindow(PhotoActivity.this);
        View view = getLayoutInflater().inflate(R.layout.plugin_item_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        coverimg = customFindViewById(R.id.photo_dynamic_coverimg);
        title = customFindViewById(R.id.photo_dynamic_title);
        intro1 = customFindViewById(R.id.photo_dynamic_intro1);
        intro2 = customFindViewById(R.id.photo_dynamic_intro2);
        intro3 = customFindViewById(R.id.photo_dynamic_intro3);
        intro4 = customFindViewById(R.id.photo_dynamic_intro4);
        type = customFindViewById(R.id.photo_dynamic_typeLL);
        association_name = customFindViewById(R.id.photo_dynamic_type_text);

        img1 = customFindViewById(R.id.photo_dynamic_img1);
        img2 = customFindViewById(R.id.photo_dynamic_img2);
        img3 = customFindViewById(R.id.photo_dynamic_img3);
        up = customFindViewById(R.id.photo_dynamic_up);
        r1 = customFindViewById(R.id.photo_dynamic_re);
        r2 = customFindViewById(R.id.photo_dynamic_re1);
        r3 = customFindViewById(R.id.photo_dynamic_re2);
        r4 = customFindViewById(R.id.photo_dynamic_re3);
        coverimg.setOnClickListener(this);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        up.setOnClickListener(this);
        type.setOnClickListener(this);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("the_photo_index", index);
//                startActivityWithExtras(AlbumActivity.class, bundle);
                // 激活系统图库，选择一张图片
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, SET_DYnamic_GALLERY);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }


    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
//        if (Bimp.tempSelectBitmap != null || Bimp.tempSelectBitmap.size() != 0) {
//            if (Bimp.tempSelectBitmap.get(0) != null) {
//                Drawable drawable1 = new BitmapDrawable(Bimp.tempSelectBitmap.get(0).getBitmap());
//                r1.setBackgroundDrawable(drawable1);
//                coverimg.setVisibility(View.INVISIBLE);
//            } else if (Bimp.tempSelectBitmap.get(1) != null) {
//                Drawable drawable2 = new BitmapDrawable(Bimp.tempSelectBitmap.get(1).getBitmap());
//                r2.setBackgroundDrawable(drawable2);
//                img1.setVisibility(View.INVISIBLE);
//            } else if (Bimp.tempSelectBitmap.get(2) != null) {
//                Drawable drawable3 = new BitmapDrawable(Bimp.tempSelectBitmap.get(2).getBitmap());
//                r3.setBackgroundDrawable(drawable3);
//                img2.setVisibility(View.INVISIBLE);
//            }
//        }
        super.onRestart();
    }


    public void photo() {
        String state = Environment.getExternalStorageState(); // 判断是否存在sd卡
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(openCameraIntent, TAKE_PICTURE);
        } else {
            MySnackbar.getmInstance().showMessage(r1, "请检查手机SD卡是否存在");
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //拍照
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    if (index.equals("0")) {
                        Bitmap bm = (Bitmap) data.getExtras().get("data");
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setBitmap(bm);
                        Drawable drawable1 = new BitmapDrawable(takePhoto.getBitmap());
                        r1.setImageDrawable(drawable1);
                        coverimg.setVisibility(View.INVISIBLE);
                        String fileName = COVER_IMG;
                        FileUtils.saveBitmap(bm, fileName);
                        File f = new File(FileUtils.SDPATH, COVER_IMG + ".png");
                        if (f.exists()) {
                            String path = f.getAbsolutePath();
                            uploadImage(path);
                        }
                    } else if (index.equals("1")) {
                        Bitmap bm = (Bitmap) data.getExtras().get("data");
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setBitmap(bm);
                        Drawable drawable2 = new BitmapDrawable(takePhoto.getBitmap());
                        r2.setImageDrawable(drawable2);
                        img1.setVisibility(View.INVISIBLE);
                        String fileName = IMG1;
                        FileUtils.saveBitmap(bm, fileName);
                        File f = new File(FileUtils.SDPATH, IMG1 + ".png");
                        if (f.exists()) {
                            String path = f.getAbsolutePath();
                            uploadImage(path);
                        }
                    } else if (index.equals("2")) {
//                        String fileName = String.valueOf(System.currentTimeMillis());
                        Bitmap bm = (Bitmap) data.getExtras().get("data");
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setBitmap(bm);
                        Drawable drawable3 = new BitmapDrawable(takePhoto.getBitmap());
                        r3.setImageDrawable(drawable3);
                        img2.setVisibility(View.INVISIBLE);
                        String fileName = IMG2;
                        FileUtils.saveBitmap(bm, fileName);
//                        FileUtils.saveBitmap(bm, fileName);
//                        ImageItem takePhoto = new ImageItem();
//                        takePhoto.setBitmap(bm);
//                        Bimp.tempSelectBitmap.add(2, takePhoto);
                        File f = new File(FileUtils.SDPATH, IMG2 + ".png");
                        if (f.exists()) {
                            String path = f.getAbsolutePath();
                            uploadImage(path);
                        }
                    } else if (index.equals("3")) {
                        Bitmap bm = (Bitmap) data.getExtras().get("data");
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setBitmap(bm);
                        Drawable drawable3 = new BitmapDrawable(takePhoto.getBitmap());
                        r4.setImageDrawable(drawable3);
                        img3.setVisibility(View.INVISIBLE);
                        String fileName = IMG3;
                        FileUtils.saveBitmap(bm, fileName);
                        File f = new File(FileUtils.SDPATH, IMG3 + ".png");
                        if (f.exists()) {
                            String path = f.getAbsolutePath();
                            uploadImage(path);
                        }
                    }
                }
                break;
            // 图库
            case SET_DYnamic_GALLERY:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Bitmap bitmap = null;
                        String picturePath = null;
                        Uri selectedImage = data.getData();
                        try {
                            String[] filePathColumns = {MediaStore.Images.Media.DATA};
                            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                            c.moveToFirst();
                            int columnIndex = c.getColumnIndex(filePathColumns[0]);
                            picturePath = c.getString(columnIndex);
                            c.close();
                            bitmap = Bimp.revitionImageSize(picturePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (index.equals("0")) {
                            ImageItem takePhoto = new ImageItem();
                            takePhoto.setBitmap(bitmap);
                            Drawable drawable1 = new BitmapDrawable(takePhoto.getBitmap());
                            r1.setBackgroundDrawable(drawable1);
                            coverimg.setVisibility(View.INVISIBLE);
                            uploadImage(picturePath);
                        } else if (index.equals("1")) {
                            ImageItem takePhoto = new ImageItem();
                            takePhoto.setBitmap(bitmap);
                            Drawable drawable2 = new BitmapDrawable(takePhoto.getBitmap());
                            r2.setBackgroundDrawable(drawable2);
                            img1.setVisibility(View.INVISIBLE);
                            uploadImage(picturePath);
                        } else if (index.equals("2")) {
                            ImageItem takePhoto = new ImageItem();
                            takePhoto.setBitmap(bitmap);
                            Drawable drawable3 = new BitmapDrawable(takePhoto.getBitmap());
                            r3.setBackgroundDrawable(drawable3);
                            img2.setVisibility(View.INVISIBLE);
                            uploadImage(picturePath);
                        } else if (index.equals("3")) {
                            ImageItem takePhoto = new ImageItem();
                            takePhoto.setBitmap(bitmap);
                            Drawable drawable3 = new BitmapDrawable(takePhoto.getBitmap());
                            r4.setBackgroundDrawable(drawable3);
                            img3.setVisibility(View.INVISIBLE);
                            uploadImage(picturePath);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_dynamic_coverimg:
                dialogPop();
                index = "0";
                break;
            case R.id.photo_dynamic_img1:
                dialogPop();
                index = "1";
                break;
            case R.id.photo_dynamic_img2:
                dialogPop();
                index = "2";
                break;
            case R.id.photo_dynamic_img3:
                dialogPop();
                index = "3";
                break;
            case R.id.photo_dynamic_up:
                if (isManageSysOrTrPlan == null) {  //动态发表
                    if (TextUtil.isEmpty(title.getText().toString())) {
                        MySnackbar.getmInstance().showMessage(title, "标题不能为空");
                        return;
                    } else if (association_Id == null) {
                        MySnackbar.getmInstance().showMessage(title, "请选择社团");
                        return;
                    }
                    publishDynamic(title.getText().toString(), association_Id, coverPath, intro1.getText().toString(), getHtmlStr(img1Path, intro2.getText().toString()) +
                            getHtmlStr(img2Path, intro3.getText().toString()) + getHtmlStr(img3Path, intro4.getText().toString()));
                } else {
                    if (isManageSysOrTrPlan.equals("this_is_manage_sys")) {//管理制度
                        if (TextUtil.isEmpty(title.getText().toString())) {
                            MySnackbar.getmInstance().showMessage(title, "标题不能为空");
                            return;
                        } else if (association_Id == null) {
                            MySnackbar.getmInstance().showMessage(title, "请选择社团");
                            return;
                        }
                        publishManage(association_Id, title.getText().toString(), "", getHtmlStr(img1Path, intro2.getText().toString()) +
                                getHtmlStr(img2Path, intro3.getText().toString()) + getHtmlStr(img3Path, intro4.getText().toString()));

                    } else if (isManageSysOrTrPlan.equals("this_is_training_plan")) {//课程规划
                        if (association_Id == null) {
                            MySnackbar.getmInstance().showMessage(title, "请选择社团");
                            return;
                        }
                        publishCoursePlan(association_Id, getHtmlStr(img1Path, intro2.getText().toString()) +
                                getHtmlStr(img2Path, intro3.getText().toString()) + getHtmlStr(img3Path, intro4.getText().toString()));
                    }
                }
                break;
            case R.id.photo_dynamic_typeLL:
                showPopwindown();
                break;

        }
    }

    private void dialogPop() {
        ll_popup.startAnimation(AnimationUtils.loadAnimation(PhotoActivity.this, R.anim.photo_translate_in));
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 转换为html5
     */
    private String getHtmlStr(String imgpath, String intro) {
        StringBuilder htmlStr = new StringBuilder();
        htmlStr.append("<p>");
        htmlStr.append("<img alt=\"img.jpg\" ");
        htmlStr.append("src=\"" + imgpath + "\"title=\"img.jpg\"/>");
        htmlStr.append("</p>");
        htmlStr.append("<p>");
        htmlStr.append(intro);
        htmlStr.append("</p>");
        return htmlStr.toString();
//                <p><img alt="img.jpg" src="http://www.weiraoedu.com/public/upload/images/admin/1/201605/26/1464233433.jpg"
//                title="img.jpg"/></p><p>瓦尔ware娃儿娃儿we阿尔人</p>

    }

    /**
     * 上传动态图片
     */
    private void uploadImage(final String imagepath) {
        String reqUrl = Apis.WeiRao_UpImg;
        RequestParams params = new RequestParams();
        if (imagepath.contains(".png")) {
            params.addBodyParameter("pic", new File(imagepath), "image/png");
        } else if (imagepath.contains(".jpg")) {
            params.addBodyParameter("pic", new File(imagepath), "image/jpg");
        }
        com.lidroid.xutils.HttpUtils httpUtils = new com.lidroid.xutils.HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, reqUrl, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        Log.e("db", "上传开始");
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        if (isUploading) {
                            Log.e("db", "upload: " + current + "/" + total);
                        } else {
                            Log.e("db", "reply: " + current + "/" + total);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.e("db", "上传失敗" + error.getExceptionCode() + ":"
                                + msg);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.e("db", "responseInfo=====" + responseInfo.result);
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            if (jsonObject.getString("code").equals("0")) {
                                if (index.equals("0")) {
                                    coverPath = jsonObject.getString("data");
                                } else if (index.equals("1")) {
                                    img1Path = jsonObject.getString("data");
                                } else if (index.equals("2")) {
                                    img2Path = jsonObject.getString("data");
                                } else if (index.equals("3")) {
                                    img3Path = jsonObject.getString("data");
                                }
                            } else {
                                ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 发表动态
     */
    private void publishDynamic(String name, String type, String img, String brief, String detail) {
        String reqUrl = Apis.GetAddNews(name, type, img, brief, detail);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
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

    /**
     * 发表管理制度
     */
    private void publishManage(String groupId, String name, String img, String detail) {
        String reqUrl = Apis.GetAddRule(groupId, name, img, detail);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
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

    /**
     * 发表课程规划
     */
    private void publishCoursePlan(String groupId, String content) {
        String reqUrl = Apis.GetAddTrain(groupId, content);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
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

    private void showPopwindown() {
        if (popWindow == null) {
            View view = getLayoutInflater().inflate(
                    R.layout.pop_exam_menue, null);
            popWindow = new PopupWindow(view,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            pop_list = (ListView) view
                    .findViewById(R.id.pop_exam_list);
            pop_list.setAdapter(mAdapter);
            pop_list.setItemsCanFocus(false);
            pop_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            pop_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popWindow.dismiss();
                    association_Id = mAdapter.getItemAssociationId(position);
                    association_name.setText(mAdapter.getItemName(position));
                }
            });
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popWindow.setBackgroundDrawable(cd);
        popWindow.setOutsideTouchable(true);
        popWindow.setFocusable(true);
        popWindow.showAtLocation(customFindViewById(R.id.photo_dynamic_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindow.setAnimationStyle(R.style.pop_anim);
        popWindow.update();
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                popWindow = null;
            }
        });

    }

    /**
     * 获取社团列表
     */
    private void getAssociation() {
        String reqUrl = Apis.GetGetGroups(BaseApplication.user_info.getId());
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    List<AssociationBean> list = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<List<AssociationBean>>() {
                            }.getType());
                    mAdapter.changeManagerData(list);
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
    protected void onDestroy() {
        index = null;
        File f1 = new File(FileUtils.SDPATH, COVER_IMG + ".png");
        File f2 = new File(FileUtils.SDPATH, IMG1 + ".png");
        File f3 = new File(FileUtils.SDPATH, IMG2 + ".png");
        File f4 = new File(FileUtils.SDPATH, IMG3 + ".png");
        if (f1.exists()) {
            f1.delete();
        } else if (f2.exists()) {
            f2.delete();
        } else if (f3.exists()) {
            f3.delete();
        } else if (f4.exists()) {
            f4.delete();
        }
        super.onDestroy();
    }


}
