package com.xuhj.mvp.mvp.presenter;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.xuhj.library.http.subscriber.ProgressSubscriber;
import com.xuhj.mvp.http.response.GetMovieResp;
import com.xuhj.mvp.mvp.contract.ProductListContract;
import com.xuhj.mvp.mvp.model.ProductListModel;
import com.xuhj.mvp.uiadapter.product.ProductListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author xuhj
 */
public class ProductListPresenter extends ProductListContract.Presenter {
    private static final String TAG = "ProductListPresenter";

    private List<GetMovieResp.SubjectsBean> mProductList = new ArrayList<>();

    private ProductListAdapter mProductListAdapter;

    public ProductListPresenter(ProductListContract.View view) {
        super(view);
    }

    @Override
    public ProductListContract.Model getModel() {
        return new ProductListModel(this);
    }

    @Override
    public void start() {

        // setProductListAdapter
        mProductListAdapter = new ProductListAdapter(mContext, mProductList);
        mView.setProductListAdapter(mProductListAdapter);

    }

    @Override
    public void fetchProductList() {
//        // mock get data
//        List<Product> list = mModel.getProductList();
//
//        // notify adapter
//        if (list == null)
//            return;
//        mProductListAdapter.addAll(list);
//        mProductListAdapter.notifyDataSetChanged();
//
//        // setupView
//        mView.setupView();
    }

    @Override
    public void fetchMovie() {
        Log.d(TAG, "fetchMovie: ");
        int start = 0;
        int count = 2;

        enqueueWithCustom(mModel.httpGetMovie(start, count),
                new ProgressSubscriber<GetMovieResp>(mContext) {
                    @Override
                    public void onNext(GetMovieResp response) {
                        Log.d(TAG, "fetchMovieSuccess: ");
                        List<GetMovieResp.SubjectsBean> list = response.getSubjects();

                        // notify adapter
                        if (list != null) {
                            mProductListAdapter.addAll(list);
                        }
                        mProductListAdapter.notifyDataSetChanged();

                        mView.httpGetMovieSuccess(response);
                        new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setVersion(1.2).create();
                    }
                });
    }

}
