package proiect_gui;

import java.util.HashMap;

public class Payment{
	public HashMap<Integer, HashMap<Integer, Integer>> mp = new HashMap<>();
	public double total = 0.0;
	public Payment() {}
	
	public void add(Integer f, Integer m, int am) {
		if(mp.containsKey(f)) {
			if(mp.get(f).containsKey(m)) {				
				mp.get(f).put(m, mp.get(f).get(m) + am);
			}
			else {
				mp.get(f).put(m, am);
			}
		}
		else {
			HashMap<Integer, Integer> nhmp = new HashMap<>();
			nhmp.put(m, am);
			mp.put(f, nhmp);
		}
	}
	
}