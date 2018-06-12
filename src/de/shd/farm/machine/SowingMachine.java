package de.shd.farm.machine;

import java.util.List;

import de.shd.farm.plant.More;
import de.shd.farm.plant.Plant;
import de.shd.farm.plant.Wheat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The sowing machine sows the seeds, so that the maximum number of plants on the field.
 *
 * @author ALB
 * @version 1.0
 * @since 30.03.2017
 */
public class SowingMachine implements Machine
{
   private static final Logger LOG = LogManager.getLogger(SowingMachine.class);

   /**
    * Overrides the start method from the {@link Machine} interface.
    * <p>
    * {@inheritDoc}
    */
   @Override
   public synchronized List<Plant> work(final List<Plant> plantList)
   {
      return sow(plantList);
   }

   /**
    * Sow so many plants on the field until it is full with 50 pieces.
    *
    * @param plantList the previous plants
    * @return the complete sowed field
    */
   private List<Plant> sow(final List<Plant> plantList)
   {
      int countMore = 0;
      int countWheat = 0;

      for( final Plant plant : plantList )
      {
         if( plant instanceof More )
         {
            countMore++;
         }
         else if( plant instanceof Wheat )
         {
            countWheat++;
         }
      }

      for( int i = 0; i < 50 - countMore; i++ )
      {
         plantList.add(new More());
      }

      for( int i = 0; i < 50 - countWheat; i++ )
      {
         plantList.add(new Wheat());
      }

      LOG.info("\"" + countMore + "\" more and \"" + countWheat + "\" wheat sowed");
      LOG.info(this.getClass().getSimpleName() + " just did it work");

      return plantList;
   }
}
