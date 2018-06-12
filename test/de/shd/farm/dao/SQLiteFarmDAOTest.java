package de.shd.farm.dao;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import de.shd.farm.plant.More;
import de.shd.farm.plant.Plant;
import de.shd.farm.plant.Wheat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ALB
 * @version 1.0
 * @since 14.03.2017
 */
public class SQLiteFarmDAOTest
{
   private final SQLiteFarmDAO dao = new SQLiteFarmDAO();
   private final List<Plant> plantList = new ArrayList<>();
   private final String testDb = ("jdbc:sqlite:" + Paths.get("").toAbsolutePath().toString() + "\\testResources\\test.db").replace("\\", "/");
   private final File testDbFile = new File(Paths.get("").toAbsolutePath().toString() + "\\testResources\\test.db");

   @Before
   public void setUp() throws Exception
   {
      this.plantList.add(new More(12.3456F));
      this.plantList.add(new More(23.4567F));
      this.plantList.add(new More(34.5678F));
      this.plantList.add(new Wheat(45.6789F));
      this.plantList.add(new Wheat(56.789F));
      this.plantList.add(new Wheat(67.8901F));
   }

   @SuppressWarnings("ResultOfMethodCallIgnored")
   @After
   public void tearDown() throws Exception
   {
      if( this.testDbFile.exists() )
      {
         this.testDbFile.delete();
      }
   }

   @Test
   public void checkDatabaseHandling() throws Exception
   {
      this.dao.createNewDatabase(this.testDb);

      Assert.assertTrue("The created database does not exist!", this.testDbFile.exists());
      Assert.assertNotNull("The return of the created database must not be \"null\".", this.dao.getAllPlants(this.testDb));


      Assert.assertFalse("The created database must not have entries!", this.dao.getAllPlants(this.testDb).size() > 0);
      this.dao.insertContent(this.testDb, this.plantList);
      Assert.assertTrue("The created database must have entries!", this.dao.getAllPlants(this.testDb).size() > 0);

      this.dao.updateContent(this.testDb, this.plantList);
      Assert.assertEquals("The created database must have 6 entries!", 5, this.dao.getAllPlants(this.testDb).size());
   }
}
