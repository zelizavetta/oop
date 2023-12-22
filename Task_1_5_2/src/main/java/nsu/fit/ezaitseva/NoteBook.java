package nsu.fit.ezaitseva;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NoteBook {
    ArrayList<Note> notes = new ArrayList<>();

    NoteBook() {
        this.notes = new ArrayList<>();
    }

    public void addNote(String heading) {
        Note note = new Note(heading);
        this.notes.add(note);
    }

    public void renameNote(Note curNote, String newHeading) {
        int oldNoteIdx = notes.indexOf(curNote);
        curNote.heading = newHeading;
        notes.set(oldNoteIdx, curNote);
    }

    public void setNote(Note note, String mainPart) {
        int oldNoteIdx = notes.indexOf(note);
        note.mainPart = mainPart;
        notes.set(oldNoteIdx, note);
    }

    public void removeNote(Note note) {
        notes.remove(note);
    }

    public void showNotesTimeRange(String startDateStr, String endDateStr, ArrayList<String> keywords) throws
            ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy H:mm");
        Date startDate = df.parse(startDateStr);
        Date endDate = df.parse(endDateStr);
        for (Note note : this.notes) {
            boolean contains = true;
            if (note.creatingData.after(startDate) && note.creatingData.before(endDate)) {
                for (String keyword : keywords) {
                    if (!note.heading.toLowerCase().contains(keyword.toLowerCase())) {
                        contains = false;
                        break;
                    }
                }
                if (contains) {
                    System.out.println(note.heading + '\n');
                }
            }
        }
    }
}