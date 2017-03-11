import coordinates.Position2D;
import logs.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * PackageName PACKAGE_NAME
 * Created by mhafidi on 06/03/2017.
 */

public class TestFunctionalities
{

  String CLASS_NAME= this.getClass().getName();
  Logger logger = Logger.getInstance();
  @Test
  public void instructionsTest()
  {

    Instructions instructions = new Instructions();
    Assert.assertFalse(instructions.setPosition2D("5 5"));
    Assert.assertTrue(instructions.setPosition2D("1 2 N"));
    Assert.assertTrue(instructions.setDirections("GAGAGAGAA"));
    Assert.assertFalse(instructions.setDirections("GZAARZJ3R"));
    logger.logTestSucceeded(CLASS_NAME,"Test Of  Instructions Class");
  }
  @Test
  public void testFileParser()
  {

    try
    {

      FileParser fileParser = new FileParser("src\\test\\testFilesToRead\\testF.csv");
      Assert.assertTrue(fileParser.filterValidInstructions());
      Assert.assertEquals(2,fileParser.getInstructionsList().size());
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    logger.logTestSucceeded(CLASS_NAME,"Test Of FileParser 1 Class");
  }
  @Test
  public void testFileParserWIthUnreadable()
  {
    try
    {

      FileParser fileParser = new FileParser("src\\test\\testFilesToRead\\testE.csv");
      Assert.assertTrue(fileParser.filterValidInstructions());
      Assert.assertEquals(2,fileParser.getInstructionsList().size());
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    logger.logTestSucceeded(CLASS_NAME,"Test Of FileParser 2 Class");
  }
  @Test
  public void testMowerMovesInYardWithFile()
  {

    YardMowers yardMowers = new YardMowers(100,100,0,0,"Yard:1");
    if(yardMowers.addNewFileInstruction("src\\test\\testFilesToRead\\testF.csv"))
    {
      yardMowers.executeInstructions();
    }
    Position2D position2D1 = new Position2D("1 3 N");
    Position2D position2D2 = new Position2D("5 1 E");
    Assert.assertEquals(yardMowers.getCache().stream().filter(mowerMachine ->
        mowerMachine.getCurrentPosition().equals(position2D1)||mowerMachine.getCurrentPosition().equals(position2D2))
        .count(),2);
    logger.logTestSucceeded(CLASS_NAME,"Test Of YardMowers  Class with File");

  }

  @Test
  public void testMowerMovesWithInstructionInterface()
  {

    logger.logTestSucceeded(CLASS_NAME,"YardMowers  Class with Instruction Interface");
    Instructions instructions = new Instructions();
    Assert.assertFalse(instructions.setPosition2D("5 5"));
    Assert.assertTrue(instructions.setPosition2D("1 2 N"));
    Assert.assertTrue(instructions.setDirections("GAGAGAGAA"));
    YardMowers yardMowers = new YardMowers(100,100,-2,-3,"Yard:1");
    Position2D position2D= yardMowers.executeOneBlockInstructions(instructions);
    Assert.assertTrue(position2D.equals(new Position2D("1 3 N")));
    Assert.assertTrue(instructions.setPosition2D("3 3 E"));
    Assert.assertTrue(instructions.setDirections("AADAADADDA"));
    position2D= yardMowers.executeOneBlockInstructions(instructions);
    Assert.assertTrue(position2D.equals(new Position2D("5 1 E")));
  }

  @Test

 public void testCollision()
  {
    logger.logTestSucceeded(CLASS_NAME,"YardMowers  Class in case of collision");
    YardMowers yardMowers = new YardMowers(100,100,-2,-4,"Yard:1");
    Instructions instructions = new Instructions();
    Assert.assertFalse(instructions.setPosition2D("5 5"));
    Assert.assertTrue(instructions.setPosition2D("1 2 N"));
    Assert.assertTrue(instructions.setDirections("GAGAGAGAA"));
    Position2D position2D= yardMowers.executeOneBlockInstructions(instructions);
    Assert.assertTrue(position2D.equals(new Position2D("1 3 N")));
    Assert.assertTrue(instructions.setPosition2D("3 3 E"));
    Assert.assertTrue(instructions.setDirections("AADAADADDA"));
    position2D= yardMowers.executeOneBlockInstructions(instructions);
    Assert.assertTrue(position2D.equals(new Position2D("5 1 E")));
    Assert.assertTrue(instructions.setPosition2D("1 2 N"));
    Assert.assertTrue(instructions.setDirections("GAGAGAGAA"));
    position2D= yardMowers.executeOneBlockInstructions(instructions);
    Assert.assertFalse(position2D.equals(new Position2D("1 3 N")));

  }

}
