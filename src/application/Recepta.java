package application;


import java.util.Date;
//data
//kolejny
public class Recepta {

	private int nr_recepty;
	private Lekarz lekarz;
	private Pacjent pesel_pacjenta;
	private Date data;
	private String opis;
	public Recepta()
	{
		this.data = new Date();
	}
	public Recepta(int id_lekarza, String pesel_pacjenta, String opis) {
		
		this.data = new Date();
		this.opis = opis;
	}
	
	public int getNr_recepty() {
		return nr_recepty;
	}
	public void setNr_recepty(int id) {
		this.nr_recepty = id;
	}
	public Lekarz getLekarz() {
		return lekarz;
	}
	public void setLekarz(Lekarz lekarz) {
		this.lekarz = lekarz;
	}
	public Pacjent getPesel_pacjenta() {
		return pesel_pacjenta;
	}
	public void setPesel_pacjenta(Pacjent pesel_pacjenta) {
		this.pesel_pacjenta = pesel_pacjenta;
	}

	public void setData(Date data) {
		this.data = data;
	}
	public Date getData() {
		return data;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
}
