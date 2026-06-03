/**
 * Hardship Stage 5 final battle scenario implementation.
 *
 * Resolves the final siege conflict before signaling the ultimate chronicle outcome checks.
 * Extends Room base class and handles final stat changes.
 */
public class SiegeBreakerRoom extends Room {
  /**
   * Constructs the tactical siege breaker context options.
   */
  public SiegeBreakerRoom() {
    this.title = "Hardship 5 - The Final Battle (The Siege Breaker)";
    this.contextText = "The main Western Chalukya force surrounds your current stronghold. Siege engines pound the walls, and resources are critically low. You must break the siege tonight.";
    this.optionA_Text = "Lead an elite strike force through secret routes at midnight to execute a direct decapitation strike on the Chalukya Emperor's command tent. [The Night Raid]";
    this.optionB_Text = "Rig the city's outer rings with oil and pitch, feign a retreat to lure the enemy vanguard inside, and ignite the traps, sacrificing a section of the city. [The Scorched Rampart]";
    this.keywordsA = new String[]{"raid", "night", "strike", "decapitation", "midnight",
        "commando", "sneak", "tent", "command", "surgical", "infiltrate", "assassinate"};
    this.keywordsB = new String[]{"rampart", "oil", "pitch", "trap", "lure",
        "ignite", "sacrifice", "rig", "torch", "scorch", "bait", "fire"};
    this.closingQuestion = "Tonight may be your only chance to break this siege. What is your plan?";
    this.exploreItem = new Item(
        "Chalukya Supply Map",
        "Slipping through the outer rubble under cover of darkness you find an abandoned Chalukya officer's satchel. "
            + "Inside: detailed maps of their supply lines and the position of their reserve forces.",
        "supply",
        "You redirect a flanking unit along the enemy supply route. The disruption throws their logistics into disarray and the vanguard hesitates — "
            + "but using captured intelligence earns quiet whispers of dishonor among your own commanders.",
        0, 0, 20, -5, 0
    );
  }

  @Override
  public void executeChoiceA(PlayerStats stats) {
    stats.updateStats(10, 10, 20, 15, 0, 
        "The midnight commando strike succeeds brilliantly! The enemy command structure is thrown into complete chaos.");
  }

  @Override
  public void executeChoiceB(PlayerStats stats) {
    stats.updateStats(-20, -20, 35, -15, -25, 
        "The explosive traps ignite in a horrific inferno. The vanguard is wiped out, but a massive sector of your city is reduced to ash.");
  }
}