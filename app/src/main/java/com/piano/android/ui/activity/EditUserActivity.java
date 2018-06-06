package com.piano.android.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.piano.android.R;
import com.piano.android.base.BaseActivity;
import com.piano.android.common.CheckUtils;
import com.piano.android.common.Constant;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: 陈国权
 * @date: 2018/4/24 下午11:49
 * @describe:
 */

public class EditUserActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.iv_clean)
    ImageView ivClean;

    @Override
    public int getContentViewId() {
        return R.layout.activity_edit_user;
    }

    @Override
    public void initActivity() {
        setToolbar(toolbar, toolbarTitle, "昵称", true, true);
    }

    @OnClick(R.id.iv_clean)
    public void onViewClicked() {
        etName.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_confirm:
                String nickname = etName.getText().toString();
                if (CheckUtils.checkNickname(nickname)) {
                    Intent intent = new Intent();
                    intent.putExtra(Constant.INTENT_NICKNAME, nickname);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
