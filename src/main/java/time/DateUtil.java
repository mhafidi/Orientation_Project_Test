package time;


import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mhafidi on 07/03/2017
 * @author mhafidi
 */
public class DateUtil
{
  private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");



  public static String getStartDate()
  {

    return Calendar.getInstance().getTime().toString();
  }


  private static String getDateAsString(Date date)
  {
    try
    {
      return sdf.format(date);
    } catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
