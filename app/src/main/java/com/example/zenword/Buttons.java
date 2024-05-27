package com.example.zenword;

import android.content.res.ColorStateList;
import android.widget.Button;

public class Buttons
{
    private final Button clear, send, mescla, bonus, ajuda, reinicia;
    private final Button[] buttons;

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


    public void setTextSize(int textSize)
    {
        bonus.setTextSize((float) textSize/2);

        float size = (float) (textSize*0.8);
        clear.setTextSize(size);
        send.setTextSize(size);
    }


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


    public void disable()
    {
        /*for (Button btn : buttons)
        {
            if (btn.equals(bonus) || btn.equals(reinicia)) continue;

            btn.setClickable(false);
            btn.setAlpha(0.5F);
        }*/

        clear.setClickable(false);
        clear.setAlpha(0.5F);

        send.setClickable(false);
        send.setAlpha(0.5F);

        mescla.setClickable(false);
        mescla.setAlpha(0.5F);

        ajuda.setClickable(false);
        ajuda.setAlpha(0.5F);
    }


    public void setNumAjudes(int numAjudes)
    {
        bonus.setText(String.valueOf(numAjudes));
    }

}
