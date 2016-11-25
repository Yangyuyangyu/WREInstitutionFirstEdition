package com.example.administrator.weiraoeducationinstitution.fragment.base;

import android.graphics.Color;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;


/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:21:43
 */
public class StringFragment extends BaseFragment {
    private String mText;
    private WebView mTvText;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_string;
    }

    @Override
    protected void initView() {
        mText = getArguments().getString("text");
        mTvText = (WebView) getContentView().findViewById(R.id.tv_text);
        mTvText.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mTvText.setBackgroundColor(Color.TRANSPARENT);
        if (mText != null) {
            if (!mText.equals(""))
                webText(mText);
            else
                webText("暂无信息");
        }

    }

    private void webText(String str) {
        if (str != null) {
            String strUrl = Apis.WeiRao_http;
            StringBuilder html = new StringBuilder();
            html.append("<html>");
            html.append("<head>");
            html.append("<title>活动简介</title>");
            html.append("<style>img{width:100% !important;}</style></head>");
            html.append("<body>");
            html.append(str);
            html.append("</body>");
            html.append("</html>");
            mTvText.loadDataWithBaseURL(strUrl.toString(), html.toString(), "text/html", "utf-8", null);
        }
    }
}
