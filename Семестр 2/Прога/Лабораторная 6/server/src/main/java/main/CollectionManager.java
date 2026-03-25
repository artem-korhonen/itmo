package main;

import java.util.List;
import java.util.ArrayList;

import data.City;
import data.Human;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Hashtable;

public class CollectionManager {
   private Hashtable<Integer, City> cities;
   private LocalDateTime initializeDate = LocalDateTime.now();
   private CsvConverter csvConverter;

   public CollectionManager(CsvConverter csvConverter) {
      this.cities = new Hashtable<>();
      this.csvConverter = csvConverter;

      try {
         List<City> cities = csvConverter.csvFileToCities(this);

         for (City city : cities) {
            this.cities.put(city.getId(), city);
         }
      } catch (IOException e) {
         System.out.println(e.getMessage());
      }

   }

   public Hashtable<Integer, City> getCollection() {
      return this.cities;
   }

   public Class<?> getCollectionClass() {
      return this.cities.getClass();
   }

   public int getCollectionSize() {
      return this.cities.size();
   }

   public LocalDateTime getCollectionInitializeDate() {
      return this.initializeDate;
   }

   public List<String> getGovernors() {
      List<String> governors = new ArrayList<>();

      for (City city : this.cities.values()) {
         Human governor = city.getGovernor();
         governors.add(governor.toString());
      }

      return governors;
   }

   public void addToCollection(City city) {
      this.cities.put(city.getId(), city);
   }

   public void updateCollection(Integer id, City city) {
      this.cities.replace(id, city);
   }

   public void removeElementsCollection(List<Integer> citiesIds) {
      for (Integer cityId : citiesIds) {
         this.cities.remove(cityId);
      }
   }

   public void removeElementCollection(Integer id) {
      this.cities.remove(id);
   }

   public void clearCollection() {
      this.cities.clear();
   }

   public void saveCollection() {
      try {
         this.csvConverter.CitiesToCsvFile(this.cities);
      } catch (IOException e) {
         System.out.println(e.getMessage());;
      }
   }

   public boolean containsTest(Integer id) {
      return this.cities.keySet().contains(id);
   }
}
