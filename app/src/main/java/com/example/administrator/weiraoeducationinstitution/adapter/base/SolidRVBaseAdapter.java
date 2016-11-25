package com.example.administrator.weiraoeducationinstitution.adapter.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ViewUtils;

import java.util.List;


/**
 * Created by _SOLID
 * Date:2016/4/5
 * Time:11:18
 * <p/>
 * 通用的RecyclerView的适配器
 * <p/>
 * 思想上参考了Hongyang的 http://blog.csdn.net/lmj623565791/article/details/38902805这篇博客
 */
public abstract class SolidRVBaseAdapter<T> extends RecyclerView.Adapter<SolidRVBaseAdapter.SolidCommonViewHolder> {

    protected List<T> mBeans;
    protected Context mContext;
    protected boolean mAnimateItems = false;
    protected int mLastAnimatedPosition = -1;

    public SolidRVBaseAdapter(Context context, List<T> beans) {
        mContext = context;
        mBeans = beans;
    }

    @Override
    public SolidRVBaseAdapter.SolidCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(getItemLayoutID(viewType), parent, false);
        SolidCommonViewHolder holder = new SolidCommonViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(SolidRVBaseAdapter.SolidCommonViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        onBindDataToView(holder, mBeans.get(position));

    }

    /**
     * 绑定数据到Item的控件中去
     *
     * @param holder
     * @param bean
     */
    protected abstract void onBindDataToView(SolidCommonViewHolder holder, T bean);

    /**
     * 取得ItemView的布局文件
     *
     * @return
     */
    public abstract int getItemLayoutID(int viewType);


    @Override
    public int getItemCount() {
        return mBeans.size();
    }

    public void add(T bean) {
        mBeans.add(bean);
        notifyDataSetChanged();
    }

    public void addAll(List<T> beans) {
        mBeans.addAll(beans);
        notifyDataSetChanged();
    }

    public void clear() {
        mBeans.clear();
        notifyDataSetChanged();
    }

    /***
     * item的加载动画
     *
     * @param view
     * @param position
     */
    private void runEnterAnimation(View view, int position) {
        if (!mAnimateItems) {
            return;
        }
        if (position > mLastAnimatedPosition) {
            mLastAnimatedPosition = position;
            view.setTranslationY(ViewUtils.getScreenHeight(mContext));
            view.animate()
                    .alpha(50)
                    .setStartDelay(100)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(300)
                    .start();
        }
    }


    public class SolidCommonViewHolder extends
            RecyclerView.ViewHolder {
        private final SparseArray<View> mViews;
        public View itemView;

        public SolidCommonViewHolder(final View itemView) {
            super(itemView);
            this.mViews = new SparseArray<>();
            this.itemView = itemView;
            //添加Item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(getAdapterPosition());
                }
            });

        }


        public <T extends View> T getView(int viewId) {

            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public void setText(int viewId, String text) {
            TextView tv = getView(viewId);
            tv.setText(text);
        }

        public void setVisible(int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }

        /**
         * 加载drawable中的图片
         *
         * @param viewId
         * @param resId
         */
        public void setImage(int viewId, int resId) {
            ImageView iv = getView(viewId);
            iv.setImageResource(resId);
        }

        /**
         * 加载网络上的图片
         *
         * @param viewId
         * @param url
         */
        public void setImageFromInternet(int viewId, String url) {
            ImageView iv = getView(viewId);
            if (url != null) {
                HttpUtils.getInstance().loadImage(url, iv);//这里可根据自己的需要变更
            }
        }

        /**
         * 关于点击事件的
         */
        public SolidCommonViewHolder setOnItemClickListener(int viewId,
                                                            View.OnClickListener listener) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
            return this;
        }

        /**
         * @return
         */
        public SolidCommonViewHolder setChecked(int viewId, boolean checked) {
            Checkable view = (Checkable) getView(viewId);
            view.setChecked(checked);
            return this;
        }

        /**
         * 设置TextView背景
         */
        public SolidCommonViewHolder setTextViewDrawable(int viewId, Drawable drawable) {
            TextView view = getView(viewId);
            view.setBackgroundDrawable(drawable);
            return this;
        }

        public SolidCommonViewHolder setTextColor(int viewId, int textColor) {
            TextView view = getView(viewId);
            view.setTextColor(textColor);
            return this;
        }

        /**
         * LinerLayout设置背景
         */
        public SolidCommonViewHolder setLLDrawable(int viewId, Drawable drawable) {
            LinearLayout view = getView(viewId);
            view.setBackgroundDrawable(drawable);
            return this;
        }
    }

    /**
     * ItemView的单击事件(如果需要，重写此方法就行)
     *
     * @param position
     */
    protected void onItemClick(int position) {

    }


}
