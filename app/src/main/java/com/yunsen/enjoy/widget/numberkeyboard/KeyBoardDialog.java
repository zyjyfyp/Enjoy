package com.yunsen.enjoy.widget.numberkeyboard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.utils.ToastUtils;

/**
 * Created by Administrator on 2018/6/27.
 */

public class KeyBoardDialog extends Dialog {
    private final Activity mAct;
    private EditText edPrice;
    private static final String TAG = "KeyBoardDialog";
    private KeyboardUtil keyboardUtil;

    public KeyBoardDialog(@NonNull Activity act) {
        super(act, R.style.SelectBankDialogStyle);
        this.mAct = act;
        // 拿到Dialog的Window, 修改Window的属性
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = 300;
        attributes.gravity = Gravity.BOTTOM;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mAct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.dialog_key_board, null);
        setContentView(rootView);
        edPrice = rootView.findViewById(R.id.et_price);
    }

    private void initData() {
        keyboardUtil = new KeyboardUtil(mAct);
        keyboardUtil.setOnOkClick(new KeyboardUtil.OnOkClick() {
            @Override
            public void onOkClick() {
                if (validate()) {
                    KeyBoardDialog.this.dismiss();
//                    priceEdt.setText("" + edPrice.getText());
                    Log.e(TAG, "onOkClick: " + edPrice.getText());
                }
            }
        });

        keyboardUtil.setOnCancelClick(new KeyboardUtil.onCancelClick() {
            @Override
            public void onCancellClick() {
                dismiss();
            }
        });


        edPrice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyboardUtil.attachTo(edPrice);
                return false;
            }
        });

    }


    public boolean validate() {
        if (edPrice.getText().toString().equals("")) {
            ToastUtils.makeTextShort("价格不能为空");
            return false;
        }
        return true;
    }

    @Override
    public void show() {
        if (keyboardUtil != null) {
            keyboardUtil.attachTo(edPrice);
            edPrice.setFocusable(true);
            edPrice.setFocusableInTouchMode(true);
            edPrice.requestFocus();
        }
        super.show();
    }
}
