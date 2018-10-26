package model;

// interface for screens that load items.
public interface Loadable {

    int loadItems();

    void loadSingleItem(String currentItem);
}
