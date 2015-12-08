package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetConnectionUtils {
	
	/*
	 * �ж�����״̬  
	 * @return ���糩ͨ����true,���粻ͨ����false
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
                    // ���糩ͨ  
                	return true;
                } else { 
                    // ���粻��ͨ  
                	return false;
                } 
            } 
        } catch (Exception e) { 
            System.out.println("���粻ͨ" + e.getLocalizedMessage());
        } 
        return false;
    } 
    
    public static void main(String[] args) {
    	System.out.println(isConnect());
	}

}
