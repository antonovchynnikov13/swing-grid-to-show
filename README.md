# Swing Grid Task

A small **Java Swing** app built with the **MVC pattern**:
- **Model** — `GridModel` (data & logic: cells, modes, navigation).
- **View** — `GridView` (Swing UI, rendering, user input).
- **Controller** — `GridController` (glues model and view, handles updates).

The app displays a grid of cells with two selection modes, hover highlight, delete-on-click, click-to-select, and keyboard navigation.

---

## Features

- **Two modes** (toggle with `SPACE`):
  - **HOVER** — hovering highlights the cell (green). **Left-click deletes** the hovered non-empty cell; cells below in the **same column** shift up; the **bottom** becomes empty.
  - **CLICK** — **Left-click selects** a cell and updates the header/title.
- **Single highlight** at a time — hover overrides keyboard selection.
- **Empty cells are inert**: not highlighted, cannot be selected, clicks do nothing.
- **Keyboard navigation**: `←` / `→` move the selection, **skipping empty cells**.
- **Header/Title** shows the selected cell’s text (or `"-"` if none/empty).

---

## Controls

### Mouse
- **Hover (HOVER mode)**: highlight non-empty cell.
- **Left-click**:
  - **HOVER mode**: delete hovered cell (column shifts up; bottom becomes empty).
  - **CLICK mode**: select cell and update header/title.

### Keyboard
- `←` / `→`: move to previous/next **non-empty** cell.
- `SPACE`: toggle selection mode (**HOVER ↔ CLICK**).

---

## Configuration

Edit constants in `GridModel`:
- `PANELS_COUNT` — total number of cells.
- `COLS`, `ROWS` — grid dimensions.


---

## How to Run

### Requirements
- **Java 17+** (tested with OpenJDK 22)
- JUnit 5 (for tests)

### Run via IDE
1. Open the project in IntelliJ IDEA or Eclipse.
2. Run the class **`Launcher`** (contains the `main` method).

### Run via CLI
```bash
# Compile
javac -d out src/main/java/**/*.java

# Run
java -cp out main.java.Launcher

