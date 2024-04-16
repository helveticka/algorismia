package com.example.zenword;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
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

    public void setLletra ( View view )
    {
        /*Button btn = ( Button ) view ;
        int aux = btn.getHeight();
        System.out.println(aux);
        btn.setVisibility(View.GONE);
        String lletra = btn . getText () . toString () ;*/


        TextView txt = findViewById(R.id.button11);
        System.out.println(txt.getWidth() + ", " + txt.getHeight());
    }

    public void push ( View view )
    {
        TextView txt = findViewById(R.id.button11);
        System.out.println(txt.getWidth() + ", " + txt.getHeight());
    }
}
