package rates.exchange.bullyboo.ru.textanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ru.bullyboo.text_animation.AlphaBuilder;
import ru.bullyboo.text_animation.AnimationBuilder;
import ru.bullyboo.text_animation.TextCounter;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,
        SeekBar.OnSeekBarChangeListener, View.OnClickListener, TextWatcher{

    private RadioButton button1, button2, button3, button4, button5, button6;

    private TextView textView, round, fps, duration;
    private SeekBar seekBar1, seekBar2, seekBar3;
    private Button start;
    private ImageView swipe;
    private EditText from, to;
    private Spinner spinner;

    private TextCounter.Builder builder;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        builder = TextCounter.newBuilder();

        textView = (TextView) findViewById(R.id.textView);
        round = (TextView) findViewById(R.id.round);
        fps = (TextView) findViewById(R.id.fps);
        duration = (TextView) findViewById(R.id.duration);

        swipe = (ImageView) findViewById(R.id.swipe);
        swipe.setOnClickListener(this);

        spinner = (Spinner) findViewById(R.id.spinner);

        from = (EditText) findViewById(R.id.from);
        to = (EditText) findViewById(R.id.to);
        from.addTextChangedListener(this);

        builder.setTextView(textView);

        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekBar1.setOnSeekBarChangeListener(this);
        seekBar2.setOnSeekBarChangeListener(this);
        seekBar3.setOnSeekBarChangeListener(this);

        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);

        button1 = (RadioButton) findViewById(R.id.radioButton1);
        button2 = (RadioButton) findViewById(R.id.radioButton2);
        button3 = (RadioButton) findViewById(R.id.radioButton3);
        button4 = (RadioButton) findViewById(R.id.radioButton4);
        button5 = (RadioButton) findViewById(R.id.radioButton5);
        button6 = (RadioButton) findViewById(R.id.radioButton6);

        button1.setOnCheckedChangeListener(this);
        button2.setOnCheckedChangeListener(this);
        button3.setOnCheckedChangeListener(this);
        button4.setOnCheckedChangeListener(this);
        button5.setOnCheckedChangeListener(this);
        button6.setOnCheckedChangeListener(this);

        button1.setChecked(true);

        seekBar1.setProgress(0);
        seekBar2.setProgress(24);
        seekBar3.setProgress(1);

        builder.setTextView(textView);

        textView.setVisibility(View.VISIBLE);

        AnimationBuilder modeBuilder = AnimationBuilder.newBuilder()
                .addPart(1000, 60)
                .addPart(1000, 60, 100)
                .addPart(1000, 100, AlphaBuilder.newInstance().fromAlpha(1f).toAlpha(0f))
                .addPart(1000, 100, 60, AlphaBuilder.newInstance().fromAlpha(0f).toAlpha(1f))
                .build();
//
        /*TextAnimator animator = */TextCounter.newBuilder()
                .setTextView(textView)
                .setType(TextCounter.LONG)
                .setCustomAnimation(modeBuilder)
                .from(100l)
                .to(1000l).build().start();

//        animator.start();
    }

//    TextAnimator animator;
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        setChecking();
        compoundButton.setChecked(true);

        switch (compoundButton.getId()){
            case R.id.radioButton1:
                builder.setType(TextCounter.BYTE);
                seekBar1.setEnabled(false);
                type = TextCounter.BYTE;
                break;
            case R.id.radioButton2:
                builder.setType(TextCounter.SHORT);
                seekBar1.setEnabled(false);
                type = TextCounter.SHORT;
                break;
            case R.id.radioButton3:
                builder.setType(TextCounter.INT);
                seekBar1.setEnabled(false);
                type = TextCounter.INT;
                break;
            case R.id.radioButton4:
                builder.setType(TextCounter.FLOAT);
                seekBar1.setEnabled(true);
                type = TextCounter.FLOAT;
                break;
            case R.id.radioButton5:
                builder.setType(TextCounter.LONG);
                seekBar1.setEnabled(false);
                type = TextCounter.LONG;
                break;
            case R.id.radioButton6:
                builder.setType(TextCounter.DOUBLE);
                seekBar1.setEnabled(true);
                type = TextCounter.DOUBLE;
                break;
            default:
                return;
        }
    }

    private void setChecking(){
        button1.setChecked(false);
        button2.setChecked(false);
        button3.setChecked(false);
        button4.setChecked(false);
        button5.setChecked(false);
        button6.setChecked(false);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()){
            case R.id.seekBar1:
                round.setText(i + "");
                builder.setRound(i);
                break;
            case R.id.seekBar2:
                if(i > 0){
                    fps.setText(i + "");
                    builder.setFPS(i);
                }
                break;
            case R.id.seekBar3:
                if(i > 0){
                    duration.setText((float)(i * 0.5) + "");
                    builder.setDuration(i * 500);
                }
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.start){
            try {
                getFromTo();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(this, "Неправильное значения для этого типа", Toast.LENGTH_LONG).show();
            }
        } else if(view.getId() == R.id.swipe){
            String fromText = from.getText().toString();
            String toText = to.getText().toString();

            from.setText(toText);
            to.setText(fromText);
        }

    }

    private void getFromTo(){

        switch (type){
            case TextCounter.BYTE:

                byte fromB = Byte.valueOf(from.getText().toString());
                byte toB = Byte.valueOf(to.getText().toString());

                builder.from(fromB).to(toB);
                break;
            case TextCounter.SHORT:
                short fromS = Short.valueOf(from.getText().toString());
                short toS = Short.valueOf(to.getText().toString());

                builder.from(fromS).to(toS);
                break;
            case TextCounter.INT:
                int fromI = Integer.valueOf(from.getText().toString());
                int toI = Integer.valueOf(to.getText().toString());

                builder.from(fromI).to(toI);
                break;
            case TextCounter.FLOAT:
                float fromF = Float.valueOf(from.getText().toString());
                float toF = Float.valueOf(to.getText().toString());

                builder.from(fromF).to(toF);
                break;
            case TextCounter.LONG:
                long fromL = Long.valueOf(from.getText().toString());
                long toL = Long.valueOf(to.getText().toString());

                builder.from(fromL).to(toL);
                break;
            case TextCounter.DOUBLE:
                double fromD = Double.valueOf(from.getText().toString());
                double toD = Double.valueOf(to.getText().toString());

                builder.from(fromD).to(toD);
                break;
            default:
                return;
        }
        builder.setMode(getMode(spinner.getSelectedItem().toString()));
        builder.build().start();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        textView.setText(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private @TextCounter.Mode int getMode(String mode){
        if(mode.equals("LINEAR_MODE")){
            return TextCounter.LINEAR_MODE;
        }
        if(mode.equals("LINEAR_ACCELERATION_MODE")){
            return TextCounter.LINEAR_ACCELERATION_MODE;
        }
        if(mode.equals("LINEAR_DECELERATION_MODE")){
            return TextCounter.LINEAR_DECELERATION_MODE;
        }
        if(mode.equals("DECELERATION_MODE")){
            return TextCounter.DECELERATION_MODE;
        }
        if(mode.equals("DECELERATION_LINEAR_MODE")){
            return TextCounter.DECELERATION_LINEAR_MODE;
        }
        if(mode.equals("DECELERATION_ACCELERATION_MODE")){
            return TextCounter.DECELERATION_ACCELERATION_MODE;
        }

        if(mode.equals("ACCELERATION_MODE")){
            return TextCounter.ACCELERATION_MODE;
        }
        if(mode.equals("ACCELERATION_LINEAR_MODE")){
            return TextCounter.ACCELERATION_LINEAR_MODE;
        }
        if(mode.equals("ACCELERATION_DECELERATION_MODE")){
            return TextCounter.ACCELERATION_DECELERATION_MODE;
        }


        if(mode.equals("LINEAR_TO_ALPHA_MODE")){
            return TextCounter.LINEAR_TO_ALPHA_MODE;
        }
        if(mode.equals("LINEAR_FROM_ALPHA_MODE")){
            return TextCounter.LINEAR_FROM_ALPHA_MODE;
        }

        if(mode.equals("DECELERATION_TO_ALPHA_MODE")){
            return TextCounter.DECELERATION_TO_ALPHA_MODE;
        }
        if(mode.equals("DECELERATION_FROM_ALPHA_MODE")){
            return TextCounter.DECELERATION_FROM_ALPHA_MODE;
        }

        if(mode.equals("ACCELERATION_TO_ALPHA_MODE")){
            return TextCounter.ACCELERATION_TO_ALPHA_MODE;
        }
        if(mode.equals("ACCELERATION_FROM_ALPHA_MODE")){
            return TextCounter.ACCELERATION_FROM_ALPHA_MODE;
        }

        if(mode.equals("CUSTOM_MODE")){
            return TextCounter.CUSTOM_MODE;
        }
        return TextCounter.LINEAR_MODE;
    }
}
