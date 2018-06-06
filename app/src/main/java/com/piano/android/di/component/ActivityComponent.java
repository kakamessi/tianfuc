package com.piano.android.di.component;


import com.piano.android.di.ActivityScoped;
import com.piano.android.ui.activity.AlbumActivity;
import com.piano.android.ui.activity.AlbumDetailActivity;
import com.piano.android.ui.activity.CourseDetailActivity;
import com.piano.android.ui.activity.FeedbackActivity;
import com.piano.android.ui.activity.ForgetPasswordActivity;
import com.piano.android.ui.activity.HistoryActivity;
import com.piano.android.ui.activity.LoginActivity;
import com.piano.android.ui.activity.MainActivity;
import com.piano.android.ui.activity.MineCourseActivity;
import com.piano.android.ui.activity.RegisterActivity;
import com.piano.android.ui.activity.SearchActivity;
import com.piano.android.ui.activity.SingleActivity;
import com.piano.android.ui.activity.SingleDetailActivity;
import com.piano.android.ui.activity.UpdatePasswordActivity;
import com.piano.android.ui.activity.UserActivity;

import dagger.Component;

/**
 * @author 陈国权
 * @date 2018/3/2 0002
 */

@ActivityScoped
@Component(dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);

    void inject(AlbumActivity albumActivity);

    void inject(SingleActivity singleActivity);

    void inject(UserActivity userActivity);

    void inject(UpdatePasswordActivity updatePasswordActivity);

    void inject(FeedbackActivity feedbackActivity);

    void inject(CourseDetailActivity courseDetailActivity);

    void inject(MineCourseActivity mineCourseActivity);

    void inject(AlbumDetailActivity albumDetailActivity);

    void inject(SingleDetailActivity singleDetailActivity);

    void inject(SearchActivity searchActivity);

    void inject(ForgetPasswordActivity forgetPasswordActivity);

    void inject(HistoryActivity historyActivity);

    void inject(MainActivity mainActivity);
}
