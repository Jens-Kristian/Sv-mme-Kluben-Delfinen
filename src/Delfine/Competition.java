package Delfine;
import java.time.LocalDate;
import java.util.ArrayList;


public class Competition {
    public String nameCompetition;
    public LocalDate date;
    public ArrayList<Delfine.Result> results;

    public Competition(String nameCompetition, LocalDate date) {
        this.nameCompetition = nameCompetition;
        this.date = date;
        this.results = new ArrayList<>();
    }
    public String getNameCompetition() {
        return nameCompetition;
    }

    public LocalDate getDate() {
        return date;
    }

    public String toString(){
        return nameCompetition + "," + date;
    }
}
