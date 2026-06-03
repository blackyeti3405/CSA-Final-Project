/**
 * Hardship Stage 7 scenario implementation set within Kaveripattinam.
 *
 * The great ancient seaport of the Chola dynasty hosts one of four distinct situations
 * determined by the combined choices made in Stages 5 and 6.
 * All four situations focus on commerce, culture, and diplomacy — the final shape of the empire.
 * Stage 7 has no hidden exploration path; the city's fate is decided by direct command.
 * Extends Room base class and evaluates a situation integer to configure the correct scenario.
 */
public class KaveripattinamRoom extends Room {
  private int situation;

  /**
   * Configures the Kaveripattinam scenario based on the compounded Stage 5 and Stage 6 outcomes.
   *
   * @param situation integer encoding: 0=NightRaid+ChoiceA, 1=NightRaid+ChoiceB,
   *                  2=ScorchedRampart+ChoiceA, 3=ScorchedRampart+ChoiceB
   */
  public KaveripattinamRoom(int situation) {
    this.situation = situation;

    if (situation == 0) {
      // Night Raid + Mercy: open harbor, maximum diplomatic goodwill
      this.title = "Hardship 7, Open Harbor - The Law of the Sea at Kaveripattinam";
      this.contextText = "Peace treaties and the prestige of your generous victory have transformed Kaveripattinam "
          + "into the most desired trading port in the known world. Arab, Roman, and Southeast Asian ships crowd every dock. "
          + "The merchant guilds demand you declare the new law of the sea before the harbor overflows with ambition and dispute.";
      this.optionA_Text = "Declare open ports for all nations — no tariffs, no restrictions, free trade as the law of the sea. "
          + "Make Kaveripattinam the neutral cosmopolitan hub of the age.";
      this.optionB_Text = "Establish a royal state trading company — Tamil merchants receive preferential rates and "
          + "exclusive contracts while controlled tariffs regulate foreign goods.";
      this.keywordsA = new String[]{"open", "free", "tariff", "neutral", "cosmopolitan",
          "declare", "liberal", "unrestricted", "access", "welcome", "global", "port"};
      this.keywordsB = new String[]{"company", "state", "preferential", "royal",
          "control", "restrict", "regulate", "charter", "exclusive", "managed", "tax", "protected"};
      this.closingQuestion = "The harbor master awaits the proclamation that will set the law of the sea. What is your ruling?";

    } else if (situation == 1) {
      // Night Raid + Tribute: contested docks, embargo to break
      this.title = "Hardship 7, Contested Docks - The Embargo Crisis at Kaveripattinam";
      this.contextText = "Chalukya tribute gold has flooded Kaveripattinam, but the diplomatic fallout has triggered a partial "
          + "trade embargo by their remaining allies. Foreign ships sit idle and guild masters grow restless. "
          + "You must find a way to break through the commercial blockade before the gold loses its meaning.";
      this.optionA_Text = "Use the tribute gold to assemble a regional trading coalition — "
          + "bribe neighboring chieftains into a Tamil-led economic bloc that bypasses the embargo entirely.";
      this.optionB_Text = "Invest the gold in a powerful state-owned naval fleet to reach distant markets directly, "
          + "making Tamil ships the bridge that hostile middlemen cannot block.";
      this.keywordsA = new String[]{"coalition", "bloc", "bribe", "chieftains", "bypass",
          "alliance", "partner", "regional", "league", "gold", "buy", "diplomatic"};
      this.keywordsB = new String[]{"navy", "fleet", "ships", "naval", "maritime",
          "build", "invest", "sail", "direct", "sea", "route", "vessel"};
      this.closingQuestion = "The tribute gold sits in your vaults while foreign embargoes choke the docks. What do you do with it?";

    } else if (situation == 2) {
      // Scorched Rampart + Rebuild: artisan boom to govern
      this.title = "Hardship 7, Artisan Tide - The Commerce Boom at Kaveripattinam";
      this.contextText = "Uraiyur's craftsmen flooded Kaveripattinam during the great rebuilding, "
          + "and the city has become an unexpected center of cultural and commercial energy. "
          + "Foreign buyers pay extraordinary prices for Tamil silks and bronzes. "
          + "You must decide how to govern this prosperity before it outgrows the empire's control.";
      this.optionA_Text = "Let the guilds self-organize — encourage free artisan markets and keep royal interference to a minimum. "
          + "Trust the craftsmen to build something the crown alone never could.";
      this.optionB_Text = "Nationalize the most valuable craft workshops and declare a royal monopoly on silk and bronze exports. "
          + "Bring the boom under the crown's direct control.";
      this.keywordsA = new String[]{"guilds", "free", "artisan", "craftsmen", "self",
          "independent", "organic", "market", "unregulated", "trust", "allow", "flourish"};
      this.keywordsB = new String[]{"monopoly", "nationalize", "royal", "control",
          "seize", "own", "centralize", "crown", "state", "oversee", "regulate", "claim"};
      this.closingQuestion = "The artisan boom grows beyond any one hand to hold. How do you govern this prosperity?";

    } else {
      // Scorched Rampart + Memorial: nationalist stagnation, reinvention required
      this.title = "Hardship 7, Nationalist Crossroads - The Reinvention of Kaveripattinam";
      this.contextText = "The nationalist fervor you cultivated brought soldiers but frightened away scholars and merchants. "
          + "Kaveripattinam's trade has stagnated and foreign investors remain wary. "
          + "The city that was once the jewel of Tamil commerce sits half-empty. "
          + "You must choose the empire's new direction before the opportunity to change course closes forever.";
      this.optionA_Text = "Host a grand pan-Tamil cultural festival — invite diaspora communities, foreign scholars, and merchants. "
          + "A deliberate and public soft-power renaissance to reopen the world's doors.";
      this.optionB_Text = "Leverage the remaining nationalist energy for aggressive mercantile expansion — "
          + "Tamil traders given naval escorts and key sea routes monopolized by state force.";
      this.keywordsA = new String[]{"festival", "cultural", "renaissance", "celebrate",
          "invite", "soft", "open", "diaspora", "scholar", "arts", "welcome", "gather"};
      this.keywordsB = new String[]{"escort", "force", "naval", "expand", "aggressive",
          "routes", "monopolize", "power", "dominance", "push", "seize", "pressure"};
      this.closingQuestion = "The empire stands at a crossroads. Which direction do you lead it?";
    }
    // Stage 7 has no explore item — the city's fate is decided by direct command alone
  }

  @Override
  public void executeChoiceA(PlayerStats stats) {
    if (this.situation == 0) {
      stats.updateStats(20, 30, -10, 25, 15,
          "Kaveripattinam becomes the crossroads of the ancient world. Every nation sends merchants and scholars. "
              + "The open harbor breeds a cosmopolitan city unlike anything the subcontinent has seen.");
    } else if (this.situation == 1) {
      stats.updateStats(-5, 25, 15, 10, 5,
          "The coalition holds. Embargo routes are bypassed, tributary gold flows, and regional chieftains "
              + "find it profitable to call themselves Tamil allies. A new kind of empire takes shape.");
    } else if (this.situation == 2) {
      stats.updateStats(30, 20, -10, 15, 25,
          "The artisan guilds flourish without restriction. Kaveripattinam's workshops become legendary. "
              + "The empire's cultural soft power begins to rival its military legacy.");
    } else {
      stats.updateStats(35, 15, -15, 30, 20,
          "The festival transforms the city. Scholars debate philosophy by torchlight, merchants negotiate over shared meals, "
              + "and the world remembers why Tamil culture was always something worth trading for.");
    }
  }

  @Override
  public void executeChoiceB(PlayerStats stats) {
    if (this.situation == 0) {
      stats.updateStats(5, 40, 5, -10, 10,
          "The royal trading company turns Kaveripattinam into a controlled but enormously profitable machine. "
              + "Tamil merchants dominate regional commerce while foreign rivals find the terms frustrating.");
    } else if (this.situation == 1) {
      stats.updateStats(10, 20, 25, -5, 10,
          "The Tamil navy cuts through embargo waters and opens new horizons. The fleet becomes a statement of power "
              + "as much as a commercial instrument — rivals take notice.");
    } else if (this.situation == 2) {
      stats.updateStats(-15, 40, 10, -5, -10,
          "The royal monopoly extracts enormous wealth from the artisan boom, but the craftsmen feel the invisible walls close in. "
              + "The creative energy that started it all begins quietly dimming.");
    } else {
      stats.updateStats(-10, 30, 15, -20, 5,
          "Tamil warships escort merchant fleets into disputed waters. Revenue climbs and rivals back down, "
              + "but the world increasingly uses words like 'aggression' and 'piracy' when speaking of Tamil ambition.");
    }
  }
}
