package com.outsideweather.cn.util;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideUtil {
    public static  void setImage(ImageView view, String url, @DrawableRes int resourceId){
        Glide.with(view.getContext())
                .load(url)
                .placeholder(resourceId)
                .fallback(resourceId)
                .error(resourceId)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(view);
    }
}
