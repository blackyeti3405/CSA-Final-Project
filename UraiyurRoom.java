/**
 * Hardship Stage 6 scenario implementation set within Uraiyur.
 *
 * The ancient Chola heartland hosts one of two diplomatic or cultural reckonings
 * depending on how the player resolved the siege in Stage 5.
 * Both situations focus on diplomacy, culture, and wealth over military expansion.
 * Extends Room base class and includes a hidden exploration path unique to each situation.
 */
public class UraiyurRoom extends Room {
  private boolean isNightRaidPath;

  /**
   * Configures the Uraiyur scenario based on the Stage 5 outcome.
   *
   * @param stage5WasNightRaid true if the player executed the Night Raid in Stage 5,
   *                           false if they used the Scorched Rampart
   */
  public UraiyurRoom(boolean stage5WasNightRaid) {
    this.isNightRaidPath = stage5WasNightRaid;

    if (stage5WasNightRaid) {
      // Situation A: diplomatic summit following surgical victory
      this.title = "Hardship 6, Night Raid Aftermath - The Diplomatic Reckoning at Uraiyur";
      this.contextText = "Your midnight strike shattered the Chalukya command structure. "
          + "Envoys have traveled to Uraiyur, the ancient Chola heartland and seat of Tamil scholarship, seeking terms of peace. "
          + "The city's Sangam scholars and guild masters watch every word you speak. "
          + "How you handle this moment will echo across the subcontinent for generations.";
      this.optionA_Text = "Offer generous terms — release all prisoners, restore seized goods, and sign a formal trade alliance. "
          + "Show the world what Tamil magnanimity looks like in victory.";
      this.optionB_Text = "Demand steep war reparations and ongoing tribute payments, humiliating the Chalukya into a generation of servitude.";
      this.keywordsA = new String[]{"mercy", "generous", "peace", "release", "treaty",
          "alliance", "forgive", "magnanimous", "pardon", "kind", "lenient", "clemency"};
      this.keywordsB = new String[]{"tribute", "reparations", "demand", "payment",
          "humiliate", "punish", "exact", "squeeze", "harsh", "compensation", "terms", "fine"};
      this.closingQuestion = "The Chalukya envoys have traveled far to hear your terms. What do you say to them?";
      this.exploreItem = new Item(
          "Sangam Poetry Scroll",
          "An elderly street poet presses a hand-copied scroll of ancient Sangam verses into your hands. "
              + "'Let the old words speak what swords cannot,' he says, and vanishes into the crowd.",
          "scroll",
          "You recite ancient Sangam verses before both delegations. The shared literary heritage moves even the Chalukya envoys. "
              + "Tension dissolves into something almost resembling mutual respect.",
          10, 0, 0, 15, 5
      );
    } else {
      // Situation B: cultural and economic reckoning after the scorched tactic
      this.title = "Hardship 6, Scorched Rampart Aftermath - The Reckoning at Uraiyur";
      this.contextText = "The siege is broken, but a district of your city lies in ash. "
          + "You travel to Uraiyur — untouched by war — but rumors of your scorched tactic have spread ahead of you. "
          + "Cultural leaders, foreign merchants, and Tamil guild masters watch you with unease. "
          + "The empire waits for a sign of what comes next.";
      this.optionA_Text = "Commission a massive rebuilding program for the burned district — hire Uraiyur's finest craftsmen, "
          + "fund new temples, and restore the merchant quarter from the ground up.";
      this.optionB_Text = "Declare the ruins a nationalist memorial. Turn collective grief into pride "
          + "and redirect resources toward rebuilding military strength for future campaigns.";
      this.keywordsA = new String[]{"rebuild", "restore", "reconstruct", "commission",
          "artisan", "renew", "repair", "construct", "revive", "fix", "build", "invest"};
      this.keywordsB = new String[]{"memorial", "symbol", "pride", "nationalism",
          "mourn", "honor", "remember", "sacrifice", "glorify", "monument", "martyr", "legacy"};
      this.closingQuestion = "The empire watches for a sign of what comes next. How do you lead them forward from the ashes?";
      this.exploreItem = new Item(
          "Merchant's Hidden Ledger",
          "In the ashes of the merchant quarter you uncover a waterproof chest belonging to a Chalukya trader who fled. "
              + "Inside: a detailed ledger mapping secret foreign trade routes your merchants have never accessed.",
          "ledger",
          "Your merchants quietly follow the stolen trade routes, securing early commercial advantages in distant markets — "
              + "but whispers of the theft soon reach foreign envoys and damage your reputation abroad.",
          0, 15, 0, -10, 5
      );
    }
  }

  @Override
  public void executeChoiceA(PlayerStats stats) {
    if (this.isNightRaidPath) {
      stats.updateStats(15, 20, -10, 25, 10,
          "Your mercy in victory echoes across the subcontinent. Trade routes open, scholars are celebrated in foreign courts, "
              + "and the peace treaty draws new merchant traffic to Tamil ports.");
    } else {
      stats.updateStats(20, -25, 0, 15, 20,
          "Uraiyur's master craftsmen flood the burned district. New temples rise from the rubble, merchants return, "
              + "and the displaced find homes — though the crown's treasury bleeds for every stone laid.");
    }
  }

  @Override
  public void executeChoiceB(PlayerStats stats) {
    if (this.isNightRaidPath) {
      stats.updateStats(-10, 35, 10, -20, 5,
          "Chalukya tribute gold floods the treasury and their military capacity is clipped for a generation. "
              + "But the bitter terms plant seeds of lasting enmity that no treaty paper can fully contain.");
    } else {
      stats.updateStats(10, -5, 20, -15, -10,
          "The memorial becomes a powerful symbol of nationalist resolve. New soldiers volunteer in droves. "
              + "But displaced citizens remain without homes and foreign merchants continue to stay away.");
    }
  }
}
