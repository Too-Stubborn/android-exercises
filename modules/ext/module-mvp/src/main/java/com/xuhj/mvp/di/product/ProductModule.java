package com.xuhj.mvp.di.product;

import com.xuhj.mvp.mvp.contract.ProductListContract;
import com.xuhj.mvp.mvp.presenter.ProductListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑    Code is far away from bug
 * 　　　　┃　　　┃代码无BUG   with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * 描述
 *
 * @author xuhj
 */
@Module
public class ProductModule {

    private ProductListContract.View mView;

    public ProductModule(ProductListContract.View view) {
        this.mView = view;
    }

    @Provides
    @Singleton
    ProductListPresenter provideProductListPresenter(ProductListContract.View view) {
        return new ProductListPresenter(view);
    }

    @Provides
    ProductListContract.View provideView() {
        return mView;
    }
}
