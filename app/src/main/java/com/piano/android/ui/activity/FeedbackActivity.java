package com.piano.android.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.chico.common.ToastUtils;
import com.chico.dialog.CategoryDialog;
import com.chico.dialog.DialogListener;
import com.piano.android.R;
import com.piano.android.base.BasePresenterActivity;
import com.piano.android.common.CheckUtils;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.ui.mvp.constract.FeedbackConstract;
import com.piano.android.ui.mvp.presenter.FeedbackPresenter;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: 陈国权
 * @date: 2018/4/25 上午12:17
 * @describe: 意见反馈
 */

public class FeedbackActivity extends BasePresenterActivity<FeedbackPresenter> implements FeedbackConstract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.et_content)
    EditText etContent;

    @BindArray(R.array.feedback_category)
    String[] mCategorys;

    private String feedbackType;
    private String feedbackContent;

    @Override
    public int getContentViewId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void injectActivityComponent(ActivityComponent component) {
        super.injectActivityComponent(component);
        component.inject(this);
    }

    @Override
    public void initActivity() {
        super.initActivity();
        setToolbar(toolbar, toolbarTitle, getString(R.string.feedback), true, true);
    }

    @OnClick(R.id.ll_type)
    public void onViewClicked() {
        CategoryDialog categoryDialog = CategoryDialog.newInstance(mCategorys);
        categoryDialog.show(getSupportFragmentManager(), null);
        categoryDialog.setDialogListener(new DialogListener() {
            @Override
            public void onDialogClick(int type, Object object) {
                feedbackType = (String) object;
                tvType.setText(feedbackType);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_submit:
                feedbackContent = etContent.getText().toString();
                if (CheckUtils.checkFeedback(feedbackType, feedbackContent)) {
                    showLoading();
                    presenter.feedback(feedbackType, feedbackContent);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showError(String msg) {
        dismissLoading();
        ToastUtils.showShortToast(this, msg);
    }

    @Override
    public void feedbackSuccess() {
        dismissLoading();
        ToastUtils.showShortToast(this, getString(R.string.feedback_success));
        finish();
    }
}
