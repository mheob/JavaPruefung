package de.shd.farm.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import de.shd.farm.dao.FileFarmDAO;
import de.shd.farm.dao.PlantDAO;
import de.shd.farm.dao.SQLiteFarmDAO;
import de.shd.farm.machine.CastingMachine;
import de.shd.farm.machine.HarvestingMachine;
import de.shd.farm.machine.Machine;
import de.shd.farm.machine.SowingMachine;
import de.shd.farm.plant.FieldPlant;
import de.shd.farm.plant.More;
import de.shd.farm.plant.Plant;
import de.shd.farm.plant.Wheat;
import de.shd.farm.utils.controls.AppAlert;
import de.shd.farm.utils.controls.AppFileChooser;
import de.shd.farm.utils.i18n.I18N;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type FarmApp view.
 *
 * @author ALB
 * @version 1.0
 * @since 14.03.2017
 */
public class MainView
{
   private static final Logger LOG = LogManager.getLogger(MainView.class);
   private static final String DB_PREFIX = "jdbc:sqlite:";
   private static final FileChooser.ExtensionFilter FILTER_DB = new FileChooser.ExtensionFilter("DB", "*.db");
   private static final FileChooser.ExtensionFilter FILTER_CSV = new FileChooser.ExtensionFilter("CSV", "*.csv");
   private static final FileChooser.ExtensionFilter FILTER_XML = new FileChooser.ExtensionFilter("XML", "*.xml");
   private static final FileChooser.ExtensionFilter FILTER_JSON = new FileChooser.ExtensionFilter("JSON", "*.json");
   private static final FileFarmDAO FILE_DAO = new FileFarmDAO();
   private static final PlantDAO PLANT_DAO = new PlantDAO();
   private static final SQLiteFarmDAO SQL_DAO = new SQLiteFarmDAO();

   @FXML
   public Button exportToFile;
   @FXML
   public Button importFromFile;
   @FXML
   public Button newDb;
   @FXML
   public Button openDb;
   @FXML
   public Button saveData;
   @FXML
   public Button startWork;
   @FXML
   public Button switchI18N;
   @FXML
   public Label countMore;
   @FXML
   public Label countMoreLabel;
   @FXML
   public Label countWheat;
   @FXML
   public Label countWheatLabel;
   @FXML
   public Label harvestedMore;
   @FXML
   public Label harvestedMoreLabel;
   @FXML
   public Label harvestedWheat;
   @FXML
   public Label harvestedWheatLabel;
   @FXML
   public Label moreTitle;
   @FXML
   public Label nothingSelected;
   @FXML
   public Label wheatTitle;
   @FXML
   public VBox plantBox;
   @FXML
   public VBox rootNode;

   private List<Plant> plantListAll;
   private List<Plant> plantListOnField;
   private List<Plant> plantListHarvestedOnly;
   private String dbName;
   private int countMoreOnField;
   private int countWheatOnField;
   private int countMoreHarvested;
   private int countWheatHarvested;

   /**
    * Initialized the MainView.
    */
   public void initUI()
   {
      // I18N - texts
      this.exportToFile.textProperty().bind(I18N.createStringBinding("button.export"));
      this.importFromFile.textProperty().bind(I18N.createStringBinding("button.import"));
      this.newDb.textProperty().bind(I18N.createStringBinding("button.new"));
      this.openDb.textProperty().bind(I18N.createStringBinding("button.open"));
      this.saveData.textProperty().bind(I18N.createStringBinding("button.save"));
      this.startWork.textProperty().bind(I18N.createStringBinding("button.startWork"));
      this.switchI18N.textProperty().bind(I18N.createStringBinding("button.i18n"));
      this.countMoreLabel.textProperty().bind(I18N.createStringBinding("label.countPlantsLabel"));
      this.countWheatLabel.textProperty().bind(I18N.createStringBinding("label.countPlantsLabel"));
      this.harvestedMoreLabel.textProperty().bind(I18N.createStringBinding("label.harvestedPlantsLabel"));
      this.harvestedWheatLabel.textProperty().bind(I18N.createStringBinding("label.harvestedPlantsLabel"));
      this.moreTitle.textProperty().bind(I18N.createStringBinding("label.moreTitle"));
      this.nothingSelected.textProperty().bind(I18N.createStringBinding("label.nothingSelected"));
      this.wheatTitle.textProperty().bind(I18N.createStringBinding("label.wheatTitle"));

      // I18N - tooltips
      this.exportToFile.setTooltip(I18N.tooltipForKey("button.export.tooltip"));
      this.importFromFile.setTooltip(I18N.tooltipForKey("button.import.tooltip"));
      this.newDb.setTooltip(I18N.tooltipForKey("button.new.tooltip"));
      this.openDb.setTooltip(I18N.tooltipForKey("button.open.tooltip"));
      this.saveData.setTooltip(I18N.tooltipForKey("button.save.tooltip"));
      this.startWork.setTooltip(I18N.tooltipForKey("button.startWork.tooltip"));
      this.switchI18N.setTooltip(I18N.tooltipForKey("button.i18n.tooltip"));

      // actions
      this.exportToFile.setOnAction(event -> exportData());
      this.importFromFile.setOnAction(event -> importData());
      this.newDb.setOnAction(event -> createNewDatabase());
      this.openDb.setOnAction(event -> openDatabase());
      this.saveData.setOnAction(event -> saveData());
      this.startWork.setOnAction(event -> startWorkingMachines());
      this.switchI18N.setOnAction(event -> switchLanguage(I18N.getLocale() == Locale.ENGLISH ? Locale.GERMAN : Locale.ENGLISH));

      // defaults
      this.plantListAll = new ArrayList<>();
      this.plantListOnField = new ArrayList<>();
      this.plantListHarvestedOnly = new ArrayList<>();

      LOG.info(getClass().getSimpleName() + " was initialized");
   }

   /**
    * Exports the data to a selected path.
    */
   private void exportData()
   {
      final FileChooser fileChooser = AppFileChooser.prepareDefaultDialog("fc.export.title", FILTER_CSV, FILTER_XML, FILTER_JSON);

      final File file = fileChooser.showSaveDialog(this.rootNode.getScene().getWindow());
      if( file == null )
      {
         LOG.warn(AppFileChooser.loggingFileSelected(null));
         return;
      }

      LOG.info(AppFileChooser.loggingFileSelected(file));

      FILE_DAO.writeData(file, this.plantListAll);
   }

   /**
    * Imports the data to a selected path.
    */
   private void importData()
   {
      final Optional<ButtonType> result = AppAlert.defaultConfirmationAlert("alert.import.title", "alert.import.header", "alert.import.content");
      if( result.isPresent() && result.get() != ButtonType.OK )
      {
         LOG.warn("import was canceled");
         return;
      }

      final FileChooser fileChooser = AppFileChooser.prepareDefaultDialog("fc.import.title", FILTER_CSV, FILTER_XML, FILTER_JSON);

      final File file = fileChooser.showOpenDialog(this.rootNode.getScene().getWindow());
      if( file == null )
      {
         LOG.warn(AppFileChooser.loggingFileSelected(null));
         return;
      }

      LOG.info(AppFileChooser.loggingFileSelected(file));

      this.plantListAll.addAll(FILE_DAO.readData(file));
   }

   /**
    * Creates a new Database to a selected path.
    */
   private void createNewDatabase()
   {
      final File file = AppFileChooser.prepareDefaultDialog("fc.newDb.title", FILTER_DB).showSaveDialog(this.rootNode.getScene().getWindow());
      if( file == null )
      {
         LOG.warn(AppFileChooser.loggingFileSelected(null));
         return;
      }

      LOG.info(AppFileChooser.loggingFileSelected(file));

      this.dbName = DB_PREFIX + file.toString().replace("\\", "/");

      SQL_DAO.createNewDatabase(this.dbName);

      final Optional<ButtonType> result = AppAlert.defaultConfirmationAlert("alert.init.title", "alert.init.header", "alert.init.content");
      if( result.isPresent() && result.get() == ButtonType.OK )
      {
         this.plantListAll.clear();
         this.plantListAll.addAll(PLANT_DAO.initPlantList(FieldPlant.WHEAT));
         this.plantListAll.addAll(PLANT_DAO.initPlantList(FieldPlant.MORE));

         SQL_DAO.insertContent(this.dbName, this.plantListAll);
      }

      updateData();
      dataLoaded();

      LOG.info("database created");
   }

   /**
    * Open an existing Database.
    */
   private void openDatabase()
   {
      final File file = AppFileChooser.prepareDefaultDialog("fc.openDb.title", FILTER_DB).showOpenDialog(this.rootNode.getScene().getWindow());
      if( file == null )
      {
         LOG.warn(AppFileChooser.loggingFileSelected(null));
         return;
      }

      LOG.info(AppFileChooser.loggingFileSelected(file));

      this.dbName = DB_PREFIX + file.toString().replace("\\", "/");

      this.plantListAll.clear();
      this.plantListAll.addAll(SQL_DAO.getAllPlants(this.dbName));

      updateData();
      dataLoaded();

      LOG.info("database content loaded");
   }

   /**
    * Saves the data to the selected database.
    */
   private void saveData()
   {
      SQL_DAO.updateContent(this.dbName, this.plantListAll);
      this.plantListAll = SQL_DAO.getAllPlants(this.dbName);

      canFileDAO(false);
      updateData();

      LOG.info("data stored in the database");
   }

   /**
    * starts all needed machines sequential
    */
   private void startWorkingMachines()
   {
      final List<Machine> machines = new ArrayList<>();
      machines.add(new SowingMachine());
      machines.add(new CastingMachine());
      machines.add(new HarvestingMachine());

      for( final Machine machine : machines )
      {
         this.plantListOnField = machine.work(this.plantListOnField);
      }

      this.plantListAll.clear();
      this.plantListAll.addAll(this.plantListHarvestedOnly);
      this.plantListAll.addAll(this.plantListOnField);

      updateData();
      LOG.info("all machines did there jobs");
   }

   /**
    * Switched the language of the gui.
    *
    * @param locale the new locale
    */
   private void switchLanguage(final Locale locale)
   {
      I18N.setLocale(locale);
      LOG.info("locale switched to: \"" + locale.toString().toUpperCase() + "\"");
   }

   /**
    * Enabled or disabled the <c>exportToFile</c> button, depending on the actual stored date in the database.
    *
    * @param dataInMemoryOnly <c>true</c> if the data currently stored only in the memory
    */
   private void canFileDAO(final boolean dataInMemoryOnly)
   {
      this.exportToFile.setDisable(dataInMemoryOnly);
      LOG.info("state of the possibility to export was set");
   }

   /**
    * Enabled or disabled some gui controls after first time of loading data from database.
    */
   private void dataLoaded()
   {
      this.nothingSelected.setVisible(false);
      this.plantBox.setVisible(true);

      this.importFromFile.setDisable(false);
      canFileDAO(true);

      LOG.info("gui content loaded first time");
   }

   /**
    * Update the gui controls.
    */
   private void updateData()
   {
      getNumberOfType();

      this.countMore.setText(String.valueOf(this.countMoreOnField));
      this.countWheat.setText(String.valueOf(this.countWheatOnField));
      this.harvestedMore.setText(String.valueOf(this.countMoreHarvested));
      this.harvestedWheat.setText(String.valueOf(this.countWheatHarvested));

      LOG.info("updated data displayed");
   }

   /**
    * Stores the count of the different types to fields.
    */
   private void getNumberOfType()
   {
      defineStock();

      this.countMoreOnField = 0;
      this.countWheatOnField = 0;

      for( final Plant plant : this.plantListOnField )
      {
         if( plant instanceof More )
         {
            this.countMoreOnField++;
         }
         else if( plant instanceof Wheat )
         {
            this.countWheatOnField++;
         }
         else
         {
            LOG.warn("wrong type of plants was selected");
         }
      }

      this.countMoreHarvested = 0;
      this.countWheatHarvested = 0;

      for( final Plant plant : this.plantListHarvestedOnly )
      {
         if( plant instanceof More )
         {
            this.countMoreHarvested++;
         }
         else if( plant instanceof Wheat )
         {
            this.countWheatHarvested++;
         }
         else
         {
            LOG.warn("wrong type of plants was selected");
         }
      }

      LOG.info("sum of plants depending on type status were calculated");
   }

   /**
    * Separated the plants to kinds on the field and in the stock.
    */
   private void defineStock()
   {
      this.plantListOnField.clear();
      this.plantListHarvestedOnly.clear();

      for( final Plant plant : this.plantListAll )
      {
         if( !plant.isHarvested() )
         {
            this.plantListOnField.add(plant);
         }
         else
         {
            this.plantListHarvestedOnly.add(plant);
         }
      }

      LOG.info("plants on field and in stock were separated");
   }
}
