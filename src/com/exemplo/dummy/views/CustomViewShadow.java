package com.exemplo.dummy.views;

import com.example.dummy.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomViewShadow extends View {
	private Paint mButtonPaint;

    public CustomViewShadow(Context context) {
        this(context, null);
    }
    
	public CustomViewShadow(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    mButtonPaint.setColor(context.getResources().getColor(R.color.accent));
	    mButtonPaint.setStyle(Paint.Style.FILL);
	    mButtonPaint.setShadowLayer(5.0f, 0.0f, 2.0f, Color.argb(100, 0, 0, 0));
	    invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
	    canvas.drawRect(0, 0, getWidth(), getHeight() - 5.0f, mButtonPaint);
	}
}
