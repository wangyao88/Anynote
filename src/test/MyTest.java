package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

public class MyTest {
	
	@Test
	public void weekTest(){
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_WEEK);
		System.out.println(day);
	}
	
	@Test
	public void substringTest(){
		String date = "2015-09";
		System.out.println(date.substring(0, 4));
		System.out.println(date.substring(5, date.length()));
	}
	
	@Test
	public void doubleTest(){
		BigDecimal d = BigDecimal.valueOf(1.0);
	}
	
	@Test
	public void log(){
		double a = 2;
		if(Math.log(a-1)/Math.log(2) + Math.log(a)/Math.log(2) == 1){
			System.out.println(a);
		}
	}
	
	@Test
	public void lengthTest(){
		int a = "121".length();
		System.out.println(a);
		
	}
	
	@Test
	public void objectTest(){
		List<Person> list = new ArrayList<Person>();
		Person p = null;
		for(int i = 0; i < 5; i ++){
		    p = new Person();
			p.setAge(i);
			p.setName("name" + i);
			list.add(p);
		}
		System.out.println(list.size());
//		list = null;
		System.out.println(list.get(4) == p);
		System.out.println(p.getName());
	}
	
	@Test
	public void StrTest(){
		List list = new ArrayList();
		String a1 = new String ("123");
		String a2 = "123";
		System.out.println(a1.intern() == a2);
	}
	
	@Test
	public void StringBufferTest(){
//		final StringBuffer sb = new StringBuffer();
//		sb.append("1213213");
//		System.out.println(sb.toString());
		
		int q = 11,p=3;
		System.out.println(q/p);
	}

	@Test
	public void buff(){
		int pp=5,p = 5,g = 5;
		int p_q=2,g_q=1;
		while(true){
			if(p >= 2 || g >= 4){
				if(g >= 4){
					pp = pp + g/4;
					g = g%4 + g/4;
					p = p + g/4;
				}
				if(p >= 2){
					pp = pp + p/2;
					p = p%2 + p/2;
					g = g + p/2;
				}
				System.out.println(p + "--" + g);
			}else{
				break;
			}
		}
		System.out.println(pp);
		
	}
	
}
