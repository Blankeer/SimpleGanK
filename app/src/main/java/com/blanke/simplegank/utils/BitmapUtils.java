package com.blanke.simplegank.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.socks.library.KLog;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by blanke on 16-2-3.
 */
public class BitmapUtils {

    public static String getPhotoPath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath();
    }

    public static boolean savaImage(Context context, Bitmap bitmap, String fileName) {
        File f = new File(getPhotoPath(context), fileName);
        KLog.d(f.getAbsolutePath());
        if (!f.exists()) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
