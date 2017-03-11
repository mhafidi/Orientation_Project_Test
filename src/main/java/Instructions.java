import coordinates.Position2D;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 10/03/2017.
 */
public class Instructions
{

  Position2D position2D;
  String directions;

  Instructions(Position2D aInPosition2D,String aInDirection)
  {
    position2D.setPosition2D(aInPosition2D);
    directions = aInDirection;
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

  public void setDirections(String directions)
  {
    this.directions = directions;
  }
}
