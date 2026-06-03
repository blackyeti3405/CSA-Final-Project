/**
 * Represents a discoverable item obtained by exploring a city during Stages 4, 5, or 6.
 *
 * Stores all narrative strings and stat effect values for a single collectible object.
 * Held by GameEngine until the player uses it or departs the city; not transferable between stages.
 */
public class Item {
  private String name;
  private String findNarrative;
  private String useKeyword;
  private String useNarrative;
  private int c;
  private int w;
  private int m;
  private int d;
  private int l;

  /**
   * Constructs an Item with all descriptive and mechanical parameters.
   *
   * @param name          display name of the item shown in inventory messages
   * @param findNarrative narrative text printed when the item is first discovered
   * @param useKeyword    trigger word the player must speak to activate this item
   * @param useNarrative  narrative text printed when the item is used
   * @param c             culture stat change applied on use
   * @param w             wealth stat change applied on use
   * @param m             military strength change applied on use
   * @param d             diplomacy change applied on use
   * @param l             civilian status of living change applied on use
   */
  public Item(String name, String findNarrative, String useKeyword,
              String useNarrative, int c, int w, int m, int d, int l) {
    this.name = name;
    this.findNarrative = findNarrative;
    this.useKeyword = useKeyword;
    this.useNarrative = useNarrative;
    this.c = c;
    this.w = w;
    this.m = m;
    this.d = d;
    this.l = l;
  }

  /**
   * Returns the display name of this item.
   *
   * @return item name string
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the narrative text shown when the item is discovered through exploration.
   *
   * @return discovery narrative string
   */
  public String getFindNarrative() {
    return this.findNarrative;
  }

  /**
   * Returns the keyword the player must speak to activate this item's effect.
   *
   * @return use trigger keyword string
   */
  public String getUseKeyword() {
    return this.useKeyword;
  }

  /**
   * Returns the narrative text shown when the item is successfully used.
   *
   * @return use narrative string
   */
  public String getUseNarrative() {
    return this.useNarrative;
  }

  /** @return culture stat delta */
  public int getC() { return this.c; }

  /** @return wealth stat delta */
  public int getW() { return this.w; }

  /** @return military strength stat delta */
  public int getM() { return this.m; }

  /** @return diplomacy stat delta */
  public int getD() { return this.d; }

  /** @return civilian status of living stat delta */
  public int getL() { return this.l; }
}
