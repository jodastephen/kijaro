package sun.lang.property;

abstract class FakeBean {
  abstract Void get();
  abstract void set(Void value);
    
  abstract boolean get_boolean();
  abstract void set_boolean(boolean value);
  
  abstract byte get_byte();
  abstract void set_byte(byte value);
  
  abstract char get_char();
  abstract void set_char(char value);
    
  abstract short get_short();
  abstract void set_short(short value);
  
  abstract int get_int();
  abstract void set_int(int value);
  
  abstract long get_long();
  abstract void set_long(long value);
  
  abstract float get_float();
  abstract void set_float(float value);
  
  abstract double get_double();
  abstract void set_double(double value);
}
