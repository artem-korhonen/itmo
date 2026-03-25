package main;

import java.util.List;
import java.util.ArrayList;

import data.City;
import data.Human;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Hashtable;

public class CollectionManager {
   private Hashtable<Integer, City> cities;
   private LocalDateTime initializeDate = LocalDateTime.now();
   private DatabaseManager databaseManager;

   public CollectionManager(DatabaseManager databaseManager) {
      this.cities = new Hashtable<>();
      this.databaseManager = databaseManager;

      try {
         List<City> cities = databaseManager.getCities();

         for (City city : cities) {
            IdGenerator.addException(city.getId());
            this.cities.put(city.getId(), city);
         }
      } catch (SQLException e) {
         System.out.println("Error: Couldn't write cities from the database to the collection");
      }
   }

   public synchronized Hashtable<Integer, City> getCollection() {
      return this.cities;
   }

   public Class<?> getCollectionClass() {
      return this.cities.getClass();
   }

   public synchronized int getCollectionSize() {
      return this.cities.size();
   }

   public LocalDateTime getCollectionInitializeDate() {
      return this.initializeDate;
   }

   public synchronized List<String> getGovernors() {
      List<String> governors = new ArrayList<>();

      for (City city : this.cities.values()) {
         Human governor = city.getGovernor();
         governors.add(governor.toString());
      }

      return governors;
   }

   public synchronized boolean addToCollection(City city) {
      try {
         this.databaseManager.loadCity(city);
         this.cities.put(city.getId(), city);
         return true;
      } catch (SQLException e) {
         return false;
      }
   }

   public synchronized boolean updateCollection(Integer id, City city) {
      try {
         this.databaseManager.updateCity(id, city);
         this.cities.replace(id, city);
         return true;
      } catch (SQLException e) {
         return false;
      }
   }

   public synchronized boolean removeElementsCollection(List<Integer> citiesIds) {
      try {
         this.databaseManager.removeCities(citiesIds);
         for (Integer cityId : citiesIds) {
            this.cities.remove(cityId);
         }
         return true;
      } catch (SQLException e) {
         return false;
      }
   }

   public synchronized boolean removeElementCollection(Integer id) {
      try {
         this.databaseManager.removeCity(id);
         this.cities.remove(id);
         return true;
      } catch (SQLException e) {
         return false;
      }
   }

   public synchronized boolean clearCollection() {
      try {
         this.databaseManager.clearCities();
         this.cities.clear();
         return true;
      } catch (SQLException e) {
         return false;
      }
   }

   public synchronized boolean containsTest(Integer id) {
      return this.cities.keySet().contains(id);
   }
}
