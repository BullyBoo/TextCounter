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

public class NullPartException extends Exception {

    public static final String NULL_PART = "Didn`t find the parts on animation, try to use addPart() method";

    public NullPartException() {
    }

    public NullPartException(String message) {
        super(message);
    }

    public NullPartException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullPartException(Throwable cause) {
        super(cause);
    }
}
