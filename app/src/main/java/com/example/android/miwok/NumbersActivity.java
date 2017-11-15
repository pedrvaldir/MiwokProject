package com.example.android.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        //CRIAR A LISTA DE PALAVRAS
        ArrayList<Word> words = new ArrayList<Word>();

        //words.add("one");
        words.add(new Word("One", "Lutti"));
        words.add(new Word("two", "otiiko"));
        words.add(new Word("three", "tolookosu"));
        words.add(new Word("four", "oyyisa"));
        words.add(new Word("five", "massokka"));
        words.add(new Word("six", "temmokka"));
        words.add(new Word("seven","kenekaku"));
        words.add(new Word("eight","kawinta"));
        words.add(new Word("nine","wo'e"));
        words.add(new Word("ten","na'aacha"));

        //Criando lista de arrayAdapter
        WordAdapter adapter  = new WordAdapter(this, words);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);


        //  ListView listView = (ListView) findViewById(R.id.list);

    /*
    introdu
        for (int index = 0; index <words.size();index++){
            //Criado view pai(rootView) que recebera a view filha WordView
            LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);
            TextView wordView = new TextView(this);

            //adicionar a palavar do indice para um textview
            wordView.setText(words.get(index));

            //Adicionar a filha(WordView ao pai (rootView)
            rootView.addView(wordView);
        }
   */
   }
}
