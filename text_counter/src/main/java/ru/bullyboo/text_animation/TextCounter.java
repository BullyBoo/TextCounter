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

import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.text.DecimalFormat;

import ru.bullyboo.text_animation.exeptions.BuilderDataException;

public class TextCounter{

    public static final int LINEAR_MODE = 1;
    public static final int LINEAR_ACCELERATION_MODE = 2;
    public static final int LINEAR_DECELERATION_MODE = 3;

    public static final int DECELERATION_MODE = 4;
    public static final int DECELERATION_LINEAR_MODE = 5;
    public static final int DECELERATION_ACCELERATION_MODE = 6;

    public static final int ACCELERATION_MODE = 7;
    public static final int ACCELERATION_LINEAR_MODE = 8;
    public static final int ACCELERATION_DECELERATION_MODE = 9;


    public static final int LINEAR_TO_ALPHA_MODE = 10;
    public static final int LINEAR_FROM_ALPHA_MODE = 11;

    public static final int DECELERATION_TO_ALPHA_MODE  = 12;
    public static final int DECELERATION_FROM_ALPHA_MODE  = 13;

    public static final int ACCELERATION_TO_ALPHA_MODE  = 14;
    public static final int ACCELERATION_FROM_ALPHA_MODE  = 15;

    public static final int CUSTOM_MODE  = 16;

    /**
     * Support primitive data types
     */
    public static final int BYTE = 1;
    public static final int SHORT = 2;
    public static final int INT = 3;
    public static final int FLOAT = 4;
    public static final int LONG = 5;
    public static final int DOUBLE = 6;

    /**
     * TextView which will show counter animation
     */
    private TextView textView;

    /**
     * from - start number
     * to - end number
     */
    private Double from, to;

    /**
     * Prefix and Postfix
     * Its use to add to start and end of text counter
     */
    private String prefix = "", postfix = "";

    /**
     * Autodetection parameter type, which will obtain from methods `from();` and `to();` (low priority)
     * User selected parameter type, which will obtain from methods `from();` and `to();` (high priority)=
     *
     * Final type which will use in programm
     */
    private int autoType;
    private int userType;
    private int type;

    /**
     * Selected animation mode
     */
    private int mode = 1;

    /**
     * Duration of animation
     */
    private long duration;

    /**
     * Step. How mush will increase or decrease number value for one frame
     */
    private Double step;

    /**
     * Amoint frames per second (FPS)
     * Default value is 60 frames per second
     *
     * Dynamic of changes fps
     */
    private int fps = 60;
    private Integer fromFps, toFps;

    private int[] fpsDynamic;
    private int[] userFpsDynamic;

    /**
     * Dynamic of changes alpha
     */
    private float alpha = 1;
    private Float fromAlpha, toAlpha;

    private float[] alphaDynamic;
    private float[] userAlphaDynamic;

    /**
     * List custom animations, which will create in ModeBuilder
     */
    private AnimationBuilder partList;

    /**
     * Current number
     * The final number
     */
    private Double currentNumber;
    private double targetNumber;


    /**
     * Object of formater, which will round a number
     */
    private DecimalFormat decimalFormat;

    /**
     * Flag of animation
     * true - animation is playing now
     * false - animation isn`t playing now
     */
    private boolean isAnimate = false;

    private TextCounter(){

    }

    public static Builder newBuilder() {
        return new TextCounter().new Builder();
    }

    public void start(){
//        заглушка, чтобы нельзя было запустить анимацию повторно, пока она не закончится
        if(!isAnimate){
            isAnimate = true;
            frame = 0;
            init();
            timer();
        }
    }

    private void init(){
        long amountFrames = fpsDynamic.length + 1;

        currentNumber = from;
        targetNumber = to;
        step = to - from;
        step = step / amountFrames;
    }

    /**
     * Frame`s counter
     */
    private int frame;

    private void timer(){
        new Handler().postDelayed(changeText, fpsDynamic[frame]);
    }

    private Runnable changeText = new Runnable() {
        @Override
        public void run() {
            currentNumber += step;

//            настраиваем прозрачность
            if(alphaDynamic != null){
                textView.setAlpha(alphaDynamic[frame]);
            }
//            прибавляем кадр
            if(frame+1 < fpsDynamic.length) {
                frame++;
            }

//            округляем число
            String roundedDouble = decimalFormat.format(currentNumber);
            roundedDouble = roundedDouble.replace(",", ".");

            changeText(roundedDouble);

//            если увеличиываем число
            if(step > 0){
                if(currentNumber < targetNumber){
                    timer();
                } else {
                    lastTextUpdate();
                    isAnimate = false;
                }

//                если уменьшаем число
            } else if (step < 0){
                if(currentNumber > targetNumber){
                    timer();
                } else {
                    lastTextUpdate();
                    isAnimate = false;
                }
            }
        }
    };

    private void changeText(String current){
        switch (type){
            case BYTE:

                textView.setText(prefix + currentNumber.byteValue() + postfix);
                break;
            case SHORT:
                textView.setText(prefix + currentNumber.shortValue() + postfix);
                break;
            case INT:
                textView.setText(prefix + currentNumber.intValue() + postfix);
                break;
            case FLOAT:
                textView.setText(prefix + current + postfix);
                break;
            case LONG:
                textView.setText(prefix + currentNumber.longValue() + postfix);
                break;
            case DOUBLE:
                textView.setText(prefix + current + postfix);
                break;
            default:
                return;
        }
    }

    private void lastTextUpdate(){
        switch (type){
            case BYTE:
                textView.setText(prefix + to.byteValue() + postfix);
                break;
            case SHORT:
                textView.setText(prefix + to.shortValue() + postfix);
                break;
            case INT:
                textView.setText(prefix + to.intValue() + postfix);
                break;
            case FLOAT:
                textView.setText(prefix + decimalFormat.format(to.floatValue()).replace(",", ".") + postfix);
                break;
            case LONG:
                textView.setText(prefix + to.longValue() + postfix);
                break;
            case DOUBLE:
                textView.setText(prefix + decimalFormat.format(to).replace(",", ".") + postfix);
                break;
            default:
                return;
        }
    }

    public class Builder{

        private Builder(){

        }

        public Builder setTextView(@NonNull TextView textView){
            TextCounter.this.textView = textView;
            return this;
        }

        public Builder from(byte fromByte){
            from = Double.valueOf(fromByte);
            autoType = BYTE;
            return this;
        }

        public Builder to(byte toByte){
            to = Double.valueOf(toByte);
            autoType = BYTE;
            return this;
        }

        public Builder from(short fromShort){
            from = Double.valueOf(fromShort);
            autoType = SHORT;
            return this;
        }

        public Builder to(short toShort){
            to = Double.valueOf(toShort);
            autoType = SHORT;
            return this;
        }

        public Builder from(int fromInt){
            from = Double.valueOf(fromInt);
            autoType = INT;
            return this;
        }

        public Builder to(int toInt){
            to = Double.valueOf(toInt);
            autoType = INT;
            return this;
        }

        public Builder from(float fromFloat){
            from = Double.valueOf(fromFloat);
            autoType = FLOAT;
            return this;
        }

        public Builder to(float toFloat){
            to = Double.valueOf(toFloat);
            autoType = FLOAT;
            return this;
        }

        public Builder from(long fromLong){
            from = Double.valueOf(fromLong);
            autoType = LONG;
            return this;
        }

        public Builder to(long toLong){
            to = Double.valueOf(toLong);
            autoType = LONG;
            return this;
        }

        public Builder from(double fromDouble){
            from = fromDouble;
            autoType = DOUBLE;
            return this;
        }

        public Builder to(double toDouble){
            to = toDouble;
            autoType = DOUBLE;
            return this;
        }

        public Builder from(Byte fromByte){
            from = Double.valueOf(fromByte);
            autoType = BYTE;
            return this;
        }

        public Builder to(Byte toByte){
            to = Double.valueOf(toByte);
            autoType = BYTE;
            return this;
        }

        public Builder from(Short fromShort){
            from = Double.valueOf(fromShort);
            autoType = SHORT;
            return this;
        }

        public Builder to(Short toShort){
            to = Double.valueOf(toShort);
            autoType = SHORT;
            return this;
        }

        public Builder from(Integer fromInt){
            from = Double.valueOf(fromInt);
            autoType = INT;
            return this;
        }

        public Builder to(Integer toInt){
            to = Double.valueOf(toInt);
            autoType = INT;
            return this;
        }

        public Builder from(Float fromFloat){
            from = Double.valueOf(fromFloat);
            autoType = FLOAT;
            return this;
        }

        public Builder to(Float toFloat){
            to = Double.valueOf(toFloat);
            autoType = FLOAT;
            return this;
        }

        public Builder from(Long fromLong){
            from = Double.valueOf(fromLong);
            autoType = LONG;
            return this;
        }

        public Builder to(Long toLong){
            to = Double.valueOf(toLong);
            autoType = LONG;
            return this;
        }

        public Builder from(Double fromDouble){
            from = fromDouble;
            autoType = DOUBLE;
            return this;
        }

        public Builder to(Double toDouble){
            to = toDouble;
            autoType = DOUBLE;
            return this;
        }

        public Builder from(String fromDouble){
            from = parseStringToDigitFrom(fromDouble);
            autoType = DOUBLE;
            return this;
        }

        public Builder to(String toDouble){
            to = parseStringToDigitTo(toDouble);
            autoType = DOUBLE;
            return this;
        }

        public Builder setPrefix(String prefix){
            TextCounter.this.prefix = prefix;
            return this;
        }

        public Builder setType(@Type int type){
            userType = type;
            return this;
        }

        public Builder setPostfix(String postfix){
            TextCounter.this.postfix = postfix;
            return this;
        }

        public Builder setDuration(long millis){
            duration = millis;
            return this;
        }

        public Builder setFPS(int fps){
            TextCounter.this.fps = fps;
            return this;
        }

        public Builder setMode(@Mode int mode){
            TextCounter.this.mode = mode;
            return this;
        }

        public Builder setFpsDynamic(int[] dynamic){
            userFpsDynamic = dynamic;
            return this;
        }

        public Builder setRound(int round){
            decimalFormat = new DecimalFormat(getRoundPattern(round));
            return this;
        }

        public Builder setDecimalFormat(DecimalFormat decimalFormat){
            TextCounter.this.decimalFormat = decimalFormat;
            return this;
        }

        public Builder setFromFps(int fromFps){
            TextCounter.this.fromFps = fromFps;
            return this;
        }

        public Builder setToFps(int toFps){
            TextCounter.this.toFps = toFps;
            return this;
        }

        public Builder setFromAlpha(float fromAlpha){
            TextCounter.this.fromAlpha = fromAlpha;
            return this;
        }

        public Builder setToAlpha(float toAlpha){
            TextCounter.this.toAlpha = toAlpha;
            return this;
        }

        public Builder setAlphaDynamic(float[] alphaDynamic){
            TextCounter.this.userAlphaDynamic = userAlphaDynamic;
            return this;
        }

        public Builder setCustomAnimation(@NonNull AnimationBuilder partList){
            TextCounter.this.partList = partList;
            return this;
        }

        public TextCounter build(){

            ModeConstructor constuctor = buildFpsDynamic(new ModeConstructor());
            buildAlphaDynamic(constuctor);

            buildType();

            if(decimalFormat == null){
                decimalFormat = new DecimalFormat(getRoundPattern(0));
            }

            try {
                checkData();
            } catch (BuilderDataException e) {
                e.printStackTrace();
                throw new NullPointerException(BuilderDataException.DIDNT_SET_DATA);
            }

            return TextCounter.this;
        }

        private double parseStringToDigitFrom(String string){

            try {
                return Double.valueOf(string);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return 0;
        }

        private double parseStringToDigitTo(String string){

            try {
                return Double.valueOf(string);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return 0;
        }

        private String getRoundPattern(int round){

            StringBuilder roundPattern = new StringBuilder();
            roundPattern.append("#0");

            if (round < 0) {
                return null;
            } else if (round > 0){
                roundPattern.append(".");
                for (int i = 0; i < round; i++){
                    roundPattern.append("0");
                }
            }
            return roundPattern.toString();
        }


        private ModeConstructor buildFpsDynamic(ModeConstructor constuctor){

            if(partList != null){
                fpsDynamic = constuctor.buildFpsDynamic(partList.getFpsList());
                return constuctor;
            }

            if(userFpsDynamic != null){
                fpsDynamic = constuctor.convertFpsToTimePause(userFpsDynamic);
                return constuctor;
            }

            if (fromFps != null && toFps != null){
                fpsDynamic = constuctor.getModeFpsDynamic(mode, duration, fromFps, toFps);
                return constuctor;
            }
            if(fromFps == null && toFps == null){
                fpsDynamic = constuctor.getModeFpsDynamic(mode, duration, fps);
                return constuctor;
            }

            try {
                if(fromFps == null && toFps != null){
                    throw new BuilderDataException(BuilderDataException.FROM_FPS_EQUELS_NULL);
                } else if(fromFps != null && toFps == null){
                    throw new BuilderDataException(BuilderDataException.TO_FPS_EQUELS_NULL);
                } else {
                    throw new BuilderDataException(BuilderDataException.DIDNT_SET_FPS_DATA);
                }
            } catch (BuilderDataException e) {
                e.printStackTrace();
                return constuctor;
            }
        }

        private void buildAlphaDynamic(ModeConstructor constuctor){
            if(partList != null){
                alphaDynamic = constuctor.buildAlphaDynamic(partList.getFpsList());

                if(alphaDynamic != null){
                    return;
                }
            }

            if(userAlphaDynamic != null){
                alphaDynamic = userAlphaDynamic;
                return;
            }

            if (fromAlpha != null && toAlpha != null){
                AlphaBuilder alphaBuilder = AlphaBuilder.newInstance().fromAlpha(fromAlpha).toAlpha(toAlpha);
                alphaDynamic = alphaBuilder.createAlphaDynamic(fpsDynamic.length);
                return;
            }

            if(mode >=10 && mode <= 15){
                alphaDynamic = constuctor.getAlphaDynamic();
                return;
            }

            if (fromAlpha == null && toAlpha == null){
                AlphaBuilder alphaBuilder = AlphaBuilder.newInstance().fromAlpha(alpha).toAlpha(alpha);
                alphaDynamic = alphaBuilder.createAlphaDynamic(fpsDynamic.length);
                return;
            }

            try {
                if(fromAlpha == null && toAlpha != null){
                    throw new BuilderDataException(BuilderDataException.FROM_ALPHA_EQUELS_NULL);
                } else if(fromAlpha != null && toAlpha == null){
                    throw new BuilderDataException(BuilderDataException.TO_ALPHA_EQUELS_NULL);
                } else {
                    throw new BuilderDataException(BuilderDataException.DIDNT_SET_ALPHA_DATA);
                }
            } catch (BuilderDataException e) {
                e.printStackTrace();
                return;
            }
        }

        private void buildType(){
            if (userType == 0){
                type = autoType;
            } else {
                type = userType;
            }
        }

        private void checkData()throws BuilderDataException{
            if(duration == 0 && partList == null){
                throw new BuilderDataException(BuilderDataException.DIDNT_SET_DURATION);
            }
            if(textView == null){
                throw new BuilderDataException(BuilderDataException.DIDNT_SET_TEXT_VIEW);
            }
            if(from == null && to == null){
                throw new BuilderDataException(BuilderDataException.DIDNT_SET_FROM_TO);
            }
        }
    }

    @IntDef({BYTE, SHORT, INT, FLOAT, LONG, DOUBLE})
    public @interface Type {}

    @IntDef({LINEAR_MODE,
            LINEAR_ACCELERATION_MODE,
            LINEAR_DECELERATION_MODE,
            DECELERATION_MODE,
            DECELERATION_LINEAR_MODE,
            DECELERATION_ACCELERATION_MODE,
            ACCELERATION_MODE,
            ACCELERATION_LINEAR_MODE,
            ACCELERATION_DECELERATION_MODE,
            LINEAR_TO_ALPHA_MODE,
            LINEAR_FROM_ALPHA_MODE,
            DECELERATION_TO_ALPHA_MODE,
            DECELERATION_FROM_ALPHA_MODE,
            ACCELERATION_TO_ALPHA_MODE,
            ACCELERATION_FROM_ALPHA_MODE,
            CUSTOM_MODE})
    public @interface Mode {}
}
