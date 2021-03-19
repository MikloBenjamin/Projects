package proiect_gui;
import java.util.*;
import java.io.Serializable;

public class Farmacie implements Serializable {
	private static final long serialVersionUID = 1L;
	List<Medicament> stoc = new ArrayList<>();
	String nume, loc;
	List<tip> ltip = new ArrayList<>();
	
	public Farmacie() {}
	public Farmacie(String nume, String loc, List<tip> ltip) {
		this.nume = nume;
		this.loc = loc;
		this.ltip = ltip;
	}
	public static Farmacie copy(Farmacie f) {
		String name = new String(f.getNume());
		String loc = new String(f.getLoc());
		List<tip> tips = new ArrayList<>();
		for(tip a : f.getTip()) {
			String tipname = a.name();
			tips.add(tip.valueOf(tipname));
		}
		Farmacie nf = new Farmacie(name, loc, tips);
		for(Medicament m : f.getStoc()) {
			String medname = new String(m.getNume());
			double medprice = Double.valueOf(m.getPret());
			int medamount = Integer.valueOf(m.getAmount());
			List<tip> medtip = new ArrayList<>();
			for(tip b : m.getTip()) {
				String tipname = b.name();
				medtip.add(tip.valueOf(tipname));
			}
			nf.getStoc().add(new Medicament(medname, medprice, medtip, medamount));
		}
		return nf;
	}
	
	public List<Medicament> find(String nume_med) {
		List<Medicament> mds = new ArrayList<>();
		for(Medicament m : this.stoc) {
			if(m.getNume().contains(nume_med)) {
				mds.add(m);
			}
		}
		return mds.size() > 0 ? mds : null;
	}
	
	public int find_poz(String nume) {
		for(int i = 0; i < this.stoc.size(); i++) {
			if(this.stoc.get(i).getNume().equals(nume)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * @return the stoc
	 */
	public List<Medicament> getStoc() {
		return stoc;
	}
	
	public List<tip> getTip(){
		return this.ltip;
	}
	
	/**
	 * @return the nume
	 */
	public String getNume() {
		return nume;
	}
	
	/**
	 * @param nume the nume to set
	 */
	public void setNume(String nume) {
		this.nume = nume;
	}
	
	/**
	 * @return the loc
	 */
	public String getLoc() {
		return loc;
	}
	/**
	 * @param loc the loc to set
	 */
	public void setLoc(String loc) {
		this.loc = loc;
	}
	@Override
	public String toString() {
		String tr = this.nume + ", " + this.loc + "\n";
		for(int i = 0; i < stoc.size(); i++) {
			tr += "\t" + stoc.get(i).toString() + ", " + ltip + "\n";
		}
		return tr;
	}
	
}
