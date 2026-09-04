# Walkthrough - Program Repository Implementation

I have implemented the Repository pattern in the `:data` module to orchestrate data between the network and the local database.

## Changes Made

### Data Mapping
- **[ProgramEntity.kt](file:///Users/kimj/proj/PlanetFitness/data/src/main/java/com/mentalmachines/planetfitness/data/database/ProgramEntity.kt)**: Added `toDomain()` extension function to convert database entities back to the domain `Program` model.

### Repository
- **[ProgramRepository.kt](file:///Users/kimj/proj/PlanetFitness/data/src/main/java/com/mentalmachines/planetfitness/data/repository/ProgramRepository.kt)**: Created the `ProgramRepository` class.
    - `programs`: A `Flow<List<Program>>` that provides a reactive stream of programs from the local database.
    - `refreshPrograms()`: A suspending function that fetches programs from the network via Retrofit and saves them to Room using `onConflict = REPLACE`.
    - `getProgram(id)`: Retrieves a specific program as a `Flow`.

## Verification Results

### Automated Tests
- Successfully ran `gradle :data:assembleDebug`. The repository and mapping logic compile correctly.

## Next Steps
- **Dependency Injection**: You can now provide `ProgramRepository` to your ViewModels. You'll need to pass the `PlanetFitnessApi` and `ProgramDao` instances to its constructor.
- **Error Handling**: The current `refreshPrograms` has basic error logging. You might want to implement a more robust `Result` wrapper to propagate network errors to the UI.
