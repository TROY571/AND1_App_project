package com.outsideweather.cn.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * email：
 * description：进度弹框
 */
public class ShowTosetDialog {

    private Activity mActivity;
    private Fragment mFragment;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    AlertDialog mAlertDialog;
    ProgressDialog mLoadingDialog;
    public ShowTosetDialog(Activity activity, Fragment fragment) {
        mActivity = activity;
        mFragment = fragment;
    }

    public Context getContext() {
        if (mActivity != null) return mActivity;
        return mFragment.getContext();
    }

    public Activity getActivity() {
        if (mActivity != null) return mActivity;
        return mFragment.getActivity();
    }

    public InputMethodManager getInputMethodManager() {
        InputMethodManager m;
        m = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return m;
    }

    /**
     * @param show 显示或隐藏输入法键盘
     */
    public boolean showSoftInput(boolean show, View view) {
        if (show) {
            getInputMethodManager().showSoftInput(view, 0);
        } else {
            IBinder token = view.getWindowToken();
            if (token == null) return false;
            getInputMethodManager().hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return true;
    }



    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        return dip2px(getContext(), dpValue);
    }

    public static int dip2px(Context context, float dpValue) {
        if (dpValue == 0) return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void toast(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) return;
        Context ctxt = getContext();
        if (ctxt == null) return;
        TostUtil.showTost(ctxt, msg.toString());
        //Toast.makeText(ctxt, msg, Toast.LENGTH_SHORT).show();
    }

    public void alert(CharSequence msg) {
        //alert(msg, null);
        /**
         * 测试说修改弹框样式 全部用吐司弹框
         */
        Context ctxt = getContext();
        if (ctxt == null) return;
        Toast toast = Toast.makeText(ctxt,
                msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        //Toast.makeText(ctxt, msg, Toast.LENGTH_SHORT).show();
    }

    public void alert(CharSequence msg, CharSequence ok) {
        /**
         * 测试说修改弹框样式 全部用吐司弹框
         */
        Context ctxt = getContext();
        if (ctxt == null) return;
        Toast toast = Toast.makeText(ctxt,
                msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        //alert(msg, ok, null);
    }

    public void alert(CharSequence msg, CharSequence ok, DialogInterface.OnClickListener listener) {
        if (TextUtils.isEmpty(msg)) {
            if (mAlertDialog != null) mAlertDialog.dismiss();
            return;
        }
        Context ctxt = getContext();
        if (ctxt == null) return;
        if (ok == null) ok = "知道了";
        if (mAlertDialog == null) mAlertDialog = new AlertDialog.Builder(ctxt).create();
        mAlertDialog.setCancelable(true);
        mAlertDialog.setMessage(msg);
        mAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE, ok, listener);
        mAlertDialog.show();
    }

    public void LoadingNoBaBackground(CharSequence msg) {
        noBackgroudloading(msg, true);
    }

    public void loading(CharSequence msg) {
        loading(msg, true);
    }

    public void noBackgroudloading(CharSequence msg, boolean cancelable) {
        if (msg == null) {
            if (mLoadingDialog != null) mLoadingDialog.dismiss();
            return;
        }
        Context ctxt = getContext();
        if (ctxt == null) return;
        if ("".equals(msg)) msg = "加载中...";
        if (mLoadingDialog == null) mLoadingDialog = new ProgressDialog(ctxt) {
            TextView mMsgView = new TextView(getContext());

            @Override
            protected void onCreate(Bundle state) {
                super.onCreate(state);
                DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
                final float dp = Math.min(dm.widthPixels, dm.heightPixels) / 360f;
                setContentView(new LinearLayout(getContext()) {{
                    final int padding = (int) (10 * dp);

                    setOrientation(VERTICAL);
                    setGravity(Gravity.CENTER);
                    setPadding(padding, padding, padding, padding);

                    addView(new ProgressBar(getContext()));
                    addView(mMsgView, new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) {{
                        topMargin = padding;
                    }});
                    if (TextUtils.isEmpty(mMsgView.getText())) mMsgView.setVisibility(GONE);
                    else mMsgView.setVisibility(VISIBLE);
                }});
                if (getWindow() != null) {
                    getWindow().getAttributes().width = (int) (180 * dp);
                    getWindow().getAttributes().dimAmount = 0;
                }
                getWindow().setBackgroundDrawable(null);
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void setMessage(CharSequence msg) {
                if (TextUtils.isEmpty(msg)) mMsgView.setVisibility(View.GONE);
                else {
                    mMsgView.setVisibility(View.VISIBLE);
                    mMsgView.setText(msg);
                }
            }
        };

        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setMessage(msg);
        mLoadingDialog.show();
    }

    public void loadingWithChangeMessage(CharSequence msg) {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.setMessage(msg);
        }
    }

    public void loading(CharSequence msg, boolean cancelable) {
        if (msg == null || (mLoadingDialog != null && mLoadingDialog.isShowing())) {
            if (mLoadingDialog != null) mLoadingDialog.dismiss();
            return;
        }
        Context ctxt = getContext();
        if (ctxt == null) return;
        if (ctxt instanceof Activity && ((Activity) ctxt).isFinishing()) {
            return;
        }
        if ("".equals(msg)) msg = "加载中...";
        if (mLoadingDialog == null) mLoadingDialog = new ProgressDialog(ctxt) {
            TextView mMsgView = new TextView(getContext());

            @Override
            protected void onCreate(Bundle state) {
                super.onCreate(state);
                DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
                final float dp = Math.min(dm.widthPixels, dm.heightPixels) / 360f;
                setContentView(new LinearLayout(getContext()) {{
                    final int padding = (int) (10 * dp);

                    setOrientation(VERTICAL);
                    setGravity(Gravity.CENTER);
                    setPadding(padding, padding, padding, padding);

                    addView(new ProgressBar(getContext()));
                    addView(mMsgView, new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) {{
                        topMargin = padding;
                    }});
                    if (TextUtils.isEmpty(mMsgView.getText())) mMsgView.setVisibility(GONE);
                    else mMsgView.setVisibility(VISIBLE);
                }});
                if (getWindow() != null) {
                    getWindow().getAttributes().width = (int) (180 * dp);
                    getWindow().getAttributes().dimAmount = 0;
                }
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setBackgroundDrawable(null);
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void setMessage(CharSequence msg) {
                if (TextUtils.isEmpty(msg)) mMsgView.setVisibility(View.GONE);
                else {
                    mMsgView.setVisibility(View.VISIBLE);
                    mMsgView.setText(msg);
                }
            }
        };

        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setMessage(msg);
        mLoadingDialog.show();
    }


}
