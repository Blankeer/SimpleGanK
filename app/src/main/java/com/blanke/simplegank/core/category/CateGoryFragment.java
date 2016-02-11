package com.blanke.simplegank.core.category;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blanke.simplegank.R;
import com.blanke.simplegank.app.BaseApplication;
import com.blanke.simplegank.base.BaseMvpLceFragment;
import com.blanke.simplegank.bean.CateGoryBean;
import com.blanke.simplegank.bean.GankBean;
import com.blanke.simplegank.consts.StaticData;
import com.blanke.simplegank.core.category.dagger.CateGoryMVPModule;
import com.blanke.simplegank.core.category.dagger.DaggerCateGoryComponent;
import com.blanke.simplegank.core.category.presenter.CateGoryPresenter;
import com.blanke.simplegank.core.category.view.CateGoryView;
import com.blanke.simplegank.core.details.ImgDetailsActivity;
import com.blanke.simplegank.core.details.WebDetailsActivity;
import com.blanke.simplegank.utils.AppConfigUtils;
import com.blanke.simplegank.utils.DateUtils;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.melnykov.fab.FloatingActionButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.socks.library.KLog;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

/**
 * Created by Blanke on 16-1-19.
 */
public class CateGoryFragment extends BaseMvpLceFragment<SwipeRefreshLayout, List<GankBean>, CateGoryView, CateGoryPresenter>
        implements CateGoryView {
    public final static String fab_init = "fab_init";
    private final static String KEY_CATEGORY = "category";
    private static final int PAGE_COUNT = StaticData.PAGE_COUNT;
    @Bind(R.id.fragment_cate_recyclerview)
    FamiliarRecyclerView mRecyclerview;
    @Bind(R.id.contentView)
    MaterialRefreshLayout mSwipeRefreshLayout;
    private int itemWidthDp = StaticData.ITEM_WIDTH_DP;

    private CateGoryBean mCateGoryBean;
    private RecyclerAdapter mAdapter;

    @Inject
    CateGoryPresenter mCateGoryPresenter;

    private boolean isFootLoading = false;

    private int page = 1;
    private DisplayImageOptions options;

    public static CateGoryFragment getInstance(CateGoryBean cateGoryBean) {
        CateGoryFragment fragment = new CateGoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_CATEGORY, cateGoryBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCateGoryBean = getArguments().getParcelable(KEY_CATEGORY);
        DaggerCateGoryComponent.builder()
                .appComponent(BaseApplication.getApplication(getActivity()).getAppComponent())
                .cateGoryMVPModule(new CateGoryMVPModule())
                .build().inject(this);
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = fab_init)
    private void initFab(FloatingActionButton fab) {
        fab.setOnClickListener(v -> mRecyclerview.smoothScrollToPosition(0));
        fab.attachToRecyclerView(mRecyclerview);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int screenWidth = newConfig.screenWidthDp;
        mRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(screenWidth / itemWidthDp + 1, OrientationHelper.VERTICAL));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int screenWidth = AppConfigUtils.getScreenWidthDp(getActivity());
        mRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(screenWidth / itemWidthDp + 1, OrientationHelper.VERTICAL));

        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RecyclerAdapter();
        mRecyclerview.setAdapter(mAdapter);

        mRecyclerview.setOnItemClickListener((familiarRecyclerView, view1, position) -> {
            GankBean bean = mAdapter.getData().get(position);
            if (bean.isImage()) {
                ImageView imageView = (ImageView) view1.findViewById(R.id.item_cate_card_img);
                ImgDetailsActivity.start(getActivity(), imageView, bean);
            } else {
                WebDetailsActivity.start(getActivity(), bean);
            }
        });
        mSwipeRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                KLog.d("开始下拉刷新事件");
                page = 1;
                loadData(true);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                KLog.d("开始上拉刷新事件");
                isFootLoading = true;
                loadData(true);
            }
        });

        loadData(false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_category;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @Override
    public CateGoryPresenter createPresenter() {
        return mCateGoryPresenter;
    }

    List<GankBean> newData;

    @Override
    public void setData(List<GankBean> data) {
        this.newData = data;
        page++;
        if (!isFootLoading) {//下拉，直接覆盖数据
            mAdapter.setData(newData);
        } else {
            mAdapter.addData(newData);
        }
        isFootLoading = false;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        KLog.d("开始加载数据  是否是第一次自动加载:" + !pullToRefresh);
        mCateGoryPresenter.loadGank(pullToRefresh, mCateGoryBean, PAGE_COUNT, page);
    }

    private DisplayImageOptions getImageOptions() {
        if (options != null) {
            return options;
        }
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.load) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.load)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.load)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
                .build();//构建完成
        return options;
    }

    @Override
    public void stopRefreshing() {
        KLog.d("刷新结束");
        mSwipeRefreshLayout.finishRefresh();
        mSwipeRefreshLayout.finishRefreshLoadMore();
    }

    @Override
    public void onFail(Throwable e) {
        Snackbar.make(mRecyclerview, e.toString(), Snackbar.LENGTH_LONG).show();
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<GankBean> data;

        public List<GankBean> getData() {
            return data;
        }

        public void setData(List<GankBean> data) {
            if (this.data == null) {
                this.data = new ArrayList<>();
            } else {
                this.data.clear();
            }
            this.data.addAll(data);
        }

        public void addData(List<GankBean> data) {
            if (this.data == null) {
                this.data = new ArrayList<>();
            }
            this.data.addAll(data);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                View root = LayoutInflater.from(getActivity()).inflate(R.layout.item_category_text_recycler, null);
                return new TextViewHolder(root);
            } else {
                View root = LayoutInflater.from(getActivity()).inflate(R.layout.item_category_img_recycler, null);
                return new ImgViewHolder(root);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            GankBean bean = data.get(position);
            if (holder instanceof TextViewHolder) {
                TextViewHolder textholder = (TextViewHolder) holder;
                textholder.mTextViewTitle.setText(bean.getDesc());
                textholder.mTextViewTag.setText(bean.getType());
                textholder.mTextViewTime.setText(DateUtils.getTimestampString(bean.getUpdatedAt()));
            } else {
                ImgViewHolder imgViewHolder = (ImgViewHolder) holder;
                ImageLoader.getInstance().displayImage(bean.getUrl(), imgViewHolder.mImageView, getImageOptions());
            }
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }


        @Override
        public int getItemViewType(int position) {
            GankBean item = data.get(position);
            return item.isImage() ? 1 : 0;
        }
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_cate_card_title)
        TextView mTextViewTitle;
        @Bind(R.id.item_cate_card_tag)
        TextView mTextViewTag;
        @Bind(R.id.item_cate_card_time)
        TextView mTextViewTime;


        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ImgViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_cate_card_img)
        ImageView mImageView;

        public ImgViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
