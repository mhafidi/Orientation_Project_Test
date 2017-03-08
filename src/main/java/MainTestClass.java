import coordinates.OrientationEnum;
import coordinates.Position2D;
import utils.Containers;
import utils.FileParser;
import utils.StringsUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 06/03/2017.
 */
public class MainTestClass
{

  public static void main(String[]args)
  {
    Position2D position2D = new Position2D(0,0,OrientationEnum.NORTH);
    System.out.println(position2D);
    position2D.rotateClockWise();
    position2D.rotateAntiClockWise();
    position2D.rotateAntiClockWise();
    System.out.println(position2D);
    position2D.translateThroughDirection();
    System.out.println(position2D);
    String testLine = "Coordinates (-1,0) and orientation: WEST";
    //testLine = testLine.replaceAll("\\s+","");
    String delims = "[\\s]+";
    String[] tokens = testLine.split(delims);
    String alpha= "23";
    System.out.println(StringsUtil.isInteger(alpha));
    Containers.getInstance().convertStringTabToList(tokens).stream().forEach(str-> System.out.println(str));
    //StringsUtil.convertStringLineToCharList(testLine).stream().forEach(str-> System.out.println(str));
    try
    {
      FileParser fileParser = new FileParser("C:\\Users\\mhafidi\\Documents\\Projects\\Orientation_Project_Test\\src\\test\\testFilesToRead\\testF.csv");

      Pattern p = Pattern.compile("\\d\\d");
      System.out.println(fileParser.filterValidInstructions());
      //Matcher m = p.matcher(lStringEx);
    }
    catch (Exception e)
    {
      System.out.printf(e.getMessage());
    }



  }
}
