package java.lang;


import sun.lang.property.DaVinciPropertyAccessors;
import sun.lang.property.PropertyAccessor;
import sun.lang.property.ReflectionPropertyAccessor;

public final class Property<B> {
  private final String name;
  private final Class<B> declaringClass;
  private final PropertyAccessor accessor;
  
  Property(Class<B> declaringClass,String name,PropertyAccessor accessor) {
      this.declaringClass=declaringClass;
      this.name=name;
      this.accessor=accessor;
  }
  
  public String getName() {
      return name;
  }
  
  public Class<B> getDeclaringClass() {
      return declaringClass;
  }
  
  public Class<?> getType() {
      return accessor.getType();
  }
  
  public Object getValue(B bean) {
      return accessor.getValue(bean);
  }
  
  public void setValue(B bean,Object value) {
      accessor.setValue(bean, value);
  }
  
  @Override
  public String toString() {
      return getType() + " " + getDeclaringClass().getName() + '.' + name;
  }
  
  public static <B> Property<B> create(Class<B> clazz, String name) {
      PropertyAccessor accessor;
      if (isDaVinciVM)
	  accessor=DaVinciPropertyAccessors.createDaVinciProperty(clazz, name);
      else
	  accessor=new ReflectionPropertyAccessor(clazz, name);
      return new Property<B>(clazz, name, accessor);
  }
  
  private static final boolean isDaVinciVM;
  static {
      boolean daVinciVM;
      try {
	  DaVinciPropertyAccessors.createDaVinciProperty(Thread.class, "name");
	  daVinciVM = true;
      } catch (UnsatisfiedLinkError e) {
	  daVinciVM = false;
      }
      isDaVinciVM = daVinciVM;
  }
}