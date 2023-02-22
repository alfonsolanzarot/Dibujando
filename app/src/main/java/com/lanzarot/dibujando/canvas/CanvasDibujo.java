package com.lanzarot.dibujando.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.lanzarot.dibujando.R;

import java.util.ArrayList;

public class CanvasDibujo extends View {
    //Variable que guarda la posición del botón seleccionado
    private int posicion = -1;
    //Variable para el pincel de las distintas formas
    private final Paint pincel = new Paint();
    //Variable para definir los caminos de trazado
    private final Path trazado = new Path();
    //Paint para crear el canvas donde se mostrarán los dibujos
    private final Paint canvasPaint = new Paint();
    //Canvas para mostrar los trazados dibujados
    private Canvas drawCanvas;
    //Bitmap para crear el canvas anterior
    private Bitmap canvasBitmap;

    //CONSTRUCTOR
    public CanvasDibujo(Context context) {
        super(context);
    }

    //MÉTODOS
    //Metodo para elegir el pincel según el botón pulsado
    public void elegirPincel(ArrayList<Button> botones) {
        //Buscamos el botón seleccionado. Almacenamos su posición
        for (int i = 0; i < botones.size(); i++) {
            if (!botones.get(i).isEnabled()) {
                posicion = i;
                i = botones.size();
            }
        }
        //Dependiendo de la posición elegimos el color y el trazado del pincel
        if (posicion == 0) {
            pincel.setColor(ContextCompat.getColor(getContext(), R.color.azul));
        } else if (posicion == 1) {
            pincel.setColor(ContextCompat.getColor(getContext(), R.color.verde));
        } else if (posicion == 2) {
            pincel.setColor(ContextCompat.getColor(getContext(), R.color.rojo));
        } else if (posicion == 3) {
            pincel.setColor(ContextCompat.getColor(getContext(), R.color.colorEstrella));
        }
    }

    //Método para crear el trazado en círculo
    private void circulo(float x, float y) {
        trazado.addCircle(x, y, 20, Path.Direction.CW);
    }

    //Método para crear el trazado en estrella
    private void estrella(float x, float y) {
        trazado.moveTo(x, y);
        trazado.lineTo(x -15, y + 40);
        trazado.lineTo(x - 60, y + 40);
        trazado.lineTo(x - 23, y + 62);
        trazado.lineTo(x - 40, y + 105);
        trazado.lineTo(x, y + 77);
        trazado.lineTo(x + 40, y + 105);
        trazado.lineTo(x + 23, y + 62);
        trazado.lineTo(x + 60, y + 40);
        trazado.lineTo(x + 15, y + 40);
        trazado.lineTo(x, y);

    }

    //Método para el trazado de la cara
    private void cara(float x, float y) {
        Bitmap cara = BitmapFactory.decodeResource(getResources(), R.drawable.cara);
        drawCanvas.drawBitmap(cara, x, y, null);
    }

    //Método para dibujar el trazado. Dependiendo del botón elegido cambiará el trazado
    private void dibujar(float x, float y) {
        if (posicion >= 0 && posicion <= 2) {
            circulo(x, y);
        } else if (posicion == 3) {
            estrella(x, y);
        } else {
            cara(x, y);
        }
    }

    //Método para controlar el evento onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //Detectamos qué tipo de evento se está realizando: presionar, mover o levantar. Enviamos las coordenadas para dibujar
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            dibujar(e.getX(), e.getY());
        }
        if (e.getAction() == MotionEvent.ACTION_MOVE) {
            dibujar(e.getX(), e.getY());
        }
        if (e.getAction() == MotionEvent.ACTION_UP) {
            trazado.reset();
        }

        invalidate();
        return true;
    }

    //Método onDraw para dibujar en el lienzo de dibujo
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Dibujamos un bitmap para que se puedan solapar los dibujos y no desaparezcan
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        //Dibujamos los diferentes trazados
        drawCanvas.drawPath(trazado, pincel);
    }
    //Método onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //Creamos el canvas que tendrá los dibujos con su correspondiente bitmap
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    //Método para borrar
    public void reset() {
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

}