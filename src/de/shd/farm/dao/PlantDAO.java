package de.shd.farm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.shd.farm.plant.FieldPlant;
import de.shd.farm.plant.More;
import de.shd.farm.plant.Plant;
import de.shd.farm.plant.Wheat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Plant dao.
 *
 * @author ALB
 * @version 1.0
 * @since 24.03.2017
 */
public class PlantDAO
{
   private static final Logger LOG = LogManager.getLogger(PlantDAO.class);

   /**
    * Initialize the plants at the first running of the database.
    *
    * @param species the plant species
    * @return the list of plants
    */
   public List<Plant> initPlantList(final FieldPlant species)
   {
      final List<Plant> plantList = new ArrayList<>();

      if( species == FieldPlant.MORE )
      {
         final int randomCount = new Random().nextInt(25) + 25;
         for( int i = 0; i < randomCount; i++ )
         {
            plantList.add(new More(new Random().nextFloat() * 70));
         }
      }
      else if( species == FieldPlant.WHEAT )
      {
         final int randomCount = new Random().nextInt(25) + 25;
         for( int i = 0; i < randomCount; i++ )
         {
            plantList.add(new Wheat(new Random().nextFloat() * 70));
         }
      }
      else
      {
         LOG.warn("unauthorized entry - no data passed on");
      }

      return plantList;
   }
}
