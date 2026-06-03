# Trail of Tamil — Game Spec

By Djoni Muresan, Rig Saini, Rahul Pamulapati, Aashi Jain, Sadhana Arun

---

## I. Overview

Trail of Tamil is a text-based adventure game set in the 16th century Tamil Empire. You play as the Vendar (King) and make decisions that shape the fate of your empire across 7 stages in real historical Tamil cities. The game is written in Java (originally for code.org but tested locally).

Instead of picking from a numbered menu, you type naturally — like "I'll execute the traitor publicly" or "send a spy to handle it quietly." The game searches your input for keywords and figures out which path you're going down. If the game doesn't understand your input, it tells you so and you can type `hint` to see what kinds of responses would work.

Every decision affects 5 empire stats. If any stat drops below its minimum, the empire collapses and you lose. Survive all 7 stages and your final stats determine which of 3 endings you get.

Your council — the Aimperungkulu (Committee of 5: ministers, priests, army commanders, envoys, spies) and the Enperayam (Committee of 8: accountants, executive officials, treasury guards, palace guards, leading citizens) — advises you, but the final word is always yours.

---

## II. Empire Stats

All stats start at 50 and are clamped between 0 and 100. Every decision changes all five stats (some changes are 0 but they're all technically affected). A justification for the change is always printed after every decision.

| Stat | Starting Value | Minimum | What It Tracks |
|------|---------------|---------|---------------|
| Empire Culture | 50 | 11 | Arts, scholarship, Tamil identity |
| Empire Wealth | 50 | 6 | Treasury and trade economy |
| Military Strength | 50 | 15 | Army size and combat readiness |
| Diplomacy | 50 | 1 | Relations with neighboring states |
| Status of Living | 50 | 26 | Quality of civilian life |

---

## III. Game Over Conditions

After every player action, all 5 stats are checked. If anything falls below its minimum, the game immediately ends with the specific message for that stat:

- **Culture < 11** — "You no longer create new contributions in science, art, math, or language. You are a complacent empire that has no reason to exist in the world any longer."
- **Wealth < 6** — "The empire is unable to sustain itself and its economy crashes leading to a dark age of chaos and violence."
- **Military < 15** — "You are unable to fend off bandits and the Mahal (royal palace) is overrun."
- **Diplomacy ≤ 0** — "Your council overthrows you as you have destroyed previous relations with neighbors, something highly valued in Tamil culture."
- **Living < 26** — "The common people are unhappy with their lives and revolt against you. You are powerless against their numbers."

---

## IV. Input System

**Normal play:** You type a natural language response to the closing question at the end of each scene. The game runs your input through a keyword array for each path and picks the first match. If nothing matches, it tells you it didn't understand and you try again.

**Special commands:**
- `hint` — Shows both option descriptions and a few sample trigger keywords. Doesn't advance the stage or reprint the scoreboard. Doesn't reveal the explore path (that's a secret).
- `explore` — Only works in Stages 4, 5, and 6. Lets you search the current city and find a hidden item. One-time use per stage. Doesn't advance the stage.
- `quit` — Ends the game.

**Items:** If you found an item through exploring, a reminder shows above each turn with its use keyword. Speak that keyword in any response before moving to the next stage and the item applies its stat effects. Unused items are automatically discarded when you move on.

Each scene ends with a direct open-ended question that frames the decision. The two options are never shown outright — the player figures it out through natural input or uses `hint`.

The scoreboard only reprints when stats actually change (after an action or item use). Hint and explore never cause a reprint.

---

## V. Stages

### Stage 1 — The Assassination Plot (Thanjavur)

An assassination attempt reveals a traitor on your inner council, backed by a foreign rival power.

**Option A — Execute publicly** *(launches Iron Fist path)*
Public execution and brutal purge of the nobility to root out spies.
Stats: Culture -10, Wealth -5, Military +20, Diplomacy -10, Living -15
Keywords: `execute`, `public`, `punish`, `condemn`, `trial`, `judgment`, `hang`, `arrest`, `example`, `sentence`, `guilt`, `parade`

**Option B — Use the spy network** *(launches Velvet Glove path)*
Keep the betrayal secret and neutralize the threat covertly, working with local councils and guilds.
Stats: Culture +10, Wealth +15, Military -5, Diplomacy +15, Living +10
Keywords: `spy`, `shadow`, `secret`, `covert`, `network`, `silence`, `infiltrate`, `undercover`, `quiet`, `hidden`, `agent`, `dark`

**Closing question:** "A traitor sits within your inner circle and a foreign power stands behind the blade. What is your response?"

---

### Stage 2 (Iron Fist) — The Conscription Mandate (Nagapattinam)

You're at the main naval base. The shipyards need a massive surge in labor and crew immediately.

**Option A — Seize and conscript**
Commandeer all merchant vessels and force local sailors into military service.
Stats: Culture -10, Wealth -15, Military +35, Diplomacy -10, Living -5
Keywords: `seize`, `conscript`, `force`, `draft`, `commandeer`, `mandatory`, `compel`, `press`, `impress`, `mobilize`, `take`, `demand`

**Option B — Pay volunteers**
Drain the royal treasury to offer bounties for volunteers and hire foreign mercenaries; leave merchant ships alone.
Stats: Culture +0, Wealth -20, Military +20, Diplomacy +0, Living +10
Keywords: `treasury`, `bounty`, `volunteer`, `pay`, `hire`, `recruit`, `offer`, `wage`, `incentive`, `buy`, `fund`, `reward`

**Closing question:** "The shipyards stand idle and your campaign timeline is slipping. What are your orders?"

---

### Stage 2 (Velvet Glove) — The Sangam Concession (Madurai)

The southern chieftains are skeptical and demand something in return for their troops before northern rivals strike.

**Option A — Grant autonomy**
Give the southern prince co-ruler status, lower their taxes, and recognize their political independence.
Stats: Culture +25, Wealth -20, Military +30, Diplomacy +40, Living +15
Keywords: `autonomy`, `grant`, `self-rule`, `recognize`, `prince`, `rights`, `sovereignty`, `independent`, `co-ruler`, `govern`, `concede`, `freedom`

**Option B — Host a festival**
Fund a massive cultural festival and Sangam expansion instead of giving up political control.
Stats: Culture +50, Wealth -35, Military +10, Diplomacy +15, Living +20
Keywords: `festival`, `temple`, `cultural`, `celebrate`, `art`, `spectacle`, `ceremony`, `feast`, `expand`, `build`, `fund`, `grand`

**Closing question:** "The southern chieftains have traveled far to hear your terms. What do you offer them?"

---

### Stage 3 — Mass Famine (Universal)

A catastrophic drought hits the empire. The granaries are in secure inland cities. The player chooses who gets fed — the military core or everyone.

> **Note:** Same two options regardless of path (Iron Fist or Velvet Glove) but the stat results differ. The Stage 3 choice also determines Stage 4 routing: Option A (Prioritize Core) → Kanchipuram, Option B (Feed Masses) → Srirangam.

**Option A — Prioritize the military (Core)**
Lock emergency grain reserves in imperial capitals. The army stays fed.

- *Iron Fist result:* Port workers riot and sabotage the docks. Stats: Culture +0, Wealth -15, Military -20, Diplomacy +0, Living -25
- *Velvet Glove result:* High trust prevents open revolt but allies feel the chill. Stats: Culture +0, Wealth +15, Military -15, Diplomacy -30, Living -10

Keywords: `core`, `army`, `military`, `soldiers`, `reserve`, `guard`, `protect`, `strategic`, `troops`, `lock`, `secure`, `capitol`

**Option B — Feed the masses**
Empty the granaries entirely and distribute food to all civilians and peasants.

- *Iron Fist result:* Unexpected generosity shocks everyone. Fear softens. Stats: Culture +10, Wealth -15, Military -20, Diplomacy +0, Living +30
- *Velvet Glove result:* Cements your status as a legendary ruler, but finances crater. Stats: Culture +30, Wealth -40, Military -10, Diplomacy +20, Living +40

Keywords: `masses`, `people`, `distribute`, `share`, `civilians`, `citizens`, `feed`, `everyone`, `equal`, `hungry`, `open`, `all`

**Closing question:** "The drought tightens and the granaries hold what little remains. Who receives the grain?"

---

### Stage 4 (Prioritize Core) — Silk Guild War Bonds (Kanchipuram)

You march north to intercept the Chalukya. You need emergency funding to fortify the walls.

**Hidden Explore Item:** *Weaver's Guild Charter* — An ancient charter grants special legal protection to weavers. Use keyword: `charter`. Stats when used: Wealth +15, Diplomacy +10.

**Option A — Forcibly requisition guild wealth**
Seize the silk guild and monastery treasuries under military emergency.

- *Iron Fist result:* The guilds sabotage your supply lines. Stats: Culture +0, Wealth -30, Military +25, Diplomacy +0, Living -15
- *Velvet Glove result:* The guilds feel betrayed and riots break out at the front. Stats: Culture -10, Wealth +0, Military -20, Diplomacy +0, Living -30

Keywords: `requisition`, `seize`, `take`, `force`, `confiscate`, `commandeer`, `emergency`, `compel`, `strip`, `demand`, `conscript`, `levy`

**Option B — Negotiate a loan**
Offer the guilds permanent tax-exempt status and council seats in exchange for financing.

- *Iron Fist result:* Low trust — guilds demand a royal heir as collateral. Stats: Culture +0, Wealth +20, Military +0, Diplomacy -20, Living +0
- *Velvet Glove result:* Guilds fund everything out of loyalty. Stats: Culture +0, Wealth +40, Military +30, Diplomacy +10, Living +10

Keywords: `loan`, `borrow`, `negotiate`, `deal`, `offer`, `request`, `ask`, `purchase`, `partnership`, `council`, `tax-exempt`, `contract`

**Closing question:** "The walls will not hold without resources and time is running out. How do you secure funding?"

---

### Stage 4 (Feed Masses) — Sacred Labyrinth Defense (Srirangam)

Your treasury is low and troops are weak, but the population is loyal. You fall back to Srirangam — a river-island temple-city with seven concentric walls.

**Hidden Explore Item:** *Temple Battle Hymns* — An elderly priest teaches you ancient battle hymns sung by defenders who once held these walls. Use keyword: `hymns`. Stats when used: Culture +10, Military +15, Living +5.

**Option A — Scorched-earth retreat**
Burn the surrounding fields and pull all local peasants inside the temple walls as refugees.

- *Iron Fist result:* Forced refugees harbor resentment. Disease breaks out. Stats: Culture -20, Wealth +0, Military +15, Diplomacy +0, Living -40
- *Velvet Glove result:* Loyal peasants willingly sacrifice their property. Stats: Culture +25, Wealth +0, Military +30, Diplomacy +0, Living -10

Keywords: `scorched`, `burn`, `retreat`, `pull`, `withdraw`, `refuge`, `walls`, `fortify`, `shelter`, `inside`, `defensive`, `peasants`

**Option B — Guerrilla ambush at the river crossing**
Keep the temple clear and use your forces to hit the enemy at the crossing point.

- *Iron Fist result:* Demoralized troops fail the high-risk ambush. Heavy casualties. Stats: Culture +0, Wealth +0, Military -35, Diplomacy +0, Living +0
- *Velvet Glove result:* Soldiers fight fanatically and crush the enemy vanguard. Stats: Culture +20, Wealth +0, Military +40, Diplomacy +10, Living +10

Keywords: `ambush`, `guerrilla`, `river`, `strike`, `attack`, `flank`, `surprise`, `intercept`, `cross`, `offensive`, `assault`, `trap`

**Closing question:** "The enemy approaches the river crossing and your forces are stretched thin. What is your command?"

---

### Stage 5 — The Siege Breaker

The main Chalukya force surrounds your stronghold. Siege engines are pounding the walls and resources are critically low. You must break the siege tonight.

> **Note:** The Stage 5 choice determines which Stage 6 situation you get. Night Raid → diplomatic summit in Uraiyur. Scorched Rampart → cultural/economic reckoning in Uraiyur.

**Hidden Explore Item:** *Chalukya Supply Map* — An abandoned satchel contains detailed maps of enemy supply lines and reserve positions. Use keyword: `supply`. Stats when used: Military +20, Diplomacy -5.

**Option A — Night Raid**
Lead an elite strike force through secret routes at midnight. Direct decapitation strike on the Chalukya command tent.
Stats: Culture +10, Wealth +10, Military +20, Diplomacy +15, Living +0
Keywords: `raid`, `night`, `strike`, `decapitation`, `midnight`, `commando`, `sneak`, `tent`, `command`, `surgical`, `infiltrate`, `assassinate`

**Option B — Scorched Rampart**
Rig the outer city walls with oil and pitch, feign a retreat to lure the enemy vanguard in, then ignite the traps. You sacrifice a section of the city.
Stats: Culture -20, Wealth -20, Military +35, Diplomacy -15, Living -25
Keywords: `rampart`, `oil`, `pitch`, `trap`, `lure`, `ignite`, `sacrifice`, `rig`, `torch`, `scorch`, `bait`, `fire`

**Closing question:** "Tonight may be your only chance to break this siege. What is your plan?"

---

### Stage 6 — The Reckoning (Uraiyur)

The ancient Chola heartland and seat of Tamil scholarship. Two situations exist based on Stage 5.

---

#### Situation A — Diplomatic Reckoning (Stage 5 = Night Raid)

Your midnight strike worked. Chalukya envoys have arrived seeking peace. The Sangam scholars are watching every word.

**Hidden Explore Item:** *Sangam Poetry Scroll* — A street poet presses a hand-copied scroll of ancient Sangam verses into your hands. Use keyword: `scroll`. Stats when used: Culture +10, Diplomacy +15, Living +5.

**Option A — Offer generous terms**
Release prisoners, restore seized goods, sign a trade alliance. Show what Tamil victory looks like in peace.
Stats: Culture +15, Wealth +20, Military -10, Diplomacy +25, Living +10
Keywords: `mercy`, `generous`, `peace`, `release`, `treaty`, `alliance`, `forgive`, `magnanimous`, `pardon`, `kind`, `lenient`, `clemency`

**Option B — Demand tribute**
Demand steep war reparations and ongoing tribute payments. Humiliate the Chalukya for a generation.
Stats: Culture -10, Wealth +35, Military +10, Diplomacy -20, Living +5
Keywords: `tribute`, `reparations`, `demand`, `payment`, `humiliate`, `punish`, `exact`, `squeeze`, `harsh`, `compensation`, `terms`, `fine`

**Closing question:** "The Chalukya envoys have traveled far to hear your terms. What do you say to them?"

---

#### Situation B — Scorched Reckoning (Stage 5 = Scorched Rampart)

A district of your city lies in ash. You travel to Uraiyur, but rumors of your tactic have spread. Cultural leaders and foreign merchants are watching with unease.

**Hidden Explore Item:** *Merchant's Hidden Ledger* — In the ruins of the merchant quarter, you find a waterproof chest with a Chalukya trader's ledger mapping secret foreign trade routes. Use keyword: `ledger`. Stats when used: Wealth +15, Diplomacy -10, Living +5.

**Option A — Commission a massive rebuild**
Hire Uraiyur's finest craftsmen to restore the burned district — temples, merchant quarters, homes.
Stats: Culture +20, Wealth -25, Military +0, Diplomacy +15, Living +20
Keywords: `rebuild`, `restore`, `reconstruct`, `commission`, `artisan`, `renew`, `repair`, `construct`, `revive`, `fix`, `build`, `invest`

**Option B — Declare a nationalist memorial**
Turn the ruins into a symbol of sacrifice and pride. Redirect resources toward military rebuilding.
Stats: Culture +10, Wealth -5, Military +20, Diplomacy -15, Living -10
Keywords: `memorial`, `symbol`, `pride`, `nationalism`, `mourn`, `honor`, `remember`, `sacrifice`, `glorify`, `monument`, `martyr`, `legacy`

**Closing question:** "The empire watches for a sign of what comes next. How do you lead them forward from the ashes?"

---

### Stage 7 — Final Shaping (Kaveripattinam)

The legendary ancient seaport of the Chola dynasty. Four situations based on the combined Stage 5 + Stage 6 choices. No hidden explore path — the city's fate is decided by direct command.

| Situation | Stage 5 | Stage 6 | Title |
|-----------|---------|---------|-------|
| 0 | Night Raid | Mercy | Open Harbor |
| 1 | Night Raid | Tribute | Contested Docks |
| 2 | Scorched Rampart | Rebuild | Artisan Tide |
| 3 | Scorched Rampart | Memorial | Nationalist Crossroads |

---

#### Situation 0 — Open Harbor

Peace treaties have made Kaveripattinam the most desired trading port in the world. Merchant guilds demand you declare a law of the sea.

**Option A — Declare open ports**
No tariffs, no restrictions. Make it the neutral cosmopolitan hub of the ancient world.
Stats: Culture +20, Wealth +30, Military -10, Diplomacy +25, Living +15
Keywords: `open`, `free`, `tariff`, `neutral`, `cosmopolitan`, `declare`, `liberal`, `unrestricted`, `access`, `welcome`, `global`, `port`

**Option B — Royal trading company**
Tamil merchants get preferential rates and exclusive contracts; controlled tariffs regulate foreign goods.
Stats: Culture +5, Wealth +40, Military +5, Diplomacy -10, Living +10
Keywords: `company`, `state`, `preferential`, `royal`, `control`, `restrict`, `regulate`, `charter`, `exclusive`, `managed`, `tax`, `protected`

**Closing question:** "The harbor master awaits the proclamation that will set the law of the sea. What is your ruling?"

---

#### Situation 1 — Contested Docks

Tribute gold has flooded Kaveripattinam but triggered a partial trade embargo by Chalukya allies. Ships sit idle and guilds grow restless.

**Option A — Build a trade coalition**
Use the gold to bribe neighboring chieftains into a Tamil-led economic bloc that bypasses the embargo.
Stats: Culture -5, Wealth +25, Military +15, Diplomacy +10, Living +5
Keywords: `coalition`, `bloc`, `bribe`, `chieftains`, `bypass`, `alliance`, `partner`, `regional`, `league`, `gold`, `buy`, `diplomatic`

**Option B — Build a naval fleet**
Invest the gold in a state-owned naval fleet to reach distant markets directly — no middlemen.
Stats: Culture +10, Wealth +20, Military +25, Diplomacy -5, Living +10
Keywords: `navy`, `fleet`, `ships`, `naval`, `maritime`, `build`, `invest`, `sail`, `direct`, `sea`, `route`, `vessel`

**Closing question:** "The tribute gold sits in your vaults while foreign embargoes choke the docks. What do you do with it?"

---

#### Situation 2 — Artisan Tide

Uraiyur's craftsmen flooded Kaveripattinam during the rebuilding. The city is an unexpected center of cultural and commercial energy. Foreign buyers pay extraordinary prices for Tamil silks and bronzes.

**Option A — Let the guilds self-organize**
Encourage free artisan markets with minimal royal interference.
Stats: Culture +30, Wealth +20, Military -10, Diplomacy +15, Living +25
Keywords: `guilds`, `free`, `artisan`, `craftsmen`, `self`, `independent`, `organic`, `market`, `unregulated`, `trust`, `allow`, `flourish`

**Option B — Royal monopoly**
Nationalize the most valuable workshops. Declare a royal monopoly on silk and bronze exports.
Stats: Culture -15, Wealth +40, Military +10, Diplomacy -5, Living -10
Keywords: `monopoly`, `nationalize`, `royal`, `control`, `seize`, `own`, `centralize`, `crown`, `state`, `oversee`, `regulate`, `claim`

**Closing question:** "The artisan boom grows beyond any one hand to hold. How do you govern this prosperity?"

---

#### Situation 3 — Nationalist Crossroads

Nationalist fervor brought soldiers but scared away scholars and merchants. Kaveripattinam's trade has stagnated. The city sits half-empty.

**Option A — Cultural renaissance**
Host a grand pan-Tamil cultural festival — invite diaspora communities, foreign scholars, and merchants back.
Stats: Culture +35, Wealth +15, Military -15, Diplomacy +30, Living +20
Keywords: `festival`, `cultural`, `renaissance`, `celebrate`, `invite`, `soft`, `open`, `diaspora`, `scholar`, `arts`, `welcome`, `gather`

**Option B — Naval expansion**
Use nationalist energy for aggressive mercantile expansion. Tamil traders get naval escorts; key sea routes monopolized by force.
Stats: Culture -10, Wealth +30, Military +15, Diplomacy -20, Living +5
Keywords: `escort`, `force`, `naval`, `expand`, `aggressive`, `routes`, `monopolize`, `power`, `dominance`, `push`, `seize`, `pressure`

**Closing question:** "The empire stands at a crossroads. Which direction do you lead it?"

---

## VI. Endings

After Stage 7 completes, final stats are checked in this priority order:

**Ending 1 — Pax Tamilakam**
Condition: (Diplomacy + Culture + Living ≥ 175) AND (Wealth ≥ 30) AND (Military < 90)
The Tamil kingdoms fully unify. A golden age of diplomacy, cultural renown, and enduring peace follows.

**Ending 2 — The Kaveri Golden Age**
Condition: (Wealth ≥ 70) AND (Culture ≥ 55) — only checked if Pax Tamilakam fails
Kaveripattinam becomes the greatest trading port in the ancient world. Your legacy is commerce and culture, not conquest.

**Ending 3 — The Bloodstained Throne**
Condition: Everything else
The siege is broken but the land is devastated. Alienated guilds and factions splinter into rebellion. You sit alone on a fortified but broken throne.

---

## VII. Class Design

```
[Display]       (Static utility — ANSI colors + typewriter output. Used by everything.)

[GameEngine]
        |
        +---> [PlayerStats]  (Holds and updates the 5 empire stats)
        +---> [Item]         (Collectible found by exploring; discarded on stage advance)
        |
        +---> [Room]  (Abstract base class)
                |
                +---> ThanjavurPlotRoom   (Stage 1, determines Iron Fist vs Velvet Glove)
                +---> NagapattinamRoom    (Stage 2, Iron Fist branch)
                +---> MaduraiRoom         (Stage 2, Velvet Glove branch)
                +---> FamineRoom          (Stage 3, universal — results differ by path + choice)
                +---> KanchipuramRoom     (Stage 4, Prioritize Core — has explore item)
                +---> SrirangamRoom       (Stage 4, Feed Masses — has explore item)
                +---> SiegeBreakerRoom    (Stage 5 — has explore item)
                +---> UraiyurRoom         (Stage 6, 2 situations — has explore item)
                +---> KaveripattinamRoom  (Stage 7, 4 situations — no explore)
```

### `Display.java`
Static utility class — all ANSI color constants and typewriter output methods. Every class calls Display methods instead of `System.out.println` directly.

> **AP CSA Note:** `Thread.sleep` is used here for the typewriter timing effect. It's the only non-AP method in the project and it's isolated to this class on purpose.

**Output methods:**
- `typeSlow(text)` — 15ms/char, for narrative and story endings
- `type(text)` — 10ms/char, for standard game messages
- `typeFast(text)` — 4ms/char, for titles and stat boards
- `typeInstant(text)` — no delay, for borders and separators
- `pause(ms)` — dramatic pause before major moments
- `c(text, code)` — wraps text in ANSI code + RESET for inline styling
- `delta(val)` — bold green +N or bold red −N for stat changes
- `statColor(val, min)` — red if near minimum (≤ min+9), green if healthy (≥ 65), plain otherwise

**Color scheme:**
| What | Style |
|------|-------|
| Titles, headers, borders, prompt label | Bold Blue |
| Narrative context, story text | Italic Blue |
| Instructions, hint guidance | Green |
| Explore find narrative | Italic Green |
| Item carry reminder, item used, closing questions | Bold Green |
| Invalid input, errors, item discard, minimum labels | Red |
| Game over, negative deltas, threshold failures | Bold Red |
| Consequence/justification text, hint option descriptions | Italic |

### `GameEngine.java`
The main controller. Owns the game loop, routing logic, item tracking, and all stage transitions.

**Key fields:** `PlayerStats stats`, `ArrayList<Room> gameRoute`, `int currentStage`, `boolean isIronFistPath`, `boolean stage5WasNightRaid`, `boolean stage6WasChoiceA`, `Item heldItem`, `boolean hasExploredCurrentRoom`, `boolean sceneNeedsDisplay`

**Key methods:**
- `startGame()` — sets up the first room and runs the main loop
- `processInput(String, Room)` — handles commands in priority order: quit → hint → explore → item use → keyword A → keyword B → invalid
- `checkGameOver()` — checks all stat thresholds after every action
- `advanceStage(boolean)` — discards unused items, resets explore flag, adds next room based on path flags
- `triggerEndingSequence()` — prints final stats and the correct ending text

### `PlayerStats.java`
Holds the 5 stats and handles all updates.

**Key methods:**
- `updateStats(c, w, m, d, l, justification)` — applies deltas, clamps to 0–100, prints consequence and colored deltas, sets dirty = true
- `printStatusBoard()` — prints the dashboard with color-coded values, sets dirty = false
- `isDirty()` — true if stats changed since last board print; used by GameEngine to decide when to reprint
- `isMinThresholdViolated()` — checks all 5 minimums and returns true/false
- `getDominantEnding()` — returns `"PAX_TAMILAKAM"`, `"KAVERI_GOLDEN_AGE"`, or `"BLOODSTAINED_THRONE"`

### `Item.java`
Immutable object for a discoverable city item. All fields set in constructor, getters only.

**Fields:** `name`, `findNarrative`, `useKeyword`, `useNarrative`, int deltas for all 5 stats.

### `Room.java`
Abstract base class for all stages.

**Protected fields:** `title`, `contextText`, `closingQuestion`, `optionA_Text`, `optionB_Text`, `String[] keywordsA`, `String[] keywordsB`, `Item exploreItem`

**Methods:**
- `displayScene()` — prints title, narrative, closing question, hint nudge. Never shows the options.
- `displayHint()` — shows both option descriptions and sample keywords. Does NOT mention explore.
- `hasExploreItem()`, `getExploreItem()` — used by GameEngine
- `getKeywordsA()`, `getKeywordsB()` — used by GameEngine for matching
- `abstract executeChoiceA(PlayerStats)`, `abstract executeChoiceB(PlayerStats)`

### Room Subclasses

| Class | Stage | Constructor Args | Notes |
|-------|-------|-----------------|-------|
| `ThanjavurPlotRoom` | 1 | none | Sets Iron Fist vs Velvet Glove |
| `NagapattinamRoom` | 2 | none | Iron Fist branch |
| `MaduraiRoom` | 2 | none | Velvet Glove branch |
| `FamineRoom` | 3 | `boolean isIronFistPath` | Results differ based on path |
| `KanchipuramRoom` | 4 | `boolean isIronFistPath` | Prioritize Core branch; has explore item |
| `SrirangamRoom` | 4 | `boolean isIronFistPath` | Feed Masses branch; has explore item |
| `SiegeBreakerRoom` | 5 | none | Has explore item; choice sets stage5WasNightRaid |
| `UraiyurRoom` | 6 | `boolean stage5WasNightRaid` | 2 situations; has explore item |
| `KaveripattinamRoom` | 7 | `int situation` (0–3) | 4 situations; no explore |
