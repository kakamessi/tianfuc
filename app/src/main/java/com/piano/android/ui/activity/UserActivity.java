package com.piano.android.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chico.common.ToastUtils;
import com.chico.dialog.DialogConstant;
import com.chico.dialog.DialogListener;
import com.chico.dialog.GenderDialog;
import com.chico.dialog.PortraitDialog;
import com.piano.android.BuildConfig;
import com.piano.android.R;
import com.piano.android.base.BasePresenterActivity;
import com.piano.android.bean.user.Portrait;
import com.piano.android.bean.user.UserBean;
import com.piano.android.common.Constant;
import com.piano.android.common.ImageLoad;
import com.piano.android.common.glide.PianoGlideEngine;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.ui.mvp.constract.UserConstract;
import com.piano.android.ui.mvp.presenter.UserPresenter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.chico.common.PhotoUtils.hasSdcard;
import static com.chico.common.PhotoUtils.openPic;

/**
 * @author: 陈国权
 * @date: 2018/4/24 上午12:18
 * @describe: 个人资料
 */

public class UserActivity extends BasePresenterActivity<UserPresenter> implements DialogListener, UserConstract.View {

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int REQUEST_CODE_PHOTO = 300;
    private static final int REQUEST_CODE_CAMERA = 400;

    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;


    //拍照所得到的图像的保存路径
    private Uri imageUri;

    //当前用户拍照或者从相册选择的照片的文件名
    private String fileName;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_portrait)
    ImageView ivPortrait;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_clean_cache)
    TextView tvCleanCache;

    private String sex;
    private String nickname;
    private String mobile;
    private String img;

    @Override
    public int getContentViewId() {
        return R.layout.activity_user;
    }


    @Override
    public void injectActivityComponent(ActivityComponent component) {
        super.injectActivityComponent(component);
        component.inject(this);
    }

    @Override
    public void initActivity() {
        super.initActivity();
        setToolbar(toolbar, toolbarTitle, getString(R.string.edit_person_info), true, true);
        presenter.getUserInfo(false);
    }


    @OnClick({R.id.ll_portrait, R.id.ll_nickname, R.id.ll_sex, R.id.ll_update_password, R.id.tv_clean_cache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_portrait:
                PortraitDialog portraitDialog = PortraitDialog.newInstance();
                portraitDialog.show(getSupportFragmentManager(), null);
                portraitDialog.setDialogListener(this);
                break;
            case R.id.ll_nickname:
                startActivity(this, EditUserActivity.class, Constant.REQUEST_CODE_OK);
                break;
            case R.id.ll_sex:
                GenderDialog genderDialog = GenderDialog.newInstance();
                genderDialog.show(getSupportFragmentManager(), null);
                genderDialog.setDialogListener(this);
                break;
            case R.id.ll_update_password:
                if (!TextUtils.isEmpty(mobile)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.INTENT_MOBILE, mobile);
                    startActivity(this, UpdatePasswordActivity.class, bundle);
                }
                break;
            case R.id.tv_clean_cache:
                presenter.cleanCache();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDialogClick(int type, Object object) {
        if (type == DialogConstant.DIALOG_GENDER) {
            sex = String.valueOf(object);
            if (sex.equals("0")) {
                tvSex.setText("男");
            } else {
                tvSex.setText("女");
            }
            updateUserInfo();
        } else if (type == DialogConstant.DIALOG_CAMERA) {
            autoObtainCameraPermission();
        } else if (type == DialogConstant.DIALOG_ALBUM) {
            autoObtainStoragePermission();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_PHOTO) {
            List<String> mSelected = Matisse.obtainPathResult(data);
            if (mSelected != null && mSelected.size() > 0) {
                uploadPortrait(mSelected.get(0));
            }
        } else if (requestCode == REQUEST_CODE_CAMERA) {
            File picPath = new File(imageUri.getPath());
            uploadPortrait(picPath.getAbsolutePath());
        }
    }


    private void updateUserInfo() {
        HashMap<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(nickname)) {
            map.put("username", nickname);
        }

        if (!TextUtils.isEmpty(sex)) {
            map.put("sex", sex);
        }

        if (!TextUtils.isEmpty(img)) {
            map.put("img", img);
        }
        presenter.updateUserInfo(map);

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void setUserInfo(UserBean bean) {
        if (bean != null) {
            if (TextUtils.isEmpty(bean.getImg())) {
                ImageLoad.loadImageCircle(this, R.drawable.icon_default_portrait, ivPortrait);
            } else {
                ImageLoad.loadImageCircle(this, bean.getImg(), ivPortrait);
            }

            tvNickname.setText(bean.getUsername());

            if ("1".equals(bean.getSex())) {
                tvSex.setText("女");
            } else {
                tvSex.setText("男");
            }

            mobile = bean.getMobile();
        }
    }

    @Override
    public void updateSuccess() {
        ToastUtils.showShortToast(this, "修改成功");
        presenter.getUserInfo(true);
    }

    @Override
    public void cleanSuccess() {
        ToastUtils.showShortToast(this, "缓存清理成功");
    }

    @Override
    public void uploadPortraitSuccess(Portrait bean) {
        if (bean != null) {
            ToastUtils.showShortToast(this, "头像上传成功");
            img = BuildConfig.BASE_IMAGE_URL + bean.getPath();
            updateUserInfo();
        }
    }


    private void uploadPortrait(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            MultipartBody.Part body = null;
            File picFile = new File(path);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), picFile);
            body = MultipartBody.Part.createFormData("file", picFile.getName(), requestFile);
            presenter.uploadPortrait(body);
        } else {
            ToastUtils.showShortToast(this, "图片异常");
        }
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastUtils.showShortToast(this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                takePhoto();
            } else {
                ToastUtils.showShortToast(this, "设备没有SD卡！");
            }
        }
    }

    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            Matisse.from(this)
                    .choose(MimeType.ofImage())
                    .showSingleMediaType(true)
                    .countable(true)
                    .captureStrategy(
                            new CaptureStrategy(true, "com.piano.android.fileprovider"))
                    .maxSelectable(1)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new PianoGlideEngine())
                    .forResult(REQUEST_CODE_PHOTO);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        takePhoto();
                    } else {
                        ToastUtils.showShortToast(this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShortToast(this, "请允许打开相机！！");
                }
                break;
            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openPic(this, CODE_GALLERY_REQUEST);
                } else {

                    ToastUtils.showShortToast(this, "请允许打操作SDCard！！");
                }
                break;
        }
    }


    /**
     * 当用户点击按钮时，打开摄像头进行拍照
     */
    public void takePhoto() {
        //用时间戳的方式来命名图片文件，这样可以避免文件名称重复
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        this.fileName = "Piano" + format.format(date);

        // 创建一个File对象，用于存放拍照所得到的照片
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File outputImage = new File(path, this.fileName + ".jpg");

        //以防万一，看一下这个文件是不是存在，如果存在的话，先删除掉
        if (outputImage.exists()) {
            outputImage.delete();
        }

        try {
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将File对象转换为Uri对象，以便拍照后保存
        this.imageUri = Uri.fromFile(outputImage);

        //启动系统的照相Intent
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE"); //Android系统自带的照相intent
        intent.putExtra(MediaStore.EXTRA_OUTPUT, this.imageUri); //指定图片输出地址
        startActivityForResult(intent, REQUEST_CODE_CAMERA); //以forResult模式启动照相intent
    }
}
