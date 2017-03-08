package utils;

/**
 * PackageName utils
 * Created by mhafidi on 07/03/2017.
 */
public class Maths
{

  public static Integer posiveModulo(Integer aInTargetInteger,Integer aInModulo)
  {
    Integer result= aInTargetInteger %(aInModulo);
    if(result>=0)
      return result;
    while(result<0)
    {
      result =result+aInModulo;
    }
    return result;
  }
}
