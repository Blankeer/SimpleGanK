package com.blanke.simplegank.utils;

import android.content.Context;

/**
 * Created by blanke on 16-1-18.
 */
public class ResUtils {

    public static String getResString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static int getResStringIdByName(Context context, String name) {
        return context.getResources().getIdentifier(context.getPackageName() + ":string/" + name, null, null);
    }

    public static String getResStringByName(Context context, String name) {
        return getResString(context, getResStringIdByName(context, name));
    }

    public static int getResDrawableIdByName(Context context, String name) {
        return context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + name, null, null);
    }
    public static int getResMipmapIdByName(Context context, String name) {
        return context.getResources().getIdentifier(context.getPackageName() + ":mipmap/" + name, null, null);
    }
}
