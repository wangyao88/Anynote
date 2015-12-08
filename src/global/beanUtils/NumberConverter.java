package global.beanUtils;

import org.apache.commons.beanutils.Converter;
import util.StringUtils;

public class NumberConverter
  implements Converter
{
  public Object convert(Class type, Object value)
  {
    if (type.equals(Integer.class)) {
      if ((value != null) && (StringUtils.isNotEmpty(value.toString()))) {
        return Integer.valueOf(value.toString());
      }
      return null;
    }

    return null;
  }
}