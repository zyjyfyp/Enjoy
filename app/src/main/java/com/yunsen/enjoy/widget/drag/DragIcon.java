package com.yunsen.enjoy.widget.drag;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yunsen.enjoy.R;

/**
 * Created by Administrator on 2018/4/5.
 */
public class DragIcon extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private ImageView imgView;
    private ImageView closeImage;
    private FrameLayout icon_layout;

    public DragIcon(Context context) {
        super(context);
        initView(context);
    }

    public DragIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DragIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.drag_icon_layout, this);
        imgView = ((ImageView) view.findViewById(R.id.drag_img));
        closeImage = (ImageView) view.findViewById(R.id.close_img);
        icon_layout = (FrameLayout) view.findViewById(R.id.drag_icon_layout);
        //        imgView.setOnClickListener(this);
        closeImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_img:
                Toast.makeText(mContext, "close", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public void setImgViewRes(Drawable drawable) {
        imgView.setImageDrawable(drawable);
    }

    public FrameLayout getIconLayout() {
        return icon_layout;
    }


    public void setCloseImgVisibility(int visibility) {
        if (closeImage != null) {
            closeImage.setVisibility(visibility);
        }
    }
}
