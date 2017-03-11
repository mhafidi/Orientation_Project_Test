import coordinates.Position2D;
import logs.Logger;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 06/03/2017.
 */

/*YardMowers class contains the limit of the yard where the mower machines will act
* I designed it like that for further improvement, it will contains also the occupied places, in other words,
* it will save a cache reference of MowerMachine (instances that are alive)*/
public class YardMowers
{
  private String yardName;
  protected Integer maxX, maxY;
  // this map contains alive lawn mowers
  protected List<MowerMachine> cache = new ArrayList<>();
  // list of instructions to execute they can come from several Interfaces : file, console or other service
  private List<Instructions> instructionList;

  private Boolean debugFlag = false;
  private Logger logger = Logger.getInstance();
  private final String CLASS_NAME = this.getClass().getName();
  private final PrintStream PROMPT = System.out;

  public YardMowers(Integer aInMaxX, Integer aInMaxY, String aInYardName)
  {
    yardName = aInYardName;
    if (aInMaxX > 0 && aInMaxY > 0)
    {
      maxX = aInMaxX;
      maxY = aInMaxY;
    }
    else
    {
      logger.logError(CLASS_NAME, "Limit dimensions of yard must be greater than 0", PROMPT);
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

  public boolean addNewFileInstruction(String aInPath)
  {

    FileParser instructionFile = null;
    try
    {
      instructionFile = new FileParser(aInPath);


      if (instructionFile.filterValidInstructions())
      {
        instructionList = instructionFile.getInstructionsList();
        if(debugFlag)
        {
          logger.logDebug(CLASS_NAME, "There are " + instructionList.size() + " filtered " +
              "blocks in the file:" + instructionFile.getFileName(), PROMPT);
        }
        return true;
      }
    }

    catch (FileNotFoundException e)
    {
      logger.logError(CLASS_NAME, e.getMessage(), PROMPT);
      return false;
    }

    return false;


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
  public Integer cacheCotainsMower(Position2D aInPosition2D)
  {
    for (MowerMachine mowerMachine : cache)
    {
      if (mowerMachine.getCurrentPosition().
          equals(aInPosition2D))
        return cache.indexOf(mowerMachine);
    }

    return -1;
  }

  public Boolean checkIfMowerParkedInAposition(Position2D aInPosition, Integer aInIndexCurrentMowerMoving)
  {
    return cache.stream().filter(mowerMachine -> cache.indexOf(mowerMachine) != aInIndexCurrentMowerMoving)
        .filter(mowerMachine1 -> mowerMachine1.getCurrentPosition().equals(aInPosition)).findAny().isPresent();
  }

  public Position2D processOfShiftingProcedures(String shiftingProcedures, Position2D aInCurrentPosition2D,
                                                 Integer aInMowerIndex)
  {
    Position2D stopPosition = new Position2D(aInCurrentPosition2D);
    Position2D temporaryPosition = new Position2D(aInCurrentPosition2D);
    //shiftingProcedures.remove(0);

    String procedureRotationChunk;

    while (shiftingProcedures.length() > 0)
    {
      procedureRotationChunk = shiftingProcedures.substring(0, shiftingProcedures.indexOf("A"));
      shiftingProcedures = shiftingProcedures.substring(shiftingProcedures.indexOf("A"), shiftingProcedures.length());
      if (procedureRotationChunk.length() > 0)
      {
        temporaryPosition.shiftingPositionFromQuery(procedureRotationChunk);
        stopPosition.setPosition2D(temporaryPosition);
      }
      if (shiftingProcedures.indexOf("A") == 0)
      {
        temporaryPosition.translateThroughDirection();
        if (checkIfMowerParkedInAposition(temporaryPosition, aInMowerIndex))
        {
          logger.logInfo(CLASS_NAME, "This mower Stopped in the following position " + stopPosition + " another" +
              " mower is parked in position " + temporaryPosition, PROMPT);
          return stopPosition;
        }
        else if (temporaryPosition.checkOutOfALimitedSpace(maxX, maxY, 0, 0))
        {
          logger.logInfo(CLASS_NAME, "This mower Stopped in the following position " + stopPosition +
              " because it is going out of yard ", PROMPT);
          return stopPosition;
        }
        else
        {
          shiftingProcedures = shiftingProcedures.substring(shiftingProcedures.indexOf("A") + 1, shiftingProcedures.length());
          stopPosition.setPosition2D(temporaryPosition);
        }
      }
    }
    return stopPosition;
  }

  public void executeInstructions()
  {
    if (!instructionList.isEmpty())
    {
      logger.logInfo(CLASS_NAME,
          "Checking the existence of Mowers in the entered positions before proceeding to execution", PROMPT);

      logger.logInfo(CLASS_NAME, "There are " + cache.size() + " Mowers in that field already", PROMPT);

      if (debugFlag)
      {
        cache.stream()
            .peek(mowerMachine -> logger.logDebug(CLASS_NAME, String.valueOf(mowerMachine.getCurrentPosition()), PROMPT)).count();
      }
      instructionList.stream().peek(instruct->executeOneBlockInstructions(instruct)).count();

      instructionList.clear(); // make sure to clean the instructions list in order not to play the same again
    }

    else
    {
      logger.logInfo(CLASS_NAME, "There are No instructions to Execute", PROMPT);
    }
  }
  public boolean addInstruction( Instructions aInInstruction)
  {
    if(!instructionList.contains(aInInstruction))
    {
      instructionList.add(aInInstruction);
      return true;
    }
    logger.logWarning(CLASS_NAME,"This Instruction: "+ aInInstruction+" already exist",PROMPT);
    return false;
  }
  public void printCurrentCache()
  {
    cache.stream().peek(mowerMachine -> logger.logInfo(CLASS_NAME, mowerMachine.getMowerMachineName() + ":" +
        String.valueOf(mowerMachine.getCurrentPosition()), PROMPT)).count();
  }

  public Position2D executeOneBlockInstructions(Instructions aInInstructions)
  {
    Position2D position2D = aInInstructions.getPosition2D();
    Integer mowerCacheIndex = cacheCotainsMower(position2D);
    Position2D lastPosition = new Position2D(position2D);
    if (mowerCacheIndex > 0)
    {

      Position2D mowerPosition = cache.get(mowerCacheIndex).getCurrentPosition();
      logger.logInfo(CLASS_NAME, "A Mower Machine already parked in the position " + mowerPosition, PROMPT);
      mowerPosition = processOfShiftingProcedures(aInInstructions.getDirections(), mowerPosition, mowerCacheIndex);
      MowerMachine mowerMachine = new MowerMachine("Mower:-" + mowerCacheIndex, mowerPosition);
      mowerMachine.setNewPosition(mowerPosition);
      cache.remove(mowerCacheIndex);
      cache.add(mowerMachine);
      logger.logInfo(CLASS_NAME, " The cache was successfully " +
          " updated for the Mower: " + mowerMachine.getMowerMachineName() + " in the YardMowers: " +
          yardName, PROMPT);
      lastPosition.setPosition2D(mowerMachine.getCurrentPosition());
    }
    else
    {
      MowerMachine mowerMachine = new MowerMachine(this.yardName + ":Mower:-" + cache.size(), new Position2D(position2D));
      logger.logInfo(CLASS_NAME, "A New Mower was created in the position: " +
          mowerMachine.getCurrentPosition(), PROMPT);
      Position2D mowerPosition = processOfShiftingProcedures(aInInstructions.getDirections(),
          mowerMachine.getCurrentPosition(), mowerCacheIndex);
      mowerMachine.setNewPosition(mowerPosition);
      cache.add(mowerMachine);
      lastPosition.setPosition2D(mowerMachine.getCurrentPosition());
      logger.logInfo(CLASS_NAME, "The cache was successfully " +
          "updated for the Mower: " + mowerMachine.getMowerMachineName() + " in the YardMowers: " +
          yardName, PROMPT);
    }
    return lastPosition;
  }

  public List<MowerMachine> getCache()
  {
    return cache;
  }
}
