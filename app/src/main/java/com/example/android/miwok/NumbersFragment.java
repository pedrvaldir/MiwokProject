package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

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
            }
        }
    };

    /**
     * Este ouvinte é ativado quando o {@link MediaPlayer} foi concluído
     * tocando o arquivo de áudio.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Agora que o arquivo de som finalizou a reprodução, liberte os recursos do media player.
            releaseMediaPlayer();
        }
    };



    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        //Crie e configure o {@link AudioManager} para solicitar o foco de áudio
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //CRIAR A LISTA DE PALAVRAS
        final ArrayList<Word> words = new ArrayList<>();

        //words.add("one");
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

        //Criando lista de arrayAdapter
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        assert listView != null;
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Libere o media player se ele existe atualmente porque estamos prestes a
                // reproduzir um arquivo de som diferente
                releaseMediaPlayer();

                // Obter o {@link Word} objeto na posição determinada, o usuário clicou em
                Word word = words.get(position);

                //Solicite foco de áudio para reprodução
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use o fluxo de música.
                        AudioManager.STREAM_MUSIC,
                        // Solicite um foco permanente.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //agora temos um foco de áudio.

                    // Crie e configure o {@link MediaPlayer} para o recurso de áudio associado
                    // com a palavra atual
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());

                    // Inicie o arquivo de áudio
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });



        return rootView;
    }

    /**
     * Limpe o media player, liberando seus recursos.
     */
    private void releaseMediaPlayer() {
        // Se o reprodutor de mídia não for nulo, então poderá estar tocando um som.
        if (mMediaPlayer != null) {
            // Independentemente do estado atual do media player, divulgue seus recursos
            // porque já não precisamos disso.
            mMediaPlayer.release();

            // Defina o reprodutor de mídia de volta para nulo. Para o nosso código, decidimos que
            // configurar o player de mídia como nulo é uma maneira fácil de dizer que o media player
            // não está configurado para reproduzir um arquivo de áudio no momento.
            mMediaPlayer = null;

            // Independentemente de ter ou não sido concedido foco de áudio, abandone-o. Isso também
            // anula o AudioFocusChangeListener para que não obtenhamos mais chamadas de retorno.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // Quando a activity for parada, libere os recursos media player porque não estaremos
        // mais tocando mais sons.
        releaseMediaPlayer();

        // Defina o reprodutor de mídia de volta para nulo. Para o nosso código, decidimos que
        // configurar o player de mídia como nulo é uma maneira fácil de dizer que o media player
        // não está configurado para reproduzir um arquivo de áudio no momento.
        mMediaPlayer = null;

        // Independentemente de ter ou não sido concedido foco de áudio, abandone-o. Isso também
        // anula o AudioFocusChangeListener para que não obtenhamos mais chamadas de retorno.
        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }

}
