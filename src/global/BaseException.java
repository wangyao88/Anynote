package global;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BaseException extends Exception
{
  private static final long serialVersionUID = -3791243372909483919L;

  public BaseException()
  {
  }

  public BaseException(Throwable cause)
  {
    super(cause);
  }

  public static String getExceptionStackTrace(Throwable cause)
  {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    cause.printStackTrace(pw);
    return sw.toString();
  }
}