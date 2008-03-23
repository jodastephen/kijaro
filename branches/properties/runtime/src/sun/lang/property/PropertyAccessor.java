package sun.lang.property;

public abstract class PropertyAccessor {
    public abstract Class<?> getType();
    
    public abstract Object getValue(Object bean);
    
    public abstract void setValue(Object bean,Object value);
}
