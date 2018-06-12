package de.shd.farm.utils.i18n;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Tooltip;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The class I18N.
 *
 * @author ALB
 * @version 1.0
 * @since 16.03.2017
 */
@SuppressWarnings("WeakerAccess")
public final class I18N
{
   private static final ObjectProperty<Locale> locale;

   static
   {
      locale = new SimpleObjectProperty<>(getDefaultLocale());
      locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
   }

   private I18N()
   {
   }

   /**
    * Gets the supported locales.
    *
    * @return List of Locale objects.
    */
   @NotNull
   public static List<Locale> getSupportedLocales()
   {
      return new ArrayList<>(Arrays.asList(Locale.ENGLISH, Locale.GERMAN));
   }

   /**
    * Get the default locale. This is the systems default if contained in the supported locales, english otherwise.
    *
    * @return default locale
    */
   public static Locale getDefaultLocale()
   {
      final Locale sysDefault = Locale.getDefault();
      return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.ENGLISH;
   }

   /**
    * Gets locale.
    *
    * @return the locale
    */
   public static Locale getLocale()
   {
      return locale.get();
   }

   /**
    * Sets locale.
    *
    * @param locale the locale
    */
   public static void setLocale(final Locale locale)
   {
      localeProperty().set(locale);
      Locale.setDefault(locale);
   }

   /**
    * Locale property object property.
    *
    * @return the object property
    */
   @Contract(pure = true)
   public static ObjectProperty<Locale> localeProperty()
   {
      return locale;
   }

   /**
    * Gets the string with the given key from the resource bundle for the current locale and uses it as first argument to MessageFormat.format,
    * passing in the optional args and returning the result.
    *
    * @param key  the message key
    * @param args the optional arguments for the message
    * @return the localized formatted string
    */
   @NotNull
   public static String get(final String key, final Object... args)
   {
      final ResourceBundle bundle = ResourceBundle.getBundle("i18n", getLocale());
      return MessageFormat.format(bundle.getString(key), args);
   }

   /**
    * Creates a String binding to a localized String for the given message bundle key
    *
    * @param key  the key
    * @param args the args
    * @return the string binding
    */
   @NotNull
   public static StringBinding createStringBinding(final String key, final Object... args)
   {
      return Bindings.createStringBinding(() -> get(key, args), locale);
   }

   /**
    * Creates a bound Tooltip for the given ResourceBundle key
    *
    * @param key  ResourceBundle key
    * @param args optional arguments for the message
    * @return Label tooltip
    */
   public static Tooltip tooltipForKey(final String key, final Object... args)
   {
      final Tooltip tooltip = new Tooltip();
      tooltip.textProperty().bind(createStringBinding(key, args));
      return tooltip;
   }
}
