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

/**
 *
 * @author Hyunsung Shin
 * @since 2018. 10. 12.
 */
public class JointCoordinate {

	private float mX;
	private float mY;
	private float mConfidence;

	public JointCoordinate(float x, float y) {
		mX = x;
		mY = y;
		mConfidence = 0;
	}

	public JointCoordinate(float x, float y, float confidence) {
		mX = x;
		mY = y;
		mConfidence = confidence;
	}

	public float getX() {
		return mX;
	}

	public void setX(float x) {
		this.mX = x;
	}

	public float getY() {
		return mY;
	}

	public float getY1() {
		int y = (int)(mY * 10 + 0.5);
		return (float)y/10;
	}

	public void setY(float y) {
		this.mY = y;
	}

	public float getConfidence() {
		return mConfidence;
	}

	public float getConfidence1() {
		int c = (int)(mConfidence * 100 + 0.5);
		return (float)c/100;
	}


	static public double dist2(JointCoordinate left, JointCoordinate right) {
        return Math.sqrt( (left.getX() - right.getX()) * (left.getX() - right.getX())
				+ (left.getY() - right.getY()) * (left.getY() - right.getY()) );
    }


	public void setConfidence(float confidence) {
		this.mConfidence = confidence;
	}
}
