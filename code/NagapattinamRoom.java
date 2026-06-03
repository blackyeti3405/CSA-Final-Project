/**
 * Hardship Stage 2 Iron Fist variant implementation set within Nagapattinam.
 *
 * Details severe coastline maritime conscription measures used to aggressively build fleet numbers.
 * Extends Room base class and handles specific resource adjustments.
 */
public class NagapattinamRoom extends Room {
  /**
   * Formulates parameters mapping the naval yards conscription crisis.
   */
  public NagapattinamRoom() {
    this.title = "Hardship 2, Iron Fist - The Conscription Mandate at Nagapattinam";
    this.contextText = "To prepare for your forced overseas campaigns, you travel to the main naval base at Nagapattinam. The shipyards require a massive surge in labor and crews immediately.";
    this.optionA_Text = "Seize all merchant vessels for military conversion and force local fishermen and sailors into naval conscription.";
    this.optionB_Text = "Drain the royal treasury to offer massive bounties for volunteers and hire foreign mercenaries, leaving merchant ships untouched.";
    this.keywordsA = new String[]{"seize", "conscript", "force", "draft", "commandeer",
        "mandatory", "compel", "press", "impress", "mobilize", "take", "demand"};
    this.keywordsB = new String[]{"treasury", "bounty", "volunteer", "pay", "hire",
        "recruit", "offer", "wage", "incentive", "buy", "fund", "reward"};
    this.closingQuestion = "The shipyards stand idle and your campaign timeline is slipping. What are your orders?";
  }

  @Override
  public void executeChoiceA(PlayerStats stats) {
    stats.updateStats(-10, -15, 35, -10, -5,
        "A fearsome armada rises within weeks. Coastal trade is disrupted and families are strained, but the iron military grip keeps the streets tense rather than ablaze.");
  }

  @Override
  public void executeChoiceB(PlayerStats stats) {
    stats.updateStats(0, -20, 20, 0, 10,
        "Generous bounties draw a motivated fleet and merchant ships remain untouched. Defensive work creates jobs, though the crown's reserves are heavily drained.");
  }
}