package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


public class Pacjent {
	//private int id_lekarza;
	private String pesel;
	private String imie;
	private String nazwisko;
	private int wiek;
	private String ulica;
	private int nr_domu;
	private Integer nr_mieszkania;
	private String miejscowosc;
	private String kod_pocztowy;
	
	private Centrala C;
	private Set<Recepta> recepty = 
			new HashSet<Recepta>(0);
	private Set<Skierowanie> skierowania = 
			new HashSet<Skierowanie>(0);
	private Set<Wizyta> wizyty = 
			new HashSet<Wizyta>(0);

	Pacjent()
	{
		
	}
	Pacjent(String pesel)
	{
		this.pesel = pesel;
	}

	Pacjent(/*int id_lekarza, */String pesel, String imie, String nazwisko, int wiek , String ulica, int nr_domu, Integer nr_mieszkania,
			String miejscowosc, String kod_pocztowy)
	{
		//this.id_lekarza = id_lekarza;
		this.imie = imie;
		this.pesel = pesel;
		this.nazwisko = nazwisko;
		this.wiek = wiek;
		this.ulica = ulica;
		this.nr_domu = nr_domu;
		this.nr_mieszkania = nr_mieszkania;
		this.miejscowosc = miejscowosc;
		this.kod_pocztowy = kod_pocztowy;
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
	
	public String getPesel() {
		if (this.pesel == null)
		{
			return "00000000000";
		}
		return pesel;
	}
	@SuppressWarnings("static-access")
	public boolean setPesel(String pesel) {
		String pattern = "[0-9]{11}";
		if (pesel.matches(pattern))
		{
			this.pesel = pesel;
			return true;
			
		}
		//System.out.println("Pesel niepoprawny!");
		return false;
	}
	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		String pattern = "[A-Z][a-z]*";
		if (imie.matches(pattern))
		{
			this.imie = imie;
		}
		
	}
	public String getNazwisko() {
		return nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		String pattern = "[A-Z][a-z]*";
		if (nazwisko.matches(pattern))
		{
			this.nazwisko = nazwisko;
		}
		this.nazwisko = nazwisko;
	}
	public int getWiek() {
		return wiek;
	}
	public void setWiek(int wiek) {
		this.wiek = wiek;
	}
	public String getUlica() {
		return ulica;
	}
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}
	public int getNr_domu() {
		return nr_domu;
	}
	public void setNr_domu(int nr_domu) {
		this.nr_domu = nr_domu;
	}

	public int getNr_mieszkania() {
		if (this.nr_mieszkania != null)
			return this.nr_mieszkania;
		return 0;
	}
	public void setNr_mieszkania(Integer nr_mieszkania) {
		this.nr_mieszkania = nr_mieszkania;
	}
	public String getMiejscowosc() {
		return miejscowosc;
	}
	public void setMiejscowosc(String miejscowosc) {
		this.miejscowosc = miejscowosc;
	}
	public String getKod_pocztowy() {
		return kod_pocztowy;
	}
	public void setKod_pocztowy(String kod_pocztowy) {
		this.kod_pocztowy = kod_pocztowy;
	}
	/*
	public int getLekarz() {
		return id_lekarza;
	}
	public void setLekarz(int id) {
		this.id_lekarza = id;
	}
	*/
}
