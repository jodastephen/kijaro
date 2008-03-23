package sun.lang.property;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionPropertyAccessor extends PropertyAccessor {
    private final Method getter;
    private final Method setter;

    public ReflectionPropertyAccessor(Class<?> clazz, String name) {
	this.getter = ReflectionUtils.getGetter(clazz, name);
	getter.setAccessible(true);
        this.setter = ReflectionUtils.getSetter(clazz, name, getter.getReturnType());
        setter.setAccessible(true);
    }

    @Override
    public Class<?> getType() {
	return getter.getReturnType();
    }

    @Override
    public Object getValue(Object bean) {
	return call(getter, bean);
    }

    @Override
    public void setValue(Object bean,Object value) {
	call(setter, bean, value);
    }

    private static Object call(Method method, Object target, Object... values) {
	try {
	    return method.invoke(target, values);
	} catch(IllegalArgumentException e) {
	    throw new IllegalArgumentException(
		    "method " + method + " called with " + Arrays.toString(values));
	} catch(IllegalAccessException e) {
	    throw new AssertionError(e);
	} catch(InvocationTargetException e) {
	    Throwable cause=e.getCause();
	    if (cause instanceof RuntimeException) {
		throw (RuntimeException)cause;
	    }
	    if (cause instanceof Error) {
		throw (Error)cause;
	    }
	    throw new AssertionError(cause);
	}
    }
}
