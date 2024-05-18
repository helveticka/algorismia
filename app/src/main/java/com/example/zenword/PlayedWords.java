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


    public PlayedWords(MainActivity mainActivity)
    {
        textView = mainActivity.findViewById(R.id.textView1);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setTextSize((float) MainActivity.textSize/2);
    }


    public void display()
    {
        /*int numParaulesValides = MainActivity.w.getNumParaulesValides();
        int enc = MainActivity.w.getNumParaulesEncertades();
        SpannableStringBuilder startText = new SpannableStringBuilder("Has encertat " + enc + " de " + numParaulesValides + " possibles: ");

        SpannableStringBuilder t = MainActivity.w.getParaulesTorbades(true);
        startText.append(t);
        textView.setText(startText, TextView.BufferType.SPANNABLE);*/

        String s = MainActivity.w.getParaulesTorbades(true);
        textView.setText(Html.fromHtml(s));
    }


    public String wow(boolean repetides)
    {
        StringBuilder s = new StringBuilder();
        if (repetides)
        {
            int numParaulesValides = MainActivity.w.getNumParaulesValides();
            int enc = MainActivity.w.getNumParaulesEncertades();
            s = new StringBuilder("Has encertat " + enc + " de " + numParaulesValides + " possibles: ");
        }

        Iterator it = MainActivity.w.getSolucions().iterator();
        while (it.hasNext())
        {
            BSTMapping.Pair pair = (BSTMapping.Pair) it.next();
            String str = (String) pair.getKey();

            if (repetides && ((Boolean) pair.getValue()))
            {
                s.append("<font color='red'>");
                s.append(str);
                s.append("</ font >");
            }
            else s.append(str);

            s.append(", ");
        }
        System.out.println(s);

        if (s.length() == 0) return s.toString();
        return s.substring(0, s.length()-2);
    }

}
