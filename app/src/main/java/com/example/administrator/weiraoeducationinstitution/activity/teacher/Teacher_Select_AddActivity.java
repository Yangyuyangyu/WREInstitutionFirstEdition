package com.example.administrator.weiraoeducationinstitution.activity.teacher;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.TeacherTagsActivity;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.db.TeacherTagsManager;
import com.example.administrator.weiraoeducationinstitution.photo.util.Bimp;
import com.example.administrator.weiraoeducationinstitution.photo.util.ImageItem;
import com.example.administrator.weiraoeducationinstitution.photo.util.PublicWay;
import com.example.administrator.weiraoeducationinstitution.utils.FileUtils;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.TagGroup;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timeselector.Utils.TextUtil;

public class Teacher_Select_AddActivity extends BaseActivity implements View.OnClickListener {
    public static final int SET_DYnamic_GALLERY = 2;// 从相册中选择
    private static final int TAKE_PICTURE = 0x000001;
    @Bind(R.id.include_setting_introduction_back)
    ImageView back;
    @Bind(R.id.include_setting_introduction_title)
    TextView title;
    @Bind(R.id.include_setting_introduction_ok)
    TextView includeSettingIntroductionOk;
    private String head_path = null;
    private StringBuilder feacherStr = new StringBuilder();

    private LinearLayout tagLL, popLL, ll_popup;
    private TagGroup mTagGroup;
    private TeacherTagsManager mTagsManager;
    private PopupWindow pop = null;

    private EditText name, phone, pass, email, intro;
    private CircleImageView headimg;


    private TagGroup.OnTagClickListener mTagClickListener = new TagGroup.OnTagClickListener() {
        @Override
        public void onTagClick(String tag) {
            Toast.makeText(Teacher_Select_AddActivity.this, tag, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_teacher_select_add;
    }

    @Override
    protected void init() {
        mTagsManager = TeacherTagsManager.getInstance(getApplicationContext());

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        title.setText("新增老师");
        if (mTagsManager.getTags() != null) {
            mTagsManager.clearTags();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        includeSettingIntroductionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtil.isEmpty(name.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(name, "姓名不能为空");
                    return;
                } else if (TextUtil.isEmpty(phone.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(name, "电话不能为空");
                    return;
                } else if (TextUtil.isEmpty(pass.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(name, "密码不能为空");
                    return;
                }
                if (mTagsManager.getTags() != null) {
                    String[] gg = mTagsManager.getTags();
                    for (int i = 0; i < gg.length; i++) {
                        feacherStr.append(gg[i]);
                        if (i != gg.length - 1) {
                            feacherStr.append(",");
                        }
                    }
                }
                saveAdd(BaseApplication.user_info.getId(), head_path, name.getText().toString()
                        , phone.getText().toString(), pass.getText().toString(), email.getText().toString(), feacherStr.toString(), intro.getText().toString());

            }
        });
        tagLL = customFindViewById(R.id.teacher_add_tagGroupLL);
        mTagGroup = customFindViewById(R.id.teacher_add_tagGroup);
        mTagGroup.setOnTagClickListener(mTagClickListener);
        name = customFindViewById(R.id.teacher_add_name);
        phone = customFindViewById(R.id.teacher_add_phone);
        pass = customFindViewById(R.id.teacher_add_pass);
        email = customFindViewById(R.id.teacher_add_email);
        intro = customFindViewById(R.id.teacher_add_intro);
        headimg = customFindViewById(R.id.teacher_add_headimg);
        popLL = customFindViewById(R.id.teacher_add_popLL);
        popLL.setOnClickListener(this);
        tagLL.setOnClickListener(this);
        pop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.teacher_add_tagGroupLL:
                startActivityWithoutExtras(TeacherTagsActivity.class);
                break;
            case R.id.teacher_add_popLL:
                ll_popup.startAnimation(AnimationUtils.loadAnimation(Teacher_Select_AddActivity.this, R.anim.photo_translate_in));
                pop.showAtLocation(intro, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    private void saveAdd(String id, String head_img, String name, String mobile, String pass, String email, String feature, String intro) {
        String reqUrl = Apis.GetAddTeacher(id, head_img, name, mobile, pass, email, feature, intro);
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
                }

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    protected void onRestart() {
        String[] tags = mTagsManager.getTags();
        mTagGroup.setTags(tags);
        super.onRestart();
    }

    private void pop() {
        pop = new PopupWindow(Teacher_Select_AddActivity.this);
        View view = getLayoutInflater().inflate(R.layout.plugin_item_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
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


    public void photo() {
        String state = Environment.getExternalStorageState(); // 判断是否存在sd卡
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(openCameraIntent, TAKE_PICTURE);
        } else {
            MySnackbar.getmInstance().showMessage(name, "请检查手机SD卡是否存在");
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (data != null && resultCode == RESULT_OK) {
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    Drawable drawable = new BitmapDrawable(takePhoto.getBitmap());
                    headimg.setImageDrawable(drawable);
                    String fileName = "weirao_new_teacher_head";
                    FileUtils.saveBitmap(bm, fileName);
                    File f = new File(FileUtils.SDPATH, "weirao_new_teacher_head.png");
                    if (f.exists()) {
                        String path = f.getAbsolutePath();
                        uploadImage(path);
                    }
                }
                break;
            // 图库
            case SET_DYnamic_GALLERY:
                if (data != null && resultCode == RESULT_OK) {
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
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setBitmap(bitmap);
                        Drawable drawable = new BitmapDrawable(takePhoto.getBitmap());
                        headimg.setImageDrawable(drawable);
                        uploadImage(picturePath);
                    }
                }
                break;
        }
    }

    /**
     * 上传老师头像
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
                                ToastUtils.getInstance().showToast("老师头像上传成功" + jsonObject.getString("data"));
                                head_path = jsonObject.getString("data");
                            } else {

                                ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for (int i = 0; i < PublicWay.activityList.size(); i++) {
                if (null != PublicWay.activityList.get(i)) {
                    PublicWay.activityList.get(i).finish();
                }
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        File f = new File(FileUtils.SDPATH, "weirao_new_teacher_head.png");
        if (f.exists()) {
            f.delete();
        }
        mTagsManager.clearTags();
        super.onDestroy();
    }
}
