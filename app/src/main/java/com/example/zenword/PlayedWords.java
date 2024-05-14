package com.example.zenword;

import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.io.Serializable;


public class PlayedWords implements Serializable
{
    private final TextView textView;


    public PlayedWords(MainActivity mainActivity)
    {
        textView = mainActivity.findViewById(R.id.textView1);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    // CANVIAR SA PUTA MERDA D'ES COLOR QUE JA HAVIA FET PQ NO PODIA EXPLICAR-HO SA SETMANA ANTERIOR
    public void display()
    {
        int numParaulesValides = MainActivity.w.getNumParaulesValides();
        int enc = MainActivity.w.getNumParaulesEncertades();
        SpannableStringBuilder startText = new SpannableStringBuilder("Has encertat " + enc + " de " + numParaulesValides + " possibles: ");

        SpannableStringBuilder t = MainActivity.w.getParaulesTorbades(true);
        startText.append(t);
        textView.setText(startText, TextView.BufferType.SPANNABLE);
    }

}
