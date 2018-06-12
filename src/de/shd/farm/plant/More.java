package de.shd.farm.plant;

import java.util.Random;

/**
 * The type More.
 *
 * @author ALB
 * @version 1.0
 * @since 15.03.2017
 */
public class More extends Plant
{
   /**
    * Instantiates a new More.
    */
   public More()
   {
      this(new Random().nextFloat() * 30);
   }

   /**
    * Instantiates a new More.
    *
    * @param height the height
    */
   public More(final float height)
   {
      setHeight(height);
      setHarvested(false);
   }
}
