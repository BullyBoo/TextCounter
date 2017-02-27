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

import java.util.ArrayList;
import java.util.List;

import ru.bullyboo.text_animation.exeptions.NullPartException;

public class AnimationBuilder {

    private List<Part> fpsList;

    public static Builder newBuilder(){
        return new AnimationBuilder().new Builder();
    }

    private AnimationBuilder(){
        fpsList = new ArrayList<>();
    }

    protected List<Part> getFpsList(){
        return this.fpsList;
    }

    public class Builder{

        public Builder addPart(long duration, int fps){
            fpsList.add(new Part(duration, fps, fps));
            return this;
        }

        public Builder addPart(long duration, int fps, AlphaBuilder animator){
            fpsList.add(new Part(duration, fps, animator));
            return this;
        }

        public Builder addPart(long duration, int fromFps, int toFps){
            fpsList.add(new Part(duration, fromFps, toFps));
            return this;
        }

        public Builder addPart(long duration, int fromFps, int toFps, AlphaBuilder animator){
            fpsList.add(new Part(duration, fromFps, toFps, animator));
            return this;
        }

        public AnimationBuilder build(){
            if(fpsList.size() > 0){
                return AnimationBuilder.this;
            } else {
                try {
                    throw new NullPartException(NullPartException.NULL_PART);
                } catch (NullPartException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
    }
}
