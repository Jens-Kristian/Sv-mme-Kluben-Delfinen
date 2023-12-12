package Delfine;

import java.io.File;
import java.time.LocalDate;
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
                "\n 2. View all unpaid memberships" +
                "\n 3. Confirm membership payment" +
                "\n 9. Main Menu");
        int choose = scanner.nextInt();
        scanner.nextLine();
        switch (choose) {
            case 1 -> totalEarnings();
            case 2 -> viewUnPaidMembersShips();
            case 3 -> confirmMembershipPayment();

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

            if (swimmer.hasPaid) {
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
    public void viewUnPaidMembersShips(){
        for (Swimmer swimmer : swimmers){
            if (!swimmer.hasPaid){
                System.out.println("Name: "+swimmer.getName()+" Registration Date:"+swimmer.getRegistrationDate()+" Last paid: "+swimmer.getHasPaidDate());
            }
        }econOptions();
    }
    public void confirmMembershipPayment(){
        System.out.println("What swimmer do you want to confirm payment for?");
        String name = scanner.nextLine();
        for (Swimmer swimmer : swimmers){
            if (swimmer.getName().equalsIgnoreCase(name)){
                if (swimmer.isHasPaid()){
                    System.out.println("Swimmer has already paid");
                    econOptions();
                }else {
                    swimmer.setHasPaid(true);
                    System.out.println("What day was the payment processed?");
                    int day = scanner.nextInt();
                    System.out.println("What month?");
                    int month = scanner.nextInt();
                    System.out.println("What year?");
                    int year = scanner.nextInt();
                    LocalDate paymentProcessDay = LocalDate.of(year, month, day);
                    swimmer.setHasPaidDate(paymentProcessDay);
                    fileHandling.saveSwimmersToTxtFile();
                    System.out.println("Payment confirmed");
                }
            }
        }econOptions();
    }
}