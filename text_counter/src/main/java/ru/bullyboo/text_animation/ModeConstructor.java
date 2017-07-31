/*
 * Copyright (C) 2017 BullyBoo
 *
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

package ru.bullyboo.text_animation;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

class ModeConstructor {

    private float[] alphaDynamic;

    public float[] getAlphaDynamic(){
        return alphaDynamic;
    }

    protected int[] getModeFpsDynamic(int mode, long duration, Integer fps){
        return getModeFpsDynamic(mode, duration, fps, null);
    }

    protected int[] getModeFpsDynamic(int mode, long duration, Integer fromFps, Integer toFps){

        AlphaBuilder fromAlpha = AlphaBuilder.newInstance().fromAlpha(0f).toAlpha(1f);
        AlphaBuilder toAlpha = AlphaBuilder.newInstance().fromAlpha(1f).toAlpha(0f);

        switch (mode){
            case TextCounter.LINEAR_MODE :
                return getFpsDynamicLinear(duration, fromFps);
            case TextCounter.LINEAR_ACCELERATION_MODE :
                return getFpsDynamicLinearAcceleration(duration, fromFps);
            case TextCounter.LINEAR_DECELERATION_MODE :
                return getFpsDynamicLinearDeceleration(duration, fromFps);

            case TextCounter.DECELERATION_MODE :
                if (toFps != null) {
                    return getFpsDynamicDeceleration(duration, fromFps, toFps);
                } else {
                    return getFpsDynamicDeceleration(duration, fromFps);
                }
            case TextCounter.DECELERATION_LINEAR_MODE :
                return getFpsDynamicDecelerationLinear(duration, fromFps);
            case TextCounter.DECELERATION_ACCELERATION_MODE :
                return getFpsDynamicDecelerationAcceleration(duration, fromFps);

            case TextCounter.ACCELERATION_MODE :
                if (toFps != null) {
                    return getFpsDynamicAcceleration(duration, fromFps, toFps);
                } else {
                    return getFpsDynamicAcceleration(duration, fromFps);
                }
            case TextCounter.ACCELERATION_LINEAR_MODE :
                return getFpsDynamicAccelerationLinear(duration, fromFps);
            case TextCounter.ACCELERATION_DECELERATION_MODE :
                return getFpsDynamicAccelerationDeceleration(duration, fromFps);

            case TextCounter.LINEAR_TO_ALPHA_MODE :
                int[] linearToAlpha = getFpsDynamicLinear(duration, fromFps);
                alphaDynamic = toAlpha.createAlphaDynamic(linearToAlpha.length);
                return linearToAlpha;
            case TextCounter.LINEAR_FROM_ALPHA_MODE :
                int[] linearFromAlpha = getFpsDynamicLinear(duration, fromFps);
                alphaDynamic = fromAlpha.createAlphaDynamic(linearFromAlpha.length);
                return linearFromAlpha;

            case TextCounter.DECELERATION_TO_ALPHA_MODE :
                int[] decToAlpha = getFpsDynamicDeceleration(duration, fromFps);
                alphaDynamic = toAlpha.createAlphaDynamic(decToAlpha.length);
                return decToAlpha;
            case TextCounter.DECELERATION_FROM_ALPHA_MODE :
                int[] decFromAlpha = getFpsDynamicDeceleration(duration, fromFps);
                alphaDynamic = fromAlpha.createAlphaDynamic(decFromAlpha.length);
                return decFromAlpha;

            case TextCounter.ACCELERATION_TO_ALPHA_MODE :
                int[] accToAlpha = getFpsDynamicAcceleration(duration, fromFps);
                alphaDynamic = toAlpha.createAlphaDynamic(accToAlpha.length);
                return accToAlpha;
            case TextCounter.ACCELERATION_FROM_ALPHA_MODE :
                int[] accFromAlpha = getFpsDynamicAcceleration(duration, fromFps);
                alphaDynamic = fromAlpha.createAlphaDynamic(accFromAlpha.length);
                return accFromAlpha;

            case TextCounter.CUSTOM_MODE :
                return getCustomDynamic(duration, fromFps, toFps);

            default:
//                если такого кейса нет, возвращаем стандартную анимацию
                return getFpsDynamicLinear(duration, fromFps);

        }
    }


    protected int[] buildFpsDynamic(@NonNull List<Part> list){
        return getFpsDynamic(list);
    }

    protected float[] buildAlphaDynamic(@NonNull List<Part> list){
        return getAlphaDynamic(list);
    }


    public int[] getFpsDynamicLinear(long duration, int fps){
        List<Part> fpsList = AnimationBuilder.newBuilder()
                .addPart(duration, fps, fps)
                .build().getFpsList();
        return getFpsDynamic(fpsList);
    }

    public int[] getFpsDynamicDeceleration(long duration, int fps){
        List<Part> fpsList = AnimationBuilder.newBuilder()
                .addPart(duration, fps, fps - fps*9/10)
                .build().getFpsList();
        return getFpsDynamic(fpsList);
    }

    public int[] getFpsDynamicDeceleration(long duration, int fromFps, int toFps){
        List<Part> fpsList = AnimationBuilder.newBuilder()
                .addPart(duration, fromFps, toFps)
                .build().getFpsList();
        return getFpsDynamic(fpsList);
    }

    public int[] getFpsDynamicAcceleration(long duration, int fps){
        List<Part> fpsList = AnimationBuilder.newBuilder()
                .addPart(duration, fps - fps*9/10, fps)
                .build().getFpsList();
        return getFpsDynamic(fpsList);
    }

    public int[] getFpsDynamicAcceleration(long duration, int fromFps, int toFps){
        List<Part> fpsList = AnimationBuilder.newBuilder()
                .addPart(duration, fromFps, toFps)
                .build().getFpsList();
        return getFpsDynamic(fpsList);
    }

    public int[] getFpsDynamicLinearAcceleration(long duration, int fps){
        List<Part> fpsList = AnimationBuilder.newBuilder()
                .addPart(duration/4, fps/4, fps/4)
                .addPart(duration*3/4, fps/4, fps + fps*19/20)
                .build().getFpsList();
        return getFpsDynamic(fpsList);
    }

    public int[] getFpsDynamicLinearDeceleration(long duration, int fps){
        List<Part> fpsList = AnimationBuilder.newBuilder()
                .addPart(duration/3, fps, fps)
                .addPart(duration*2/3, fps, fps - fps*9/10)
                .build().getFpsList();
        return getFpsDynamic(fpsList);
    }

    public int[] getFpsDynamicAccelerationLinear(long duration, int fps){
        List<Part> fpsList = AnimationBuilder.newBuilder()
                .addPart(duration*2/3, fps - fps*9/10, fps)
                .addPart(duration/3, fps, fps)
                .build().getFpsList();
        return getFpsDynamic(fpsList);
    }

    public int[] getFpsDynamicAccelerationDeceleration(long duration, int fps){
        List<Part> fpsList = AnimationBuilder.newBuilder()
                .addPart(duration/2, fps - fps*9/10, fps + fps*9/10)
                .addPart(duration/2, fps + fps*9/10, fps - fps*9/10)
                .build().getFpsList();
        return getFpsDynamic(fpsList);
    }

    public int[] getFpsDynamicDecelerationLinear(long duration, int fps){
        List<Part> fpsList = AnimationBuilder.newBuilder()
                .addPart(duration*2/3, fps + fps*9/10, fps/2)
                .addPart(duration/3, fps/2, fps/2)
                .build().getFpsList();
        return getFpsDynamic(fpsList);
    }

    public int[] getFpsDynamicDecelerationAcceleration(long duration, int fps){
        List<Part> fpsList = AnimationBuilder.newBuilder()
                .addPart(duration/2, fps + fps*9/10, fps - fps*9/10)
                .addPart(duration/2, fps - fps*9/10, fps + fps*9/10)
                .build().getFpsList();
        return getFpsDynamic(fpsList);
    }

    public int[] getCustomDynamic(long duration, int fromFps, int toFps){
        List<Part> fpsList = AnimationBuilder.newBuilder()
                .addPart(duration, fromFps, toFps)
                .build().getFpsList();
        return getFpsDynamic(fpsList);
    }

    private int[] getFpsDynamic(List<Part> fpsList){
        List<int[]> arrays = new ArrayList<>();

        for(Part part : fpsList){
            int[] onePart = getFpsDynamicOfPart(part.getDuration(), part.getFromFps(), part.getToFps());
            part.setAmountFrames(onePart.length);
            arrays.add(onePart);
        }

        return convertFpsToTimePause(unitIntArrays(arrays));
    }

    private int[] getFpsDynamicOfPart(long duration, int fromFps, int toFps){
        int[] fpsDynamic = countTimePauses(duration, fromFps, toFps);
        fpsDynamic = convertTimePauseToFps(fpsDynamic);
        return fpsDynamic;
    }

    private float[] getAlphaDynamic(List<Part> fpsList){
        List<float[]> arrays = new ArrayList<>();

//        проверяем есть ли данные по изменению альфаканала
        boolean hasAlphaData = false;

        for(Part part : fpsList){
            if(part.getAlphaDynamic() != null){
                hasAlphaData = true;
                break;
            }
        }
        if(!hasAlphaData){
            return null;
        }

        for(Part part : fpsList){
            if(part.getAlphaDynamic() == null){
                AlphaBuilder alphaBuilder = AlphaBuilder.newInstance().fromAlpha(1).toAlpha(1);
                arrays.add(alphaBuilder.createAlphaDynamic(part.getAmountFrames()));
            } else {
                arrays.add(part.getAlphaDynamic().createAlphaDynamic(part.getAmountFrames()));
            }
        }

        return unitFloatArrays(arrays);
    }

    /**
     * Converters
     * @param fps
     * @return
     */
    public static int convertFpsToTimePause(int fps){
        return convert(fps);
    }

    public static int[] convertFpsToTimePause(int[] fpsArray){
        for (int i = 0; i < fpsArray.length; i++){
            fpsArray[i] = convertFpsToTimePause(fpsArray[i]);
        }
        return fpsArray;
    }

    private static int convertTimePauseToFps(int timePause){
        return convert(timePause);
    }

    private static int[] convertTimePauseToFps(int[] timePauses){
        for (int i = 0; i < timePauses.length; i++){
            timePauses[i] = convertTimePauseToFps(timePauses[i]);
        }
        return timePauses;
    }

    private static int convert(int value){
        return 1000/value;
    }

    public int[] countTimePauses(long duration, int fromFps, int toFps){
        int fromTimePause = convertFpsToTimePause(fromFps);
        int toTimePause = convertFpsToTimePause(toFps);

        float midleTimePause = (fromTimePause + toTimePause)/ 2;

        int amountFrames = (int)(duration/midleTimePause);
        if(amountFrames == 0){
            amountFrames = 1;
        }

        float stepFps = toTimePause - fromTimePause;

        stepFps = stepFps/amountFrames;

        int[] timePause = new int[amountFrames];

        for(int i = 0; i < amountFrames; i++){
            timePause[i] = (int)(fromTimePause + (stepFps*i));
        }
        return timePause;
    }

    private int[] unitIntArrays(List<int[]> list){

        int[] unitArray;

        int arraylenght = 0;

        for(int[] array : list){
            arraylenght += array.length;
        }

        unitArray = new int[arraylenght];

        int index = 0;
        for(int[] array : list){
            for(int i = 0; i < array.length; i++){
                unitArray[index] = array[i];
                index++;
            }
        }

        return unitArray;
    }

    private float[] unitFloatArrays(List<float[]> list){

        float[] unitArray;

        int arraylenght = 0;

        for(float[] array : list){
            arraylenght += array.length;
        }

        unitArray = new float[arraylenght];

        int index = 0;
        for(float[] array : list){
            for(int i = 0; i < array.length; i++){
                unitArray[index] = array[i];
                index++;
            }
        }

        return unitArray;
    }
}
