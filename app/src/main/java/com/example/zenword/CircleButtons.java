package com.example.zenword;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;


public class CircleButtons
{
    private final ImageView circle;
    private final Button[] circleButtons;
    private Button[] currentCircleButtons;

    /**
     * Constructor dels botons del cercle. Els guarda dins l'array circleButtons
     * @param mainActivity activitat principal de l'aplicació
     */
    public CircleButtons(MainActivity mainActivity)
    {
        circle = mainActivity.findViewById(R.id.cercle);

        circleButtons = new Button[7];
        circleButtons[0] = mainActivity.findViewById(R.id.circleButton1);
        circleButtons[1] = mainActivity.findViewById(R.id.circleButton2);
        circleButtons[2] = mainActivity.findViewById(R.id.circleButton3);
        circleButtons[3] = mainActivity.findViewById(R.id.circleButton4);
        circleButtons[4] = mainActivity.findViewById(R.id.circleButton5);
        circleButtons[5] = mainActivity.findViewById(R.id.circleButton6);
        circleButtons[6] = mainActivity.findViewById(R.id.circleButton7);
    }

    /**
     * Crea la disposició dels botons del cercle en funció de la longitud de
     * la paraula solució.
     */
    public void createCircleButtons()
    {
        String paraula = MainActivity.w.getParaulaTriada();
        int wordLength = paraula.length();

        //Cream un nou array de botons dins del cercle
        currentCircleButtons = new Button[wordLength];
        //Diferenciam els casos en funció de la longitud de la paraula
        switch (wordLength)
        {
            case 4:
                circleButtons[0].setVisibility(View.INVISIBLE);
                currentCircleButtons[0] = circleButtons[1];
                currentCircleButtons[1] = circleButtons[2];
                circleButtons[3].setVisibility(View.INVISIBLE);
                currentCircleButtons[2] = circleButtons[4];
                currentCircleButtons[3] = circleButtons[5];
                circleButtons[6].setVisibility(View.INVISIBLE);
                break;

            case 5:
                circleButtons[0].setVisibility(View.INVISIBLE);
                currentCircleButtons[0] = circleButtons[1];
                currentCircleButtons[1] = circleButtons[2];
                currentCircleButtons[2] = circleButtons[3];
                currentCircleButtons[3] = circleButtons[4];
                currentCircleButtons[4] = circleButtons[5];
                circleButtons[6].setVisibility(View.INVISIBLE);
                break;

            case 6:
                currentCircleButtons[0] = circleButtons[0];
                currentCircleButtons[1] = circleButtons[1];
                currentCircleButtons[2] = circleButtons[2];
                circleButtons[3].setVisibility(View.INVISIBLE);
                currentCircleButtons[3] = circleButtons[4];
                currentCircleButtons[4] = circleButtons[5];
                currentCircleButtons[5] = circleButtons[6];
                break;

            case 7:
                currentCircleButtons = circleButtons;
                break;

            default:
                break;
        }

        //Actualitzam les propietats de cada botó
        for (int i=0; i<wordLength; i++)
        {
            currentCircleButtons[i].setTextColor(Color.parseColor("#FFFFFF"));
            currentCircleButtons[i].setTextSize(MainActivity.textSize);
            currentCircleButtons[i].setText(String.valueOf(paraula.charAt(i)).toUpperCase());
            currentCircleButtons[i].setVisibility(View.VISIBLE);
        }

        circle.setBackgroundTintList(ColorStateList.valueOf(MainActivity.rColor));
        random();
    }

    /**
     * Implementa la funcionalitat del botó Random
     */
    public void random()
    {
        Random r = new Random();

        //Assignam una posició aleatoria nova a cada botó del cercle
        for (int i = currentCircleButtons.length-1; i >= 0; i--)
        {
            int j = r.nextInt(i+1);
            CharSequence text = currentCircleButtons[i].getText();
            boolean isClckable = currentCircleButtons[i].isClickable();
            ColorStateList textColors = currentCircleButtons[i].getTextColors();

            currentCircleButtons[i].setText(currentCircleButtons[j].getText());
            currentCircleButtons[i].setClickable(currentCircleButtons[j].isClickable());
            currentCircleButtons[i].setTextColor(currentCircleButtons[j].getTextColors());

            currentCircleButtons[j].setText(text);
            currentCircleButtons[j].setClickable(isClckable);
            currentCircleButtons[j].setTextColor(textColors);
        }
    }

    /**
     * Habilita els botons del cercle
     */
    public void enable()
    {
        for (Button btn : currentCircleButtons)
        {
            btn.setClickable(true);
            btn.setTextColor(Color.parseColor("#FFFFFFFF"));
        }
    }

    /**
     * Deshabilita els botons del cercle
     */
    public void disable()
    {
        for (Button btn : currentCircleButtons)
        {
            btn.setClickable(false);
            btn.setTextColor(Color.parseColor("#80FFFFFF"));
        }
    }

}
