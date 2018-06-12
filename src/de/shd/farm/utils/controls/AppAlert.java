package de.shd.farm.utils.controls;

import java.net.URL;
import java.util.Optional;

import de.shd.farm.utils.i18n.I18N;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type AppFileChooser.
 * This class is a helper for setting up {@link Alert} with own styles.
 *
 * @author ALB
 * @version 1.0
 * @since 29.03.2017
 */
public abstract class AppAlert
{
   private static final Logger LOG = LogManager.getLogger(AppAlert.class);

   private AppAlert()
   {
   }

   /**
    * Prepare the default settings for the {@link Alert} of the type confirmation.
    *
    * @param title   the title
    * @param header  the header
    * @param content the content
    * @return the {@link Optional<ButtonType>}
    */
   public static Optional<ButtonType> defaultConfirmationAlert(final String title, final String header, final String content)
   {
      final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.titleProperty().bind(I18N.createStringBinding(title));
      alert.headerTextProperty().bind(I18N.createStringBinding(header));
      alert.contentTextProperty().bind(I18N.createStringBinding(content));

      final Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
      final URL iconResource = Thread.currentThread().getContextClassLoader().getResource("icons/wheat.png");
      if( iconResource != null )
      {
         stage.getIcons().add(new Image(iconResource.toExternalForm()));
      }

      LOG.info("generated a default alert of the type confirmation");

      return alert.showAndWait();
   }
}
