package team.lf.drawingview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PaintView extends View {
    private static final String TAG = "PaintView";
    // To hold the path that will be drawn.
    private Path mDrawPath;

    // Paint object to draw mDrawPath and mDrawCanvas.
    private Paint mDrawPaint, mCanvasPaint;
    // initial color
    private int mPaintColor = 0xff000000;
    private int mPreviousColor = mPaintColor;
    // canvas on which drawing takes place.
    private Canvas mDrawCanvas;
    // canvas bitmap
    private Bitmap mCanvasBitmap;
    // Brush stroke width
    private float mBrushSize;
    // To enable and disable erasing mode.
    private boolean erase = false;
    private Bitmap mCopyBitmap;


    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initialize all objects required for drawing here.
     * One time initialization reduces resource consumption.
     */
    private void init() {
        mDrawPath = new Path();
        mDrawPaint = new Paint();
        mDrawPaint.setColor(mPaintColor);
        // Making drawing smooth.
        mDrawPaint.setAntiAlias(true);
        mDrawPaint.setStyle(Paint.Style.STROKE);
        mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawPaint.setStrokeCap(Paint.Cap.ROUND);

        mCanvasPaint = new Paint(Paint.DITHER_FLAG);

        mBrushSize = getResources().getInteger(R.integer.medium_size);
        mDrawPaint.setStrokeWidth(mBrushSize);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mDrawCanvas = new Canvas(mCanvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mCanvasBitmap, 0, 0, mCanvasPaint);

        canvas.drawPath(mDrawPath, mDrawPaint);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCopyBitmap = mCanvasBitmap.copy(mCanvasBitmap.getConfig(), false);
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDrawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                mDrawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                if (erase) {
                    mDrawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                }

                mDrawCanvas.drawPath(mDrawPath, mDrawPaint);
                mDrawPath.reset();
                mDrawPaint.setXfermode(null);
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setColor(int newColor) {
        // invalidate the view
        invalidate();
        mPaintColor = newColor;
        mDrawPaint.setColor(mPaintColor);
        mPreviousColor = mPaintColor;
    }

    public void setBrushSize(float newSize) {
        mBrushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        mDrawPaint.setStrokeWidth(mBrushSize);
    }

    public void setErase(boolean isErase) {
        erase = isErase;
        if (erase) {
            mDrawPaint.setColor(Color.WHITE);
        } else {
            mDrawPaint.setColor(mPreviousColor);
            mDrawPaint.setXfermode(null);
        }
    }

    public void startNew() {
        mDrawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void undo() {
        mDrawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        mCanvasBitmap = mCopyBitmap.copy(mCopyBitmap.getConfig(),true);
        mDrawCanvas = new Canvas(mCanvasBitmap);
        invalidate();
    }

}
