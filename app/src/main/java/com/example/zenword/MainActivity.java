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


/**
 *
 */
public class MainActivity extends AppCompatActivity
{

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

        TextView txt = findViewById(R.id.button11);
        System.out.println(txt.getWidth() + ", " + txt.getHeight());
        txt.setWidth(txt.getHeight());
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
}
