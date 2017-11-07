package com.example.valdir.futebolamericano;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int scoreHome = 0, scoreGuest=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayForHome(int score){
        TextView scoreView = (TextView)findViewById(R.id.scoreHome);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForGuest(int score){
        TextView scoreView = (TextView)findViewById(R.id.scoreGuest);
        scoreView.setText(String.valueOf(score));
    }

    //Home TeamA
    public void addTdHome(View view){ //Adiciona 8 pontos(TouchDown)
        scoreHome += 8;
        displayForHome(scoreHome);
    }

    public void addEpHome(View view){//Adiciona 1 ponto (ExtraPoint)
        scoreHome += 1;
        displayForHome(scoreHome);
    }

    public void addFgHome(View view){ //Adiciona 3 pontos (FielGoal)
        scoreHome += 3;
        displayForHome(scoreHome);
    }

    public void addSafeHome(View view){ //Adiciona 2 pontos (Safety)
        scoreHome += 2;
        displayForHome(scoreHome);
    }


    //Guest TeamB
    public void addTdGuest(View view){ //Adiciona 8 pontos(TouchDown)
        scoreGuest += 8;
        displayForGuest(scoreGuest);
    }

    public void addEpGuest(View view){ //Adiciona 1 ponto (ExtraPoint)
        scoreGuest += 1;
        displayForGuest(scoreGuest);
    }

    public void addFgGuest(View view){ //Adiciona 3 pontos (Fiel Goal)
        scoreGuest += 3;
        displayForGuest(scoreGuest);
    }

    public void addSafeGuest(View view){ //Adiciona 2 pontos (Safety)
        scoreGuest += 2;
        displayForGuest(scoreGuest);
    }


    // reset pontuação
    public void resetScore(View view){
        scoreHome = 0;
        scoreGuest = 0;
        TextView scoreViewH = (TextView)findViewById(R.id.scoreHome);
        scoreViewH.setText(String.valueOf(scoreHome));

        TextView scoreViewG = (TextView)findViewById(R.id.scoreGuest);
        scoreViewG.setText(String.valueOf(scoreGuest));
    }

}
