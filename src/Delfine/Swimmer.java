package Delfine;
import java.time.LocalDate;
import java.util.ArrayList;

public class Swimmer {
    public  String name;
    public int age;
    public boolean membershipActive;
    public boolean isCompetitiveSwimmer;
    public ArrayList<Result> competitionHistory;
    public String discipline;
    public LocalDate registrationDate;
    public LocalDate membershipActiveDate;

    public Swimmer(String name, int age, boolean membershipActive, boolean isCompetitiveSwimmer, String discipline, LocalDate registrationDate,LocalDate membershipActiveDate) {
        this.name = name;
        this.age = age;
        this.membershipActive = membershipActive;
        this.isCompetitiveSwimmer = isCompetitiveSwimmer;
        this.discipline = discipline;
        this.registrationDate = registrationDate;
        this.competitionHistory = new ArrayList<>();
        this.membershipActiveDate = membershipActiveDate;
    }

    public void addResult(Result result) {
        competitionHistory.add(result);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Boolean getMembershipActive() {
        return membershipActive;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isCompetitiveSwimmer() {
        return isCompetitiveSwimmer;
    }

    public void setCompetitiveSwimmer(boolean competitiveSwimmer) {isCompetitiveSwimmer = competitiveSwimmer;}

    public ArrayList<Result> getCompetitionHistory(){return competitionHistory;}

    public LocalDate getRegistrationDate(){return registrationDate;}

    public void setRegistrationDate(LocalDate registrationDate) {this.registrationDate = registrationDate;}

    public void setMembershipActiveDate(LocalDate membershipActiveDate) {this.membershipActiveDate = membershipActiveDate;}

    public LocalDate getMembershipActiveDate() {return membershipActiveDate;}

    public void setMembershipActive(Boolean membershipActive) {this.membershipActive = membershipActive;}

    public String toString(){
        return name+","+age+","+membershipActive+","+isCompetitiveSwimmer+","+discipline+","+ registrationDate+","+membershipActiveDate;
    }

}