package de.shd.farm.utils.controls;

import java.io.File;
import java.nio.file.Paths;

import javafx.stage.FileChooser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ALB
 * @version 1.0
 * @since 03.04.2017
 */
public class AppFileChooserTest
{
   private final File testFile = new File(Paths.get("").toAbsolutePath().toString() + "\\testResources\\" + "test.txt");
   private final FileChooser fc = AppFileChooser.prepareDefaultDialog("fc.openDb.title", new FileChooser.ExtensionFilter("DB", "*.db"));

   @Before
   public void setUp() throws Exception
   {
   }

   @After
   public void tearDown() throws Exception
   {
   }

   @Test
   public void prepareDefaultDialog() throws Exception
   {
      final File initPath = new File(Paths.get("").toAbsolutePath().toString());
      Assert.assertEquals(initPath, this.fc.getInitialDirectory());
   }

   @Test
   public void loggingFileSelected() throws Exception
   {
      Assert.assertEquals("no file selected", AppFileChooser.loggingFileSelected(null));
      Assert.assertEquals("file \"" + this.testFile.toString() + "\" selected", AppFileChooser.loggingFileSelected(this.testFile));
   }
}
