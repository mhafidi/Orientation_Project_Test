import coordinates.Position2D;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 06/03/2017.
 */
public interface MowerMachineInterface
{
  public void setPosition2D(Position2D aInPosition);
  public Position2D getCurrentPosition();
  public void move();
  public void rotateClockWise();
  public void rotateAntiClockWise();

}
