package util;

import java.text.DecimalFormat;

public class DecimalFormatUtils {
	
	public static String format(double data,String format){
        DecimalFormat df = new DecimalFormat("#"+format);
        return df.format(data);
	}

}
