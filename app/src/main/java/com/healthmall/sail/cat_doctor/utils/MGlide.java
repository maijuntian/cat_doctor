package com.healthmall.sail.cat_doctor.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.healthmall.sail.cat_doctor.MyApplication;
import com.mai.xmai_fast_lib.utils.MLog;

import java.io.ByteArrayOutputStream;
import java.io.File;


/**
 * Created by mai on 16/8/25.
 */
public class MGlide {


    public static void initImageView(ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }


    public static void loadCircle(Context ctx, String url, ImageView imageView) {
        initImageView(imageView);
        Glide.with(ctx).load(url).transform(new CenterCrop(MyApplication.get().getApplicationContext()), new GlideCircleTransform(MyApplication.get().getApplicationContext())).into(imageView);

    }

    public static void load(Context ctx, String url, ImageView imageView) {
        initImageView(imageView);
        Glide.with(ctx).load(url).into(imageView);

    }

    public static void loadCircle(Context ctx, Bitmap bitmap, ImageView imageView) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        initImageView(imageView);
        Glide.with(ctx).load(bytes).transform(new CenterCrop(MyApplication.get().getApplicationContext()), new GlideCircleTransform(MyApplication.get().getApplicationContext())).into(imageView);
    }

    public static void loadFile(String path, ImageView imageView) {

        initImageView(imageView);

        imageView.setImageBitmap(BitmapFactory.decodeFile(path));
    }

    public static void loadFace(Context ctx, ImageView imageView){

        Glide.with(ctx)
                .load(CommonUtils.getFaceFile()).diskCacheStrategy( DiskCacheStrategy.NONE )//禁用磁盘缓存
                .skipMemoryCache( true )//跳过内存缓存
                .into(imageView);
    }

    public static void loadTon(Context ctx, ImageView imageView){
        Glide.with(ctx)
                .load(CommonUtils.getTonFile()).diskCacheStrategy( DiskCacheStrategy.NONE )//禁用磁盘缓存
                .skipMemoryCache( true )//跳过内存缓存
                .into(imageView);
        ;
    }

    public static void loadFace2(Context ctx, ImageView imageView){

        Glide.with(ctx)
                .load(CommonUtils.getFaceFile()).diskCacheStrategy( DiskCacheStrategy.NONE )//禁用磁盘缓存
                .skipMemoryCache( true )//跳过内存缓存
                .transform(new GlideCircleTransform(MyApplication.get().getApplicationContext()))
                .into(imageView);
    }

    public static void loadTon2(Context ctx, ImageView imageView){
        Glide.with(ctx)
                .load(CommonUtils.getTonFile()).diskCacheStrategy( DiskCacheStrategy.NONE )//禁用磁盘缓存
                .skipMemoryCache( true )//跳过内存缓存
                .transform(new GlideCircleTransform(MyApplication.get().getApplicationContext()))
                .into(imageView);
        ;
    }

}
