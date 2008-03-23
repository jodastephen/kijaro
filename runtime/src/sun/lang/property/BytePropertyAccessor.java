package sun.lang.property;

public class BytePropertyAccessor extends PropertyAccessor {
    @Override
    public Class<?> getType() {
	return byte.class;
    }

    @Override
    public Object getValue(Object bean) {
	return ((FakeBean)bean).get_byte();
    }

    @Override
    public void setValue(Object bean, Object value) {
	((FakeBean)bean).set_byte((Byte)value);
    }
}
