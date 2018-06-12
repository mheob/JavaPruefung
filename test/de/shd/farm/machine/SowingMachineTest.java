package de.shd.farm.machine;

import java.util.ArrayList;
import java.util.List;

import de.shd.farm.plant.More;
import de.shd.farm.plant.Plant;
import de.shd.farm.plant.Wheat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ALB
 * @version 1.0
 * @since 30.03.2017
 */
public class SowingMachineTest
{
   private final List<Plant> plantList = new ArrayList<>();
   private final SowingMachine machine = new SowingMachine();

   @Before
   public void setUp() throws Exception
   {
      for( int i = 0; i < 47; i++ )
      {
         this.plantList.add(new More());
      }

      for( int i = 0; i < 45; i++ )
      {
         this.plantList.add(new Wheat());
      }
   }

   @After
   public void tearDown() throws Exception
   {
   }

   @Test
   public void work() throws Exception
   {
      final int countBeforeWork = this.plantList.size();
      final List<Plant> plantListAfterWork = this.machine.work(this.plantList);

      Assert.assertEquals("There must be exactly 8 plant added!", 8, plantListAfterWork.size() - countBeforeWork);
   }
}
