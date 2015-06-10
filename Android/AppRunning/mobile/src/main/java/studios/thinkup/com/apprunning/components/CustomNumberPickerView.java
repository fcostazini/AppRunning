package studios.thinkup.com.apprunning.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import studios.thinkup.com.apprunning.IconTextView;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.provider.TypefaceProvider;

/**
 * TODO: document your custom view class.
 */
public class CustomNumberPickerView extends RelativeLayout implements View.OnClickListener {
    private IconTextView plus;
    private IconTextView minus;
    private TextView numero;
    private int numeroVal;
    private String   numberTypeFace;
    private String   plusSymbol;
    private String   minusSymbol;
    private int      numberColor;
    private int      plusColor;
    private int      minusColor;
    private Drawable plusBackground;
    private Drawable minusBackground;
    private Drawable numberBackground;

    public CustomNumberPickerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CustomNumberPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CustomNumberPickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        // Load attributes
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null){
            inflater.inflate(R.layout.custom_number_piker, this,true);
        }

        this.numero = (TextView)findViewById(R.id.txt_num);
        this.plus = (IconTextView)findViewById(R.id.ic_plus);
        this.minus = (IconTextView)findViewById(R.id.ic_minus);
        this.plus.setOnClickListener(this);
        this.minus.setOnClickListener(this);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CustomNumberPickerView, defStyle, 0);

        this.numeroVal  = a.getInt(R.styleable.CustomNumberPickerView_numberValue,0);

        if(a.hasValue(R.styleable.CustomNumberPickerView_numberTypeFace)){
            this.numberTypeFace = a.getString(R.styleable.CustomNumberPickerView_numberTypeFace);
        }else{
            this.numberTypeFace = TypefaceProvider.DIGIT;
        }

        if(a.hasValue(R.styleable.CustomNumberPickerView_plusSymbol)){
            this.plusSymbol = a.getString(R.styleable.CustomNumberPickerView_plusSymbol);
        }else{
            this.plusSymbol = context.getResources().getString(R.string.icon_plus_square);
        }

        if(a.hasValue(R.styleable.CustomNumberPickerView_minusSymbol)){
            this.minusSymbol = a.getString(R.styleable.CustomNumberPickerView_minusSymbol);
        }else{
            this.minusSymbol = context.getResources().getString(R.string.icon_minus_square);
        }

        this.numberColor = a.getColor(R.styleable.CustomNumberPickerView_numberColor, 0x77000000);
        this.plusColor = a.getColor(R.styleable.CustomNumberPickerView_plusColor, 0x77000000);
        this.minusColor = a.getColor(R.styleable.CustomNumberPickerView_minusColor, 0x77000000);

        if(a.hasValue(R.styleable.CustomNumberPickerView_numberBackground)){
            this.numberBackground = a.getDrawable(R.styleable.CustomNumberPickerView_numberBackground);
        }else{
            this.numberBackground = new ColorDrawable(0xAA00AAFF);
        }
        if(a.hasValue(R.styleable.CustomNumberPickerView_minusBackground)){
            this.minusBackground = a.getDrawable(R.styleable.CustomNumberPickerView_minusBackground);
        }else{
            this.minusBackground = new ColorDrawable(0xAA00AAFF);
        }
        if(a.hasValue(R.styleable.CustomNumberPickerView_plusBackground)){
            this.plusBackground = a.getDrawable(R.styleable.CustomNumberPickerView_plusBackground);
        }else{
            this.plusBackground = new ColorDrawable(0xAA00AAFF);
        }

        this.numero.setTypeface(TypefaceProvider.getInstance(context).getTypeface(this.numberTypeFace));
        this.numero.setText(getNumeroText(numeroVal));
        this.numero.setTextColor(this.numberColor);
        this.numero.setBackground(this.numberBackground);

        this.plus.setTypeface(TypefaceProvider.getInstance(context).getTypeface(TypefaceProvider.ICOMOON));
        this.plus.setText(plusSymbol);
        this.plus.setTextColor(this.plusColor);
        this.plus.setBackground(this.plusBackground);

        this.minus.setTypeface(TypefaceProvider.getInstance(context).getTypeface(TypefaceProvider.ICOMOON));
        this.minus.setText(minusSymbol);
        this.minus.setTextColor(this.minusColor);
        this.minus.setBackground(this.minusBackground);

        a.recycle();
    }

    private String getNumeroText(int val) {
        return val <10 ? "0"+ val: val+"";
    }

    public int getNumeroVal() {
        return numeroVal;
    }


    public String getNumberTypeFace() {
        return numberTypeFace;
    }

    public void setNumberTypeFace(String numberTypeFace) {
        this.numberTypeFace = numberTypeFace;
        this.numero.setTypeface(TypefaceProvider.getInstance(this.getContext()).getTypeface(this.numberTypeFace));
        invalidate();
    }

    public String getPlusSymbol() {
        return plusSymbol;
    }

    public void setPlusSymbol(String plusSymbol) {
        this.plusSymbol = plusSymbol;
        this.plus.setText(plusSymbol);
        invalidate();
    }

    public String getMinusSymbol() {
        return minusSymbol;
    }

    public void setMinusSymbol(String minusSymbol) {
        this.minusSymbol = minusSymbol;
        this.minus.setText(minusSymbol);
        invalidate();
    }

    public int getNumberColor() {
        return numberColor;
    }

    public void setNumberColor(int numberColor) {
        this.numberColor = numberColor;
        this.numero.setTextColor(numberColor);
        invalidate();
    }

    public int getPlusColor() {
        return plusColor;
    }

    public void setPlusColor(int plusColor) {
        this.plusColor = plusColor;
        this.plus.setTextColor(plusColor);
        invalidate();
    }

    public int getMinusColor() {
        return minusColor;
    }

    public void setMinusColor(int minusColor) {
        this.minusColor = minusColor;
        this.minus.setTextColor(minusColor);
        invalidate();
    }

    public Drawable getPlusBackground() {
        return plusBackground;
    }

    public void setPlusBackground(Drawable plusBackground) {
        this.plusBackground = plusBackground;
        this.plus.setBackground(plusBackground);
        invalidate();
    }

    public Drawable getMinusBackground() {
        return minusBackground;

    }

    public void setMinusBackground(Drawable minusBackground) {
        this.minusBackground = minusBackground;
        this.minus.setBackground(minusBackground);
        invalidate();
    }

    public Drawable getNumberBackground() {
        return numberBackground;
    }

    public void setNumberBackground(Drawable numberBackground) {
        this.numberBackground = numberBackground;
        this.numero.setBackground(numberBackground);
        invalidate();
    }

    public void plus(){
        this.numeroVal += 1;
        if( this.numeroVal >= 60){
            this.numeroVal = 59;
        }else{
            this.numero.setText(getNumeroText(numeroVal));
        }

    }


    public void minus(){
        this.numeroVal -= 1;
        if( this.numeroVal < 0){
            this.numeroVal = 0;
        }else{
            this.numero.setText(getNumeroText(numeroVal));
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.ic_plus){
            this.plus();
        }else{
            this.minus();
        }
    }

    public void setNumeroVal(int numeroVal) {
        this.numeroVal = numeroVal;
        this.numero.setText(getNumeroText(numeroVal));
    }
}
