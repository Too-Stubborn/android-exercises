package com.xuhj.android.base.router.sample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xuhj.android.base.router.R;
import com.xuhj.android.base.router.sample.bean.User;
import com.xuhj.android.base.router.sample.router.RouterConsts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/3/30
 */
@Route(path = RouterConsts.uiSample2, name = "示例二")
public class Sample2Activity extends AppCompatActivity {

    @Autowired(name = "Int")
    int mInt;
    @Autowired(name = "String")
    String mString;
    @Autowired(name = "Boolean")
    boolean mBoolean;
    @Autowired(name = "User")
    User mUser;
    @Autowired(name = "Users")
    ArrayList<User> mUsers;

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        tvMsg.setText(String.valueOf(mInt));
        tvMsg.append("\n" + String.valueOf(mString));
        tvMsg.append("\n" + String.valueOf(mBoolean));
        if (mUser != null) {
            tvMsg.append("\n" + mUser.getName());
        }
        if (mUsers != null) {
            tvMsg.append("\nsize = " + mUsers.size());
        }

    }


}
