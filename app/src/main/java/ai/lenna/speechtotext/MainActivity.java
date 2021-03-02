package ai.lenna.speechtotext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RecognitionListener {
    ImageView img_mic;
    TextView tv_result;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private boolean isTouch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        img_mic = findViewById(R.id.iv_mic);
        tv_result = findViewById(R.id.tv_result);
        speech = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        SpeakToTextUtils.setIntentSpeakToText(recognizerIntent, MainActivity.this);
        img_mic.setOnClickListener(onMic);





    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},1234);
        }
    }

    private View.OnClickListener onMic = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isTouch) {
                isTouch = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    img_mic.setImageDrawable(getResources().getDrawable(R.drawable.ic_mic_off, MainActivity.this.getApplicationContext().getTheme()));
                } else {
                    img_mic.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_mic_24));
                }
                speech.startListening(recognizerIntent);
            } else {
                isTouch = true;
                speech.stopListening();
            }
        }
    };

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.i("Log Mic = ", "onReadyForSpeech");

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i("Log Mic = ", "onBeginningOfSpeech");

    }

    @Override
    public void onRmsChanged(float rmsdB) {


    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
        Log.i("Log Mic = ", "onEndOfSpeech");
        isTouch = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img_mic.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_mic_24, Objects.requireNonNull(MainActivity.this).getApplicationContext().getTheme()));
        } else {
            img_mic.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_mic_24));
        }


    }

    @Override
    public void onError(int error) {
        Log.i("Log Mic = ", "onError " + error);

    }

    @Override
    public void onResults(Bundle results) {
        Log.i("Log Mic = ", "onResults " + results  );
        isTouch = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (!TextUtils.isEmpty(tv_result.getText())) {
//                tv_result.setText("");
            }
            img_mic.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_mic_24, getApplicationContext().getTheme()));
        } else {
            if (!TextUtils.isEmpty(tv_result.getText())) {
//                tv_result.setText("");
            }
            img_mic.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_mic_24));

        }

    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i("Log Mic = ", "onPartialResults" + partialResults);
        ArrayList<String> matches = partialResults
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        StringBuilder text = new StringBuilder();
        for (String result : matches)
            text.append(result);
        tv_result.setText(text.toString());

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}