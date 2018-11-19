package model.collections;

import exceptions.input.BadClassTypeException;
import exceptions.input.BadTimeException;
import inputs.Textbook;
import inputs.UniClass;
import model.inputHandling.UniClassInputHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CollectionOfUniClasses implements CollectionOfItems {

    private HashMap<ArrayList<String>, UniClass> classMap;
    private CollectionOfTextbooks collectionOfTextbooks;
    private UniClassInputHandler inputHandler;

    // EFFECTS: instantiates the fields of the class.
    public CollectionOfUniClasses() {
        classMap = new HashMap<>();
        collectionOfTextbooks = new CollectionOfTextbooks();
        inputHandler = new UniClassInputHandler();
    }

    // ADDING METHODS (START)

    // MODIFIES: this
    // EFFECTS: allows user to create a new UniClass and then saves the created UniClass.
    public void addItem(Scanner user_input) {
        try {
            String classType = inputHandler.userClassType(user_input);
            String name = inputHandler.userClassName(user_input);
            String prof = inputHandler.userProf(user_input);
            String location = inputHandler.userLocation(user_input);
            String startTime = inputHandler.userStartTime(user_input);
            String endTime = inputHandler.userEndTime(user_input);
            ArrayList<Integer> days = inputHandler.userDays(user_input);
            if (days.size() == 0) {
                System.out.println("No days entered. Your class was not created.");
                return;
            }
            makeClass(user_input, classType, name, prof, location, startTime, endTime, days);
        } catch (BadClassTypeException bctex) {
            System.out.println(bctex.getMessage());
        } catch (BadTimeException btex) {
            System.out.println(btex.getMessage());
        }
    }

    // EFFECTS: makes a class with or without a textbook.
    public void makeClass(Scanner user_input, String classType, String name, String prof, String location, String startTime, String endTime, ArrayList<Integer> days) {
        if (inputHandler.wantToAddTextbook(user_input)) {
            Textbook textbook = new Textbook();
            textbook.setDetails(user_input);
            createUniClass(classType, name, prof, location, startTime, endTime, days, textbook);
        } else {
            Textbook textbook = new Textbook();
            createUniClass(classType, name, prof, location, startTime, endTime, days, textbook);
        }
    }


    // MODIFIES: this
    // EFFECTS: Creates a new UniClass from the parameters, adds a textbook to collectionOfTextbooks, and adds the class
    //          to the classMap.
    public void createUniClass(String classType, String name, String prof, String location, String startTime, String endTime,
                               ArrayList<Integer> days, Textbook textbook) {
        UniClass newClass = new UniClass(classType, name, prof, location, Integer.parseInt(startTime), Integer.parseInt(endTime), days,
                textbook);
        if (!textbook.equals(new Textbook())) { collectionOfTextbooks.addTextbook(newClass); }
        ArrayList<String> key = new ArrayList<>(Arrays.asList(classType, name));
        classMap.put(key, newClass);
        saveUniClass(newClass);
    }

    // EFFECTS: saves uc to a file.
    public void saveUniClass(UniClass uc) {
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
            e.printStackTrace();
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
        UniClass tempUniClass = new UniClass(classType, className, prof, room, Integer.parseInt(start), Integer.parseInt(end),
                days, tempTextbook);
        if (!tempTextbook.equals(new Textbook())) {
            collectionOfTextbooks.addTextbook(tempUniClass);
        }
        ArrayList<String> key = new ArrayList<>(Arrays.asList(classType, className));
        classMap.put(key, tempUniClass);
    }

    // EFFECTS: outputs stored UniClasses
    public void printItems() {
        System.out.println("**CLASSES**\n");
        if (classMap.size() == 1) {
            System.out.println("There is " + classMap.size() + " class stored.");
        } else {
            System.out.println("There are " + classMap.size() + " classes stored.");
        }
        System.out.println();
        if (classMap.size() > 0) {
            for (UniClass uniClass : classMap.values()) {
                uniClass.printItem();
                System.out.println();
            }
        }
    }

    // EFFECTS: prints the stored Textbooks.
    public void printTextbooks() {
        collectionOfTextbooks.printTextbooks();
    }

    // MODIFIES: this
    // EFFECTS: removes a class from the classMap, removes textbook from collectionOfTextbooks
    public void removeItem(Scanner user_input) {
        if (classMap.isEmpty()) {
            return;
        }
        printItems();
        System.out.println("To remove a class, provide the name and class type.");
        ArrayList<String> key = inputHandler.userKey(user_input);
        checkAndRemoveClass(key);
    }

    // MODIFIES: this
    // EFFECTS: removes a class from the classMap, removes textbook from collectionOfTextbooks
    private void checkAndRemoveClass(ArrayList<String> key) {
        if (classMap.containsKey(key)) {
            collectionOfTextbooks.removeTextbook(classMap.get(key));
            classMap.remove(key);
            System.out.println("The class was removed successfully");
            return;
        } else {
            System.out.println("The class you tried to remove was not found.");
        }
    }

    // MODIFIES: this.
    // EFFECTS: removes Textbook from collectionOfTextbooks.
    public void removeTextbook(Scanner user_input) {
        if (classMap.isEmpty()) {
            return;
        }
        printItems();
        System.out.println("To remove a textbook from a class, provide the name and class type of the class.");
        ArrayList<String> key = inputHandler.userKey(user_input);
        checkAndRemoveTextbook(key);

    }

    // MODIFIES: this.
    // EFFECTS: removes Textbook from collectionOfTextbooks.
    private void checkAndRemoveTextbook(ArrayList<String> key) {
        if (classMap.containsKey(key)) {
            UniClass uc = classMap.get(key);
            if (uc.getTextbook().equals(new Textbook())) {
                System.out.println("The class you searched for has no associated textbook.");
                return;
            } else {
                collectionOfTextbooks.removeTextbook(uc);
                uc.removeTextbook(uc.getTextbook());
                System.out.println("The textbook was removed successfully");
                return;
            }
        } else {
            System.out.println("The class you searched for does not exist.");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds textbook to UniClass in ClassMap and adds textbook to collectionOfTextbooks
    public void addTextbook(Scanner user_input) {
        if (classMap.isEmpty()) {
            return;
        }
        printItems();
        System.out.println("To add a textbook to a class, provide the name and class type of the class.");
        System.out.println("If the class already has a textbook, it will be replaced with the new textbook.");
        ArrayList<String> key = inputHandler.userKey(user_input);
        checkAndAddTextbook(user_input, key);
    }

    // MODIFIES: this
    // EFFECTS: adds textbook to UniClass in ClassMap and adds textbook to collectionOfTextbooks
    private void checkAndAddTextbook(Scanner user_input, ArrayList<String> key) {
        if (classMap.containsKey(key)) {
            UniClass uc = classMap.get(key);
            Textbook tempTextbook = new Textbook();
            tempTextbook.setDetails(user_input);
            uc.addTextbook(tempTextbook);
            collectionOfTextbooks.addTextbook(uc);
            System.out.println("Textbook added successfully.");
            return;
        } else {
            System.out.println("The class you were searching for does not exist.");
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

    public UniClassInputHandler getInputHandler() {
        return inputHandler;
    }
}

