package studios.thinkup.com.apprunning.provider;

import android.content.ContentValues;

import com.google.common.base.CaseFormat;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import studios.thinkup.com.apprunning.model.entity.IEntity;
import studios.thinkup.com.apprunning.provider.exceptions.CampoNoMapeableException;

/**
 * Created by FaQ on 13/06/2015.
 * Factory para ContentValues
 */
public class ContentValueFactory {


    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    public static boolean isWrapperType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    private static Set<Class<?>> getWrapperTypes() {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(Boolean.class);
        ret.add(boolean.class);
        ret.add(Character.class);
        ret.add(char.class);
        ret.add(Byte.class);
        ret.add(byte.class);
        ret.add(Short.class);
        ret.add(int.class);
        ret.add(Integer.class);
        ret.add(long.class);
        ret.add(Long.class);
        ret.add(float.class);
        ret.add(Float.class);
        ret.add(double.class);
        ret.add(Double.class);
        ret.add(String.class);
        return ret;
    }

    public ContentValues getContentValues(IEntity ent) throws CampoNoMapeableException {

        ContentValues parametros = new ContentValues();
        for (Field f : ent.getClass().getDeclaredFields()) {
            try {
                if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                    continue;
                }
                if (ent.getIgnoredFields().contains(f.getName())) {
                    continue;
                }
                if (IEntity.class.isAssignableFrom(f.getType())) {

                    f.setAccessible(true);
                    parametros.put(f.getName().toUpperCase(), ((IEntity) f.get(ent)).getId());
                    f.setAccessible(false);

                } else if (ContentValueFactory.isWrapperType(f.getType())) {

                    putPrimitiveParameter(parametros, ent, f);
                } else {
                    throw new CampoNoMapeableException(f.getName());
                }
            } catch (IllegalAccessException e) {
                parametros.put(f.getName(), (String) null);
            }
        }
        return parametros;

    }

    private void putPrimitiveParameter(ContentValues parametros, Object o, Field f) throws IllegalAccessException {
        f.setAccessible(true);
        if (f.getType().equals(String.class)) {
            parametros.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, f.getName()), (String) f.get(o));
        } else if (f.getType().equals(Boolean.class) || f.getType().equals(boolean.class)) {
            parametros.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, f.getName()), (Boolean) f.get(o));
        } else if (f.getType().equals(Character.class) || f.getType().equals(char.class)) {
            parametros.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, f.getName()), (String) f.get(o));
        } else if (f.getType().equals(Byte.class) || f.getType().equals(byte.class)) {
            parametros.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, f.getName()), (Byte) f.get(o));
        } else if (f.getType().equals(Short.class) || f.getType().equals(short.class)) {
            parametros.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, f.getName()), (Short) f.get(o));
        } else if (f.getType().equals(Integer.class) || f.getType().equals(int.class)) {
            parametros.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, f.getName()), (Integer) f.get(o));
        } else if (f.getType().equals(Long.class) || f.getType().equals(long.class)) {
            parametros.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, f.getName()), (Long) f.get(o));
        } else if (f.getType().equals(Float.class) || f.getType().equals(float.class)) {
            parametros.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, f.getName()), (Float) f.get(o));
        } else if (f.getType().equals(Double.class) || f.getType().equals(double.class)) {
            parametros.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, f.getName()), (Double) f.get(o));
        }

        f.setAccessible(false);
    }
}
