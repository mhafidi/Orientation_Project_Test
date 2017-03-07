package coordinates;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 06/03/2017.
 */
public enum OrientationEnum
{
  NORTH("N"),
  EAST("E"),
  SOUTH("S"),
  WEST("W");

  private String name;

  OrientationEnum(String aInName)
  {
    name= aInName;
  }


 @Override
  public String toString()
 {
   return name;
 }
}
