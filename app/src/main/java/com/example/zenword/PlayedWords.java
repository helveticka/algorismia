package com.example.zenword;


import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class PlayedWords
{
    private final TextView textView;
    private SpannableString text;


    public PlayedWords(MainActivity mainActivity)
    {
        textView = mainActivity.findViewById(R.id.textView1);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }


    public void createPlayedWords()
    {
        int numParaulesValides = MainActivity.w.getNumParaulesValides();
        text = new SpannableString("Has encertat 0 de " + numParaulesValides + " possibles: ");
        textView.setText(text, TextView.BufferType.SPANNABLE);
    }


    public void append(String s, boolean b)
    {
        text = new SpannableString(s);
        textView.setText(text, TextView.BufferType.SPANNABLE);
    }

}
