package com.piano.android.di.component;

import com.piano.android.di.ActivityScoped;
import com.piano.android.ui.fragment.CourseChildFragment;
import com.piano.android.ui.fragment.MineFavoriteAlbumFragment;
import com.piano.android.ui.fragment.MineFavoriteCourseFragment;
import com.piano.android.ui.fragment.MineFavoriteSingleFragment;
import com.piano.android.ui.fragment.MineFragment;
import com.piano.android.ui.fragment.SongbookLatestFragment;
import com.piano.android.ui.fragment.SongbookRecommendFragment;
import com.piano.android.ui.fragment.SongbookSortFragment;

import dagger.Component;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午10:11
 * @describe:
 */

@ActivityScoped
@Component(dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(SongbookRecommendFragment songbookRecommendFragment);

    void inject(SongbookLatestFragment songbookLatestFragment);

    void inject(CourseChildFragment courseChildFragment);

    void inject(MineFragment mineFragment);

    void inject(MineFavoriteCourseFragment mineFavoriteCourseFragment);

    void inject(MineFavoriteAlbumFragment mineFavoriteAlbumFragment);

    void inject(MineFavoriteSingleFragment mineFavoriteSingleFragment);

    void inject(SongbookSortFragment songbookSortFragment);
}
