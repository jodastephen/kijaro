package sun.lang.property;

public class CharPropertyAccessor extends PropertyAccessor {
    @Override
    public Class<?> getType() {
	return char.class;
    }

    @Override
    public Object getValue(Object bean) {
	return ((FakeBean)bean).get_char();
    }

    @Override
    public void setValue(Object bean, Object value) {
	((FakeBean)bean).set_char((Character)value);
    }
}
