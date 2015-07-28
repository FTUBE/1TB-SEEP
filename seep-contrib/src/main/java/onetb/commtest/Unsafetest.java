package onetb.commtest;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class Unsafetest {
public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
	Field f1 = Unsafe.class.getDeclaredField("theUnsafe"); //Internal reference  
	f1.setAccessible(true);  
	Unsafe unsafe = (Unsafe) f1.get(null);
	byte[] a = new byte[]{1,2,3,4,5};
	byte[] b = new byte[5];
	unsafe.copyMemory(a, 16, b,16,5);
	System.out.println(b[0]+" "+b[1]+" "+b[2]+" "+b[3]+" "+b[4]);
}
}
