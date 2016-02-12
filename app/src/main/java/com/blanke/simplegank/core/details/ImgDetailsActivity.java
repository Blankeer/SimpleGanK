
package com.blanke.simplegank.core.details;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blanke.simplegank.R;
import com.blanke.simplegank.base.BaseActivity;
import com.blanke.simplegank.bean.GankBean;
import com.blanke.simplegank.utils.BitmapUtils;
import com.blanke.simplegank.utils.ResUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImgDetailsActivity extends BaseActivity {

    public static final String ARG_NAME_BEAN = "ImgDetailsActivity_bean";
    public static final String ARG_NAME_IMG = "ImgDetailsActivity_img";
    @Bind(R.id.activity_img_img)
    ImageView mImageView;

    private GankBean mGankBean;
    private Bitmap mBitmap;

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
        ViewGroup.LayoutParams params = mImageView.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mImageView.setLayoutParams(params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().registerSticky(this);//注册延迟事件，主要是接受从上个页面传过来的bitmap，intent不能传，bitmap太大
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_img_details);
        Bundle bundle = getIntent().getExtras();
        mGankBean = bundle.getParcelable(ARG_NAME_BEAN);

        PhotoViewAttacher mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setOnLongClickListener(v -> {
            new MsgDialog(mBitmap, mImageView, mGankBean, ResUtils.getResString(ImgDetailsActivity.this, R.string.msg_down_img))
                    .show(getSupportFragmentManager(), "dialog");
            return false;
        });
        mAttacher.setOnViewTapListener((view, x, y) -> onBackPressed());
    }

    static class MsgDialog extends DialogFragment {
        String title;
        Bitmap bitmap;
        GankBean gankBean;
        WeakReference<ImageView> imageViewWeakReference;

        public MsgDialog(Bitmap bitmap, ImageView imageView, GankBean gankBean, String title) {
            this.bitmap = bitmap;
            this.gankBean = gankBean;
            this.title = title;
            imageViewWeakReference = new WeakReference<>(imageView);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            ImageView imageView = imageViewWeakReference.get();
            if (imageView == null) {
                return null;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(title);
            builder.setPositiveButton("确定", (dialog, id) -> {
                BitmapUtils.savaImage(imageView.getContext(), bitmap, gankBean.getUrlName())
                        .subscribe(aBoolean -> {
                            if (imageView != null) {
                                Snackbar.make(imageView, aBoolean ? R.string.msg_down_img_ok : R.string.msg_down_img_error, Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        });
                dialog.dismiss();
            });
            builder.setNegativeButton("取消", (dialog, id) -> {
                dialog.dismiss();
            });
            return builder.create();
        }
    }
}
