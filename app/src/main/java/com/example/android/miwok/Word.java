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
    /**Id Recurso da imagem para a palavra*/
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Cria um novo objeto palavra
     * @param defaultTranslation é uma palavra em uma lingua que não está de origem miwok
     *
     * @param miworkTranslation é a palavra na lingua Miwok
     *
     * @param imageResourceId é o id do recurso de imagem para a tradução especifica
     */

    public Word(String miworkTranslation, String defaultTranslation, int imageResourceId) {
       mMiworkTranslation = miworkTranslation;
       mDefaultTranslation = defaultTranslation;
       mImageResourceId = imageResourceId;
    }

    public Word(String miworkTranslation, String defaultTranslation){
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

    /**
     * Retorna o ID da imagem da palavra
     */
    public int getImageResourceId() {return mImageResourceId;}

    public  boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
