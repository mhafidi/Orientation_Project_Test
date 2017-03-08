package coordinates;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 06/03/2017.
 */
public enum OrientationEnum
{
  NORTH("NORTH"),
  EAST("EAST"),
  SOUTH("SOUTH"),
  WEST("WEST");

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
