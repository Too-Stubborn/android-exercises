package com.xuhj.mvp.mvp.contract;

import com.xuhj.mvp.base.AppBaseModel;
import com.xuhj.mvp.base.AppBasePresenter;
import com.xuhj.mvp.base.AppBaseView;
import com.xuhj.mvp.http.response.GetMovieResp;
import com.xuhj.mvp.model.Product;
import com.xuhj.mvp.uiadapter.product.ProductListAdapter;

import java.util.List;

import io.reactivex.Observable;

/**
 * 描述
 *
 * @author xuhj
 */
public interface ProductListContract {

    /**
     * Model
     */
    interface Model extends AppBaseModel {

        List<Product> getProductList();

        Observable<GetMovieResp> httpGetMovie(int start, int count);

    }

    /**
     * View
     */
    interface View extends AppBaseView {

        void initView();

        void setupView();

        void setProductListAdapter(ProductListAdapter adapter);

        void httpGetMovieSuccess(GetMovieResp data);

        void httpGetMovieFailure(Throwable t);
    }

    /**
     * Presenter
     */
    abstract class Presenter extends AppBasePresenter<Model, View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void fetchProductList();

        public abstract void fetchMovie();
    }
}
