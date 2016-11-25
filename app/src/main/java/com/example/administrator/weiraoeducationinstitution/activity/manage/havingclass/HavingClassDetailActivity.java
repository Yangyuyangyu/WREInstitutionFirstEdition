package com.example.administrator.weiraoeducationinstitution.activity.manage.havingclass;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.activity.change.studentWorkImg.adapter.ImageRecyclerAdapter;
import com.example.administrator.weiraoeducationinstitution.activity.change.studentWorkImg.adapter.OnRecyclerItemClickListener;
import com.example.administrator.weiraoeducationinstitution.activity.change.studentWorkImg.presenter.MainPresenter;
import com.example.administrator.weiraoeducationinstitution.activity.change.studentWorkImg.view.ImageBrowseActivity;
import com.example.administrator.weiraoeducationinstitution.activity.change.studentWorkImg.view.MainView;
import com.example.administrator.weiraoeducationinstitution.activity.manage.leave.LeaveRefuseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.change.AllStudentStateAdapter;

import com.example.administrator.weiraoeducationinstitution.bean.change.CallNameBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.TipsDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HavingClassDetailActivity extends BaseActivity implements MainView {

    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;

    private String getId, the_id;
    private LinearLayout bottomLL;
    private Button ok, refuse, checkMore;
    private TextView manage, process, solution, isSolve;
    //修改
    private XRecyclerView mRecyclerView;//学生列表状态:头像 姓名 状态  电话
    private AllStudentStateAdapter adapter;
    private TipsDialog dialog;
    //作业
    private TextView workContent;
    private RecyclerView rv;//作业
    private List<String> images;
    private ImageRecyclerAdapter adapter_workImg;
    private MainPresenter presenter;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_having_class_detail;
    }

    @Override
    protected void init() {
        getId = getIntent().getStringExtra("the_having_class_id");

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        weiraoToolbarTop.setTitle("上课记录审核");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        manage = customFindViewById(R.id.having_detail_manage);
        process = customFindViewById(R.id.having_detail_process);
        solution = customFindViewById(R.id.having_detail_solution);
        isSolve = customFindViewById(R.id.having_detail_isSolve);
        ok = customFindViewById(R.id.having_detail_ok);
        bottomLL = customFindViewById(R.id.having_detail_bottomLL);
        refuse = customFindViewById(R.id.having_detail_refuse);
        checkMore = customFindViewById(R.id.having_detail_checkMore);
        //修改
        dialog = new TipsDialog(this);
        mRecyclerView = customFindViewById(R.id.having_detail_recycleview);

        adapter = new AllStudentStateAdapter(this, new ArrayList<CallNameBean>());
        adapter.setPhoneItemClick(new AllStudentStateAdapter.phoneItemClick() {
            @Override
            public void callOnClick(final String phoneNumber) {
                dialog.myFunction(new TipsDialog.DialogCallBack() {
                    @Override
                    public void setContent(TextView v) {
                        v.setText("是否拨打电话 " + phoneNumber);
                    }

                    @Override
                    public void setConfirmOnClickListener(Button btn) {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dissMiss();
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        workContent = customFindViewById(R.id.having_detail_workContent);
        rv = customFindViewById(R.id.having_detail_recycleview_img);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audit(the_id, "1", "通过");
            }
        });
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("the_havingclass_id", the_id);
                startActivityWithExtras(LeaveRefuseActivity.class, bundle);
                finish();
            }
        });
        checkMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("the_havingclass_id", the_id);
                startActivityWithExtras(HavingClassCheckActivity.class, bundle);
            }
        });

        initPresenter();

    }

    public void initPresenter() {
        presenter = new MainPresenter(this);
    }

    @Override
    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public void initRecycler(int size) {
        Log.e("作业数组的size", size + "");
        if (images == null || images.size() == 0) {
            rv.setVisibility(View.GONE);//没有图片不显示
            return;
        }

        if (adapter_workImg == null) {
            rv.setLayoutManager(new GridLayoutManager(this, 3));

            rv.setItemAnimator(new DefaultItemAnimator());
            adapter_workImg = new ImageRecyclerAdapter(this, images);
            adapter_workImg.setItemClickListener(new OnRecyclerItemClickListener() {
                @Override
                public void click(View item, int position) {
                    ImageBrowseActivity.startActivity(HavingClassDetailActivity.this, (ArrayList<String>) images, position);
                }
            });
            rv.setAdapter(adapter_workImg);
        }
    }


    @Override
    protected void initData() {
        getData();
        getStudentStateDate();


    }


    private void getData() {
        String reqUrl = Apis.GetReportInfo(getId);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    HavingClassDetail hcd = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<HavingClassDetail>() {
                            }.getType());
                    if (hcd.getStatus().equals("2")) {
                        bottomLL.setVisibility(View.GONE);
                        isSolve.setText("已拒绝");
                        isSolve.setTextColor(getResources().getColor(R.color.weirao_title_color));
                        isSolve.setVisibility(View.VISIBLE);
                    } else if (hcd.getStatus().equals("1")) {
                        bottomLL.setVisibility(View.GONE);
                        isSolve.setVisibility(View.VISIBLE);
                    }
                    manage.setText(hcd.getContent());
                    process.setText(hcd.getProblem());
                    solution.setText(hcd.getSolution());
                    the_id = hcd.getId();

                    if (hcd.getHas_work().equals("0")) {
                        workContent.setText("无作业内容");
                    } else {
                        workContent.setText(hcd.getWork_content());
                    }
                    //判断作业图片是否为空
                    if (!hcd.getImg().equals("") && hcd.getImg().length() != 0) {
                        String workArray;
                        String workImages = hcd.getImg();
                        String lastStr = workImages.substring(workImages.length() - 1);

                        if (lastStr.equals(",")) {
                            workArray = workImages.substring(0, workImages.length() - 1);
                        } else {
                            workArray = workImages;
                        }
                        String[] strarray = workArray.split("[,]");
                        List<String> list = new ArrayList<String>();// list
                        for (int i = 0; i < strarray.length; i++) {
                            list.add(strarray[i]);
                        }


                        Log.e("作业数组list-----", list.toString());

                        presenter.loadImage(list);//加载作业图
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.getInstance().showToast(e.getMessage());
            }
        });
    }


    /**
     * 审核是否通过
     */
    private void audit(String id, String result, String reason) {
        String reqUrl = Apis.GetReportAuth(id, result, reason);
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
                ToastUtils.getInstance().showToast(e.getMessage());
            }
        });
    }


    private void getStudentStateDate() {
        String reqUrl = Apis.GetStudentCallInfo(getId);
        Log.e("学生考勤-----", reqUrl);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                Log.e("学生考情-----", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        Gson gson = new Gson();
                        List<CallNameBean> list = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<CallNameBean>>() {
                                }.getType());
                        adapter.addAll(list);
                        mRecyclerView.refreshComplete();
                        mRecyclerView.setPullRefreshEnabled(false);
                        mRecyclerView.setLoadingMoreEnabled(false);

                    } else {
                        ToastUtils.getInstance().showToast("暂无学生考勤信息");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.getInstance().showToast(e.getMessage());
            }
        });

    }


    class HavingClassDetail {
        //        "id": "16",
//                "content": "疼偷了",
//                "problem": "了科目诺记",
//                "solution": "V5口蘑咯了",
//                "has_work": "0",
//                "work_content": "",
//                "img": "",
//                "class_id": "12",
//                "status": "1",
//                "course_id": "17"
//        content：课堂重点
//        problem：上课过程
//        solution：建议办法
//        has_work：是否有作业：0无，1有
//        work_content：作业内容，当has_work为0时为空值
//        img：作业图片，当has_work为0时为空值，当有多个图片时为用逗号隔开的字符串
//        status：审核状态，0未审核，1通过，2拒绝
//        refuse：拒绝原因


        private String id;
        private String content;//课堂管理
        private String problem;
        private String solution;
        private String has_work;
        private String class_id;
        private String status;
        private String course_id;
        //新增
        private String work_content;
        private String img;

        public String getWork_content() {
            return work_content;
        }

        public void setWork_content(String work_content) {
            this.work_content = work_content;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getProblem() {
            return problem;
        }

        public void setProblem(String problem) {
            this.problem = problem;
        }

        public String getSolution() {
            return solution;
        }

        public void setSolution(String solution) {
            this.solution = solution;
        }

        public String getHas_work() {
            return has_work;
        }

        public void setHas_work(String has_work) {
            this.has_work = has_work;
        }

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }
    }
}
