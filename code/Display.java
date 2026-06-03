/**
 * Provides ANSI terminal formatting constants and typewriter-style print utilities.
 *
 * All color and style codes use standard ANSI SGR escape sequences supported by
 * Unix/macOS terminals. ANSI escape sequences embedded within text are detected and
 * printed atomically so the terminal never receives a partial escape code.
 *
 * NOTE: Thread.sleep is used for the typewriter effect and is not in the AP CSA curriculum.
 * NOTE: ANSI codes may not render on all platforms (e.g., code.org's browser console).
 *       Colors and styles will simply be absent on unsupported platforms without crashing.
 */
public class Display {

  // ── Reset ────────────────────────────────────────────────────────────────────
  public static final String RESET        = "[0m";

  // ── Base colors ──────────────────────────────────────────────────────────────
  public static final String RED          = "[31m";
  public static final String GREEN        = "[32m";
  public static final String BLUE         = "[34m";

  // ── Styles ───────────────────────────────────────────────────────────────────
  public static final String BOLD         = "[1m";
  public static final String ITALIC       = "[3m";

  // ── Bold + color combos ───────────────────────────────────────────────────────
  public static final String BOLD_RED     = "[1;31m";
  public static final String BOLD_GREEN   = "[1;32m";
  public static final String BOLD_BLUE    = "[1;34m";

  // ── Italic + color combos ─────────────────────────────────────────────────────
  public static final String ITALIC_BLUE  = "[3;34m";
  public static final String ITALIC_GREEN = "[3;32m";

  /**
   * Prints text one visible character at a time with the specified delay between characters.
   * ANSI escape sequences (ESC + '[' ... 'm') are detected and printed atomically with
   * no per-character delay so the terminal never receives an incomplete color code.
   *
   * @param text    the text to type out (may contain embedded ANSI codes)
   * @param delayMs milliseconds to pause between each visible character
   */
  public static void type(String text, int delayMs) {
    int i = 0;
    while (i < text.length()) {
      char c = text.charAt(i);
      // Check for ANSI escape sequence: ESC followed by '['
      if (c == '' && i + 1 < text.length() && text.charAt(i + 1) == '[') {
        int end = text.indexOf('m', i);
        if (end != -1) {
          // Print the entire sequence at once with no per-char delay
          System.out.print(text.substring(i, end + 1));
          System.out.flush();
          i = end + 1;
        } else {
          System.out.print(c);
          System.out.flush();
          i++;
        }
      } else {
        System.out.print(c);
        System.out.flush();
        try {
          Thread.sleep(delayMs);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        i++;
      }
    }
    System.out.println();
  }

  /**
   * Standard typewriter speed — 10ms per character.
   * Suitable for most game messages and responses.
   *
   * @param text the text to display
   */
  public static void type(String text) {
    type(text, 10);
  }

  /**
   * Narrative typewriter speed — 15ms per character.
   * Suitable for story context, justification flavor text, and ending narratives.
   *
   * @param text the text to display
   */
  public static void typeSlow(String text) {
    type(text, 15);
  }

  /**
   * Header typewriter speed — 4ms per character.
   * Suitable for titles, stat lines, and short headers.
   *
   * @param text the text to display
   */
  public static void typeFast(String text) {
    type(text, 4);
  }

  /**
   * Prints text immediately with no typewriter effect.
   * Suitable for separator lines, borders, and input prompts.
   *
   * @param text the text to display
   */
  public static void typeInstant(String text) {
    System.out.println(text);
  }

  /**
   * Pauses output for the specified duration.
   * Used for dramatic timing around major game events.
   *
   * @param ms milliseconds to pause
   */
  public static void pause(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Wraps text in the given ANSI code followed by a reset.
   *
   * @param text the visible text to style
   * @param code an ANSI code constant from this class
   * @return the styled string ready for embedding in a larger string
   */
  public static String c(String text, String code) {
    return code + text + RESET;
  }

  /**
   * Formats a stat delta with color: bold green for positive, bold red for negative,
   * plain for zero.
   *
   * @param val the integer stat change
   * @return a color-formatted string such as "+15" in green or "-10" in red
   */
  public static String delta(int val) {
    if (val > 0) return BOLD_GREEN + "+" + val + RESET;
    if (val < 0) return BOLD_RED   + val + RESET;
    return String.valueOf(val);
  }

  /**
   * Colors a stat value based on its proximity to the danger threshold.
   * Red if within 9 points of the minimum, green if 65 or above, plain otherwise.
   *
   * @param val          the current stat value
   * @param minThreshold the minimum allowed value for this stat
   * @return a color-formatted string of the value
   */
  public static String statColor(int val, int minThreshold) {
    if (val <= minThreshold + 9) return RED   + val + RESET;
    if (val >= 65)               return GREEN + val + RESET;
    return String.valueOf(val);
  }
}
