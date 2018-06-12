package de.shd.farm.utils.i18n;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ALB
 * @version 1.0
 * @since 16.03.2017
 */
public class I18NTest
{
   @Before
   public void setUp() throws Exception
   {
   }

   @After
   public void tearDown() throws Exception
   {
   }

   @Test
   public void getSupportedLocales() throws Exception
   {
      Assert.assertTrue(I18N.getSupportedLocales().size() > 0);
   }

   @Test
   public void getDefaultLocale() throws Exception
   {
      Assert.assertTrue(I18N.getDefaultLocale() == Locale.ENGLISH || I18N.getDefaultLocale() == Locale.GERMAN);
   }

   @Test
   public void setAndGetLocale() throws Exception
   {
      I18N.setLocale(Locale.ENGLISH);
      Assert.assertNotNull(I18N.getLocale());
   }

   @Test
   public void createStringBinding() throws Exception
   {
      Assert.assertNotNull(I18N.createStringBinding("button.new"));
      Assert.assertNotNull(I18N.get("button.new"));
   }
}
