package com.example.trident.smart;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.trident.common.MyLog;

import java.util.ArrayList;

/**
 * The dynamic listview is an extension of listview that supports cell dragging
 * and swapping.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@SuppressLint("InlinedApi")
public class DynamicListView extends ListView {


	public static boolean ROOM_ITEM_DRAGDROP = false;//

	private final int SMOOTH_SCROLL_AMOUNT_AT_EDGE = 15;
	private final int MOVE_DURATION = 220;//150;
	private final int LINE_THICKNESS = 15;

	public ArrayList<String> mCheeseList;

	private int mLastEventY = -1;

	private int mDownY = -1;
	private int mDownX = -1;

	private int mTotalOffset = 0;

	private boolean mCellIsMobile = false;
	private boolean mIsMobileScrolling = false;
	private int mSmoothScrollAmountAtEdge = 0;

	private final int INVALID_ID = -1;
	private long mAboveItemId = INVALID_ID;
	private long mMobileItemId = INVALID_ID;
	private long mBelowItemId = INVALID_ID;

	private BitmapDrawable mHoverCell;
	private Rect mHoverCellCurrentBounds;
	private Rect mHoverCellOriginalBounds;

	private final int INVALID_POINTER_ID = -1;
	private int mActivePointerId = INVALID_POINTER_ID;

	private boolean mIsWaitingForScrollFinish = false;
	private int mScrollState = OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;//SCROLL_STATE_IDLE;

	public DynamicListView(Context context) {
		super(context);
		init(context);
	}

	public DynamicListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public DynamicListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void init(Context context) {
		setOnItemLongClickListener(mOnItemLongClickListener);
		setOnScrollListener(mScrollListener);
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		mSmoothScrollAmountAtEdge = (int) (SMOOTH_SCROLL_AMOUNT_AT_EDGE / metrics.density);
	}

	private OnItemLongClickListener mOnItemLongClickListener = new OnItemLongClickListener() {
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos,
				long id) {
			mTotalOffset = 0;

			int position = pointToPosition(mDownX, mDownY);
			int itemNum = position - getFirstVisiblePosition();

			View selectedView = getChildAt(itemNum);
			mMobileItemId = getAdapter().getItemId(position);
			mHoverCell = getAndAddHoverView(selectedView);
			selectedView.setVisibility(INVISIBLE);

			mCellIsMobile = true;

			updateNeighborViewsForID(mMobileItemId);

			return true;
		}
	};

	private BitmapDrawable getAndAddHoverView(View v) {

		int w = v.getWidth();
		int h = v.getHeight();
		int top = v.getTop();
		int left = v.getLeft();

		Bitmap b = getBitmapWithBorder(v);

		BitmapDrawable drawable = new BitmapDrawable(getResources(), b);

		mHoverCellOriginalBounds = new Rect(left, top, left + w, top + h);
		mHoverCellCurrentBounds = new Rect(mHoverCellOriginalBounds);

		drawable.setBounds(mHoverCellCurrentBounds);

		return drawable;
	}

	private Bitmap getBitmapWithBorder(View v) {
		Bitmap bitmap = getBitmapFromView(v);
		Canvas can = new Canvas(bitmap);

		Rect rect = new Rect(0, 0/*-1 //hide top rectangle line.*/, bitmap.getWidth(), bitmap.getHeight());

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Paint.Cap.SQUARE);
		paint.setStrokeWidth(4);//LINE_THICKNESS //hide top line of rectangle
		paint.setColor(/*Color.BLACK*/Color.parseColor("#000000"));//7d180e
		// set the shadowLayer

	/*	paint.setShadowLayer(
				5f, // radius
				.0f, // dx
				85.0f, // dy
				Color.parseColor("#540d06")); // shadow color
*/
		can.drawBitmap(bitmap, 0, 0, null);
		can.drawRect(rect, paint);
		return bitmap;
	}

	private Bitmap getBitmapFromView(View v) {
		Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		v.draw(canvas);
		return bitmap;
	}


	//add by raptiye: kullanılacak customViewAdapter nesnesi. for decomposition.
	static Object customObject = null;
	public void setCustomViewAdapterObject(Object object){
		customObject = object;
	}

	private void updateNeighborViewsForID(long itemID) {
		//original code:disabled by raptiye.
		/*int position = getPositionForID(itemID);
		DynamicListViewAdapter adapter = ((DynamicListViewAdapter) getAdapter());//raptiye
		mAboveItemId = adapter.getItemId(position - 1);
		mBelowItemId = adapter.getItemId(position + 1);*/

		//FIX by raptiye:
		int position = getPositionForID(itemID);
		if(customObject instanceof DynamicListViewAdapter){
			DynamicListViewAdapter adapter = ((DynamicListViewAdapter) getAdapter());//raptiye
			mAboveItemId = adapter.getItemId(position - 1);
			mBelowItemId = adapter.getItemId(position + 1);

		}else if(customObject instanceof DynamicListAdapter_RoomSettings_DeviceItem){
			DynamicListAdapter_RoomSettings_DeviceItem adapter = ((DynamicListAdapter_RoomSettings_DeviceItem) getAdapter());//raptiye
			mAboveItemId = adapter.getItemId(position - 1);
			mBelowItemId = adapter.getItemId(position + 1);
		}else{
			MyLog.d("kertenSs3:","else. i think object is null"); //TODO:FIXME:
		}

	}

	public View getViewForID(long itemID) {

		//original code:disabled by raptiye.
		/*int firstVisiblePosition = getFirstVisiblePosition();
		DynamicListViewAdapter adapter = ((DynamicListViewAdapter) getAdapter());//raptiye
		for (int i = 0; i < getChildCount(); i++) {
			View v = getChildAt(i);
			int position = firstVisiblePosition + i;
			long id = adapter.getItemId(position);
			if (id == itemID) {
				return v;
			}
		}*/
		//FIX by raptiye:
		//MyLog.d("kerten","customObject:" + customObject );

		int firstVisiblePosition = getFirstVisiblePosition();
		if(customObject instanceof DynamicListViewAdapter) {
			DynamicListViewAdapter adapter = ((DynamicListViewAdapter) getAdapter());//raptiye
			for (int i = 0; i < getChildCount(); i++) {
				View v = getChildAt(i);
				int position = firstVisiblePosition + i;
				long id = adapter.getItemId(position);
				if (id == itemID) {
					return v;
				}
			}
		}else if(customObject instanceof DynamicListAdapter_RoomSettings_DeviceItem){
			DynamicListAdapter_RoomSettings_DeviceItem adapter = ((DynamicListAdapter_RoomSettings_DeviceItem) getAdapter());//raptiye
			for (int i = 0; i < getChildCount(); i++) {
				View v = getChildAt(i);
				int position = firstVisiblePosition + i;
				long id = adapter.getItemId(position);
				if (id == itemID) {
					return v;
				}
			}
		}

		return null;
	}

	public int getPositionForID(long itemID) {
		View v = getViewForID(itemID);
		if (v == null) {
			return -1;
		} else {
			return getPositionForView(v);
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mHoverCell != null) {
			mHoverCell.draw(canvas);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mDownX = (int) event.getX();
			mDownY = (int) event.getY();
			mActivePointerId = event.getPointerId(0);
			break;
		case MotionEvent.ACTION_MOVE:
			if (mActivePointerId == INVALID_POINTER_ID) {
				break;
			}
			int pointerIndex = event.findPointerIndex(mActivePointerId);

			mLastEventY = (int) event.getY(pointerIndex);
			int deltaY = mLastEventY - mDownY;
			if (mCellIsMobile) {
				mHoverCellCurrentBounds.offsetTo(mHoverCellOriginalBounds.left,
						mHoverCellOriginalBounds.top + deltaY + mTotalOffset);
				mHoverCell.setBounds(mHoverCellCurrentBounds);
				invalidate();

				handleCellSwitch();

				mIsMobileScrolling = false;
				handleMobileCellScroll();

				return false;
			}
			break;
		case MotionEvent.ACTION_UP:
			touchEventsEnded();
			break;
		case MotionEvent.ACTION_CANCEL:
			touchEventsCancelled();
			break;
		case MotionEvent.ACTION_POINTER_UP:
			pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
			final int pointerId = event.getPointerId(pointerIndex);
			if (pointerId == mActivePointerId) {
				touchEventsEnded();
			}
			break;
		default:
			break;
		}

		return super.onTouchEvent(event);
	}

	private void handleCellSwitch() {
		final int deltaY = mLastEventY - mDownY;
		int deltaYTotal = mHoverCellOriginalBounds.top + mTotalOffset + deltaY;

		View belowView = getViewForID(mBelowItemId);
		View mobileView = getViewForID(mMobileItemId);
		View aboveView = getViewForID(mAboveItemId);

		boolean isBelow = (belowView != null)
				&& (deltaYTotal > belowView.getTop());
		boolean isAbove = (aboveView != null)
				&& (deltaYTotal < aboveView.getTop());

		if (isBelow || isAbove) {

			final long switchItemID = isBelow ? mBelowItemId : mAboveItemId;
			View switchView = isBelow ? belowView : aboveView;
			final int originalItem = getPositionForView(mobileView);

			if (switchView == null) {
				updateNeighborViewsForID(mMobileItemId);
				return;
			}

			swapElements(mCheeseList, originalItem,getPositionForView(switchView));
			((BaseAdapter) getAdapter()).notifyDataSetChanged();
			mDownY = mLastEventY;
			final int switchViewStartTop = switchView.getTop();

			///fix by raptiye:@{
			/*mobileView.setVisibility(View.VISIBLE);
			switchView.setVisibility(View.INVISIBLE);*/
			mobileView.setVisibility(View.VISIBLE);
			switchView.setVisibility(View.VISIBLE);
			///fix by raptiye:@}
			updateNeighborViewsForID(mMobileItemId);
			final ViewTreeObserver observer = getViewTreeObserver();
			observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
				@SuppressLint("NewApi")
				public boolean onPreDraw() {
					observer.removeOnPreDrawListener(this);

					View switchView = getViewForID(switchItemID);

					///FIX:for test raptiye: 19temm @{
					MyLog.d("kertenSss","switchView:" + switchView);
					if (switchView == null) {
						updateNeighborViewsForID(mMobileItemId);
						return false;
					}
					///@}

					mTotalOffset += deltaY;

					int switchViewNewTop = switchView.getTop();
					int delta = switchViewStartTop - switchViewNewTop;

					switchView.setTranslationY(delta);

					ObjectAnimator animator = ObjectAnimator.ofFloat(
							switchView, View.TRANSLATION_Y, 0);
					animator.setDuration(MOVE_DURATION);
					animator.start();

					return true;
				}
			});

		}
	}

	private void swapElements(ArrayList arrayList, int indexOne, int indexTwo) {
		Object temp = arrayList.get(indexOne);
		arrayList.set(indexOne, arrayList.get(indexTwo));
		arrayList.set(indexTwo, temp);
		//ActivityRoomManagement.ROOM_ITEM_DRAGDROP = true;
		//this.ROOM_ITEM_DRAGDROP = true;
		setRoomItemDragDropChanges(true);
	}

	//add by raptiye:
	public static void setRoomItemDragDropChanges(boolean _state){
		ROOM_ITEM_DRAGDROP = _state;
	}
	public static boolean isRoomItemDragDropChanges(){
		return ROOM_ITEM_DRAGDROP;
	}


	private void touchEventsEnded() {
		final View mobileView = getViewForID(mMobileItemId);

		///FIX:for test raptiye:19temm (NullPointerException)
		 if(mobileView == null)
		 	return;
         ///@}

		if (mCellIsMobile || mIsWaitingForScrollFinish) {
			mCellIsMobile = false;
			mIsWaitingForScrollFinish = false;
			mIsMobileScrolling = false;
			mActivePointerId = INVALID_POINTER_ID;

			if (mScrollState != OnScrollListener.SCROLL_STATE_IDLE) {
				mIsWaitingForScrollFinish = true;
				return;
			}

			mHoverCellCurrentBounds.offsetTo(mHoverCellOriginalBounds.left,mobileView.getTop());

			ObjectAnimator hoverViewAnimator = ObjectAnimator.ofObject(
					mHoverCell, "bounds", sBoundEvaluator,
					mHoverCellCurrentBounds);
			hoverViewAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(
								ValueAnimator valueAnimator) {
							invalidate();
						}
					});
			hoverViewAnimator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationStart(Animator animation) {
					setEnabled(false);
				}

				@Override
				public void onAnimationEnd(Animator animation) {
					mAboveItemId = INVALID_ID;
					mMobileItemId = INVALID_ID;
					mBelowItemId = INVALID_ID;
					mobileView.setVisibility(VISIBLE);
					mHoverCell = null;
					setEnabled(true);
					invalidate();
				}
			});
			hoverViewAnimator.start();
		} else {
			touchEventsCancelled();
		}
	}

	private void touchEventsCancelled() {
		View mobileView = getViewForID(mMobileItemId);
		if (mCellIsMobile) {
			mAboveItemId = INVALID_ID;
			mMobileItemId = INVALID_ID;
			mBelowItemId = INVALID_ID;
			mobileView.setVisibility(VISIBLE);
			mHoverCell = null;
			invalidate();
		}
		mCellIsMobile = false;
		mIsMobileScrolling = false;
		mActivePointerId = INVALID_POINTER_ID;
	}

	private final static TypeEvaluator sBoundEvaluator = new TypeEvaluator() {
		public Rect evaluate(float fraction, Rect startValue, Rect endValue) {
			return new Rect(interpolate(startValue.left, endValue.left,
					fraction), interpolate(startValue.top, endValue.top,
					fraction), interpolate(startValue.right, endValue.right,
					fraction), interpolate(startValue.bottom, endValue.bottom,
					fraction));
		}

		public int interpolate(int start, int end, float fraction) {
			return (int) (start + fraction * (end - start));
		}

		@Override
		public Object evaluate(float arg0, Object arg1, Object arg2) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	private void handleMobileCellScroll() {
		mIsMobileScrolling = handleMobileCellScroll(mHoverCellCurrentBounds);
	}

	public boolean handleMobileCellScroll(Rect r) {
		int offset = computeVerticalScrollOffset();
		int height = getHeight();
		int extent = computeVerticalScrollExtent();
		int range = computeVerticalScrollRange();
		int hoverViewTop = r.top;
		int hoverHeight = r.height();

		if (hoverViewTop <= 0 && offset > 0) {
			smoothScrollBy(-mSmoothScrollAmountAtEdge, 0);
			return true;
		}

		if (hoverViewTop + hoverHeight >= height && (offset + extent) < range) {
			smoothScrollBy(mSmoothScrollAmountAtEdge, 0);
			return true;
		}

		return false;
	}

	public void setCheeseList(ArrayList<String> cheeseList) {
		mCheeseList = cheeseList;
	}

	private OnScrollListener mScrollListener = new OnScrollListener() {

		private int mPreviousFirstVisibleItem = -1;
		private int mPreviousVisibleItemCount = -1;
		private int mCurrentFirstVisibleItem;
		private int mCurrentVisibleItemCount;
		private int mCurrentScrollState;

		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			mCurrentFirstVisibleItem = firstVisibleItem;
			mCurrentVisibleItemCount = visibleItemCount;

			mPreviousFirstVisibleItem = (mPreviousFirstVisibleItem == -1) ? mCurrentFirstVisibleItem
					: mPreviousFirstVisibleItem;
			mPreviousVisibleItemCount = (mPreviousVisibleItemCount == -1) ? mCurrentVisibleItemCount
					: mPreviousVisibleItemCount;

			checkAndHandleFirstVisibleCellChange();
			checkAndHandleLastVisibleCellChange();

			mPreviousFirstVisibleItem = mCurrentFirstVisibleItem;
			mPreviousVisibleItemCount = mCurrentVisibleItemCount;
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			mCurrentScrollState = scrollState;
			mScrollState = scrollState;
			isScrollCompleted();
		}

		private void isScrollCompleted() {
			if (mCurrentVisibleItemCount > 0
					&& mCurrentScrollState == SCROLL_STATE_IDLE) {
				if (mCellIsMobile && mIsMobileScrolling) {
					handleMobileCellScroll();
				} else if (mIsWaitingForScrollFinish) {
					touchEventsEnded();
				}
			}
		}

		public void checkAndHandleFirstVisibleCellChange() {
			if (mCurrentFirstVisibleItem != mPreviousFirstVisibleItem) {
				if (mCellIsMobile && mMobileItemId != INVALID_ID) {
					updateNeighborViewsForID(mMobileItemId);
					handleCellSwitch();
				}
			}
		}

		public void checkAndHandleLastVisibleCellChange() {
			int currentLastVisibleItem = mCurrentFirstVisibleItem
					+ mCurrentVisibleItemCount;
			int previousLastVisibleItem = mPreviousFirstVisibleItem
					+ mPreviousVisibleItemCount;
			if (currentLastVisibleItem != previousLastVisibleItem) {
				if (mCellIsMobile && mMobileItemId != INVALID_ID) {
					updateNeighborViewsForID(mMobileItemId);
					handleCellSwitch();
				}
			}
		}
	};
}