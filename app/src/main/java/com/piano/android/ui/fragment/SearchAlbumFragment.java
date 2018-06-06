package com.piano.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chico.common.DensityUtils;
import com.piano.android.R;
import com.piano.android.base.BaseFragment;
import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.common.Constant;
import com.piano.android.ui.activity.AlbumDetailActivity;
import com.piano.android.ui.adapter.AlbumAdapter;
import com.piano.android.widget.GridItemDecoration;
import java.util.List;
import butterknife.BindView;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午5:24
 * @describe:
 */

public class SearchAlbumFragment extends BaseFragment {

    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;

    private AlbumAdapter mAdapter;

    /**
     * 更新数据
     *
     * @param list
     */
    public void updateData(List<AlbumBean> list) {
        if (mAdapter == null) {
            mAdapter = new AlbumAdapter(R.layout.item_album, null);
            mRecycleView.setAdapter(mAdapter);
            mRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            mRecycleView.addItemDecoration(new GridItemDecoration(3, DensityUtils.dp2px(getActivity(), 5), true, 0));
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    AlbumBean bean = (AlbumBean) adapter.getData().get(position);
                    Bundle bundle = new Bundle();
                    bundle.putLong(Constant.INTENT_ID, bean.getId());
                    startActivity(getActivity(), AlbumDetailActivity.class, bundle);
                }
            });
        }

        mAdapter.setNewData(list);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_search_result;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setUserVisibleHint(true);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initFragment() {
        Log.e("=====>", "1111111111");
    }

}
