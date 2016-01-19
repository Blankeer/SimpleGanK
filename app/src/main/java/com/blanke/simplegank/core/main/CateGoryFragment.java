package com.blanke.simplegank.core.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.blanke.simplegank.core.retrofit.RetroFitModule;
import com.blanke.simplegank.core.main.view.CateGoryView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Blanke on 16-1-19.
 */
public class CateGoryFragment extends BaseMvpLceFragment<SwipeRefreshLayout, List<GankBean>, CateGoryView, CateGoryPresenter> implements CateGoryView, SwipeRefreshLayout.OnRefreshListener {

    private final static String KEY_CATEGORY = "category";
    @Bind(R.id.fragment_cate_recyclerview)
    RecyclerView mRecyclerview;
    @Bind(R.id.contentView)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CateGoryBean mCateGoryBean;
    private RecyclerAdapter mAdapter;

    @Inject
    CateGoryPresenter mCateGoryPresenter;

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
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecyclerAdapter();
        mRecyclerview.setAdapter(mAdapter);

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
            holder.mteTextView.setText(bean.getDesc());
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_cate_text)
        TextView mteTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
