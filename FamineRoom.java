/**
 * Hardship Stage 3 Universal Crisis scenario tracking Mass Famine.
 *
 * Reads path variables passed from the engine to execute conditional metric transformations.
 * Extends Room base class and handles state branch calculations.
 */
public class FamineRoom extends Room {
  private boolean isIronFistPath;

  /**
   * Contextually shapes the famine crisis environment parameters.
   *
   * @param isIronFistPath true if active route is Iron Fist, false otherwise
   */
  public FamineRoom(boolean isIronFistPath) {
    this.isIronFistPath = isIronFistPath;
    this.title = "Hardship 3 - Mass Famine (The Universal Crisis)";
    this.contextText = "A catastrophic drought strikes the empire, causing crops to fail. Granaries are located in the secure inland temple-cities. The player faces a universal decision, but consequences match your path.";
    this.optionA_Text = "Keep the emergency grain reserves locked in the imperial capitals and military hubs to ensure the army remains fed and combat-ready. [Prioritize Core]";
    this.optionB_Text = "Completely empty the imperial granaries, distributing food evenly to all citizens and peasants, putting the military on survival rations. [Feed the Masses]";
    this.keywordsA = new String[]{"core", "army", "military", "soldiers", "reserve",
        "guard", "protect", "strategic", "troops", "lock", "secure", "capitol"};
    this.keywordsB = new String[]{"masses", "people", "distribute", "share", "civilians",
        "citizens", "feed", "everyone", "equal", "hungry", "open", "all"};
    this.closingQuestion = "The drought tightens and the granaries hold what little remains. Who receives the grain?";
  }

  @Override
  public void executeChoiceA(PlayerStats stats) {
    // Branching conditional evaluation driven by early gameplay choice pathways
    if (this.isIronFistPath) {
      stats.updateStats(0, -15, -20, 0, -25,
          "The starving port workers riot and sabotage the docks. The army holds its grain but civilian unrest spreads through every quarter of the city.");
    } else {
      stats.updateStats(0, 15, -15, -30, -10,
          "Chieftains feel coldness and close borders, alienating foreign allies, but high public trust prevents open revolt.");
    }
  }

  @Override
  public void executeChoiceB(PlayerStats stats) {
    // Branching conditional evaluation driven by early gameplay choice pathways
    if (this.isIronFistPath) {
      stats.updateStats(10, -15, -20, 0, 30,
          "Your unexpected act of generosity shocks the empire. Fear slowly turns to grudging respect as famine is averted, though frontier garrisons go hungry.");
    } else {
      stats.updateStats(30, -40, -10, 20, 40,
          "This cements your status as a divine ruler with unrivaled public love and foreign admiration, though you face total bankruptcy.");
    }
  }
}