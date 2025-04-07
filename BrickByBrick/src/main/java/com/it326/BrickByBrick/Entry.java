import java.time.LocalDate;

public class Entry {
    private String content;
    private LocalDate date;
    private int feeling;  // Must be between 1 and 10 (inclusive)

    /**
     * Primary constructor: Creates a new Entry with today's date.
     * Throws IllegalArgumentException if feeling is out of [1..10].
     */
    public Entry(String content, int feeling) {
        if (feeling < 1 || feeling > 10) {
            throw new IllegalArgumentException("Feeling must be between 1 and 10 inclusive.");
        }
        this.content = content;
        this.feeling = feeling;
        this.date = LocalDate.now();
    }

    /**
     * Overloaded constructor: Allows specifying date explicitly
     * (used when merging past entries).
     */
    public Entry(String content, int feeling, LocalDate date) {
        if (feeling < 1 || feeling > 10) {
            throw new IllegalArgumentException("Feeling must be between 1 and 10 inclusive.");
        }
        this.content = content;
        this.feeling = feeling;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getFeeling() {
        return feeling;
    }

    /**
     * Edits this entry only if the entry’s date is today's date.
     * Otherwise, it does nothing (or you could throw an exception).
     * Also enforces the feeling range [1..10].
     */
    public void editEntry(String newContent, int newFeeling) {
        LocalDate today = LocalDate.now();
        if (!this.date.equals(today)) {
            System.out.println("You can no longer edit this entry because its date has passed.");
            return;
        }
        if (newFeeling < 1 || newFeeling > 10) {
            throw new IllegalArgumentException("Feeling must be between 1 and 10 inclusive.");
        }
        this.content = newContent;
        this.feeling = newFeeling;
    }
}
