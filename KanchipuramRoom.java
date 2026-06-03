/**
 * Hardship Stage 4 scenario implementation for Kanchipuram.
 *
 * Triggered if the player chose to prioritize the military core during the famine stage.
 * Extends Room base class and manages conditional modifier branches.
 */
public class KanchipuramRoom extends Room {
  private boolean isIronFistPath;

  /**
   * Initializes Kanchipuram war asset collection choices.
   *
   * @param isIronFistPath tracking orientation path value
   */
  public KanchipuramRoom(boolean isIronFistPath) {
    this.isIronFistPath = isIronFistPath;
    this.title = "Hardship 4, Prioritize Core - The Silk Guild War Bonds in Kanchipuram";
    this.contextText = "With your well-fed army, you march north to the frontline city of Kanchipuram to intercept the invading Western Chalukya Empire. You need emergency funding to fortify the walls.";
    this.optionA_Text = "Forcefully requisition the immense treasures of the local silk guilds and monastic estates under a state of total military emergency.";
    this.optionB_Text = "Request a loan from the guilds in exchange for granting them permanent tax-exempt status and high imperial council seats.";
    this.keywordsA = new String[]{"requisition", "seize", "take", "force", "confiscate",
        "commandeer", "emergency", "compel", "strip", "demand", "conscript", "levy"};
    this.keywordsB = new String[]{"loan", "borrow", "negotiate", "deal", "offer",
        "request", "ask", "purchase", "partnership", "council", "tax-exempt", "contract"};
    this.closingQuestion = "The walls of Kanchipuram will not hold without resources. How do you secure what you need?";
    this.exploreItem = new Item(
        "Weaver's Guild Charter",
        "Exploring Kanchipuram's market district you discover an ancient guild charter sealed with the silk-weavers' emblem, left behind in an abandoned merchant stall. "
            + "A document this old could legitimize almost any trade negotiation.",
        "charter",
        "You invoke the ancient charter before the guild elders. The document lends your demands an air of legal precedent stretching back centuries. "
            + "The room shifts — this changes everything.",
        0, 15, 0, 10, 0
    );
  }

  @Override
  public void executeChoiceA(PlayerStats stats) {
    if (this.isIronFistPath) {
      stats.updateStats(0, -30, 25, 0, -15, 
          "Furious at your tyranny, local guilds actively sabotage supply lines, reducing efficiency despite wall fortifications.");
    } else {
      stats.updateStats(-10, 0, -20, 0, -30, 
          "The civilian population feels deeply betrayed by this sudden act of aggression, triggering brutal frontline riots.");
    }
  }

  @Override
  public void executeChoiceB(PlayerStats stats) {
    if (this.isIronFistPath) {
      stats.updateStats(0, 20, 0, -20, 0, 
          "Because your past records inspire little domestic trust, the guilds demand your royal heir as a hostage before releasing assets.");
    } else {
      stats.updateStats(0, 40, 30, 10, 10, 
          "Out of absolute loyalty to your lineage, wealthy silk guilds fund your defenses perfectly, boosting strength.");
    }
  }
}