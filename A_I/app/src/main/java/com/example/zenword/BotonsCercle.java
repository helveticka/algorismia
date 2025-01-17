package com.example.zenword;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;


public class BotonsCercle
{
    private final ImageView cercle;
    private final Button[] botonsCercle;
    private Button[] botonsCercleActuals;


    /**
     * Constructor dels botons del cercle. Els guarda dins l'array BotonsCercle
     * @param mainActivity activitat principal de l'aplicació
     */
    public BotonsCercle(MainActivity mainActivity)
    {
        cercle = mainActivity.findViewById(R.id.imageViewCercle);

        botonsCercle = new Button[7];
        botonsCercle[0] = mainActivity.findViewById(R.id.cercleButton1);
        botonsCercle[1] = mainActivity.findViewById(R.id.cercleButton2);
        botonsCercle[2] = mainActivity.findViewById(R.id.cercleButton3);
        botonsCercle[3] = mainActivity.findViewById(R.id.cercleButton4);
        botonsCercle[4] = mainActivity.findViewById(R.id.cercleButton5);
        botonsCercle[5] = mainActivity.findViewById(R.id.cercleButton6);
        botonsCercle[6] = mainActivity.findViewById(R.id.cercleButton7);
    }


    /**
     * Crea la disposició dels botons del cercle en funció de la longitud de
     * la paraula solució.
     */
    public void crear(String paraulaTriada)
    {
        int wordLength = paraulaTriada.length();

        //Cream un nou array de botons dins del cercle
        botonsCercleActuals = new Button[wordLength];

        //Diferenciam els casos en funció de la longitud de la paraula
        switch (wordLength)
        {
            case 4:
                botonsCercle[0].setVisibility(View.INVISIBLE);
                botonsCercleActuals[0] = botonsCercle[1];
                botonsCercleActuals[1] = botonsCercle[2];
                botonsCercle[3].setVisibility(View.INVISIBLE);
                botonsCercleActuals[2] = botonsCercle[4];
                botonsCercleActuals[3] = botonsCercle[5];
                botonsCercle[6].setVisibility(View.INVISIBLE);
                break;

            case 5:
                botonsCercle[0].setVisibility(View.INVISIBLE);
                botonsCercleActuals[0] = botonsCercle[1];
                botonsCercleActuals[1] = botonsCercle[2];
                botonsCercleActuals[2] = botonsCercle[3];
                botonsCercleActuals[3] = botonsCercle[4];
                botonsCercleActuals[4] = botonsCercle[5];
                botonsCercle[6].setVisibility(View.INVISIBLE);
                break;

            case 6:
                botonsCercleActuals[0] = botonsCercle[0];
                botonsCercleActuals[1] = botonsCercle[1];
                botonsCercleActuals[2] = botonsCercle[2];
                botonsCercle[3].setVisibility(View.INVISIBLE);
                botonsCercleActuals[3] = botonsCercle[4];
                botonsCercleActuals[4] = botonsCercle[5];
                botonsCercleActuals[5] = botonsCercle[6];
                break;

            case 7:
                botonsCercleActuals = botonsCercle;
                break;

            default:
                break;
        }

        //Actualitzam les propietats de cada botó
        for (int i=0; i<wordLength; i++)
        {
            botonsCercleActuals[i].setTextColor(Color.parseColor("#FFFFFF"));
            botonsCercleActuals[i].setTextSize(MainActivity.textSize);
            botonsCercleActuals[i].setText(String.valueOf(paraulaTriada.charAt(i)).toUpperCase());
            botonsCercleActuals[i].setVisibility(View.VISIBLE);
        }

        cercle.setBackgroundTintList(ColorStateList.valueOf(MainActivity.color));
        random();
    }


    /**
     * Implementa la funcionalitat del botó Random
     */
    public void random()
    {
        Random ran = new Random();

        //Assignam una posició aleatoria nova a cada botó del cercle
        for (int i = botonsCercleActuals.length-1; i >= 0; i--)
        {
            int j = ran.nextInt(i+1);
            CharSequence text = botonsCercleActuals[i].getText();
            boolean isClckable = botonsCercleActuals[i].isClickable();
            ColorStateList textColors = botonsCercleActuals[i].getTextColors();

            botonsCercleActuals[i].setText(botonsCercleActuals[j].getText());
            botonsCercleActuals[i].setClickable(botonsCercleActuals[j].isClickable());
            botonsCercleActuals[i].setTextColor(botonsCercleActuals[j].getTextColors());

            botonsCercleActuals[j].setText(text);
            botonsCercleActuals[j].setClickable(isClckable);
            botonsCercleActuals[j].setTextColor(textColors);
        }
    }


    /**
     * Habilita els botons del cercle
     */
    public void activa()
    {
        for (Button btn : botonsCercleActuals)
        {
            btn.setClickable(true);
            btn.setTextColor(Color.parseColor("#FFFFFFFF"));
        }
    }


    /**
     * Deshabilita els botons del cercle
     */
    public void desactiva()
    {
        for (Button btn : botonsCercleActuals)
        {
            btn.setClickable(false);
            btn.setTextColor(Color.parseColor("#80FFFFFF"));
        }
    }

}
