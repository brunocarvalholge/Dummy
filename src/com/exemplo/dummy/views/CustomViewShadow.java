package com.exemplo.dummy.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CustomViewShadow extends View {
	private Paint mButtonPaint;

    public CustomViewShadow(Context context) {
        this(context, null);
    }
    
	public CustomViewShadow(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    mButtonPaint.setColor(0xff64B5F6);
	    mButtonPaint.setStyle(Paint.Style.FILL);
	    mButtonPaint.setShadowLayer(5.0f, 0.0f, 2.0f, Color.argb(100, 0, 0, 0));
	    invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
	    canvas.drawRect(0, 0, getWidth(), getHeight() - 5.0f, mButtonPaint);
	}
}
