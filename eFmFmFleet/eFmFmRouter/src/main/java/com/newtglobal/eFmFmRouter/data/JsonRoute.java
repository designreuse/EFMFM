package com.newtglobal.eFmFmRouter.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.newtglobal.eFmFmRouter.routing.Depot;
import com.newtglobal.eFmFmRouter.routing.Employee;
import com.newtglobal.eFmFmRouter.routing.RoutingProblem;

import jsprit.core.problem.driver.Driver;
import jsprit.core.problem.solution.route.VehicleRoute;
import jsprit.core.problem.solution.route.activity.DeliverShipment;
import jsprit.core.problem.solution.route.activity.DeliveryActivity;
import jsprit.core.problem.solution.route.activity.PickupActivity;
import jsprit.core.problem.solution.route.activity.PickupShipment;
import jsprit.core.problem.solution.route.activity.TourActivity;
import jsprit.core.problem.vehicle.Vehicle;
import net.minidev.json.JSONObject;

public class JsonRoute {
  public static int number_of_routes = 0;
  public int route_id;
  public double total_time;
  public double total_length;
  public double effective_length;
  public double effective_time;
  public int number_of_employees;
  public boolean escort_required;
  public JsonVehicleType vehicle_type;
  public boolean is_assigned_dummy_vehicle;
  public JsonVehicle vehicle;  //assigned iff it is not a dummy vehicle
  public String depot_name; //assigned iff it is a dummy vehicle
  public Map<Integer,Job> jobs;
  public Date start_time;
  public Date end_time;
  public long start_time_instant;
  public long end_time_instant;

  public void setStartTime(Date start_time) {
    this.start_time = start_time;
    this.start_time_instant = start_time.toInstant().toEpochMilli();
  }

  public void setEndTime(Date end_time) {
    this.end_time = end_time;
    this.end_time_instant = end_time.toInstant().toEpochMilli();
  }


  public class Job {
    public JsonEmployee employee;
    public String type;  //Is either pickup or deliver
    public Date time;
    public long time_instant;

    public Job(JsonEmployee employee, String type) {
      this.employee = employee;
      this.type = type;
    }

    public void setTime(Date time) {
      this.time = time;
      this.time_instant = time.toInstant().toEpochMilli();
    }

    public Job(JSONObject job){
      this.employee = new JsonEmployee((JSONObject) job.get("employee"));
      this.type = (String) job.get("type");
      this.time_instant = (Long) job.get("time_instant");
      this.time = new Date((Long) this.time_instant);
    }
  }

  public Map<Integer, Job> jobstoMap(Map<String, JSONObject> jsonjobs) {
    Map<Integer, Job> jobs = new HashMap<Integer, Job>();
    Set<String> keySet = jsonjobs.keySet();
    for(String  i : keySet) {
      jobs.put(Integer.parseInt(i), new Job(jsonjobs.get(i)));
    }
    return jobs;
  }

  public JsonRoute() {
    //empty constructor just for intialising with default values
  }

  public void createRoute(VehicleRoute R, RoutingProblem problem, Map<String, JsonEmployee> employees, Map<String,
      JsonVehicleType> vehicle_types, Map<String, JsonVehicle> vehicles, 
      ArrayList<Depot> depots) {
    Settings settings = problem.getSettings();
    number_of_routes += 1;
    route_id = number_of_routes;
    total_time = 0; total_length = 0; effective_length = 0; effective_time = 0;
    number_of_employees = 0; escort_required = false; is_assigned_dummy_vehicle = true;
    jobs = new HashMap<Integer,Job>();
    long timedelta = 0;

    int index = 1;
    List<TourActivity> touractivities = R.getActivities();
    TourActivity prev = R.getStart();
    TourActivity start = R.getStart();
    TourActivity end = R.getEnd();
    Driver D = R.getDriver();
    Vehicle V = R.getVehicle();

    if(vehicles.get(V.getId()) != null) {
      this.is_assigned_dummy_vehicle = false;
      this.vehicle = vehicles.get(V.getId());
      this.vehicle_type = vehicles.get(V.getId()).vehicle_type;
      for(Depot depot : depots) {
        if (depot.containsVehicle(V) && settings.finite_vehicles) {
          depot.removeVehicle(V);
          break;
        }
      }
    }

    double depart_time = R.getDepartureTime();
    int jobcount = touractivities.size();

    for(TourActivity now : touractivities) {
      if(now.getName().equals("pickupShipment")) {
        number_of_employees += 1;

        PickupActivity P = (PickupActivity) now;
        jsprit.core.problem.job.Job L = P.getJob();

        if(settings.escorts && !settings.rigid_escort_constraint && 
            L.getName().equalsIgnoreCase("female") && prev.equals(start) && settings.trip_type.equalsIgnoreCase("login")) {
          this.escort_required = true;
            }
        else if(settings.escorts && settings.rigid_escort_constraint &&
            L.getName().equalsIgnoreCase("female")) {
          this.escort_required = true;
            }

        JsonEmployee E = employees.get(L.getId());
        if (settings.trip_type.equalsIgnoreCase("login")) {
          jobs.put(index, new Job(E, "pickup"));
          index += 1;
        }
        else {
          jobs.put(index, new Job(E, "pickup"));
          index += 1;
        }
      }

      else if(now.getName().equals("deliverShipment")) {
        DeliveryActivity P = (DeliveryActivity) now;
        jsprit.core.problem.job.Job L = P.getJob();
        JsonEmployee E = employees.get(L.getId());
        if (settings.trip_type.equalsIgnoreCase("login")) {
          jobs.put(index, new Job(E, "drop"));
          index += 1;
        }
        else {
          jobs.put(index, new Job(E, "drop"));
          index += 1;
        }
      }

      total_length += problem.getCostMatrix().getDistance(prev.getLocation().getId(), now.getLocation().getId());
      total_time += problem.getCostMatrix().getTransportTime(prev.getLocation(), now.getLocation(), depart_time, D, V);

      if(!settings.trip_type.equalsIgnoreCase("login")) {
        effective_length += problem.getCostMatrix().getDistance(prev.getLocation().getId(), now.getLocation().getId());
        effective_time += problem.getCostMatrix().getTransportTime(prev.getLocation(), now.getLocation(), depart_time, D, V);
      }

      else if(!prev.equals(start)) {
        effective_length += problem.getCostMatrix().getDistance(prev.getLocation().getId(), now.getLocation().getId());
        effective_time += problem.getCostMatrix().getTransportTime(prev.getLocation(), now.getLocation(), depart_time, D, V);
      }

      prev = now;
    }

    if(settings.escorts && !settings.rigid_escort_constraint && 
        prev.getName().equals("deliverShipment") && !settings.trip_type.equalsIgnoreCase("login")) {
      DeliveryActivity P = (DeliveryActivity) prev;
      jsprit.core.problem.job.Job L = P.getJob();
      if(L.getName().equalsIgnoreCase("female")) {
        this.escort_required = true;
      }
    }

    total_length += problem.getCostMatrix().getDistance(prev.getLocation().getId(), end.getLocation().getId());
    total_time += problem.getCostMatrix().getTransportTime(prev.getLocation(), end.getLocation(), depart_time, D, V);

    if(settings.trip_type.equalsIgnoreCase("login")) {
      effective_length += problem.getCostMatrix().getDistance(prev.getLocation().getId(), end.getLocation().getId());
      effective_time += problem.getCostMatrix().getTransportTime(prev.getLocation(), end.getLocation(), depart_time, D, V);
      effective_time += number_of_employees*settings.employee_service_time;
      total_time += number_of_employees*settings.employee_service_time;
    }

    if (this.is_assigned_dummy_vehicle) {
      vehicle_type = vehicle_types.get(V.getType().getTypeId());
      if (number_of_employees < vehicle_type.capacity) {
        int used_capacity = number_of_employees;
        if (this.escort_required) {
          used_capacity += 1;
        }
        assign_correct_vehicle(used_capacity, vehicle_types, problem, depots, V);
      }
      else {
        for(Depot depot : depots) {
          if (depot.containsVehicle(V)) {
            if (settings.finite_vehicles) {
              depot.removeVehicle(V);
            }
            this.depot_name = depot.getName();
            break;
          }
        }
      }
    }

    //assign correct times to each activity
    if (settings.trip_type.equalsIgnoreCase("login")) {
      timedelta = settings.shift_buffer_time;
      long time_instant = settings.shift_time_instant - timedelta*1000;
      Date end_time = new Date(time_instant);
      this.setEndTime(end_time);
      int job_id = jobs.size();

      prev = end;
      while (job_id > 0) {
        TourActivity now = touractivities.get(job_id -1);
        if (now instanceof PickupShipment) {
          timedelta += settings.employee_service_time;
        }
        timedelta += problem.getCostMatrix().getTransportTime(now.getLocation(), prev.getLocation(), depart_time, D, V);
        time_instant = settings.shift_time_instant - timedelta*1000;
        jobs.get(job_id).setTime(new Date(time_instant));
        job_id -= 1;
        prev = now;
      }
      timedelta += problem.getCostMatrix().getTransportTime(start.getLocation(), prev.getLocation(), depart_time, D, V);
      time_instant = settings.shift_time_instant - timedelta*1000;
      this.setStartTime(new Date(time_instant));
    }
    else {
      this.setStartTime(settings.shift_time);
      int job_id = 1;
      long time_instant;
      prev = start;
      for (TourActivity now : touractivities) {
        timedelta += problem.getCostMatrix().getTransportTime(prev.getLocation(), now.getLocation(), depart_time, D, V);
        time_instant = settings.shift_time_instant + timedelta*1000;
        jobs.get(job_id).setTime(new Date(time_instant));
        job_id += 1;
        prev = now;
      }

      timedelta += problem.getCostMatrix().getTransportTime(prev.getLocation(), end.getLocation(), depart_time, D, V);
      time_instant = settings.shift_time_instant + timedelta*1000;
      this.setEndTime(new Date(time_instant));
    }
  }


  public JsonRoute(JSONObject route) {
    long temp = (Long) route.get("route_id");
    route_id = (int) temp;
    total_time = (Double) route.get("total_time");
    total_length = (Double) route.get("total_length");
    effective_length = (Double) route.get("effective_length");
    effective_time = (Double) route.get("effective_time");
    temp = (Long) route.get("number_of_employees");
    number_of_employees = (int) temp;
    escort_required = (Boolean) route.get("escort_required");
    vehicle_type = new JsonVehicleType((JSONObject) route.get("vehicle_type"));
    is_assigned_dummy_vehicle = (Boolean) route.get("is_assigned_dummy_vehicle");
    start_time_instant = (Long) route.get("start_time_instant");
    end_time_instant = (Long) route.get("end_time_instant");
    start_time = new Date(start_time_instant);
    end_time = new Date(end_time_instant);
    try {
      vehicle = new JsonVehicle((JSONObject) route.get("vehicle"));
    } catch (Exception E) {
      vehicle = null;
    }
    try {
      depot_name = (String) route.get("depot_name");
    } catch (Exception E) {
      depot_name = null;
    }
    jobs = jobstoMap((Map<String, JSONObject>) route.get("jobs"));
  }

  public void assign_correct_vehicle(int used_capacity, Map<String, JsonVehicleType> vehicle_types,
      RoutingProblem problem, ArrayList<Depot> depots, Vehicle currently_assigned_vehicle) {
    Settings settings = problem.getSettings();
    Set<String> keySet = vehicle_types.keySet();
    JsonVehicleType assigned_vehicle = vehicle_types.get(currently_assigned_vehicle.getType().getTypeId());
    int assigned_capacity = assigned_vehicle.capacity;
    if (settings.finite_vehicles) {
      Vehicle vehicle_to_remove = currently_assigned_vehicle;
      for(Depot D : depots) {
        if (D.containsVehicle(currently_assigned_vehicle)) {
          for(com.newtglobal.eFmFmRouter.routing.Vehicle V : D.getVehicleList()) {
            int capacity = V.getVehicleInstance().getType().getCapacityDimensions().get(0);
            if (capacity < assigned_capacity && used_capacity <= capacity) {
              assigned_capacity = capacity;
              vehicle_to_remove = V.getVehicleInstance();
              assigned_vehicle = vehicle_types.get(vehicle_to_remove.getType().getTypeId());
            }
          }
          D.removeVehicle(vehicle_to_remove);
          this.vehicle_type = assigned_vehicle;
          this.depot_name = D.getName();
          return;
        }
      }
      //If code reaches here, unfortunately, the presently assigned vehicle has already been
      //allocated to another route. We try to find the depot to which this vehicle belonged to 
      //using another method and do the allocation.
      for(Depot D : depots) {
        if(currently_assigned_vehicle.getId().toLowerCase().contains(D.getName().toLowerCase())) {
          //In same depot as assigned vehicle
          assigned_vehicle = null;
          assigned_capacity = Integer.MAX_VALUE;
          for(com.newtglobal.eFmFmRouter.routing.Vehicle V : D.getVehicleList()) {
            int capacity = V.getVehicleInstance().getType().getCapacityDimensions().get(0);
            if (capacity < assigned_capacity && used_capacity <= capacity) {
              assigned_capacity = capacity;
              vehicle_to_remove = V.getVehicleInstance();
              assigned_vehicle = vehicle_types.get(vehicle_to_remove.getType().getTypeId());
            }
          }
          D.removeVehicle(vehicle_to_remove);
          this.vehicle_type = assigned_vehicle;
          this.depot_name = D.getName();
          return;
        }
      }
    }

    else {
      for (String key : keySet) {
        JsonVehicleType V = vehicle_types.get(key);
        if (V.capacity < assigned_capacity && used_capacity <= V.capacity) {
          assigned_capacity = V.capacity;
          assigned_vehicle = V;
        }
      }
    }

    this.vehicle_type = assigned_vehicle;
    for(Depot D : depots) {
      if (D.containsVehicle(currently_assigned_vehicle)) {
        this.depot_name = D.getName();
      }
    }

    return;
  }

  public ArrayList<Employee> getEmployees(Settings settings)
  {
    Set<Integer> keySet = jobs.keySet();
    ArrayList<Employee> employees = new ArrayList<Employee>();
    for (int i : keySet) {
      Job emp = jobs.get(i);
      if(emp.type.equals("pickup")) {
        employees.add(new Employee(emp.employee, 
              settings.max_travel_time, settings.employee_service_time));
      }
    }
    return employees;
  }

  public static ArrayList<JsonRoute> convertArray(ArrayList<JSONObject> jsonroutes) {
    ArrayList<JsonRoute> routes = new ArrayList<JsonRoute>();
    for (JSONObject route : jsonroutes) {
      routes.add(new JsonRoute(route));
    }
    return routes;
  }
}
