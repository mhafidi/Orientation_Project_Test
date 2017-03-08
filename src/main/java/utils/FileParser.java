package utils;

import logs.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PackageName utils
 * Created by mhafidi on 07/03/2017.
 */
public class FileParser
{
  FileReader fileReader;
  private Logger logger = Logger.getInstance();
  private final String CALSS_NAME = this.getClass().toString();
  PrintStream PROMPT = System.out;
  private Map<Integer,List<String>> filteredInstructions;

  public FileParser(String aInFile)
  {
    try
    {
      fileReader = new FileReader(aInFile);
      filteredInstructions= new HashMap<>();
    }
    catch (FileNotFoundException e)
    {
      logger.logError(CALSS_NAME, e.getMessage(), PROMPT);
    }

  }

  public List<String> getFileLines()
  {
    List<String> fileLines = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(fileReader))
    {
      try
      {
        for (String line; (line = br.readLine()) != null; )
        {
          fileLines.add(line);
        }
      }
      catch (IOException e)
      {
        logger.logError(CALSS_NAME, e.getMessage(), PROMPT);
      }
    }
    catch (IOException e)
    {
      logger.logError(CALSS_NAME, e.getMessage(), PROMPT);
    }

    return fileLines;
  }

  public boolean filterValidInstructions()
  {
    Integer index=0;
    boolean check=false; //the first filtered instruction must be coordinates and not directions

    for (String lString:getFileLines())
    {
      if(StringsUtil.coordinatesValidator(lString))
      {
        if(check)
        {
          index++;
        }
        List list= new ArrayList<>();
        list.add(lString);
        check =true;
        filteredInstructions.put(index,list);
      }
      else if(StringsUtil.directionsValidator(lString) && check)
      {
        List l= filteredInstructions.get(index);
        l.add(lString);
        filteredInstructions.put(index,l);
      }
    }
    return !filteredInstructions.isEmpty();
  }

}
