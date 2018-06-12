package de.shd.farm.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.google.gson.GsonBuilder;
import de.shd.farm.plant.Plant;
import de.shd.farm.utils.helper.FileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The type File farm dao.
 *
 * @author ALB
 * @version 1.0
 * @since 28.03.2017
 */
public class FileFarmDAO
{
   private static final Logger LOG = LogManager.getLogger(FileFarmDAO.class);
   private static final String PLANT = "plant";
   private static final String FIELD = "field";
   private static final String HEIGHT = "weight";
   private static final String ID = "id";
   private static final String HARVESTED = "harvested";
   private static final String TYPE = "type";

   /**
    * Write data to a file.
    *
    * @param fieldFile the field file
    * @param plants    the plants
    */
   public void writeData(final File fieldFile, final List<Plant> plants)
   {
      switch( FileHelper.getFileExtension(fieldFile) )
      {
         case "csv":
            writeCSV(fieldFile, plants);
            break;
         case "xml":
            writeXML(fieldFile, plants);
            break;
         case "json":
            writeJSON(fieldFile, plants);
            break;
         default:
      }

      LOG.info("file saved");
   }

   /**
    * Read data from a file to a list.
    *
    * @param fieldFile the field file
    * @return the list
    */
   public List<Plant> readData(final File fieldFile)
   {
      List<Plant> plants = new ArrayList<>();

      switch( FileHelper.getFileExtension(fieldFile) )
      {
         case "csv":
            plants.addAll(readCSV(fieldFile));
            break;
         case "xml":
            plants.addAll(readXML(fieldFile));
            break;
         case "json":
            plants.addAll(readJSON(fieldFile));
            break;
         default:
            plants = new ArrayList<>();
      }

      LOG.info("file read");

      return plants;
   }

   /**
    * Write data to a "CSV" file.
    *
    * @param fieldFile the field file
    * @param plants    the plants
    */
   private void writeCSV(final File fieldFile, final List<Plant> plants)
   {
      try (PrintWriter pw = new PrintWriter(fieldFile))
      {
         for( final Plant plant : plants )
         {
            pw.println(plant.getId() + ";" +
                       plant.getClass().getName() + ";" +
                       plant.getHeight() + ";" +
                       plant.isHarvested());
         }
      }
      catch(final FileNotFoundException e)
      {
         LOG.error("file could not be written: " + e);
      }
   }

   /**
    * Read data from a "CSV" file to a list.
    *
    * @param fieldFile the field file
    * @return the list
    */
   private List<Plant> readCSV(final File fieldFile)
   {
      final List<Plant> plants = new ArrayList<>();

      try (BufferedReader reader = new BufferedReader(new FileReader(fieldFile)))
      {
         String line;
         while( (line = reader.readLine()) != null )
         {
            final String[] lineSplit = line.split(";");

            final Plant plant = (Plant) Class.forName(lineSplit[1]).newInstance();
            plant.setId(Integer.parseInt(lineSplit[0]));
            plant.setHeight(Float.parseFloat(lineSplit[2]));
            plant.setHarvested(Boolean.parseBoolean(lineSplit[3]));

            plants.add(plant);
         }
      }
      catch(final Exception e)
      {
         LOG.error("failed to read from csv file: " + e);
      }

      return plants;
   }

   /**
    * Write data to a "XML" file.
    *
    * @param fieldFile the field file
    * @param plants    the plants
    */
   private void writeXML(final File fieldFile, final List<Plant> plants)
   {
      try
      {
         final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
         final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

         final Document doc = docBuilder.newDocument();
         final Element rootElem = doc.createElement(FIELD);
         doc.appendChild(rootElem);

         for( final Plant plant : plants )
         {
            final Element plantElem = doc.createElement(PLANT);
            rootElem.appendChild(plantElem);

            final Element idElem = doc.createElement(ID);
            idElem.appendChild(doc.createTextNode(String.valueOf(plant.getId())));
            plantElem.appendChild(idElem);

            final Element typeElem = doc.createElement(TYPE);
            typeElem.appendChild(doc.createTextNode(plant.getClass().getName()));
            plantElem.appendChild(typeElem);

            final Element heightElem = doc.createElement(HEIGHT);
            heightElem.appendChild(doc.createTextNode(String.valueOf(plant.getHeight())));
            plantElem.appendChild(heightElem);

            final Element genderElem = doc.createElement(HARVESTED);
            genderElem.appendChild(doc.createTextNode(String.valueOf(plant.isHarvested())));
            plantElem.appendChild(genderElem);
         }

         final Transformer transformer = TransformerFactory.newInstance().newTransformer();
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
         transformer.transform(new DOMSource(doc), new StreamResult(fieldFile));
      }
      catch(ParserConfigurationException | TransformerException e)
      {
         LOG.error("failed to write to xml file" + e);
      }
   }

   /**
    * Read data from a "XML" file to a list.
    *
    * @param fieldFile the field file
    * @return the list
    */
   private List<Plant> readXML(final File fieldFile)
   {
      final List<Plant> plants = new ArrayList<>();

      try
      {
         final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
         final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
         final Document doc = docBuilder.parse(fieldFile);

         doc.getDocumentElement().normalize();

         final NodeList nList = doc.getElementsByTagName("plant");

         for( int temp = 0; temp < nList.getLength(); temp++ )
         {
            final Node nNode = nList.item(temp);

            if( nNode.getNodeType() != Node.ELEMENT_NODE )
            {
               continue;
            }

            final Element elem = (Element) nNode;

            final Plant plant = (Plant) Class.forName(elem.getElementsByTagName(TYPE).item(0).getTextContent()).newInstance();
            plant.setId(Integer.parseInt(elem.getElementsByTagName(ID).item(0).getTextContent()));
            plant.setHeight(Float.parseFloat(elem.getElementsByTagName(HEIGHT).item(0).getTextContent()));
            plant.setHarvested(Boolean.parseBoolean(elem.getElementsByTagName(HARVESTED).item(0).getTextContent()));

            plants.add(plant);
         }
      }
      catch(IOException | ParserConfigurationException | InstantiationException | IllegalAccessException | SAXException | ClassNotFoundException e)
      {
         LOG.error("failed to read to xml file" + e);
      }

      return plants;
   }

   /**
    * Write data to a "JSON" file.
    *
    * @param fieldFile the field file
    * @param plants    the plants
    */
   @SuppressWarnings("unchecked")
   private void writeJSON(final File fieldFile, final List<Plant> plants)
   {
      final JSONObject rootObj = new JSONObject();
      final JSONArray fieldList = new JSONArray();

      for( final Plant plant : plants )
      {
         final JSONObject plantObj = new JSONObject();
         plantObj.put(ID, plant.getId());
         plantObj.put(TYPE, plant.getClass().getName());
         plantObj.put(HEIGHT, plant.getHeight());
         plantObj.put(HARVESTED, plant.isHarvested());

         fieldList.add(plantObj);
      }

      rootObj.put(FIELD, fieldList);

      try (FileWriter file = new FileWriter(fieldFile))
      {
         file.write(new GsonBuilder().setPrettyPrinting().create().toJson(rootObj));
         file.flush();
      }
      catch(final IOException e)
      {
         LOG.error("Failed to write to json file: " + e);
      }
   }

   /**
    * Read data from a "JSON" file to a list.
    *
    * @param fieldFile the field file
    * @return the list
    */
   private List<Plant> readJSON(final File fieldFile)
   {
      final List<Plant> plants = new ArrayList<>();

      try
      {
         final JSONParser parser = new JSONParser();
         final JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(fieldFile));

         final JSONArray fieldList = (JSONArray) jsonObj.get(FIELD);

         for( final Object plantObject : fieldList )
         {
            final JSONObject plantObj = (JSONObject) plantObject;

            final Plant plant = (Plant) Class.forName(String.valueOf(plantObj.get(TYPE))).newInstance();
            plant.setId(Integer.parseInt(String.valueOf(plantObj.get(ID))));
            plant.setHeight(Float.parseFloat(String.valueOf(plantObj.get(HEIGHT))));
            plant.setHarvested(Boolean.parseBoolean(String.valueOf(plantObj.get(HARVESTED))));

            plants.add(plant);
         }
      }
      catch(IOException | InstantiationException | IllegalAccessException | ParseException | ClassNotFoundException e)
      {
         LOG.error("Failed to read from json file: " + e);
      }

      return plants;
   }
}
