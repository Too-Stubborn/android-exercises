package com.xuhj.android.base.router.sample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xuhj.android.base.router.R;
import com.xuhj.android.base.router.sample.bean.User;
import com.xuhj.android.base.router.sample.router.RouterConsts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/3/30
 */
@Route(path = RouterConsts.uiSample, name = "示例一")
public class SampleActivity extends AppCompatActivity {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);

        tvMsg.setText("页面1");
    }

    @OnClick(R.id.tv_msg)
    public void onViewClicked() {
//        User user = new User();
//        user.setName("xuhaojie");
//        user.setAddress("HangZhou");

        List<User> users = new ArrayList<>();
        users.add(new User("xiaoming"));
        users.add(new User("xiaohong"));

        ARouter.getInstance()
                .build(RouterConsts.uiSample2)
                .withInt("Int", 222)
                .withString("String", "value")
                .withBoolean("Boolean", false)
                .withParcelable("User", new User("xuhaojie"))
                .withObject("Users", users)
                .navigation();
    }

}
