package test;

import java.util.Arrays;

public class DaVinciPrimitivePerf {
    
  public static class Bean {
      private int x;
      public int getX() {
	return x;
      }
      public void setX(int x) {
	this.x = x;
      }
      
      public static final Property<Bean> X=Property.create(Bean.class, "x");
  }
  
  private static void testSet(Bean bean, int... values) {
      for(int i=0;i<1000000;i++) {
	  for(int value:values) {
	    Bean.X.setValue(bean, value);
	    if (value != bean.getX())
		throw new AssertionError();
	  }
      }
  }
  
  private static void testGet(Bean bean, int... values) {
      for(int i=0;i<1000000;i++) {
	  for(int value:values) {
	    bean.setX(value);
	    if (value != (Integer)Bean.X.getValue(bean))
		throw new AssertionError();
	  }
      }
  }
  
  private static long mean(long[] array) {
      long mean=0;
      for(int i=2;i<array.length;i++)
	  mean+=array[i];
      return mean/(array.length-2);
  }
  
  public static void main(String[] args) {
      Bean bean=new Bean();
      int[] values=new int[]{12, 34, 101, 256};
      
      long[] times=new long[30];
      System.out.println("test get");
      for(int i=0;i<times.length;i++) {
	  long time=System.nanoTime();
	  testGet(bean,values);
	  long time2=System.nanoTime();
	  times[i]=time2-time;
      }
      System.out.println("mean "+mean(times));
      
      System.out.println("test set");
      for(int i=0;i<times.length;i++) {
	  long time=System.nanoTime();
	  testSet(bean,values);
	  long time2=System.nanoTime();
	  times[i]=time2-time;
      }
      System.out.println("mean "+mean(times));
  }
}
