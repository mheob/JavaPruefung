package de.shd.farm.machine;

import java.util.List;

import de.shd.farm.plant.Plant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The harvester harvests all plants which are larger than 100 cm.
 *
 * @author ALB
 * @version 1.0
 * @since 30.03.2017
 */
public class HarvestingMachine implements Machine
{
   private static final Logger LOG = LogManager.getLogger(HarvestingMachine.class);

   /**
    * Overrides the start method from the {@link Machine} interface.
    * <p>
    * {@inheritDoc}
    */
   @Override
   public synchronized List<Plant> work(final List<Plant> plantList)
   {
      return harvest(plantList);
   }

   /**
    * Harvesting all plants that are higher than 100 cm.
    *
    * @param plantList the previous plants
    * @return the complete field after the harvesting
    */
   private List<Plant> harvest(final List<Plant> plantList)
   {
      for( final Plant plant : plantList )
      {
         if( plant.getHeight() >= 100 )
         {
            plant.setHarvested(true);
         }
      }

      LOG.info(this.getClass().getSimpleName() + " just did it work");

      return plantList;
   }
}
