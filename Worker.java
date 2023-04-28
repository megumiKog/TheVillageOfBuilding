package org.example;

/**
 * Program: The Village of Testing
 * A class that stores workers' info
 * @author Megumi Kogo
 * @version 2023.4
 */
public class Worker {
    Village village;

    // en delegate/ functional interface som innehåller vad som händer när de arbetar
    private String workerName;
    private String occupation;
    private boolean hungry;
    // sätts till false när de får mat
    private int daysHungry;
    // som räknare, man borde ha en separat method för hungry räknare???
    // om de blir hungriga så ökas daysHungry med 1
    // om de får mat sätt detta till 0 och hungry till false
    // om daysHungry når 40 eller högre sätt alive till false
    private boolean alive = true;
    // sätts till false när daysHungry når >=40
    // gör så att om alive är false så går det inte mata arbetaren eller få denna att arbeta

    // ☆　CONSTRUCTOR
    public Worker() {
    }

    public Worker(String workerName, String occupation, boolean hungry, int daysHungry, boolean alive) {
        village = new Village();
        this.workerName = workerName;
        this.occupation = occupation;
        this.hungry = hungry;
        this.daysHungry = daysHungry;
        this.alive = alive;

    }

    // ☆　GETTER/SETTER
    public String getWorkerName() {
        return workerName;
    }

    public Worker setWorkerName(String workerName) {
        this.workerName = workerName;
        return null;
    }

    public String getOccupation() {
        return occupation;
    }

    public boolean isHungry() {
        return hungry;
    }

    public void setHungry(boolean hungry) {
        this.hungry = hungry;
    }

    public int getDaysHungry() {
        return daysHungry;
    }

    public void setDaysHungry(int daysHungry) {
        this.daysHungry = daysHungry;
    }

    public boolean isAlive() {
        return alive;
    }

    private void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setVillage(Village village) {
        this.village = village;
    }


    // ☆　USER DEFINED FUNCTIONAL INTERFACE,is it used to show working status?
    @FunctionalInterface
    public interface IWork {
        void status();
    }

    IWork iWork;

    // ☆　METHODS
    public boolean doWork(Village village) {
        // denna kallar på en delegate/functional interface som finns skapad som ett objekt i Worker
        // denna skall kalla en av fyra funktioner i village
        // lägg till en check som gör så att en arbetare bara arbetar i fall de inte är hungriga

        if(isHungry()) {
            System.out.println("THE WORKER IS HUNGRY AND IMPOSSIBLE TO WORK.");
            return false;
        } else {

            iWork = () -> System.out.println("THE WORKER IS READY TO WORK NOW. \n");
            iWork.status();

            // shouldn't it be build()

            village.build();
            return true;
        }
    }

// feed() = addFood(-1)
//    public void feed(){
//        // redundant to feedWorkers?
//        // för varje worker så subtraheras 1 mat från byns resurser
//        // om village inte har tillräckligt med mat sätt en boolean hungry till true i Worker
//        //village.setFood(village.getFood()- 1);
//
//        village.addFood(-1);
//
//    }

    public boolean feedWorker(Village village, Worker worker) {
        if(village.getFood() > 0 && worker.isAlive()==true) {
            village.addFood(-1);
            System.out.println("(--food resource " + village.getFood() + ")");
            iWork = () -> System.out.println("THE WORKER IS SERVED WITH THE FOOD.");
            iWork.status();

            worker.setHungry(false);
            System.out.println("( ----hungry was set to " + hungry+")");
            worker.setDaysHungry(0);
            return true;
        } else {
            worker.setHungry(true);
            worker.setDaysHungry(worker.getDaysHungry() + 1);
            System.out.println("(-----hungry days" + worker.getDaysHungry());
            System.out.println("THE WORKER COULD NOT GET THE FOOD. THE WORKER IS HUNGRY. ");
            if(worker.getDaysHungry() >= 40) {
                worker.setAlive(false);
                iWork = () -> System.out.println("THE WORKER HAS BEEN HUNGRY FOR 40 DAYS AND IS DEAD NOW");
                iWork.status();
                village.buryDead();
            }
            return false;
        }
    }
}
