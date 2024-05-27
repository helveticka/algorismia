package com.example.zenword;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.Iterator;

import DataStructures.*;


public class HiddenWords
{
    private final MainActivity mainActivity;
    private final int[] guidelines;
    private TextView[][] wordsTextViews;


    public HiddenWords(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        guidelines = new int[6];

        if (MainActivity.isVertical)
        {
            guidelines[0] = mainActivity.findViewById(R.id.guidelineHor1).getId();
            guidelines[1] = mainActivity.findViewById(R.id.guidelineHor2).getId();
            guidelines[2] = mainActivity.findViewById(R.id.guidelineHor3).getId();
            guidelines[3] = mainActivity.findViewById(R.id.guidelineHor4).getId();
            guidelines[4] = mainActivity.findViewById(R.id.guidelineHor5).getId();
            guidelines[5] = mainActivity.findViewById(R.id.guidelineHor6).getId();
        }
        else
        {
            guidelines[0] = mainActivity.findViewById(R.id.guidelineHor1).getId();
            guidelines[1] = mainActivity.findViewById(R.id.guidelineHor2).getId();
            guidelines[2] = mainActivity.findViewById(R.id.guidelineHor3).getId();
            guidelines[3] = mainActivity.findViewById(R.id.guidelineHor4).getId();
            guidelines[4] = mainActivity.findViewById(R.id.guidelineHor5).getId();
            guidelines[5] = mainActivity.findViewById(R.id.guidelineHor6).getId();
        }
    }


    public void createHiddenWords()
    {
        wordsTextViews = new TextView[MainActivity.w.getGuessingRows()][Words.maxWordsLength];
        UnsortedArrayMapping<String, Integer> ajudes = MainActivity.w.getAjudes();

        Iterator it = MainActivity.w.getParaulesOcultes().iterator();
        while (it.hasNext())
        {
            UnsortedArrayMapping.Pair pair = (UnsortedArrayMapping.Pair) it.next();
            int i = (int) pair.getValue();
            String s = (String) pair.getKey();

            wordsTextViews[i] = crearFilaTextViews(i, s.length());
            ajudes.put(s, i);
        }

        it = MainActivity.w.getTrobades().iterator();
        while (it.hasNext())
        {
            UnsortedArrayMapping.Pair pair = (UnsortedArrayMapping.Pair) it.next();
            int i = (int) pair.getValue();
            String s = MainActivity.w.get((String) pair.getKey());

            wordsTextViews[i] = crearFilaTextViews(i, s.length());
            for (int j=0; j<wordsTextViews[i].length; j++)
            {
                wordsTextViews[i][j].setText(String.valueOf(s.charAt(j)).toUpperCase());
            }
        }
    }


    private TextView[] crearFilaTextViews(int guia, int lletres)
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
            param[i].setTextSize(MainActivity.textSize);
            param[i].setTextColor(Color.parseColor("#FFFFFF"));
            param[i].setBackgroundResource(R.drawable.letter_box);
            param[i].setBackgroundTintList(ColorStateList.valueOf(MainActivity.rColor));
            constraint.addView(param[i]);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraint);

            int wdth, startId, maxHeight;
            if (MainActivity.isVertical)
            {
                wdth = (int) ((MainActivity.dpWidth-28)*MainActivity.density/7);
                startId = ConstraintSet.PARENT_ID;
                maxHeight = (int) (MainActivity.dpHeight*0.07*MainActivity.density) -8;
                //constraintSet.constrainMaxHeight(id, (int) MainActivity.dpHeight/6);
            }
            else
            {
                wdth = (int) (((MainActivity.dpWidth-28)*MainActivity.density*0.4)/7);
                startId = mainActivity.findViewById(R.id.guidelineVer1).getId();
                maxHeight = (int) (MainActivity.dpHeight*0.2*MainActivity.density) -8;
                constraintSet.constrainMaxHeight(id, (int) (MainActivity.dpWidth/5));
            }

            int margin = (int) ((wdth*(7-lletres)+28*MainActivity.density)/2);


            constraintSet.connect(id, ConstraintSet.BOTTOM, guidelines[guia+1], ConstraintSet.TOP,4);
            constraintSet.connect(id, ConstraintSet.TOP, guidelines[guia], ConstraintSet.BOTTOM,4);

            constraintSet.constrainHeight(id, maxHeight);
            constraintSet.constrainWidth(id, wdth);

            if (i == 0)
            {
                constraintSet.connect(id, ConstraintSet.START, startId, ConstraintSet.START, margin);
            }
            else
            {
                constraintSet.connect(id, ConstraintSet.START, param[i-1].getId(), ConstraintSet.END, 2);
            }

            constraintSet.applyTo(constraint);
        }

        return param;
    }


    public void mostraParaula(String s, int posicio)
    {
        for (int i=0; i<wordsTextViews[posicio].length; i++)
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
