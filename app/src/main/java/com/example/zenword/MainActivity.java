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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import java.util.Random;

import UnsortedElements.UnsortedArrayMapping;


/**
 *
 */
public class MainActivity extends AppCompatActivity
{
    public Button[] circleButtons = new Button[7];


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
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

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;
        System.out.println(dpHeight);
        System.out.println(dpWidth);

        TextView textView = findViewById(R.id.textView1);
        textView.setMovementMethod(new ScrollingMovementMethod());

        circleButtons[0] = findViewById(R.id.circleButton1);
        circleButtons[1] = findViewById(R.id.circleButton2);
        circleButtons[2] = findViewById(R.id.circleButton3);
        circleButtons[3] = findViewById(R.id.circleButton4);
        circleButtons[4] = findViewById(R.id.circleButton5);
        circleButtons[5] = findViewById(R.id.circleButton6);
        circleButtons[6] = findViewById(R.id.circleButton7);
    }

    public void setLletra(View view)
    {
        Button btn = (Button) view ;
        String lletra = btn.getText().toString();

        TextView txt = findViewById(R.id.textView2);
        txt.append(lletra);

        btn.setClickable(false);
        btn.setTextColor(Color.parseColor("#80FFFFFF"));
    }

    public void clear(View view)
    {
        TextView txt = findViewById(R.id.textView2);
        txt.setText("");

        for (Button btn : circleButtons)
        {
            btn.setClickable(true);
            btn.setTextColor(Color.parseColor("#FFFFFFFF"));
        }
    }

    public void random(View view)
    {
                Random r = new Random();

                /* Moure a un INNIT
                circleButtons[0] = findViewById(R.id.circleButton1);
                circleButtons[1] = findViewById(R.id.circleButton2);
                circleButtons[2] = findViewById(R.id.circleButton3);
                circleButtons[3] = findViewById(R.id.circleButton4);
                circleButtons[4] = findViewById(R.id.circleButton5);
                circleButtons[5] = findViewById(R.id.circleButton6);
                circleButtons[6] = findViewById(R.id.circleButton7);
                */

                for (int i = circleButtons.length-1; i >= 0; i--)
                {
                    int j = r.nextInt(i);
                    CharSequence text = circleButtons[i].getText();
                    boolean isClckable = circleButtons[i].isClickable();
                    ColorStateList textColors = circleButtons[i].getTextColors();

                    circleButtons[i].setText(circleButtons[j].getText());
                    circleButtons[i].setClickable(circleButtons[j].isClickable());
                    circleButtons[i].setTextColor(circleButtons[j].getTextColors());

                    circleButtons[j].setText(text);
                    circleButtons[j].setClickable(isClckable);
                    circleButtons[j].setTextColor(textColors);
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

    public TextView [] crearFilaTextViews(int guia, int lletres)
    {
        TextView[] param = new TextView[lletres];
        ConstraintLayout constraint = findViewById(R.id.main);
        for (int i = 0; i < lletres; i++)
        {
            param[i] = new TextView ( this ) ;
            param[i].setId(View.generateViewId());
            param[i].setText("");
            constraint.addView(param[i]);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.connect(param[i].getId(), ConstraintSet.START, param[i - 1].getId(), ConstraintSet.END, 0);
            constraintSet.applyTo(constraint);
        }

        return param;
    }
    
    public static boolean esParaulaSolucio(String paraula1, String paraula2)
    {
        UnsortedArrayMapping<Character, Integer> repetitions = new UnsortedArrayMapping<Character, Integer>(paraula1.length());
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
            else {
                number = repetitions.get(arrAux[i]);
                repetitions.put(arrAux[i], number+1);
            }
        }
        //ara passem a la paraula 2 i anem restant aparicions/eliminant elements un per un segons
        //les lletres de la segona paraula, si acabam amb la segona paraula i el hashmap es buid,
        //la paraula2 es solucio, si es buid abans d'acabar amb la segona paraula no és solució i si
        //acabam amb la paraula i no es buid tampoc és solució
        arrAux = paraula2.toCharArray();
        for (int i = 0; i < arrAux.length+1; i++){
            if (repetitions.isEmpty() && i == arrAux.length){
                return true;
            } else if (repetitions.isEmpty() && i < arrAux.length) {
                return false;
            } else if (!repetitions.isEmpty() && i == arrAux.length){
                return true;
            }
            if (repetitions.get(arrAux[i]) == null){
                return false ;
            } else {
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

    }

    private void disableViews(int parent)
    {

    }
}
