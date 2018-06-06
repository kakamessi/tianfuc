package com.piano.android.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.piano.android.App;
import com.piano.android.R;
import com.piano.android.base.BasePresenterActivity;
import com.piano.android.bean.course.CourseBean;
import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.bean.songbook.SearchResultBean;
import com.piano.android.bean.songbook.SearchWordBean;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.ui.adapter.HistoryWordAdapter;
import com.piano.android.ui.adapter.TabPageAdapter;
import com.piano.android.ui.fragment.SearchAlbumFragment;
import com.piano.android.ui.fragment.SearchCourseFragment;
import com.piano.android.ui.fragment.SearchSingleFragment;
import com.piano.android.ui.mvp.constract.SearchConstract;
import com.piano.android.ui.mvp.presenter.SearchPresenter;
import com.piano.android.widget.TagView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author: 陈国权
 * @date: 2018/4/26 下午9:51
 * @describe: 搜索
 */

public class SearchActivity extends BasePresenterActivity<SearchPresenter> implements SearchConstract.View {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_clean)
    ImageView ivClean;
    @BindView(R.id.ll_default)
    LinearLayout llDefault;
    @BindView(R.id.tag_view)
    TagView tagView;
    @BindView(R.id.recycle_view_history)
    RecyclerView recycleViewHistory;

    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.view_page)
    ViewPager viewPage;
    @BindView(R.id.ll_search_result)
    LinearLayout llSearchResult;

    @BindView(R.id.ll_empty)
    LinearLayout emptyLayout;
    @BindView(R.id.empty_text)
    TextView tvEmpty;
    @BindView(R.id.ll_error)
    LinearLayout erroLayout;

    @BindArray(R.array.search_tab)
    String[] mTitles;

    private HistoryWordAdapter historyWordAdapter;


    private ArrayList<Fragment> mFragments;
    private TabPageAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void injectActivityComponent(ActivityComponent component) {
        super.injectActivityComponent(component);
        component.inject(this);
    }

    @Override
    public void initActivity() {
        super.initActivity();

        presenter.getHistoryWord();
        presenter.getSearchHotWord();
    }


    @OnClick({R.id.tv_cancel, R.id.iv_clean})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.iv_clean:
                etSearch.setText("");
                break;
        }
    }

    @OnTextChanged(value = R.id.et_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())) {
            ivClean.setVisibility(View.GONE);
            llDefault.setVisibility(View.VISIBLE);
            llSearchResult.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);
            erroLayout.setVisibility(View.GONE);
        } else {
            ivClean.setVisibility(View.VISIBLE);
            llDefault.setVisibility(View.GONE);
            llSearchResult.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            erroLayout.setVisibility(View.GONE);
            App.mInstance.setSearchWord(etSearch.getText().toString());
            presenter.getSearchResult(etSearch.getText().toString());
        }
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void setSearchHotWord(List<SearchWordBean> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        tagView.setTags(list);
        tagView.setTagClickListener(new TagView.TagClickListener() {
            @Override
            public void onTagItemClick(SearchWordBean bean) {
                etSearch.setText(bean.getName());
            }
        });
    }

    @Override
    public void setHistoryWord(List<String> list) {
        historyWordAdapter = new HistoryWordAdapter(R.layout.item_history_word, list);
        recycleViewHistory.setAdapter(historyWordAdapter);
        recycleViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recycleViewHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        historyWordAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_name:
                        etSearch.setText((String) adapter.getData().get(position));
                        break;
                    case R.id.iv_delete:
                        App.getInstance().removeSearchWord(position);
                        adapter.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void setSearchResult(SearchResultBean bean) {
        if (bean != null) {


            List<SingleBean> singleBeanList = bean.getSongList();
            List<AlbumBean> albumBeanList = bean.getSongsetList();
            List<CourseBean> courseBeanList = bean.getTeachList();

            if ((singleBeanList == null || singleBeanList.size() == 0)
                    && (albumBeanList == null || albumBeanList.size() == 0)
                    && (courseBeanList == null || courseBeanList.size() == 0)) {
                tvEmpty.setText("未找到于"+etSearch.getText().toString()+"相关的结果");
                emptyLayout.setVisibility(View.VISIBLE);
                erroLayout.setVisibility(View.GONE);
                llDefault.setVisibility(View.GONE);
                llSearchResult.setVisibility(View.GONE);
                return;
            } else {
                emptyLayout.setVisibility(View.GONE);
                llDefault.setVisibility(View.GONE);
                erroLayout.setVisibility(View.GONE);
                llSearchResult.setVisibility(View.VISIBLE);

                if (mFragments == null || mFragments.size() == 0) {
                    mFragments = new ArrayList<>();
                    mFragments.add(new SearchSingleFragment());
                    mFragments.add(new SearchAlbumFragment());
                    mFragments.add(new SearchCourseFragment());
                    adapter = new TabPageAdapter(getSupportFragmentManager(), mFragments, mTitles);
                    viewPage.setAdapter(adapter);
                    viewPage.setCurrentItem(0);
                    viewPage.setOffscreenPageLimit(adapter.getCount());
                    tabLayout.setViewPager(viewPage, mTitles);
                }


                if (singleBeanList != null && singleBeanList.size() > 0) {
                    ((SearchSingleFragment) mFragments.get(0)).updateData(singleBeanList);
                }

                if (albumBeanList != null && albumBeanList.size() > 0) {
                    ((SearchAlbumFragment) mFragments.get(1)).updateData(albumBeanList);
                }

                if (courseBeanList != null && courseBeanList.size() > 0) {
                    ((SearchCourseFragment) mFragments.get(2)).updateData(courseBeanList);
                }
            }
        }
    }

    @Override
    public void setSearchError() {
        emptyLayout.setVisibility(View.GONE);
        erroLayout.setVisibility(View.VISIBLE);
        llDefault.setVisibility(View.GONE);
        llSearchResult.setVisibility(View.GONE);
    }
}
