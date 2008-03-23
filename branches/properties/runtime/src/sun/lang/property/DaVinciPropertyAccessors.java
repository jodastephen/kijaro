package sun.lang.property;

import java.dyn.AnonymousClassLoader;
import java.dyn.AnonymousClassLoader.Constant;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.IdentityHashMap;

public class DaVinciPropertyAccessors {
    private DaVinciPropertyAccessors(){
	// do nothing
    }
    
    private static final IdentityHashMap<Class<?>, Object[]> templateMap;
    static {
        IdentityHashMap<Class<?>, Object[]> map=
  	  new IdentityHashMap<Class<?>, Object[]>();
        map.put(boolean.class, new Object[]{BooleanPropertyAccessor.class, "get_boolean", "set_boolean"});
        map.put(byte.class, new Object[]{BooleanPropertyAccessor.class, "get_byte", "set_byte"});
        map.put(char.class, new Object[]{BooleanPropertyAccessor.class, "get_char", "set_char"});
        map.put(short.class, new Object[]{BooleanPropertyAccessor.class, "get_short", "set_short"});
        map.put(int.class, new Object[]{IntPropertyAccessor.class, "get_int", "set_int"});  
        map.put(long.class, new Object[]{BooleanPropertyAccessor.class, "get_long", "set_long"});
        map.put(float.class, new Object[]{BooleanPropertyAccessor.class, "get_float", "set_float"});
        map.put(double.class, new Object[]{BooleanPropertyAccessor.class, "get_double", "set_double"});
        
        templateMap=map;
    }
    
    public static PropertyAccessor createDaVinciProperty(Class<?> beanClass, String name) {
        Method getter = ReflectionUtils.getGetter(beanClass, name);
        Class<?> type = getter.getReturnType();
        Method setter = ReflectionUtils.getSetter(beanClass, name, type);
        
        AnonymousClassLoader loader = new AnonymousClassLoader(beanClass);
        
        HashMap<Object,Object> patchMap=new HashMap<Object,Object>();
        Class<?> accessorType;
        String templateGetterName;
        String templateSetterName;
        
        Object[] templateObject = templateMap.get(type);
        if (templateObject==null) {
            accessorType = ObjectPropertyAccessor.class;
            templateGetterName = "get";
            templateSetterName = "set";
            patchMap.put(Constant.forClass("java.lang.Void"), type);
            patchMap.put(Constant.forUtf8("()Ljava/lang/Void;"), "()L"+type.getName().replace('.','/')+';');
            patchMap.put(Constant.forUtf8("(Ljava/lang/Void;)V"), "(L"+type.getName().replace('.','/')+";)V");
        } else {
            accessorType = (Class<?>)templateObject[0];
            templateGetterName =  templateObject[1].toString();
            templateSetterName =  templateObject[2].toString();
        }
        
        try {
            loader.setClassFile(accessorType);
        } catch (IOException e) {
            throw new AssertionError(e);  
        }
        
        patchMap.put(Constant.forClass("sun.lang.property.FakeBean"), beanClass);
        patchMap.put(Constant.forUtf8(templateGetterName), getter.getName());
        patchMap.put(Constant.forUtf8(templateSetterName), setter.getName());

        /*
            String beanClassName=beanClass.getName();
            patchMap.put(Constant.forMethod("sun.lang.property.FakeBean", "get_int", "()I"),
                Constant.forMethod(beanClassName, getter.getName(), "()I"));
            patchMap.put(Constant.forMethod("sun.lang.property.FakeBean", "set_int", "(I)V"),
                Constant.forMethod(beanClassName, setter.getName(), "(I)V"));
         */
        loader.setPatchMap(patchMap);
        try {
            return (PropertyAccessor)loader.loadClass().newInstance();
        } catch (InstantiationException e) {
            throw new AssertionError(e);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }
}
