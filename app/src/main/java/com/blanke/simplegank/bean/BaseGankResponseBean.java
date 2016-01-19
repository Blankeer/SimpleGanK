package com.blanke.simplegank.bean;

import java.util.List;

/**
 * Created by Blanke on 16-1-19.
 */
public class BaseGankResponseBean {
    private boolean error;
    private List<GankBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GankBean> getResults() {
        return results;
    }

    public void setResults(List<GankBean> results) {
        this.results = results;
    }

}
