package model.collections;

import exceptions.BadTimeException;
import exceptions.EmptyCollectionException;
import exceptions.ItemNotFoundException;
import exceptions.NoTextbookException;
import inputs.Textbook;
import inputs.UniClass;
import model.observerPattern.Subject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CollectionOfUniClasses extends Subject implements CollectionOfItems {

    private HashMap<ArrayList<String>, UniClass> classMap;
    private CollectionOfTextbooks collectionOfTextbooks;

    // EFFECTS: instantiates the fields of the class.
    public CollectionOfUniClasses() {
        classMap = new HashMap<>();
        collectionOfTextbooks = new CollectionOfTextbooks();
    }

    // ADDING METHODS (START)

    // MODIFIES: this
    // EFFECTS: allows user to create a new UniClass and then saves the created UniClass.
    public void addItem(ArrayList<String> item) throws NumberFormatException, BadTimeException, IOException {
        String classType = item.get(0);
        String name = item.get(1);
        String prof = item.get(2);
        String location = item.get(3);
        String startTime = item.get(4);
        String endTime = item.get(5);
        String textbookName = item.get(6);
        String textbookAuthor = item.get(7);
        String textbookPages = item.get(8);

        try {
            Integer.parseInt(startTime);
            Integer.parseInt(endTime);
        } catch (NumberFormatException nfex) {
            throw new BadTimeException();
        }

        try {
            if (!textbookPages.equals("")) {
                Integer.parseInt(textbookPages);
            }
        } catch (NumberFormatException nfex) {
            throw new NumberFormatException();
        }

        HashMap<String, Integer> daysKey = new HashMap();
        daysKey.put("Monday", 1); daysKey.put("Tuesday", 2); daysKey.put("Wednesday", 3); daysKey.put("Thursday", 4);
        daysKey.put("Friday", 5); daysKey.put("Saturday", 6); daysKey.put("Sunday", 7);

        ArrayList days = new ArrayList();
        if (item.size() > 9) {
            for (int i = 9; i < item.size(); i++) {
                int day = daysKey.get(item.get(i));
                days.add(day);
            }
            makeClass(classType, name, prof, location, startTime, endTime, textbookName, textbookAuthor, textbookPages, days);
        }
    }

    // EFFECTS: makes a class with or without a textbook.
    public void makeClass(String classType, String name, String prof, String location, String startTime, String endTime,
                          String title, String author, String pages, ArrayList<Integer> days) throws IOException {
        if (title.equals("") | author.equals("") | pages.equals("")) {
            Textbook textbook = new Textbook();
            createUniClass(classType, name, prof, location, startTime, endTime, days, textbook);
        } else {
            Textbook textbook = new Textbook();
            textbook.setDetails(title, author, Integer.parseInt(pages));
            createUniClass(classType, name, prof, location, startTime, endTime, days, textbook);
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a new UniClass from the parameters, adds a textbook to collectionOfTextbooks, and adds the class
    //          to the classMap.
    public void createUniClass(String classType, String name, String prof, String location, String startTime, String endTime,
                               ArrayList<Integer> days, Textbook textbook) throws IOException {
        UniClass newClass = new UniClass(classType, name, prof, location, Integer.parseInt(startTime), Integer.parseInt(endTime), days,
                textbook);
        if (!textbook.equals(new Textbook())) {
            collectionOfTextbooks.addTextbook(newClass);
        }
        ArrayList<String> key = new ArrayList<>(Arrays.asList(classType, name));
        classMap.put(key, newClass);
        saveUniClass(newClass);
        notifyObservers();

    }

    // EFFECTS: saves uc to a file.
    public void saveUniClass(UniClass uc) throws IOException {
        String filename = "listofclasses.csv";
        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(uc.getClassType() + "," + uc.getName() + "," + uc.getProf() + "," + uc.getLocation() + ",");
            bufferedWriter.write(uc.getStartTime() + "," + uc.getEndTime() + ",");
            Textbook t = uc.getTextbook();
            bufferedWriter.write(t.getTitle() + "," + t.getAuthor() + "," + t.getPages());
            ArrayList<Integer> days = uc.getDays();
            for (int day : days) {
                bufferedWriter.write("," + day);
            }
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new IOException();
        }
    }

    // ADDING METHODS (END)

    // MODIFIES: this
    // EFFECTS: extracts details of uniClass from currentItem and adds the class to the classMap.
    public void loadSingleItem(String currentItem) {
        ArrayList<String> tempClassDetails;
        tempClassDetails = new ArrayList<>(Arrays.asList(currentItem.split(",")));
        String classType = tempClassDetails.get(0);
        String className = tempClassDetails.get(1);
        String prof = tempClassDetails.get(2);
        String room = tempClassDetails.get(3);
        String start = tempClassDetails.get(4);
        String end = tempClassDetails.get(5);
        String textbookTitle = tempClassDetails.get(6);
        String textbookAuthor = tempClassDetails.get(7);
        int textbookPages = Integer.parseInt(tempClassDetails.get(8));
        ArrayList<Integer> days = new ArrayList<>();
        for (int i = 9; i < tempClassDetails.size(); i++) {
            String day = tempClassDetails.get(i);
            days.add(Integer.parseInt(day));
        }
        addTextbookAndClassToCollection(classType, className, prof, room, start, end, textbookTitle, textbookAuthor, textbookPages, days);
    }

    // MODIFIES: this
    // EFFECTS: creates a Textbook and UniClass from the parameters, adds Textbook to collectionOfTextbooks and adds UniClass to classMap.
    private void addTextbookAndClassToCollection(String classType, String className, String prof, String room, String start, String end,
                                                 String textbookTitle, String textbookAuthor, int textbookPages, ArrayList<Integer> days) {
        Textbook tempTextbook;
        if (textbookTitle.equals("null") && textbookAuthor.equals("null")) {
            tempTextbook = new Textbook();
        } else {
            tempTextbook = new Textbook(textbookTitle, textbookAuthor, textbookPages);
        }
        UniClass tempUniClass = new UniClass(classType, className, prof, room, Integer.parseInt(start), Integer.parseInt(end), days, tempTextbook);
        if (!tempTextbook.equals(new Textbook())) {
            collectionOfTextbooks.addTextbook(tempUniClass);
        }
        ArrayList<String> key = new ArrayList<>(Arrays.asList(classType, className));
        classMap.put(key, tempUniClass);
    }

    // MODIFIES: this
    // EFFECTS: removes a class from the classMap, removes textbook from collectionOfTextbooks
    public void removeItem(String classType, String className) throws ItemNotFoundException, EmptyCollectionException {
        if (classMap.isEmpty()) {
            throw new EmptyCollectionException();
        }
        System.out.println("To remove a class, provide the name and class type.");
        ArrayList<String> key = new ArrayList<>(Arrays.asList(classType, className));
        checkAndRemoveClass(key);
    }

    // MODIFIES: this
    // EFFECTS: removes a class from the classMap, removes textbook from collectionOfTextbooks
    private void checkAndRemoveClass(ArrayList<String> key) throws ItemNotFoundException {
        if (classMap.containsKey(key)) {
            collectionOfTextbooks.removeTextbook(classMap.get(key));
            classMap.remove(key);
            notifyObservers();
            return;
        } else {
            throw new ItemNotFoundException();
        }
    }

    // MODIFIES: this.
    // EFFECTS: removes Textbook from collectionOfTextbooks.
    public void removeTextbook(String classType, String className) throws NoTextbookException, ItemNotFoundException, EmptyCollectionException {
        if (classMap.isEmpty()) {
            throw new EmptyCollectionException();
        }
        ArrayList<String> key = new ArrayList<>(Arrays.asList(classType, className));
        checkAndRemoveTextbook(key);
    }

    // MODIFIES: this.
    // EFFECTS: removes Textbook from collectionOfTextbooks.
    private void checkAndRemoveTextbook(ArrayList<String> key) throws ItemNotFoundException, NoTextbookException {
        if (classMap.containsKey(key)) {
            UniClass uc = classMap.get(key);
            if (uc.getTextbook().equals(new Textbook())) {
                throw new NoTextbookException();
            } else {
                collectionOfTextbooks.removeTextbook(uc);
                uc.removeTextbook(uc.getTextbook());
                notifyObservers();
                return;
            }
        } else {
            throw new ItemNotFoundException();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds textbook to UniClass in ClassMap and adds textbook to collectionOfTextbooks
    public void addTextbook(ArrayList<String> item) throws NumberFormatException, ItemNotFoundException {
        if (classMap.isEmpty()) {
            return;
        }
        String classType = item.get(0);
        String className = item.get(1);
        String title = item.get(2);
        String author = item.get(3);
        int pages;
        try {
            pages = Integer.parseInt(item.get(4));
        } catch (NumberFormatException nfex) {
            throw new NumberFormatException();
        }

        ArrayList<String> key = new ArrayList<>(Arrays.asList(classType, className));
        checkAndAddTextbook(title, author, pages, key);
    }

    // MODIFIES: this
    // EFFECTS: adds textbook to UniClass in ClassMap and adds textbook to collectionOfTextbooks
    private void checkAndAddTextbook(String title, String author, int pages, ArrayList<String> key) throws ItemNotFoundException {
        if (classMap.containsKey(key)) {
            UniClass uc = classMap.get(key);
            collectionOfTextbooks.removeTextbook(uc);
            Textbook tempTextbook = new Textbook();
            tempTextbook.setDetails(title, author, pages);
            uc.addTextbook(tempTextbook);
            collectionOfTextbooks.addTextbook(uc);
            notifyObservers();
        } else {
            throw new ItemNotFoundException();
        }
    }

    // EFFECTS: gets the classMap
    public HashMap<ArrayList<String>, UniClass> getClassMap() {
        return classMap;
    }

    // EFFECTS: saves all UniClasses to a file.
    public void saveCollection() {
        String filename = "listofclasses.csv";
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.close();
            for (UniClass uc : classMap.values()) {
                saveUniClass(uc);
            }
        } catch (IOException e) {
            System.out.println("Error while saving to file.");
        }
    }

    // EFFECTS: gets collectionOfTextbooks
    public CollectionOfTextbooks getCollectionOfTextbooks() {
        return collectionOfTextbooks;
    }
}

