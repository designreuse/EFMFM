package com.newtglobal.eFmFmFleetRouter.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.newtglobal.eFmFmFleetRouter.clustering.Geocode;

public class DistanceCache implements Serializable {
	private static final long serialVersionUID = 2686007927033076204L;
	private String cachepath;
	private Map<Integer, ArrayList<DistancePair>> distancePairMap;
	
	public DistanceCache(String cachepath) throws IOException, ClassNotFoundException {
		this.cachepath = cachepath;
		File f = new File(cachepath);
		if(f.exists()) {
			FileInputStream fin = new FileInputStream(cachepath);
			try {
				ObjectInputStream in = new ObjectInputStream(fin);
				distancePairMap = (Map<Integer, ArrayList<DistancePair>>) in.readObject();
				in.close();
			} catch (Exception e) {
				System.err.println(e);
				fin = new FileInputStream(cachepath + ".bak");
				ObjectInputStream in = new ObjectInputStream(fin);
				distancePairMap = (Map<Integer, ArrayList<DistancePair>>) in.readObject();
				File backup_cache = new File(cachepath+".bak");
				f.delete();
				backup_cache.renameTo(f);
				in.close();
			}
			fin.close();
		}
		else {
			f.createNewFile();
			distancePairMap = new HashMap<Integer, ArrayList<DistancePair>>();
		}
	}
	
	public void saveToDisk() throws IOException {
		File backup_cache = new File(cachepath+".bak");
		if (backup_cache.exists()) backup_cache.delete();
		File cache = new File(cachepath);
		cache.renameTo(backup_cache);
		FileOutputStream fout = new FileOutputStream(cachepath);
		ObjectOutputStream out = new ObjectOutputStream(fout);
		out.writeObject(distancePairMap);
		out.close();
	}
	
	public DistancePair search(Geocode G1, Geocode G2){
		DistancePair searchElement = new DistancePair(G1, G2);
		int key = searchElement.hashCode();
		DistancePair D = null;
		ArrayList<DistancePair> distancePairs = distancePairMap.get(key);

		if (!(distancePairs == null)) {
			for (DistancePair distancePair : distancePairs) {
				if(distancePair.equals(searchElement)){
					D = distancePair;
				}
			}
		}
		
		Date today = new Date();
		if (D != null) {
			//Delete old data
			if (Math.abs(D.getTime().toInstant().getEpochSecond() - 
					today.toInstant().getEpochSecond()) < 1296000) {
				return D;
			}
			else {
				distancePairMap.get(key).remove(D);
			}
		}
		return null;
	}
	
	public void addToCache(DistancePair D){
		int key = D.hashCode();
		if (distancePairMap.containsKey(key)) {
			distancePairMap.get(key).add(D);
		}
		else {
			distancePairMap.put(key, new ArrayList<DistancePair>());
			distancePairMap.get(key).add(D);
		}
	}
}