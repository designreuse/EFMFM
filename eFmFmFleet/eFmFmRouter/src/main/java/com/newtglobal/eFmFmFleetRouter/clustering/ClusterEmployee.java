package com.newtglobal.eFmFmFleetRouter.clustering;

import java.util.*;

import com.newtglobal.eFmFmFleetRouter.routing.Employee;

public class ClusterEmployee
{
  private Map<Integer, ArrayList<Employee>> content;
  private ArrayList<Employee> centres;
  private double clusSize;

  public ClusterEmployee(ArrayList<Employee> employees) {
    content = new HashMap<Integer, ArrayList<Employee>>();
    content.put(0, employees);
  }

  public ClusterEmployee() {

  }

  public void createCluster(ArrayList<Employee> employees, double clusSize)
  {
    this.clusSize = clusSize;
    double lat_min = employees.get(0).getPickup().getLat();
    double long_min = employees.get(0).getPickup().getLong(), long_max = employees.get(0).getPickup().getLong();
    for (Employee E : employees){
      if (E.getPickup().getLat() < lat_min) lat_min = E.getPickup().getLat();
      if (E.getPickup().getLong() < long_min) long_min = E.getPickup().getLong();
      if (E.getPickup().getLong() > long_max) long_max = E.getPickup().getLong();
    }
    int vert_size = (int)Math.ceil((long_max-long_min)/clusSize);
    content = new HashMap<Integer, ArrayList<Employee>>();

    for (Employee E : employees) {
      double x = E.getPickup().getLat();
      double y = E.getPickup().getLong();
      int p = (int)Math.floor((x-lat_min)/clusSize);
      int q = (int)Math.floor((y-long_min)/clusSize);
      if(!content.containsKey(p+q*vert_size)) {
        content.put(p+q*vert_size, new ArrayList<Employee>());
      }
      content.get(p+q*vert_size).add(E);
    }

    Map<Integer, ArrayList<Employee>> reindexed_content = new HashMap<Integer, ArrayList<Employee>>();
    int i = 0;
    for(int j : content.keySet()) {
      reindexed_content.put(i, content.get(j));
      i++;
    }

    content = reindexed_content;

    centres = randomcentres();
  }

  public ArrayList<Employee> randomcentres()
  {
    ArrayList<Employee> new_centres = new ArrayList<Employee>();
    Random generator = new Random();
    //randomly select a centre from each cluster
    for(int i : content.keySet()){
      ArrayList<Employee> G = content.get(i);
      int j = generator.nextInt(G.size());
      new_centres.add(G.get(j));
    }
    return new_centres;
  }

  public ArrayList<Employee> shiftcentres()
  {
    ArrayList<Employee> new_centres = new ArrayList<Employee>();
    Random generator = new Random();
    for(Employee E : centres){
      new_centres.add(E);
    }

    int i = generator.nextInt(new_centres.size());
    int j = generator.nextInt(content.get(i).size());
    new_centres.set(i, content.get(i).get(j));
    return new_centres;
  }

  public void repartition() {
    Map<Integer, ArrayList<Employee>> new_content = new HashMap<Integer, ArrayList<Employee>>();

    for(int i : content.keySet()) {
      new_content.put(i, new ArrayList<Employee>());
    }

    Map<Integer, double [][]> costs = compute_cost(centres);

    for(int i = 0; i < costs.get(0).length; i++){
      for(int j = 0; j < costs.get(0)[i].length; j++) {

        double min = Double.MAX_VALUE;
        int min_index = 0;

        for(int k : costs.keySet()) {
          if(costs.get(k)[i][j] < min) {
            min_index = k;
            min = costs.get(k)[i][j];
          }
        }

        new_content.get(min_index).add(content.get(i).get(j));
      }
    }

    content = new_content;
  }

  public Map<Integer, double [][]> compute_cost(ArrayList<Employee> m_centres){ 
    Map<Integer, double [][]> costs = new HashMap<Integer, double [][]>();
    for(int i = 0; i < m_centres.size(); i++) {
      Employee G = m_centres.get(i);
      costs.put(i, new double [m_centres.size()][]);
      for(int j : content.keySet()) {
        ArrayList<Employee> Glist = content.get(j);
        costs.get(i)[j] = new double [Glist.size()];
        for(int k = 0; k < Glist.size(); k++) {
          costs.get(i)[j][k] = Geocode.distance(G.getPickup(), Glist.get(k).getPickup());
        }
      }
    }
    return costs;
  }

  public void kmediod(int iterations) {
    for(int l = 0; l < iterations; l++) {
      ArrayList<Employee> new_centres = shiftcentres();
      Map<Integer, double [][]> new_costs = compute_cost(new_centres);
      Map<Integer, double [][]> old_costs = compute_cost(centres);
      double new_cost= 0 , old_cost = 0;
      for(int i : new_costs.keySet()) {
        for(double [] j : new_costs.get(i)) {
          for(double k : j) {
            new_cost += k;
          }
        }
      }
      for(int i : old_costs.keySet()) {
        for(double [] j : old_costs.get(i)) {
          for(double k : j) {
            old_cost += k;
          }
        }
      }

      if(new_cost < old_cost) {
        centres = new_centres;
        repartition();
      }
    }
  }

  public Map<Integer, ArrayList<Employee>> getCluster()
  {
    return content;
  }

  public void split(int max_size) {
    Set<Integer> keys = content.keySet();
    int this_key = -1;
    for(int i : keys) {

      if(content.get(i).size() > max_size) {
        this_key = i;
        break;
      }
    }
    if(this_key != -1) {
      ArrayList<Employee> employees = content.get(this_key);
      int splits = (int) Math.floor(employees.size()/max_size);
      double lat_min = employees.get(0).getPickup().getLat(), lat_max = employees.get(0).getPickup().getLat(), 
             long_min = employees.get(0).getPickup().getLong(), long_max = employees.get(0).getPickup().getLong();
      for(Employee G : employees) {
        if (G.getPickup().getLat() < lat_min) lat_min = G.getPickup().getLat();
        if (G.getPickup().getLat() > lat_max) lat_max = G.getPickup().getLat();
        if (G.getPickup().getLong() < long_min) long_min = G.getPickup().getLong();
        if (G.getPickup().getLong() > long_max) long_max = G.getPickup().getLong();

      }
      double clus_size = Math.min(Math.abs((lat_max-lat_min)/splits), 
          Math.abs((long_max-long_min)/splits));
      ClusterEmployee N = new ClusterEmployee();
      N.createCluster(employees, clus_size);
      N.kmediod(5000);

      content.put(this_key, N.getCluster().get(0));
      for(int j = 1; j < N.getCluster().size(); j++) {
        content.put(content.size() + j, N.getCluster().get(j));
      }
      this.split(max_size);
    }
  }

  public void break_clusters(int max_size) {
    Set<Integer> keySet = content.keySet();
    int num = keySet.size();
    for (int i = 0; i < num; i++) {
      ArrayList<Employee> employees = content.get(i);
      int size = employees.size();
      if (size > max_size) {
        double factor = Math.sqrt(size/max_size);
        ClusterEmployee split_clusters = new ClusterEmployee();
        split_clusters.createCluster(employees, clusSize*0.8/factor);
        split_clusters.kmediod(20000);
        content.remove(employees);
        Set<Integer> keySet2 = split_clusters.content.keySet();
        content.put(i, split_clusters.content.get(0));
        int num_of_elements = content.keySet().size();
        for(int j = 1; j < keySet2.size(); j++) {
          content.put(num_of_elements + j - 1, split_clusters.content.get(j));
        }
      }
    }
  }

  public void join_clusters(int max_size) {
    int size = content.keySet().size();
    for (int i = size - 1; i > 0; i--) {
      int j = i-1;
      if (content.get(i).size() + content.get(j).size() <= max_size || content.get(i).size() <= max_size/4) {
        for(Employee E : content.get(i)) {
          content.get(j).add(E);
        }
        content.remove(i);
      }
    }
  }

  public void sort()
  {
    //Using insertion sort for now, since Map's are not expected to be that big.
    //Reimplement before deployment if necessary, though insertion sort is a better choice
    //for smaller sized input.
    Map<Integer, ArrayList<Employee>> new_content = new HashMap<Integer, ArrayList<Employee>>();
    for(int i : content.keySet()) {
      ArrayList<Employee> key = content.get(i);
      int j = 0;
      while(j < i && key.size() < new_content.get(j).size()) j++;
      ArrayList<Employee> temp = new_content.get(j);
      new_content.put(j, content.get(i));
      for(int k = j+1; k <= i; k++) {
        ArrayList<Employee> temp2 = new_content.get(k);
        new_content.put(k, temp);
        temp = temp2;
      }
    }
    content = new_content;
  }

  public void addToCluster(ArrayList<Employee> employees) {
    //Always add to last cluster, which is in principle the smallest cluster
    Set<Integer> keySet = content.keySet();
    int key = keySet.size() - 1;
    content.get(key).addAll(employees);
  }

  public String toString()
  {
    String str = new String();
    for(int i : content.keySet()){
      str += "\n -----------------\n CLUSTER" + (i+1) + "\n ----------------- \n";
      for(Employee g : content.get(i)){
        str += g + "\n";
      }
    }
    return str;
  }
}
