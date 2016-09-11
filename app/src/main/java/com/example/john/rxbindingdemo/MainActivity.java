package com.example.john.rxbindingdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Toolbar使用RxToolbar监听点击事件; Snackbar使用RxSnackbar监听;
 * EditText使用RxTextView监听;
 * Adapter使用RxAdapterView.itemClicks()来进行监听
 * 其余使用RxView监听.
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_1)
    Button mBtn1;
    @BindView(R.id.btn_2)
    Button mBtn2;
    @BindView(R.id.cb_1)
    CheckBox mCb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //1防抖处理
        RxView.clicks(mBtn1)
                .throttleFirst(5000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(MainActivity.this, "防抖处理的点击", Toast.LENGTH_SHORT).show();
                    }
                });
        //2按钮的长时间点击
        RxView.longClicks(mBtn1)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(MainActivity.this, "这是按钮的长时间点击", Toast.LENGTH_SHORT).show();
                    }
                });
        //3.用户登录界面当用户完成对某一操作的点击时,就变得高亮
        RxCompoundButton.checkedChanges(mCb1).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                mBtn1.setEnabled(aBoolean);
                mBtn1.setBackgroundResource(aBoolean ? R.color.clickYes : R.color.clickNo);
            }
        });

    }


    //    @OnClick({R.id.btn_1, R.id.btn_2})
    //    public void onClick(final View view) {
    //        Observable
    //                .create(new Observable.OnSubscribe<View>() {
    //                    @Override
    //                    public void call(Subscriber<? super View> subscriber) {
    //                        subscriber.onNext(view);
    //                    }
    //                })
    //                .buffer(5000, TimeUnit.MILLISECONDS)
    //                .subscribeOn(Schedulers.io())
    //                .observeOn(AndroidSchedulers.mainThread())
    //                .subscribe(new Action1<List<View>>() {
    //                    @Override
    //                    public void call(List<View> views) {
    //                        if (views.size() == 0) {
    //                            return;
    //                        }
    //                        View view1 = views.get(views.size() - 1);
    //                        switch (view1.getId()) {
    //                            case R.id.btn_1:
    //                                Toast.makeText(MainActivity.this, "点击了1", Toast.LENGTH_SHORT).show();
    //                                break;
    //                            case R.id.btn_2:
    //                                Toast.makeText(MainActivity.this, "点击了2", Toast.LENGTH_SHORT).show();
    //                                break;
    //                        }
    //                    }
    //                });
    //
    //    }
}
