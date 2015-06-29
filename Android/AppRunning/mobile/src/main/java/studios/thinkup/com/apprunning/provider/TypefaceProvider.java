package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by FaQ on 08/06/2015.
 */
public class TypefaceProvider {
    private HashMap<String,Typeface> typeFaces;
    private static TypefaceProvider instance;
    public final static String DIGIT = "fonts/digit.ttf";
    public final static String ICOMOON = "fonts/icomoon.ttf";
    public final static String MAINFONT = "fonts/mainfont.ttf";

    private TypefaceProvider(Context context){
        typeFaces = new HashMap<>();
        typeFaces.put(DIGIT,Typeface.createFromAsset(context.getAssets(), DIGIT));
        typeFaces.put(ICOMOON,Typeface.createFromAsset(context.getAssets(),ICOMOON));
        typeFaces.put(MAINFONT,Typeface.createFromAsset(context.getAssets(), MAINFONT));

    }


    public static TypefaceProvider getInstance(Context context){
        if(TypefaceProvider.instance==null){
            TypefaceProvider.instance = new TypefaceProvider(context);
        }
        return TypefaceProvider.instance;
    }

    public Typeface getTypeface(String nombre){
        return this.typeFaces.get(nombre);
    }

}
