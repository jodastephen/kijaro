package test;

public class DaVinciPerf {
    
  public static class Bean {
      private String title;
      public String getTitle() {
	  return title;
      }
      public void setTitle(String title) {
	  this.title = title;
      }
      
      public static final Property<Bean> TITLE=Property.create(Bean.class, "title");
  }
  
  private static void testSet(Bean bean, String... values) {
      for(int i=0;i<1000000;i++) {
	  for(String value:values) {
	    Bean.TITLE.setValue(bean, value);
	    if (value != bean.getTitle())
		throw new AssertionError();
	  }
      }
  }
  
  private static void testGet(Bean bean, String... values) {
      for(int i=0;i<1000000;i++) {
	  for(String value:values) {
	    bean.setTitle(value);
	    if (value != Bean.TITLE.getValue(bean))
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
      
      String[] values=new String[]{"hello", "da", "vinci", "property"};
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
