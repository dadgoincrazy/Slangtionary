package com.trucs.slangtionary;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 *  THIS CLASS TAKES HEAVY SAMPLES FROM https://www.developer.com/ws/android/programming/adding-basic-android-text-to-speech-to-your-apps.html
 *  Credit to this person for helping me figure out text-to-speach
 */

public class ViewWordActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextToSpeech mTTS = null;
    private final int ACT_CHECK_TTS_DATA = 1000;
    private WordViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_word);

        mModel = ViewModelProviders.of(this).get(WordViewModel.class);

        final TextView wordDesc = findViewById(R.id.wordDescription);
        final TextView wordTitle = findViewById(R.id.wordTitle);

        Intent intent = getIntent();
        Long id = intent.getLongExtra("wordId", 0);
        if(id > 0)
        {
            Word word = mModel.getWord(id);
            wordTitle.setText(word.getWord());
            wordDesc.setText(word.getDescription());
        }

        // Have buttonOnClick read description
        final Button bsay = findViewById(R.id.bsay);
        bsay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saySomething(wordTitle.getText().toString().trim() + " - " + wordDesc.getText().toString().trim(), 1);
            }
        });

        // Check to see if we have TTS voice data
        Intent ttsIntent = new Intent();
        ttsIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(ttsIntent, ACT_CHECK_TTS_DATA);
    }

    /**
     * Goes back to the MainActivity, which is the list of words
     * @param v
     */
    public void goBack(View v)
    {
        Intent intent = new Intent(ViewWordActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // Speaks the text out loud
    private void saySomething(String text, int qmode) {
        if (qmode == 1)
            mTTS.speak(text, TextToSpeech.QUEUE_ADD, null, null);
        else
            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    /**
     * Checks if we can initialize TextToSpeech Engine, if we can't we download the files so we can
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == ACT_CHECK_TTS_DATA) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // Data exists, so we instantiate the TTS engine
                mTTS = new TextToSpeech(this, this);
            } else {
                // Data is missing, so we start the TTS
                // installation process
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    /**
     * Checks and tells us if TTS initialization was successful or not
     * @param status
     */
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            if (mTTS != null) {
                // Might be able to set this differently based on the language of the word
                int result = mTTS.setLanguage(Locale.CANADA);
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this, "TTS language is not supported", Toast.LENGTH_LONG).show();
                } else {
//                    saySomething("TTS is ready", 0);
                    Toast.makeText(this, "TTS is ready", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "TTS initialization failed", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * on destroy we need to shut down TTS so it doesn't lock up resources
     */
    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}