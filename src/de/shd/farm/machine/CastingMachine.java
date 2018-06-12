package de.shd.farm.machine;

import java.util.List;
import java.util.Random;

import de.shd.farm.plant.FieldPlant;
import de.shd.farm.plant.More;
import de.shd.farm.plant.Plant;
import de.shd.farm.plant.Wheat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The casting machine gives water to all the plants so they grow.
 *
 * @author ALB
 * @version 1.0
 * @since 30.03.2017
 */
public class CastingMachine implements Machine
{
   private static final Logger LOG = LogManager.getLogger(CastingMachine.class);

   /**
    * Overrides the start method from the {@link Machine} interface.
    * <p>
    * {@inheritDoc}
    */
   @Override
   public List<Plant> work(final List<Plant> plantList)
   {
      return water(plantList);
   }

   /**
    * Water all the plants so they can grow.
    *
    * @param plantList the previous plants
    * @return the complete field after the casting
    */
   private List<Plant> water(final List<Plant> plantList)
   {
      for( final Plant plant : plantList )
      {
         final float heightBefore = plant.getHeight();

         if( plant instanceof More )
         {
            plant.setHeight(heightBefore + (new Random().nextInt(20) * FieldPlant.MORE.getGrowthAccelerator()));
         }
         else if( plant instanceof Wheat )
         {
            plant.setHeight(heightBefore + (new Random().nextInt(20) * FieldPlant.WHEAT.getGrowthAccelerator()));
         }
         else
         {
            LOG.warn("wrong plant type to process");
         }
      }

      LOG.info(this.getClass().getSimpleName() + " just did it work");

      return plantList;
   }
}
