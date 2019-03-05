package gnnt.mebs.common.util;


import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*******************************************************************
 * InputMethodUtils.java  2018/12/4
 * <P>
 * 输入法工具类<br/>
 * <br/>
 * </p>
 * Copyright2018 by GNNT Company. All Rights Reserved.
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class InputMethodUtils {

    /**
     * 隐藏输入法
     *
     * @param focusView 当前文本框
     */
    public static void hiddenInputMethod(View focusView) {
        if (focusView == null || focusView.getWindowToken() == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) focusView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
    }

    /**
     * 当滚动时隐藏输入法
     *
     * @param recyclerView recyclerView
     * @param focusView    焦点View
     */
    public static final void hiddenInputWhenScrolling(@NonNull RecyclerView recyclerView, @NonNull final View focusView) {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    InputMethodUtils.hiddenInputMethod(focusView);
                }
            }
        });
    }

    /**
     * 显示输入法
     *
     * @param focusView 焦点视图
     */
    public static void showInputMethod(View focusView) {
        if (focusView == null) {
            return;
        }
        focusView.requestFocus();
        InputMethodManager imm = (InputMethodManager) focusView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusView, 0);
    }
}