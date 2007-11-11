package java.lang;

import java.lang.reflect.InvocationTargetException;

public abstract class Property<B,T> {
    private final String name;
    private final Class<T> type;
    private final Class<B> declaringType;
    private final int flags = 0;
    
    /**
     * name of the property.
     */
    public String getName() {
      return name;
    }
    
    /**
     * type of the property.
     */
    public Class<T> getType() {
      return type;
    }
    
    /**
     * the declaring type of the property.
     */
    public Class<B> getDeclaringType() {
      return declaringType;
    }
    
    public boolean getBound() {
	return (flags & BOUND) != 0;
    }
    
    public boolean hasGetter() {
	return (flags & SETTER_ONLY) == 0;
    }
    
    public boolean hasSetter() {
	return (flags & GETTER_ONLY) == 0;
    }
    
    private static final int BOUND = 1<<0;
    private static final int GETTER_ONLY = 1<<1;
    private static final int SETTER_ONLY = 1<<2;
    
    /**
     * Create a property with a name its type and its declaring type.
     * @param name the name of the property.
     * @param type the type of the property.
     * @param declaringType the declaring class of the property.
     */
    /*protected*/public Property(String name, Class<T> type, Class<B> declaringType) {
	this.name = name;
	this.type = type;
	this.declaringType = declaringType;
    }

    public abstract T get(B bean);

    public abstract void set(B bean, T value);
    
    /**
     * @param bean
     * @throws ClassCastException if bean is not an instance of B.
     */
    @SuppressWarnings("unchecked")
    public T rawGet(Object bean) {
	return get((B)bean);
    }

    /**
     * @param bean
     * @param value
     * @throws ClassCastException if bean (resp. value) id not instance of B (resp. T)
     */
    @SuppressWarnings("unchecked")
    public void rawSet(Object bean, Object value) {
	set((B)bean, (T)value);
    }

    @Override
    public String toString() {
	return "property " + type.getName() +" "+ name +" in " + declaringType;
    }

    public static <B> Property<B,?> getProperty(Class<B> type, String name) throws NoSuchPropertyException {
	//FIXME Remi gloups, linear scan !!
	for(Property<B,?> property : getProperties(type)) {
	    if (property.name.equals(name))
		return property;
	}
	throw new NoSuchPropertyException("no property " + name + " defined in " + type);
    }

    @SuppressWarnings("unchecked")
    public static <B> Property<B,?>[] getProperties(Class<B> type) {
	try {
	    return (Property<B,?>[])type.getMethod("properties", (Class<?>[])null).
	    invoke(null,(Object[])null);
	} catch (IllegalAccessException e) {
	    // user made properties ??
	} catch (InvocationTargetException e) {
	    // user made properties ??
	} catch (NoSuchMethodException e) {
	    // user made properties ??
	}
	return (Property<B,?>[]) EMPTY_PROPERTIES;
    }

    private static final Property<?,?>[] EMPTY_PROPERTIES =
	new Property<?,?>[0];
}