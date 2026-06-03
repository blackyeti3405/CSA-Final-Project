/**
 * Abstract template modeling a single event, city choice, or hardship stage.
 *
 * Provides fields and blueprint methods required to render a text-based scenario choice interface.
 * Implemented as discrete separate subclasses representing unique steps across the game route.
 * Options are never shown during normal play. Each room ends with an open question.
 * Full option guidance, with keyword samples, is only revealed when the player requests a hint.
 */
public abstract class Room {
  protected String title;
  protected String contextText;
  protected String closingQuestion;
  protected String optionA_Text;
  protected String optionB_Text;
  protected String[] keywordsA;
  protected String[] keywordsB;
  protected Item exploreItem;

  /**
   * Outputs the narrative context and a closing question to the terminal.
   * No action options or keywords are shown — the player speaks freely.
   * Title is rendered in bold blue, narrative in italic blue, closing question in bold.
   */
  public void displayScene() {
    Display.typeInstant("\n" + Display.c("--------------------------------------------------", Display.BOLD_BLUE));
    Display.typeFast(Display.c("CRISIS SITE: " + this.title, Display.BOLD_BLUE));
    Display.typeInstant(Display.c("--------------------------------------------------", Display.BOLD_BLUE));
    Display.typeSlow(Display.c(this.contextText, Display.ITALIC_BLUE));
    Display.pause(300);
    Display.type(Display.c("\n" + this.closingQuestion, Display.BOLD));
    Display.typeInstant(Display.c("(Type 'hint' if you need guidance.)", Display.GREEN));
    Display.typeInstant(Display.c("--------------------------------------------------", Display.BOLD_BLUE));
  }

  /**
   * Reveals full option descriptions and a sample of trigger keywords when the player asks.
   * The hidden explore path is intentionally omitted — it remains a secret.
   * Header in bold blue, option text in italic, keyword samples in green.
   */
  public void displayHint() {
    Display.typeInstant("\n" + Display.c("[COUNCIL GUIDANCE]", Display.BOLD_BLUE));
    Display.type("\n  " + Display.c("Path 1: ", Display.BOLD) + Display.c(this.optionA_Text, Display.ITALIC));
    Display.typeInstant(Display.c("  -> Try words like: ", Display.GREEN) + buildSample(this.keywordsA));
    Display.type("\n  " + Display.c("Path 2: ", Display.BOLD) + Display.c(this.optionB_Text, Display.ITALIC));
    Display.typeInstant(Display.c("  -> Try words like: ", Display.GREEN) + buildSample(this.keywordsB));
  }

  /**
   * Builds a short human-readable sample of up to three keywords from the given array.
   *
   * @param keywords the full keyword array to sample from
   * @return a formatted string such as: "execute", "public", "condemn"
   */
  private String buildSample(String[] keywords) {
    String result = "\"" + keywords[0] + "\"";
    for (int i = 1; i < keywords.length && i < 3; i++) {
      result += ", \"" + keywords[i] + "\"";
    }
    return result;
  }

  /**
   * Returns whether this room contains a hidden exploration item.
   *
   * @return true if an explore item has been defined for this room
   */
  public boolean hasExploreItem() {
    return this.exploreItem != null;
  }

  /**
   * Returns the hidden item available through city exploration.
   *
   * @return the Item object, or null if this room has no explore path
   */
  public Item getExploreItem() {
    return this.exploreItem;
  }

  /**
   * Returns the full keyword array for Choice A.
   *
   * @return String array of trigger words for path A
   */
  public String[] getKeywordsA() {
    return this.keywordsA;
  }

  /**
   * Returns the full keyword array for Choice B.
   *
   * @return String array of trigger words for path B
   */
  public String[] getKeywordsB() {
    return this.keywordsB;
  }

  /**
   * Polymorphic decision method resolving the consequence of Option A.
   *
   * @param stats references the metric storage object being modified
   */
  public abstract void executeChoiceA(PlayerStats stats);

  /**
   * Polymorphic decision method resolving the consequence of Option B.
   *
   * @param stats references the metric storage object being modified
   */
  public abstract void executeChoiceB(PlayerStats stats);
}
