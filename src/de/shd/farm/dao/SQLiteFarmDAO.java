package de.shd.farm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.shd.farm.plant.Plant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Farm dao.
 *
 * @author ALB
 * @version 1.0
 * @since 14.03.2017
 */
public class SQLiteFarmDAO
{
   private static final Logger LOG = LogManager.getLogger(SQLiteFarmDAO.class);
   private static final String LOGGING_CLOSE_FAILED = "closing the connection failed: ";

   /**
    * Instantiates a new FarmDAO by using SQLite.
    */
   public SQLiteFarmDAO()
   {
      // do nothing
   }

   /**
    * Create new database.
    *
    * @param fileUrl the database file name
    */
   public void createNewDatabase(final String fileUrl)
   {
      LOG.info("database is being created");

      final String sql = "CREATE TABLE IF NOT EXISTS plants (" +
                         "p_id         INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                         "p_species    TEXT    NOT NULL, " +
                         "p_height     REAL    NOT NULL, " +
                         "p_harvested  INTEGER NOT NULL)";

      Connection conn = null;
      Statement stmt = null;

      try
      {
         conn = connect(fileUrl);

         assert conn != null;

         stmt = conn.createStatement();
         stmt.executeUpdate("DROP TABLE IF EXISTS plants");
         stmt.execute(sql);

         LOG.info("needed tables are created");
      }
      catch(final SQLException e)
      {
         LOG.error("database creation unsuccessfully caused of: " + e);
      }
      finally
      {
         assert stmt != null;
         try
         {
            stmt.close();
            conn.close();
         }
         catch(final SQLException e)
         {
            LOG.error(LOGGING_CLOSE_FAILED + e);
         }
      }
   }

   /**
    * Update the content to the database.
    *
    * @param fileUrl   the database file url
    * @param plantList the plant list
    */
   public void updateContent(final String fileUrl, final List<Plant> plantList)
   {
      deleteValues(fileUrl, null);
      insertContent(fileUrl, plantList);
   }

   /**
    * Insert the content to the database.
    *
    * @param fileUrl   the database file url
    * @param plantList the plant list
    */
   public void insertContent(final String fileUrl, final List<Plant> plantList)
   {
      final String sql = "INSERT INTO plants (p_species, p_height, p_harvested) VALUES (?, ?, ?)";

      Connection conn = null;
      PreparedStatement pstmt = null;

      try
      {
         conn = connect(fileUrl);

         assert conn != null;

         pstmt = conn.prepareStatement(sql);

         for( final Plant plant : plantList )
         {
            pstmt.setString(1, plant.getClass().getName());
            pstmt.setFloat(2, plant.getHeight());
            pstmt.setBoolean(3, plant.isHarvested());
            pstmt.executeUpdate();
         }

         LOG.info(plantList.size() + " entries are stored to the database");
      }
      catch(final SQLException e)
      {
         LOG.error("storing the initial data unsuccessfully caused of: " + e);
      }
      finally
      {
         assert pstmt != null;
         try
         {
            pstmt.close();
            conn.close();
         }
         catch(final SQLException e)
         {
            LOG.error(LOGGING_CLOSE_FAILED + e);
         }
      }
   }

   /**
    * Select all rows in the plants table.
    *
    * @param fileUrl the file url
    * @return the plants
    */
   public List<Plant> getAllPlants(final String fileUrl)
   {
      final List<Plant> plantList = new ArrayList<>();

      final String sql = "SELECT * FROM plants";

      Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;

      try
      {
         conn = connect(fileUrl);

         assert conn != null;

         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);

         if( !rs.next() )
         {
            LOG.info("nothing stored in the database");
            return new ArrayList<>();
         }

         int rows = 0;

         while( rs.next() )
         {
            final Plant plant = (Plant) Class.forName(rs.getString("p_species")).newInstance();
            plant.setId(rs.getInt("p_id"));
            plant.setHeight(rs.getFloat("p_height"));
            plant.setHarvested(rs.getBoolean("p_harvested"));

            plantList.add(plant);

            rows++;
         }

         LOG.info("\"" + rows + "\" records read from database");
      }
      catch(final SQLException e)
      {
         LOG.error("SQLException at getAllPlants unsuccessfully caused of: " + e);
      }
      catch(final ClassNotFoundException e)
      {
         LOG.error("ClassNotFoundException at getAllPlants unsuccessfully caused of: " + e);
      }
      catch(final IllegalAccessException e)
      {
         LOG.error("IllegalAccessException at getAllPlants unsuccessfully caused of: " + e);
      }
      catch(final InstantiationException e)
      {
         LOG.error("InstantiationException at getAllPlants unsuccessfully caused of: " + e);
      }
      finally
      {
         try
         {
            assert rs != null;
            rs.close();
         }
         catch(final SQLException e)
         {
            LOG.error(LOGGING_CLOSE_FAILED + e);
         }

         try
         {
            stmt.close();
            conn.close();
         }
         catch(final SQLException e)
         {
            LOG.error(LOGGING_CLOSE_FAILED + e);
         }
      }

      return plantList;
   }

   /**
    * Delete values.
    *
    * @param fileUrl   the database file url
    * @param condition the condition
    */
   private void deleteValues(final String fileUrl, @SuppressWarnings("SameParameterValue") final String condition)
   {
      final String sql = "DELETE FROM plants" + (condition != null ? " WHERE " + condition : "");

      Connection conn = null;
      PreparedStatement pstmt = null;

      try
      {
         conn = connect(fileUrl);

         assert conn != null;

         pstmt = conn.prepareStatement(sql);

         pstmt.executeUpdate();

         LOG.info("deleted all entries in the table \"plants\"");
      }
      catch(final SQLException e)
      {
         if( condition == null )
         {
            LOG.error("deleting of all entries unsuccessfully caused of: " + e);
         }
         else
         {
            LOG.error("deleting of the entry with the condition \"" + condition + "\" unsuccessfully caused of: " + e);
         }
      }
      finally
      {
         assert pstmt != null;
         try
         {
            pstmt.close();
            conn.close();
         }
         catch(final SQLException e)
         {
            LOG.error(LOGGING_CLOSE_FAILED + e);
         }
      }
   }

   /**
    * Connects to the database.
    *
    * @param fileUrl the database file url
    * @return the connection
    */
   private Connection connect(final String fileUrl)
   {
      Connection conn = null;

      try
      {
         conn = DriverManager.getConnection(fileUrl);
         LOG.info("connection to \"" + fileUrl + "\" has been established");
      }
      catch(final SQLException e)
      {
         LOG.error("connection to \"" + fileUrl + "\" unsuccessfully caused of: " + e);
      }

      return conn;
   }
}
