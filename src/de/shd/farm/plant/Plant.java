package de.shd.farm.plant;

/**
 * The type Plant.
 *
 * @author ALB
 * @version 1.0
 * @since 15.03.2017
 */
public class Plant
{
   private int id;
   private float height;
   private boolean harvested;

   /**
    * Getter for property 'id'.
    *
    * @return Value for property 'id'.
    */
   public int getId()
   {
      return this.id;
   }

   /**
    * Setter for property 'id'.
    *
    * @param id Value to set for property 'id'.
    */
   public void setId(final int id)
   {
      this.id = id;
   }

   /**
    * Getter for property 'height'.
    *
    * @return Value for property 'height'.
    */
   public float getHeight()
   {
      return this.height;
   }

   /**
    * Setter for property 'height'.
    *
    * @param height Value to set for property 'height'.
    */
   public void setHeight(final float height)
   {
      this.height = height;
   }

   /**
    * Getter for property 'harvested'.
    *
    * @return Value for property 'harvested'.
    */
   public boolean isHarvested()
   {
      return this.harvested;
   }

   /**
    * Setter for property 'harvested'.
    *
    * @param harvested Value to set for property 'harvested'.
    */
   public void setHarvested(final boolean harvested)
   {
      this.harvested = harvested;
   }

   /**
    * Overrides the overall method <c>toString</c> for the plants (for debugging only).
    * <p>
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return "type: " + this.getClass().getSimpleName() + ", height: " + getHeight() + " cm, isHarvested " + isHarvested();
   }
}
