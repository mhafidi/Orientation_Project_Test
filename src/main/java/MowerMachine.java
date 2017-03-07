import coordinates.Position2D;
import logs.Logger;

import java.io.PrintStream;

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


  public void setPosition2D(Position2D aInPosition)
  {
    logger.logInfo(CLASS_NAME, "The position is set to "+ aInPosition,PROMPT);
    position2D.setX(aInPosition.getX());
    position2D.setY(aInPosition.getY());
    position2D.setOrientation(aInPosition.getOrientation());
  }

  public Position2D getCurrentPosition()
  {
    return position2D;
  }

  public void move()
  {
    position2D.translateThroughDirection();
  }

  public void rotateClockWise()
  {
    position2D.rotateClockWise();
  }

  public void rotateAntiClockWise()
  {
    position2D.rotateAntiClockWise();
  }
}
