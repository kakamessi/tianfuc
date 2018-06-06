package com.piano.android.http;


import com.piano.android.bean.PageResultBean;
import com.piano.android.bean.ResultBean;
import com.piano.android.bean.course.CourseBean;
import com.piano.android.bean.course.CourseDetailBean;
import com.piano.android.bean.login.LoginBean;
import com.piano.android.bean.songbook.AdvertBean;
import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.bean.songbook.AlbumDetailBean;
import com.piano.android.bean.songbook.BannerBean;
import com.piano.android.bean.songbook.HistoryBean;
import com.piano.android.bean.songbook.SearchResultBean;
import com.piano.android.bean.songbook.SearchWordBean;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.bean.songbook.SingleDetailBean;
import com.piano.android.bean.user.Portrait;
import com.piano.android.bean.user.UserBean;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * @author 陈国权
 * @date 2016/7/22
 * @describe 接口列表
 */
public interface ApiService {


    /**
     * 登陆
     *
     * @param mobile
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("app/login")
    Observable<ResultBean<LoginBean>> login(@Field("mobile") String mobile, @Field("password") String password);


    /**
     * 注册
     *
     * @param mobile
     * @param password
     * @param SMSCode
     * @return
     */
    @FormUrlEncoded
    @POST("app/register")
    Observable<ResultBean<String>> register(@Field("mobile") String mobile, @Field("password") String password, @Field("SMSCode") String SMSCode);


    /**
     * 获取短信验证码
     *
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("app/getSMSCodeRegister")
    Observable<ResultBean<String>> getSMSCodeRegister(@Field("mobile") String mobile);

    /**
     * 忘记密码 获取验证码
     *
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("app/getSMSCodeForget")
    Observable<ResultBean<String>> getSMSCodeForget(@Field("mobile") String mobile);


    /**
     * 修改密码  获取验证码
     *
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("app/getSMSCodePassword")
    Observable<ResultBean<String>> getSMSCodePassword(@Field("mobile") String mobile);

    /**
     * 修改密码
     *
     * @param password
     * @param SMSCode
     * @return
     */
    @FormUrlEncoded
    @POST("app/changePassword")
    Observable<ResultBean<String>> updatePassword(@Field("password") String password, @Field("SMSCode") String SMSCode);


    /**
     * 忘记密码
     *
     * @param password
     * @param SMSCode
     * @return
     */
    @FormUrlEncoded
    @POST("app/forgetPassword")
    Observable<ResultBean<String>> forgetPassword(@Field("mobile") String mobile, @Field("password") String password, @Field("SMSCode") String SMSCode);


    /**
     * 用户反馈
     *
     * @param type
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("app/feedback")
    Observable<ResultBean<String>> feedback(@Field("type") String type, @Field("content") String content);


    /**
     * 获取用户信息
     *
     * @return
     */
    @POST("app/userInfo")
    Observable<ResultBean<UserBean>> getUserInfo();

    /**
     * 修改用户信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/updateUser")
    Observable<ResultBean<String>> updateUserInfo(@FieldMap HashMap<String, Object> map);


    /**
     * 头像上传
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("sys/file/uploadFile")
    Observable<Portrait> uploadPortrait(@Part MultipartBody.Part file);


    /**
     * 获取广告
     *
     * @return
     */
    @POST("app/ad")
    Observable<ResultBean<List<AdvertBean>>> getAdvertList();

    /**
     * 获取轮播列表
     *
     * @return
     */
    @POST("app/banner")
    Observable<ResultBean<List<BannerBean>>> getBannerList();


    /**
     * 获取推荐曲集6张（首页)
     *
     * @return
     */
    @POST("app/recommendSongsetHome")
    Observable<ResultBean<List<AlbumBean>>> getSongbookRecommendAlbum();

    /**
     * 获取推荐单曲10条（首页)
     *
     * @return
     */
    @POST("app/recommendSongHome")
    Observable<ResultBean<List<SingleBean>>> getSongbookRecommendSingle();

    /**
     * 获取所有推荐曲集
     *
     * @param page
     * @param limit
     * @return
     */
    @FormUrlEncoded
    @POST("app/recommendSongsetAll")
    Observable<PageResultBean<List<AlbumBean>>> getAllRecommendAlbum(@Field("page") int page, @Field("limit") int limit);


    /**
     * 获取所有推荐单曲
     *
     * @param page
     * @param limit
     * @return
     */
    @FormUrlEncoded
    @POST("app/recommendSongAll")
    Observable<PageResultBean<List<SingleBean>>> getAllRecommendSingle(@Field("page") int page, @Field("limit") int limit);

    /**
     * 获取最新曲集6张（首页)
     *
     * @return
     */
    @POST("app/newSongsetHome")
    Observable<ResultBean<List<AlbumBean>>> getSongbookLatestAlbum();

    /**
     * 获取最新单曲10条（首页)
     *
     * @return
     */
    @POST("app/newSongHome")
    Observable<ResultBean<List<SingleBean>>> getSongbookLatestSingle();


    /**
     * 获取所有最新曲集
     *
     * @param page
     * @param limit
     * @return
     */
    @FormUrlEncoded
    @POST("app/newSongsetAll")
    Observable<PageResultBean<List<AlbumBean>>> getAllLatestAlbum(@Field("page") int page, @Field("limit") int limit);


    /**
     * 获取所有最新单曲
     *
     * @param page
     * @param limit
     * @return
     */
    @FormUrlEncoded
    @POST("app/newSongAll")
    Observable<PageResultBean<List<SingleBean>>> getAllLatestSingle(@Field("page") int page, @Field("limit") int limit);


    /**
     * 获取曲集详情
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("app/songsetDetail")
    Observable<ResultBean<AlbumDetailBean>> getAlbumDetail(@Field("id") long id);

    /**
     * 获取单曲详情
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("app/songDetail")
    Observable<ResultBean<SingleDetailBean>> getSingleDetail(@Field("id") long id);

    /**
     * 分类筛选曲集
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/filterSongset")
    Observable<PageResultBean<List<AlbumBean>>> getFilterAlbum(@FieldMap HashMap<String, Object> map);


    /**
     * 获取所有热搜词
     *
     * @return
     */
    @POST("app/getSearchWord")
    Observable<ResultBean<List<SearchWordBean>>> getSearchHotWord();

    /**
     * 根据搜索词查找曲谱和曲集
     *
     * @param keywords
     * @return
     */
    @FormUrlEncoded
    @POST("app/searchByWords")
    Observable<ResultBean<SearchResultBean>> getSearchResult(@Field("keywords") String keywords);


    /**
     * 获取浏览历史 传回type的意义:0代表曲谱 1代表视频
     *
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("app/getHistory")
    Observable<ResultBean<List<HistoryBean>>> getHistoryList(@Field("type") int type);


    /**
     * 浏览曲谱或视频或教程时存入历史
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/addToHistory")
    Observable<ResultBean<String>> addHistory(@FieldMap HashMap<String, Object> map);


    /**
     * 获取教程 基础 0,进阶 1,考级 2
     *
     * @param teachType
     * @param keywords
     * @return
     */
    @FormUrlEncoded
    @POST("app/teachListByType")
    Observable<ResultBean<List<CourseBean>>> getCourseList(@Field("teachType") int teachType, @Field("keywords") String keywords);

    /**
     * 获取教程详情
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("app/teachDetail")
    Observable<ResultBean<CourseDetailBean>> getCourseDetail(@Field("id") long id);

    /**
     * 获取我浏览的课程
     *
     * @return
     */
    @POST("app/getMyTeach")
    Observable<ResultBean<List<CourseBean>>> getMyCourse();


    /**
     * 查看我收藏的曲集
     *
     * @return
     */
    @POST("app/collectSongsetList")
    Observable<ResultBean<List<AlbumBean>>> getFavoriteAlbumList();

    /**
     * 收藏曲集
     *
     * @param songsetId
     * @return
     */
    @FormUrlEncoded
    @POST("app/collectSongset")
    Observable<ResultBean<String>> addFavoriteAlbum(@Field("songsetId") long songsetId);

    /**
     * 取消曲集
     *
     * @param songsetId
     * @return
     */
    @FormUrlEncoded
    @POST("app/cancelCollectSongset")
    Observable<ResultBean<String>> cancelFavoriteAlbum(@Field("songsetId") long songsetId);

    /**
     * 查看我收藏的曲谱
     *
     * @return
     */
    @POST("app/collectSongList")
    Observable<ResultBean<List<SingleBean>>> getFavoriteSingleList();


    /**
     * 收藏曲谱
     *
     * @param songId
     * @return
     */
    @FormUrlEncoded
    @POST("app/collectSong")
    Observable<ResultBean<String>> addFavoriteSingle(@Field("songId") long songId);


    /**
     * 取消曲谱
     *
     * @param songId
     * @return
     */
    @FormUrlEncoded
    @POST("app/cancelCollectSong")
    Observable<ResultBean<String>> cancelFavoriteSingle(@Field("songId") long songId);

    /**
     * 查看我收藏的教程
     *
     * @return
     */
    @POST("app/collectTeachList")
    Observable<ResultBean<List<CourseBean>>> getFavoriteCourseList();

    /**
     * 收藏教程
     *
     * @param teachId
     * @return
     */
    @FormUrlEncoded
    @POST("app/collectTeach")
    Observable<ResultBean<String>> addFavoriteCourse(@Field("teachId") long teachId);

    /**
     * 取消教程
     *
     * @param teachId
     * @return
     */
    @FormUrlEncoded
    @POST("app/cancelCollectTeach")
    Observable<ResultBean<String>> cancelFavoriteCourse(@Field("teachId") long teachId);
}
