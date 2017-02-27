# TextCounter
Animation of text counter

## Download
Gradle:

Maven:

## Samples

## Usage
To use text counter you need call `TextAnimator.newBuilder()` and set properties of animation.
For example:
``` java
TextAnimator.newBuilder()
                .setTextView(textView)
                .setType(TextAnimator.LONG)
                .setCustomAnimation(modeBuilder)
                .from(100l)
                .to(1000l)
                .build()
                .start();
```

The main method of TextAnimation Builder is `setTextView();`, `from();`, `to();` and `setDuration();`. It's will not working without.

## Methods

`setTextView();`
You need to set TextView. which will show text animaton of counter.

`from();` and `to();`
These methods supports with:
```
byte
short
int
float
long
double

and 

Byte
Short
Integer
Float
Long
Double
String
```

You also can directly set Typt of numbers, which will use in counter. Use `setType();` method for.
All of them will converting to Double type!

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
These methods will add to text counter usual String params. For example, if you want to show:"Result: 175$". You need to add `setPrefix("Result: ");` and `setPostfix("$");` to your builder.

Method `setDuration();` does setting duration of animation.

Method `setFPS();` does setting fps of animation. If you will not use this method, text counter will use default 60 FPS.

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
I think that you can understand meaning of ths methods from their names.

You can say to text counter automaticly change them fps dynamic. Use `setFromFps();` and `setToFps();` methods for.

Also you can use set Fps Dynimc with method - `setFpsDynamic();'`, but be careful you need to put int array in this method.

It's absolutely same with alpha channel. You can use `setFromAlpha();` and `setToAlpha();` methods for. And `setAlphaDynamic();` with float array.

The main method of this library is - `setCustomAnimation()`. You need to use it with ModeConstructor.class. 
With this method you can create your own animation!

For example:
``` java
AnimationBuilder modeBuilder = AnimationBuilder.newBuilder()
                .addPart(1000, 60)
                .addPart(1000, 60, 100)
                .addPart(1000, 100, AlphaBuilder.newInstance().fromAlpha(1f).toAlpha(0f))
                .addPart(1000, 100, 60, AlphaBuilder.newInstance().fromAlpha(0f).toAlpha(1f))
                .build();
                
TextAnimator animator = TextCounter.newBuilder()
                .setTextView(textView)
                .setCustomAnimation(modeBuilder)
                .from(0d)
                .to(100d)
                .build()
                .start();
```

What's happen here?
The first method `addPart();` has the first parameter `duration` and the second parameter `fps`.
The second: `duration`, `fromFps`,`toFps`.
The third: `duration`, `fps`, and AlphaBuilder, which will create alpha dynamic change.
Fourth: `duration`, `fromFps`, `toFps`, and AlphaBuilder, which will create alpha dynamic change.

You can create all what you want with method `setCustomAnimation();`.

## License
