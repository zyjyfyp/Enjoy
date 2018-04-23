package com.yunsen.enjoy.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.yunsen.enjoy.R;

/**
 * Created by Administrator on 2018/4/23.
 */

public class DialogUtils {
    public static Dialog createNumbmerPickerDialog(Activity act) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(act);
        View view = act.getLayoutInflater().inflate(R.layout.num_picker_dialog, null);
        builder.setView(view);
        TextView leftTv = (TextView) view.findViewById(R.id.left_tv);
        TextView rightTv = (TextView) view.findViewById(R.id.right_tv);
        NumberPicker picker = (NumberPicker) view.findViewById(R.id.number_picker);
        leftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        String[] strings = new String[]{"智能排序", "最新上架", "价格最低", "价格最高", "车龄最短", "理财最少"};
        picker.setDisplayedValues(strings);
        picker.setMaxValue(strings.length - 1);
        picker.setMinValue(0);
        picker.setWrapSelectorWheel(true);
        return builder.create();
    }
}
