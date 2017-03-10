package utils;

import coordinates.OrientationEnum;
import coordinates.Position2D;
import logs.Logger;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * PackageName utils
 * Created by mhafidi on 07/03/2017.
 */
public class StringsUtil
{

  private final  String[] ORIENTATIONS = {"N","E","W","S"};
  private final  String[] DIRECTIONS = {"D","A","G"};
  private  final OutputStream PROMPT = System.out ;
  Logger logger = Logger.getInstance();
  private final String CLASS_NAME=this.getClass().getName();

   private static StringsUtil instance;
   private StringsUtil()
   {

   }
  public static StringsUtil getInstance()
  {
    return instance==null?new StringsUtil():instance;
  }
    public List<String> convertStringLineToCharList(String aInLine)
   {
     List<String> stringList = new ArrayList<>();
     String[] tokens = aInLine.trim().split("(?! ^ )");
     stringList = Containers.getInstance().convertStringTabToList(tokens);
     return stringList;
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
   private boolean isDirection(String aInString)
  {
    return IntStream.range(0,DIRECTIONS.length).filter(index->DIRECTIONS[index].
        equals(aInString)).findAny().isPresent();

  }
   public boolean coordinatesValidator(String aInStringLine)
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

   public Position2D computeCoordinateFromQuery(String aInQuery)
  {
    if(coordinatesValidator(aInQuery))
    {
      String delims = "[\\s]+"; //pattern based on space to decompose line into coordinates
      String[] tokens = aInQuery.split(delims);
      return new Position2D(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),getOrientation(tokens[2]) );
    }
    logger.logError(CLASS_NAME,"Issue Occured while Parsing the following Coordinates Query: "+aInQuery,PROMPT);
    return null;
  }

  public String getOnlyFirstRotations(String aInQuery,String aInRemainQuery)
  {
    aInRemainQuery = aInQuery.substring(aInQuery.indexOf("A"),aInQuery.length());
    return aInQuery.substring(0,aInQuery.indexOf("A"));
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
   public boolean directionsValidator(String aInStringLine)
  {
    return !Containers.getInstance().convertCharArrayToStringList(aInStringLine.toCharArray()).stream()
        .filter(str-> !Containers.getInstance().convertStringTabToList(DIRECTIONS).contains(str)).findAny().isPresent();
  }
}
