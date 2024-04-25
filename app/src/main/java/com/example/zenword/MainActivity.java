package com.example.zenword;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
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
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void setLletra(View view)
    {
        Button btn = ( Button ) view ;
        String lletra = btn . getText () . toString () ;

        TextView txt = findViewById(R.id.textView2);
        txt.append(lletra);

        btn.setClickable(false);
        //editar color
    }

    public void clear(View view)
    {
        Button btn = ( Button ) view ;
        TextView txt = findViewById(R.id.textView2);
        txt.setText("");

        //reactivar tots els botons
    }

    public void random(View view)
    {
                Random r = new Random();

                circleButtons[0] = findViewById(R.id.circleButton1);
                circleButtons[1] = findViewById(R.id.circleButton2);
                circleButtons[2] = findViewById(R.id.circleButton3);
                circleButtons[3] = findViewById(R.id.circleButton4);
                circleButtons[4] = findViewById(R.id.circleButton5);
                circleButtons[5] = findViewById(R.id.circleButton6);
                circleButtons[6] = findViewById(R.id.circleButton7);
                int j;
                Button aux;
                for (int i = circleButtons.length-1; i >= 0; i--) {
                    j = r.nextInt(6);
                    aux = circleButtons[i];
                    circleButtons[i].setText(circleButtons[j].getText());
                    circleButtons[j].setText(aux.getText());
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
        for (TextView txtView : param)
        {
            txtView = new TextView ( this ) ;
            txtView.setId(View.generateViewId());
            txtView.setText("");
        }

        return param;
    }
    public static boolean esParaulaSolucio(String paraula1, String paraula2) {
        UnsortedArrayMapping<Character, Integer> repetitions = new UnsortedArrayMapping<Character, Integer>(paraula1.length());
        char[] arrAux;
        //si tenen longituds diferents ja no és solució
        if (paraula1.length() != paraula2.length()) {
            return false;
        } else {
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
                    return false;
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
        }
        return false;
    }

}

