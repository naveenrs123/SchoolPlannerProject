package inputs;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Textbook {

    private String title;
    private String author;
    private int pages;
    private UniClass associatedClass;

    public Textbook() {
        this.title = null;
        this.author = null;
        this.pages = 0;
        associatedClass = new UniClass();
    }

    public Textbook(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        associatedClass = new UniClass();
    }

    public void addUniClass(UniClass uc) {
        if (!associatedClass.equals(uc)) {
            associatedClass = uc;
            associatedClass.addTextbook(this);
        }
    }

    public void removeUniClass(UniClass uc) {
        if (associatedClass.equals(uc)) {
            associatedClass.removeTextbook(this);
            associatedClass = new UniClass();
        }
    }

    public String userTitle(Scanner user_input) {
        System.out.print("Title: ");
        String title = user_input.nextLine();
        return title;
    }

    public String userAuthor(Scanner user_input) {
        System.out.print("Author(s): ");
        String author= user_input.nextLine();
        return author;
    }

    public int userPages(Scanner user_input) {
        int pages;
        do {
            System.out.print("Pages: ");
            try {
                pages = user_input.nextInt();
                if (pages <= 0) {
                    System.out.println("Your textbook must have at least 1 page.");
                    continue;
                }
                else {
                    this.pages = pages;
                    return pages;
                }
            } catch (InputMismatchException imex) {
                System.out.println("Enter a valid integer value.");
            }
        } while (true);
    }

    public void setDetails(Scanner user_input) {
        title = userTitle(user_input);
        author = userAuthor(user_input);
        pages = userPages(user_input);
    }

    public void printTextbook() {
        System.out.println("Textbook: " + title + " by " + author + ", " + pages + " pages long.");
    }

    public int getPages() {
        return pages;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public UniClass getAssociatedClass() {
        return associatedClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Textbook textbook = (Textbook) o;
        return pages == textbook.pages && Objects.equals(title, textbook.title) &&
                Objects.equals(author, textbook.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, pages);
    }
}
