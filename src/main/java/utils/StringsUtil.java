package utils;

import coordinates.OrientationEnum;
import logs.Logger;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * PackageName utils
 * Created by mhafidi on 07/03/2017.
 */
public class StringsUtil
{

  private final  String[] ORIENTATIONS = {"N","E","W","S"};
  private final  String[] DIRECTIONS = {"D","A","G"};


   private static StringsUtil instance;
   private StringsUtil()
   {

   }
  public static StringsUtil getInstance()
  {
    return instance==null?new StringsUtil():instance;
  }

   public boolean isInteger(String aInString)
  {
    try
    {
      Integer.parseInt(aInString);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

    private  boolean isAnOrientation(String aInString)
  {
    return IntStream.range(0,ORIENTATIONS.length).filter(index->ORIENTATIONS[index].
        equals(aInString)).findAny().isPresent();

  }

   public boolean isPosition2D(String aInStringLine)
  {
    if(aInStringLine!=null)
    {
      String delims = "[\\s]+"; //pattern based on space to decompose line into coordinates
      String[] tokens = aInStringLine.split(delims);
      if(tokens.length==3)
      {
        return (isInteger(tokens[0]) && isInteger(tokens[1])
            && isAnOrientation(tokens[2]));
      }

    }
    return false;
  }



  public   OrientationEnum getOrientation(String aInToken)
  {

    switch (aInToken)
    {
      case "N":
        return OrientationEnum.NORTH;
      case "E":
        return OrientationEnum.EAST;
      case "W":
        return OrientationEnum.WEST;
      case "S":
        return OrientationEnum.SOUTH;
    }
    return null;
  }

  //returns false if one character isn't a direction char
   public boolean isDirection(String aInStringLine)
  {
    return !Containers.getInstance().convertCharArrayToStringList(aInStringLine.toCharArray()).stream()
        .filter(str-> !Containers.getInstance().convertStringTabToList(DIRECTIONS).contains(str)).findAny().isPresent();
  }
}
