package com.xuhj.library.http.base;

import java.util.List;

/**
 * 返回业务Data层封装，单个对象和数据集
 *
 * @author xuhj
 */
public class DataResp<T, F> {

    // 单个对象
    private T parameter;
    // 数据集
    private List<F> dataset;

    public T getParameter() {
        return parameter;
    }

    public void setParameter(T parameter) {
        this.parameter = parameter;
    }

    public List<F> getDataset() {
        return dataset;
    }

    public void setDataset(List<F> dataset) {
        this.dataset = dataset;
    }
}
