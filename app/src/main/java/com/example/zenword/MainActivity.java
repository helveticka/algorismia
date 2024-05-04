package com.example.zenword;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.Iterator;
import java.util.Random;
import java.util.jar.JarOutputStream;

import UnsortedElements.UnsortedArrayMapping;
import UnsortedElements.UnsortedLinkedListSet;


/**
 *
 */
public class MainActivity extends AppCompatActivity
{
    public Button[] circleButtons, currentCircleButtons;
    public Guideline[] guies;
    public DisplayMetrics outMetrics;
    public static float density, dpHeight, dpWidth;
    public TextView[][] wordsTextViews;
    public String rColor;
    public  Words w;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        w = new Words(this);
        INNIT();
    }


    public void INNIT()
    {
        Display display = this.getWindowManager().getDefaultDisplay();
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        density  = this.getResources().getDisplayMetrics().density;
        dpHeight = outMetrics.heightPixels / density;
        dpWidth  = outMetrics.widthPixels / density;
        System.out.println(dpHeight);
        System.out.println(dpWidth);

        circleButtons = new Button[7];
        circleButtons[0] = findViewById(R.id.circleButton1);
        circleButtons[1] = findViewById(R.id.circleButton2);
        circleButtons[2] = findViewById(R.id.circleButton3);
        circleButtons[3] = findViewById(R.id.circleButton4);
        circleButtons[4] = findViewById(R.id.circleButton5);
        circleButtons[5] = findViewById(R.id.circleButton6);
        circleButtons[6] = findViewById(R.id.circleButton7);

        guies = new Guideline[6];
        guies[0] = findViewById(R.id.guidelineHor1);
        guies[1] = findViewById(R.id.guidelineHor2);
        guies[2] = findViewById(R.id.guidelineHor3);
        guies[3] = findViewById(R.id.guidelineHor4);
        guies[4] = findViewById(R.id.guidelineHor5);
        guies[5] = findViewById(R.id.guidelineHor6);

        //
        w.novaParaula();
        String paraula = w.getParaulaTriada().split(";")[1];
        System.out.println(paraula);
        int wordLength = w.getWordLength();
        currentCircleButtons = new Button[wordLength];
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

        //
        for (int i=0; i<wordLength; i++)
        {
            currentCircleButtons[i].setText(String.valueOf(paraula.charAt(i)).toUpperCase());
            currentCircleButtons[i].setVisibility(View.VISIBLE);
        }
        random(null);

        //
        wordsTextViews = new TextView[w.getGuessingRows()][Words.maxWordsLength];
        Iterator it = w.paraulesOcultes.iterator();
        while (it.hasNext())
        {
            UnsortedArrayMapping.Pair pair = (UnsortedArrayMapping.Pair) it.next();
            int i = (int) pair.getValue();
            System.out.println(i);
            String s = (String) pair.getKey();
            System.out.println(s);
            wordsTextViews[i] = crearFilaTextViews(i, s.length()/2);
        }

        //cambiam el color del cercle
        ImageView imageView = findViewById(R.id.cercle);
        rColor = generarColorAleatorio();
        imageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(rColor)));

        TextView textView = findViewById(R.id.textView1);
        textView.setText("Has encertat 0 de " + w.getNumParaulesValides() + " possibles: ");
        textView.setMovementMethod(new ScrollingMovementMethod());
    }


    public void setLletra(View view)
    {
        Button btn = (Button) view;
        String lletra = btn.getText().toString();

        TextView txt = findViewById(R.id.textView2);
        txt.append(lletra);

        btn.setClickable(false);
        btn.setTextColor(Color.parseColor(rColor));
    }


    public void send(View view)
    {
        TextView txt2 = findViewById(R.id.textView2);
        String s = String.valueOf(txt2.getText());
        txt2.setText("");

        TextView txt1 = findViewById(R.id.textView1);

        if (w.esParaulaValida(s))
        {
            txt1.append(s);

            int pos = w.esParauaOculta(s);
            if (pos < 0) return;

            mostraParaula(s, pos);
        }
        else
        {
            String aux = String.valueOf(txt1.getText()).concat(s);
            SpannableString text = new SpannableString(aux);
            //text.

            txt1.setText(text, TextView.BufferType.SPANNABLE);
        }
    }


    public void reset(View view)
    {
        rColor = generarColorAleatorio();
        for (TextView[] wordsTextView : wordsTextViews)
        {
            for (TextView aux : wordsTextView) aux.setVisibility(View.GONE);
        }

        clear(null);

        for (Button btn : currentCircleButtons)
        {
            btn.setClickable(true);
            btn.setTextColor(Color.parseColor("#FFFFFFFF"));
        }
        INNIT();
    }


    private String generarColorAleatorio()
    {
        Random rnd = new Random();
        // Generar valores aleatorios para el componente de rojo, verde, azul y alfa
        int red = rnd.nextInt(256);
        int green = rnd.nextInt(256);
        int blue = rnd.nextInt(256);
        //transparencia
        int alpha = 200;

        // Formatear el color en formato hexadecimal
        String colorHex = String.format("#%02X%02X%02X%02X", alpha, red, green, blue);

        return colorHex;
    }


    public void clear(View view)
    {
        TextView txt = findViewById(R.id.textView2);
        txt.setText("");

        for (Button btn : currentCircleButtons)
        {
            btn.setClickable(true);
            btn.setTextColor(Color.parseColor("#FFFFFFFF"));
        }
    }


    public void random(View view)
    {
        Random r = new Random();

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


    public void bonus(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Figureta del mes");
        builder.setMessage("Rararararararararmon");

        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public TextView[] crearFilaTextViews(int guia, int lletres)
    {
        TextView[] param = new TextView[lletres];
        ConstraintLayout constraint = findViewById(R.id.main);

        for (int i=0; i<lletres; i++)
        {
            int id = View.generateViewId();

            param[i] = new TextView (this);
            param[i].setId(id);
            param[i].setText("");
            param[i].setGravity(Gravity.CENTER);
            param[i].setTextSize(24);
            param[i].setTextColor(Color.parseColor("#FFFFFF"));
            param[i].setBackgroundResource(R.drawable.letter_box);
            //param[i].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(rColor)));
            constraint.addView(param[i]);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraint);

            //int wdth = outMetrics.widthPixels/7;
            int wdth = (int) ((dpWidth-16)*density/7);
            int margin = (int) ((wdth*(7-lletres)+16*density)/2);

            if (i == 0)
            {
                //constraintSet.connect(id, ConstraintSet.BOTTOM, guia+1, ConstraintSet.TOP);
                constraintSet.connect(id, ConstraintSet.BOTTOM, guies[guia+1].getId(), ConstraintSet.TOP);
                constraintSet.connect(id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, margin);
                //constraintSet.connect(id, ConstraintSet.TOP, guia, ConstraintSet.BOTTOM);
                constraintSet.connect(id, ConstraintSet.TOP, guies[guia].getId(), ConstraintSet.BOTTOM);

                constraintSet.constrainHeight(id, ConstraintSet.MATCH_CONSTRAINT);
                constraintSet.constrainWidth(id, wdth);
                constraintSet.setDimensionRatio(id, "1:1");
            }
            else
            {
                //constraintSet.connect(id, ConstraintSet.BOTTOM, guia+1, ConstraintSet.TOP);
                constraintSet.connect(id, ConstraintSet.BOTTOM, guies[guia+1].getId(), ConstraintSet.TOP);
                constraintSet.connect(id, ConstraintSet.START, param[i-1].getId(), ConstraintSet.END, 0);
                //constraintSet.connect(id, ConstraintSet.TOP, guia, ConstraintSet.BOTTOM);
                constraintSet.connect(id, ConstraintSet.TOP, guies[guia].getId(), ConstraintSet.BOTTOM);

                constraintSet.constrainHeight(id, ConstraintSet.MATCH_CONSTRAINT);
                constraintSet.constrainWidth(id, wdth);
                constraintSet.setDimensionRatio(id, "1:1");
            }

            constraintSet.applyTo(constraint);
        }

        return param;
    }


    public static boolean esParaulaSolucio(String paraula1, String paraula2)
    {
        UnsortedArrayMapping<Character, Integer> repetitions = new UnsortedArrayMapping<>(paraula1.length());
        char[] arrAux;

        //començam ficant la primera paraula a un hashmap lletra per lletra
        //on la clau es la lletra i el valor el numero de aparicions
        arrAux = paraula1.toCharArray();
        //repetitions.put(arrAux[0], 1);
        int number;
        for (int i = 0; i < arrAux.length; i++) {
            //si la lletra no está al hashmap es fica amb valor 1
            if (repetitions.get(arrAux[i]) == null) {
                repetitions.put(arrAux[i], 1);
            }
            //si la lletra ja era al hashmap es fica amb valor anterior+1
            else
            {
                number = repetitions.get(arrAux[i]);
                repetitions.put(arrAux[i], number+1);
            }
        }

        //ara passem a la paraula 2 i anem restant aparicions/eliminant elements un per un segons
        //les lletres de la segona paraula, si acabam amb la segona paraula i el hashmap es buid,
        //la paraula2 es solucio, si es buid abans d'acabar amb la segona paraula no és solució i si
        //acabam amb la paraula i no es buid tampoc és solució
        arrAux = paraula2.toCharArray();
        for (int i = 0; i < arrAux.length+1; i++)
        {
            if (repetitions.isEmpty() && i == arrAux.length){
                return true;
            } else if (repetitions.isEmpty() && i < arrAux.length) {
                return false;
            } else if (!repetitions.isEmpty() && i == arrAux.length){
                return true;
            }

            if (repetitions.get(arrAux[i]) == null)
            {
                return false;
            }
            else
            {
                number = repetitions.get(arrAux[i]);
                if (number > 1) {
                    repetitions.put(arrAux[i], number-1);
                }else{
                    repetitions.remove(arrAux[i]);
                }
            }
        }
        return false;
    }


    private void mostraParaula(String s, int posicio)
    {
        for (int i=0; i<s.length(); i++)
        {
            String c = String.valueOf(s.charAt(i)).toUpperCase();
            wordsTextViews[posicio][i].setText(c);
        }
    }


    private void mostraPrimeraLletra(String s, int posicio)
    {
        String c = String.valueOf(s.charAt(0)).toLowerCase();
        wordsTextViews[posicio][0].setText(c);
    }


    private void mostraMissatge(String s, boolean llarg)
    {
        Context context = getApplicationContext();
        int duration;

        if (llarg) duration = Toast.LENGTH_LONG;
        else duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }


    private void enableViews(int parent)
    {
        ViewGroup viewGroup = findViewById(parent);
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++)
        {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(true);
        }
    }


    private void disableViews(int parent)
    {
        ViewGroup viewGroup = findViewById(parent);
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++)
        {
            View view = viewGroup.getChildAt(i);
            if ((view.getId() != R.id.imageBonus) && (view.getId() != R.id.imageReinicia))
            {
                view.setEnabled(false);
            }
        }
    }

}
