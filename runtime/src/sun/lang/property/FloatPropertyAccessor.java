package sun.lang.property;

public class FloatPropertyAccessor extends PropertyAccessor {
    @Override
    public Class<?> getType() {
	return float.class;
    }

    @Override
    public Object getValue(Object bean) {
	return ((FakeBean)bean).get_float();
    }

    @Override
    public void setValue(Object bean, Object value) {
	((FakeBean)bean).set_float((Float)value);
    }
}
