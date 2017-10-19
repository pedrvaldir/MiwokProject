package com.example.android.miwok;

import android.view.View;
import android.widget.Toast;

/**
 * Created by VALDIR on 19/10/2017.
 */

public class NumbersClickListeners implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Abriu a Lista de Numeros", Toast.LENGTH_SHORT).show();
    }
}
