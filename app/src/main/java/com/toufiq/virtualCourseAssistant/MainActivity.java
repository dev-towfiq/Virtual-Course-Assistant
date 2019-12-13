package com.toufiq.virtualCourseAssistant;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private SpeechRecognizer speechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeTextToSpeech();
        initializeSpeechRecognizer();
    }

    public void fab(View view) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 20);
            speechRecognizer.startListening(intent);
    }

    private void initializeSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            speechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    List<String> result_arr = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    processResult(result_arr.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT >= 21){
            tts.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        } else {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH,null);
        }
    }
//        Command 1: who are you?
//        Command 2: my faculty info
//        Command 3: call my faculty
//        Command 4: email my faculty
//        Command 5: what is software?
//        Command 6: what is software engineering?
//        Command 7: open lecture 1
//        Command 8: our team info

    private void processResult(String command) {
        command = command.toLowerCase();
        if (command.contains("who")){
            if(command.contains("you")){
                speak(getString(R.string.who_are_your));
            }
        }
        if (command.contains("what")) {
            if (command.contains("your name")) {
                speak(getString(R.string.what_is_your_name));
            }
            if (command.contains("software")) {
                if (command.contains("engineering")) {
                    speak(getString(R.string.software_engineering));
                } else {
                    speak(getString(R.string.software));
                }
            }
        }
        if (command.contains("open lecture 1")) {
            Intent intent = new Intent(this, ActivityShowLecture.class);
            startActivity(intent);
        }
        if (command.contains("our team info")) {
            Intent intent = new Intent(this, ActivityAboutTeam.class);
            startActivity(intent);
        }
        if (command.contains("my faculty info")) {
            Intent intent = new Intent(this, ActivityAboutFaculty.class);
            startActivity(intent);
        }
        if (command.contains("call my faculty")) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:001087654321"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);
        }
        if (command.contains("email my faculty")){
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:khan.habibullah@northsouth.edu"));
            startActivity(intent);
        }
    }
    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                    tts.setLanguage(Locale.US);
                    speak(getString(R.string.who_is_Lalita));
            }
        });
    }
    //activity lifecycle
    @Override
    protected void onPause() {
        super.onPause();
        tts.shutdown();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Reinitialize the recognizer upon resuming from background
        initializeSpeechRecognizer();
    }
}