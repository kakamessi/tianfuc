package com.piano.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.piano.android.R;
import com.piano.android.base.BaseFragment;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.common.Constant;
import com.piano.android.ui.activity.SingleDetailActivity;
import com.piano.android.ui.adapter.SingleAdapter;
import java.util.List;
import butterknife.BindView;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午5:24
 * @describe:
 */

public class SearchSingleFragment extends BaseFragment {

    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;

    private SingleAdapter mAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setUserVisibleHint(true);
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 更新数据
     *
     * @param list
     */
    public void updateData(List<SingleBean> list) {
        if (mAdapter == null) {
            mAdapter = new SingleAdapter(R.layout.item_single, null);
            mRecycleView.setAdapter(mAdapter);
            mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    SingleBean singleBean = (SingleBean) adapter.getData().get(position);
                    Bundle bundle = new Bundle();
                    bundle.putLong(Constant.INTENT_ID, singleBean.getId());
                    startActivity(getActivity(), SingleDetailActivity.class, bundle);
                }
            });
        }
        mAdapter.setNewData(list);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected void initFragment() {

    }
}
