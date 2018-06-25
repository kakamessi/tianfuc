package com.chico.common;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by 陈国权 on 2018/3/26 0026
 */

public class CacheUtils {

    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }


    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            long availableBlocks = (long) stat.getAvailableBlocks() - 4; //获取空闲的数据块的数量
            long freeBlocks = stat.getAvailableBlocks();  //获取单个数据块的大小（byte）
            return freeBlocks * availableBlocks;
        }
        return 0;
    }


    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (!TextUtils.isEmpty(filePath) && filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }


    /**
     * 根据路径获取硬盘状态
     *
     * @param path
     * @return
     */
    public static StatFs getStatFs(String path) {
        try {
            return new StatFs(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算硬盘大小
     *
     * @param stat
     * @return
     */
    public static float calculateSize(StatFs stat) {
        if (stat != null)
            return stat.getAvailableBlocks()
                    * (stat.getBlockSize() / (1024f * 1024f));
        return 0.0f;
    }

    /**
     * 计算可使用硬盘大小
     *
     * @param stat
     * @return
     */
    public static float calculateSizeForG(StatFs stat) {
        if (stat != null)
            return stat.getAvailableBlocks()
                    * (stat.getBlockSize() / (1024f * 1024f * 1024f));
        return 0.0f;
    }


    /**
     * 获取block的SIZE
     *
     * @param stat
     * @return
     */
    public static float allSizeForG(StatFs stat) {
        if (stat != null)
            return stat.getBlockCount()
                    * (stat.getBlockSize() / (1024f * 1024f * 1024f));
        return 0.0f;
    }

    /**
     * 获取缓存
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) {
        long cacheSize = 0;
        try {
            cacheSize = getFolderSize(context.getCacheDir());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cacheSize += getFolderSize(context.getExternalCacheDir());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getFormatSize(cacheSize);
    }


    /**
     * 清空缓存
     *
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        deleteDir(context.getFilesDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
            deleteDir(context.getExternalFilesDir(null));
        }
    }

    /**
     * 删除缓存目录
     *
     * @param dir
     * @return
     */
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        }
        return false;

    }

    /**
     * 获取指定文件总大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化文件大小单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0KB";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 获取文件缓存路径
     * @param context
     * @param dir
     * @return
     */
    public static String getFilePath(Context context,String dir) {
        String directoryPath="";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ) {//判断外部存储是否可用
            directoryPath =context.getExternalFilesDir(dir).getAbsolutePath();
        }else{//没外部存储就使用内部存储
            directoryPath=context.getFilesDir()+File.separator+dir;
        }
        File file = new File(directoryPath);
        if(!file.exists()){//判断文件目录是否存在
            file.mkdirs();
        }
        return directoryPath;
    }

    //判断文件是否存在
    public static boolean fileIsExists(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

}
