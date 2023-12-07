package Delfine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class SwimmerOperation {
    Scanner scanner = new Scanner(System.in);
    public ArrayList<Swimmer> swimmers;
    public ArrayList<Competition> competitions;
    public ArrayList<Result> results;
    public Menu menu;
    FileHandling fileHandling = new FileHandling();

    public SwimmerOperation() {
        this.menu = new Menu();
        this.results = fileHandling.results;
        this.swimmers = fileHandling.swimmers;
        this.competitions = fileHandling.competitions;
    }

    public void swimmerOptions() {
        fileHandling.readSwimmersFromTxtFile();
        fileHandling.readCompetitionsFromTxtFile();
        fileHandling.readResultsFromTxtFile();
        System.out.println("What do you wish to do?" +
                "\n 1. Add new member" +
                "\n 2. Delete member" +
                "\n 3. See swimmer details" +
                "\n 4. Search options" +
                "\n 5. Change specifics of certain member" +
                "\n 6. Add best training time" +
                "\n 9. Main Menu");
        int choose = scanner.nextInt();
        scanner.nextLine();
        switch (choose) {
            case 1 -> createSwimmer();
            case 2 -> deleteSwimmer();
            case 3 -> seeSwimmerDitails();
            case 4 -> search();
            case 5 -> changeSwimmerAtribute();
            case 6 -> newBestTrainingTime();
            case 9 -> {
                menu.run();
            }
        }
    }

    public void createSwimmer() {
        String discipline = "Null";
        String name;
        System.out.println("Whats the name of the swimmer? (Full name)");
        name = scanner.nextLine();

        for (Swimmer swimmer : swimmers) {
            if (name.equalsIgnoreCase(swimmer.getName())) {
                System.out.println("Name already taken. Please start over.");
                menu.run();
            }
        }
        System.out.println("Whats the age of the swimmer? (In years)");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Is the swimmer a competitive swimmer? (yes/no)");
        boolean isCompetitiveSwimmer = scanner.nextLine().equalsIgnoreCase("yes");

        System.out.println("What is the swimmers main discipline?" +
                "\n 1. Crawl" +
                "\n 2. Breaststroke" +
                "\n 3. Butterfly" +
                "\n 4. Backcrawl" +
                "\n 5. Megley");
        int choose = scanner.nextInt();
        switch (choose) {
            case 1 -> discipline = "Crawl";
            case 2 -> discipline = "Breaststroke";
            case 3 -> discipline = "Butterfly";
            case 4 -> discipline = "Backcrawl";
            case 5 -> discipline = "Megley";
        }
        LocalDate registrationDate = LocalDate.now();
        //Det er i stedet for "null" som ikke kunne blive parsed i FileHandling.java:72
        LocalDate membershipActiveDate = LocalDate.of(0001,1,1);
        //Går ud fra alle nye svømmere ikke starter med at active
        Swimmer newSwimmer = new Swimmer(name, age, true, isCompetitiveSwimmer, discipline, registrationDate, membershipActiveDate);
        swimmers.add(newSwimmer);
        fileHandling.saveSwimmersToTxtFile();
        System.out.println("Swimmer added successfully.");
        swimmerOptions();
    }

    public void deleteSwimmer() {
        ArrayList<Competition> competitionsToDelete = new ArrayList<>();
        System.out.println("Whats the name off the swimmer ('9' to exit)");
        String name = scanner.nextLine();
        if (name.equals("9")) {swimmerOptions();}

        Swimmer swimmerToDelete = null;
        for (Swimmer swimmer : swimmers) {
            if (name.equalsIgnoreCase(swimmer.getName())) {
                System.out.println("Are you sure you want to delete " + swimmer.getName() + "? Y/N");
                String yesNo = scanner.nextLine();
                if (yesNo.equalsIgnoreCase("y")) {
                    swimmerToDelete = swimmer;
                }
            }
        }
        if (swimmerToDelete == null) {
            System.out.println("Swimmer not found, try again");
            deleteSwimmer();
        }
        for (Competition competition : competitions){
            for (Result result : results){
                if (competition.getResults().equals(result)){
                    competitionsToDelete.add(competition);
                }
            }
        }
        for (Competition competition : competitionsToDelete){
            System.out.println("deleting : "+competition);
            competitions.remove(competition);
        }
        results.removeIf(result -> result.getSwimmerName().equalsIgnoreCase(name));
        swimmers.remove(swimmerToDelete);
        fileHandling.saveResultsToTxtFile();
        fileHandling.saveCompetitionsToTxtFile();
        fileHandling.saveSwimmersToTxtFile();
        System.out.println("Deletion successful");

        swimmerOptions();
    }

    public Result findBestTrainingTime(Swimmer swimmer) {
        Result bestTrainingResult = null;
        for (Result result : swimmer.getCompetitionHistory()) {
            if (result.getCompetition().getNameCompetition().equalsIgnoreCase("Training") &&
                    (bestTrainingResult == null || result.getTime() < bestTrainingResult.getTime())) {
                bestTrainingResult = result;
            }
        }
        return bestTrainingResult;
    }

    public void search() {
        Boolean swimmerFound = false;
        int chosenNumber;
        System.out.println("What do you want to search by?" +
                "\n 1. View full member list" +
                "\n 2. Age" +
                "\n 3. See all Competitive swimmers" +
                "\n 4. See all Active members" +
                "\n 5. See all Passive members" +
                "\n 9. Exit");
        chosenNumber = scanner.nextInt();
        scanner.nextLine(); //scanner bug
        switch (chosenNumber) {
            case 1 -> {
                for (Swimmer swimmer : swimmers){
                    System.out.println(swimmer.getName()+" "+swimmer.getAge());
                }
                swimmerOptions();
            }
            case 2 -> {
                System.out.println("Whats the age you want to search?");
                swimmerFound = false;
                int age = scanner.nextInt();
                for (Swimmer swimmer : swimmers) {
                    if (age == swimmer.getAge()) {
                        System.out.println("Name: "+swimmer.getName()+" Age: "+swimmer.getAge());
                        swimmerFound = true;
                    }
                }
                if (!swimmerFound) System.out.println("Swimmer by that name not found in system");
                swimmerOptions();
            }
            case 3 -> {
                swimmerFound = false;
                for (Swimmer swimmer : swimmers) {
                    if (swimmer.isCompetitiveSwimmer()) {
                        System.out.println("Name: "+swimmer.getName()+" Age: "+swimmer.getAge());
                        swimmerFound = true;
                    }
                    if (!swimmerFound)System.out.println("No competitive swimmers found");
                }
                swimmerOptions();
            }
            case 4 -> {
                swimmerFound = false;
                for (Swimmer swimmer : swimmers) {
                    if (swimmer.getMembershipActive()) {
                        System.out.println("Name: "+swimmer.getName()+" Age: "+swimmer.getAge());
                        swimmerFound =true;
                    }
                    if (!swimmerFound)System.out.println("No active member found");
                }
                swimmerOptions();
            }
            case 5 -> {
                swimmerFound = false;
                for (Swimmer swimmer : swimmers) {
                    if (!swimmer.getMembershipActive()) {
                        System.out.println("Name: "+swimmer.getName()+" Age: "+swimmer.getAge());
                        swimmerFound = true;
                    }
                    if(!swimmerFound)System.out.println("No inactive member found");
                }
                swimmerOptions();
            }
        }
    }

    public void changeSwimmerAtribute() {
        String pasivActive = "null";
        String competitiveMotion = "null";
        String discipline = "null";
        boolean swimmerFound = false;
        System.out.println("Whats the name of the member you what to change? (9 for exit)");
        String name = scanner.nextLine();
        if (name.equals("9")) swimmerOptions();
        for (Swimmer swimmer : swimmers) {
            if (name.equalsIgnoreCase(swimmer.getName())) {
                swimmerFound = true;
                System.out.println(swimmer);
                System.out.println("What attribute do you what to change?" +
                        "\n 1. age" +
                        "\n 2. membership Active/passive" +
                        "\n 3. Competitive Swimmer / Motion Swimmer" +
                        "\n 4. Main discipline" +
                        "\n 5. Registration date" +
                        "\n 6. The date membership last got changed from Active/passive");
                int choose = scanner.nextInt();
                switch (choose) {
                    case 1 -> {
                        System.out.println("What is the new age?");
                        int age = scanner.nextInt();
                        swimmer.setAge(age);
                        fileHandling.saveSwimmersToTxtFile();
                        System.out.println(swimmer.getName() + " updated age to " + age);
                        swimmerOptions();
                    }
                    case 2 -> {
                        boolean activePasiv = swimmer.getMembershipActive();
                        if (activePasiv) {
                            swimmer.setMembershipActiveDate(LocalDate.now());
                            swimmer.setMembershipActive(false);
                            pasivActive = "a passive member";
                        } else {
                            swimmer.setMembershipActiveDate(LocalDate.now());
                            swimmer.setMembershipActive(true);
                            pasivActive = "an active member";
                        }
                        fileHandling.saveSwimmersToTxtFile();
                        System.out.println(swimmer.getName() + " is from todays date " + pasivActive);
                        swimmerOptions();
                    }
                    case 3 -> {
                        boolean competitive = swimmer.isCompetitiveSwimmer();
                        if (competitive) {
                            swimmer.setCompetitiveSwimmer(false);
                            competitiveMotion = "Motion";
                        } else {
                            swimmer.setCompetitiveSwimmer(true);
                            competitiveMotion = "competitive";
                        }
                        fileHandling.saveSwimmersToTxtFile();
                        System.out.println(swimmer.getName() + " is now " + competitiveMotion + " swimmer");
                        swimmerOptions();
                    }
                    case 4 -> {
                        System.out.println("What is the swimmers main discipline?" +
                                "\n 1. Crawl" +
                                "\n 2. Breaststroke" +
                                "\n 3. Butterfly" +
                                "\n 4. Backcrawl" +
                                "\n 5. Megley");
                        int choose2 = scanner.nextInt();
                        switch (choose2) {
                            case 1 -> discipline = "Crawl";
                            case 2 -> discipline = "Breaststroke";
                            case 3 -> discipline = "Butterfly";
                            case 4 -> discipline = "Backcrawl";
                            case 5 -> discipline = "Megley";
                        }
                        swimmer.setDiscipline(discipline);
                        fileHandling.saveSwimmersToTxtFile();
                        System.out.println(swimmer.getName() + " new main discipline is " + discipline);
                        swimmerOptions();
                    }
                    case 5 -> {
                        System.out.println("Current registration date: "+swimmer.getRegistrationDate());
                        System.out.println("What day?");
                        int day = scanner.nextInt();
                        System.out.println("What month?");
                        int month = scanner.nextInt();
                        System.out.println("What year?");
                        int year = scanner.nextInt();
                        LocalDate newRegistrationDate = LocalDate.of(year, month, day);
                        swimmer.setRegistrationDate(newRegistrationDate);
                        fileHandling.saveSwimmersToTxtFile();
                        System.out.println("New registration date: "+swimmer.getRegistrationDate());
                        swimmerOptions();
                    }
                    case 6-> {
                        LocalDate replacementForNull = LocalDate.of(0001,1,1);
                        if (!replacementForNull.equals(swimmer.getMembershipActiveDate())){
                            System.out.println("What day?");
                            int day = scanner.nextInt();
                            System.out.println("What month?");
                            int month = scanner.nextInt();
                            System.out.println("What year?");
                            int year = scanner.nextInt();
                            LocalDate membershibActiveDate = LocalDate.of(year, month, day);
                            swimmer.setMembershipActiveDate(membershibActiveDate);
                            fileHandling.saveSwimmersToTxtFile();
                            System.out.println("The date membership last got changed from Active/passive is now: "+swimmer.getRegistrationDate());
                            swimmerOptions();
                        }else System.out.println("Error: Member needs to have his membershib status set from active to passive before this action can be done");
                    }
                }
            }
        }
        if (!swimmerFound) {
            System.out.println("Swimmer wasn't found, try again");
            changeSwimmerAtribute();
        }
    }

    public void newBestTrainingTime() {
        System.out.println("What is the name of the swimmer? (9 for exit)");
        String name = scanner.nextLine();
        if (name.equals("9")) swimmerOptions();

        Swimmer selectedSwimmer = null;
        for (Swimmer swimmer : swimmers) {
            if (name.equalsIgnoreCase(swimmer.getName())) {
                selectedSwimmer = swimmer;
            }
        }

        if (selectedSwimmer == null) {
            System.out.println("Swimmer not found, try again.");
            newBestTrainingTime();
        }

        System.out.println("What discipline was the best time recorded in?" +
                "\n 1. Crawl" +
                "\n 2. Breaststroke" +
                "\n 3. Butterfly" +
                "\n 4. Backcrawl" +
                "\n 5. Megley");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Scanner bug
        String discipline = null;
        switch (choice) {
            case 1 -> discipline = "Crawl";
            case 2 -> discipline = "Breaststroke";
            case 3 -> discipline = "Butterfly";
            case 4 -> discipline = "Backcrawl";
            case 5 -> discipline = "Megley";
        }

        System.out.println("What was the time? (in minutes)");
        double time = scanner.nextDouble();
        scanner.nextLine(); // Scanner bug

        System.out.println("Enter the date for the best time:");
        System.out.println("Day of the day:");
        int day = scanner.nextInt();
        System.out.println("Month (1-12):");
        int month = scanner.nextInt();
        System.out.println("Year:");
        int year = scanner.nextInt();
        scanner.nextLine(); // Scanner bug

        LocalDate localDate = LocalDate.of(year, month, day);
        Competition newCompetition = new Competition("Training", localDate);
        competitions.add(newCompetition);
        Result newResult = new Result(selectedSwimmer.getName(), 0, time, discipline, newCompetition);
        selectedSwimmer.addResult(newResult);
        results.add(newResult);
        fileHandling.saveResultsToTxtFile();
        fileHandling.saveCompetitionsToTxtFile();
        System.out.println("New best training time recorded for " + selectedSwimmer.getName());
        swimmerOptions();
    }

    public void seeSwimmerDitails(){
        boolean swimmerFound = false;
        System.out.println("Whats the name off the swimmer you want to look op? (9 for exit)");
        String name = scanner.nextLine();
        if (name.equalsIgnoreCase("9"))swimmerOptions();
        for (Swimmer swimmer : swimmers){
            if (name.equalsIgnoreCase(swimmer.getName())){
                printSwimmerDitails(swimmer);
                swimmerFound = true;
            }
        }
        if (!swimmerFound){
            System.out.println("Swimmer not found, try again");
            seeSwimmerDitails();
        }swimmerOptions();
    }

    public void printSwimmerDitails(Swimmer swimmer) {
        boolean hasCompetedInCompetition = false;
        System.out.println("Name: " + swimmer.getName());
        System.out.println("Age: " + swimmer.getAge());
        System.out.println("Active Member: " + swimmer.getMembershipActive());
        System.out.println("Competitive Swimmer: " + swimmer.isCompetitiveSwimmer());
        System.out.println("Main Discipline: " + swimmer.getDiscipline());
        System.out.println("Registration date: " + swimmer.getRegistrationDate());
        LocalDate replacementForNull = LocalDate.of(0001,1,1);
        if (!replacementForNull.equals(swimmer.getMembershipActiveDate())){
            if (swimmer.membershipActive){
                System.out.println("Date membership was changed to active: "+swimmer.getMembershipActiveDate());
            }else System.out.println("Date membership was changed to passive: "+swimmer.getMembershipActiveDate());
        }

        Result bestTrainingTime = findBestTrainingTime(swimmer);
        if (bestTrainingTime != null) {
            System.out.println("Best Training Time: " + bestTrainingTime.getTime() + " minutes in " + bestTrainingTime.getDiscipline());
        } else System.out.println("No training times recorded for " + swimmer.getName());

        for (Result result : results) {
            if (result.getSwimmerName().equalsIgnoreCase(swimmer.getName()) && !result.getCompetiotionName().equalsIgnoreCase("training")) {
                hasCompetedInCompetition = true;
                System.out.println(swimmer.getName() + " has competed in " + result.getCompetiotionName());
                System.out.println("Discipline : "+result.getDiscipline());
                System.out.println("Placement : "+result.getPlacement());
                System.out.println("Time : "+result.getTime());
            }
        }


        if (!hasCompetedInCompetition) System.out.println("Swimmer has never competed in competitions before");
    }
}
