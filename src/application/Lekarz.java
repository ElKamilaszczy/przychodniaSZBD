package application;

import java.util.HashSet;
import java.util.Set;

public class Lekarz {
	private int id_lekarza;
	private int sala;
	private PracownicyInformacje pracownicy_informacje;
	//Lekarz posiada wiele wypisanych recept
	private Set<Recepta> recepty = 
			new HashSet<Recepta>(0);
	private Set<Skierowanie> skierowania = 
			new HashSet<Skierowanie>(0);
	private Set<Wizyta> wizyty = 
			new HashSet<Wizyta>(0);
	
	public Lekarz()
	{
		
	}
	
	public Set<Skierowanie> getSkierowania() {
		return skierowania;
	}

	public void setSkierowania(Set<Skierowanie> skierowania) {
		this.skierowania = skierowania;
	}

	public Set<Wizyta> getWizyty() {
		return wizyty;
	}

	public void setWizyty(Set<Wizyta> wizyty) {
		this.wizyty = wizyty;
	}

	public Set<Recepta> getRecepty() {
		return recepty;
	}

	public void setRecepty(Set<Recepta> recepty) {
		this.recepty = recepty;
	}
	
	public PracownicyInformacje getPracownicy_informacje(){
		return pracownicy_informacje;
	}
	public void setPracownicy_informacje(PracownicyInformacje pi){
		this.pracownicy_informacje = pi;
	}
	public Lekarz(int sala)
	{
		this.sala = sala;
	}
	public int getId_lekarza() {
		return id_lekarza;
	}

	public void setId_lekarza(int id) {
		this.id_lekarza = id;
	}

	public int getSala() {
		return sala;
	}

	public boolean setSala(int sala) {
		
		this.sala = sala;
		return true;
	}
}
