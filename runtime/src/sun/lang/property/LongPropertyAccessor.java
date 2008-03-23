package sun.lang.property;

public class LongPropertyAccessor extends PropertyAccessor {
    @Override
    public Class<?> getType() {
	return long.class;
    }

    @Override
    public Object getValue(Object bean) {
	return ((FakeBean)bean).get_long();
    }

    @Override
    public void setValue(Object bean, Object value) {
	((FakeBean)bean).set_long((Long)value);
    }
}
