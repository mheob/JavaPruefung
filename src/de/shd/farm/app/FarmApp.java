package de.shd.farm.app;

import java.io.IOException;
import java.net.URL;

import de.shd.farm.controller.MainView;
import de.shd.farm.utils.i18n.I18N;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Java Training - the final exam
 *
 * @author ALB
 * @version 1.0
 * @since 14.03.2017
 */
public class FarmApp extends Application
{
   private static final Logger LOG = LogManager.getLogger(FarmApp.class);

   /**
    * The entry point of application.
    *
    * @param args the input arguments
    */
   public static void main(final String[] args)
   {
      LOG.info("application started");

      launch(args);

      Runtime.getRuntime().addShutdownHook(new Thread(FarmApp::loggingAtClose));
   }

   private static void loggingAtClose()
   {
      LOG.info("application closed");
      LOG.info("----------");
   }

   /**
    * Overrides the start method from the {@link Application} class.
    * <p>
    * {@inheritDoc}
    */
   @Override
   public void start(final Stage primaryStage)
   {
      final FXMLLoader loader = new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource("views/MainView.fxml"));
      Parent rootParent = null;

      try
      {
         rootParent = loader.load();
      }
      catch(final IOException e)
      {
         LOG.error("could not load the parent scene: " + e);
      }

      assert rootParent != null;
      final Scene scene = new Scene(rootParent);
      primaryStage.setScene(scene);

      final URL iconResource = Thread.currentThread().getContextClassLoader().getResource("icons/wheat.png");
      if( iconResource != null )
      {
         primaryStage.getIcons().add(new Image(iconResource.toExternalForm()));
      }

      primaryStage.titleProperty().bind(I18N.createStringBinding("window.title"));

      final MainView mainViewController = loader.getController();
      mainViewController.initUI();

      primaryStage.show();
   }
}
