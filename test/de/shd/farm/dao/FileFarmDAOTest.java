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
 * @since 28.03.2017
 */
public class FileFarmDAOTest
{
   private final FileFarmDAO dao = new FileFarmDAO();
   private final String resourcesPath = Paths.get("").toAbsolutePath().toString() + "\\testResources\\";
   private final File resourceCSV = new File(this.resourcesPath + "farm.csv");
   private final File resourceJSON = new File(this.resourcesPath + "farm.json");
   private final File resourceXML = new File(this.resourcesPath + "farm.xml");
   private List<Plant> plantList;

   @Before
   public void setUp() throws Exception
   {
      this.plantList = new ArrayList<>();
      this.plantList.add(new More(12.3456F));
      this.plantList.add(new More(23.4567F));
      this.plantList.add(new More(34.5678F));
      this.plantList.add(new Wheat(45.6789F));
      this.plantList.add(new Wheat(56.789F));
      this.plantList.add(new Wheat(67.8901F));

      this.dao.writeData(this.resourceCSV, this.plantList);
      this.dao.writeData(this.resourceJSON, this.plantList);
      this.dao.writeData(this.resourceXML, this.plantList);
   }

   @SuppressWarnings("ResultOfMethodCallIgnored")
   @After
   public void tearDown() throws Exception
   {
      if( this.resourceCSV.exists() )
      {
         this.resourceCSV.delete();
      }

      if( this.resourceJSON.exists() )
      {
         this.resourceJSON.delete();
      }

      if( this.resourceXML.exists() )
      {
         this.resourceXML.delete();
      }
   }

   @Test
   public void checkWrittenData() throws Exception
   {
      Assert.assertTrue("The written CSV file does not exist!", this.resourceCSV.exists());
      Assert.assertTrue("The written JSON file does not exist!", this.resourceJSON.exists());
      Assert.assertTrue("The written XML file does not exist!", this.resourceXML.exists());

      Assert.assertNotNull("The return of the CSV file must not be \"null\".", this.dao.readData(new File(this.resourcesPath + "farm.csv")));
      Assert.assertNotNull("The return of the JSON must not be \"null\".", this.dao.readData(new File(this.resourcesPath + "farm.json")));
      Assert.assertNotNull("The return of the XML must not be \"null\".", this.dao.readData(new File(this.resourcesPath + "farm.xml")));

      Assert.assertTrue(
            "The return of the CSV file must be greater than 0.",
            this.dao.readData(new File(this.resourcesPath + "test_correct.csv")).size() > 0
      );
      Assert.assertFalse(
            "The return of the CSV file must not be greater than 0.",
            this.dao.readData(new File(this.resourcesPath + "test_incorrect.csv")).size() > 0
      );
      Assert.assertTrue(
            "The return of the JSON file must be greater than 0.",
            this.dao.readData(new File(this.resourcesPath + "test_correct.json")).size() > 0
      );
      Assert.assertFalse(
            "The return of the JSON file must not be greater than 0.",
            this.dao.readData(new File(this.resourcesPath + "test_incorrect.json")).size() > 0
      );
      Assert.assertTrue(
            "The return of the XML file must be greater than 0.",
            this.dao.readData(new File(this.resourcesPath + "test_correct.xml")).size() > 0
      );
      Assert.assertFalse(
            "The return of the XML file must not be greater than 0.",
            this.dao.readData(new File(this.resourcesPath + "test_incorrect.xml")).size() > 0
      );
   }

   @Test
   public void checkReadData() throws Exception
   {
      Assert.assertTrue("The written CSV file does not exist!", this.resourceCSV.exists());
      Assert.assertTrue("The written JSON file does not exist!", this.resourceJSON.exists());
      Assert.assertTrue("The written XML file does not exist!", this.resourceXML.exists());

      this.plantList = this.dao.readData(this.resourceCSV);
      Assert.assertEquals("The result must be 6 (from CSV): ", 6, this.plantList.size());

      this.plantList = this.dao.readData(this.resourceJSON);
      Assert.assertEquals("The result must be 6 (from JSON): ", 6, this.plantList.size());

      this.plantList = this.dao.readData(this.resourceXML);
      Assert.assertEquals("The result must be 6 (from XML): ", 6, this.plantList.size());
   }
}
