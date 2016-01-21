package com.blanke.simplegank.consts;

import android.content.Context;

import com.blanke.simplegank.bean.CateGoryBean;
import com.blanke.simplegank.utils.ResUtils;
import com.blanke.simplegank2.R;
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
    public static final String ROOT_URL = "http://gank.avosapps.com/";
    public static final String GANK_URL = "api/data/";
    private static TreeSet<CateGoryBean> cateGoryBeens = null;

    /**
     * 从config.json中读取分类配置
     *
     * @param context
     * @throws IOException
     */
    public synchronized static void init(Context context) throws IOException {
        if (cateGoryBeens != null) {
            return;
        }
        cateGoryBeens = new TreeSet<>();
        InputStream is = context.getAssets().open(ResUtils.getResString(context, R.string.config_name));
        Gson gson = new Gson();
        List<CateGoryBean> list = gson.fromJson(new InputStreamReader(is), new TypeToken<List<CateGoryBean>>() {
        }.getType());
        for (CateGoryBean item : list) {
            item.setType(ResUtils.getResStringByName(context, item.getType()));//读取type
//            item.setIconResId(ResUtils.getResDrawableIdByName(context, item.getIconName()));
            item.setLayoutResId(R.layout.item_cate_recycler);
            KLog.d(item.getType() + item.getPath());
            cateGoryBeens.add(item);
        }
        KLog.d("获取到配置信息," + cateGoryBeens);
    }

    public static TreeSet<CateGoryBean> getCateGoryBeens() {
        return cateGoryBeens;
    }
}
