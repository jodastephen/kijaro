
public class Test2 {
    public property String test;
    
    public static void main(String[] args) {
        //System.out.println(test());
        System.out.println(Test2#test);

        //System.out.println(Test2#test);
        /*
         Test2 test2 = new Test2();
         test().set(test2, "hello");
         System.out.println(test().get(test2));

         for(Property<Test2, ?> p:Test2.properties()) {
             System.out.println(p+" = "+p.get(test2));
         }*/
    }
}
