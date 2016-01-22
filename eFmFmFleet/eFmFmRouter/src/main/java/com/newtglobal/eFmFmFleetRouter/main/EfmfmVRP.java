package com.newtglobal.eFmFmFleetRouter.main;
import java.io.IOException;
import java.util.*;

import com.newtglobal.eFmFmFleetRouter.clustering.*;
import com.newtglobal.eFmFmFleetRouter.data.*;
import com.newtglobal.eFmFmFleetRouter.routing.*;

public class EfmfmVRP {
  private ClusterEmployee employeeClusters;
  private ArrayList<Employee> employees;
  private ArrayList<Depot> depots;
  private Settings settings;
  ArrayList<JsonEmployee> jsonEmployees;
  private ArrayList<JsonVehicleType> jsonVehicleTypes;
  private ArrayList<JsonVehicle> jsonVehicles;

  private ArrayList<JsonDepot> jsonDepots;

  private JsonSolution solution;

  public JsonSolution getSolution() {
    return solution;
  }

  public EfmfmVRP(Settings settings, ArrayList<JsonEmployee> employees,
      ArrayList<JsonDepot> depots, ArrayList<JsonVehicleType> vehicle_types, ArrayList<JsonVehicle> vehicles) {
    this.jsonEmployees = employees;
    this.jsonVehicleTypes = vehicle_types;
    this.jsonVehicles = vehicles;
    this.jsonDepots = depots;

    this.settings = settings;
    this.settings.finalise();

    this.employees = new ArrayList<Employee>();
    this.depots = new ArrayList<Depot>();

    this.jsonEmployees = employees;

    for (JsonEmployee E : employees) {
      long employee_max_time;
      if (settings.global_employee_time_constraint) {
        employee_max_time = settings.max_travel_time;
      }
      else if(E.time > 0 && E.time < settings.max_travel_time) {
        employee_max_time = E.time;
      }
      else {
        employee_max_time = settings.max_travel_time;
      }
      this.employees.add(new Employee(E, employee_max_time,
            settings.employee_service_time));
    }

    for(JsonDepot D : depots) {
      this.depots.add(new Depot(D));
    }

    for(JsonVehicle V : vehicles) {
      this.depots.add(new Depot(V));
    }

    if (settings.auto_clustering && employees.size() > 100) {
      this.employeeClusters = new ClusterEmployee();
      this.employeeClusters.createCluster(this.employees, 0.23);
      this.employeeClusters.kmediod(20000);
      this.employeeClusters.break_clusters(123); //Split clusters which are too large
      this.employeeClusters.sort(); //The larger clusters are solved first
      //this.employeeClusters.join_clusters(50); //Join clusters which are too small, but this ruins output
      //this.employeeClusters.sort();
    }
    else {
      this.employeeClusters = new ClusterEmployee(this.employees);
    }
  }


  public void solve() {
    JsonRoute.number_of_routes = 0;

    solution = new JsonSolution(jsonEmployees, jsonVehicleTypes, jsonVehicles, settings);
    DistanceCache cache = null;

    if (settings.use_api) {
      try {
        cache = new DistanceCache("input/cachefile");
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    int number_of_clusters = employeeClusters.getCluster().keySet().size();
    for(int key : employeeClusters.getCluster().keySet()) {
      //check before every iteration if vehicle collection is empty or not
      boolean empty = true;
      for(Depot depot : depots) {
        if(depot.getVehicleList().size() > 0) {
          empty = false;
        }
      }

      if(empty) {
        solution.addUnassignedEmployees(employeeClusters.getCluster().get(key));
        continue;
      }

      ArrayList<Employee> employees = employeeClusters.getCluster().get(key);
      RoutingProblem problem = new RoutingProblem();
      problem.setSettings(settings); //Always do this before anything else

      if (settings.use_api) {
        problem.init_cost("gme-skymapglobalindia", "ZkcB7U7OSeKArCiA0rBuEdQSIGM=", cache);  //Enterprise account
        //problem.init_cost("AIzaSyCbq62Wp2avFqEndhuRwkfuNDxYqvES314", cache); //Free API key
      }
      else {
        problem.init_cost();
      }

      if(settings.trip_type.equalsIgnoreCase("login")) {
        problem.setupLoginProblem(employees, depots);
      }
      else {
        problem.setupLogoutProblem(employees, depots);
      }
      if(settings.finite_vehicles) {
        problem.setFinite();
      }

      problem.useCostMatrix(); //Always use a costmatrix since we have a different metric

      problem.createProblem();
      if(!settings.escorts && settings.max_radial_distance < 0 && settings.max_route_length < 0) {
        System.out.println("No constraints are used");
        problem.defaultsolve();
      }
      else {
        System.out.println("Constraints are being used");
        problem.constraintsolve();
      }

      solution.add(problem, depots, this, key < number_of_clusters - 1); //Last cluster jobs are never rescheduled

      System.out.println("Cumulative number of API calls : " + GoogleCosts.getApiCalls() +
          " Cumulative number of cache hits : " + GoogleCosts.getCacheHits());

      //problem.plotGraph();
      problem.printSolution();
    }

    if(settings.use_api) {
      try {
        cache.saveToDisk();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  public void reschedule(ArrayList<Employee> employees, JsonVehicleType vehicle, String depot_name) {
    Depot depot = null;
    for (Depot D : depots) {
      if(D.getName().equals(depot_name)) {
        depot = D;
        break;
      }
    }
    depot.addVehicle(new VehiclePrototype(vehicle), 1);
    employeeClusters.addToCluster(employees);
  }
}
