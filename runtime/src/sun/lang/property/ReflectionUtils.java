package sun.lang.property;

import java.lang.reflect.Method;

public class ReflectionUtils {
    private ReflectionUtils() {
	// enforce static helper class 
    }
    
    public static Method getGetter(Class<?> clazz, String name) {
	try {
	    return clazz.getMethod(capitalize("get", name), (Class<?>[])null);
	} catch(NoSuchMethodException e) {
	    try {
		return clazz.getMethod(capitalize("is", name), (Class<?>[])null);
	    } catch(NoSuchMethodException e2) {
	        throw new IllegalArgumentException("no getter for property " + name +
	            " in class "+clazz.getName());
	    }
	}
    }
    
    public static Method getSetter(Class<?> clazz, String name, Class<?> type) {
	try {
	    return clazz.getMethod(capitalize("set", name), type);
	} catch(NoSuchMethodException e) {
	    throw new IllegalArgumentException("no setter for property " + name +
	            " in class "+clazz.getName());
	}
    }

    public static String capitalize(String prefix,String name) {
	return prefix + Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
