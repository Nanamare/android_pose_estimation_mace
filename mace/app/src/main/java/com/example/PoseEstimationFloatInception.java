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

import com.xiaomi.mace.MaceEngineJNI;

import java.io.IOException;


public class PoseEstimationFloatInception extends PoseEstimation {

    public static final String TAG = PoseEstimationFloatInception.class.getSimpleName();

    private static final int OUT_D2 = 64;	// height
    private static final int OUT_D3 = 36;	// width
    private static final int OUT_D4 = 14;	// joint number

    public static final int OUT_HEIGHT 		= 64;	// height
    public static final int OUT_WIDTH 		= 36;	// width
    private static final int JOINT_NUMBER 	= 14;	// joint

    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;


    /**
     * Initializes an {@code PoseEstimation}.
     *
     * @param activity
     */
    PoseEstimationFloatInception(Activity activity) throws IOException {
        super(activity);
    }


    @Override
    protected int getImageSizeX() {
        return 144;
    }

    @Override
    protected int getImageSizeY() {
        return 256;
    }

    @Override
    protected int getOutputSizeX() {
        return 36;
    }

    @Override
    protected int getOutputSizeY() {
        return 64;
    }

    /**
     * feed value
     * -1 ~ 1 스케일링.
     * 위에서부터 r, g ,b 값.
     */
    @Override
    protected void addPixelValue(int pixelValue) {
        floatBuffer.put((((pixelValue >> 16) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
        floatBuffer.put((((pixelValue >> 8) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
        floatBuffer.put(((pixelValue & 0xFF) - IMAGE_MEAN) / IMAGE_STD);

    }


    @Override
    protected void runInference() {
        float[] result = MaceEngineJNI.maceCPMClassify(floatBuffer.array());

        if (mPrintPointArray == null)
            mPrintPointArray = new float[2][14];

        float[][][] output3D = new float[64][36][14];
        int index = 0;
        for(int height = 0; height < 64; height++) {
            for(int width = 0; width < 36; width++) {
                for(int channel = 0; channel < 14; channel++) {
                    output3D[height][width][channel] = result[index++];
                }
            }
        }


        float[][] jointData = new float[JOINT_NUMBER][OUT_WIDTH * OUT_HEIGHT];

        for (int h = 0; h < OUT_D2; h++) {
            for (int w = 0; w < OUT_D3; w++) {
                for (int j = 0; j < OUT_D4; j++) {
                    jointData[j][OUT_D3 * h + w] = output3D[h][w][j];
                }
            }
        }

        if(jointInfo.size() > 0) {
            jointInfo.clear();
        }

        for (int jt = 0; jt < JOINT_NUMBER; jt++) {

            float maxValue = 0;
            int maxX = 0;        // 최고값의 x 좌표
            int maxY = 0;        // 최고값의 y 좌표

            for (int idx = 0; idx < OUT_WIDTH * OUT_HEIGHT; idx++) {
                if (jointData[jt][idx] > maxValue) {
                    maxValue = jointData[jt][idx];
                    maxX = idx % OUT_WIDTH;
                    maxY = idx / OUT_WIDTH;
                }
            }



            float[][] slide = new float[3][3];    // SLIDE_X = 3, SLIDE_Y = 3 : 변경될 경우 아래 수정해야함

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    if ((maxY - 1 + i) < 0 || (maxX - 1 + j) < 0 || (maxY - 1 + i) >= OUT_HEIGHT || (maxX - 1 + j) >= OUT_WIDTH)
                        slide[i][j] = 0;
                    else
                        slide[i][j] = jointData[jt][OUT_WIDTH * (maxY - 1 + i) + (maxX - 1 + j)];
                }
            }

            float probSum = 0;
            float sumX = 0;
            float sumY = 0;

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    sumX += slide[i][j] * (maxX - 1 + i);
                    sumY += slide[i][j] * (maxY - 1 + j);
                    probSum += slide[i][j];
                }
            }

            float slideMeanX = sumX / probSum;
            float slideMeanY = sumY / probSum;


            mPrintPointArray[0][jt] = slideMeanY;
            mPrintPointArray[1][jt] = slideMeanX;

            jointInfo.put(jt, new JointCoordinate(slideMeanX, slideMeanY, output3D[maxY][maxX][jt]));

        }

    }

}
