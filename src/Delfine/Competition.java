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
    public void addResult(Delfine.Result result) {results.add(result);}

    public String getNameCompetition() {
        return nameCompetition;
    }

    public LocalDate getDate() {
        return date;
    }

    public ArrayList<Delfine.Result> getResults() {
        return results;
    }

    public String toString(){
        return nameCompetition + "," + date;
    }
}
