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
    public final static String MILKRUN = "fonts/milkrun.ttf";

    private TypefaceProvider(Context context){
        typeFaces = new HashMap<>();
        typeFaces.put("fonts/digit.ttf",Typeface.createFromAsset(context.getAssets(), "fonts/digit.ttf"));
        typeFaces.put("fonts/icomoon.ttf",Typeface.createFromAsset(context.getAssets(), "fonts/icomoon.ttf"));
        typeFaces.put("fonts/milkrun.ttf",Typeface.createFromAsset(context.getAssets(), "fonts/milkrun.ttf"));

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
