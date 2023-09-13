package cs3500.pa05.model;

/**
 * interface for file reading
 */
public interface Reader {

  /**
   * reads content from a .bujo file
   *
   * @return BulletJournal object from the file
   */
  BulletJournal read();
}
