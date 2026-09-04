# Implementation Plan - HomeViewModel and Repository Integration

Create a `HomeViewModel` to manage the state for the `HomePage`, integrating it with the `ProgramRepository` from the `:data` module.

## User Review Required

> [!IMPORTANT]
> I will be adding a dependency from the `:app` module to the `:data` module so the ViewModel can access the repository.

## Proposed Changes

### [Component: App Dependencies]

#### [MODIFY] [app/build.gradle.kts](file:///Users/kimj/proj/PlanetFitness/app/build.gradle.kts)
- Add `implementation(project(":data"))`.
- Add `androidx-lifecycle-viewmodel-compose` dependency.

#### [MODIFY] [libs.versions.toml](file:///Users/kimj/proj/PlanetFitness/gradle/libs.versions.toml)
- Add `androidx-lifecycle-viewmodel-compose` to the `libraries` section.

---

### [Component: ViewModel Implementation]

#### [NEW] [HomeViewModel.kt](file:///Users/kimj/proj/PlanetFitness/app/src/main/java/com/mentalmachines/planetfitness/features/homepage/HomeViewModel.kt)
- Create `HomeViewModel` extending `ViewModel`.
- Inject `ProgramRepository` (via constructor).
- Expose a `HomeUiState` using `StateFlow`.
- Implement `refreshPrograms()` to trigger data loading.

#### [NEW] [HomeUiState.kt](file:///Users/kimj/proj/PlanetFitness/app/src/main/java/com/mentalmachines/planetfitness/features/homepage/HomeUiState.kt)
- Define a data class to represent the UI state (Loading, Success, Error).

---

### [Component: UI Integration]

#### [MODIFY] [HomePage.kt](file:///Users/kimj/proj/PlanetFitness/app/src/main/java/com/mentalmachines/planetfitness/features/homepage/HomePage.kt)
- Update `HomePage` to accept a `HomeViewModel`.
- Observe the `uiState` from the ViewModel.
- Pass data to `HomeContent()`.

## Verification Plan

### Automated Tests
- Run `gradle :app:assembleDebug` to ensure compilation and dependency resolution.

### Manual Verification
- Verify that the Home tab displays the loading state and then populates with data (once the network/db is wired up).
