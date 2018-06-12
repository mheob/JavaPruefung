package de.shd.farm.plant;

import org.jetbrains.annotations.Contract;

/**
 * The enum Field plant.
 *
 * @author ALB
 * @version 1.0
 * @since 15.03.2017
 */
public enum FieldPlant
{
   /**
    * The field plant MORE.
    */
   MORE(1.2F),
   /**
    * The field plant WHEAT.
    */
   WHEAT(0.8F);

   private final float growthAccelerator;

   /**
    * Initialize the field plants.
    *
    * @param growthAccelerator the accelerator for different growing
    */
   FieldPlant(final float growthAccelerator)
   {
      this.growthAccelerator = growthAccelerator;
   }

   /**
    * Getter for property 'growthAccelerator'.
    *
    * @return Value for property 'growthAccelerator'.
    */
   @Contract(pure = true)
   public float getGrowthAccelerator()
   {
      return this.growthAccelerator;
   }
}
