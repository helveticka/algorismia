package com.example.zenword;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.Iterator;
import java.util.Random;

import UnsortedElements.UnsortedArrayMapping;


/**
 *
 */
public class MainActivity extends AppCompatActivity
{
    public static float density, dpHeight, dpWidth;
    public static int rColor;
    public static Words w;
    public HiddenWords hiddenWords;
    public CircleButtons circleButtons;
    public PlayedWords playedWords;
    public int bonus, ajudes;


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

        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        density  = this.getResources().getDisplayMetrics().density;
        dpHeight = outMetrics.heightPixels / density;
        dpWidth  = outMetrics.widthPixels / density;

        w = new Words(this);
        hiddenWords = new HiddenWords(this);
        circleButtons = new CircleButtons(this);
        playedWords = new PlayedWords(this);
        bonus = 0;
        ajudes = 0;

        INNIT();
    }


    public void INNIT()
    {
        rColor = generarColorAleatorio();

        w.novaParaula();
        hiddenWords.createHiddenWords();
        circleButtons.createCircleButtons();
        playedWords.createPlayedWords();

        enableViews(R.id.main);
    }


    public void setLletra(View view)
    {
        Button btn = (Button) view;
        String lletra = String.valueOf(btn.getText());

        TextView txt = findViewById(R.id.textView2);
        txt.append(lletra);

        btn.setClickable(false);
        btn.setTextColor(rColor);
    }


    public void send(View view)
    {
        TextView txt2 = findViewById(R.id.textView2);
        String s = String.valueOf(txt2.getText());
        clear(null);

        if (w.esParaulaValida(s))
        {
            playedWords.append(s, true);

            int pos = w.esParauaOculta(s);
            if (pos < 0) return;

            hiddenWords.mostraParaula(s, pos);
            mostraMissatge("Added", true);

            bonus++;
            if (bonus%5 == 0)
            {
                bonus = 0;
                ajudes++;
            }

            if (w.getParaulesOcultes().isEmpty()) win();
        }
        else
        {
            playedWords.append(s, false);
            mostraMissatge("Not added", false);
        }
    }


    private void win()
    {
        disableViews(R.id.main);
        mostraMissatge("Enhorabona, has guanyat!", true);
    }


    public void reset(View view)
    {
        hiddenWords.amaga();
        playedWords.setValides(0);
        INNIT();
    }


    private int generarColorAleatorio()
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
        return Color.parseColor(colorHex);
    }


    public void clear(View view)
    {
        TextView txt = findViewById(R.id.textView2);
        txt.setText("");

        circleButtons.showButtons();
    }


    public void random(View view)
    {
        circleButtons.random();
    }


    public void consultarBonus(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Encertades (" + playedWords.getValides() + " de " + w.getNumParaulesValides() + "):");
        builder.setMessage(w.getParaulesTorbades());
        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void ajuda(View view)
    {
        //FALTARIA CANVIAR I MIRAR SI AQUELLA PARAULA NO TÉ JA PISTA
        if (ajudes == 0) return;

        Random ran = new Random();
        UnsortedArrayMapping<String, Integer> paraulesOcultes = w.getParaulesOcultes();
        Iterator it = paraulesOcultes.iterator();
        UnsortedArrayMapping.Pair pair = (UnsortedArrayMapping.Pair) it.next();
        String word = (String) pair.getKey();
        int ind = (Integer) pair.getValue();

        for (int i=2; it.hasNext(); i++)
        {
            if ((ran.nextInt(i) % i) == 0)
            {
                pair = (UnsortedArrayMapping.Pair) it.next();
                word = (String) pair.getKey();
                ind = (Integer) pair.getValue();
            }
            else
            {
                it.next();
            }
        }

        hiddenWords.mostraPrimeraLletra(word, ind);
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
        for (int i=0; i<count; i++)
        {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(true);
        }
    }


    private void disableViews(int parent)
    {
        ViewGroup viewGroup = findViewById(parent);
        int count = viewGroup.getChildCount();
        for (int i=0; i<count; i++)
        {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(false);
        }

        findViewById(R.id.imageBonus).setEnabled(true);
        findViewById(R.id.imageReinicia).setEnabled(true);
    }

}
