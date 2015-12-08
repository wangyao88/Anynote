package global.beanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import org.apache.commons.beanutils.ConvertUtils;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	static {
		ConvertUtils.register(new DateConverter(), Date.class);

		ConvertUtils.register(new NumberConverter(), Integer.class);

		ConvertUtils.register(new StringConverter(), String.class);
	}

	@SuppressWarnings("rawtypes")
	public static void populate(Object bean, Map properties)
			throws IllegalAccessException, InvocationTargetException {
		org.apache.commons.beanutils.BeanUtils.populate(bean, properties);
	}

	public static void setProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {
		org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
	}
}