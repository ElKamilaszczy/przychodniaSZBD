package application;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Wizyta {
	
	//private static int total_id = 0;
	private int id;
	private Lekarz lekarz;
	private Pacjent pesel_pacjenta;
	private Timestamp data;
	private String opis;
	private WizytyDomowe wizyty_domowe;

	public Wizyta()
	{
		//this.data = new Timestamp(id);
	}
	/*
		public Wizyta(int id_lekarza, String pesel_pacjenta, String opis, String data) {
		//this.id = total_id++;
		this.id_lekarza = id_lekarza;
		this.pesel_pacjenta = pesel_pacjenta;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		    try {
				this.data = formatter.parse(data);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		this.opis = opis;
	}
	*/
	public int getId() {
		return id;
	}
	public WizytyDomowe getWizyty_domowe() {
		return wizyty_domowe;
	}
	public void setWizyty_domowe(WizytyDomowe wd) {
		this.wizyty_domowe = wd;
	}
	public Lekarz getLekarz() {
		return lekarz;
	}
	public void setLekarz(Lekarz id_lekarza) {
		this.lekarz = id_lekarza;
	}
	public Pacjent getPesel_pacjenta() {
		return pesel_pacjenta;
	}
	public void setPesel_pacjenta(Pacjent pesel_pacjenta) {
		this.pesel_pacjenta = pesel_pacjenta;
	}
	public Date getData() {
		return data;
	}
	public void setData(Timestamp data) {
		/*
		SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD HH24:MI");
		try {
		    this.data = (Timestamp) formatter.parse(data);
		    
		} catch (ParseException e) {
		}
		*/
		this.data = data;
	}
	
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
}
