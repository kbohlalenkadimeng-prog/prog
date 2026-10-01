# Project Notes for the Report and Video

## Screen flow
Main/Pantry -> Add/Edit Ingredient -> Main/Pantry
Main/Pantry -> Suggested Recipes -> Recipe Detail
Main/Pantry -> Settings

## Database model
pantry_items: id (PK), name, quantity, unit, expiry_date
recipes: id (PK), name, ingredients, method

## Core algorithm
DatabaseHelper.getStrictSuggestions() obtains all pantry records and all recipes. Each recipe is passed to canMake(). Every required ingredient is compared with the pantry using IngredientMatcher. If any ingredient is absent or its quantity is insufficient, the complete recipe is excluded. Only recipes for which every requirement passes are returned.

## Demonstration test
1. Start with an empty pantry. Suggested Recipes should show the empty-state message.
2. Add eggs (2 items), tomatoes (2 items), onion (0.5 items). Tomato Egg Scramble should appear.
3. Delete onion. Tomato Egg Scramble must disappear.
4. Add onion again. Recipe should return.
5. Edit the egg quantity below 2. The recipe must disappear.
6. Restore eggs to 2. The recipe should return.
7. Close and reopen the app. Pantry data should still exist.

## Report screenshots to capture
- Pantry screen with ingredients
- Add Ingredient form
- Validation error
- Updated pantry item
- Delete confirmation/result
- Suggested Recipes with matching recipe
- Suggested Recipes with no matches
- Recipe Detail
- Settings
- App after reopening to demonstrate persistence
- GitHub repository and commit history

## Video structure
0:00-1:00 GitHub repository and genuine commit history
1:00-3:30 live app: CRUD, strict matching, recipe detail, settings, persistence
3:30-5:30 explain database, RecyclerView/Adapter, Intents and strict matching
5:30-6:15 database justification

## Academic integrity
The student should be able to explain the submitted code and should use their own GitHub history, screenshots and voice-over. Replace report placeholders with the student's own details and evidence.
