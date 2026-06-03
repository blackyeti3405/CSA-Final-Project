/**
 * Entry point for the Trail of Tamil adventure simulation game.
 *
 * Demonstrates execution management by bootstrapping the main GameEngine system.
 * Used by the runtime environment to start up the player interactive session loop.
 */
public class Main {
  /**
   * Main method serving as the execution entry point.
   *
   * @param args command-line arguments (not utilized)
   */
  public static void main(String[] args) {
    GameEngine game = new GameEngine();
    game.startGame();
  }
}