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

public class AlphaBuilder {

    public static AlphaBuilder newInstance(){
        return new AlphaBuilder();
    }

    private AlphaBuilder(){

    }

    private float fromAlpha;

    private float toAlpha;

    /**
     * @param fromAlpha - value from 0.0 to 1.0
     * @return
     */
    public AlphaBuilder fromAlpha(float fromAlpha) {
        this.fromAlpha = fromAlpha;
        return this;
    }

    /**
     * @param toAlpha - value from 0.0 to 1.0
     * @return
     */
    public AlphaBuilder toAlpha(float toAlpha) {
        this.toAlpha = toAlpha;
        return this;
    }

    protected float[] createAlphaDynamic(int arraySize){

        if(arraySize == 0){
            return null;
        }
        float[] alphaDynamic = new float[arraySize];

        float step = (toAlpha - fromAlpha)/arraySize;
        for(int i = 0; i < arraySize; i++){
            alphaDynamic[i] = fromAlpha + step*i;
        }

        alphaDynamic[0] = fromAlpha;
        alphaDynamic[arraySize-1] = toAlpha;

        return alphaDynamic;
    }
}
