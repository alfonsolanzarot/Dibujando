package com.lanzarot.dibujando;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.lanzarot.dibujando.audio.Audio;
import com.lanzarot.dibujando.canvas.CanvasDibujo;

import java.util.ArrayList;

public class DibujoActivity extends AppCompatActivity {
    //Objeto de audio
    private Audio audio = new Audio();

    //Objeto de la clase CanvasDibujo.
    private CanvasDibujo canvasDibujo;
    private ConstraintLayout constraintLayout;
    //Creamos un arraylist para almacenar los diferentes botones
    private ArrayList<Button> botones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dibujo);
        //Instanciamos la clase canvas y mostramos el contenido en el ConstraintLayout.
        canvasDibujo = new CanvasDibujo(getApplicationContext());
        constraintLayout = findViewById(R.id.lienzo);
        constraintLayout.addView(canvasDibujo);

        //Iniciamos la música
        audio.audioDibujo(this);

        //Llamada al método guardarBotones para almacenarlos en el ArrayList
        guardarBotones();
    }

    //Método para guardar los botones en el ArrayList.
    private void guardarBotones(){
        //Guardamos el primer botón, el del círculo azul, que estará activado por defecto al iniciar la pantalla.
        Button defecto = (Button) findViewById(R.id.btn_azul);
        //Hacemos clic de forma automática a este botón al inicio de la pantalla
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                defecto.performClick();
            }
        }, 0);

        //Añadimos los botones al ArrayList
        this.botones.add(defecto);
        this.botones.add((Button) findViewById(R.id.btn_verde));
        this.botones.add((Button) findViewById(R.id.btn_rojo));
        this.botones.add((Button) findViewById(R.id.btn_estrella));
        this.botones.add((Button) findViewById(R.id.btn_cara));
    }

    //Método para controlar los clics de los botones.

    public void eventoClic (View view){
        //Recorremos con un switch los casos de pulsación de los botones.
        switch(view.getId()){
            case R.id.btn_borrar:
                //Limpiamos el lienzo al pulsar el botón borrar.
                canvasDibujo.reset();
                break;
            case R.id.btn_inicio:
                //Volvemos a la pantalla de inicio al pulsar el botón de inicio.
                onBackPressed();
                break;
            default:
                //Recorremos el array con los botones.
                for (int i = 0; i < botones.size(); i++){
                //Si el botón está pulsado, le cambiamos el fondo y lo desactivamos. El resto se resetean.
                    if(botones.get(i).getId() == view.getId()){
                        botones.get(i).setEnabled(false);
                        botones.get(i).setBackgroundColor(ContextCompat.getColor(this, R.color.botonPulsado));
                    } else {
                        botones.get(i).setEnabled(true);
                        botones.get(i).setBackgroundColor(ContextCompat.getColor(this, R.color.botonesPanel));
                    }
                }
                //Llamamos al método elegirPincel de la clase CanvasDibujo, que cambiará el pincel.
                canvasDibujo.elegirPincel(botones);
                break;
        }
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
            audio.audioDibujo(this);
        }
    }
}
