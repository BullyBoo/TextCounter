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

package ru.bullyboo.text_animation.exeptions;

public class BuilderDataException extends Exception {

    public static final String FROM_FPS_EQUELS_NULL = "Value of fromFps is null, when value of toFps is not null";
    public static final String TO_FPS_EQUELS_NULL = "Value of toFps is null, when value of fromFps is not null";
    public static final String FROM_ALPHA_EQUELS_NULL = "Value of fromAlpha is null, when value of toAlpha is not null";
    public static final String TO_ALPHA_EQUELS_NULL = "Value of toAlpha is null, when value of fromAlpha is not null";
    public static final String DIDNT_SET_FPS_DATA = "You didn`t set data of fps. Try to use setCustomAnimation(), " +
            "or setFpsDynamic(), or setFromFps() with setToFps(), or setFps()";
    public static final String DIDNT_SET_ALPHA_DATA = "You didn`t set data of alpha. Try to use setFromAlpha() " +
            "and setToAlpha(), or setAlphaDynamic(), or set alpha in setCustomAnimation()";

    public static final String DIDNT_SET_DURATION = "You didn`t set duration";

    public static final String DIDNT_SET_TEXT_VIEW = "You didn`t set TextView object";
    public static final String DIDNT_SET_FROM_TO = "You didn`t set from and to numeric";
    public static final String DIDNT_SET_DATA = "You didn`t set all the necessery data";

    public BuilderDataException() {
    }

    public BuilderDataException(String message) {
        super(message);
    }

    public BuilderDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderDataException(Throwable cause) {
        super(cause);
    }
}
