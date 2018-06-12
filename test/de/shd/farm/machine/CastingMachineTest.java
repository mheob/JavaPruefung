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
public class CastingMachineTest
{
   private final List<Plant> plantList = new ArrayList<>();
   private final CastingMachine machine = new CastingMachine();

   @Before
   public void setUp() throws Exception
   {
      this.plantList.add(new More(12.3456F));
      this.plantList.add(new Wheat(78.9012F));
   }

   @After
   public void tearDown() throws Exception
   {
   }

   @Test
   public void checkWork() throws Exception
   {
      final float plantBefore_1 = this.plantList.get(0).getHeight();
      final float plantBefore_2 = this.plantList.get(1).getHeight();

      final List<Plant> plantListAfterWork = this.machine.work(this.plantList);

      final float plantAfter_1 = plantListAfterWork.get(0).getHeight();
      final float plantAfter_2 = plantListAfterWork.get(1).getHeight();

      Assert.assertTrue("The plant 1 must be higher after pouring.", plantBefore_1 < plantAfter_1);
      Assert.assertTrue("The plant 2 must be higher after pouring.", plantBefore_2 < plantAfter_2);

      Assert.assertFalse("The plant 1 must not be small or equals after pouring.", plantBefore_1 >= plantAfter_1);
      Assert.assertFalse("The plant 2 must not be small or equals after pouring.", plantBefore_2 >= plantAfter_2);
   }
}
