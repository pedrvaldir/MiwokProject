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
public class PhrasesFragment extends Fragment {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
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


    public PhrasesFragment() {
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
        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        //Criando lista de arrayAdapter
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_phrases);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        assert listView != null;
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                                                // Libere o media player se ele existe atualmente porque estamos prestes a
                                                // reproduzir um arquivo de som diferente
                                                releaseMediaPlayer();

                                                //Obter o {@link Word} objeto na posição determinada, o usuário clicou no item especifico
                                                Word word = words.get(position);

                                                // Solicite foco de áudio para reprodução
                                                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                                                        // Use the music stream.
                                                        AudioManager.STREAM_MUSIC,

                                                        // Request permanent focus.
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
                                        }
        );

        return rootView;
    }

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

        // Quando a atividade for parada, libere os recursos do player de mídia porque não iremos
        // estar jogando mais sons.
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
