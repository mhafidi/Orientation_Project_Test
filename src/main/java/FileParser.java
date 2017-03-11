import coordinates.Position2D;
import logs.Logger;
import utils.StringsUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PackageName utils
 * Created by mhafidi on 07/03/2017.
 */
public class FileParser
{
  private FileReader fileReader;
  private String fileName;
  private Logger logger = Logger.getInstance();
  private final String CALSS_NAME = this.getClass().toString();
  PrintStream PROMPT;
  private List<Instructions> instructionsList;

  public FileParser(String aInFile) throws FileNotFoundException
  {
    try
    {
      PROMPT = new PrintStream(new FileOutputStream("src\\main\\logs\\logserver",true),true);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    try
    {
      fileReader = new FileReader(aInFile);
      instructionsList = new ArrayList<>();
      File file = new File(aInFile);
      fileName = file.getName();
    }
    catch (FileNotFoundException e)
    {
      logger.logError(CALSS_NAME, e.getMessage(), PROMPT);
      throw e;
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

  public String getFileName()
  {
    return fileName;
  }
  public boolean filterValidInstructions()
  {
    Integer index=0;
    boolean check=false; //the first filtered instruction must be coordinates and not directions (for index==0)


    for (String lString:getFileLines())
    {
      if(StringsUtil.getInstance().isPosition2D(lString))
      {

        if(check)
        {
          index++;
        }
        Instructions instructions = new Instructions(new Position2D(lString),"");
        check =true;
        instructionsList.add(index,instructions);
      }
      else if(StringsUtil.getInstance().isDirection(lString) && check)
      {
        Instructions linsInstructions= instructionsList.get(index);
        linsInstructions.setDirections(linsInstructions.getDirections()+lString);
        instructionsList.set(index,linsInstructions);
      }
    }
    return !instructionsList.isEmpty();
  }


  public List<Instructions> getInstructionsList()
  {
    return instructionsList;
  }
}
