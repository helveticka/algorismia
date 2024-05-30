package com.example.zenword;

import android.content.res.ColorStateList;
import android.widget.Button;

public class Buttons
{
    private final Button clear, send, mescla, bonus, ajuda, reinicia;
    private final Button[] buttons;

    /**
     * Constructor dels botons de l'aplicació. Els guarda dins l'array buttons
     * @param mainActivity activitat principal de l'aplicació
     */
    public Buttons(MainActivity mainActivity)
    {
        buttons = new Button[6];
        buttons[0] = clear = mainActivity.findViewById(R.id.buttonClear);
        buttons[1] = send = mainActivity.findViewById(R.id.buttonSend);
        buttons[2] = mescla = mainActivity.findViewById(R.id.imageMescla);
        buttons[3] = bonus = mainActivity.findViewById(R.id.imageBonus);
        buttons[4] = ajuda = mainActivity.findViewById(R.id.imageAjuda);
        buttons[5] = reinicia = mainActivity.findViewById(R.id.imageReinicia);
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
    public void updateColor(int color)
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
    public void enable()
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
    public void disable()
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