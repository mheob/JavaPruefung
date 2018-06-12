package de.shd.farm.utils.helper;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * The type File helper.
 *
 * @author ALB
 * @version 1.0
 * @since 28.03.2017
 */
public abstract class FileHelper
{
   private static final Logger LOG = LogManager.getLogger(FileHelper.class);

   private FileHelper()
   {
   }

   /**
    * Gets the file extension.
    *
    * @param file the file
    * @return the file extension
    */
   @NotNull
   public static String getFileExtension(final File file)
   {
      final String fileName = file.getName();
      if( fileName.lastIndexOf('.') != -1 && fileName.lastIndexOf('.') != 0 )
      {
         final String result = fileName.substring(fileName.lastIndexOf('.') + 1);
         LOG.info("file extension " + result + " has been read out");
         return result;
      }
      else
      {
         LOG.warn("no file extension was detected");
         return "";
      }
   }
}
