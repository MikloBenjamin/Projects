package proiect_gui;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Medicament implements Serializable{
	private static final long serialVersionUID = 1L;
	String nume;
	double pret;
	int amount;
	List<tip> ltip = new ArrayList<>();
	
	public Medicament(String nume, double pret, List<tip> ltip, int amount) {
		this.nume = nume;
		this.pret = pret;
		this.amount = amount;
		this.ltip = ltip;
	}
	
	public List<tip> getTip(){
		return this.ltip;
	}
	
	public String getNume() {
		return this.nume;
	}
	
	public double getPret() {
		return this.pret;
	}
	
	@Override
	public String toString() {
		return this.nume + ", " + this.pret + ", " + amount + ", " + ltip; 
	}
		
	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void addAmount(int amount) {
		this.amount += amount;
	}
	
	public void minusAmount(int amount) {
		this.amount -= amount;
	}

}
