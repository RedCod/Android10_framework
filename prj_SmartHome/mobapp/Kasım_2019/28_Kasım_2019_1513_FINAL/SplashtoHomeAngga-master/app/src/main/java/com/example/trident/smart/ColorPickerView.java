package com.example.trident.smart;

import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.trident.common.Lamp;
import com.example.trident.common.MyLog;
import com.example.trident.common.Server;

//import com.christophesmet.colorpicker.lib.R;


/**
 * //Created by christophesmet on 24/10/15. : https://android-arsenal.com/details/1/2733
 *
 *
 * by raptiye: using by "ActivityLampControl.java" class.
 */

public class ColorPickerView extends FrameLayout {

    private static String TAG ="[ColorPickerView]";
    //Debug props
    private boolean mDrawDebug = false;
    private Paint mDebugPaint = null;
    private Point mLastSelectedColorPoint = null;

    private int mLastSelectedColor;

    //Views
    private ImageView imageViewWheel;
    private ImageView imageViewThumb;

    //Drawable references
    @Nullable
    private Drawable mWheelDrawable;
    private Drawable mThumbDrawable;

    //Path of the color range
    @NonNull
    private Path mThumbWheelPath;

    //Center
    private float mCenterX = 0;
    private float mCenterY = 0;
    private float mRadius = 0;
    private float mRadiusOffset = 0;

    //Listeners
    @Nullable
    protected ColorListener mColorListener;

    public ColorPickerView(Context context) {
        super(context);
    }

    public ColorPickerView(Context context, AttributeSet attrs) {//////////////////////// 1.çalışan
        super(context, attrs);
        if(MyLog.DEGUB)MyLog.d(TAG,"ColorPickerView-constructur_1");
        init();
        initAttributes(attrs);
        initViews();


    }

    public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(MyLog.DEGUB)MyLog.d(TAG,"ColorPickerView-constructur_2");
        init();
        initAttributes(attrs);
        initViews();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if(MyLog.DEGUB)MyLog.d(TAG,"ColorPickerView-constructur_2");
        init();
        initAttributes(attrs);
        initViews();
    }

    private void init() {//////////////////////// 2.çalışan
        //register first measure.
        registerMeasure();
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.colorpicker);

        try {
            if (a.hasValue(R.styleable.colorpicker_radiusOffset)) {
                mRadiusOffset = a.getDimension(R.styleable.colorpicker_radiusOffset, 0);
            }
            if (a.hasValue(R.styleable.colorpicker_thumbDrawable)) {
                mThumbDrawable = a.getDrawable(R.styleable.colorpicker_thumbDrawable);
            }
            if (a.hasValue(R.styleable.colorpicker_wheelDrawable)) {
                mWheelDrawable = a.getDrawable(R.styleable.colorpicker_wheelDrawable);
            }
        } finally {
            a.recycle();
        }
    }

    private void initViews() {

        imageViewWheel = new ImageView(getContext());
        if (mWheelDrawable != null) {
            imageViewWheel.setImageDrawable(mWheelDrawable);
        }
        FrameLayout.LayoutParams wheelParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wheelParams.leftMargin= (int) dpToPx(getContext(),16);
        wheelParams.topMargin= (int) dpToPx(getContext(),16);
        wheelParams.rightMargin= (int) dpToPx(getContext(),16);
        wheelParams.bottomMargin= (int) dpToPx(getContext(),16);

        wheelParams.gravity = Gravity.CENTER;
        addView(imageViewWheel, wheelParams);

        imageViewThumb = new ImageView(getContext());

        if (mThumbDrawable != null) {
            imageViewThumb.setImageDrawable(mThumbDrawable);
        }
        FrameLayout.LayoutParams thumbParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        thumbParams.gravity = Gravity.CENTER;
        addView(imageViewThumb, thumbParams);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageViewThumb.setStateListAnimator(AnimatorInflater.loadStateListAnimator(getContext(), /*R.anim.raise*/R.animator.raise));//FIXME:raptiye
            imageViewThumb.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setOval(0, 0, imageViewThumb.getMeasuredWidth(), imageViewThumb.getMeasuredHeight());
                }
            });
        }
    }

    private void loadListeners() {////////////////////////////////////////////////// 7.çalışan
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //MyLog.d("kertens:S","getRawX:" + event.getRawX() +",getRawY:" + event.getRawY() +",getX:" + event.getX() + ",getY:" + event.getY());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        imageViewThumb.setPressed(true);
                        return onTouchReceived(event);
                    case MotionEvent.ACTION_MOVE:
                        imageViewThumb.setPressed(true);
                        return onTouchReceived(event);
                    case MotionEvent.ACTION_UP:
                        /*//TODO(raptiye):save the last selected point(for color) to the database.
                           +todo: because when the user first opens the "Lamp control page",we will apply the last chosen color.
                           +todo: for fast processing,we will send the value as mqtt to the server instead of sending it directly to the database.Then we will save it to the database by the server
                         */
                        Server.sendLastSelectedColorPoint(event.getX(),event.getY());//send to mqtt server and insert database.
                        return false;
                    default:
                        imageViewThumb.setPressed(false);
                        return false;
                }
            }
        });
    }

    //-12059121
    //public void setColor(int color){ mLastSelectedColor = color;}
    public int getColor() {
        return mLastSelectedColor;
    }


    /**
     * add by raptiye(me). for first:
     * @param x
     * @param y
     * @return
     */
    private boolean onTouchReceivedFirst(float x,float y) {//////////////////////////////// 5.çalışan.

        if(MyLog.DEGUB)MyLog.d(TAG,"onTouchReceivedFirst:" + x + "," + y);//ÇÖP
        Point snapPoint = getClosestPoint(/*210f,429f*/x,y); //add by me//FIXME: TODO: app ilk açıldığında,kullanıcının son belirlediği renk serverden çekilip buraya uygulanacak.
        //Point snapPoint = getClosestPoint(event.getX(), event.getY());//original
        //We have the closes point on the circle.
        //Now adjust the postion to center the thumb in the middle of the point
        imageViewThumb.setX(snapPoint.x - (imageViewThumb.getMeasuredWidth() / 2));
        imageViewThumb.setY(snapPoint.y - (imageViewThumb.getMeasuredHeight() / 2));
        return true;
    }
    //return true if consumed
    //Check for the closes point on the circle
    //The move the thumb image to that spot.
    private boolean onTouchReceived(@NonNull MotionEvent event) {//////////////////////////////// 5.çalışan.
        Point snapPoint = getClosestPoint(event.getX(), event.getY());//original
        //We have the closes point on the circle.
        //Now adjust the postion to center the thumb in the middle of the point
        imageViewThumb.setX(snapPoint.x - (imageViewThumb.getMeasuredWidth() / 2));
        imageViewThumb.setY(snapPoint.y - (imageViewThumb.getMeasuredHeight() / 2));
        return true;
    }

    /**
     * This will return the closes point on the circle relative to the touch event
     *
     * @param touchX
     * @param touchY
     * @return
     */
    private Point getClosestPoint(float touchX, float touchY) {//////////////////////////////////6.çalışan
        //Todo: find if there is a faster way.
        if(MyLog.DEGUB)MyLog.d(TAG,"getClosestPoint: touchX:" + touchX +",touchY:"+touchY);
        //This is for center 0,0
        double angle = Math.atan2(touchY - getCenterYInParent(), touchX - getCenterXInParent());

        double onCircleX = Math.cos(angle) * mRadius;
        double onCircleY = Math.sin(angle) * mRadius;

        //fetch the selected color from the drawable
        mLastSelectedColor = getColorFromColorRing((float) onCircleX + mCenterX, (float) onCircleY + mCenterY);
        //Log.d("kertensS:colorpicker", "Selected color: " + mLastSelectedColor);
        fireColorListener(getColor());

        //The circle is on an offset, not on 0,0 but centered and in a viewgroup (our parent framelayout)
        onCircleX += getCenterXInParent();
        onCircleY += getCenterYInParent();

        return new Point((int) onCircleX, (int) onCircleY);
    }


    /**
     * by raptiye:
     * for hide "ImageViewWheel".
     //* @param visibility
     */
 /*   public void setVisibilityImageViewWheel(int visibility){
        imageViewWheel.setVisibility(visibility);
    }*/

    /**
     * by raptiye:
     * for hide "imageViewThumb".
     //* @param visibility
     */
   /* public void setVisibilityImageViewThumb(int visibility){
        imageViewThumb.setVisibility(visibility);
    }
    */

    private int getColorFromColorRing(float x, float y) {///////////////////// 7.çalışan.
        if (mWheelDrawable == null) {
            return 0;
        }
        //Log.d("kertensS", "getColorFromColorRing x: " + x + " y: " + y); //X,Y deperleri geliyor ve renk bulunuyor.
        Matrix invertMatrix = new Matrix();
        imageViewWheel.getImageMatrix().invert(invertMatrix);

        float[] mappedPoints = new float[]{x, y};
        // Log.d("kertensS:colorpicker", "mapped touch x: " + mappedPoints[0] + " y: " + mappedPoints[1]);

        invertMatrix.mapPoints(mappedPoints);

        if (imageViewWheel.getDrawable() != null && imageViewWheel.getDrawable() instanceof BitmapDrawable &&
                mappedPoints[0] > 0 && mappedPoints[1] > 0 &&
                mappedPoints[0] < imageViewWheel.getDrawable().getIntrinsicWidth() && mappedPoints[1] < imageViewWheel.getDrawable().getIntrinsicHeight()) {

            mLastSelectedColorPoint = new Point((int) mappedPoints[0], (int) mappedPoints[1]);
            invalidate();
            return ((BitmapDrawable) imageViewWheel.getDrawable()).getBitmap().getPixel((int) mappedPoints[0], (int) mappedPoints[1]);
        }
        return 0;
    }

    private void onFirstLayout() {//////////////////////// 4.çalışan //SAYFA İLK AÇILIŞ
        //MyLog.d("kertensS:","onFirstLayout()");
        //First layout, lets grab the size and generate the path for
        mThumbWheelPath = generateThumbWheelPath(imageViewWheel.getMeasuredWidth(), imageViewWheel.getMeasuredHeight());
        //Fake a touch top center
       /* onTouchReceived( //ORIGINAL CODE
                MotionEvent.obtain(System.currentTimeMillis(),
                        System.currentTimeMillis() + 100,
                        MotionEvent.ACTION_UP,
                        getMeasuredWidth() / 2,
                        0,
                        0)
        );*/

        //add by me:
        /*//TODO:##########################################################
          // İLK AÇILIŞ:Veritabanından kullanıcının son seçtiği renk pointer değerini get yap  X,Y olarak "onTouchReceivedFirst"'e gönder.
         */
        //
        onTouchReceivedFirst(/*210f,429f*/Lamp.getColorXPoint(),Lamp.getColorYPoint());//FIXME:  TODO serverden alınan son kullanıcı renk pointi seçeneğini buraya uygula.
        //Setup done, register listeners
        loadListeners();
    }

    public float getRadius(float side) {
        //Offset the entire circle
        //Radius is the smallest side - the offset to center the circle in the center of the color circle.
        float radius = ((side) - mRadiusOffset) / 2;
        return radius;
    }

    @NonNull
    private Path generateThumbWheelPath(int measuredWidth, int measuredHeight) {
        //By default we just make a circle. default scaletype of an imageview is fitCenter
        //Lets calculate the square size of the imageview's content
        int side = Math.min(measuredHeight, measuredWidth);
        //Lets create the path
        Path output = new Path();
        mRadius = getRadius(side);
        mCenterX = (float) measuredWidth / 2;
        mCenterY = (float) measuredHeight / 2;

        //add the offset of the imageview wheel in this viewgroup
        output.addCircle(getCenterXInParent(), getCenterYInParent(), mRadius, Path.Direction.CW);
        return output;
    }


    private void registerMeasure() {//////////////////////// 3.çalışan
        if(MyLog.DEGUB)MyLog.d(TAG,"registerMeasure()");
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                onFirstLayout();
            }
        });
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

         /*//MyLog.d("kertensS:","mLastSelectedColorPoint.x:" +mLastSelectedColorPoint.x +",mLastSelectedColorPoint.y:" +mLastSelectedColorPoint.y);
        //for debug:
           // mDrawDebug =true;
       if (mDrawDebug && mThumbWheelPath != null) {
            checkDebugPaint();
            //Draw the path to see while debugging
            canvas.drawPath(mThumbWheelPath, mDebugPaint);
            //cross in the center of the parent
           // canvas.drawLine(getCenterXInParent(), getCenterYInParent() - 20, getCenterXInParent(), getCenterYInParent() + 20, mDebugPaint);
            //canvas.drawLine(getCenterXInParent() - 20, getCenterYInParent(), getCenterXInParent() + 20, getCenterYInParent(), mDebugPaint);
            if (mLastSelectedColorPoint != null) {
                canvas.drawCircle(imageViewWheel.getX() + mLastSelectedColorPoint.x, imageViewWheel.getY() + mLastSelectedColorPoint.y, 14, mDebugPaint);
            }
        }*/
    }

    private float getCenterXInParent() {
        return mCenterX + imageViewWheel.getX();
    }

    private float getCenterYInParent() {
        return mCenterY + imageViewWheel.getY();
    }

    //for debug:
   /* private void checkDebugPaint() {
        //Allocations during a draw phase are a big no-no. But will happen only once, and it's for debugging anyway.
        if (mDebugPaint == null) {
            mDebugPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mDebugPaint.setStrokeWidth(5f);
            mDebugPaint.setStyle(Paint.Style.STROKE);
            mDebugPaint.setColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }*/

    public static float pxToDp(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dpToPx(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

   /*///for debug:
    public void setDrawDebug(boolean drawDebug) {
        mDrawDebug = drawDebug;
        //Debug draw enabled.
        //request a draw
        invalidate();
    }*/

    /**
     * set Visible or InVisible "imageViewWheel" and "imageViewThumb"
     * @add by raptiye
     * @param visibility
     */
    public void setVisibilityColorPalete(int visibility){
        imageViewWheel.setVisibility(visibility);
        imageViewThumb.setVisibility(visibility);
    }



    private void fireColorListener(int color) {
        if (mColorListener != null) {
            mColorListener.onColorSelected(color);
        }
    }

    public void setColorListener(@Nullable ColorListener colorListener) {
        mColorListener = colorListener;
    }

    public interface ColorListener {
        void onColorSelected(int color);
    }
}