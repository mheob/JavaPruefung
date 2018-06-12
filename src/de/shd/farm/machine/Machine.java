package de.shd.farm.machine;

import java.util.List;

import de.shd.farm.plant.Plant;

/**
 * The interface Machine.
 *
 * @author ALB
 * @version 1.0
 * @since 15.03.2017
 */
@FunctionalInterface
public interface Machine
{
   /**
    * Work list.
    *
    * @param plantList the plants
    * @return the list
    */
   List<Plant> work(List<Plant> plantList);
}
