package com.lanzarot.dibujando.audio;

import android.content.Context;
import android.media.MediaPlayer;


import com.lanzarot.dibujando.R;

public class Audio {
    //Objeto mediaplayer
    private MediaPlayer mp;

    //Método para iniciar la música en la pantalla principal
    public void audioPrincipal(Context context){
        //Creamos el objeto MediaPlayer con el audio a reproducir
        mp = MediaPlayer.create(context, R.raw.they_say);
        //Lo ponemos para que se inicie al comenzar
        mp.seekTo(0);
        //Iniciamos la reproducción
        mp.start();
        //Lo ponemos en bucle
        mp.setLooping(true);
    }

    //Método para iniciar la música en el pantalla de dibujo.
    public void audioDibujo(Context context){
        //Creamos el objeto MediaPlayer
        mp = MediaPlayer.create(context, R.raw.autumn_allure);
        //Lo pongemos para que se inicie al comenzar
        mp.seekTo(0);
        //Iniciamos la reproducción
        mp.start();
        //Lo ponemos en bucle
        mp.setLooping(true);
    }

    //Método para parar la música
    public void stopSound() {
        mp.stop();
    }

    //Getter
    public MediaPlayer getMp() {
        return mp;
    }

}
