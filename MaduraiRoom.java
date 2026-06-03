/**
 * Hardship Stage 2 Velvet Glove variant implementation set within Madurai.
 *
 * Manages southern diplomatic autonomous negotiations surrounding the Sangam academy.
 * Extends Room base class and supplies baseline choices.
 */
public class MaduraiRoom extends Room {
  /**
   * Sets the scholastic Sangam academy negotiations choices.
   */
  public MaduraiRoom() {
    this.title = "Hardship 2, Velvet Glove - The Sangam Concession in Madurai";
    this.contextText = "You travel to the cultural heart of Madurai to win the allegiance of the skeptical southern chieftains before northern empires strike. They demand autonomy and funding for the Sangam (scholastic academy) in exchange for troops.";
    this.optionA_Text = "Grant full political autonomy, lower southern imperial taxes, and recognize the local prince as a co-ruler.";
    this.optionB_Text = "Refuse political autonomy, but offer to fully fund a massive temple expansion and host a grand pan-Tamil cultural festival.";
    this.keywordsA = new String[]{"autonomy", "grant", "self-rule", "recognize", "prince",
        "rights", "sovereignty", "independent", "co-ruler", "govern", "concede", "freedom"};
    this.keywordsB = new String[]{"festival", "temple", "cultural", "celebrate", "art",
        "spectacle", "ceremony", "feast", "expand", "build", "fund", "grand"};
    this.closingQuestion = "The southern chieftains have traveled far to hear your answer. What terms do you offer them?";
  }

  @Override
  public void executeChoiceA(PlayerStats stats) {
    stats.updateStats(25, -20, 30, 40, 15, 
        "Pandya regional arts are celebrated, unlocking elite southern cavalry units, though direct crown tax revenues fall.");
  }

  @Override
  public void executeChoiceB(PlayerStats stats) {
    stats.updateStats(50, -35, 10, 15, 20, 
        "Madurai becomes a global cultural beacon. High festive morale sweeps the population, but massive festival staging costs deplete funds.");
  }
}