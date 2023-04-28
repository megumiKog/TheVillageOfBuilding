package org.example;

import java.util.*;

/**
 * Program: The Village of Testing
 * A class that handles events of the game
 * @author Megumi Kogo
 * @version 2023.4
 */
public class Village {

    public Random random;
    Worker worker;
    DatabaseConnection db;
    // ☆　CLASS VARIABLES & INSTANCE VARIABLES (ARRAYLISTS)
    private int food;
    private int wood;
    private int metal;
    //    private int foodPerDay;
//    private int woodPerDay;
//    private int metalPerDay;
    private int daysGone;
    private ArrayList<Worker> tentativeWorkers;
    private ArrayList<Worker> workers;
    private ArrayList<Building> buildings;
    private ArrayList<Building> currentProjects;
    // När en arbetare kör build funktionen så skall den gå in i listan av projekt
    // ta den första och lägga till 1 till antalet arbetsdagar spenderade på byggnaden
    // om antalet spenderade arbetsdagar överstiger antalet arbetsdagar det tar att bygga byggnaden så är byggnaden klar
    // flytta den från listan av pågående projekt till listan av klara byggnader beroende på byggnaden borde den klara byggnaden ha en påverkan på byn
    // som borde kunna göras med variabler
    private ArrayList<Building> completeProjects;
    private Map<String, Integer> vacantHouses;


    // ☆　CONSTRUCTOR
    public Village() {

        food = 10;
        wood = 10;
        metal = 10;
        daysGone = 0;

        tentativeWorkers = new ArrayList<>();
        workers = new ArrayList<>();
        buildings = new ArrayList<>();
        currentProjects = new ArrayList<>();
        completeProjects = new ArrayList<>();

        worker = new Worker();

        vacantHouses = new HashMap<>();

        completeProjects.add(new Building("House", 5, 0, 0, 3, true));
        completeProjects.add(new Building("House", 5, 0, 0, 3, true));
        completeProjects.add(new Building("House", 5, 0, 0, 3, true));
        // Add three vacant houses with the capacity of 10 ppl (10 beds)
        vacantHouses.put("House1", 10);
        vacantHouses.put("House2", 10);
        vacantHouses.put("House3", 10);

        db = new DatabaseConnection();
    }

    public Village(DatabaseConnection db) {
        this.db = db;
    }


    // ☆　GETTER/SETTER
    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public int getMetal() {
        return metal;
    }

    public void setMetal(int metal) {
        this.metal = metal;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<Worker> workers) { this.workers = workers; }

    public ArrayList<Worker> getTentativeWorkers() {
        return tentativeWorkers;
    }

    public ArrayList<Building> getCurrentProjects() {
        return currentProjects;
    }

    public void setCurrentProjects(ArrayList<Building> currentProjects) {
        this.currentProjects = currentProjects;
    }

    public ArrayList<Building> getCompleteProjects() {
        return completeProjects;
    }

    public ArrayList<Building> getBuildings() { return buildings; }

    public int getDaysGone() {
        return daysGone;
    }

    public void setDaysGone(int daysGone) {
        this.daysGone = daysGone;
    }

    public Map<String, Integer> getVacantHouses() {
        return vacantHouses;
    }

    public void setVacantHouses(Map<String, Integer> vacantHouses) {
        this.vacantHouses = vacantHouses;
    }

    public void setTentativeWorkers(ArrayList<Worker> tentativeWorkersInTest) {
        this.tentativeWorkers = tentativeWorkersInTest;
    }

    public void setBuildings(ArrayList<Building> buildingsInTest) {
        this.buildings = buildingsInTest;
    }


    @FunctionalInterface
    public interface IDelegate {
        void function();
    }
    IDelegate iDelegate;

    // ☆　METHODS
    public boolean addProject(Building building, Worker worker) {
        //Lägger till en ny byggnad till listan av byggnader att bygga. Den skall bara göra
        //detta ifall man har tillräckligt med resurser, och om man har det så skall dessa dras från ens totala
        //resurser.
        this.worker = worker;
        if((building != null && getWood() >= building.getWoodCost()) || (building != null && getMetal() >= building.getMetalCost())) {
            // self supplying system depending on the cost of each material
//            if(building.getWoodCost() > getWood() || building.getMetalCost() > getMetal()){
//                for(int i = 0; i<building.getWoodCost(); i++){
//                    addWood();
//                }
//                for(int i = 0; i<building.getMetalCost(); i++){
//                    addMetal();
//                }
//            }
            buildings.add(building);
            currentProjects.add(building);
            System.out.println(currentProjects.size());
            System.out.println("(--currentProjects hash code in addProject(): " + System.identityHashCode(currentProjects) + ")");
            setWood(getWood() - building.getWoodCost());
            setMetal(getMetal() - building.getMetalCost());

            return true;
        } else {
            System.out.println("THERE IS NOT ENOUGH RESOURCE IN THE VILLAGE");
            return false;
        }
    }

    public boolean addWorker(ArrayList<Worker> tentativeWorkers, IDelegate iDelegate) {
        iDelegate = () -> {
            Worker lastWorker = workers.get(workers.size() - 1);
            System.out.println(lastWorker.getWorkerName() + " is assigned as a " + lastWorker.getOccupation());
        };

        int workersAdded = 0;

          for (Map.Entry<String, Integer> entry : vacantHouses.entrySet()) {
            int spotsLeft = entry.getValue();

            for (int i = 0; i < spotsLeft && workersAdded < tentativeWorkers.size(); i++) {
                if(buildings!=null) {
                    workers.add(tentativeWorkers.get(workersAdded));
                    iDelegate.function();
                    workersAdded++;
                }
                if(spotsLeft == 10) { // if spotsLeft equals 10, we skip to the next house
                    continue;
                }
            }

            if(workersAdded == tentativeWorkers.size() && !workers.isEmpty()) {
                break;
            }
        }
        if(workers.isEmpty()) {
            System.out.println("IMPOSSIBLE TO ASSIGN ALL WORKERS DUE TO THE LACK OF A RESIDENT.");
            return false;
        } else {
            tentativeWorkers.clear();
            return true;

        }
    }

    private static final Set<String> ALL_BUILDING_NAMES = Set.of("House", "Wood-mill", "Quarry", "Farm", "Castle");

    public void day() {
        // Den skall kalla doWork() method på alla workers. Den skall mata alla arbetare också
        //Day funktionen skall loop igenom alla dina arbetare, mata dem och sedan kalla på
        //DoWork() på varje.
        if(!currentProjects.isEmpty() && !workers.isEmpty()) {
            Building project = currentProjects.get(0);

            addFood(5);
            addWood(1);
            addMetal(1);
            iDelegate = () -> System.out.println("\nFOOD(5)/WOOD(1)/METAL(1) IS DELIVERED TO THE WORKPLACE.");
            iDelegate.function();

            while (true) {

                daysGone++;
                System.out.println("\n. .  D A Y " + daysGone + " . .  ");
                System.out.println("(------workers' size: " + workers.size() + ")");

                // declare and initialize the flag variable outside the loop
                boolean anyWorkerHungry = false;

                for (Worker w : workers) {
                    System.out.println("\n" + w.getWorkerName() + " IS OFF TO WORK.");
                    w.feedWorker(this, w);
                    w.doWork(this);

                    // check if the worker is hungry and set the flag variable
                    if(w.isHungry()) {
                        anyWorkerHungry = true;
                    }
                }

                if(!anyWorkerHungry) {
                    currentProjects.get(0).setDaysWorked(currentProjects.get(0).getDaysWorked() + 1);
                } else {
                    System.out.println("\n\nTODAY'S  WORK WAS NOT ABLE TO BE COMPLETE DUE TO A HUNGRY WORKER/WORKERS. *Food distribution rescue will be delivered.");
                    //currentProjects.remove(project);
                    addFood(10);
                    //break;
                }

                if(currentProjects.get(0).getDaysWorked() >= currentProjects.get(0).getDaysToComplete()) {
                    System.out.println("\nA NEW " + currentProjects.get(0).getBuildingName() + " IS COMPLETE IN THE VILLAGE!");
                    completeProjects.add(project);
                    System.out.println(completeProjects.size());
                    currentProjects.remove(project);
                    System.out.println(currentProjects.size());
                    break;
                }
            }

            long houseCount = completeProjects.stream()
                    .filter(proj -> proj.getBuildingName().equals("House"))
                    .count();
            long woodMillCount = completeProjects.stream()
                    .filter(proj -> proj.getBuildingName().equals("Wood-mill"))
                    .count();
            long quarryCount = completeProjects.stream()
                    .filter(proj -> proj.getBuildingName().equals("Quarry"))
                    .count();
            long farmCount = completeProjects.stream()
                    .filter(proj -> proj.getBuildingName().equals("Farm"))
                    .count();
            long castleCount = completeProjects.stream()
                    .filter(proj -> proj.getBuildingName().equals("Castle"))
                    .count();

            iDelegate = () -> System.out.println("\nVILLAGE STATUS: " + getCompleteProjects().size() + " PROJECTS ARE COMPLETE SO FAR." +
                                                         "\n (HOUSES: " + houseCount + ")" +
                                                         "\n (WOOD-MILLS: " + woodMillCount + ")" +
                                                         "\n (QUARRIES: " + quarryCount + ")" +
                                                         "\n (FARMS: " + farmCount + ")" +
                                                         "\n (CASTLE: " + castleCount + ")" +
                                                         "\n  * WOODEN RESOURCE: " + getWood() +
                                                         "\n  * METALLIC RESOURCE: " + getMetal() +
                                                         "\n  * FOOD RESOURCE: " + getFood());
            iDelegate.function();

            if(completeProjects.stream()
                    .map(Building::getBuildingName)
                    .distinct()
                    .count() == ALL_BUILDING_NAMES.size()) {
                System.out.println("\nCONGRATULATIONS! YOU HAVE SUCCESSFULLY BUILT ALL BUILDINGS AND COMPLETED THE GAME.");
                System.exit(0); // terminate the game
            }

//            buryDead();
//            workers.clear();

            // update vacantHouses after the status of completeProject has changed
            vacantHouses.clear();

            int houseIndex = 1;

            for (Building proj : completeProjects) {
                if(proj.getBuildingName().equals("House")) {
                    String houseKey = "House" + houseIndex;
                    vacantHouses.put(houseKey, 10); // add a new entry for the completed house with a capacity of 10 workers
                    houseIndex++;
                }
            }
            System.out.println("\n---vacantHouses UPDATE " + vacantHouses.entrySet().iterator().next());
            for (String key : vacantHouses.keySet()) {
                int value = vacantHouses.get(key);
                System.out.println("---" + key + ": " + value);
            }

        } else {
            System.out.println("THERE IS NO CURRENT PROJECT OR WORKERS ASSIGNED TO THE PROJECT");

        }
    }

    public void addFood(int pieces) {
        // ger 5 mat till village (mer än 1 i alla fall....what is this )
        food += pieces;
    }

    public void addMetal(int pieces) {
        // lägger till 1 metall till villages resources
        metal += pieces;
    }

    public void addWood(int pieces) {
        // Metoden lägger till 1 wood till byns resurser
        // when the Wood-mill is finished, change a variable that is used in the AddWood()
        //
        wood += pieces;
    }

    public void build() {
        // build, vi kommer till vad den gör
        // skicka in via en lambda expression
        // Listan av projekt behövs!
        //addProject();
        System.out.println("(--Size of currentProjects list: " + currentProjects.size() + ")");
        System.out.println("(--currentProjects hash code in build(): " + System.identityHashCode(currentProjects) + ")");
        if(currentProjects.size() > 0) {
//            Building project = currentProjects.get(0);
//            currentProjects.get(0).setDaysWorked(currentProjects.get(0).getDaysWorked()+1);
            iDelegate = () -> System.out.println("THE WORKER IS BUILDING.");
            iDelegate.function();
            iDelegate = () -> System.out.println("THE WORKER IS CONTINUOUSLY BUILDING.");
            iDelegate.function();

            if(currentProjects.get(0).getBuildingName().equals("Wood-mill")) {
                addWood(2);
                iDelegate = () -> System.out.println("THE WORKER HAS MADE 2 TIMBERS.");
                iDelegate.function();
            }
            if(currentProjects.get(0).getBuildingName().equals("Quarry")) {
                addMetal(2);
                iDelegate = () -> System.out.println("THE WORKER HAS MADE 2 METALS.");
                iDelegate.function();
            }
            if(currentProjects.get(0).getBuildingName().equals("Farm")) {
                addFood(10);
                iDelegate = () -> System.out.println("THE WORKER HAS MADE 10 FOOD.");
                iDelegate.function();
            }
            iDelegate = () -> System.out.println("THE END OF THE WORKDAY. "); // + "NUMBER OF DAYS LEFT TO COMPLETE THE " + currentProjects.get(0).getBuildingName() + " PROJECT: " + (currentProjects.get(0).getDaysToComplete() - currentProjects.get(0).getDaysWorked()));
            iDelegate.function();

        }
    }

    public boolean buryDead() {
        // tar bort alla Workers som har alive=false ur listan workers
        // om listan av workers nu blir tom skriv ut "game over".
        for (Worker w : workers) {
            if(!w.isAlive()) {
                workers.remove(w);
            }
        }
        if(workers.isEmpty()) {
            System.out.println("GAME OVER!");
            System.exit(0);
        }
        return true;
    }

    public void saveProgress(DatabaseConnection db) {
        // Save variables to the database
        db.save("wood", wood);
        db.save("food", food);
        db.save("metal", metal);
        db.save("daysGone", daysGone);
        db.save("workers", workers.size());
        db.save("buildings", buildings.size());
    }

    public void loadProgress() {
        // Load variables from the database
        wood = (int) db.load("wood");
        food = (int) db.load("food");
        daysGone = (int) db.load("daysGone");
        workers = (ArrayList<Worker>) db.load("workers");
        buildings = (ArrayList<Building>) db.load("buildings");
        db.getFood();
        db.getWood();
        db.getDaysGone();
        db.getWorkers();
        db.getBuildings();
    }

    ArrayList<Worker> randomWorkers = new ArrayList<>();

    public void addRandomWorker(){

        Worker worker1 = new Worker("Eowejan", "Farmer", true, 0, true);
        Worker worker2 = new Worker("Aderajar", "Sawyer", true, 0, true);
        Worker worker3 = new Worker("Legilalin", "Quarryman", true, 0, true);
        Worker worker4 = new Worker("Jelinn", "Carpenter", true, 0, true);
        Worker worker5 = new Worker("Sobwyn", "Castle-builder", true, 0, true);

        randomWorkers.add(worker1);
        randomWorkers.add(worker2);
        randomWorkers.add(worker3);
        randomWorkers.add(worker4);
        randomWorkers.add(worker5);

        addFood(50);
        addWood(50);
        addMetal(50);

        //Random random = new Random(1234L);
        Random random = new Random();
        int index = random.nextInt(randomWorkers.size());
        System.out.println("(-------randomWorkers' size: " + randomWorkers.size() + ")");
        System.out.println("(-------randomly generated index: " + index + ")");
        String randomWorkerName = randomWorkers.get(index).getWorkerName();
        WorkerAction workerAction = getWorkerAction(randomWorkerName);

        // Removes the rest of the workers
        for (int i = randomWorkers.size() - 1; i >= 0; i--) {
            if (i != index) {
                randomWorkers.remove(i);
            }
        }
//        randomWorkers.subList(0, index).clear();
//        randomWorkers.subList(1, randomWorkers.size()).clear();

        System.out.println("(-------randomWorkers' size after removed the rest: " + randomWorkers.size() + ")");

        Building project = null;

        switch(randomWorkerName) {
            case "Eowejan":
                project = new Building("Farm", 5, 2, 0, 5, false);
                break;
            case "Aderajar":
                project = new Building("Wood-mill", 5, 1, 0, 5, false);
                break;
            case "Legilalin":
                project = new Building("Quarry", 3, 5, 0, 7, false);
                break;
            case "Jelinn":
                project = new Building("House", 5, 0, 0, 3, false);
                break;
            case "Sobwyn":
                project = new Building("Castle", 50, 50, 0, 50, false);
                break;
        }

        setWorkers(randomWorkers); // assigning the randomWorker ArrayList as workers ArrayList, because day() method makes use of workers

        if (project != null) {
            currentProjects.add(project);
            //addProject(project, worker);
        }

        workerAction.perform();
    }


    private WorkerAction getWorkerAction(String worker) {
        switch (worker) {
            case "Eowejan":
                return () -> System.out.println("EOWEJAN WILL WORK ON THE FARM PROJECT");
            case "Aderajar":
                return () -> System.out.println("ADERAJAR WILL WORK ON THE WOOD-MILL PROJECT");
            case "Legilalin":
                return () -> System.out.println("LEGILALIN WILL WORK ON THE QUARRY PROJECT");
            case "Jelinn":
                return () -> System.out.println("JELINN will WILL WORK ON THE HOUSE PROJECT");
            case "Sobwyn":
                return () -> System.out.println("SOBWYN will WILL WORK ON THE CASTLE PROJECT");
        }
        return null;
    }

    @FunctionalInterface
    interface WorkerAction{
        void perform();
    }

}
