package test;

public enum SelectionMode {
    CLICK {
        @Override public void onClick(MyContainer c, int idx) {
            if (!c.isEmptyCell(idx)) { c.setHighlight(idx); c.updateView(); }
        }
        @Override public void onEnter(MyContainer c, int idx) {}
    },
    HOVER {
        @Override public void onClick(MyContainer c, int idx) {
            if (!c.isEmptyCell(idx)) { c.setHighlight(idx); c.deleteAt(idx); c.updateView(); }
        }
        @Override public void onEnter(MyContainer c, int idx) {
            if (!c.isEmptyCell(idx)) { c.setHighlight(idx); c.updateView(); }
        }
    };

    public abstract void onClick(MyContainer c, int idx);
    public abstract void onEnter(MyContainer c, int idx);
}
