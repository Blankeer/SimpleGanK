package com.example;

public class Dp2PxTools {
    private String[] dpis = {"ldpi", "mdpi", "hdpi", "xhdpi", "xxhdpi"};
    private int[] densitys = {120, 160, 240, 320, 480};//dpi分别对应
    private int baseDensity = 160;

    private int dp2px(float dp, int density) {
        return (int) (dp * (density * 1.0 / baseDensity) + 0.5f);
    }


    private int px2dp(float px, int density) {
        return (int) (px / (density * 1.0 / baseDensity) + 0.5f);
    }

    public int[] dp2pxDpis(int dp) {
        System.out.println("-> "+dp + "dp to px");
        int[] re = new int[dpis.length];
        for (int i = 0; i < dpis.length; i++) {
            re[i] = dp2px(dp, densitys[i]);
            System.out.println(dpis[i] + " : " + re[i] + " px");
        }
        return re;
    }

    public int[] px2dpDpis(int px) {
        System.out.println("-> "+px + "px to dp");
        int[] re = new int[dpis.length];
        for (int i = 0; i < dpis.length; i++) {
            re[i] = px2dp(px, densitys[i]);
            System.out.println(dpis[i] + " : " + re[i] + " dp");
        }
        return re;
    }

    public static void main(String[] args) {
        Dp2PxTools tools = new Dp2PxTools();
        tools.dp2pxDpis(24);
    }

}
