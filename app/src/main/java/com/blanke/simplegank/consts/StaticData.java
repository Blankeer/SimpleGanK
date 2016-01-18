package com.blanke.simplegank.consts;

import android.content.Context;

import com.blanke.simplegank.R;
import com.blanke.simplegank.bean.CateGoryBean;
import com.blanke.simplegank.utils.ResUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by blanke on 16-1-18.
 */
public class StaticData {
    private static TreeSet<CateGoryBean> cateGoryBeens = new TreeSet<>();

    /**
     * 从config.json中读取分类配置
     *
     * @param context
     * @throws IOException
     */
    public static void init(Context context) throws IOException {
        InputStream is = context.getAssets().open(ResUtils.getResString(context, R.string.config_name));
        Gson gson = new Gson();
        cateGoryBeens.addAll(gson.fromJson(new InputStreamReader(is), new TypeToken<List<CateGoryBean>>() {
        }.getType()));
        KLog.d("获取到配置信息," + cateGoryBeens);
    }

    public static TreeSet<CateGoryBean> getCateGoryBeens() {
        return cateGoryBeens;
    }
}
