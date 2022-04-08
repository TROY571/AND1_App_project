package com.outsideweather.cn.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.outsideweather.cn.R;


/**
 * email：
 * description：
 */
public class BaseDialog extends Dialog {

    private Context mContext;

    private onSubClickBack onUnLockBackListener;
    private TextView tvCouponContent;
    private TextView tvCancer;
    private TextView tvNotice;
    private TextView tvOpen;
    private String content;
    private String notic;
    private String leftCount;
    private String rightCount;

    /**
     *
     * @param context
     * @param content 内容
     * @param notic 标题
     * @param leftCount 左边按钮
     * @param rightCount 右边按钮
     * @param onUnLockBackListener
     */
    public BaseDialog(Context context, String content, String notic, String leftCount, String rightCount, onSubClickBack onUnLockBackListener) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.onUnLockBackListener = onUnLockBackListener;
        this.content=content;
        this.notic=notic;
        this.leftCount=leftCount;
        this.rightCount=rightCount;
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_base_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.format = PixelFormat.TRANSPARENT;  //内容全透明
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.height = dm.heightPixels;
        window.setAttributes(lp);
        initView();

    }

    public void initView() {
        tvCouponContent = (TextView) findViewById(R.id.tvCouponContent);
        tvCouponContent.setText(content);
        tvCancer = (TextView) findViewById(R.id.tvCancer);
        tvCancer.setText(leftCount);
        tvCancer.setOnClickListener(p->{
            dismiss();
           // onUnLockBackListener.onClickBack(0);
        });
        tvNotice= (TextView) findViewById(R.id.tvNotice);
        tvNotice.setText(notic);
        tvOpen = (TextView) findViewById(R.id.tvOpen);
        tvOpen.setText(rightCount);
        tvOpen.setOnClickListener(p->{
            dismiss();
            onUnLockBackListener.onClickBack(1);
        });
    }


    public interface onSubClickBack {
        //status 右边 0 左边
        void onClickBack(int status);

    }


}

