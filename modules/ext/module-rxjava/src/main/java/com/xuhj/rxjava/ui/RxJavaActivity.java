package com.xuhj.rxjava.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.xuhj.rxjava.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class RxJavaActivity extends AppCompatActivity {
    private static final String TAG = "RxJavaActivity";

    @BindView(R.id.edit_text)
    EditText editText;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);

        editText.setKeyListener(new DigitsKeyListener(true, true));

    }

    private void rxOperatorSet() {
        Log.d(TAG, "----------------- rxOperatorSet -----------------");
        Observable
                .just(12, 23, 34, 45, 56)
                .subscribeOn(Schedulers.io())
                // map
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        Log.d(TAG, "map: " + integer.getClass().getSimpleName() + "->" + integer);
                        return "i am " + integer;
                    }
                })
                // filter
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        Log.d(TAG, "filter: " + s.getClass().getSimpleName() + "->" + s);
                        return s.contains("2");
                    }
                })
                // flatmap
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull String s) throws Exception {
                        Log.d(TAG, "flatMap: " + s.getClass().getSimpleName() + "->" + s);
                        List<String> list = new ArrayList<String>();
                        list.add("flatmap:" + s.substring(s.length() - 2, s.length() - 1));
                        list.add("flatmap:" + s.substring(s.length() - 1));
                        return Observable.fromIterable(list);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                // do
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.d(TAG, "doOnSubscribe: ");
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.d(TAG, "doOnNext: ");
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "doOnError: ");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "doOnComplete: ");
                    }
                })
                // subscribe
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: " + Thread.currentThread().getName() + "->" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + Thread.currentThread().getName() + "->" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: " + Thread.currentThread().getName());
                    }
                });
    }

    private void rxDoOnNext() {
        Log.d(TAG, "----------------- rxDoOnNext -----------------");
        Observable
                .just("1", "2")
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.d(TAG, "accept: doOnNext1");
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.d(TAG, "accept: doOnNext2");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.d(TAG, "subscribe: accept: String->" + s);
                    }
                });
    }

    /**
     * 数据转换
     */
    private void rxMap() {
        Log.d(TAG, "----------------- rxMap -----------------");
        Observable
                .just(1, 2)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        Log.d(TAG, "apply: map");
                        return "format to string " + integer;
                    }
                })
                .cast(Integer.class)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.d(TAG, "accept: " + integer.getClass() + "->" + integer);
                    }
                });
    }

    /**
     * 数据类型转换
     */
    private void rxCast() {
        Log.d(TAG, "----------------- rxCast -----------------");
        Observable
                .just("1", "2")
                .cast(Integer.class)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.d(TAG, "accept: " + integer.getClass() + "\n" +
                                "                        Log.d(TAG, \"accept: \" + integer.getClass() + \"->\" + integer);->" + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * observable变换
     */
    private void rxFlatMap() {
        Log.d(TAG, "----------------- rxFlatMap -----------------");
        Observable
                .just(1, 2)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                        Log.d(TAG, "apply: flatMap");
                        List<String> list = new ArrayList<String>();
                        for (int i = 0; i < 3; i++) {
                            list.add("format to string " + integer);
                        }
                        return Observable.fromIterable(list);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.d(TAG, "subscribe: accept: String->" + s);
                    }
                });
    }

    /**
     * 过滤数据
     */
    private void rxFilter() {
        Log.d(TAG, "----------------- rxFilter -----------------");
        Observable
                .just("12", 23, 34, 45)
                .cast(String.class)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        return !s.contains("3");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.d(TAG, "accept: String->" + s);
                    }
                });
    }

    /**
     * 防止数据短时间内重复发送
     */
    private void rxDebounce() {
        Log.d(TAG, "----------------- rxDebounce -----------------");
        Observable
                .just(1, 2, 3, 4)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.d(TAG, "accept: Integer->" + integer);
                    }
                });

    }


    /**
     * 防止重复点击
     */
    private void rxThrottleFirst() {
        Log.d(TAG, "----------------- rxThrottleFirst -----------------");
        Observable
                .just(1, 2, 3, 4, 5)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {

                    }
                });
    }

    /**
     * 合并
     */
    private void rxMerge() {
        Log.d(TAG, "----------------- rxMerge -----------------");

    }

    private void rxZip() {
        Log.d(TAG, "----------------- rxZip -----------------");
        Observable obb1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, "onNext: A");
                e.onNext("A");
                Log.d(TAG, "onNext: B");
                e.onNext("B");
                Log.d(TAG, "onNext: C");
                e.onNext("C");
                Log.d(TAG, "onNext: D");
                e.onNext("D");
            }
        }).subscribeOn(Schedulers.io());
        Observable obb2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "onNext: 1");
                e.onNext(1);
                Log.d(TAG, "onNext: 2");
                e.onNext(2);
                Log.d(TAG, "onNext: 3");
                e.onNext(3);
            }
        }).subscribeOn(Schedulers.io());
        Observable
                .zip(obb1, obb2, new BiFunction<String, Integer, String>() {
                    @Override
                    public String apply(@NonNull String s, @NonNull Integer integer) throws Exception {
                        Log.d(TAG, "apply: zip");
                        return s + "+" + integer;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.d(TAG, "accept: " + s);
                    }
                });
    }

    /**
     * 间隔时间发送数据
     */
    private void rxInterval() {
        Log.d(TAG, "----------------- rxInterval -----------------");
        Observable
                .interval(0, 1, TimeUnit.SECONDS)
                .take(10)
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "doAfterTerminate: ");
                    }
                })
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "doOnTerminate: ");
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Log.d(TAG, "accept: Long->" + aLong);
                    }
                });
    }

    /**
     * 定时器
     */
    private void rxTimer() {
        Log.d(TAG, "----------------- rxTimer -----------------");
        Observable
                .timer(3, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Log.d(TAG, "accept: Long->" + aLong);
                    }
                });
    }

    private void rxAsyncSubjects() {
        Log.d(TAG, "----------------- rxAsyncSubjects -----------------");
        AsyncSubject<String> as = AsyncSubject.create();

        Log.d(TAG, "onNext: 1");
        as.onNext("1");

        as.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d(TAG, "accept: " + s);
            }
        });

        Log.d(TAG, "onNext: A");
        as.onNext("A");

        // 一定要调用onComplete才会回调accept
        Log.d(TAG, "onComplete: ");
        as.onComplete();
    }

    private void rxPublishSubjects() {
        Log.d(TAG, "----------------- rxPublishSubjects -----------------");
        PublishSubject<String> ps = PublishSubject.create();

        Log.d(TAG, "onNext: 1");
        ps.onNext("1");

        ps.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d(TAG, "accept1: " + s);
            }
        });

        Log.d(TAG, "onNext: A");
        ps.onNext("A");

        ps.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d(TAG, "accept2: " + s);
            }
        });

        Log.d(TAG, "onComplete: ");
        ps.onComplete();
    }

    private void rxBehaviorSubjects() {
        Log.d(TAG, "----------------- rxBehaviorSubjects -----------------");
        BehaviorSubject<String> bs = BehaviorSubject.createDefault("-1");

        bs.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d(TAG, "accept1: " + s);
            }
        });

        Log.d(TAG, "onNext: 1");
        bs.onNext("1");

        Log.d(TAG, "onNext: A");
        bs.onNext("A");

        bs.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d(TAG, "accept2: " + s);
            }
        });

        Log.d(TAG, "onComplete: ");
        bs.onComplete();
    }

    private void rxReplaySubjects() {
        Log.d(TAG, "----------------- rxReplaySubjects -----------------");
        ReplaySubject<String> rs = ReplaySubject.create();

        Log.d(TAG, "onNext: 1");
        rs.onNext("1");

        rs.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d(TAG, "accept1: " + s);
            }
        });

        Log.d(TAG, "onNext: A");
        rs.onNext("A");

        rs.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d(TAG, "accept2: " + s);
            }
        });

        Log.d(TAG, "onComplete: ");
        rs.onComplete();
    }

    /**
     * Single
     */
    private void rxSingleObservable() {
        Log.d(TAG, "----------------- rxSingleObservable -----------------");
        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> e) throws Exception {
                Log.d(TAG, "subscribe: message1");
                e.onSuccess("message1");
            }
        }).subscribe(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(@NonNull String s, @NonNull Throwable throwable) throws Exception {
                Log.d(TAG, "accept: String->" + s + "---Throwable->" + throwable);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @OnClick({R.id.btn_rx_operator_set, R.id.btn_rx_map, R.id.btn_rx_flat_map, R.id.btn_rx_zip,
            R.id.btn_async_subject, R.id.btn_publish_subject, R.id.btn_behavior_subject, R.id.btn_replay_subject})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_rx_operator_set:
                rxOperatorSet();
                break;
            case R.id.btn_rx_map:
                rxMap();
                break;
            case R.id.btn_rx_flat_map:
                rxFlatMap();
                break;
            case R.id.btn_rx_zip:
                rxZip();
                break;
            case R.id.btn_async_subject:
                rxAsyncSubjects();
                break;
            case R.id.btn_publish_subject:
                rxPublishSubjects();
                break;
            case R.id.btn_behavior_subject:
                rxBehaviorSubjects();
                break;
            case R.id.btn_replay_subject:
                rxReplaySubjects();
                break;
        }
    }

}
