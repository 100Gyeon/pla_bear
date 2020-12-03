package com.pla_bear.coupon;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pla_bear.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CouponView extends androidx.appcompat.widget.AppCompatTextView {
    @SuppressWarnings("EmptyMethod")
    public void init() {
    }

    public void setPrice(int price) {
        NumberFormat formatter = new DecimalFormat("#,###");
        this.setText(getContext().getString(R.string.discount, formatter.format(price)));
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 42);
        this.setTextColor(getResources().getColor(R.color.colorCouponText));
    }

    public void setDisposition() {
        float couponPadding = getResources().getDimension(R.dimen.coupon_padding);

        this.setGravity(Gravity.CENTER_HORIZONTAL);
        this.setPadding(0, (int)couponPadding, 0, (int)couponPadding);
    }

    public CouponView(@NonNull Context context) {
        super(context);
        init();
    }

    public CouponView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CouponView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
