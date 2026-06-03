/**
 * Hardship Stage 4 scenario implementation for Srirangam.
 *
 * Triggered if the player chose to feed the masses during the famine stage.
 * Extends Room base class and computes tactical options.
 */
public class SrirangamRoom extends Room {
  private boolean isIronFistPath;

  /**
   * Initializes Srirangam tactical combat options.
   *
   * @param isIronFistPath tracking orientation path value
   */
  public SrirangamRoom(boolean isIronFistPath) {
    this.isIronFistPath = isIronFistPath;
    this.title = "Hardship 4, Prioritize People - The Sacred Labyrinth Defense in Srirangam";
    this.contextText = "Your treasury is low and troops are physically weak, but the population loves you. You fall back to Srirangam, a river-island temple-city with seven concentric walls.";
    this.optionA_Text = "Order a scorched-earth retreat, burning surrounding fields and pulling all local peasants inside the temple walls as refugees.";
    this.optionB_Text = "Keep the temple grounds clear and use your remaining forces to launch a high-risk guerilla ambush at the river crossing.";
    this.keywordsA = new String[]{"scorched", "burn", "retreat", "pull", "withdraw",
        "refuge", "walls", "fortify", "shelter", "inside", "defensive", "peasants"};
    this.keywordsB = new String[]{"ambush", "guerrilla", "river", "strike", "attack",
        "flank", "surprise", "intercept", "cross", "offensive", "assault", "trap"};
    this.closingQuestion = "The enemy approaches the river crossing and your forces are stretched thin. What is your command?";
    this.exploreItem = new Item(
        "Temple Battle Hymns",
        "In Srirangam's inner sanctum an elderly priest whispers ancient battle hymns once sung by defenders who held these walls against impossible odds. "
            + "He teaches you every word.",
        "hymns",
        "At dawn you lead your troops in the ancient battle hymns. The sound rises from within the great temple walls and every soldier stands straighter. "
            + "Morale surges through the ranks.",
        10, 0, 15, 0, 5
    );
  }

  @Override
  public void executeChoiceA(PlayerStats stats) {
    if (this.isIronFistPath) {
      stats.updateStats(-20, 0, 15, 0, -40, 
          "Forced refugees inside cramped temple grounds harbor intense resentment. Disease breaks out and dissent weakens social order.");
    } else {
      stats.updateStats(25, 0, 30, 0, -10, 
          "Peasants willingly sacrifice their property for the empire, helping to fortify internal defensive maze walls.");
    }
  }

  @Override
  public void executeChoiceB(PlayerStats stats) {
    if (this.isIronFistPath) {
      stats.updateStats(0, 0, -35, 0, 0, 
          "Your unmotivated troops completely lack morale. The high-risk river ambush fails disastrously, resulting in heavy casualties.");
    } else {
      stats.updateStats(20, 0, 40, 10, 10, 
          "Inspired by your history of kindness, troops fight fanatically, totally crushing the enemy vanguard at the crossing.");
    }
  }
}