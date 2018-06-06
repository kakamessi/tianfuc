package com.chico.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;

/**
 * @author: 陈国权
 * @date: 2018/5/8 下午10:12
 * @describe:
 */

public class VideoUtils {

    /**
     * 打开系统播放器
     *
     * @param context
     * @param url
     */
    public static void openSysVideo(Context context, String url) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

        Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
        mediaIntent.setDataAndType(Uri.parse(url), mimeType);
        context.startActivity(mediaIntent);

    }
}
