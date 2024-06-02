package com.example.zenword;

import android.content.res.ColorStateList;
import android.widget.Button;


public class Botons
{
    private final Button clear, send, mescla, bonus, ajuda, reinicia;


    /**
     * Constructor dels botons de l'aplicació.
     * @param mainActivity activitat principal de l'aplicació
     */
    public Botons(MainActivity mainActivity)
    {
        clear = mainActivity.findViewById(R.id.buttonClear);
        send = mainActivity.findViewById(R.id.buttonSend);
        mescla = mainActivity.findViewById(R.id.buttonMescla);
        bonus = mainActivity.findViewById(R.id.buttonBonus);
        ajuda = mainActivity.findViewById(R.id.buttonAjuda);
        reinicia = mainActivity.findViewById(R.id.buttonReinicia);
    }


    /**
     * Actualitza el tamany del text dels botons
     * @param textSize tamany del text
     */
    public void setTextSize(int textSize)
    {
        bonus.setTextSize((float) textSize/2);

        float size = (float) (textSize*0.8);
        clear.setTextSize(size);
        send.setTextSize(size);
    }


    /**
     * Canvia el color de fons de tots els botons
     * @param color color a utilitzar
     */
    public void setColor(int color)
    {
        clear.setTextColor(color);
        send.setTextColor(color);

        ColorStateList colorStateList = ColorStateList.valueOf(color);
        mescla.setBackgroundTintList(colorStateList);
        bonus.setBackgroundTintList(colorStateList);
        ajuda.setBackgroundTintList(colorStateList);
        reinicia.setBackgroundTintList(colorStateList);
    }


    /**
     * Habilita els botons de clear, send, mescla i ajuda
     */
    public void activa()
    {
        clear.setClickable(true);
        clear.setAlpha(1);

        send.setClickable(true);
        send.setAlpha(1);

        mescla.setClickable(true);
        mescla.setAlpha(1);

        ajuda.setClickable(true);
        ajuda.setAlpha(1);
    }


    /**
     * Deshabilita els botons de clear, send, mescla i ajuda
     */
    public void desactiva()
    {
        clear.setClickable(false);
        clear.setAlpha(0.5F);

        send.setClickable(false);
        send.setAlpha(0.5F);

        mescla.setClickable(false);
        mescla.setAlpha(0.5F);

        ajuda.setClickable(false);
        ajuda.setAlpha(0.5F);
    }


    /**
     * Actualitza el text del botó de bonus amb el nombre d'ajudes corresponent
     * @param numAjudes nombre d'ajudes
     */
    public void setNumAjudes(int numAjudes)
    {
        bonus.setText(String.valueOf(numAjudes));
    }
}
