package global.beanUtils;

import java.util.Date;
import org.apache.commons.beanutils.Converter;
import util.DateUtils;
import util.StringUtils;

public class DateConverter
  implements Converter
{
  public Object convert(Class type, Object value)
  {
    if (type.equals(Date.class)) {
      if ((value != null) && (StringUtils.isNotEmpty(value.toString()))) {
        return DateUtils.formatStr2Date(value.toString(), "yyyy-MM-dd");
      }
      return value;
    }

    return null;
  }
}