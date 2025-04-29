package com.it326.BrickByBrick;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Journal {
    private List<Entry> entries;

    public Journal() {
        this.entries = new ArrayList<>();
    }

    /**
     * Add a new Entry to the journal with today's date.
     */
    public void addEntry(String content, int feeling) {
        Entry newEntry = new Entry(content, feeling);
        entries.add(newEntry);
    }

    public void addEntry(Entry entry){
        Entry newEntry = new Entry(entry.getContent(),entry.getFeeling(),entry.getDate());
        entries.add(newEntry);
    }

    /**
     * Deletes a specific Entry from the journal if it exists.
     */
    public boolean deleteEntry(Entry entry) {
        return entries.remove(entry);
    }

    /**
     * Edits an existing Entry’s content and feeling, 
     * only if that Entry was created today.
     */
    public void editEntry(Entry entry, String newContent, int newFeeling) {
        if (entries.contains(entry)) {
            entry.editEntry(newContent, newFeeling);
        } else {
            System.out.println("Entry not found in journal.");
        }
    }

    /**
     * Retrieves all entries for a specific date (read-only).
     * (No editing allowed if the date is not today anyway.)
     */
    public List<Entry> getEntriesByDate(LocalDate date) {
        List<Entry> result = new ArrayList<>();
        for (Entry e : entries) {
            if (e.getDate().equals(date)) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * Returns the average feeling across all entries ever added to this journal.
     * (If no entries exist, returns 0.0)
     */
    public double getAverageFeeling() {
        if (entries.isEmpty()) {
            return 0.0;
        }
        double total = 0;
        for (Entry e : entries) {
            total += e.getFeeling();
        }
        return total / entries.size();
    }

    /**
     * Displays the current average feeling and how many entries 
     * contribute to that average.
     */
    public void displayFeelingScale() {
        double avg = getAverageFeeling();
        System.out.println("Total number of entries: " + entries.size());
        System.out.println("Average feeling across all entries: " + avg);
    }

    /**
     * This method looks at all past dates (i.e., any date before 'today'),
     * groups the entries by date, and if there are multiple entries for a
     * single past date, it merges them into one big entry. The newly
     * created entry will have:
     *   - Content = concatenation of all entry contents (with a separator).
     *   - Feeling = averaged feeling (rounded) of those entries.
     * The old multiple entries for that date are removed and replaced by this single, merged entry.
     */
    public void combineAllPastEntries() {
        LocalDate today = LocalDate.now();
        
        // Group entries by date
        Map<LocalDate, List<Entry>> dateToEntries = new HashMap<>();
        for (Entry e : entries) {
            dateToEntries.computeIfAbsent(e.getDate(), k -> new ArrayList<>()).add(e);
        }

        // For each date that is before today, combine them if more than one entry
        for (Map.Entry<LocalDate, List<Entry>> mapEntry : dateToEntries.entrySet()) {
            LocalDate entryDate = mapEntry.getKey();
            List<Entry> dayEntries = mapEntry.getValue();
            
            // Only merge if date is in the past AND there are multiple entries
            if (entryDate.isBefore(today) && dayEntries.size() > 1) {
                StringBuilder mergedContent = new StringBuilder();
                int totalFeeling = 0;
                
                // Build new content and sum up the feelings
                for (Entry e : dayEntries) {
                    mergedContent
                        .append(e.getContent())
                        .append("\n---\n");
                    totalFeeling += e.getFeeling();
                }
                
                // Compute the average feeling (rounding)
                int averageFeeling = (int) Math.round(
                    (double) totalFeeling / dayEntries.size()
                );
                
                // Create the new merged entry with the correct (past) date
                Entry mergedEntry = new Entry(
                    mergedContent.toString().trim(), // remove trailing newlines if desired
                    averageFeeling,
                    entryDate
                );

                // Remove old entries from the master list
                entries.removeAll(dayEntries);
                // Add the newly merged single entry
                entries.add(mergedEntry);
            }
        }
    }
}
