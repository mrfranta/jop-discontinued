package cz.zcu.kiv.jop.example.session;

import java.util.List;

/**
 * Session which stores loaded dictionaries (lists of expressions) for dictionary value generator (
 * {@link cz.zcu.kiv.jop.example.generator.DictionaryValueGenerator}).
 *
 * @author Mr.FrAnTA
 */
public interface DictionarySession {

  /**
   * Returns information whether the session contains dictionary for file at given path.
   *
   * @param path the path of loaded dictionary file.
   * @return <code>true</code> if session contains loaded dictionary from given path;
   *         <code>false</code> otherwise.
   */
  public boolean containsDictionary(String path);

  /**
   * Returns (stored) loaded dictionary for file at given path. In case that session contains no
   * dictionary, it returns <code>null</code>. Returned dictionary is copy of stored one and can be
   * edited - the edit operation don't affect the stored dictionary.
   *
   * @param path the path of dictionary file.
   * @return Loaded dictionary or <code>null</code>.
   */
  public List<String> getDictionary(String path);

  /**
   * Stores loaded dictionary for file at given path.
   *
   * @param path the path of loaded dictionary file.
   * @param dictionary loaded dictionary.
   */
  public void setDictionary(String path, List<String> dictionary);

}
