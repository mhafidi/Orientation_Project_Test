import coordinates.Position2D;
import logs.Logger;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 06/03/2017.
 */
public class MowerMachine implements MowerMachineInterface
{
  protected String mowerMachineName;
  protected Position2D position2D;
  private Logger logger =Logger.getInstance();
  private final String CLASS_NAME=this.getClass().toString();
  private final PrintStream PROMPT = System.out;

  public static List<String> MowerLanguage = new ArrayList<String>();

  {
    MowerLanguage.add("A");
    MowerLanguage.add("D");
    MowerLanguage.add("G");
  }

  List<String> unmodifiableOrientationMap = Collections.unmodifiableList(MowerLanguage);

  MowerMachine(String aInMowerMachineName)
  {
    mowerMachineName = aInMowerMachineName;
  }

  private void setPosition2D(Position2D aInPosition)
  {
    if(logger!=null)
    {
      logger.logInfo(CLASS_NAME, "The previous position is set to " + aInPosition, PROMPT);
    }
    position2D.setX(aInPosition.getX());
    position2D.setY(aInPosition.getY());
    position2D.setOrientation(aInPosition.getOrientation());
    if(logger!=null)
    {
      logger.logInfo(CLASS_NAME, "The Current position is set to "+ aInPosition+ " [Succeeded]",PROMPT);
    }
  }
  public Position2D getCurrentPosition()
  {
    return position2D;
  }

  private void move()
  {
    position2D.translateThroughDirection();
  }

  private void rotateClockWise()
  {
    position2D.rotateClockWise();
  }

  private void rotateAntiClockWise()
  {
    position2D.rotateAntiClockWise();
  }

  public boolean queryTreatment(String aInQuery)
  {
    return false;
  }
}
