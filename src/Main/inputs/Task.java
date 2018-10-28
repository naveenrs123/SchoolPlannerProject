package inputs;

abstract public class Task implements StoredObject {

    protected enum Importance {
        LOW, MEDIUM, HIGH, EXTREME
    }

    protected String taskType;
    protected String description;
    protected Importance importance;

    // EFFECTS: Creates a new Task object and sets all its fields.
    public Task(String type, String desc, String importance) {
        taskType = type;
        description = desc;
        switch (importance.toUpperCase()) {
            case "LOW":
                this.importance = Importance.LOW;
                break;
            case "MEDIUM":
                this.importance = Importance.MEDIUM;
                break;
            case "HIGH":
                this.importance = Importance.HIGH;
                break;
            case "EXTREME":
                this.importance = Importance.EXTREME;
                break;
            default:
                this.importance = Importance.LOW;
        }

    }

    // EFFECTS: prints the details of a Task to the console.
    abstract public void printItem();

    // EFFECTS: gets the type.
    public String getType() { return taskType; }

    // EFFECTS: gets the description.
    public String getDescription() { return description; }

    // EFFECTS: gets the importance level.
    public String getImportance() {
        switch (importance) {
            case LOW:
                return "LOW";
            case MEDIUM:
                return "MEDIUM";
            case HIGH:
                return "HIGH";
            case EXTREME:
                return "EXTREME";
            default:
                return "LOW";
        }
    }
}
