package com.xuhj.mvp.ui.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xuhj.mvp.R;
import com.xuhj.mvp.base.AppBaseActivity;

/**
 * 描述
 *
 * @author xuhj
 */
public class ProductListActivity extends AppBaseActivity {
    private static final String TAG = "ProductListActivity";

    private ProductListFragment mProductListFragment;

    public static void newInstance(Context context) {
        Intent i = new Intent(context, ProductListActivity.class);
        context.startActivity(i);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.product_list_act;
    }

    @Override
    protected void onInitActivity(Bundle savedInstanceState) {
        super.onInitActivity(savedInstanceState);
        mProductListFragment = ProductListFragment.newInstance(0);
        attachFragment(R.id.container, mProductListFragment, ProductListFragment.class.getSimpleName());
    }

}
