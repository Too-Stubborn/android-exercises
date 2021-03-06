package others.core;

import android.os.Bundle;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import others.core.entity.HttpResult;

/**
 * 管理RxJava生命周期，避免内存泄漏
 * RxJava处理服务器返回
 * <p>
 * Created by Weiss on 2016/12/23.
 */

public abstract class BaseRxActivity extends BaseCoreActivity {

    private CompositeDisposable disposables2Stop;// 管理Stop取消订阅者者
    private CompositeDisposable disposables2Destroy;// 管理Destroy取消订阅者者

    protected abstract int getLayoutId();

    protected abstract void initView();

    /**
     * Rx优雅处理服务器返回
     *
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<HttpResult<T>, T> handleResult() {
        return null;
        // FIXME: 2017/4/20 xuhj 报错先注释
//        return new ObservableTransformer<HttpResult<T>, T>() {
//            @Override
//            public ObservableSource<T> apply(@NonNull Observable<HttpResult<T>> upstream) {
//                return upstream.flatMap(new Function<HttpResult<T>, ObservableSource<?>>() {
//                                            @Override
//                                            public ObservableSource<?> apply(@NonNull HttpResult<T> result) throws Exception {
//                                                if (result.isSuccess()) {
//                                                    return BaseRxActivity.this.createData(result.results);
//                                                } else if (result.isTokenInvalid()) {
//                                                    //处理token时效
//                                                    //tokenInvalid();
//                                                } else {
//                                                    return Observable.error(new Exception(result.msg));
//                                                }
//                                                return Observable.empty();
//                                            }
//                                        }
//                );
//            }
//        };
    }

    private <T> Observable<T> createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> subscriber) throws Exception {
                try {
                    subscriber.onNext(t);
                    subscriber.onComplete();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public boolean addRxStop(Disposable disposable) {
        if (disposables2Stop == null) {
            throw new IllegalStateException(
                    "addUtilStop should be called between onStart and onStop");
        }
        disposables2Stop.add(disposable);
        return true;
    }

    public boolean addRxDestroy(Disposable disposable) {
        if (disposables2Destroy == null) {
            throw new IllegalStateException(
                    "addUtilDestroy should be called between onCreate and onDestroy");
        }
        disposables2Destroy.add(disposable);
        return true;
    }

    public void remove(Disposable disposable) {
        if (disposables2Stop == null && disposables2Destroy == null) {
            throw new IllegalStateException("remove should not be called after onDestroy");
        }
        if (disposables2Stop != null) {
            disposables2Stop.remove(disposable);
        }
        if (disposables2Destroy != null) {
            disposables2Destroy.remove(disposable);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (disposables2Destroy != null) {
            throw new IllegalStateException("onCreate called multiple times");
        }
        disposables2Destroy = new CompositeDisposable();
    }

    public void onStart() {
        super.onStart();
        if (disposables2Stop != null) {
            throw new IllegalStateException("onStart called multiple times");
        }
        disposables2Stop = new CompositeDisposable();
    }

    public void onStop() {
        super.onStop();
        if (disposables2Stop == null) {
            throw new IllegalStateException("onStop called multiple times or onStart not called");
        }
        disposables2Stop.dispose();
        disposables2Stop = null;
    }

    public void onDestroy() {
        super.onDestroy();
        if (disposables2Destroy == null) {
            throw new IllegalStateException(
                    "onDestroy called multiple times or onCreate not called");
        }
        disposables2Destroy.dispose();
        disposables2Destroy = null;
    }
}
