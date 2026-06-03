# Trail of Tamil — Debug Log

This is our running log of every change we made to the spec and codebase, why we made it, and what broke along the way. Written as we go so we remember why things are the way they are.

---

## Entry 1 — May 26

### Bug: Iron Fist always kills you at Stage 2 no matter what you do

We ran through the Iron Fist path and noticed the game ended at Stage 2 every single time regardless of which option you picked. After checking the math we figured out why.

Starting stats: C50, W50, M50, D50, L50

After Stage 1 (Execute): C40, W45, M70, D40, L35

- Picked Option A (seize ships): Living went from 35 → 10. Minimum is 26. Dead.
- Picked Option B (pay volunteers): Wealth went from 45 → 5. Minimum is 6. Also dead.

Both options were instant game-overs. That's obviously not how it's supposed to work — Iron Fist should be a harder path, not an impossible one.

The same issue was in the Famine stage. Even if we fixed Stage 2, the Iron Fist penalties there were so big (Living -40, Military -30) that you'd almost certainly die at Stage 3 before ever reaching the interesting parts of the game.

---

### Fix: Rebalanced `NagapattinamRoom.java`

| Option | Before | After |
|--------|--------|-------|
| Seize (A) | C-15, W-30, M+40, D-10, L-25 | C-10, W-15, M+35, D-10, L-5 |
| Treasury (B) | C+0, W-40, M+20, D+0, L+10 | C+0, W-20, M+20, D+0, L+10 |

The idea is that the Iron Fist ruler's military grip suppresses the worst breakdown — trade is disrupted but not destroyed, the public is suffering but not rioting. You still feel the Iron Fist penalty, it's just not immediately fatal.

After the fix:
- Stage 1 + Seize: C30, W30, M100, D30, L30 — all above minimums ✓
- Stage 1 + Treasury: C40, W25, M90, D40, L45 — all above minimums ✓

---

### Fix: Rebalanced `FamineRoom.java` — Iron Fist variants only

| Option | Before | After |
|--------|--------|-------|
| Core A (Iron Fist) | C+0, W-20, M-30, D+0, L-40 | C+0, W-15, M-20, D+0, L-25 |
| Masses B (Iron Fist) | C+10, W-20, M-25, D+0, L+30 | C+10, W-15, M-20, D+0, L+30 |

The original -40 Living / -30 Military didn't account for the fact that Iron Fist players are already working with a weakened stat pool. The fixed values still make Prioritize Core brutal on the Iron Fist path (Living still drops to 5 if you go Seize + Core — that's intentional) but Feed the Masses becomes a real redemption arc.

Velvet Glove variants were fine and we left those alone.

---

### Fix: Removed explicit keywords from scene display — added `hint` system

The original scene display literally printed `"-> Type command containing: 'execute' to choose this path."` for every option. That's just a menu. It completely breaks the text adventure feel.

We removed all option text from `displayScene()`. Now the player sees only the narrative context and a closing question. If they want guidance, they type `hint` and the council reveals the options. The hint is in-world — it's like asking your advisors what they think. It doesn't count as a turn.

This is the Hitchhiker's Guide style we were going for.

**Changes:**
- `Room.java` — `displayScene()` now only shows narrative + closing question
- `Room.java` — added `displayHint()` which shows both option descriptions and keyword samples
- `GameEngine.java` — `processInput()` intercepts `"hint"` before keyword matching, calls `displayHint()`, returns without advancing stage

---

### Viable Iron Fist paths after the fix (quick trace):

| Stage 2 | Stage 3 | Notes |
|---------|---------|-------|
| Seize | Core | **GAME OVER** at Stage 3 (Living = 5) |
| Seize | Masses | Survives ✓ |
| Treasury | Core | **GAME OVER** at Stage 3 (Living = 20) |
| Treasury | Masses | Survives ✓ |

Iron Fist is intentionally harder — most paths eventually end in Bloodstained Throne or game over. Picking Core at Stage 3 is always fatal on this path, which makes narrative sense (the brutal ruler ignores the people and the people revolt).

---

## Entry 2 — May 28

### Feature: Added Stages 6 and 7

The original 5-stage game was almost entirely military stat changes. We wanted the last two stages to actually reward players who built up Diplomacy and Culture, not just Military. So we added:

**Stage 6 — Uraiyur** (ancient Chola cultural heartland, not used before)
- 2 situations depending on Stage 5 choice (Night Raid vs Scorched Rampart)
- Each situation has a different hidden explore item
- Stat changes focus on Culture, Wealth, and Diplomacy

**Stage 7 — Kaveripattinam** (legendary ancient seaport, not used before)
- 4 situations depending on combined Stage 5 + Stage 6 choices
- The situation is encoded as an int: `situation = (stage5WasNightRaid ? 0 : 2) + (stage6WasChoiceA ? 0 : 1)`
- No explore path in Stage 7 — by this point the city's fate is direct command only
- All stat changes focus on Commerce, Culture, and Diplomacy

**New files:** `UraiyurRoom.java`, `KaveripattinamRoom.java`

**GameEngine changes:**
- Added `stage5WasNightRaid` and `stage6WasChoiceA` booleans
- `advanceStage()` extended from 5 stages to 7
- Stage 5 now transitions to Stage 6 instead of calling `triggerEndingSequence()`
- Stage 7 choice triggers the ending

---

### Feature: Hidden Explore Path (Stages 4, 5, 6)

Before making a decision in Stages 4–6, the player can type `explore` to search the city and find a hidden item. The item gives a stat bonus (or penalty in some cases) when used. We deliberately kept this out of the hint — it's supposed to feel like a secret you discover on your own.

Rules:
- One explore per room
- Exploring doesn't consume a turn
- Items can be used at any point in the same city before advancing
- Unused items are discarded automatically when moving on
- Stage 7 has no explore (final stage, no time for wandering)

**Items:**

| Room | Item Name | Use Keyword | Effect |
|------|-----------|-------------|--------|
| KanchipuramRoom | Weaver's Guild Charter | `charter` | W+15, D+10 |
| SrirangamRoom | Temple Battle Hymns | `hymns` | C+10, M+15, L+5 |
| SiegeBreakerRoom | Chalukya Supply Map | `supply` | M+20, D-5 |
| UraiyurRoom (Night Raid) | Sangam Poetry Scroll | `scroll` | C+10, D+15, L+5 |
| UraiyurRoom (Scorched) | Merchant's Hidden Ledger | `ledger` | W+15, D-10, L+5 |

**New file:** `Item.java` — immutable class with all item data (name, find narrative, use keyword, use narrative, stat deltas)

**Room.java changes:**
- Added `protected Item exploreItem` field (null by default)
- Added `hasExploreItem()` and `getExploreItem()` methods

**GameEngine.java changes:**
- Added `Item heldItem` and `boolean hasExploredCurrentRoom` fields
- `processInput()` handles `"explore"` before main keywords
- Item use keyword is checked before A/B keywords so there's no shadowing
- Item reminder printed above the scene every turn when holding something
- `advanceStage()` discards unused items with a message and resets explore flag

---

### Feature: Third ending — The Kaveri Golden Age

Stages 6 and 7 introduced commerce and cultural paths that didn't fit either Pax Tamilakam (diplomatic utopia) or Bloodstained Throne (military ruin). We needed a third ending for the "rich merchant empire" trajectory.

New ending: **KAVERI_GOLDEN_AGE** — triggers when Wealth ≥ 70 AND Culture ≥ 55, checked only if Pax Tamilakam fails.

We also bumped the Pax Tamilakam threshold from 135 to 175 (Diplomacy + Culture + Living) because two extra stages meant stat totals got a lot higher. We adjusted the wealth requirement to 30 and raised the military cap from 75 to 90.

---

## Entry 3 — June 1

### Feature: Closing questions per stage

Players were getting confused about what they were supposed to decide without seeing the options. The narrative ended and they just had a blank prompt. We added a `closingQuestion` field to `Room.java` that's set in every room constructor and printed at the end of `displayScene()`. It's an open-ended question that frames the decision without revealing the options — like "The drought tightens and the granaries hold what little remains. Who receives the grain?"

This made the input experience way more intuitive without giving away the options or breaking the Hitchhiker's Guide feel.

---

### Feature: Scoreboard only reprints when stats change

The scoreboard was reprinting at the top of every loop iteration, including after typing `hint`, `explore`, or an invalid command. That flooded the terminal with repeated stat boards and made everything feel cluttered.

We added a `boolean dirty` flag to `PlayerStats`:
- Initialized to `true` so the board shows once at game start
- Set to `true` in `updateStats()` whenever stats change
- Set to `false` in `printStatusBoard()` after printing
- `GameEngine`'s main loop only calls `printStatusBoard()` when `isDirty()` is true

Result: The scoreboard shows exactly when something changes — after an action path or item use. Typing `hint`, `explore`, or invalid input doesn't trigger it.

---

### Fix: Removed `explore` from `displayHint()`

We realized our hint was revealing the existence of the explore path, which is supposed to be a secret. We removed it. Now `hint` only tells you about the two main action paths. You have to discover explore on your own.

---

### Feature: Expanded to 12 keywords per option

We ran some playtests and people kept getting the "council doesn't understand" message even when they were saying something totally reasonable. The original 5-ish keywords per option just weren't wide enough. We bumped every option to 12 keywords, covering synonyms, related concepts, and different phrasings of the same intent.

For example, the Night Raid option in Stage 5 now accepts: `raid`, `night`, `strike`, `decapitation`, `midnight`, `commando`, `sneak`, `tent`, `command`, `surgical`, `infiltrate`, `assassinate`. Someone saying "let's sneak into the command tent" gets matched now. Someone saying "assassinate the Chalukya leader" gets matched. Much better.

---

### Full Iron Fist path analysis (so we know the game actually has winnable paths)

**Starting stats:** C50, W50, M50, D50, L50

**After Stage 1 (Execute):** C40, W45, M70, D40, L35

| Stage 2 | Stage 3 | Stage 4 | Stage 5 | Stage 6 | Stage 7 | Final Stats | Ending |
|---------|---------|---------|---------|---------|---------|-------------|--------|
| Seize | Core | — | — | — | — | L5 | **GAME OVER** |
| Treasury | Core | — | — | — | — | L20 | **GAME OVER** |
| Seize | Masses | Scorched (Srirangam) | Scorched Rampart | — | — | W-10 | **GAME OVER** |
| Seize | Masses | Ambush (Srirangam) | Scorched Rampart | — | — | W-5 | **GAME OVER** |
| Treasury | Masses | Scorched (Srirangam) | Night Raid | Mercy | Open Harbor | C55,W40,M90,D80,L45 | Pax Tamilakam |
| Treasury | Masses | Ambush (Srirangam) | Night Raid | Mercy | Open Harbor | C95,W70,M35,D100,L95 | **Pax Tamilakam** |
| Seize | Masses | Ambush (Srirangam) | Night Raid | Mercy | Open Harbor | C65,W45,M55,D70,L70 | Pax Tamilakam (with Sangam Scroll) |
| Treasury | Masses | Ambush (Srirangam) | Night Raid | Tribute | Navy | C40,W75,M65,D30,L50 | Bloodstained Throne |

Key takeaways:
- Picking Core at Stage 3 is always fatal on Iron Fist
- Scorched Rampart at Stage 5 is always fatal on Iron Fist (Wealth is already too low)
- Items matter — the Sangam Scroll (+C10, +D15) can push a borderline Bloodstained Throne into Pax Tamilakam
- Iron Fist can reach all three good endings but requires good decisions throughout

---

## Entry 4 — June 1

### Feature: ANSI colors, bold/italic formatting, and typewriter cursor effect

The game was outputting everything as plain text walls. We wanted it to actually look good in a terminal — colored stats, italic flavor text, and a typewriter effect so it feels live instead of just dumping a text block.

---

### New file: `Display.java`

We made a static utility class that owns all ANSI formatting and output timing. Every other class calls `Display` methods instead of `System.out.println`. This keeps all the formatting logic in one place.

ANSI escape sequences look like `[<code>m` and tell the terminal to apply colors/styles. They work on macOS and Linux terminals. On platforms that don't support them (like code.org's browser), the text still shows up — just without the colors.

**Typewriter implementation:**
The `type(String, int)` method walks through the string one character at a time and sleeps `delayMs` milliseconds between each. The tricky part is ANSI escape sequences — if the terminal receives an incomplete sequence (like `[3` without the closing `m`), it can display garbage. We handle this by detecting the start of any escape sequence (`` followed by `[`), scanning forward to the next `m`, and printing the whole sequence at once with no delay before resuming character-by-character output.

**Speed tiers we decided on:**
- `typeFast` — 4ms, for stat boards and titles where you want it fast but still animated
- `type` — 10ms, for standard game messages
- `typeSlow` — 15ms, for long narrative paragraphs and story endings where the pacing matters
- `typeInstant` — 0ms, for separator lines and borders that don't need animation

We added `pause(ms)` for dramatic beats — there's a 400ms pause before consequence text and a 500ms pause before the final ending sequence.

---

### Color decisions and why

We spent some time deciding what gets what color. Here's the system we landed on:

- **Bold Blue** — everything that's "structure": titles, headers, stat board borders, the `[Vendar Command]` prompt. Blue felt official and commanding.
- **Italic Blue** — narrative text, the opening intro, ending flavor text, quit message. Same color family but softer.
- **Green** — instructions, hint guidance, "type hint if you need help." Green = helpful/safe.
- **Italic Green** — the explore find narrative. Italic to make it feel more like a discovery moment.
- **Bold Green** — positive stat deltas (+N), item carry reminder, item use confirmation. Bold because it's good news you should notice.
- **Red** — stat minimum labels, invalid input messages, item discard messages, errors. Red = warning/danger.
- **Bold Red** — game over, negative stat deltas (−N), threshold failure messages. The really bad stuff gets bold so it stands out.
- **Italic** — consequence/justification text in `updateStats()` and option descriptions in `displayHint()`. This is "story text" — softer.

---

### Files changed

- `Display.java` — Created from scratch
- `Room.java` — `displayScene()` and `displayHint()` rewritten to use Display calls
- `PlayerStats.java` — `printStatusBoard()`, `updateStats()`, and `isMinThresholdViolated()` use Display calls
- `GameEngine.java` — All output in `startGame()`, `processInput()`, `advanceStage()`, `checkGameOver()`, `triggerEndingSequence()` uses Display calls

Everything compiled clean after integration.

---

## Demo Paths (For Class Presentation)

Three paths we can run live in front of the class. Stat math verified.

---

### Demo 1 — Game Over (Brutal and Fast)

**Goal:** Show how quickly the empire collapses when you go full authoritarian and ignore your people.

**Inputs to type:**
1. Stage 1: `"Execute the traitor publicly as an example to everyone"`
2. Stage 2: `"Seize all merchant vessels and conscript the sailors by force"`
3. Stage 3: `"Lock down the granaries — the military gets fed first"`

**What happens:**
- After Stage 1 (Execute): C40, W45, M70, D40, L35
- After Stage 2 (Seize): C30, W30, M100, D30, L30
- After Stage 3 (Core, Iron Fist): C30, W15, M80, D30, **L5**
- L5 < 26 minimum → **GAME OVER: "The common people are unhappy..."**

**Why it's a good demo:** Three inputs, dies fast, shows that the game actually enforces consequences. Iron Fist + ignoring people = collapse.

---

### Demo 2 — The Bloodstained Throne

**Goal:** Show a full 7-stage run where you survive but end up as a brutal military ruler that the world fears and resents.

**Inputs to type:**
1. Stage 1: `"Execute the traitor publicly"` → Iron Fist
2. Stage 2: `"Pay volunteers with treasury gold — buy loyalty"` → Treasury
3. Stage 3: `"Distribute the food to everyone, the people need it"` → Feed Masses
4. Stage 4: `"Explore the city"` → Use battle hymns
5. Stage 5: `"Order a scorched retreat, burn the fields, pull everyone behind the walls"` → Scorched
6. Stage 6: `"Lead a night raid on the command tent at midnight"` → Night Raid
7. Stage 7: `"Demand reparations and tribute — they will pay for this war"` → Tribute
8. Stage 8: `"Build a naval fleet and sail to distant markets directly"` → Navy

**Stat trace:**
| After | C | W | M | D | L |
|-------|---|---|---|---|---|
| Start | 50 | 50 | 50 | 50 | 50 |
| Stage 1 (Execute) | 40 | 45 | 70 | 40 | 35 |
| Stage 2 (Treasury) | 40 | 25 | 90 | 40 | 45 |
| Stage 3 (Masses, IF) | 50 | 10 | 70 | 40 | 75 |
| Stage 4 (Scorched, IF) | 30 | 10 | 85 | 40 | 35 |
| Stage 5 (Night Raid) | 40 | 20 | 100 | 55 | 35 |
| Stage 6 (Tribute) | 30 | 55 | 100 | 35 | 40 |
| Stage 7 (Navy) | 40 | 75 | 100 | 30 | 50 |

**Final ending check:**
- Pax Tamilakam needs D+C+L ≥ 175: 30+40+50 = 120 → FAILS
- Kaveri Golden Age needs W ≥ 70 AND C ≥ 55: W=75 but C=40 → FAILS (Culture too low)
- → **BLOODSTAINED THRONE** ✓

**Why it's a good demo:** Shows the full 7-stage arc. The player survives through increasingly difficult situations but ends up militarily dominant and culturally hollow. The Wealth is actually decent (75) but the Culture is gutted from the Iron Fist choices early on. Classic "won the war, lost the empire" story.

---

### Demo 3 — The Kaveri Golden Age

**Goal:** Show the trade/culture ending where you build wealth through diplomacy and festivals rather than conquest.

**Inputs to type:**
1. Stage 1: `"Use our spy network to handle this quietly in the shadows"` → Velvet Glove
2. Stage 2: `"Fund a grand cultural festival and expand the Sangam academy"` → Festival
3. Stage 3: `"Protect the military core — lock down the grain reserves"` → Core
4. Stage 4 (Kanchipuram): `"Negotiate a loan with the guilds, offer them tax-exempt status"` → Loan
5. Stage 5: `"Explore the city"` -> read supply maps
6. Stage 6: `"Lead a night raid on the command tent at midnight"` → Night Raid
7. Stage 7: `"Demand tribute and reparations — they owe us for this war"` → Tribute
8. Stage 8: `"Build a state naval fleet and reach distant markets directly"` → Navy

**Stat trace:**
| After | C | W | M | D | L |
|-------|---|---|---|---|---|
| Start | 50 | 50 | 50 | 50 | 50 |
| Stage 1 (Spy) | 60 | 65 | 45 | 65 | 60 |
| Stage 2 (Festival) | 100 | 30 | 55 | 80 | 80 |
| Stage 3 (Core, VG) | 100 | 45 | 40 | 50 | 70 |
| Stage 4 (Loan, VG) | 100 | 85 | 70 | 60 | 80 |
| Stage 5 (Night Raid) | 100 | 95 | 90 | 75 | 80 |
| Stage 6 (Tribute) | 90 | 100 | 100 | 55 | 85 |
| Stage 7 (Navy) | 100 | 100 | 100 | 50 | 95 |

**Final ending check:**
- Pax Tamilakam needs M < 90: M=100 → FAILS (too militarized even on Velvet Glove)
- Kaveri Golden Age needs W ≥ 70 AND C ≥ 55: W=100, C=100 → **PASSES** ✓
- → **KAVERI GOLDEN AGE** ✓