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
public class HarvestingMachineTest
{
   private final List<Plant> plantList = new ArrayList<>();
   private final HarvestingMachine machine = new HarvestingMachine();

   @Before
   public void setUp() throws Exception
   {
      this.plantList.add(new More(12.3456F));
      this.plantList.add(new Wheat(178.9012F));
   }

   @After
   public void tearDown() throws Exception
   {
   }

   @Test
   public void checkWork() throws Exception
   {
      final List<Plant> plantListAfterWork = this.machine.work(this.plantList);

      Assert.assertTrue("The second plant must be harvested TRUE!", plantListAfterWork.get(1).isHarvested());
   }

}
