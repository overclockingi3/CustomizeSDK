package com.overc_i3.mars.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by Mars on 2017-10-21.
 */

public class FileUtils {

    /**
     * 获取文件的MD5值 该操作为耗时操作，切勿在主线程中调用
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        MessageDigest messageDigest;
        RandomAccessFile randomAccessFile = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            if (file == null) {
                return "";
            }
            if (!file.exists()) {
                return "";
            }
            randomAccessFile = new RandomAccessFile(file, "r");
            byte[] bytes = new byte[1024 * 1024 * 10];
            int len;
            while ((len = randomAccessFile.read(bytes)) != -1) {
                //对于较大的文件计算MD5,我们不要一次将文件读取然后调用update方法,
                // 不然执行update方法时就会出现OOM
                messageDigest.update(bytes, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            String md5 = bigInt.toString(16);
            while (md5.length() < 32) {//补全到32位
                md5 = "0" + md5;
            }
            return md5;
        } catch (Exception e) {
            Log.e("FileUtils##getFileMD5" , e.getMessage());
        }finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                    randomAccessFile = null;
                }
            } catch (IOException e) {
                Log.e("FileUtils##getFileMD5" , e.getMessage());
            }
        }
        return "";
    }

    public static String getMd5(String str){
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return "";
        }
        byte[] bs = messageDigest.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs) {
            if((x & 0xff)>>4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }

    /**
     * 获得文件SHA-512值，该值有128位，减少碰撞的概率（判断秒传时，如果传过去的MD5值成功匹配
     * 服务器返回文件的SHA-512值，与本地文件对比，如果相同，则秒传成功，否则要重新上传该文件）
     * 耗时操作，切勿在主线程中调用
     * @param file
     * @return
     */
    public static String getFileSHA512(File file) {
        MessageDigest messageDigest;
        RandomAccessFile randomAccessFile = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
            if (file == null) {
                return "";
            }
            if (!file.exists()) {
                return "";
            }
            randomAccessFile = new RandomAccessFile(file, "r");
            byte[] bytes = new byte[1024 * 1024 * 10];
            int len;
            while ((len = randomAccessFile.read(bytes)) != -1) {
                //对于较大的文件计算MD5,我们不要一次将文件读取然后调用update方法,
                // 不然执行update方法时就会出现OOM
                messageDigest.update(bytes, 0, len);
            }

            byte byteBuffer[] = messageDigest.digest();
            // 將 byte 轉換爲 string
            StringBuffer strHexString = new StringBuffer();
            // 遍歷 byte buffer
            for (int i = 0; i < byteBuffer.length; i++)
            {
                String hex = Integer.toHexString(0xff & byteBuffer[i]);
                if (hex.length() == 1)
                {
                    strHexString.append('0');
                }
                strHexString.append(hex);
            }
            // 得到返回結果
            String sha512 = strHexString.toString();
            return sha512;
        } catch (Exception e) {
            Log.e("FileUtils#getFileSHA512" , e.getMessage());
        }finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                    randomAccessFile = null;
                }
            } catch (IOException e) {
                Log.e("FileUtils#getFileSHA512" , e.getMessage());
            }
        }
        return "";
    }

    public static File getDownloadFolder(){
        String path = Environment.getExternalStorageDirectory() + File.separator + "edp";
        File folder = new File(path);
        if (!folder.exists()){
            folder.mkdirs();
        }
        File downloadFolder = new File(folder,"download");
        if (!downloadFolder.exists()){
            downloadFolder.mkdir();
        }
        return downloadFolder;
    }

    public static File getPreviewFolder(){
        String path = Environment.getExternalStorageDirectory() + File.separator + "edp";
        File folder = new File(path);
        if (!folder.exists()){
            folder.mkdirs();
        }
        File downloadFolder = new File(folder,"preview");
        if (!downloadFolder.exists()){
            downloadFolder.mkdir();
        }
        return downloadFolder;
    }

    /**
     * 清空文件夹
     * @return
     */
    public static void delAllFile(File file,boolean deleteRootFlag) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {//否则如果它是一个目录
            File[] files = file.listFiles();//声明目录下所有的文件 files[];
            for (int i = 0; i < files.length; i++) {//遍历目录下所有的文件
                delAllFile(files[i],true);//把每个文件用这个方法进行迭代
            }
            if (deleteRootFlag){
                file.delete();//删除文件夹
            }
        }
    }

    /**
     * 计算文件大小
     * @param file
     * @return
     */
    public static long getTotalSizeOfFilesInDir(File file) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;
    }

    /**
     * 获取音频在磁盘缓存文件
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getAudioCacheDir(Context context, String uniqueName) {
        String cachePath;
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) && context.getExternalCacheDir() != null) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        File dir = new File(cachePath, "Audio");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return new File(dir + File.separator + uniqueName);
    }

    public static byte[] getFileToByte(File file) {
        byte[] by = new byte[(int) file.length()];
        try {
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return by;
    }

    /**
     * 检验文件名是否合法 文件名只允许存在中文、英文、数字和下划线
     * @param name
     * @return
     */
    public static boolean checkFileName(String name){
        String pattern = "[\u4e00-\u9fa5\\w\\-]+";
        if (name != null && name.matches(pattern)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获得图片的缩略图
     * @param path
     * @return
     */
    public static String getImageThumb(String path){
        if (TextUtils.isEmpty(path)){
            return null;
        }
        File file = new File(path);
        if (!file.exists()){
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        int height = options.outHeight;
        int inSampleSize = 1;
        if (height > 600){
            inSampleSize = height / 600;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,bos);
        bitmap.recycle();
        byte[] datas = bos.toByteArray();
        String thumb = Base64.encodeToString(datas, Base64.NO_WRAP);
        return thumb;
    }

}
