import java.util.HashMap;
import java.util.Map;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 06/03/2017.
 */

/*singleton which contains the limit of the yard where the mower machines will act
* I designed it like that for further improvement, it will contains also the occupied places, in other words,
* it will save a cache reference of MowerMachine instances that are alive*/
public class Yard
{
  protected Integer MaxX,MaxY;
  protected Map<Integer,MowerMachine> cache= new HashMap<>();
  static Yard instance;
  private Yard()
  {

  }
  static Yard getInstance()
  {
    return instance==null?new Yard():instance;
  }

  public Integer getMaxX()
  {
    return MaxX;
  }

  public void setMaxX(Integer maxX)
  {
    MaxX = maxX;
  }

  public Integer getMaxY()
  {
    return MaxY;
  }

  public void setMaxY(Integer maxY)
  {
    MaxY = maxY;
  }
}
