/**
 * Tracks and manages the quantitative state metrics of the Tamil Empire.
 *
 * Main data container tracking core integer metrics with clamp limits and critical triggers.
 * Interacts directly with GameEngine to modify stats and Room subclasses during option resolution.
 */
public class PlayerStats {
  private int culture;
  private int wealth;
  private int military;
  private int diplomacy;
  private int livingStatus;
  private boolean dirty;

  /**
   * Initializes player metrics to default historical baselines.
   * dirty is set true so the board prints once on game start before any action is taken.
   */
  public PlayerStats() {
    this.culture = 50;
    this.wealth = 50;
    this.military = 50;
    this.diplomacy = 50;
    this.livingStatus = 50;
    this.dirty = true;
  }

  /**
   * Returns whether stats have changed since the last time the board was printed.
   *
   * @return true if a stat update has occurred since the last printStatusBoard call
   */
  public boolean isDirty() {
    return this.dirty;
  }

  /**
   * Modifies empire metrics and prints a narrative justification.
   *
   * @param c change value applied to Culture
   * @param w change value applied to Wealth
   * @param m change value applied to Military Strength
   * @param d change value applied to Diplomacy
   * @param l change value applied to Civilian Status of Living
   * @param justification narrative explanation describing why metrics shifted
   */
  public void updateStats(int c, int w, int m, int d, int l, String justification) {
    this.culture += c;
    this.wealth += w;
    this.military += m;
    this.diplomacy += d;
    this.livingStatus += l;

    // Manual boundaries checks - clamping values between 0 and 100 per spec limits
    if (this.culture > 100) { this.culture = 100; }
    if (this.wealth > 100) { this.wealth = 100; }
    if (this.military > 100) { this.military = 100; }
    if (this.diplomacy > 100) { this.diplomacy = 100; }
    if (this.livingStatus > 100) { this.livingStatus = 100; }

    if (this.culture < 0) { this.culture = 0; }
    if (this.wealth < 0) { this.wealth = 0; }
    if (this.military < 0) { this.military = 0; }
    if (this.diplomacy < 0) { this.diplomacy = 0; }
    if (this.livingStatus < 0) { this.livingStatus = 0; }

    this.dirty = true;
    Display.pause(400);
    Display.typeInstant("\n" + Display.c("--- CONSEQUENCE & JUSTIFICATION ---", Display.BOLD));
    Display.typeSlow(Display.c(justification, Display.ITALIC));
    Display.type("Changes applied: Culture(" + Display.delta(c) + "), "
        + "Wealth(" + Display.delta(w) + "), "
        + "Military(" + Display.delta(m) + "), "
        + "Diplomacy(" + Display.delta(d) + "), "
        + "Living Status(" + Display.delta(l) + ")");
  }

  /**
   * Displays the visual status dashboard of the empire metrics.
   * Header and borders in bold blue; stat values color-coded by proximity to minimum;
   * minimum thresholds labeled in red for quick danger assessment.
   */
  public void printStatusBoard() {
    Display.typeInstant("\n" + Display.c("==================================================", Display.BOLD_BLUE));
    Display.typeFast(Display.c("         CURRENT TAMIL EMPIRE STATE BOARD         ", Display.BOLD_BLUE));
    Display.typeInstant(Display.c("==================================================", Display.BOLD_BLUE));
    Display.typeFast("  * Empire Culture      : " + Display.statColor(this.culture, 11)
        + " / 100  " + Display.c("(Min: 11)", Display.RED));
    Display.typeFast("  * Empire Wealth       : " + Display.statColor(this.wealth, 6)
        + " / 100  " + Display.c("(Min: 6)", Display.RED));
    Display.typeFast("  * Military Strength   : " + Display.statColor(this.military, 15)
        + " / 100  " + Display.c("(Min: 15)", Display.RED));
    Display.typeFast("  * Diplomacy           : " + Display.statColor(this.diplomacy, 1)
        + " / 100  " + Display.c("(Min: 1)", Display.RED));
    Display.typeFast("  * Status of Living    : " + Display.statColor(this.livingStatus, 26)
        + " / 100  " + Display.c("(Min: 26)", Display.RED));
    Display.typeInstant(Display.c("==================================================", Display.BOLD_BLUE));
    this.dirty = false;
  }

  /**
   * Assesses whether any critical empire metric has breached its minimum safe threshold.
   *
   * @return true if a baseline threshold is violated, false otherwise
   */
  public boolean isMinThresholdViolated() {
    // Checking exact thresholds and prints requested verbatim specs statements
    if (this.culture < 11) {
      Display.type(Display.c("\nYou no longer create new contributions in science, art, math, or language. "
          + "You are a complacent empire that has no reason to exist in the world any longer.", Display.BOLD_RED));
      return true;
    }
    if (this.wealth < 6) {
      Display.type(Display.c("\nThe empire is unable to sustain itself and its economy crashes "
          + "leading to a dark age of chaos and violence.", Display.BOLD_RED));
      return true;
    }
    if (this.military < 15) {
      Display.type(Display.c("\nYou are unable to fend off bandits and the Mahal (royal palace) is overrun.", Display.BOLD_RED));
      return true;
    }
    if (this.diplomacy <= 0) {
      Display.type(Display.c("\nYour council overthrows you as you have destroyed previous relations with neighbors, "
          + "something highly valued in Tamil culture.", Display.BOLD_RED));
      return true;
    }
    if (this.livingStatus < 26) {
      Display.type(Display.c("\nThe common people are unhappy with their lives and revolt against you. "
          + "You are powerless against their numbers.", Display.BOLD_RED));
      return true;
    }
    return false;
  }

  /**
   * Evaluates metrics at the end of the game to determine the narrative trajectory path.
   * Three endings are possible, checked in priority order.
   *
   * PAX_TAMILAKAM   - high combined diplomacy, culture, and living (>=175), with adequate wealth
   *                   and a military that has not become the empire's defining trait (< 90).
   * KAVERI_GOLDEN_AGE - high wealth (>=70) and culture (>=55), representing a commercial and
   *                     cultural empire that does not meet the diplomatic threshold for Pax.
   * BLOODSTAINED_THRONE - all other outcomes; military dominance with crippled civil metrics.
   *
   * @return String identifier denoting the specific ending achieved
   */
  public String getDominantEnding() {
    int peacefulScore = this.diplomacy + this.culture + this.livingStatus;

    // Threshold raised from 135 to 175 to account for two additional stages of stat accumulation
    if (peacefulScore >= 175 && this.wealth >= 30 && this.military < 90) {
      return "PAX_TAMILAKAM";
    } else if (this.wealth >= 70 && this.culture >= 55) {
      return "KAVERI_GOLDEN_AGE";
    } else {
      return "BLOODSTAINED_THRONE";
    }
  }
}