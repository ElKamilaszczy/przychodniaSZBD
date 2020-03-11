package application;


import java.util.Date;
//data
//kolejny
public class Skierowanie {

	private int nr_skierowania;
	private Lekarz lekarz;
	private Pacjent pesel_pacjenta;
	private Date data;
	private String cel_skierowania;
	private String opis_skierowania;
	public Skierowanie()
	{
		this.data = new Date();
	}
	/*
	public Recepta(int id_lekarza, String pesel_pacjenta, String opis) {
		
		this.data = new Date();
		this.opis = opis;
	}
	*/
	public int getNr_skierowania() {
		return nr_skierowania;
	}
	public void setNr_skierowania(int id) {
		this.nr_skierowania = id;
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
	public String getOpis_skierowania() {
		return opis_skierowania;
	}
	public void setOpis_skierowania(String opis) {
		this.opis_skierowania = opis;
	}
	public String getCel_skierowania() {
		return cel_skierowania;
	}
	public void setCel_skierowania(String cel_skierowania) {
		this.cel_skierowania = cel_skierowania;
	}
	
}
