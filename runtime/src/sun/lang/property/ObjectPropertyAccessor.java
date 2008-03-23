package sun.lang.property;

public class ObjectPropertyAccessor extends PropertyAccessor {
    @Override
    public Class<?> getType() {
	return Void.class;
    }

    @Override
    public Object getValue(Object bean) {
	return ((FakeBean)bean).get();
    }

    @Override
    public void setValue(Object bean, Object value) {
	((FakeBean)bean).set((Void)value);
    }
}
