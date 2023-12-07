package Delfine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandling {
    public ArrayList<Delfine.Swimmer> swimmers = new ArrayList<>();
    public ArrayList<Competition> competitions = new ArrayList<>();
    public ArrayList<Result> results = new ArrayList<>();

    public void saveSwimmersToTxtFile() {
        try {
            PrintStream ps = new PrintStream(new FileOutputStream("Swimmers.txt"), true);
            for (Delfine.Swimmer fileSwimmer : swimmers) {
                ps.println(fileSwimmer);
            }
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveCompetitionsToTxtFile() {
        try {
            PrintStream ps = new PrintStream(new FileOutputStream("Competitions.txt"), true);
            for (Competition fileCompetition : competitions) {
                ps.println(fileCompetition);
            }
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveResultsToTxtFile() {
        try {
            PrintStream ps = new PrintStream(new FileOutputStream("Results.txt"), true);
            for (Result fileResult : results) {
                ps.println(fileResult);
            }
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readSwimmersFromTxtFile() {
        File file = new File("Swimmers.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                boolean swimmerIsDuplicate = false;
                String line = scanner.nextLine();
                String[] attributes = line.split(",");

                String name = attributes[0];
                int age = Integer.parseInt(attributes[1]);
                Boolean membershipActive = Boolean.parseBoolean(attributes[2]);
                boolean isCompetitiveSwimmer = Boolean.parseBoolean(attributes[3]);
                String discipline = attributes[4];
                LocalDate registrationDate = LocalDate.parse(attributes[5]);
                LocalDate inactiveStartDate = LocalDate.parse(attributes[6]);
                for (Delfine.Swimmer testDuplicateSwimmer : swimmers){
                    if (testDuplicateSwimmer.getName().equalsIgnoreCase(name)){
                        swimmerIsDuplicate = true;
                    }
                }
                if (!swimmerIsDuplicate){
                    Delfine.Swimmer swimmer = new Delfine.Swimmer(name, age, membershipActive, isCompetitiveSwimmer, discipline, registrationDate, inactiveStartDate);
                    swimmers.add(swimmer);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readCompetitionsFromTxtFile() {
        File file = new File("Competitions.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                boolean competitionIsDuplicate = false;
                String line = scanner.nextLine();
                String[] attributes = line.split(",");

                String nameCompetition = attributes[0];
                LocalDate date = LocalDate.parse(attributes[1]);

                for (Competition testDuplicateCompetitions : competitions){
                    if (testDuplicateCompetitions.getNameCompetition().equalsIgnoreCase(nameCompetition) && testDuplicateCompetitions.getDate().equals(date)){
                        competitionIsDuplicate = true;
                    }
                }
                if (!competitionIsDuplicate){
                    Competition competition = new Competition(nameCompetition, date);
                    competitions.add(competition);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readResultsFromTxtFile() {
        File file = new File("Results.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                boolean resultIsDuplicate = false;
                String line = scanner.nextLine();
                String[] attributes = line.split(",");

                String swimmerName = attributes[0];
                int placement = Integer.parseInt(attributes[1]);
                double time = Double.parseDouble(attributes[2]);
                String discipline = attributes[3];
                String competitionName = attributes[4];
                LocalDate competitionDate = LocalDate.parse(attributes[5]);

                for (Result testDuplicateResult : results) {
                    if (testDuplicateResult.getSwimmerName().equalsIgnoreCase(swimmerName) && testDuplicateResult.getCompetition().getNameCompetition().equalsIgnoreCase(competitionName)) {
                        resultIsDuplicate = true;
                    }
                }
                if (!resultIsDuplicate) {
                    Competition competition = findCompetitionByNameAndDate(competitionName, competitionDate);
                    if (competition != null) {
                        Result result = new Result(swimmerName, placement, time, discipline, competition);
                        results.add(result);
                        for (Delfine.Swimmer swimmer : swimmers){
                            if (swimmer.getName().equalsIgnoreCase(swimmerName)){
                                swimmer.addResult(result);
                            }
                        }
                        competition.addResult(result);
                    }
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Competition findCompetitionByNameAndDate(String name, LocalDate date) {
        for (Competition comp : competitions) {
            if (comp.getNameCompetition().equals(name) && comp.getDate().equals(date)) {
                return comp;
            }
        }
        return null;
    }

}
