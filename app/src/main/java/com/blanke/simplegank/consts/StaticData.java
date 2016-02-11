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
    public static final String ROOT_URL = "http://gank.avosapps.com/";
    public static final String GANK_URL = "api/data/";
    public static final String GANK_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static TreeSet<CateGoryBean> cateGoryBeens = null;
    private static String preIconName = "icon_";
    public static final int PAGE_COUNT=20;
    public static final int ITEM_WIDTH_DP=200;
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
//            item.setType(ResUtils.getResStringByName(context, item.getType()));//读取type
            item.setIconResId(ResUtils.getResMipmapIdByName(context, preIconName + item.getIcon()));
            KLog.d(item);
//            item.setLayoutResId(R.layout.item_category_text_recycler);
//            item.setIconResId(R.mipmap.icon_android);
            cateGoryBeens.add(item);
        }
        KLog.d("获取到配置信息," + cateGoryBeens);
    }

    public static TreeSet<CateGoryBean> getCateGoryBeens() {
        return cateGoryBeens;
    }
}
