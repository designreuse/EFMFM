package com.newtglobal.eFmFmFleet.eFmFmFleet;

import java.util.ArrayList;
import java.util.List;

public class PolygonalGeofence extends Geofence
{
  private Geocode centre; //Should lie inside the polygon
  private List<Geocode> vertices;

  public PolygonalGeofence(Geocode centre, double radius)
  {
    super();
    this.centre = centre;
    vertices = new ArrayList<Geocode>();
  }

  public void addVertex(Geocode vertex)
  {
    this.vertices.add(vertex);
  }

  private double subtendedArea(Geocode G) throws Exception
  {
    double area = 0;
    int n = vertices.size();
    if (n < 3) throw new Exception("Polygonal geofence should" +
        " contain at least three points");
    for(int i = 0; i < n; i++) {
      Geocode V1 = vertices.get(i % n);
      Geocode V2 = vertices.get((i+1) % n);
      double a = Geocode.distance(G, V1)/1000;
      double b = Geocode.distance(G, V2)/1000;
      double c = Geocode.distance(V1, V2)/1000;
      double s = (a+b+c)/2;
      area += Math.sqrt(s*(s-a)*(s-b)*(s-c));
    }
    return area;
  }

  @Override
  public boolean isInGeofence()
  {
    boolean result = false;
    try {
      System.out.println(subtendedArea(vehicleLocation));
      System.out.println(subtendedArea(centre));
      double areaRatio = subtendedArea(vehicleLocation)/subtendedArea(centre);
      result = (areaRatio > 0.98 && areaRatio < 1.02);

    }
    catch(Exception e) {
    }
    return result;
  }
}
