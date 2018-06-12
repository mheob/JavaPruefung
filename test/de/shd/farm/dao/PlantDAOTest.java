package de.shd.farm.dao;

import de.shd.farm.plant.FieldPlant;
import de.shd.farm.plant.More;
import de.shd.farm.plant.Wheat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ALB
 * @version 1.0
 * @since 24.03.2017
 */
public class PlantDAOTest
{
   private final PlantDAO dao = new PlantDAO();

   @Before
   public void setUp() throws Exception
   {
   }

   @After
   public void tearDown() throws Exception
   {
   }

   @Test
   public void checkInitPlantList() throws Exception
   {
      Assert.assertTrue(
            "The return must be greater than 0, but lower or equals than 50 (for more only).",
            this.dao.initPlantList(FieldPlant.MORE).size() > 0 && this.dao.initPlantList(FieldPlant.MORE).size() < 50
      );
      Assert.assertFalse(
            "The return must not between 0 and 50 (for more only).",
            this.dao.initPlantList(FieldPlant.MORE).size() < 0 || this.dao.initPlantList(FieldPlant.MORE).size() > 50
      );

      Assert.assertTrue(
            "The return must be greater than 0, but lower or equals than 50 (for wheat only).",
            this.dao.initPlantList(FieldPlant.WHEAT).size() > 0 && this.dao.initPlantList(FieldPlant.WHEAT).size() < 50
      );
      Assert.assertFalse(
            "The return must not between 0 and 50 (for wheat only).",
            this.dao.initPlantList(FieldPlant.WHEAT).size() < 0 || this.dao.initPlantList(FieldPlant.WHEAT).size() > 50
      );

      Assert.assertTrue("The plant must be of the more type", this.dao.initPlantList(FieldPlant.MORE).get(0) instanceof More);
      Assert.assertTrue("The plant must be of the wheat type", this.dao.initPlantList(FieldPlant.WHEAT).get(0) instanceof Wheat);

      Assert.assertFalse("The plant must not be of the more type", this.dao.initPlantList(FieldPlant.MORE).get(0) instanceof Wheat);
      Assert.assertFalse("The plant must not be of the wheat type", this.dao.initPlantList(FieldPlant.WHEAT).get(0) instanceof More);
   }
}
