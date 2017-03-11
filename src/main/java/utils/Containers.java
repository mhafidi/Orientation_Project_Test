package utils;


import logs.Logger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by mhafidi on 07/03/2017.
 */
public class Containers
{
  private static Containers instance=null;

  private Containers()
  {

  }

  static public Containers getInstance()
  {
    return instance==null? new Containers():instance;
  }

  public List<String> convertStringTabToList(String[]aInStringTab)
  {
    return
        IntStream.range(0,aInStringTab.length).mapToObj(index->aInStringTab[index]).collect(Collectors.toList());
  }


  public List<String> convertCharArrayToStringList( char[] aInCharArray)
  {
   return (List<String>) IntStream.range(0,aInCharArray.length).
        mapToObj(characterIndex-> Character.toString((char)aInCharArray[characterIndex])).collect(Collectors.toList());
  }
}
