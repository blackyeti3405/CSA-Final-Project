/**
 * Hardship Stage 1 scenario implementation set within Thanjavur.
 *
 * Handles the initial assassination attempt and establishes the primary global timeline route.
 * Extends Room base class and supplies baseline constructor mappings.
 */
public class ThanjavurPlotRoom extends Room {
  /**
   * Initializes context and keywords configurations for Stage 1.
   */
  public ThanjavurPlotRoom() {
    this.title = "Hardship 1 - The Assassination Plot in Thanjavur";
    this.contextText = "An assassination plot takes place against you and it is revealed a high-ranking member of the Aimperungkulu orchestrated it with backing from a rival foreign superpower.";
    this.optionA_Text = "Publicly execute suspected councilmember without a trial. Launches a brutal, paranoid purge of nobility to root out remaining spies.";
    this.optionB_Text = "Choose shadows over public execution. Keep the betrayal a secret and use your spy network to neutralize the threat, working with Sabhas (local councils) and merchant guilds to strengthen the empire from within.";
    this.keywordsA = new String[]{"execute", "public", "punish", "condemn", "trial",
        "judgment", "hang", "arrest", "example", "sentence", "guilt", "parade"};
    this.keywordsB = new String[]{"spy", "shadow", "secret", "covert", "network",
        "silence", "infiltrate", "undercover", "quiet", "hidden", "agent", "dark"};
    this.closingQuestion = "A traitor sits within your inner circle and a foreign power stands behind the blade. What is your response?";
  }

  @Override
  public void executeChoiceA(PlayerStats stats) {
    stats.updateStats(-10, -5, 20, -10, -15, 
        "Your public executions create a wave of terror. Fear stifles the arts and noble estates, but martial law reinforces immediate troop deployments.");
  }

  @Override
  public void executeChoiceB(PlayerStats stats) {
    stats.updateStats(10, 15, -5, 15, 10, 
        "Covert actions neutralize the threat quietly. Public stability boosts poetic scholarship, and merchant guilds trade with high confidence.");
  }
}