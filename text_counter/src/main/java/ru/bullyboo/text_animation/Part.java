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

class Part{

    private int fromFps;
    private int toFps;
    private long duration;

    private int amountFrames;

    private AlphaBuilder alphaAnimator;

    public Part(long duration, int fps) {
        this.duration = duration;
        this.fromFps = fps;
        this.toFps = fps;
    }

    public Part(long duration, int fps, @NonNull AlphaBuilder alphaAnimator) {
        this.duration = duration;
        this.fromFps = fps;
        this.toFps = fps;
        this.alphaAnimator = alphaAnimator;
    }

    public Part(long duration, int fromFps, int toFps) {
        this.duration = duration;
        this.fromFps = fromFps;
        this.toFps = toFps;
    }

    public Part(long duration, int fromFps, int toFps, @NonNull AlphaBuilder alphaAnimator) {
        this.duration = duration;
        this.fromFps = fromFps;
        this.toFps = toFps;
        this.alphaAnimator = alphaAnimator;
    }

    public int getFromFps() {
        return fromFps;
    }

    public int getToFps() {
        return toFps;
    }

    public long getDuration() {
        return duration;
    }

    protected void setAmountFrames(int amountFrames){
        this.amountFrames = amountFrames;
    }

    protected int getAmountFrames(){
        return amountFrames;
    }

    public AlphaBuilder getAlphaDynamic(){
        return alphaAnimator;
    }
}