import coordinates.Position2D;
import logs.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
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
  private static PrintStream PROMPT=null;
  MowerMachine(String aInMowerMachineName, Position2D aInPosition2D)
  {
    if(PROMPT==null)
    {
      try
      {
        PROMPT = new PrintStream(new FileOutputStream("src\\main\\logs\\logserver",true),true);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    mowerMachineName = aInMowerMachineName;
    position2D = new Position2D(aInPosition2D);

  }

  public String getMowerMachineName()
  {
    return mowerMachineName;
  }

  public void setNewPosition(Position2D aInNewPosition)
  {


    position2D.setX(aInNewPosition.getX());
    position2D.setY(aInNewPosition.getY());
    position2D.setOrientation(aInNewPosition.getOrientation());

    logger.logInfo(CLASS_NAME, "The Mower "+mowerMachineName
        +" position was successfully set to: "+position2D,PROMPT);

  }
  public Position2D getCurrentPosition()
  {
    return position2D;
  }

}
