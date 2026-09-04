# Implementation Plan - Repository Pattern for Offline-First Data

Implement a Repository in the `:data` module to orchestrate data flow between Retrofit (Network) and Room (Local Database), providing an "Offline-First" experience.

## Proposed Changes

### [Component: Data Repository]
Create the repository and necessary mapping logic.

#### [MODIFY] [ProgramEntity.kt](file:///Users/kimj/proj/PlanetFitness/data/src/main/java/com/mentalmachines/planetfitness/data/database/ProgramEntity.kt)
- Add a mapper function `toDomain()` to convert `ProgramEntity` back to the domain `Program` model.

#### [NEW] [ProgramRepository.kt](file:///Users/kimj/proj/PlanetFitness/data/src/main/java/com/mentalmachines/planetfitness/data/repository/ProgramRepository.kt)
- Create a `ProgramRepository` class.
- Methods:
    - `programs: Flow<List<Program>>`: Exposes a stream of programs from the local database.
    - `refreshPrograms()`: Fetches programs from the network and updates the local database.
    - `getProgram(id: String): Flow<Program?>`: Retrieves a specific program.

## Design Decisions

- **Offline-First Strategy**: The UI will observe the local database. The repository is responsible for fetching fresh data from the network and saving it to Room, which automatically triggers UI updates via `Flow`.
- **Data Mapping**:
    - Network (`Program`) -> Database (`ProgramEntity`) via `toEntity()`.
    - Database (`ProgramEntity`) -> UI/Domain (`Program`) via `toDomain()`.
- **Simplification**: Note that the current `ProgramEntity` stores a subset of the `Program` data (e.g., it currently excludes nested `Workouts` and `Trainer` details). I will use the current entity structure for the boilerplate implementation.

## Verification Plan

### Automated Tests
- Run `gradle :data:assembleDebug` to ensure compilation.

### Manual Verification
- Verify the repository correctly interacts with both `ProgramDao` and `PlanetFitnessApi`.
