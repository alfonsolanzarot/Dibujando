package com.lanzarot.dibujando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lanzarot.dibujando.audio.Audio;
import com.lanzarot.dibujando.canvas.CanvasPrincipal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Objeto de la clase CanvasPrincipal
    private CanvasPrincipal canvasPrincipal;
    private ConstraintLayout constraintLayout;
    //Objeto botón.
    private Button boton;
    //Objeto de audio.
    private Audio audio = new Audio();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton = findViewById(R.id.button);
        boton.setOnClickListener(this);

        //Instanciamos la clase canvas y muestro el contenido en el ConstraintLayout.
        canvasPrincipal = new CanvasPrincipal(this);
        constraintLayout = findViewById(R.id.contenedorPantallaPrincipal);
        constraintLayout.addView(canvasPrincipal);

        //Iniciamos la música
        audio.audioPrincipal(this);
    }

    //Método onPause
    @Override
    protected void onPause() {
        super.onPause();
        if (audio.getMp().isPlaying()) {
            audio.stopSound();
        }
    }

    //Método onResume
    @Override
    protected void onResume() {
        super.onResume();
        if (!audio.getMp().isPlaying()) {
            audio.audioPrincipal(this);
        }
    }

    //Método para pasar a la pantalla de dibujo.
    @Override
    public void onClick (View view){
        Intent intent = new Intent(this, DibujoActivity.class);
        startActivity(intent);

    }

    //Metodo que controla la pulsación de la tecla física de atrás
    @Override
    public void onBackPressed() {

    }
}