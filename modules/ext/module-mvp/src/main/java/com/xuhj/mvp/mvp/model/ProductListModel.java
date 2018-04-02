package com.xuhj.mvp.mvp.model;

import com.xuhj.mvp.http.ApiFactory;
import com.xuhj.mvp.http.api.ApiService;
import com.xuhj.mvp.http.response.GetMovieResp;
import com.xuhj.mvp.model.Product;
import com.xuhj.mvp.mvp.contract.ProductListContract;
import com.xuhj.mvp.mvp.presenter.ProductListPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 描述
 *
 * @author xuhj
 */
public class ProductListModel implements ProductListContract.Model {

    private ProductListPresenter mPresenter;

    public ProductListModel(ProductListPresenter p) {
        this.mPresenter = p;
    }

    @Override
    public List<Product> getProductList() {
        List<Product> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Product data = new Product();
            data.setId(i);
            data.setName("商品名称" + i);
            list.add(data);
        }
        return list;
    }

    @Override
    public Observable<GetMovieResp> httpGetMovie(int start, int count) {
        return ApiFactory.create(ApiService.class).getMovie(start, count);
    }
}
