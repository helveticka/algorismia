package com.example.zenword;

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
        AlertDialog. Builder builder = new AlertDialog . Builder ( this ) ;
        builder . setTitle ("Figureta del mes") ;
        builder . setMessage ("Rararararararararmon") ;
        // Un bot´o OK per tancar la finestra
        builder . setPositiveButton ("OK", null ) ;
        // Mostrar l’AlertDialog a la pantalla
        AlertDialog dialog = builder . create () ;
        dialog . show () ;
    }


    public TextView [] crearFilaTextViews (int guia , int lletres )
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

    public void exempleCrearFilaTextViews() { //funcio auxiliar si la necessitam
        crearFilaTextViews(R.id.guidelineHor1, 5); // Guia corresponent i 5 lletres
    }
}
