package cz.zcu.kiv.jop.example.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Implementation of {@link DictionarySession} which all methods are synchronized (the session may
 * be used by multiple threads).
 *
 * @author Mr.FrAnTA
 */
@Singleton
public class DictionarySessionImpl implements DictionarySession {

  /** Map which contains stored dictionaries. */
  protected final Map<String, List<String>> dictonaries = new HashMap<String, List<String>>();

  /**
   * {@inheritDoc}
   */
  public synchronized boolean containsDictionary(String path) {
    return dictonaries.containsKey(path);
  }

  /**
   * {@inheritDoc}
   */
  public synchronized List<String> getDictionary(String path) {
    List<String> dictionary = dictonaries.get(path);
    if (dictionary == null) {
      return null;
    }

    return new ArrayList<String>(dictionary);
  }

  /**
   * {@inheritDoc}
   */
  public synchronized void setDictionary(String path, List<String> dictionary) {
    dictonaries.put(path, dictionary);
  }

}
