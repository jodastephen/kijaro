package sun.lang.property;

public class DoublePropertyAccessor extends PropertyAccessor {
    @Override
    public Class<?> getType() {
	return double.class;
    }

    @Override
    public Object getValue(Object bean) {
	return ((FakeBean)bean).get_double();
    }

    @Override
    public void setValue(Object bean, Object value) {
	((FakeBean)bean).set_double((Double)value);
    }
}
