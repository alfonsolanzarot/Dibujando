package com.lanzarot.dibujando.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.lanzarot.dibujando.R;

public class CanvasPrincipal extends View {
    private Paint pincelTitulo;
    private Bitmap fondo;
    private Rect rect;
    private Rect bounds;
    private Bitmap imgIcono;

    //CONSTRUCTOR
    public CanvasPrincipal(Context context) {
        super(context);
        inicializar();
    }

    public void inicializar(){
        //Inicializamos el pincel para el texto.
        pincelTitulo = new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int h = getHeight();
        int w = getWidth();

        //Establecemos el fondo.
        fondo = BitmapFactory.decodeResource(getResources(),R.drawable.fondo);
        rect = new Rect(0, 0, w, h);
        canvas.drawBitmap(fondo, null, rect, null);

        //Ponemos el texto del título en la pantalla principal.
        //1º le damos estilo al texto a través del pincel.
        TextView estilo =new TextView(getContext());
        estilo.setTextAppearance(getContext(), R.style.titulo);
        pincelTitulo.setColor(estilo.getCurrentTextColor());
        pincelTitulo.setTextSize(estilo.getTextSize());
        //2º Aplicamos la fuente.
        Typeface tipoFuente = ResourcesCompat.getFont(getContext(), R.font.danube_);
        pincelTitulo.setTypeface(tipoFuente);
        //3º Tomamos el título del fichero strings.xml
        String titulo = getResources().getString(R.string.app_name);
        //4º Creamos un rectángulo para meter el texto y centrarlo.
        bounds = new Rect();
        pincelTitulo.getTextBounds(titulo, 0, titulo.length(),bounds);
        canvas.drawText(titulo, (canvas.getWidth()/2) - (bounds.width()/2), 300, pincelTitulo);

        //Introducimos un icono.
        imgIcono = BitmapFactory.decodeResource(getResources(), R.drawable.icono);
        //Dibujo la imagen y la centro arriba.
        canvas.drawBitmap(imgIcono, (getWidth() - imgIcono.getWidth())/2, (getHeight() - imgIcono.getHeight())/5, null);


    }
}
