package com.example.zenword;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Iterator;

import DataStructures.BSTMapping;


public class PlayedWords implements Serializable
{
    private final TextView textView;

    /**
     * Constructor de la classe PlayedWords
     * @param mainActivity activitat principal de l'aplicació
     */
    public PlayedWords(MainActivity mainActivity)
    {
        textView = mainActivity.findViewById(R.id.textView1);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setTextSize((float) MainActivity.textSize/2);
    }


    public void display()
    {
        /*String s = MainActivity.w.getParaulesTorbades(true);
        textView.setText(Html.fromHtml(s));*/
    }

}
