package com.blanke.simplegank.core.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import com.blanke.simplegank.core.main.dagger.CateGoryMVPModule;
import com.blanke.simplegank.core.main.dagger.DaggerCateGoryComponent;
import com.blanke.simplegank.core.main.presenter.CateGoryPresenter;
import com.blanke.simplegank.core.main.view.CateGoryView;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerViewOnScrollListener;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

import static android.view.ViewGroup.*;

/**
 * Created by Blanke on 16-1-19.
 */
public class CateGoryFragment extends BaseMvpLceFragment<SwipeRefreshLayout, List<GankBean>, CateGoryView, CateGoryPresenter> implements CateGoryView, SwipeRefreshLayout.OnRefreshListener {

    private final static String KEY_CATEGORY = "category";
    @Bind(R.id.fragment_cate_recyclerview)
    FamiliarRecyclerView mRecyclerview;
    @Bind(R.id.contentView)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CateGoryBean mCateGoryBean;
    private RecyclerAdapter mAdapter;

    @Inject
    CateGoryPresenter mCateGoryPresenter;
    private SmoothProgressBar footLoadView;

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
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecyclerAdapter();
        mRecyclerview.setAdapter(mAdapter);
        footLoadView = (SmoothProgressBar) LayoutInflater.from(getActivity()).inflate(R.layout.view_loading_smooth, null);
        LayoutParams layoutparams = new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        footLoadView.setLayoutParams(layoutparams);
        mRecyclerview.addFooterView(footLoadView);
        mRecyclerview.addOnScrollListener(new FamiliarRecyclerViewOnScrollListener(mRecyclerview.getLayoutManager()) {
            @Override
            public void onScrolledToTop() {

            }

            @Override
            public void onScrolledToBottom() {
                footLoadView.progressiveStart();
                loadData(true);
//                mRecyclerview.postDelayed(() -> footLoadView.progressiveStop(), 2000);
            }
        });
        mRecyclerview.setOnItemClickListener((familiarRecyclerView, view1, position) -> {
            Snackbar.make(view1, mAdapter.getData().get(position).getDesc(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);


        loadData(false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_category;
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

    @Override
    public void setData(List<GankBean> data) {
        mAdapter.setData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mCateGoryPresenter.loadGank(pullToRefresh, mCateGoryBean, 20, 1);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
        KLog.d();
        footLoadView.progressiveStop();
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
            this.data = data;
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
            holder.mTextViewTime.setText(bean.getUpdatedAt());
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
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
