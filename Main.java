package org.example;

import java.util.Scanner;

/**
 * Program: The Village of Testing
 * This is a Main class
 * @author Megumi Kogo
 * @version 2023.4
 */
public class Main {

    public static void main(String[] args) {
        //En användare sätts i en loop och kalla de flesta funktionerna i Village.
        //Du behöver inte testa main funktionen
        Village village = new Village();
        Worker worker = new Worker();

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("+ + + WELCOME TO THE GAME OF VILLAGE + + +");
            System.out.println("Which construction project do you want to work with?");
            System.out.println("1. Farm  2. Wood-mill  3. Quarry  4. House  5. Castle  6. Random");
            int number = scanner.nextInt();

            switch (number) {
                case 1 -> {
                    System.out.println("* * FARM PROJECT * *");

                    Building farmProj = new Building("Farm", 5, 2, 0, 5, false);
//                    buildings.add();
                    village.addProject(farmProj, worker);

                    var wkr16 = new Worker("Eowejan", "Farmer", true, 0, true);
                    var wkr17 = new Worker("Hamwen", "Farmer", true, 0, true);
                    var wkr18 = new Worker("Cadazzle", "Farmer", true, 0, true);
                    var wkr19 = new Worker("Rottothy", "Farmer", true, 0, true);
                    var wkr20 = new Worker("Gnomarshall", "Farmer", true, 0, true);
                    village.getTentativeWorkers().add(wkr16);
                    village.getTentativeWorkers().add(wkr17);
                    village.getTentativeWorkers().add(wkr18);
                    village.getTentativeWorkers().add(wkr19);
                    village.getTentativeWorkers().add(wkr20);
                    village.addWorker(village.getTentativeWorkers(), village.iDelegate);

                    village.day();
                    village.getWorkers().clear();
                    break;
                }
                case 2 -> {
                    System.out.println("* * WOOD-MILL PROJECT * *");

                    Building woodMillProj = new Building("Wood-mill", 5, 1, 0, 5, false);
                    village.addProject(woodMillProj, worker);

                    var wkr4 = new Worker("Aderajar", "Sawyer", true, 0, true);
                    var wkr5 = new Worker("Niliria", "Sawyer", true, 0, true);
                    var wkr6 = new Worker("Ailald", "Sawyer", true, 0, true);
                    var wkr7 = new Worker("Glitterllo", "Sawyer", true, 0, true);
                    var wkr8 = new Worker("Warganta", "Sawyer", true, 0, true);
                    village.getTentativeWorkers().add(wkr4);
                    village.getTentativeWorkers().add(wkr5);
                    village.getTentativeWorkers().add(wkr6);
                    village.getTentativeWorkers().add(wkr7);
                    village.getTentativeWorkers().add(wkr8);
                    village.addWorker(village.getTentativeWorkers(), village.iDelegate);

                    village.day();
                    village.getWorkers().clear();
                    break;

                }
                case 3 -> {
                    System.out.println("* * QUARRY PROJECT * *");

                    Building quarryProj = new Building("Quarry", 3, 5, 0, 7, false);
                    village.addProject(quarryProj, worker);

                    var wkr9 = new Worker("Legilalin", "Quarryman", true, 0, true);
                    var wkr10 = new Worker("Etaygord", "Quarryman", true, 0, true);
                    var wkr11 = new Worker("Wicardossi", "Quarryman", true, 0, true);
                    var wkr12 = new Worker("Slimeperez", "Quarryman", true, 0, true);
                    var wkr13 = new Worker("Thobow", "Quarryman", true, 0, true);
                    var wkr14 = new Worker("Poola", "Quarryman", true, 0, true);
                    var wkr15 = new Worker("Knificent", "Quarryman", true, 0, true);
                    village.getTentativeWorkers().add(wkr9);
                    village.getTentativeWorkers().add(wkr10);
                    village.getTentativeWorkers().add(wkr11);
                    village.getTentativeWorkers().add(wkr12);
                    village.getTentativeWorkers().add(wkr13);
                    village.getTentativeWorkers().add(wkr14);
                    village.getTentativeWorkers().add(wkr15);
                    village.addWorker(village.getTentativeWorkers(), village.iDelegate);

                    village.day();
                    village.getWorkers().clear();
                    break;
                }
                case 4 -> {
                    System.out.println("* * HOUSE PROJECT * *");

                    Building houseProj = new Building("House", 5, 0, 0, 3, false);
                    village.addProject(houseProj, worker);

                    var wkr1 = new Worker("Jelinn", "Carpenter", true, 0, true);
                    var wkr2 = new Worker("Wicisa", "Carpenter", true, 0, true);
//                    var wkr3 = new Worker("Gwiredda","Carpenter", true,0,true);
                    village.getTentativeWorkers().add(wkr1);
                    village.getTentativeWorkers().add(wkr2);
//                    village.getTentativeWorkers().add(wkr3);
                    village.addWorker(village.getTentativeWorkers(), village.iDelegate);

                    village.day();
                    village.getWorkers().clear();
                    break;
                }
                case 5 -> {
                    System.out.println("* * CASTLE PROJECT * *");

                    Building castleProj = new Building("Castle", 50, 50, 0, 50, false);
                    village.addProject(castleProj, worker);

                    var wkr21 = new Worker("Sobwyn", "Castle-builder", true, 0, true);
                    var wkr22 = new Worker("Milaviel", "Castle-builder", true, 0, true);
                    var wkr23 = new Worker("Aledrijan", "Castle-builder", true, 0, true);
                    var wkr24 = new Worker("Yaucien", "Castle-builder", true, 0, true);
                    var wkr25 = new Worker("Kaiaveth", "Castle-builder", true, 0, true);

                    village.getTentativeWorkers().add(wkr21);
                    village.getTentativeWorkers().add(wkr22);
                    village.getTentativeWorkers().add(wkr23);
                    village.getTentativeWorkers().add(wkr24);
                    village.getTentativeWorkers().add(wkr25);

                    village.addWorker(village.getTentativeWorkers(), village.iDelegate);

                    village.day();
                    village.getWorkers().clear();
                    break;
                }
                case 6 -> {
                    System.out.println("* * RANDOM PROJECT * *");

                    village.addRandomWorker();

                    village.day();
                    village.getWorkers().clear();
                    break;
                }
            }
            System.out.println("\nDO YOU WANT TO CONTINUE? y/n\n");
            String answer = scanner.next();

            if(answer.equals("n")){

                System.out.println("GOOD BYE!");
                System.out.println("YOU HAVE " + village.getCompleteProjects().size() + " COMPLETE PROJECTS IN YOUR VILLAGE.");
                break;
            }
        }
    }
}