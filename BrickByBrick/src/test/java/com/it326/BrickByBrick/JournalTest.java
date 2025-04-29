package com.it326.BrickByBrick;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JournalTest {

    private Journal journal;

    @BeforeEach
    public void setUp() {
        journal = new Journal();
    }

    @AfterEach
    public void clean_up(){
        journal = null;
    }
      
    @Test
    public void testAddEntryAndGetEntriesByDate() {
        journal.addEntry("My first entry", 7);
        LocalDate today = LocalDate.now();
        List<Entry> entries = journal.getEntriesByDate(today);

        assertEquals(1, entries.size());
        assertEquals("My first entry", entries.get(0).getContent());
        assertEquals(7, entries.get(0).getFeeling());
    }

    @Test
    public void testDeleteEntry() {
        Entry entry = new Entry("To be deleted", 5);
        journal.addEntry("To be deleted", 5);

        List<Entry> todayEntries = journal.getEntriesByDate(LocalDate.now());
        Entry toDelete = todayEntries.get(0);

        boolean removed = journal.deleteEntry(toDelete);
        assertTrue(removed);
        assertTrue(journal.getEntriesByDate(LocalDate.now()).isEmpty());
    }

    @Test
    public void testEditEntryWithValidDate() {
        journal.addEntry("Before edit", 5);
        Entry entry = journal.getEntriesByDate(LocalDate.now()).get(0);

        journal.editEntry(entry, "After edit", 8);
        assertEquals("After edit", entry.getContent());
        assertEquals(8, entry.getFeeling());
    }

    @Test
    public void testEditEntryWithPastDate() {
        Entry pastEntry = new Entry("Past", 4, LocalDate.now().minusDays(1));
        journal.deleteEntry(pastEntry); // Ensure no duplicates
        journal.getEntriesByDate(LocalDate.now().minusDays(1)).clear();
        journal.getEntriesByDate(LocalDate.now()).clear();

        // Manually inject past entry (bypass addEntry)
        journal.getEntriesByDate(LocalDate.now()).add(pastEntry); // Injecting for test purpose

        pastEntry.editEntry("Should not edit", 6);
        assertEquals("Past", pastEntry.getContent());
        assertEquals(4, pastEntry.getFeeling());
    }

    @Test
    public void testGetAverageFeeling() {
        journal.addEntry("Entry 1", 6);
        journal.addEntry("Entry 2", 8);
        journal.addEntry("Entry 3", 10);
        assertEquals(8.0, journal.getAverageFeeling(), 0.001);
    }

    @Test
    public void testCombineAllPastEntries() {
        LocalDate pastDate = LocalDate.now().minusDays(2);

        Entry past1 = new Entry("Past one", 4, pastDate);
        Entry past2 = new Entry("Past two", 8, pastDate);

        // Bypass addEntry and inject manually
        journal.getEntriesByDate(LocalDate.now()).clear(); // avoid conflict
        journal.getEntriesByDate(pastDate).add(past1);
        journal.getEntriesByDate(pastDate).add(past2);

        journal.combineAllPastEntries();

        List<Entry> mergedEntries = journal.getEntriesByDate(pastDate);
        assertEquals(1, mergedEntries.size());

        Entry merged = mergedEntries.get(0);
        assertTrue(merged.getContent().contains("Past one"));
        assertTrue(merged.getContent().contains("Past two"));
        assertEquals(6, merged.getFeeling());
    }  
}
