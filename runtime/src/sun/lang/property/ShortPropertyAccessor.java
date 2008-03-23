package sun.lang.property;

public class ShortPropertyAccessor extends PropertyAccessor {
    @Override
    public Class<?> getType() {
	return short.class;
    }

    @Override
    public Object getValue(Object bean) {
	return ((FakeBean)bean).get_short();
    }

    @Override
    public void setValue(Object bean, Object value) {
	((FakeBean)bean).set_short((Short)value);
    }
}
