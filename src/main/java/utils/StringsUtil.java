package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

/**
 * PackageName utils
 * Created by mhafidi on 07/03/2017.
 */
public class StringsUtil
{

  private final static String[] ORIENTATIONS = {"N","E","W","S"};
  private final static String[] DIRECTIONS = {"D","A","G"};
   static public List<String> convertStringLineToCharList(String aInLine)
   {
     List<String> stringList = new ArrayList<>();
     String[] tokens = aInLine.trim().split("(?! ^ )");
     stringList = Containers.getInstance().convertStringTabToList(tokens);
     return stringList;
   }

  static public boolean isInteger(String aInString)
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

  static  private boolean isAnOrientation(String aInString)
  {
    return IntStream.range(0,ORIENTATIONS.length).filter(index->ORIENTATIONS[index].
        equals(aInString)).findAny().isPresent();

  }
  static private boolean isDirection(String aInString)
  {
    return IntStream.range(0,DIRECTIONS.length).filter(index->DIRECTIONS[index].
        equals(aInString)).findAny().isPresent();

  }
  static public boolean coordinatesValidator(String aInStringLine)
  {
    if(aInStringLine!=null)
    {
      String delims = "[\\s]+"; //pattern based on space to decompose line into coordinates
      String[] tokens = aInStringLine.split(delims);
      if(tokens.length==3)
      {
        return (StringsUtil.isInteger(tokens[0]) && StringsUtil.isInteger(tokens[1])
            && StringsUtil.isAnOrientation(tokens[2]));
      }

    }
    return false;
  }

  //returns false if one character isn't a direction char
  static public boolean directionsValidator(String aInStringLine)
  {
    return !Containers.getInstance().convertCharArrayToStringList(aInStringLine.toCharArray()).stream()
        .filter(str-> !Containers.getInstance().convertStringTabToList(DIRECTIONS).contains(str)).findAny().isPresent();
  }
}
