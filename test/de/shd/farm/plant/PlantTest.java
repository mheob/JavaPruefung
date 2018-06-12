package de.shd.farm.plant;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ALB
 * @version 1.0
 * @since 03.04.2017
 */
public class PlantTest
{
   More defaultMore = new More();
   Wheat defaultWheat = new Wheat();

   @Before
   public void setUp() throws Exception
   {
   }

   @After
   public void tearDown() throws Exception
   {
   }

   @Test
   public void checkGetter() throws Exception
   {
      final More more = new More(12.3456F);
      final Wheat wheat = new Wheat(78.9012F);

      more.setId(5);
      wheat.setId(6);

      Assert.assertEquals("The ID of type 1 must be exactly 5!", 5, more.getId());
      Assert.assertEquals("The ID of type 2 must be exactly 6!", 6, wheat.getId());

      Assert.assertTrue("The HEIGHT of type 1 must be exactly 12.3456!", 12.3456F == more.getHeight());
      Assert.assertTrue("The HEIGHT of type 2 must be exactly 78.9012!", 78.9012F == wheat.getHeight());

      Assert.assertEquals("The HARVESTED of type 1 must be exactly false!", false, more.isHarvested());
      Assert.assertEquals("The HARVESTED of type 2 must be exactly false!", false, wheat.isHarvested());

      Assert.assertNotNull("The debugging info of \"toString\" can not be \"null\"!", more.toString());
      Assert.assertNotNull("The debugging info of \"toString\" can not be \"null\"!", wheat.toString());
   }
}
