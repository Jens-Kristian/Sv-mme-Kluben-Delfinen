package Delfine;

public class Result {
    public String swimmerName;
    public int placement;
    public double time;
    public String discipline;
    public Competition competition;

    public Result(String swimmerName, int placement, double time, String discipline, Competition competition) {
        this.swimmerName = swimmerName;
        this.placement = placement;
        this.time = time;
        this.discipline = discipline;
        this.competition = competition;
    }

    public String getSwimmerName() {
        return swimmerName;
    }

    public int getPlacement() {
        return placement;
    }

    public double getTime() {
        return time;
    }

    public String getDiscipline() {
        return discipline;
    }

    public Competition getCompetition() {
        return competition;
    }

    public String getCompetiotionName(){return competition.nameCompetition;}

    public String toString() {
        return swimmerName + "," + placement + "," + time + "," + discipline + "," + competition.getNameCompetition() + "," + competition.getDate();
    }


}
