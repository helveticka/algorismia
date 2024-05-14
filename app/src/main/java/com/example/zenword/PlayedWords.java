package com.example.zenword;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;


public class PlayedWords
{
    private final TextView textView;
    private SpannableStringBuilder text;
    private String separator;
    private int valides;
    private int validesLength;


    public PlayedWords(MainActivity mainActivity)
    {
        textView = mainActivity.findViewById(R.id.textView1);
        textView.setMovementMethod(new ScrollingMovementMethod());
        valides = 0;
        validesLength = 1;
    }


    public void createPlayedWords()
    {
        int numParaulesValides = MainActivity.w.getNumParaulesValides();
        separator = ": ";
        text = new SpannableStringBuilder("Has encertat " + valides + " de " + numParaulesValides + " possibles");
        textView.setText(text, TextView.BufferType.SPANNABLE);
    }


    public void append(String s, boolean b)
    {
        int start = text.length();
        text.append(separator).append(s);

        if (b)
        {
            String num = String.valueOf(++valides);
            text.replace(13,(13+validesLength), num);

            if (num.length() > validesLength) validesLength = num.length();
        }
        else
        {
            text.setSpan(new ForegroundColorSpan(Color.RED), start+1, text.length(), 0);
        }

        textView.setText(text, TextView.BufferType.SPANNABLE);
        separator = ", ";
    }


    public int getValides() {return valides;}

    public void setValides(int n) {
        valides = n;
    }
}
