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
    Yard yard = new Yard(10,10,"yard1");
    yard.addNewFile("C:\\Users\\mhafidi\\Documents\\Projects\\Orientation_Project_Test\\src\\test\\testFilesToRead\\testF.csv");
    yard.executeInstructions();
    yard.printCurrentCache();



  }
}
