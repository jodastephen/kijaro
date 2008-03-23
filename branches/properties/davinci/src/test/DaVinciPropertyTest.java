package test;

public class DaVinciPropertyTest {
  private int x;
  public int getX() {
    return x;
  }
  public void setX(int x) {
    this.x = x;
  }
  
  private String text;
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
    
  public static void main(String[] args) {
      DaVinciPropertyTest test=new DaVinciPropertyTest();
      Property<DaVinciPropertyTest> property=Property.create(DaVinciPropertyTest.class, "x");
      
      property.setValue(test, 0xCAFEBABE);
      System.out.printf(property+" %x\n",property.getValue(test));
      
      Property<DaVinciPropertyTest> property2=Property.create(DaVinciPropertyTest.class, "text");
      property2.setValue(test, "hello property");
      System.out.println(test.getText());
  }
}
