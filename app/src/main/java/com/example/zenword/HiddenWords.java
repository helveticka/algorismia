package com.example.zenword;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;

import java.util.Iterator;

import UnsortedElements.UnsortedArrayMapping;


public class HiddenWords
{
    private final MainActivity mainActivity;
    private final Guideline[] guidelines;
    private TextView[][] wordsTextViews;

    public HiddenWords(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        guidelines = new Guideline[6];
        guidelines[0] = mainActivity.findViewById(R.id.guidelineHor1);
        guidelines[1] = mainActivity.findViewById(R.id.guidelineHor2);
        guidelines[2] = mainActivity.findViewById(R.id.guidelineHor3);
        guidelines[3] = mainActivity.findViewById(R.id.guidelineHor4);
        guidelines[4] = mainActivity.findViewById(R.id.guidelineHor5);
        guidelines[5] = mainActivity.findViewById(R.id.guidelineHor6);
    }


    public void createHiddenWords()
    {
        wordsTextViews = new TextView[MainActivity.w.getGuessingRows()][Words.maxWordsLength];
        Iterator it = MainActivity.w.paraulesOcultes.iterator();
        while (it.hasNext())
        {
            UnsortedArrayMapping.Pair pair = (UnsortedArrayMapping.Pair) it.next();
            int i = (int) pair.getValue();
            System.out.println(i);
            String s = (String) pair.getKey();
            System.out.println(s);
            wordsTextViews[i] = crearFilaTextViews(i, s.length()/2);
        }
    }


    public TextView[] crearFilaTextViews(int guia, int lletres)
    {
        TextView[] param = new TextView[lletres];
        ConstraintLayout constraint = mainActivity.findViewById(R.id.main);

        for (int i=0; i<lletres; i++)
        {
            int id = View.generateViewId();

            param[i] = new TextView (mainActivity);
            param[i].setId(id);
            param[i].setText("");
            param[i].setGravity(Gravity.CENTER);
            param[i].setTextSize(24);
            param[i].setTextColor(Color.parseColor("#FFFFFF"));
            param[i].setBackgroundResource(R.drawable.letter_box);
            //param[i].setBackgroundTintList(ColorStateList.valueOf(MainActivity.rColor));
            constraint.addView(param[i]);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraint);

            int wdth = (int) ((MainActivity.dpWidth-16)*MainActivity.density/7);
            int margin = (int) ((wdth*(7-lletres)+16*MainActivity.density)/2);

            if (i == 0)
            {
                constraintSet.connect(id, ConstraintSet.BOTTOM, guidelines[guia+1].getId(), ConstraintSet.TOP);
                constraintSet.connect(id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, margin);
                constraintSet.connect(id, ConstraintSet.TOP, guidelines[guia].getId(), ConstraintSet.BOTTOM);

                constraintSet.constrainHeight(id, ConstraintSet.MATCH_CONSTRAINT);
                constraintSet.constrainMaxHeight(id, (int) (MainActivity.dpHeight*0.07*MainActivity.density));
                constraintSet.constrainWidth(id, wdth);
                constraintSet.setDimensionRatio(id, "1:1");
            }
            else
            {
                constraintSet.connect(id, ConstraintSet.BOTTOM, guidelines[guia+1].getId(), ConstraintSet.TOP);
                constraintSet.connect(id, ConstraintSet.START, param[i-1].getId(), ConstraintSet.END, 0);
                constraintSet.connect(id, ConstraintSet.TOP, guidelines[guia].getId(), ConstraintSet.BOTTOM);

                constraintSet.constrainHeight(id, ConstraintSet.MATCH_CONSTRAINT);
                constraintSet.constrainMaxHeight(id, (int) (MainActivity.dpHeight*0.07*MainActivity.density));
                constraintSet.constrainWidth(id, wdth);
                constraintSet.setDimensionRatio(id, "1:1");
            }

            constraintSet.applyTo(constraint);
        }

        return param;
    }


    public void mostraParaula(String s, int posicio)
    {
        for (int i=0; i<s.length(); i++)
        {
            String c = String.valueOf(s.charAt(i)).toUpperCase();
            wordsTextViews[posicio][i].setText(c);
        }
    }


    public void mostraPrimeraLletra(String s, int posicio)
    {
        String c = String.valueOf(s.charAt(0)).toLowerCase();
        wordsTextViews[posicio][0].setText(c);
    }


    public void amaga()
    {
        for (TextView[] wordsTextView : wordsTextViews)
        {
            for (TextView aux : wordsTextView) aux.setVisibility(View.GONE);
        }
    }

}
