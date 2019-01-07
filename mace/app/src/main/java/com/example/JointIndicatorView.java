/*
 * Copyright 2019 nanamare(nanamare.tistory.com)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Size;
import android.util.SparseArray;
import android.view.View;

import static com.example.PoseEstimationFloatInception.OUT_HEIGHT;
import static com.example.PoseEstimationFloatInception.OUT_WIDTH;


/**
 *
 * @author Hyunsung Shin
 * @since 2018. 10. 12.
 */
public class JointIndicatorView extends View {

	private static final String TAG = JointIndicatorView.class.getSimpleName();
	private Context mContext;

	private Handler mUiHandler;

	private Paint mPaint;
	private Paint mCirclePaint;
	private Size mPreviewSize;
	private Point mDisplaySize = new Point();
	private float mDensity;
	private int mColor;
	private SparseArray<JointCoordinate> mJointInfo = null;

	public JointIndicatorView(Context context, Size size, Handler uiHandler) {
		super(context);
		this.mContext = context;

		mPaint = new Paint();

		this.mPreviewSize = size;
		this.mUiHandler = uiHandler;

		((Activity)mContext).getWindowManager().getDefaultDisplay().getSize(mDisplaySize);
		DisplayMetrics dm = new DisplayMetrics();
		((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
		mDensity = dm.density;
		mColor = mContext.getResources().getColor(R.color.material_orangeA100);
	}

	public JointIndicatorView(Context context, Size size) {
		super(context);
		this.mContext = context;

		mPaint = new Paint();
		mCirclePaint = new Paint();

		this.mPreviewSize = size;

		((Activity)mContext).getWindowManager().getDefaultDisplay().getSize(mDisplaySize);
		DisplayMetrics dm = new DisplayMetrics();
		((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
		mDensity = dm.density;
		mColor = mContext.getResources().getColor(R.color.material_orangeA100);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if(mJointInfo == null || mPreviewSize == null)
			return;

		mPaint.setColor(mColor);
		mPaint.setStrokeWidth(3 * mDensity);
		mPaint.setTextSize(12 * mDensity);
		mPaint.setAntiAlias(true);

		mCirclePaint.setColor(mColor);
		mCirclePaint.setStrokeWidth(11 * mDensity);
		mCirclePaint.setAntiAlias(true);

		int pointKey;
		for(int i = 0; i < mJointInfo.size(); i++) {
			pointKey = mJointInfo.keyAt(i);
			JointCoordinate entry = mJointInfo.get(pointKey);
			if(entry == null)
				return;
			float x = mDisplaySize.x * entry.getX() / 36;
			float y = mDisplaySize.y * entry.getY() / 64;

			canvas.drawCircle(x, y,10, mCirclePaint);


		}

		int key = 0;
		int key2 = 0;
		for(int i = 0; i < 5; i++) {
			key = mJointInfo.keyAt(i);
			key2 = mJointInfo.keyAt(i + 1);
			JointCoordinate entry = mJointInfo.get(key);
			JointCoordinate entry2 = mJointInfo.get(key2);
			if(entry == null || entry2 == null)
				return;
			float x = mDisplaySize.x * entry.getX() / OUT_WIDTH;
			float y = mDisplaySize.y * entry.getY() / OUT_HEIGHT;

			float x2 = mDisplaySize.x * entry2.getX() / OUT_WIDTH;
			float y2 = mDisplaySize.y * entry2.getY() / OUT_HEIGHT;
			canvas.drawLine(x, y ,x2, y2, mPaint);
		}

		for(int i = 6; i < 11; i++) {
			key = mJointInfo.keyAt(i);
			key2 = mJointInfo.keyAt(i + 1);
			JointCoordinate entry = mJointInfo.get(key);
			JointCoordinate entry2 = mJointInfo.get(key2);
			if(entry == null || entry2 == null)
				return;
			float x = mDisplaySize.x * entry.getX() / OUT_WIDTH;
			float y = mDisplaySize.y * entry.getY() / OUT_HEIGHT;

			float x2 = mDisplaySize.x * entry2.getX() / OUT_WIDTH;
			float y2 = mDisplaySize.y * entry2.getY() / OUT_HEIGHT;
			canvas.drawLine(x, y ,x2, y2, mPaint);
		}

		for(int i = 12; i < 13; i++) {
			key = mJointInfo.keyAt(i);
			key2 = mJointInfo.keyAt(i + 1);
			JointCoordinate entry = mJointInfo.get(key);
			JointCoordinate entry2 = mJointInfo.get(key2);
			if(entry == null || entry2 == null)
				return;
			float x = mDisplaySize.x * entry.getX() / OUT_WIDTH;
			float y = mDisplaySize.y * entry.getY() / OUT_HEIGHT;

			float x2 = mDisplaySize.x * entry2.getX() / OUT_WIDTH;
			float y2 = mDisplaySize.y * entry2.getY() / OUT_HEIGHT;
			canvas.drawLine(x, y ,x2, y2, mPaint);
		}

		//3 ~ 9
		{
			key = mJointInfo.keyAt(3);
			key2 = mJointInfo.keyAt(9);
			JointCoordinate entry = mJointInfo.get(key);
			JointCoordinate entry2 = mJointInfo.get(key2);
			if (entry == null || entry2 == null)
				return;
			float x = mDisplaySize.x * entry.getX() / OUT_WIDTH;
			float y = mDisplaySize.y * entry.getY() / OUT_HEIGHT;

			float x2 = mDisplaySize.x * entry2.getX() / OUT_WIDTH;
			float y2 = mDisplaySize.y * entry2.getY() / OUT_HEIGHT;
			canvas.drawLine(x, y, x2, y2, mPaint);
		}

		//2 ~ 8
		{
			key = mJointInfo.keyAt(2);
			key2 = mJointInfo.keyAt(8);
			JointCoordinate entry = mJointInfo.get(key);
			JointCoordinate entry2 = mJointInfo.get(key2);
			if (entry == null || entry2 == null)
				return;
			float x = mDisplaySize.x * entry.getX() / OUT_WIDTH;
			float y = mDisplaySize.y * entry.getY() / OUT_HEIGHT;

			float x2 = mDisplaySize.x * entry2.getX() / OUT_WIDTH;
			float y2 = mDisplaySize.y * entry2.getY() / OUT_HEIGHT;
			canvas.drawLine(x, y, x2, y2, mPaint);
		}
	}

	public void drawJoint(SparseArray<JointCoordinate> jointInfo) {
		mJointInfo = jointInfo;
		invalidate();
	}

	public void clearJoint() {
		mJointInfo = null;
		invalidate();
	}

}
