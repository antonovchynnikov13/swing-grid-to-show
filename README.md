# Swing Grid Task

A small Java Swing app that displays a grid of cells.  
It supports hover highlight, delete-on-click (in one mode), click-to-select (in another), and simple keyboard navigation.

## Features

- **Two modes** (toggle with `SPACE`):
    - **HOVER** — hover highlights the cell (green). **Left-click deletes** the hovered non-empty cell; cells below in the **same column** shift up; the **bottom** becomes empty.
    - **CLICK** — **Left-click selects** a cell and updates the header/title.
- **Single highlight** at a time — hover overrides keyboard selection.
- **Empty cells are inert**: not highlighted, cannot be selected, clicks do nothing.
- **Keyboard navigation**: `←` / `→` move the selection, **skipping empty cells**.
- **Header/Title** shows the selected cell’s text (or `"-"` if none/empty).
- **Config validation**: throws `IllegalArgumentException` if `ROWS*COLS < PANELS_COUNT`, grid sizes are non-positive, or `highlightIndex` is out of bounds.

> If arrow keys don’t respond, click the grid once to ensure it has focus.

## Controls

**Mouse**
- **Hover (HOVER mode)**: highlight non-empty cell.
- **Left-click**:
    - **HOVER mode**: delete hovered cell (column shifts up; bottom becomes empty).
    - **CLICK mode**: select cell and update header/title.

**Keyboard**
- `←` / `→`: move to previous/next **non-empty** cell.
- `SPACE`: toggle mode (**HOVER ↔ CLICK**).

## Configuration

Edit constants in `MyContainer`:
- `PANELS_COUNT` — total number of cells.
- `COLS`, `ROWS` — grid dimensions.

The app validates that `ROWS * COLS >= PANELS_COUNT`. Otherwise you’ll see:
IllegalArgumentException: Invalid configuration: ROWS*COLS ... < PANELS_COUNT ...

## How to Run

### Using an IDE (IntelliJ IDEA / Eclipse)
1. Open the project as a Java project.
2. Run the class **`Launcher`** (it contains the `main` method).

Next Steps:
1. Unit tests for delete/shift behavior and navigation.
2. Extract a simple GridModel (data + logic) separate from Swing UI.
3. Apply Strategy pattern for selection behaviors (HOVER vs CLICK).