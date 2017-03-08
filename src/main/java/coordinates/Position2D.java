package coordinates;

import utils.Maths;
import utils.StringsUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 06/03/2017.
 */
public class Position2D
{
  protected Integer x;
  protected Integer y;
  protected OrientationEnum orientation;


  Position2D(Position2D aInPosition2D)
  {
    this.x =aInPosition2D.getX();
    this.y =aInPosition2D.getY();
    this.orientation = aInPosition2D.getOrientation();
  }
   Map<Integer,OrientationEnum> realMap = new HashMap<Integer,OrientationEnum>();

  {
    realMap.put(0,OrientationEnum.NORTH);
    realMap.put(1,OrientationEnum.EAST);
    realMap.put(2,OrientationEnum.SOUTH);
    realMap.put(3,OrientationEnum.WEST);
  }

  Map<Integer, OrientationEnum> unmodifiableOrientationMap = Collections.unmodifiableMap(realMap);

  public Integer getX()
  {
    return x;
  }

  public void setX(Integer x)
  {
    this.x = x;
  }

  public Integer getY()
  {
    return y;
  }

  public void setY(Integer y)
  {
    this.y = y;
  }

  public OrientationEnum getOrientation()
  {
    return orientation;
  }

  public void setOrientation(OrientationEnum orientation)
  {
    this.orientation = orientation;

  }

  public void rotateClockWise()
  {
    Integer index= IntStream.range(0,unmodifiableOrientationMap.size()).
        filter(id->unmodifiableOrientationMap.get(id)==orientation).findFirst().getAsInt();

    orientation =unmodifiableOrientationMap.get(Math.abs((index+1) %(unmodifiableOrientationMap.size())));

  }
  public void rotateAntiClockWise()
  {
    Integer index= IntStream.range(0,unmodifiableOrientationMap.size()).
        filter(id->unmodifiableOrientationMap.get(id)==orientation).findFirst().getAsInt();

    orientation =unmodifiableOrientationMap.get(Maths.posiveModulo((index - 1) , (unmodifiableOrientationMap.size())));

  }

   public void translateThroughDirection()
   {
     switch (orientation)
     {
       case NORTH:
       {
         y++;
         break;
       }
       case SOUTH:
       {
         y--;
         break;
       }
       case EAST:
       {
         x++;
         break;
       }
       case WEST:
       {
         x--;
         break;
       }
     }
   }

  public boolean equals(Position2D aInPosition2D)
  {
    return (this.x==aInPosition2D.getX() && this.y==aInPosition2D.y);
  }
  public Position2D(Integer x, Integer y, OrientationEnum orientation)
  {

    this.x = x;
    this.y = y;
    this.orientation = orientation;
  }

  public String toString()
  {
    return "Coordinates ("+x+","+y+")"+" and orientation: "+orientation;
  }
}
