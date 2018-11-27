package model.collections;

import java.io.IOException;
import java.util.ArrayList;

public interface CollectionOfItems {

    void loadSingleItem(String currentItem);

    void saveCollection();
}
