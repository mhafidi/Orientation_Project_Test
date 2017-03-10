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
  final private String CLASS_NAME=  this.getClass().getName();
  Logger logger = Logger.getInstance();

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
  public List<List<String>> convertListOfStringTabToListOfStringList(List<String[]> aInStringTabList)
  {
    return aInStringTabList.stream().map(t->convertStringTabToList(t)).collect(Collectors.toList());
  }

  static public double[] convertDoubleListToDoubleTab(List<Double> aInDoubleList)
  {
    double[] tab;
    if(aInDoubleList!=null)
    {
      tab = new double[aInDoubleList.size()];
      IntStream.range(0,aInDoubleList.size()).peek(index->tab[index]=aInDoubleList.get(index)).count();
    }
    else
    {

      tab = new double[0];
    }

    return tab;
  }
  public List<String> convertCharArrayToStringList( char[] aInCharArray)
  {
   return (List<String>) IntStream.range(0,aInCharArray.length).
        mapToObj(characterIndex-> Character.toString((char)aInCharArray[characterIndex])).collect(Collectors.toList());
  }
}
