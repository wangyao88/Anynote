package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetConnectionUtils {
	
	/*
	 * ÅĞ¶ÏÍøÂç×´Ì¬  
	 * @return ÍøÂç³©Í¨·µ»Øtrue,ÍøÂç²»Í¨·µ»Øfalse
	 * @author wangyao
	 * 2015-08-13
	 */
    public static boolean isConnect() { 
        Runtime runtime = Runtime.getRuntime(); 
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try { 
            Process process = runtime.exec("ping " + "www.baidu.com"); 
            is = process.getInputStream(); 
            isr = new InputStreamReader(is); 
            br = new BufferedReader(isr); 
            String line = null; 
            StringBuffer sb = new StringBuffer(); 
            while ((line = br.readLine()) != null) { 
                sb.append(line); 
            } 
            is.close(); 
            isr.close(); 
            br.close(); 
            if (null != sb && !sb.toString().equals("")) { 
                if (sb.toString().indexOf("TTL") > 0) { 
                    // ÍøÂç³©Í¨  
                	return true;
                } else { 
                    // ÍøÂç²»³©Í¨  
                	return false;
                } 
            } 
        } catch (Exception e) { 
            System.out.println("ÍøÂç²»Í¨" + e.getLocalizedMessage());
        } 
        return false;
    } 
    
    public static void main(String[] args) {
    	System.out.println(isConnect());
	}

}
