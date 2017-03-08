import coordinates.Position2D;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 06/03/2017.
 */
public interface MowerMachineInterface
{

  public Position2D getCurrentPosition();
  public boolean queryTreatment(String aInQuery);

}
