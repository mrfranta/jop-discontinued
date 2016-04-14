package cz.zcu.kiv.jop.example.generator;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;

import cz.zcu.kiv.jop.example.annotation.DictionaryGenerator;
import cz.zcu.kiv.jop.example.session.DictionarySession;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;
import cz.zcu.kiv.jop.util.StringUtils;

/**
 * The implementation of value generator which provides random value from loaded dictionary.
 *
 * @author Mr.FrAnTA
 */
@Singleton
public class DictionaryValueGenerator extends AbstractValueGenerator<String, DictionaryGenerator> {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(DictionaryValueGenerator.class);

  /** Injected session which stores loaded dictionaries. */
  @Inject
  protected DictionarySession dictionarySession;

  /**
   * {@inheritDoc}
   */
  public Class<String> getValueType() {
    return String.class;
  }

  /**
   * Loads dictionary (expressions) from given path line by line.
   *
   * @param path the path of dictionary file.
   * @param encoding the encoding of dictionary file.
   * @return Loaded dictionary.
   * @throws ValueGeneratorException If some error occurs during loading of file.
   */
  protected List<String> loadDictionary(String path, String encoding) throws ValueGeneratorException {
    File dictionaryFile = null;

    if (path.startsWith(DictionaryGenerator.CLASSPATH_PREFIX)) {
      path = path.substring(DictionaryGenerator.CLASSPATH_PREFIX.length());
      URL url = getClass().getResource(path);
      if (url != null) {
        dictionaryFile = new File(url.getFile());
      }
    }
    else {
      dictionaryFile = new File(path);
    }

    if (dictionaryFile == null || !dictionaryFile.exists()) {
      throw new ValueGeneratorException("Dictionary file not found at path: " + path);
    }

    if (!dictionaryFile.isFile()) {
      throw new ValueGeneratorException("Invalid dictionary file at path: " + path);
    }

    FileInputStream fis = null;
    BufferedInputStream bis = null;
    InputStreamReader isr = null;
    BufferedReader br = null;

    List<String> dictionary = new ArrayList<String>();

    try {
      fis = new FileInputStream(dictionaryFile);
      bis = new BufferedInputStream(fis);
      isr = new InputStreamReader(bis, Charset.forName(encoding));
      br = new BufferedReader(isr);

      logger.debug("Loading dictionary file: " + path);

      for (String line = br.readLine(); line != null; line = br.readLine()) {
        if (StringUtils.hasText(line)) {
          dictionary.add(line);
        }
      }
    }
    catch (IOException exc) {
      throw new ValueGeneratorException("Cannot load dictionary", exc);
    }
    finally {
      close(br);
      close(isr);
      close(bis);
      close(fis);
    }

    return dictionary;
  }

  /**
   * Helper method for closing classes implementing {@link Closeable} interface. This method is
   * useful for null pointer checking and consuming unuseable {@link IOException}s.
   *
   * @param closeable the closeable to close.
   */
  protected void close(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      }
      catch (IOException exc) {
        // not handled
      }
    }
  }

  /**
   * Lookups for stored dictionary in {@link DictionarySession} for path given in parameters. If
   * there is no stored dictionary, the generator load expressions from the dictionary file line by
   * line. After that the generator returns random expression from dictionary.
   *
   * @throws ValueGeneratorException If parameters are not valid or if some error occurs during
   *           loading of dictionary file.
   */
  public String getValue(DictionaryGenerator params) throws ValueGeneratorException {
    checkParamsNotNull(params);

    String path = params.path();
    checkParams(StringUtils.hasText(path), "Invalid path of dictionary");

    List<String> dictionary = null;
    if (dictionarySession.containsDictionary(path)) {
      dictionary = dictionarySession.getDictionary(path);
    }
    else {
      dictionary = loadDictionary(path, params.encoding());
      dictionarySession.setDictionary(path, dictionary);
    }

    if (dictionary == null || dictionary.size() == 0) {
      throw new ValueGeneratorException("Dictionary is empty");
    }

    return dictionary.get(getRandomGenerator(params).nextInt(dictionary.size()));
  }
}
