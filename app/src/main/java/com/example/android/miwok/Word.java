package com.example.android.miwok;

/**
 * Created by VALDIR on 12/11/2017.
 * {@link Word}  representa uma palavra de vocabulário que o usuário quer aprender
 * contém uma tradução padrão e uma tradução de miwok por palavra
 */

public class Word {
    /**Tradução padrao para a palavra*/
    private String mMiworkTranslation;
    /**Tradução miwok para a palavra*/
    private String mDefaultTranslation;

    /**
     * Como não será nece
     */

    public Word(String miworkTranslation, String defaultTranslation) {
       mMiworkTranslation = miworkTranslation;
       mDefaultTranslation = defaultTranslation;
    }

    /**
     * Para o propósito do app ao criar esse objeto, não espera que as palavras em ingles ou em miwok mudem
     * Sendo assim, não téra metodos para alterar o estado do objeto
     */
    public String getMiworkTranslation() {
        return mMiworkTranslation;
    }

    /**
     * obtem a tradução em miwok da palavra
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }
}
