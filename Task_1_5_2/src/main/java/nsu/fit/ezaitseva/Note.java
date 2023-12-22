package nsu.fit.ezaitseva;


import java.util.Date;

public class Note {
    String heading;
    String mainPart;
    Date creatingData;
    int id;
    private static int freeId = 0;

    Note(String heading) {
        this.heading = heading;
        this.mainPart = "";
        this.creatingData = new Date();
        this.id = freeId;
        freeId++;
    }


}