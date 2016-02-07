
package com.blanke.simplegank.core.details;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blanke.simplegank.R;
import com.blanke.simplegank.base.BaseActivity;
import com.blanke.simplegank.bean.GankBean;
import com.blanke.simplegank.utils.BitmapUtils;
import com.blanke.simplegank.utils.ResUtils;
import com.bm.library.PhotoView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.Bind;

public class ImgDetailsActivity extends BaseActivity {

    private float startScale = 1F, endScale = 2F;
    private long scroleTime = 200;
    public static final String ARG_NAME_BEAN = "ImgDetailsActivity_bean";
    public static final String ARG_NAME_IMG = "ImgDetailsActivity_img";
    @Bind(R.id.activity_img_img)
    PhotoView mImageView;

    private GankBean mGankBean;
    private Bitmap mBitmap;
    private DisplayImageOptions options;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void start(Activity activity, ImageView imageview, GankBean bean) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                imageview, ResUtils.getResString(activity, R.string.share_img));
        Intent intent2 = new Intent(activity, ImgDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NAME_BEAN, bean);
        intent2.putExtras(bundle);
        EventBus.getDefault().postSticky(((BitmapDrawable) imageview.getDrawable()).getBitmap(), ARG_NAME_IMG);
        activity.startActivity(intent2, options.toBundle());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = ARG_NAME_IMG)
    public void initImageData(Bitmap bitmap) {
        this.mBitmap = bitmap;
        mImageView.setImageBitmap(mBitmap);
        smoothScale(startScale, endScale, scroleTime);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().registerSticky(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_img_details);
        Bundle bundle = getIntent().getExtras();
        mGankBean = bundle.getParcelable(ARG_NAME_BEAN);

//        File file = ImageLoader.getInstance().getDiskCache().get(mGankBean.getUrl());
//        mBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        initScale();
        mImageView.enable();
        mImageView.setOnClickListener(v->onBackPressed());
        mImageView.setOnLongClickListener(v -> {
            new MsgDialog(ResUtils.getResString(ImgDetailsActivity.this, R.string.msg_down_img))
                    .show(getSupportFragmentManager(), "dialog");
            return false;
        });
//        ImageLoader.getInstance().displayImage(mGankBean.getUrl(), mImageView, getImageOptions());
        /*ImageLoader.getInstance().displayImage(mGankBean.getUrl(), mImageView, getImageOptions(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                mBitmap = bitmap;
                initScale();
                smoothScale(startScale, endScale, scroleTime * 2);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });*/
    }

    private void initScale() {
        startScale = Math.max(mImageView.getScaleX(), mImageView.getScaleY());
        endScale = startScale + 0.5F;
    }

    //放大或缩小图片，动画
    private void smoothScale(float start, float end, long time) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end).setDuration(time);
        animator.start();
        animator.addUpdateListener(animation -> {
            mImageView.setScaleX((Float) animation.getAnimatedValue());
            mImageView.setScaleY((Float) animation.getAnimatedValue());
        });
    }

    @Override
    public void onBackPressed() {
        smoothScale(endScale, startScale, scroleTime);
        mImageView.postDelayed(() -> super.onBackPressed(), scroleTime);
    }

    private DisplayImageOptions getImageOptions() {
        if (options != null) {
            return options;
        }
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.NONE)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .build();//构建完成
        return options;
    }


    class MsgDialog extends DialogFragment {
        String title;

        public MsgDialog(String title) {
            this.title = title;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(title);
            builder.setPositiveButton("确定", (dialog, id) -> {
                boolean f = BitmapUtils.savaImage(ImgDetailsActivity.this, mBitmap, mGankBean.getUrlName());
                Snackbar.make(mImageView, f ? R.string.msg_down_img_ok : R.string.msg_down_img_error, Snackbar.LENGTH_SHORT).show();
                dialog.dismiss();
            });
            builder.setNegativeButton("取消", (dialog, id) -> {
                dialog.dismiss();
            });
            return builder.create();
        }
    }
}
