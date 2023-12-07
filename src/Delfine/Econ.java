package Delfine;

import java.util.ArrayList;
import java.util.Scanner;

public class Econ {
    Scanner scanner = new Scanner(System.in);
    public ArrayList<Swimmer> swimmers;
    private Menu menu;

    FileHandling fileHandling = new FileHandling();
    public Econ() {
        menu = new Menu();
        this.swimmers = fileHandling.swimmers;
    }
    public void econOptions() {
        fileHandling.readSwimmersFromTxtFile();
        System.out.println("What do you wish to do?" +
                "\n 1. View yearly earnings for memberships" +
                "\n 2. View inactive members" +
                "\n 9. Main Menu");
        int choose = scanner.nextInt();
        scanner.nextLine();
        switch (choose) {
            case 1 -> totalEarnings();
            case 2 -> viewInactiveMembers();

            case 9 -> {
                menu.run();
            }
        }
    }

    public void totalEarnings() {
        int activeMembers = 0;
        int passivMembers = 0;
        int juniorMembers = 0;
        int seniorMembers = 0;
        int superSeniorMembers = 0;

        for (Swimmer swimmer : swimmers) {

            if (swimmer.getMembershipActive()) {
                activeMembers++;
                if (swimmer.getAge() < 18) {
                    juniorMembers++;
                } else if (swimmer.getAge() >= 18 && swimmer.getAge() < 60) {
                    seniorMembers++;
                } else if (swimmer.getAge() >= 60) {
                    superSeniorMembers++;
                }
            } else {
                passivMembers++;
            }
        }

        int juniorMembersPrice = juniorMembers * 1000;
        int seniorMembersPrice = seniorMembers * 1600;
        int superSeniorMembersPrice = superSeniorMembers * 1200;
        int passiveMembersPrice = passivMembers * 500;
        int totalEarnings = juniorMembersPrice + seniorMembersPrice + superSeniorMembersPrice + passiveMembersPrice;

        System.out.println("The yearly earnings for member ships are:" +
                "\n With a total off " + swimmers.size() + " members" +
                "\n Under 18 swimmers =[" + juniorMembers + "] : " + juniorMembersPrice + "kr" +
                "\n Over 18 under 60 swimmers =[" + seniorMembers + "] : " + seniorMembersPrice + "kr" +
                "\n Over 60 swimmers =[" + superSeniorMembers + "] : " + superSeniorMembersPrice + "kr" +
                "\n Passive Members =[" + passivMembers + "] : " + passiveMembersPrice + "kr" +
                "\n Total = " + totalEarnings + "kr");
        econOptions();
    }
    public void viewInactiveMembers(){
        boolean ableToFindInactiveMembers = false;
        for (Swimmer swimmer : swimmers){
            if (!swimmer.membershipActive){
                ableToFindInactiveMembers = true;
                System.out.println("Name: " +swimmer.getName()+
                        "\nAge: "+swimmer.getAge()+
                        "\nRegistration date: "+swimmer.getRegistrationDate()+
                        "\nDate of inactivity: "+swimmer.getMembershipActiveDate()+
                        "\n__________________________");
            }
        }
        if (!ableToFindInactiveMembers) System.out.println("No inactive members found");
        econOptions();
    }
}