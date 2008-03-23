package sun.lang.property;

public class IntPropertyAccessor extends PropertyAccessor {
    @Override
    public Class<?> getType() {
	return int.class;
    }

    @Override
    public Object getValue(Object bean) {
	return ((FakeBean)bean).get_int();
    }

    @Override
    public void setValue(Object bean, Object value) {
	((FakeBean)bean).set_int((Integer)value);
    }
}
