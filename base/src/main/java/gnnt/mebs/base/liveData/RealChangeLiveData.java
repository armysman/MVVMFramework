package gnnt.mebs.base.liveData;


import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/*******************************************************************
 * RealChangeLiveData.java  2018/11/30
 * <P>
 * 真实改变才会触发的LiveData，当set相同值时，不会触发观察者，如果是第一次注册监听，也不会触发<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class RealChangeLiveData<T> extends MutableLiveData<T> {

    @MainThread
    public void observe(LifecycleOwner owner, final Observer<? super T> observer) {

        // Observe the internal MutableLiveData
        super.observe(owner, new Observer<T>() {
            boolean isFirst = true;

            @Override
            public void onChanged(@Nullable T t) {
                if (!isFirst) {
                    observer.onChanged(t);
                } else {
                    isFirst = false;
                }
            }
        });
    }

    @Override
    public void setValue(T value) {
        // 如果值相同，则不处理
        if (value == getValue()) {
            return;
        }
        super.setValue(value);
    }
}
