package com.xuhj.mvp.ui.product;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xuhj.library.base.rxbus.RxBus;
import com.xuhj.library.base.rxbus.Subscribe;
import com.xuhj.library.base.rxbus.ThreadMode;
import com.xuhj.mvp.R;
import com.xuhj.mvp.uiadapter.product.ProductListAdapter;
import com.xuhj.mvp.base.AppBaseFragment;
import com.xuhj.mvp.http.response.GetMovieResp;
import com.xuhj.mvp.model.User;
import com.xuhj.mvp.mvp.contract.ProductListContract;
import com.xuhj.mvp.mvp.presenter.ProductListPresenter;

import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 描述
 *
 * @author xuhj
 */
public class ProductListFragment extends AppBaseFragment<ProductListPresenter> implements ProductListContract.View {
    private static final String TAG = "ProductListFragment";

    @Bind(R.id.lv_product_list)
    ListView lvProductList;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_fetch)
    Button btnFetch;

    public static ProductListFragment newInstance(int index) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected ProductListPresenter getPresenter() {
        return new ProductListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.product_list_frag;
    }

    @Override
    protected void onInitFragment(Bundle savedInstanceState) {
        super.onInitFragment(savedInstanceState);
        initView();
        mPresenter.start();

        RxBus.getInstance().register(this);
    }


    @Override
    public void initView() {
        tvTitle.setText("商品列表");
    }

    @Override
    public void setProductListAdapter(ProductListAdapter adapter) {
        lvProductList.setAdapter(adapter);
    }

    @Override
    public void setupView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxBus.getInstance().unregister(this);
    }

    @Override
    public void httpGetMovieSuccess(GetMovieResp data) {
        tvTitle.setText(data.getTitle());
        Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void httpGetMovieFailure(Throwable t) {
        Toast.makeText(mContext, "failure", Toast.LENGTH_SHORT).show();
    }

    @Subscribe(code = 1, threadMode = ThreadMode.MAIN, isStickyEvent = false)
    public void receiverProduct(User user) {
        Log.d(TAG, "receiverProduct: ");
    }

    @Subscribe(code = 2)
    public void receiverProduct2(User user) {
        Log.d(TAG, "receiverProduct2: ");
    }

    @OnClick({R.id.tv_title, R.id.btn_fetch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                break;
            case R.id.btn_fetch:
                User user = new User();

                user.setName("i am back " + new Random().nextInt(100));
                RxBus.getInstance().post(1, user);

                mPresenter.fetchMovie();
                break;
        }
    }
}
