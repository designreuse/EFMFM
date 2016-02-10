package com.newtglobal.eFmFmRouter.routing;

import jsprit.core.problem.job.Shipment;
import jsprit.core.problem.solution.SolutionCostCalculator;
import jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import jsprit.core.problem.solution.route.activity.TimeWindow;
import jsprit.core.reporting.SolutionPrinter;
import jsprit.core.util.Coordinate;
import jsprit.core.util.Solutions;
import jsprit.core.problem.VehicleRoutingProblem;
import jsprit.core.util.VehicleRoutingTransportCostsMatrix;
import jsprit.core.problem.misc.JobInsertionContext;
import jsprit.core.problem.solution.route.VehicleRoute;
import jsprit.core.problem.solution.route.activity.ActivityVisitor;
import jsprit.core.problem.solution.route.activity.DeliverShipment;
import jsprit.core.problem.solution.route.activity.End;
import jsprit.core.problem.solution.route.activity.PickupShipment;
import jsprit.core.problem.solution.route.activity.Start;
import jsprit.core.problem.solution.route.activity.TourActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.newtglobal.eFmFmRouter.clustering.Geocode;
import com.newtglobal.eFmFmRouter.data.DistanceCache;
import com.newtglobal.eFmFmRouter.data.Settings;

import jsprit.core.algorithm.VehicleRoutingAlgorithm;
import jsprit.core.algorithm.box.SchrimpfFactory;
import jsprit.core.problem.Capacity;
import jsprit.core.problem.Location;
import jsprit.core.problem.VehicleRoutingProblem.FleetSize;

import jsprit.analysis.toolbox.GraphStreamViewer;
//import jsprit.analysis.toolbox.GraphStreamViewer.Label;
import jsprit.analysis.toolbox.Plotter;
//import jsprit.core.problem.io.VrpXMLWriter;

import jsprit.core.algorithm.VehicleRoutingAlgorithmBuilder;
import jsprit.core.algorithm.state.StateId;
import jsprit.core.algorithm.state.StateManager;
import jsprit.core.algorithm.state.StateUpdater;
import jsprit.core.problem.constraint.ConstraintManager;
import jsprit.core.problem.constraint.HardActivityConstraint;

public class RoutingProblem {

  private boolean isFinite = false;
  private boolean useCostMatrix = false;

  private VehicleRoutingProblem.Builder vrpBuilder;
  private VehicleRoutingProblem problem;
  private VehicleRoutingAlgorithm algorithm;
  private VehicleRoutingProblemSolution solution;
  private Collection<VehicleRoutingProblemSolution> allsolutions;
  private VehicleRoutingTransportCostsMatrix costMatrix;

  public boolean isFinite() {
    return isFinite;
  }

  public VehicleRoutingTransportCostsMatrix getCostMatrix() {
    return costMatrix;
  }

  public boolean isUseCostMatrix() {
    return useCostMatrix;
  }

  public VehicleRoutingProblem getProblem() {
    return problem;
  }

  public VehicleRoutingProblemSolution getSolution() {
    return solution;
  }

  public Settings getSettings() {
    return new Settings(settings);
  }

  public void setSettings(Settings settings) {
    this.settings = settings;
  }

  private CostInterface costintr;
  private Settings settings;

  // employees are tacitly assumed to have been already clustered
  public RoutingProblem() {
  }

  public void init_cost(String client_id, String cryptoKey, DistanceCache cache) {
    this.costintr = new GoogleCosts(client_id, cryptoKey, cache, settings);
  }

  public void init_cost(String apiKey, DistanceCache cache) {
    this.costintr = new GoogleCosts(apiKey, cache, settings);
  }

  public void init_cost() {
    this.costintr = new CrowCosts();
  }

  public RoutingProblem setupLoginProblem(ArrayList<Employee> employees, ArrayList<Depot> depots) {
    vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
    for (Employee E : employees) {
      Shipment S = Shipment.Builder.newInstance(E.getEmployeeId()).addSizeDimension(0, 1)
        .setPickupLocation(Location.newInstance(E.getPickup().getLat(), E.getPickup().getLong()))
        .setDeliveryLocation(Location.newInstance(E.getDrop().getLat(), E.getDrop().getLong()))
        .setPickupServiceTime(E.getPickupServiceTime()).setDeliveryServiceTime(0)
        .setPickupTimeWindow(TimeWindow.newInstance(settings.max_idle_time + settings.max_travel_time
              - E.getMaxTripDuration(),
              settings.max_idle_time + settings.max_travel_time))
        .setDeliveryTimeWindow(TimeWindow.newInstance(settings.max_idle_time + settings.max_travel_time,
              settings.max_idle_time + settings.max_travel_time))
        .setName(E.getSex()).build();
      vrpBuilder.addJob(S);
    }

    for (Depot D : depots) {
      for (Vehicle V : D.getVehicleList()) {
        vrpBuilder.addVehicle(V.getVehicleInstance());
      }
    }
    return this;
  }

  public RoutingProblem setupLogoutProblem(ArrayList<Employee> employees, ArrayList<Depot> depots) {
    vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
    for (Employee E : employees) {
      Shipment S = Shipment.Builder.newInstance(E.getEmployeeId()).addSizeDimension(0, 1)
        .setPickupLocation(Location.newInstance(E.getDrop().getLat(), E.getDrop().getLong()))
        .setDeliveryLocation(Location.newInstance(E.getPickup().getLat(), E.getPickup().getLong()))
        .setPickupServiceTime(0).setDeliveryServiceTime(0)
        .setPickupTimeWindow(TimeWindow.newInstance(settings.max_idle_time, settings.max_idle_time))
        .setDeliveryTimeWindow(TimeWindow.newInstance(settings.max_idle_time,
              settings.max_idle_time + E.getMaxTripDuration()))
        .setName(E.getSex()).build();
      vrpBuilder.addJob(S);
    }

    for (Depot D : depots) {
      for (Vehicle V : D.getVehicleList()) {
        vrpBuilder.addVehicle(V.getVehicleInstance());
      }
    }
    return this;
  }

  public RoutingProblem createProblem() {
    if (isFinite)
      vrpBuilder.setFleetSize(FleetSize.FINITE);
    if (useCostMatrix)
      vrpBuilder.setRoutingCost(costMatrix);
    problem = vrpBuilder.build();
    return this;
  }

  public RoutingProblem computeCostMatrix() {
    VehicleRoutingTransportCostsMatrix.Builder costMatrixBuilder = VehicleRoutingTransportCostsMatrix.Builder
      .newInstance(false);
    Map<String, Coordinate> Locations = vrpBuilder.getLocationMap();
    System.out.println("TOTAL NUMBER OF LOCATIONS : " + Locations.keySet().size());
    for (String str1 : Locations.keySet()) {
      for (String str2 : Locations.keySet()) {
        if (!str1.equals(str2)) {
          Cost cost = costintr.getCost(new Geocode(Locations.get(str1).getX(), Locations.get(str1).getY()),
              new Geocode(Locations.get(str2).getX(), Locations.get(str2).getY()));
          costMatrixBuilder.addTransportDistance(str1, str2, cost.getDistance());
          costMatrixBuilder.addTransportTime(str1, str2, cost.getTime());
        }
      }
    }
    costMatrix = costMatrixBuilder.build();
    return this;
  }

  public RoutingProblem setFinite() {
    isFinite = true;
    return this;
  }

  public RoutingProblem useCostMatrix() {
    computeCostMatrix();
    useCostMatrix = true;
    return this;
  }

  public RoutingProblem defaultsolve() {
    algorithm = new SchrimpfFactory().createAlgorithm(problem);
    allsolutions = algorithm.searchSolutions();
    solution = Solutions.bestOf(allsolutions);
    return this;
  }

  public RoutingProblem maxDistanceSolve(double max_distance) {

    VehicleRoutingAlgorithmBuilder vraBuilder = new VehicleRoutingAlgorithmBuilder(problem,
        "input/algorithmConfig_solomon.xml");
    StateManager stateManager = new StateManager(problem);
    StateId distanceStateId = stateManager.createStateId("distance");

    stateManager.addStateUpdater(new DistanceUpdater(distanceStateId, stateManager, costMatrix));

    ConstraintManager constraintManager = new ConstraintManager(problem, stateManager);
    constraintManager.addConstraint(
        new RouteDistanceConstraint(max_distance, distanceStateId, stateManager, costMatrix),
        ConstraintManager.Priority.CRITICAL);

    vraBuilder.addCoreConstraints();
    vraBuilder.addDefaultCostCalculators();
    vraBuilder.setStateAndConstraintManager(stateManager, constraintManager);

    VehicleRoutingAlgorithm algorithm = vraBuilder.build();
    allsolutions = algorithm.searchSolutions();
    solution = Solutions.bestOf(allsolutions);
    return this;
  }

  public RoutingProblem maxRadialDistanceSolve(double max_distance) {

    VehicleRoutingAlgorithmBuilder vraBuilder = new VehicleRoutingAlgorithmBuilder(problem,
        "input/algorithmConfig_solomon.xml");
    StateManager stateManager = new StateManager(problem);
    StateId distanceStateId = stateManager.createStateId("distance");

    stateManager.addStateUpdater(new DistanceUpdater(distanceStateId, stateManager, costMatrix));

    ConstraintManager constraintManager = new ConstraintManager(problem, stateManager);
    constraintManager.addConstraint(new RadialDistanceConstraint(max_distance, costMatrix),
        ConstraintManager.Priority.CRITICAL);
    constraintManager.addConstraint(new LaxEscortConstraintLogout(), ConstraintManager.Priority.CRITICAL);

    vraBuilder.addCoreConstraints();
    vraBuilder.addDefaultCostCalculators();
    vraBuilder.setStateAndConstraintManager(stateManager, constraintManager);

    VehicleRoutingAlgorithm algorithm = vraBuilder.build();
    allsolutions = algorithm.searchSolutions();
    solution = Solutions.bestOf(allsolutions);
    return this;
  }

  public RoutingProblem constraintsolve() {

    VehicleRoutingAlgorithmBuilder vraBuilder = new VehicleRoutingAlgorithmBuilder(problem,
        "input/algorithmConfig_fix_schrimpf.xml");
    StateManager stateManager = new StateManager(problem);
    StateId distanceStateId = stateManager.createStateId("distance");

    stateManager.addStateUpdater(new DistanceUpdater(distanceStateId, stateManager, costMatrix));

    ConstraintManager constraintManager = new ConstraintManager(problem, stateManager);

    //setting manual objective function, now in testing phase
    if(settings.trip_type.equalsIgnoreCase("login")) {
      vraBuilder.setObjectiveFunction(new SolutionCostCalculator() {
        @Override
        public double getCosts(VehicleRoutingProblemSolution solution) {
          double cost = 0.0;

          for (VehicleRoute route : solution.getRoutes()) {
            int count = 0;
            TourActivity prev = route.getStart();
            TourActivity start = prev;
            TourActivity end = route.getEnd();
            double routecost = 0.0;
            for (TourActivity T : route.getActivities()) {
              if (T.getName().equals("pickupShipment")) {
                routecost += count*costMatrix.getDistance(prev.getLocation().getId(),
                    T.getLocation().getId());
                prev = T;
                cost += costMatrix.getDistance(prev.getLocation().getId(),
                    T.getLocation().getId());
                count += 1;
              }
            }
            cost += costMatrix.getDistance(prev.getLocation().getId(),
                end.getLocation().getId());
            routecost += count*costMatrix.getDistance(prev.getLocation().getId(), end.getLocation().getId());
            cost += 25*(routecost/count);
            cost += 10000*(route.getVehicle().getType().getCapacityDimensions().get(0) - count);
          }
          return cost;
        }
      });
    }

    else {
      vraBuilder.setObjectiveFunction(new SolutionCostCalculator() {
        @Override
        public double getCosts(VehicleRoutingProblemSolution solution) {
          double cost = 0.0;

          for (VehicleRoute route : solution.getRoutes()) {
            int totalcount = 0;
            for (TourActivity T : route.getActivities()) {
              if (T.getName().equals("deliverShipment")) totalcount += 1;
            }

            int count = totalcount;
            TourActivity prev = route.getStart();
            TourActivity start = prev;
            TourActivity end = route.getEnd();
            double routecost = 0.0;

            for (TourActivity T : route.getActivities()) {
              if (T.getName().equals("deliverShipment")) {
                if (count > 0) {
                  routecost += count*costMatrix.getDistance(prev.getLocation().getId(),
                      T.getLocation().getId());
                  count -= 1;
                }

                cost += costMatrix.getDistance(prev.getLocation().getId(),
                    T.getLocation().getId());

                prev = T;
              }
            }
            cost += costMatrix.getDistance(prev.getLocation().getId(),
                end.getLocation().getId());
            cost += 25*(routecost/totalcount);
            cost += 10000*(route.getVehicle().getType().getCapacityDimensions().get(0) - totalcount);
          }
          return cost;
        }
      });
    }

    if (settings.max_radial_distance > 0) {
      constraintManager.addConstraint(new RadialDistanceConstraint(settings.max_radial_distance, costMatrix),
          ConstraintManager.Priority.CRITICAL);
    }

    if (settings.max_route_length > 0) {
      constraintManager.addConstraint(
          new RouteDistanceConstraint(settings.max_route_length, distanceStateId, stateManager, costMatrix),
          ConstraintManager.Priority.CRITICAL);
    }

    if (settings.max_effective_route_length > 0) {
      if (settings.trip_type.equalsIgnoreCase("login")) {
        constraintManager
          .addConstraint(
              new EffectiveRouteDistanceConstraintLogin(settings.max_effective_route_length,
                distanceStateId, stateManager, costMatrix),
              ConstraintManager.Priority.CRITICAL);
      } else {
        constraintManager
          .addConstraint(
              new EffectiveRouteDistanceConstraintLogout(settings.max_effective_route_length,
                distanceStateId, stateManager, costMatrix),
              ConstraintManager.Priority.CRITICAL);
      }
    }

    if (settings.escorts && !settings.rigid_escort_constraint && settings.trip_type.equalsIgnoreCase("login")) {
      constraintManager.addConstraint(new LaxEscortConstraintLogin(), ConstraintManager.Priority.CRITICAL);
    }

    else if (settings.escorts && !settings.rigid_escort_constraint && !settings.trip_type.equalsIgnoreCase("login")) {
      constraintManager.addConstraint(new LaxEscortConstraintLogout(), ConstraintManager.Priority.CRITICAL);
    }

    else if (settings.escorts && settings.rigid_escort_constraint) {
      constraintManager.addConstraint(new RigidEscortConstraint(), ConstraintManager.Priority.CRITICAL);
    }

    if (settings.trip_type.equalsIgnoreCase("login")) {
      constraintManager.addConstraint(new LoginDeviationConstraint(0.5, 10000, costMatrix),
          ConstraintManager.Priority.CRITICAL);
      constraintManager.addConstraint(new LoginDirectionConstraint(costMatrix),
          ConstraintManager.Priority.CRITICAL);
    }
    else {
      constraintManager.addConstraint(new LogoutDeviationConstraint(0.5, 10000, costMatrix),
          ConstraintManager.Priority.CRITICAL);
    }
    vraBuilder.addCoreConstraints();
    vraBuilder.addDefaultCostCalculators();
    vraBuilder.setStateAndConstraintManager(stateManager, constraintManager);

    VehicleRoutingAlgorithm algorithm = vraBuilder.build();
    allsolutions = algorithm.searchSolutions();
    solution = Solutions.bestOf(allsolutions);
    return this;
  }

  public void removeAssignedVehicles(ArrayList<Depot> depots) {
    Collection<VehicleRoute> routes = this.solution.getRoutes();
    for (Depot D : depots)
      for (VehicleRoute VR : routes) {
        D.removeVehicle(VR.getVehicle());
      }
    // Check if vehicle collection is empty or not
    boolean empty = true;
    for (Depot D : depots) {
      if (D.getVehicleList().size() > 0) {
        empty = false;
      }
    }

    if (empty) {
      System.out.println("There are unassigned jobs due to dearth of vehicles.");
      System.exit(1);
    }
  }

  public void plotSolution(String name) {
    Plotter plotter = new Plotter(problem, solution);
    plotter.plot("output/" + name + "_solution.png", name);
  }

  public void printSolution() {
    SolutionPrinter.print(problem, solution, SolutionPrinter.Print.VERBOSE);
  }

  public void plotGraph() {
    new GraphStreamViewer(problem, solution).setRenderDelay(10).display();
  }

  static class DistanceUpdater implements StateUpdater, ActivityVisitor {

    private final StateManager stateManager;
    private final VehicleRoutingTransportCostsMatrix costMatrix;
    private final StateId distanceStateId;
    private VehicleRoute vehicleRoute;
    private double distance = 0.;
    private TourActivity prevAct;

    public DistanceUpdater(StateId distanceStateId, StateManager stateManager,
        VehicleRoutingTransportCostsMatrix transportCosts) {
      this.costMatrix = transportCosts;
      this.stateManager = stateManager;
      this.distanceStateId = distanceStateId;
    }

    @Override
    public void begin(VehicleRoute vehicleRoute) {
      distance = 0.;
      prevAct = vehicleRoute.getStart();
      this.vehicleRoute = vehicleRoute;
    }

    @Override
    public void visit(TourActivity tourActivity) {
      distance += getDistance(prevAct, tourActivity);
      prevAct = tourActivity;
    }

    @Override
    public void finish() {
      distance += getDistance(prevAct, vehicleRoute.getEnd());
      stateManager.putRouteState(vehicleRoute, distanceStateId, distance);
    }

    double getDistance(TourActivity from, TourActivity to) {
      return costMatrix.getDistance(from.getLocation().getId(), to.getLocation().getId());
    }
  }

  static class RouteDistanceConstraint implements HardActivityConstraint {

    private final StateManager stateManager;
    private final VehicleRoutingTransportCostsMatrix costsMatrix;
    private final double maxDistance;
    private final StateId distanceStateId;

    RouteDistanceConstraint(double maxDistance, StateId distanceStateId, StateManager stateManager,
        VehicleRoutingTransportCostsMatrix transportCosts) {
      this.costsMatrix = transportCosts;
      this.maxDistance = maxDistance;
      this.stateManager = stateManager;
      this.distanceStateId = distanceStateId;
    }

    @Override
    public ConstraintsStatus fulfilled(JobInsertionContext context, TourActivity prevAct, TourActivity newAct,
        TourActivity nextAct, double v) {
      double additionalDistance = getDistance(prevAct, newAct) + getDistance(newAct, nextAct)
        - getDistance(prevAct, nextAct);
      Double routeDistance = stateManager.getRouteState(context.getRoute(), distanceStateId, Double.class);
      if (routeDistance == null)
        routeDistance = 0.;
      double newRouteDistance = routeDistance + additionalDistance;
      if (newRouteDistance > maxDistance) {
        return ConstraintsStatus.NOT_FULFILLED;
      } else
        return ConstraintsStatus.FULFILLED;
    }

    double getDistance(TourActivity from, TourActivity to) {
      return costsMatrix.getDistance(from.getLocation().getId(), to.getLocation().getId());
    }
  }

  static class EffectiveRouteDistanceConstraintLogin implements HardActivityConstraint {
    private final StateManager stateManager;
    private final VehicleRoutingTransportCostsMatrix costsMatrix;
    private final double maxDistance;
    private final StateId distanceStateId;

    EffectiveRouteDistanceConstraintLogin(double maxDistance, StateId distanceStateId, StateManager stateManager,
        VehicleRoutingTransportCostsMatrix transportCosts) {
      this.costsMatrix = transportCosts;
      this.maxDistance = maxDistance;
      this.stateManager = stateManager;
      this.distanceStateId = distanceStateId;
    }

    @Override
    public ConstraintsStatus fulfilled(JobInsertionContext context, TourActivity prevAct, TourActivity newAct,
        TourActivity nextAct, double v) {
      double additionalDistance = getDistance(prevAct, newAct) + getDistance(newAct, nextAct)
        - getDistance(prevAct, nextAct);
      Double routeDistance = stateManager.getRouteState(context.getRoute(), distanceStateId, Double.class);
      context.getNewVehicle();
      List<TourActivity> jobs = context.getRoute().getActivities();

      TourActivity firstPickup = null;
      for (TourActivity T : jobs) {
        if (T instanceof PickupShipment) {
          firstPickup = T;
          break;
        }
      }

      double vehicleToFirstDistance = 0;

      if (firstPickup != null) {
        vehicleToFirstDistance = getDistance(context.getRoute().getStart(), firstPickup);
      }

      if (routeDistance == null)
        routeDistance = 0.;
      double newRouteDistance = routeDistance + additionalDistance - vehicleToFirstDistance;
      if (newRouteDistance > maxDistance) {
        return ConstraintsStatus.NOT_FULFILLED;
      } else
        return ConstraintsStatus.FULFILLED;
    }

    double getDistance(TourActivity from, TourActivity to) {
      return costsMatrix.getDistance(from.getLocation().getId(), to.getLocation().getId());
    }
  }

  static class EffectiveRouteDistanceConstraintLogout implements HardActivityConstraint {

    private final StateManager stateManager;
    private final VehicleRoutingTransportCostsMatrix costsMatrix;
    private final double maxDistance;
    private final StateId distanceStateId;

    EffectiveRouteDistanceConstraintLogout(double maxDistance, StateId distanceStateId, StateManager stateManager,
        VehicleRoutingTransportCostsMatrix transportCosts) {
      this.costsMatrix = transportCosts;
      this.maxDistance = maxDistance;
      this.stateManager = stateManager;
      this.distanceStateId = distanceStateId;
    }

    @Override
    public ConstraintsStatus fulfilled(JobInsertionContext context, TourActivity prevAct, TourActivity newAct,
        TourActivity nextAct, double v) {
      double additionalDistance = getDistance(prevAct, newAct.getLocation())
        + getDistance(newAct, nextAct.getLocation()) - getDistance(prevAct, nextAct.getLocation());
      Double routeDistance = stateManager.getRouteState(context.getRoute(), distanceStateId, Double.class);
      List<TourActivity> jobs = context.getRoute().getActivities();
      context.getNewVehicle();
      TourActivity lastDrop = null;

      for (TourActivity T : jobs) {
        if (T instanceof DeliverShipment) {
          lastDrop = T;
        }
      }

      double lastToDepotDistance = 0;
      if (lastDrop != null) {
        // This is assuming that the vehicle returns back to the depot
        lastToDepotDistance = getDistance(lastDrop, context.getRoute().getVehicle().getEndLocation());
      }
      if (routeDistance == null)
        routeDistance = 0.;
      double newRouteDistance = routeDistance + additionalDistance - lastToDepotDistance;
      if (newRouteDistance > maxDistance) {
        return ConstraintsStatus.NOT_FULFILLED;
      } else
        return ConstraintsStatus.FULFILLED;
    }

    double getDistance(TourActivity from, Location to) {
      return costsMatrix.getDistance(from.getLocation().getId(), to.getId());
    }
  }

  static class RadialDistanceConstraint implements HardActivityConstraint {
    private final VehicleRoutingTransportCostsMatrix costsMatrix;
    private final double maxRadialDistance;

    RadialDistanceConstraint(double maxRadialDistance, VehicleRoutingTransportCostsMatrix transportCosts) {
      this.costsMatrix = transportCosts;
      this.maxRadialDistance = maxRadialDistance;
    }

    @Override
    public ConstraintsStatus fulfilled(JobInsertionContext context, TourActivity prevAct, TourActivity newAct,
        TourActivity nextAct, double v) {
      Location start = context.getNewVehicle().getStartLocation();
      double radialDistance = Math.min(getDistance(start, newAct.getLocation()),
          getDistance(newAct.getLocation(), start));

      if (radialDistance > maxRadialDistance) {
        return ConstraintsStatus.NOT_FULFILLED;
      }
      return ConstraintsStatus.FULFILLED;
    }

    double getDistance(Location from, Location to) {
      return costsMatrix.getDistance(from.getId(), to.getId());
    }
  }

  static class LoginDeviationConstraint implements HardActivityConstraint {
    private final VehicleRoutingTransportCostsMatrix costsMatrix;
    private final double deviationFactor;
    private final double maxDevationDistance;

    LoginDeviationConstraint(double deviationFactor, double maxDevationDistance,
        VehicleRoutingTransportCostsMatrix transportCosts) {
      this.costsMatrix = transportCosts;
      this.deviationFactor = deviationFactor;
      this.maxDevationDistance = maxDevationDistance;
    }

    @Override
    public ConstraintsStatus fulfilled(JobInsertionContext context, TourActivity prevAct, TourActivity newAct,
        TourActivity nextAct, double v) {
      Location start = context.getNewVehicle().getStartLocation();
      VehicleRoute route = context.getRoute();
      if (prevAct instanceof Start && newAct instanceof PickupShipment) {
        double radialDistance = getDistance(start, newAct.getLocation());
        double distance = 0;
        TourActivity prev = newAct;
        for (TourActivity T : route.getActivities()) {
          distance += getDistance(prev.getLocation(), T.getLocation());
          prev = T;
        }

        distance += getDistance(prev.getLocation(), start);
        double additionalDistance = distance - radialDistance;
        double additionalDistanceThreshold = Math.min(deviationFactor*radialDistance, maxDevationDistance);
        if (additionalDistance > additionalDistanceThreshold) {
          return ConstraintsStatus.NOT_FULFILLED;
        }
        else {
          return ConstraintsStatus.FULFILLED;
        }
      }
      else if (route.getActivities().size() > 0 && newAct instanceof PickupShipment) {
        double distance = 0;
        TourActivity first = route.getActivities().get(0);
        TourActivity prev = first;
        double radialDistance = getDistance(start, first.getLocation());
        if (first instanceof PickupShipment) {
          for (TourActivity T : route.getActivities()) {
            if (T.equals(first)) continue;
            distance += getDistance(prev.getLocation(), T.getLocation());
            prev = T;
          }
          distance += getDistance(prev.getLocation(), start);
          distance = distance + getDistance(prevAct.getLocation(), newAct.getLocation()) +
            getDistance(newAct.getLocation(), nextAct.getLocation()) - getDistance(prevAct.getLocation(),
                nextAct.getLocation());

          double additionalDistance = distance - radialDistance;
          double additionalDistanceThreshold = Math.min(deviationFactor*radialDistance, maxDevationDistance);
          if (additionalDistance > additionalDistanceThreshold) {
            return ConstraintsStatus.NOT_FULFILLED;
          }
        }
      }
      return ConstraintsStatus.FULFILLED;
    }

    double getDistance(Location from, Location to) {
      return costsMatrix.getDistance(from.getId(), to.getId());
    }
  }


  static class LogoutDeviationConstraint implements HardActivityConstraint {
    private final VehicleRoutingTransportCostsMatrix costsMatrix;
    private final double deviationFactor;
    private final double maxDevationDistance;

    LogoutDeviationConstraint(double deviationFactor, double maxDevationDistance, VehicleRoutingTransportCostsMatrix transportCosts) {
      this.costsMatrix = transportCosts;
      this.deviationFactor = deviationFactor;
      this.maxDevationDistance = maxDevationDistance;
    }

    @Override
    public ConstraintsStatus fulfilled(JobInsertionContext context, TourActivity prevAct, TourActivity newAct,
        TourActivity nextAct, double v) {
      Location start = context.getNewVehicle().getStartLocation();
      VehicleRoute route = context.getRoute();
      if (nextAct instanceof End && newAct instanceof DeliverShipment) {
        double radialDistance = getDistance(start, newAct.getLocation());
        double distance = 0;
        TourActivity prev = route.getStart();
        for (TourActivity T : route.getActivities()) {
          distance += getDistance(prev.getLocation(), T.getLocation());
          prev = T;
        }

        distance += getDistance(prevAct.getLocation(), newAct.getLocation());
        double additionalDistance = distance - radialDistance;
        double additionalDistanceThreshold = Math.min(deviationFactor*radialDistance, maxDevationDistance);
        if (additionalDistance > additionalDistanceThreshold) {
          return ConstraintsStatus.NOT_FULFILLED;
        }
        else {
          return ConstraintsStatus.FULFILLED;
        }
      }
      else if (newAct instanceof DeliverShipment){
        double distance = 0;
        TourActivity prev = route.getStart();
        for (TourActivity T : route.getActivities()) {
          distance += getDistance(prev.getLocation(), T.getLocation());
          prev = T;
        }
        distance = distance + getDistance(prevAct.getLocation(), newAct.getLocation()) +
          getDistance(newAct.getLocation(), nextAct.getLocation()) - getDistance(prevAct.getLocation(),
              nextAct.getLocation());
        if (prev instanceof DeliverShipment) {
          double radialDistance = getDistance(start, prev.getLocation());
          double additionalDistance = distance - radialDistance;
          double additionalDistanceThreshold = Math.min(deviationFactor*radialDistance, maxDevationDistance);
          if (additionalDistance > additionalDistanceThreshold) {
            return ConstraintsStatus.NOT_FULFILLED;
          }
        }
      }
      return ConstraintsStatus.FULFILLED;
    }

    double getDistance(Location from, Location to) {
      return costsMatrix.getDistance(from.getId(), to.getId());
    }
  }

  static class RigidEscortConstraint implements HardActivityConstraint {
    @Override
    public ConstraintsStatus fulfilled(JobInsertionContext context, TourActivity prevAct, TourActivity newAct,
        TourActivity nextAct, double v) {
      Capacity capacity = context.getNewVehicle().getType().getCapacityDimensions();
      if (newAct instanceof PickupShipment) {
        PickupShipment P = (PickupShipment) newAct;
        String sex = P.getJob().getName();
        boolean has_female = sex.equalsIgnoreCase("female");
        int number_of_employees = 0;
        List<TourActivity> R = context.getRoute().getActivities();
        for (TourActivity T : R) {
          if (T instanceof PickupShipment) {
            number_of_employees += 1;
            if (((PickupShipment) T).getJob().getName().equalsIgnoreCase("female")) {
              has_female = true;
            }
          }
        }
        if (has_female && number_of_employees > capacity.get(0) - 2) {
          return ConstraintsStatus.NOT_FULFILLED;
        }
      }
      return ConstraintsStatus.FULFILLED;
    }
  }

  static class LaxEscortConstraintLogin implements HardActivityConstraint {
    @Override
    public ConstraintsStatus fulfilled(JobInsertionContext context, TourActivity prevAct, TourActivity newAct,
        TourActivity nextAct, double v) {
      List<TourActivity> R = context.getRoute().getActivities();
      if (R.size() == 0){
        return ConstraintsStatus.FULFILLED;
      }
      Capacity capacity = context.getNewVehicle().getType().getCapacityDimensions();
      int number_of_employees = 0;
      boolean first_female = false;
      PickupShipment first = (PickupShipment) R.get(0);
      if (newAct instanceof PickupShipment && prevAct instanceof Start) {
        PickupShipment P = (PickupShipment) newAct;
        String sex = P.getJob().getName();
        first_female = sex.equalsIgnoreCase("female");
        if(!first_female) return ConstraintsStatus.FULFILLED;
      }

      first_female = (first_female || first.getJob().getName().equalsIgnoreCase("female"));

      if (!first_female) {
        return ConstraintsStatus.FULFILLED;
      }

      for (TourActivity T : R) {
        if (T instanceof PickupShipment) {
          number_of_employees += 1;
        }
      }
      if (number_of_employees > capacity.get(0) - 2) {
        return ConstraintsStatus.NOT_FULFILLED;
      }
      return ConstraintsStatus.FULFILLED;
    }
  }

  static class LaxEscortConstraintLogout implements HardActivityConstraint {
    @Override
    public ConstraintsStatus fulfilled(JobInsertionContext context, TourActivity prevAct, TourActivity newAct,
        TourActivity nextAct, double v) {
      List<TourActivity> R = context.getRoute().getActivities();

      if (R.size() == 0) {
        return ConstraintsStatus.FULFILLED;
      }

      boolean new_female = false;

      if (newAct instanceof DeliverShipment && nextAct instanceof End) {
        DeliverShipment D = (DeliverShipment) newAct;
        String sex = D.getJob().getName();
        new_female = sex.equalsIgnoreCase("female");
        if(!new_female) return ConstraintsStatus.FULFILLED;
      }

      Capacity capacity = context.getNewVehicle().getType().getCapacityDimensions();
      int number_of_employees = 0;
      DeliverShipment last = null;

      for (TourActivity T : R) {
        if (T instanceof DeliverShipment) {
          last = (DeliverShipment) T;
          number_of_employees += 1;
        }
      }

      if (number_of_employees > capacity.get(0) - 2
          && ((last.getJob().getName().equalsIgnoreCase("female") || new_female))) {
        return ConstraintsStatus.NOT_FULFILLED;
          }
      return ConstraintsStatus.FULFILLED;
    }
  }

  static class LoginDirectionConstraint implements HardActivityConstraint {
    private final VehicleRoutingTransportCostsMatrix costsMatrix;
    LoginDirectionConstraint(VehicleRoutingTransportCostsMatrix transportCosts) {
      this.costsMatrix = transportCosts;
    }

    @Override
    public ConstraintsStatus fulfilled(JobInsertionContext context, TourActivity prevAct, TourActivity newAct,
        TourActivity nextAct, double v) {
      VehicleRoute route = context.getRoute();
      if (newAct instanceof PickupShipment && nextAct instanceof PickupShipment) {
        double distAB = getDistance(prevAct.getLocation(), newAct.getLocation());
        double distBC = getDistance(newAct.getLocation(), nextAct.getLocation());
        double distAC = getDistance(prevAct.getLocation(), nextAct.getLocation());
        if (distAB + distAC <= distBC + 500 && distAB >= 250) {
          return ConstraintsStatus.NOT_FULFILLED;
        }
        else if (distAC + distBC <= distAB + 500 && distBC >= 250) {
          return ConstraintsStatus.NOT_FULFILLED;
        }
      }
      TourActivity first = null, second = null, third = null;
      for (TourActivity T : route.getActivities()) {
        if (first == null) {
          T = first;
        }
        else if (second == null) {
          second = first;
          first = T;
        }
        else if (third == null) {
          third = second;
          second = first;
          first = T;
        }
        else if (first != null && second != null && third != null) {
          if (first instanceof PickupShipment && second instanceof PickupShipment && third instanceof PickupShipment) {
            double distAB = getDistance(first.getLocation(), second.getLocation());
            double distBC = getDistance(second.getLocation(), third.getLocation());
            double distAC = getDistance(first.getLocation(), third.getLocation());
            if (distAB + distAC <= distBC + 500 && distAB >= 250) {
              return ConstraintsStatus.NOT_FULFILLED;
            }
            else if (distAC + distBC <= distAB + 500 && distBC >= 250) {
              return ConstraintsStatus.NOT_FULFILLED;
            }
          }
          third = second;
          second = first;
          first = T;
        }
      }
      return ConstraintsStatus.FULFILLED;
    }

    double getDistance(Location from, Location to) {
      return costsMatrix.getDistance(from.getId(), to.getId());
    }
  }
}
