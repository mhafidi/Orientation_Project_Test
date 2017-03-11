import coordinates.Position2D;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 06/03/2017.
 */
public interface MowerMachineInterface
{

  void setNewPosition(Position2D aInNewPosition);
  Position2D getCurrentPosition();

}
