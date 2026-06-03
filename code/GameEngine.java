import java.util.ArrayList;
import java.util.Scanner;

/**
 * Coordinates and acts as the central orchestrator engine executing runtime loops.
 *
 * Manages game sequences, path tracking booleans, the item system, and stat-check criteria.
 * Communicates directly with Main to kick off code loops, updating metrics through PlayerStats.
 */
public class GameEngine {
  private PlayerStats stats;
  private ArrayList<Room> gameRoute;
  private int currentStage;
  private boolean isIronFistPath;
  private boolean stage5WasNightRaid;
  private boolean stage6WasChoiceA;
  private boolean isGameOver;
  private Item heldItem;
  private boolean hasExploredCurrentRoom;
  private boolean sceneNeedsDisplay;

  /**
   * Prepares default collections framework and operational variables.
   */
  public GameEngine() {
    this.stats = new PlayerStats();
    this.gameRoute = new ArrayList<Room>();
    this.currentStage = 1;
    this.isIronFistPath = false;
    this.stage5WasNightRaid = false;
    this.stage6WasChoiceA = false;
    this.isGameOver = false;
    this.heldItem = null;
    this.hasExploredCurrentRoom = false;
    this.sceneNeedsDisplay = true;
  }

  /**
   * Orchestrates environment initialization and boots the input tracking pipeline.
   */
  public void startGame() {
    Display.typeInstant(Display.c("================================================================================", Display.BOLD_BLUE));
    Display.typeFast(Display.c("                       TRAIL OF TAMIL: THE VENDAR                              ", Display.BOLD_BLUE));
    Display.typeInstant(Display.c("================================================================================", Display.BOLD_BLUE));
    Display.pause(200);
    Display.typeSlow(Display.c("Welcome, Great Vendar (King), to the 16th Century Tamil Empire.", Display.ITALIC_BLUE));
    Display.typeSlow(Display.c("Your affairs are counseled by the Aimperungkulu and the Enperayam.", Display.ITALIC_BLUE));
    Display.typeSlow(Display.c("\nEvery decision you make will reshape the Empire's destiny.", Display.ITALIC_BLUE));
    Display.pause(300);
    Display.type(Display.c("Speak your commands naturally. The council will interpret your words.", Display.GREEN));
    Display.type(Display.c("Type 'hint' if you need guidance. Type 'quit' to abdicate.", Display.GREEN));
    Display.typeInstant(Display.c("================================================================================", Display.BOLD_BLUE));

    this.gameRoute.add(new ThanjavurPlotRoom());
    Scanner inputScanner = new Scanner(System.in);

    while (!this.isGameOver) {
      Room activeRoom = this.gameRoute.get(this.gameRoute.size() - 1);

      // Scoreboard reprints only when stats have actually changed
      if (this.stats.isDirty()) {
        this.stats.printStatusBoard();
      }

      // Item reminder always shown so the player never forgets what they are carrying
      if (this.heldItem != null) {
        Display.typeInstant(Display.c("  [Carrying: " + this.heldItem.getName()
            + " — speak \"" + this.heldItem.getUseKeyword() + "\" to use it]", Display.BOLD_GREEN));
      }

      // Scene (context + question) only shown when entering a new room or using an item
      if (this.sceneNeedsDisplay) {
        activeRoom.displayScene();
        this.sceneNeedsDisplay = false;
      }

      System.out.print("\n" + Display.c("[Vendar Command]", Display.BOLD_BLUE) + " > ");
      if (!inputScanner.hasNextLine()) {
        break;
      }
      String currentInput = inputScanner.nextLine();
      processInput(currentInput, activeRoom);
      checkGameOver();
    }
    inputScanner.close();
    Display.type(Display.c("\nSimulation safely terminated.", Display.ITALIC_BLUE));
  }

  /**
   * Evaluates text strings via keyword array searches to dispatch the correct game action.
   * Processes special commands (quit, hint, explore, item use) before main choice matching.
   * Hint and explore do not trigger a scene redisplay; only stage advances and item use do.
   *
   * @param userInput   direct text string input extracted from the scanner
   * @param currentRoom reference pointer to the active room instance
   */
  public void processInput(String userInput, Room currentRoom) {
    String cleaned = userInput.trim().toLowerCase();

    // Session exit command
    if (cleaned.equals("quit")) {
      Display.type(Display.c("Abdicated the throne. Goodbye, Vendar.", Display.ITALIC_BLUE));
      this.isGameOver = true;
      return;
    }

    // Hint — shows option descriptions and keyword samples; scene is NOT reshown
    if (cleaned.equals("hint")) {
      currentRoom.displayHint();
      return;
    }

    // Explore — available once per room in Stages 4, 5, and 6; scene is NOT reshown
    if (cleaned.contains("explore")) {
      if (!currentRoom.hasExploreItem()) {
        Display.type(Display.c("\nThis is not the time for exploration. The empire demands your command.", Display.RED));
      } else if (this.hasExploredCurrentRoom) {
        Display.type(Display.c("\nYou have already searched the city. You must now make your decision.", Display.RED));
      } else {
        this.heldItem = currentRoom.getExploreItem();
        this.hasExploredCurrentRoom = true;
        Display.typeSlow(Display.c("\n[EXPLORATION] " + this.heldItem.getFindNarrative(), Display.ITALIC_GREEN));
        Display.type(Display.c("\nYou now carry: " + this.heldItem.getName(), Display.BOLD_GREEN));
        Display.type(Display.c("Speak \"" + this.heldItem.getUseKeyword()
            + "\" at any point before leaving this city to use it.", Display.GREEN));
      }
      return;
    }

    // Item use — checked before main choices to prevent keyword shadowing
    // Using an item changes stats and triggers a scene redisplay so the player can decide
    if (this.heldItem != null
        && cleaned.contains(this.heldItem.getUseKeyword().toLowerCase())) {
      Display.type(Display.c("\n[ITEM USED: " + this.heldItem.getName() + "]", Display.BOLD_GREEN));
      this.stats.updateStats(
          this.heldItem.getC(), this.heldItem.getW(), this.heldItem.getM(),
          this.heldItem.getD(), this.heldItem.getL(),
          this.heldItem.getUseNarrative()
      );
      this.heldItem = null;
      this.sceneNeedsDisplay = true;
      return;
    }

    // Main action keyword matching — checks full arrays for each path
    if (matchesKeywords(cleaned, currentRoom.getKeywordsA())) {
      currentRoom.executeChoiceA(this.stats);
      advanceStage(true);
    } else if (matchesKeywords(cleaned, currentRoom.getKeywordsB())) {
      currentRoom.executeChoiceB(this.stats);
      advanceStage(false);
    } else {
      Display.type(Display.c("\nThe council does not understand that command, my Vendar. "
          + "Speak your intent clearly. (Type 'hint' for guidance.)", Display.RED));
    }
  }

  /**
   * Checks whether the player's cleaned input contains any keyword from the given array.
   *
   * @param cleaned  the lowercased, trimmed input string
   * @param keywords the array of trigger words to test against
   * @return true if at least one keyword is found within the input
   */
  private boolean matchesKeywords(String cleaned, String[] keywords) {
    for (int i = 0; i < keywords.length; i++) {
      if (cleaned.contains(keywords[i].toLowerCase())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Advances the stage sequence, selects the next room based on accumulated path flags,
   * discards any unused held item, and resets the exploration and scene display flags.
   *
   * @param choseOptionA true if Option A was triggered, false if Option B was selected
   */
  private void advanceStage(boolean choseOptionA) {
    // Discard any unused item before moving to the next city
    if (this.heldItem != null) {
      Display.type(Display.c("\n[You leave the " + this.heldItem.getName()
          + " behind as you depart for the next city.]", Display.RED));
      this.heldItem = null;
    }
    this.hasExploredCurrentRoom = false;
    this.sceneNeedsDisplay = true;

    if (this.currentStage == 1) {
      this.isIronFistPath = choseOptionA;
      this.currentStage = 2;
      if (this.isIronFistPath) {
        this.gameRoute.add(new NagapattinamRoom());
      } else {
        this.gameRoute.add(new MaduraiRoom());
      }
    } else if (this.currentStage == 2) {
      this.currentStage = 3;
      this.gameRoute.add(new FamineRoom(this.isIronFistPath));
    } else if (this.currentStage == 3) {
      this.currentStage = 4;
      if (choseOptionA) {
        this.gameRoute.add(new KanchipuramRoom(this.isIronFistPath));
      } else {
        this.gameRoute.add(new SrirangamRoom(this.isIronFistPath));
      }
    } else if (this.currentStage == 4) {
      this.currentStage = 5;
      this.gameRoute.add(new SiegeBreakerRoom());
    } else if (this.currentStage == 5) {
      this.stage5WasNightRaid = choseOptionA;
      this.currentStage = 6;
      this.gameRoute.add(new UraiyurRoom(this.stage5WasNightRaid));
    } else if (this.currentStage == 6) {
      this.stage6WasChoiceA = choseOptionA;
      this.currentStage = 7;
      // Encode both Stage 5 and Stage 6 choices into a single situation integer:
      // 0=NightRaid+A, 1=NightRaid+B, 2=ScorchedRampart+A, 3=ScorchedRampart+B
      int situation = (this.stage5WasNightRaid ? 0 : 2) + (this.stage6WasChoiceA ? 0 : 1);
      this.gameRoute.add(new KaveripattinamRoom(situation));
    } else if (this.currentStage == 7) {
      triggerEndingSequence();
    }
  }

  /**
   * Scans all stat thresholds after every turn and ends the game on a violation.
   */
  public void checkGameOver() {
    if (!this.isGameOver && this.stats.isMinThresholdViolated()) {
      this.isGameOver = true;
      Display.pause(300);
      Display.typeFast(Display.c("\n=== GAME OVER: YOUR EMPIRE HAS COLLAPSED ===", Display.BOLD_RED));
    }
  }

  /**
   * Evaluates final stats and renders the appropriate chronicle ending text.
   * PAX_TAMILAKAM uses bold green; KAVERI_GOLDEN_AGE uses bold blue; BLOODSTAINED_THRONE uses bold red.
   */
  private void triggerEndingSequence() {
    this.isGameOver = true;
    this.stats.printStatusBoard();
    Display.pause(500);
    Display.typeInstant(Display.c("\n=======================================================", Display.BOLD_BLUE));
    Display.typeFast(Display.c("                 FINAL CHRONICLE ENDING                 ", Display.BOLD_BLUE));
    Display.typeInstant(Display.c("=======================================================", Display.BOLD_BLUE));
    String endingCode = this.stats.getDominantEnding();

    if (endingCode.equals("PAX_TAMILAKAM")) {
      Display.typeFast(Display.c("Ending 1 — Pax Tamilakam", Display.BOLD_GREEN));
      Display.pause(300);
      Display.typeSlow(Display.c("Your enemies are forced into a historic peace treaty. The Tamil kingdoms fully "
          + "unify under your banner, initiating an enduring golden age of cultural renown, "
          + "diplomatic mastery, and the prosperity that flows from a world at peace with you.", Display.ITALIC_GREEN));
    } else if (endingCode.equals("KAVERI_GOLDEN_AGE")) {
      Display.typeFast(Display.c("Ending 2 — The Kaveri Golden Age", Display.BOLD_BLUE));
      Display.pause(300);
      Display.typeSlow(Display.c("The wars and politics of the mainland give way to an era of commerce and cultural power. "
          + "Kaveripattinam rises as the greatest trading port in the ancient world. "
          + "Tamil merchants, scholars, and artisans carry your empire's name to Rome, Arabia, and Southeast Asia. "
          + "Your legacy is not conquest — it is the age of prosperity you built in its place.", Display.ITALIC_BLUE));
    } else {
      Display.typeFast(Display.c("Ending 3 — The Bloodstained Throne", Display.BOLD_RED));
      Display.pause(300);
      Display.typeSlow(Display.c("The siege is broken and the realm survives, but the land is scarred. "
          + "Alienated guilds and bitter regional factions splinter into open rebellion. "
          + "You remain on a heavily fortified throne, alone in the wreckage of everything you chose to sacrifice.", Display.ITALIC));
    }
    Display.typeInstant(Display.c("=======================================================", Display.BOLD_BLUE));
  }
}
