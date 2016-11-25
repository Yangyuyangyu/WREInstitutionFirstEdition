package com.example.administrator.weiraoeducationinstitution.activity;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.db.TagsManager;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.TagGroup;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AssociationTagGoupActivity extends BaseActivity {

    @Bind(R.id.include_setting_introduction_back)
    ImageView back;
    @Bind(R.id.include_setting_introduction_title)
    TextView title;
    @Bind(R.id.include_setting_introduction_ok)
    TextView ok;

    private TagGroup mTagGroup;
    private TagsManager mTagsManager;

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_association_tag_goup;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        title.setText("机构特点");
        ok.setText("完成");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTagsManager.updateTags(mTagGroup.getTags());
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTagGroup.getTags() == null) {
                    return;
                }
                mTagsManager.updateTags(mTagGroup.getTags());
                StringBuilder str = new StringBuilder();
                String[] tagss = mTagGroup.getTags();
                for (int i = 0; i < tagss.length; i++) {
                    str.append(tagss[i]);
                    if (i != tagss.length - 1) {
                        str.append(",");
                    }
                }
                upContent(BaseApplication.user_info.getId(), "2", str.toString());
            }
        });

        mTagGroup = customFindViewById(R.id.association_tag_group);
        mTagsManager = TagsManager.getInstance(getApplicationContext());
        String[] tags = mTagsManager.getTags();
        mTagGroup.setTags(tags);

    }

    @Override
    public void onBackPressed() {
        if (mTagGroup.getTags() != null) {
            mTagsManager.updateTags(mTagGroup.getTags());
        }
        super.onBackPressed();
    }

    private void upContent(String id, String type, final String content) {
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
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
                        BaseApplication.user_info.setFeature(content);
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

}
