package org.example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Program: The Village of Testing
 * A class that handles database connection.
 * @author Megumi Kogo
 * @version 2023.4
 */
public class DatabaseConnection {
    private Map<String, Object> database;

    public DatabaseConnection() {
        database = new HashMap<>();
    }

    public void save(String key, Object value) {
        database.put(key, value);
    }

    public Object load(String key) {
        return database.get(key);
    }

    public int getWood() {
        return (int) load("wood");
    }

    public int getFood() {
        return (int) load("food");
    }

    public ArrayList<Worker> getWorkers() {
        return (ArrayList<Worker>) load("workers");
    }

    public ArrayList<Building> getBuildings() {
        return (ArrayList<Building>) load("buildings");
    }

    public int getDaysGone() {
        return (int) load("days");
    }


}

