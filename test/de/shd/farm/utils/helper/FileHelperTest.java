package de.shd.farm.utils.helper;

import java.io.File;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ALB
 * @version 1.0
 * @since 28.03.2017
 */
public class FileHelperTest
{
   private final String fileWithoutExt = Paths.get("").toAbsolutePath().toString() + "\\testResources\\" + "test_correct";
   private final File csvFile = new File(this.fileWithoutExt + ".csv");
   private final File xmlFile = new File(this.fileWithoutExt + ".xml");
   private final File jsonFile = new File(this.fileWithoutExt + ".json");

   @Before
   public void setUp() throws Exception
   {
   }

   @After
   public void tearDown() throws Exception
   {
   }

   @Test
   public void getFileExtension() throws Exception
   {
      final String fileWithoutExt = Paths.get("").toAbsolutePath().toString() + "\\testResources\\" + "test_correct";

      Assert.assertEquals("The extension must be CSV!", "csv", FileHelper.getFileExtension(this.csvFile));
      Assert.assertEquals("The extension must be XML!", "xml", FileHelper.getFileExtension(this.xmlFile));
      Assert.assertEquals("The extension must be JSON!", "json", FileHelper.getFileExtension(this.jsonFile));

      Assert.assertEquals("The extension must be EMPTY!", "", FileHelper.getFileExtension(new File(fileWithoutExt + "")));
   }
}
