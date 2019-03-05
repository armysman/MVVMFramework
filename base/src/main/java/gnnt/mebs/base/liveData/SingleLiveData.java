package gnnt.mebs.base.liveData;


import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/*******************************************************************
 * SingleLiveData.java  2018/11/29
 * <P>
 * 该 LiveData 在值更新后只会进行一次分发，避免配置改变重建再次触发<br/>
 * 可以用于显示弹窗或者SnackBar等信息<br/>
 * <br/>
 * </p>
 * Copyright2018 by GNNT Company. All Rights Reserved.
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class SingleLiveData<T> extends MutableLiveData<T> {

    private static final String TAG = "SingleLiveData";

    private final AtomicBoolean mPending = new AtomicBoolean(false);

    @MainThread
    public void observe(@NonNull LifecycleOwner owner,@NonNull final Observer<? super T> observer) {

        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
        }

        // Observe the internal MutableLiveData
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                if (mPending.compareAndSet(true, false)) {
                    observer.onChanged(t);
                }
            }
        });
    }

    @MainThread
    public void setValue(@Nullable T t) {
        mPending.set(true);
        super.setValue(t);
    }

    /**
     * 在 T 类型为 Void 时使用
     */
    @MainThread
    public void call() {
        setValue(null);
    }
}