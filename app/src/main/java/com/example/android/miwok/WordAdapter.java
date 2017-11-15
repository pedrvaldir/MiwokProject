package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VALDIR on 12/11/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    public WordAdapter(@NonNull Context context, @NonNull ArrayList<Word> androidWord) {
        super(context, 0, androidWord);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //Obter o objeto {@link Word} localizado nesta posição na lista
        Word currentWord = getItem(position);

        // Encontre o TextView no layout list_item.xml com o ID version_name
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.defaul_text_view);

        //Obter o nome da versão do objeto AndroidFlavor atual e
        // configure este texto no nome TextView
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // Encontre o TextView no layout list_item.xml com o número de versão ID
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);

        miwokTextView.setText(currentWord.getMiworkTranslation());


        return listItemView;
    }
}
