import coordinates.Position2D;
import logs.Logger;
import utils.FileParser;

import java.io.PrintStream;
import java.util.*;
import java.util.stream.IntStream;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 06/03/2017.
 */

/*Yard class contains the limit of the yard where the mower machines will act
* I designed it like that for further improvement, it will contains also the occupied places, in other words,
* it will save a cache reference of MowerMachine (instances that are alive)*/
public class Yard
{
  private String yardName;
  protected Integer maxX, maxY;
  // this map contains alive loan mowers
  protected List<MowerMachine> cache = new ArrayList<>();
  // this map contains files of instructions to send
  protected Map<Integer, FileParser> mapOfInstructions = new HashMap<>();

  private Boolean debugFlag = false;
  private Logger logger = Logger.getInstance();
  private final String CLASS_NAME = this.getClass().getName();
  private final PrintStream PROMPT = System.out;

  public Yard(Integer aInMaxX, Integer aInMaxY,String aInYardName)
  {
    yardName = aInYardName;
    if (aInMaxX > 0 && aInMaxY > 0)
    {
      maxX = aInMaxX;
      maxY = aInMaxY;
    }
    else
    {
      logger.logError(CLASS_NAME,"Limit dimensions of yard must be greater than 0", PROMPT);
    }

  }

  public Integer getMaxX()
  {
    return maxX;
  }

  public void setMaxX(Integer aInmaxX)
  {
    maxX = aInmaxX;
  }

  public Integer getMaxY()
  {
    return maxY;
  }

  public void setMaxY(Integer aInmaxY)
  {
    maxY = aInmaxY;
  }

  public void addNewFile(String aInPath)
  {
    FileParser instructionFile = new FileParser(aInPath);

    if (instructionFile.filterValidInstructions())
    {
      logger.logInfo(CLASS_NAME, "There are " + instructionFile.getFileFilteredInstructions().size() + " filtered " +
          "blocks in the file:" + instructionFile.getFileName(), PROMPT);
      Integer index = mapOfInstructions.size();
      mapOfInstructions.put(index, instructionFile);
    }
  }

  public Boolean getDebugFlag()
  {
    return debugFlag;
  }

  public void setDebugFlag(Boolean debugFlag)
  {
    this.debugFlag = debugFlag;
  }

  //returns index of the mower if it exists else returns -1
  public Integer cacheCotainsMower(String aInPositionQuery)
  {
    for (MowerMachine mowerMachine :cache)
    {
      if(mowerMachine.getCurrentPosition().
          equals(new Position2D(aInPositionQuery)))
        return cache.indexOf(mowerMachine);
    }

    return -1;
  }

  public Boolean checkIfMowerParkedInAposition(Position2D aInPosition,Integer aInIndexCurrentMowerMoving)
  {
    return cache.stream().filter(mowerMachine -> cache.indexOf(mowerMachine)!=aInIndexCurrentMowerMoving)
        .filter(mowerMachine1 -> mowerMachine1.getCurrentPosition().equals(aInPosition)).findAny().isPresent();
  }



  public Position2D processOfShiftingProcedures(List<String> shiftingProcedures,Position2D aInPosition2D,
                                                Integer aInMowerIndex)
  {
    Position2D stopPosition = new Position2D(aInPosition2D);
    Position2D temporaryPosition = new Position2D(aInPosition2D);
    shiftingProcedures.remove(0);

    String procedureRotationChunk;
    for(String procedure: shiftingProcedures)
    {
      while(procedure.length()>0)
      {
        procedureRotationChunk = procedure.substring(0, procedure.indexOf("A"));
        procedure = procedure.substring(procedure.indexOf("A"), procedure.length());
        if(procedureRotationChunk.length()>0)
        {
          temporaryPosition.shiftingPositionFromQuery(procedureRotationChunk);
          stopPosition.setPosition2D(temporaryPosition);
        }
        if(procedure.indexOf("A")==0)
        {
          temporaryPosition.translateThroughDirection();
          if(checkIfMowerParkedInAposition(temporaryPosition,aInMowerIndex))
          {
            logger.logInfo(CLASS_NAME,"This mower Stopped in the following position "+ stopPosition+" another" +
                " mower is parked in position "+ temporaryPosition,PROMPT);
            return stopPosition;
          }
          else if(temporaryPosition.checkOutOfALimitedSpace(maxX,maxY,0,0))
          {
            logger.logInfo(CLASS_NAME,"This mower Stopped in the following position "+ stopPosition+
                " because it is going out of yard ",PROMPT);
            return stopPosition;
          }
          else
          {
            procedure=procedure.substring(procedure.indexOf("A")+1,procedure.length());
            stopPosition.setPosition2D(temporaryPosition);
          }
        }
      }



    }

    return stopPosition;
  }


  public void executeInstructions()
  {
    logger.logInfo(CLASS_NAME,
        "Checking the existence of Mowers in the entered positions before proceeding to execution", PROMPT);

    logger.logInfo(CLASS_NAME,"There are "+cache.size()+" Mowers in that field already",PROMPT);

    if(debugFlag)
    {
      cache.stream()
          .peek(mowerMachine -> logger.logDebug(CLASS_NAME,String.valueOf(mowerMachine.getCurrentPosition()),PROMPT)).count();
    }
    for (FileParser fileParser : mapOfInstructions.values())
    {
      Map instructionPiles = fileParser.getFileFilteredInstructions();
      for(Object instructionPile : instructionPiles.values())
      {
        String positionQuery=(String)((List) instructionPile).get(0);
        Integer mowerCacheIndex =cacheCotainsMower(positionQuery);
        if(mowerCacheIndex>0)
        {

          Position2D mowerPosition = cache.get(mowerCacheIndex).getCurrentPosition();
          logger.logInfo(CLASS_NAME,"A Mower Machine already parked in the position "+ mowerPosition,PROMPT);
          mowerPosition = processOfShiftingProcedures((List) instructionPile,mowerPosition,mowerCacheIndex);
          MowerMachine mowerMachine = new MowerMachine("Mower:"+mowerCacheIndex);
          mowerMachine.setPosition2D(mowerPosition);
          cache.remove(mowerCacheIndex);
          cache.add(mowerMachine);
          logger.logInfo(CLASS_NAME,"The cache was successfully " +
              " updated for the Mower: "+ mowerMachine.getMowerMachineName()+ " in the Yard: "+
              yardName,PROMPT);


        }
        else
        {
          MowerMachine mowerMachine = new MowerMachine("Mower:"+cache.size());
          mowerMachine.setPosition2D(new Position2D(positionQuery));
          logger.logInfo(CLASS_NAME,"A New Mower was created in the position: "+
              mowerMachine.getCurrentPosition(),PROMPT);
          Position2D mowerPosition =processOfShiftingProcedures((List) instructionPile,
              mowerMachine.getCurrentPosition(),mowerCacheIndex);
          mowerMachine.setPosition2D(mowerPosition);
          cache.add(mowerMachine);
          logger.logInfo(CLASS_NAME,"The cache was successfully " +
              "updated for the Mower: "+ mowerMachine.getMowerMachineName()+ "in the Yard: "+
              yardName,PROMPT);
        }

      }
    }

  }

  public void printCurrentCache()
  {
    cache.stream().peek(mowerMachine -> logger.logInfo(CLASS_NAME,mowerMachine.getMowerMachineName()+":"+
        String.valueOf( mowerMachine.getCurrentPosition()),PROMPT)).count();
  }

}
