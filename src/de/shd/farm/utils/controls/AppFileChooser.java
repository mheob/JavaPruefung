package de.shd.farm.utils.controls;

import java.io.File;
import java.nio.file.Paths;

import de.shd.farm.utils.i18n.I18N;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * The type AppFileChooser.
 * This class is a helper for setting up {@link FileChooser} with own styles.
 *
 * @author ALB
 * @version 1.0
 * @since 20.03.2017
 */
public abstract class AppFileChooser
{
   private static final Logger LOG = LogManager.getLogger(AppFileChooser.class);

   private AppFileChooser()
   {
   }

   /**
    * Prepare the default settings for the {@link FileChooser}.
    *
    * @param title  the title
    * @param filter the filter
    * @return the {@link FileChooser}
    */
   public static FileChooser prepareDefaultDialog(final String title, final FileChooser.ExtensionFilter... filter)
   {
      final FileChooser fileChooser = new FileChooser();
      fileChooser.titleProperty().bind(I18N.createStringBinding(title));
      fileChooser.setInitialDirectory(new File(Paths.get("").toAbsolutePath().toString()));
      fileChooser.getExtensionFilters().addAll(filter);

      LOG.info("prepared a default FileChooser");

      return fileChooser;
   }

   /**
    * Logging string for selected files.
    *
    * @param file the selected file
    * @return the logging string
    */
   @NotNull
   public static String loggingFileSelected(final File file)
   {
      if( file == null )
      {
         return "no file selected";
      }
      else
      {
         return "file \"" + file.toString() + "\" selected";
      }
   }
}
