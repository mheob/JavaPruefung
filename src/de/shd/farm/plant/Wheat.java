package de.shd.farm.plant;

import java.util.Random;

/**
 * The type Wheat.
 *
 * @author ALB
 * @version 1.0
 * @since 15.03.2017
 */
public class Wheat extends Plant
{
   /**
    * Instantiates a new Wheat.
    */
   public Wheat()
   {
      this(new Random().nextFloat() * 30);
   }

   /**
    * Instantiates a new Wheat.
    *
    * @param height the height
    */
   public Wheat(final float height)
   {
      setHeight(height);
      setHarvested(false);
   }
}
