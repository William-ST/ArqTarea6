package audiolibros.example.com.audiolibros;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by William_ST on 08/02/19.
 */

public class ZoomSeekBar extends View {
    // Valor a controlar
    private int val; // valor seleccionado
    private int valMin; // valor mínimo
    private int valMax; // valor máximo
    private int escalaMin; // valor mínimo visualizado
    private int escalaMax; // valor máximo visualizado
    private int escalaIni; // origen de la escala
    private int escalaRaya; // cada cuantas unidades una rayas
    private int escalaRayaLarga; // cada cuantas rayas una larga
    // Dimensiones en pixels
    private int altoNumeros;
    private int altoRegla;
    private int altoBar;
    private int altoPalanca;
    private int anchoPalanca;
    private int altoGuia;
    // Valores que indican donde dibujar
    private int xIni;
    private int yIni;
    private int ancho;
    // Objetos Rect con diferentes regiones
    private Rect escalaRect = new Rect();
    private Rect barRect = new Rect();
    private Rect guiaRect = new Rect();
    private Rect palancaRect = new Rect();
    // Objetos Paint globales para no tener que crearlos cada vez
    private Paint textoPaint = new Paint();
    private Paint reglaPaint = new Paint();
    private Paint guiaPaint = new Paint();
    private Paint palancaPaint = new Paint();

    public ZoomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        float dp = getResources().getDisplayMetrics().density;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ZoomSeekBar, 0, 0);
        try {
            altoNumeros = a.getDimensionPixelSize(
                    R.styleable.ZoomSeekBar_altoNumeros, (int) (30 * dp));
            altoRegla = a.getDimensionPixelSize(
                    R.styleable.ZoomSeekBar_altoRegla, (int) (20 * dp));
            altoBar = a.getDimensionPixelSize(
                    R.styleable.ZoomSeekBar_altoBar, (int) (70 * dp));
            altoPalanca = a.getDimensionPixelSize(
                    R.styleable.ZoomSeekBar_altoPalanca, (int) (40 * dp));
            altoGuia = a.getDimensionPixelSize(
                    R.styleable.ZoomSeekBar_altoGuia, (int) (10 * dp));
            anchoPalanca = a.getDimensionPixelSize(
                    R.styleable.ZoomSeekBar_anchoPalanca, (int) (20 * dp));
            textoPaint.setTextSize(a.getDimension(
                    R.styleable.ZoomSeekBar_altoTexto, 16 * dp));
            textoPaint.setColor(a.getColor(
                    R.styleable.ZoomSeekBar_colorTexto, Color.BLACK));
            reglaPaint.setColor(a.getColor(
                    R.styleable.ZoomSeekBar_colorRegla, Color.BLACK));
            guiaPaint.setColor(a.getColor(
                    R.styleable.ZoomSeekBar_colorGuia, Color.BLUE));
            palancaPaint.setColor(a.getColor(
                    R.styleable.ZoomSeekBar_colorPalanca, 0xFF00007F));

            val = a.getInteger(R.styleable.ZoomSeekBar_val, 160);
            valMin = a.getInteger(R.styleable.ZoomSeekBar_valMin, 100);
            valMax = a.getInteger(R.styleable.ZoomSeekBar_valMax, 200);
            escalaMin = a.getInteger(R.styleable.ZoomSeekBar_escalaMin, 150);
            escalaMax = a.getInteger(R.styleable.ZoomSeekBar_escalaMax, 180);
            escalaIni = a.getInteger(R.styleable.ZoomSeekBar_escalaIni, 100);
            escalaRaya = a.getInteger(R.styleable.ZoomSeekBar_escalaRaya, 2);
            escalaRayaLarga = a.getInteger(R.styleable.ZoomSeekBar_escalaRayaLarga, 5);

        } finally {
            a.recycle();
        }
        textoPaint.setAntiAlias(true);
        textoPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        xIni = getPaddingLeft();
        if (altoSobrante <= 0) {
            yIni = getPaddingTop();
        } else {
            yIni = getPaddingTop() + (altoSobrante / 2);
        }
        ancho = getWidth() - getPaddingRight() - getPaddingLeft();
        barRect.set(xIni, yIni, xIni + ancho, yIni + altoBar);
        escalaRect.set(xIni, yIni + altoBar, xIni + ancho, yIni + altoBar
                + altoNumeros + altoRegla);
        int y = yIni + (altoBar - altoGuia) / 2;
        guiaRect.set(xIni, y, xIni + ancho, y + altoGuia);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Dibujamos Barra con palanca
        canvas.drawRect(guiaRect, guiaPaint);
        int y = yIni + (altoBar - altoPalanca) / 2;
        int x = xIni + ancho * (val - escalaMin) / (escalaMax - escalaMin)
                - anchoPalanca / 2;
        palancaRect.set(x, y, x + anchoPalanca, y + altoPalanca);
        canvas.drawRect(palancaRect, palancaPaint);
        palancaRect.set(x - anchoPalanca / 2, y, x + 3 * anchoPalanca / 2, y + altoPalanca);
        // Dibujamos Escala
        int v = escalaIni;
        while (v <= escalaMax) {
            if (v >= escalaMin) {
                x = xIni + ancho * (v - escalaMin) / (escalaMax - escalaMin);
                if (((v - escalaIni) / escalaRaya) % escalaRayaLarga == 0) {
                    y = yIni + altoBar + altoRegla;
                    canvas.drawText(Integer.toString(v), x, y + altoNumeros,
                            textoPaint);
                    y = yIni + altoBar + altoRegla * 1 / 3;
                }
                canvas.drawLine(x, yIni + altoBar, x, y, reglaPaint);
            } else {
            }
            v += escalaRaya;
        }
    }

    // Variables globales usadas en onTouchEvent()
    enum Estado {
        SIN_PULSACION, PALANCA_PULSADA, ESCALA_PULSADA, ESCALA_PULSADA_DOBLE
    };

    Estado estado = Estado.SIN_PULSACION;
    int antVal_0, antVal_1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x_0, y_0, x_1, y_1;
        x_0 = (int) event.getX(0);
        y_0 = (int) event.getY(0);
        int val_0 = escalaMin + (x_0 - xIni) * (escalaMax - escalaMin) / ancho;
        if (event.getPointerCount() > 1) {
            x_1 = (int) event.getX(1);
            y_1 = (int) event.getY(1);
        } else {
            x_1 = x_0;
            y_1 = y_0;
        }
        int val_1 = escalaMin + (x_1 - xIni) * (escalaMax - escalaMin) / ancho;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (palancaRect.contains(x_0, y_0)) {
                    estado = Estado.PALANCA_PULSADA;
                } else if (barRect.contains(x_0, y_0)) {
                    if (val_0 > val) val++;
                    else val--;
                    invalidate(barRect);
                } else if (escalaRect.contains(x_0, y_0)) {
                    estado = Estado.ESCALA_PULSADA;
                    antVal_0 = val_0;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (estado == Estado.ESCALA_PULSADA) {

                    if (escalaRect.contains(x_1, y_1)) {
                        antVal_1 = val_1;
                        estado = Estado.ESCALA_PULSADA_DOBLE;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                estado = Estado.SIN_PULSACION;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (estado == Estado.ESCALA_PULSADA_DOBLE) {
                    estado = Estado.ESCALA_PULSADA;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (estado == Estado.PALANCA_PULSADA) {
                    val = ponDentroRango(val_0, escalaMin, escalaMax);
                    if (onChangeSeekbar != null) onChangeSeekbar.value(val);
                    invalidate(barRect);
                }
                if (estado == Estado.ESCALA_PULSADA_DOBLE) {
                    escalaMin = antVal_0 + (xIni - x_0) * (antVal_0 - antVal_1) / (x_0 - x_1);
                    escalaMin = ponDentroRango(escalaMin, valMin, val);
                    escalaMax = antVal_0 + (ancho + xIni - x_0)
                            * (antVal_0 - antVal_1) / (x_0 - x_1);
                    escalaMax = ponDentroRango(escalaMax, val, valMax);
                    invalidate();
                }
                break;
        }
        return true;
    }

    int ponDentroRango(int val, int valMin, int valMax) {
        if (val < valMin) {
            return valMin;
        } else if (val > valMax) {
            return valMax;
        } else {
            return val;
        }
    }

    private int altoSobrante = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int altoDeseado = altoNumeros + altoRegla + altoBar + getPaddingBottom() + getPaddingTop();
        int alto = obtenDimension(heightMeasureSpec, altoDeseado);
        altoSobrante = alto - altoDeseado;
        int anchoDeseado = 2 * altoDeseado;
        int ancho = obtenDimension(widthMeasureSpec, anchoDeseado);
        setMeasuredDimension(ancho, alto);
    }

    private int obtenDimension(int measureSpec, int deseado) {
        int dimension = MeasureSpec.getSize(measureSpec);
        int modo = MeasureSpec.getMode(measureSpec);
        if (modo == MeasureSpec.EXACTLY) {
            return dimension;
        } else if (modo == MeasureSpec.AT_MOST) {
            return Math.min(dimension, deseado);
        } else {
            return deseado;
        }
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        if (valMin <= val && val <= valMax) {
            this.val = val;
            if (onChangeSeekbar != null) onChangeSeekbar.value(val);
            escalaMin = Math.min(escalaMin, val);
            escalaMax = Math.max(escalaMax, val);
            invalidate();
        }
    }

    public int getValMin() {
        return valMin;
    }

    public void setValMin(int valMin) {
        this.valMin = valMin;
        invalidate();
    }

    public int getValMax() {
        return valMax;
    }

    public void setValMax(int valMax) {
        if (valMax > valMin) {
            this.valMax = valMax;
            invalidate();
        }
    }

    public int getEscalaMin() {
        return escalaMin;
    }

    public void setEscalaMin(int escalaMin) {
        this.escalaMin = escalaMin;
        invalidate();
    }

    public int getEscalaMax() {
        return escalaMax;
    }

    public void setEscalaMax(int escalaMax) {
        this.escalaMax = escalaMax;
        invalidate();
    }

    public int getEscalaIni() {
        return escalaIni;
    }

    public void setEscalaIni(int escalaIni) {
        this.escalaIni = escalaIni;
        invalidate();
    }

    public int getEscalaRaya() {
        return escalaRaya;
    }

    public void setEscalaRaya(int escalaRaya) {
        this.escalaRaya = escalaRaya;
        invalidate();
    }

    public int getEscalaRayaLarga() {
        return escalaRayaLarga;
    }

    public void setEscalaRayaLarga(int escalaRayaLarga) {
        this.escalaRayaLarga = escalaRayaLarga;
        invalidate();
    }

    private OnChangeSeekbar onChangeSeekbar;

    public interface OnChangeSeekbar {
        void value(int value);
    }

    public void setOnChangeSeekbar(OnChangeSeekbar onChangeSeekbar) {
        this.onChangeSeekbar = onChangeSeekbar;
    }

}