package de.shd.farm.plant;

import java.util.ArrayList;
import java.util.List;

import de.shd.farm.machine.CastingMachine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ALB
 * @version 1.0
 * @since 03.04.2017
 */
public class FieldPlantTest
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
   public void getGrowthAccelerator() throws Exception
   {
      final List<Plant> plantListAfterWork = this.machine.work(this.plantList);
   }
}
