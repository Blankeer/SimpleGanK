package com.blanke.simplegank.core.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blanke.simplegank.R;
import com.blanke.simplegank.app.BaseApplication;
import com.blanke.simplegank.base.BaseMvpLceFragment;
import com.blanke.simplegank.bean.CateGoryBean;
import com.blanke.simplegank.bean.GankBean;
import com.blanke.simplegank.core.category.dagger.CateGoryMVPModule;
import com.blanke.simplegank.core.category.dagger.DaggerCateGoryComponent;
import com.blanke.simplegank.core.category.presenter.CateGoryPresenter;
import com.blanke.simplegank.core.category.view.CateGoryView;
import com.blanke.simplegank.utils.DateUtils;
import com.blanke.simplegank.view.CustomSmoothProgressBar;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerViewOnScrollListener;

import static android.view.ViewGroup.LayoutParams;

/**
 * Created by Blanke on 16-1-19.
 */
public class CateGoryFragment extends BaseMvpLceFragment<SwipeRefreshLayout, List<GankBean>, CateGoryView, CateGoryPresenter> implements CateGoryView, SwipeRefreshLayout.OnRefreshListener {

    private final static String KEY_CATEGORY = "category";
    private static final int PAGE_COUNT = 20;
    @Bind(R.id.fragment_cate_recyclerview)
    FamiliarRecyclerView mRecyclerview;
    @Bind(R.id.contentView)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CateGoryBean mCateGoryBean;
    private RecyclerAdapter mAdapter;

    @Inject
    CateGoryPresenter mCateGoryPresenter;
    private CustomSmoothProgressBar footLoadView;

    private boolean isFootLoading = false;

    private long lastLoadTime = 0;

    private int page = 1;

    public static CateGoryFragment getInstance(CateGoryBean cateGoryBean) {
        CateGoryFragment fragment = new CateGoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_CATEGORY, cateGoryBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        KLog.d();
        super.onCreate(savedInstanceState);
        mCateGoryBean = getArguments().getParcelable(KEY_CATEGORY);
        DaggerCateGoryComponent.builder()
                .appComponent(BaseApplication.getApplication(getActivity()).getAppComponent())
                .cateGoryMVPModule(new CateGoryMVPModule())
                .build().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecyclerAdapter();
        mRecyclerview.setAdapter(mAdapter);
        footLoadView = (CustomSmoothProgressBar) LayoutInflater.from(getActivity()).inflate(R.layout.view_loading_smooth, null);
        LayoutParams layoutparams = new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        footLoadView.setLayoutParams(layoutparams);


        footLoadView.setSmoothProgressDrawableCallbacks(footLoadView.new CustomCallBack() {
            @Override
            public void animStop() {
                KLog.d("上拉动画完成，上拉加载完成");
                addDataAsyn();//动画完成回调
                isFootLoading = false;//停止
            }
        });


        mRecyclerview.addFooterView(footLoadView);
        mRecyclerview.addOnScrollListener(new FamiliarRecyclerViewOnScrollListener(mRecyclerview.getLayoutManager()) {
            @Override
            public void onScrolledToTop() {

            }

            @Override
            public void onScrolledToBottom() {
                if (isFootLoading == false) {
                    isFootLoading = true;
                    KLog.d("滑动到底部，开始上拉加载");
                    footLoadView.setShow(true);
                    loadData(true);
                } else {
                    KLog.d("滑动到底部，已经开始上拉加载，忽略");
                }
            }
        });
        mRecyclerview.setOnItemClickListener((familiarRecyclerView, view1, position) -> {
            Snackbar.make(view1, mAdapter.getData().get(position).getDesc(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);


        final float[] downRawY = {0};

//        mRecyclerview.setOnTouchListener(new OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int lastPosition = mRecyclerview.getLastVisiblePosition();
//                if (downRawY[0] == 0) {
//                    downRawY[0] = event.getRawY();
//                }
//                if (lastPosition == mAdapter.getData().size() - 1) {
//                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                        float dy = downRawY[0] - event.getRawY();
//                        footLoadView.setProgress((int) dy);
//                        if (footLoadView.getProgress() == 100) {
//                            downRawY[0] = 0;
//                            mRecyclerview.postDelayed(() -> footLoadView.progressiveStop(), 2000);
//                        }
//                    }
//                }
//                return false;
//            }
//        });

        loadData(false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_category;
    }

    private void jumpTop() {//跳转到列表最开始
        jumpToPosition(0);
    }

    private void jumpBottom() {//跳转到列表最后
        jumpToPosition(mAdapter.getItemCount());
    }

    /**
     * 跳转到列表指定位置
     *
     * @param position
     */
    private void jumpToPosition(int position) {
//        ValueAnimator animator = ValueAnimator.ofInt(mRecyclerview.getFirstVisiblePosition(), position);
//        animator.setDuration(1000);
//        animator.setInterpolator(new DecelerateInterpolator());
//        animator.addUpdateListener(animation -> mRecyclerview.smoothScrollToPosition((int) animation.getAnimatedValue()));
//        animator.start();
        mRecyclerview.post(() -> mRecyclerview.smoothScrollToPosition(position));
    }

    private void jumpToShowPosition() {
        if (mRecyclerview != null && mRecyclerview.isShown()) {
            jumpToPosition(mRecyclerview.getLastVisiblePosition() + mRecyclerview.getChildCount() - 4);
        }
    }
//    @Override
//    public List<GankBean> getData() {
//        return mAdapter.getData();
//    }

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
            mAdapter.notifyDataSetChanged();
//            mAdapter.notifyItemRangeChanged(0, newData.size());
        }
    }

    /**
     * 上拉刷新的回调，需要等待动画完成后回调插入数据
     */
    public void addDataAsyn() {
        if (newData != null) {
            int oldLastPosition = mAdapter.getItemCount();
            mAdapter.addData(newData);
//            mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemRangeInserted(oldLastPosition, newData.size());
            jumpToShowPosition();
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        lastLoadTime = System.currentTimeMillis();
        KLog.d("开始加载数据  是否是手动刷新:" + pullToRefresh);
        mCateGoryPresenter.loadGank(pullToRefresh, mCateGoryBean, PAGE_COUNT, page);
    }

    @Override
    public void onRefresh() {//下拉
        page = 1;
        loadData(true);
    }


    @Override
    public void stopRefreshing() {
        KLog.d("刷新结束");
        mSwipeRefreshLayout.setRefreshing(false);
        if (isFootLoading) {
            if (System.currentTimeMillis() - lastLoadTime >= 3 * 1000 * 1000) {
                footLoadView.setShow(false);
            } else {
                footLoadView.postDelayed(() -> footLoadView.setShow(false), 3000);//延迟，防止刷新时间过短
            }
        }
    }

    @Override
    public void onFail(Throwable e) {
        Snackbar.make(mRecyclerview, e.toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<GankBean> data;
        private int layoutId;

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

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            layoutId = mCateGoryBean.getLayoutResId();
            View root = LayoutInflater.from(getActivity()).inflate(layoutId, null);
            return new ViewHolder(root);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            GankBean bean = data.get(position);
            holder.mTextViewTitle.setText(bean.getDesc());
            holder.mTextViewTag.setText(bean.getType());
            holder.mTextViewTime.setText(DateUtils.getTimestampString(bean.getUpdatedAt()));
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        public void addData(List<GankBean> data) {
            if (this.data == null) {
                this.data = new ArrayList<>();
            }
            this.data.addAll(data);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_cate_card_title)
        TextView mTextViewTitle;
        @Bind(R.id.item_cate_card_tag)
        TextView mTextViewTag;
        @Bind(R.id.item_cate_card_time)
        TextView mTextViewTime;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}