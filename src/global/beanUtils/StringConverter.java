package global.beanUtils;

import org.apache.commons.beanutils.Converter;
import util.StringUtils;

public class StringConverter
  implements Converter
{
  public Object convert(Class type, Object value)
  {
    if (type.equals(String.class)) {
      if ((value != null) && (StringUtils.isNotEmpty(value.toString()))) {
        return value.toString();
      }
      return null;
    }

    return null;
  }
}