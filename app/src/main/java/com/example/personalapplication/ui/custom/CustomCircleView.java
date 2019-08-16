package com.example.personalapplication.ui.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

//自定义圆形图片View
@SuppressLint("AppCompatCustomView")
public class CustomCircleView extends ImageView{

    private float width;
    private float height;
    private float radius;
    private Paint paint;
    private Matrix matrix;

    public CustomCircleView(Context context) {
        this(context,null);
    }

    public CustomCircleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint=new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=getMeasuredWidth();
        height=getMeasuredHeight();
        radius=Math.min(width,height)/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable=getDrawable();
        if(drawable==null){
            super.onDraw(canvas);
            return;
        }
        if(drawable instanceof BitmapDrawable){
            paint.setShader(initBitmapShader((BitmapDrawable) drawable));
            canvas.drawCircle(width/2,height/2,radius,paint);
            return;
        }
        super.onDraw(canvas);
    }

    private BitmapShader initBitmapShader(BitmapDrawable drawable) {
        matrix=new Matrix();
        Bitmap bitmap=drawable.getBitmap();
        BitmapShader bitmapShader=new BitmapShader(bitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        float scale=Math.max(width/bitmap.getWidth(),height/bitmap.getHeight());
        matrix.setScale(scale,scale);
        bitmapShader.setLocalMatrix(matrix);
        return bitmapShader;
    }
}
