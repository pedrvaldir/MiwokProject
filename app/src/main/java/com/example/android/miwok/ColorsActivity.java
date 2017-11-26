package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                // O caso AUDIOFOCUS_LOSS_TRANSIENT significa que perdemos foco de áudio por um
                // curto período de tempo. O caso AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK significa que
                // nosso aplicativo pode continuar a tocar som, mas em um volume menor. Trataremos
                // ambos os casos da mesma maneira porque nosso aplicativo está reproduzindo pequenos arquivos de som.
                // Pausar a reprodução e reiniciar o jogador no início do arquivo. Dessa forma, podemos
                // toque a palavra desde o início quando retomamos a reprodução.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // O caso AUDIOFOCUS_GAIN significa que recuperamos o foco e podemos retomar a reprodução.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // O caso AUDIOFOCUS_LOSS significa que perdemos foco de áudio e
                // Parar reprodução e limpeza de recursos
                releaseMediaPlayer();
                // Stop playback
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //CRIAR A LISTA DE PALAVRAS
        final ArrayList<Word> words = new ArrayList<>();

        //words.add("one");
        words.add(new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow,
                R.raw.color_mustard_yellow));
        words.add(new Word("dusty yellow", "ṭopiisә",
                R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));

        //Criando lista de arrayAdapter
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.list);

        assert listView != null;
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                releaseMediaPlayer();
                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //we have a audio focus now.

                    Word word = words.get(position);

                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());

                    mMediaPlayer.start();
                }

            }
        });

    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();

            mMediaPlayer = null;

            // Independentemente de ter ou não sido concedido foco de áudio, abandone-o. Isso também
            // anula o AudioFocusChangeListener para que não obtenhamos mais chamadas de retorno.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }
    }

    @Override
    protected void onStop() {

        super.onStop();

        // Quando a atividade for parada, libere os recursos do player de mídia porque não iremos
        // estar jogando mais sons.
        releaseMediaPlayer();


    }


}
