# TextCounter
Animation of text counter

## Download
Gradle:
``` groovy 
compile 'ru.bullyboo.animation:text_counter:1.0.1'
```

Maven:
``` xml
<dependency> 
  <groupId>ru.bullyboo.animation</groupId> 
  <artifactId>text_counter</artifactId> 
  <version>1.0.1</version> 
  <type>pom</type> 
</dependency>
```

## Samples
Apk of [Demo version](https://github.com/BullyBoo/TextCounter/releases/download/1.0.0/app-debug.apk)

## Usage
To use text counter you need to call `TextAnimator.newBuilder()` and set properties of animation.
For example:
``` java
TextCounter.newBuilder()
                .setTextView(textView)
                .setType(TextAnimator.LONG)
                .setCustomAnimation(modeBuilder)
                .from(100l)
                .to(1000l)
                .build()
                .start();
```

The main method of TextAnimation Builder is `setTextView();`, `from();`, `to();` and `setDuration();`. It's will not working without this.

## Methods

`setTextView();`
You need to set TextView. which will show text animaton of counter.

`from();` and `to();`
These methods support with:
```
byte, short, int, float, long, double

and 

Byte, Short, Integer, Float, Long, Double, String
```

You can also set Type of numbers directly, which will be used in counter. Use `setType();` method for.
All of them will convert to Double type!

And you can use method `setRound();` to double and float types for setting round your number after decimal separator. For example:
```
//...
from(0d).
to(100d).
setRound(2)
//...
```
You will have result - 100.00

`setPrefix();` and `setPostfix();`
These methods will add to text counter usual String parameters. For example, if you want to show:"Result: 175$". You need to add `setPrefix("Result: ");` and `setPostfix("$");` to your builder.

Method `setDuration();` does setting duration of animation.

Method `setFPS();` does setting fps of animation. If you do not use this method, the text counter will use default 60 FPS.

Method `setMode();` does setting mode of animation. You can use one of next modes:
```
LINEAR_MODE
LINEAR_ACCELERATION_MODE
LINEAR_DECELERATION_MODE
DECELERATION_MODE
DECELERATION_LINEAR_MODE
DECELERATION_ACCELERATION_MODE
ACCELERATION_MODE
ACCELERATION_LINEAR_MODE
ACCELERATION_DECELERATION_MODE
LINEAR_TO_ALPHA_MODE
LINEAR_FROM_ALPHA_MODE
DECELERATION_TO_ALPHA_MODE
DECELERATION_FROM_ALPHA_MODE
ACCELERATION_TO_ALPHA_MODE
ACCELERATION_FROM_ALPHA_MODE
CUSTOM_MODE
```
I belive that you can understand the meaning of ths methods from their names.

You can make to text counter automatically change fps dynamic. Use `setFromFps();` and `setToFps();` methods for.

Also you can use set Fps Dynamic with method `setFpsDynamic();'`, but be careful you need to put int array in this method.

It's absolutely same with alpha channel. You can use `setFromAlpha();` and `setToAlpha();` methods for. And `setAlphaDynamic();` with float array.

The main method of this library is `setCustomAnimation()`. You need to use it with `ModeConstructor.class`. 
With this method you can create your own animation!

For example:
``` java
AnimationBuilder modeBuilder = AnimationBuilder.newBuilder()
                .addPart(1000, 60)
                .addPart(1000, 60, 100)
                .addPart(1000, 100, AlphaBuilder.newInstance().fromAlpha(1f).toAlpha(0f))
                .addPart(1000, 100, 60, AlphaBuilder.newInstance().fromAlpha(0f).toAlpha(1f))
                .build();
                
TextCounter animator = TextCounter.newBuilder()
                .setTextView(textView)
                .setCustomAnimation(modeBuilder)
                .from(0d)
                .to(100d)
                .build()
                .start();
```

What happened here?
The first method `addPart();` has the first parameter `duration` and the second parameter `fps`.
The second: `duration`, `fromFps`,`toFps`.
The third: `duration`, `fps`, and AlphaBuilder, which will create alpha dynamic change.
Fourth: `duration`, `fromFps`, `toFps`, and AlphaBuilder, which will create alpha dynamic change.

You can create everthing you want with method `setCustomAnimation();`.

## License
```
  Copyright (C) 2017 BullyBoo

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ```
