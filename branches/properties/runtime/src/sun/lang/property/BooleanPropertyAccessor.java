package sun.lang.property;

public class BooleanPropertyAccessor extends PropertyAccessor {
    @Override
    public Class<?> getType() {
	return boolean.class;
    }

    @Override
    public Object getValue(Object bean) {
	return ((FakeBean)bean).get_boolean();
    }

    @Override
    public void setValue(Object bean, Object value) {
	((FakeBean)bean).set_boolean((Boolean)value);
    }
}
