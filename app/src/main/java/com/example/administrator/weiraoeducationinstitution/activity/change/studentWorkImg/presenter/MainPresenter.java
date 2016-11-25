package com.example.administrator.weiraoeducationinstitution.activity.change.studentWorkImg.presenter;



import android.util.Log;

import com.example.administrator.weiraoeducationinstitution.activity.change.studentWorkImg.view.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2016/9/3.
 */
public class MainPresenter {

    private MainView view;
    private List<String> images;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void loadImage(List<String> image){

        if(images == null) images = new ArrayList<String>();
        images=  image;
        view.setImages(images);
        view.initRecycler(image.size());
        Log.e("加载作业数组",images.toString());
    }

}
