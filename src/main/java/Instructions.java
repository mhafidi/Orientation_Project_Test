import coordinates.OrientationEnum;
import coordinates.Position2D;
import logs.Logger;
import utils.StringsUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 10/03/2017.
 */
public class Instructions
{

  protected Position2D position2D;
  protected String directions;
  String CLASS_NAME=this.getClass().getName();
  Logger logger= Logger.getInstance();
  static PrintStream PROMPT=null;

  Instructions()
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
    position2D = new Position2D(0,0, OrientationEnum.NORTH);
  }
  Instructions(Position2D aInPosition2D,String aInDirection)
  {
    if(PROMPT==null)
    {
      try
      {
        PROMPT = new PrintStream(new FileOutputStream("src\\main\\logs\\logserver"),true);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    position2D= new Position2D(aInPosition2D);
    directions = aInDirection;

  }

  public boolean setPosition2D(String aInPosition2DQuery)
  {
    if(StringsUtil.getInstance().isPosition2D(aInPosition2DQuery))
    {
      position2D.setPosition2D(new Position2D(aInPosition2DQuery));
      return true;
    }
    logger.logError(CLASS_NAME,"The Query: "+aInPosition2DQuery+" isn't a valid Position query",PROMPT);

    return false;
  }
  public boolean setDirections(String aInDirectionQuery)
  {
    if(StringsUtil.getInstance().isDirection(aInDirectionQuery))
    {
      directions= aInDirectionQuery;
      return true;
    }
    logger.logError(CLASS_NAME,"The Query: "+aInDirectionQuery+" isn't a valid Direction query",PROMPT);
    return false;

  }
  public Position2D getPosition2D()
  {
    return position2D;
  }

  public void setPosition2D(Position2D position2D)
  {
    this.position2D = position2D;
  }

  public String getDirections()
  {
    return directions;
  }


  public boolean equals(Instructions aInInstructions)
  {
    return aInInstructions!=null?
        (aInInstructions.position2D.equals(position2D)&&aInInstructions.getDirections().equals(directions)):false;
  }
  public String toString()
  {
    return position2D+" Directions:"+ directions;
  }

}
