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

import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.Random;

import DataStructures.*;


public class MainActivity extends AppCompatActivity
{
    public static float density, dpHeight, dpWidth;
    public static int color, textSize, nEncertades, nAjudes;
    public static boolean isVertical;

    private Paraules paraules;
    private ParaulesOcultes paraulesOcultes;
    private BotonsCercle botonsCercle;
    private Botons botons;
    private TextView textViewSolucions, textViewParaula;


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

        botons = new Botons(this);
        botonsCercle = new BotonsCercle(this);
        paraulesOcultes = new ParaulesOcultes(this);
        textViewSolucions = findViewById(R.id.textViewSolucions);
        textViewParaula = findViewById(R.id.textViewParaula);

        if (savedInstanceState == null)
        {
            generarColorAleatori();

            paraules = new Paraules(this);
            paraules.novaParaula();
            nEncertades = nAjudes = 0;
        }
        else
        {
            paraules = (Paraules) savedInstanceState.getSerializable("paraules");
            nEncertades = savedInstanceState.getInt("nEncertades");
            nAjudes = savedInstanceState.getInt("nAjudes");
        }

        INNIT();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putSerializable("paraules", paraules);
        savedInstanceState.putInt("nEncertades", nEncertades);
        savedInstanceState.putInt("nAjudes", nAjudes);
        super.onSaveInstanceState(savedInstanceState);
    }


    /**
     * Inicialitza les variables del joc.
     */
    private void INNIT()
    {
        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        density  = this.getResources().getDisplayMetrics().density;
        dpHeight = outMetrics.heightPixels / density;
        dpWidth  = outMetrics.widthPixels / density;

        isVertical = (dpHeight > dpWidth);
        if (isVertical) textSize = (int) dpHeight*3/80;
        else textSize = (int) dpWidth*3/80;

        textViewSolucions.setMovementMethod(new ScrollingMovementMethod());
        textViewSolucions.setTextSize((float) MainActivity.textSize/2);

        botons.setTextSize(textSize);
        botons.setColor(color);
        botons.setNumAjudes(nAjudes);

        paraulesOcultes.crear(paraules);
        botonsCercle.crear(paraules.getParaulaTriada());
        textViewSolucions.setText(paraules.getParaulesTorbades(true));

        if (paraules.getParaulesOcultes().isEmpty())
        {
            disableViews(R.id.main);
            botons.desactiva();
            botonsCercle.desactiva();
        }
    }


    /**
     * Afegeix una lletra de les possibles a la paraula.
     */
    public void setLletra(View view)
    {
        Button button = (Button) view;
        String lletra = String.valueOf(button.getText());

        textViewParaula.append(lletra);

        button.setClickable(false);
        button.setTextColor(color);
    }


    /**
     * Reinicia la paraula i els botons de les lletres.
     */
    public void clear(View view)
    {
        textViewParaula.setText("");
        botonsCercle.activa();
    }


    /**
     * Comprova la paraula jugada, afegint-la a la colecció de solucions si està continguda.
     */
    public void send(View view)
    {
        String s = String.valueOf(textViewParaula.getText()).toLowerCase();
        clear(null);

        boolean esParaulaValida = paraules.esParaulaValida(s);
        textViewSolucions.setText(paraules.getParaulesTorbades(true));

        if (esParaulaValida)
        {
            mostraMissatge("Added", false);

            nEncertades++;
            if ((nEncertades % 5) == 0)
            {
                nEncertades = 0;
                botons.setNumAjudes(++nAjudes);
            }

            int pos = paraules.esParaulaOculta(s);
            if (pos < 0) return;

            String res = paraules.getParaulaFormatada(s);
            paraulesOcultes.mostraParaula(res, pos);
            if (paraules.getParaulesOcultes().isEmpty()) win();
        }
        else
        {
            mostraMissatge("Not added", false);
        }
    }


    /**
     * Mescla les lletres del cercle.
     */
    public void random(View view)
    {
        botonsCercle.random();
    }


    /**
     * Mostra una ajuda d'una paraula amagada aleatòria.
     */
    public void ajuda(View view)
    {
        if (nAjudes == 0) return;

        UnsortedArrayMapping<String, Integer> ajudes = paraules.getAjudesDisponibles();
        if (ajudes.isEmpty()) return;

        UnsortedArrayMapping.Pair pair = ajudes.random();
        String s = (String) pair.getKey();
        int i = (Integer) pair.getValue();

        paraulesOcultes.mostraPrimeraLletra(s, i);
        paraules.getAjudesActuals().put(s, i);
        ajudes.remove(s);
        botons.setNumAjudes(--nAjudes);
    }


    /**
     * Consulta les paraules de solucions.
     */
    public void consultarBonus(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Encertades (" + paraules.getNumParaulesEncertades() + " de " + paraules.getNumParaulesValides() + "):");
        builder.setMessage(paraules.getParaulesTorbades(false));
        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /**
     * Comença una nova partida.
     */
    public void reset(View view)
    {
        paraulesOcultes.amaga();

        generarColorAleatori();
        paraules.novaParaula();

        textViewSolucions.setText(paraules.getParaulesTorbades(true));
        paraulesOcultes.crear(paraules);
        botonsCercle.crear(paraules.getParaulaTriada());

        botons.setColor(color);
        botons.activa();

        clear(null);
        enableViews(R.id.main);
    }


    /**
     * Mostra un missatge per pantalla amb una llargaria definida.
     */
    private void mostraMissatge(String s, boolean llarg)
    {
        Context context = getApplicationContext();
        int duration;

        if (llarg) duration = Toast.LENGTH_LONG;
        else duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }


    /**
     * Habilita tots els views de la pantalla.
     */
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


    /**
     * Deshabilita tots els views de la pantalla.
     */
    private void disableViews(int parent)
    {
        ViewGroup viewGroup = findViewById(parent);
        int count = viewGroup.getChildCount();
        for (int i=0; i<count; i++)
        {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(false);
        }

        findViewById(R.id.buttonBonus).setEnabled(true);
        findViewById(R.id.buttonReinicia).setEnabled(true);
    }


    /**
     * Mostra un missatge de victòria i bloqueja tot el que no sigui veure les solucions i reiniciar.
     */
    private void win()
    {
        disableViews(R.id.main);
        botons.desactiva();
        botonsCercle.desactiva();
        mostraMissatge("Enhorabona, has guanyat!", true);
    }


    /**
     * Genera un color aleatori.
     */
    private void generarColorAleatori()
    {
        Random ran = new Random();

        // Generar valores aleatorios para el componente de rojo, verde, azul y alfa
        int red = ran.nextInt(256);
        int green = ran.nextInt(256);
        int blue = ran.nextInt(256);

        //transparencia
        int alpha = 200;

        // Formatear el color en formato hexadecimal
        String colorHex = String.format("#%02X%02X%02X%02X", alpha, red, green, blue);
        color = Color.parseColor(colorHex);
    }

}
