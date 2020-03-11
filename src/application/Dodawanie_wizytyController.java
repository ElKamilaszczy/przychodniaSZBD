package application;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.function.UnaryOperator;

import javax.imageio.spi.RegisterableService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;


public class Dodawanie_wizytyController {
	@FXML
	private ComboBox w_lekarz, w_pacjent;
	@FXML
	private DatePicker w_data;
	@FXML
	private TextField w_opis;
	@FXML
	public Button p_ok, p_anuluj;
	@FXML
	private VBox TimeSpinnerGodzinaRozp;
	private TimeSpinner w_godzina_rozp;
	private Centrala C;
	private ObservableList<Pacjent> pacjent;
	private ObservableList<PracownicyInformacje> lekarz;
	private ObservableList<Wizyta> wizyta;
	private ObservableList<CennikWizytDomowych> cennik;
	//Nale¿y j¹ wykonaæ, by nadaæ jakby eventy na poszczególne pola (wykonuje siê dla wszystkich FXML), jest to taka inicjalizacyjna
	//Inicjalizuje ona w³asciwoœci dla FXMLi

	
	public void initialize() throws NumberFormatException
	{
		//Ustawienie Spinnera Godzina Rozpoczecia//
		w_godzina_rozp = new TimeSpinner();

		
		w_godzina_rozp.setMinWidth(Region.USE_PREF_SIZE);
		TimeSpinnerGodzinaRozp.getChildren().add(w_godzina_rozp);
		//TimeSpinner.setVgrow(w_godzina, Priority.ALWAYS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        w_godzina_rozp.valueProperty().addListener((obs, oldTime, newTime) -> 
            System.out.println(formatter.format(newTime)));

		
	
		
	}
	public void zamknijOkno(ActionEvent event)
	{
		   Stage stage = (Stage) p_anuluj.getScene().getWindow();
		   stage.close();
	}
	public void handle(ActionEvent event)
	{	

		if(p_ok != null)
		{	
			boolean czyPustyLekarz = w_lekarz.getSelectionModel().isEmpty();
			boolean czyPustaGodzina = w_godzina_rozp.getValue() == null;
			boolean czyPustyPacjent = w_pacjent.getSelectionModel().isEmpty();
			boolean czyPustyOpis = w_opis.getText().isEmpty();
			boolean czyPustaData = w_data.getValue() == null;
			if(czyPustyLekarz || czyPustyPacjent || czyPustaGodzina || czyPustyOpis || czyPustaData)
				{
					errorWindow();
					return;
				}
			
			if(w_data.getValue().isBefore(LocalDate.now()))
			{
				dataWindow();
				return;
			}
			String id = w_lekarz.getSelectionModel().getSelectedItem().toString();
			String id1[] = id.split(" ", 2);
			PracownicyInformacje pracownik = null;
			Iterator it1 = lekarz.iterator();
			while(it1.hasNext())
			{
				PracownicyInformacje d = (PracownicyInformacje) it1.next();
				if(d.getId() == Integer.parseInt(id1[0]))
				{
					pracownik = d;
					break;
				}
			}
			Pacjent pacjencik = null;
			String pesel = w_pacjent.getSelectionModel().getSelectedItem().toString();
			String pesel1[] = pesel.split(" ", 2);
			Iterator it2 = pacjent.iterator();
			while(it2.hasNext())
			{
				Pacjent d = (Pacjent) it2.next();
				if(d.getPesel().equals(pesel1[0]))
				{
					pacjencik = d;
					break;
				}
			}
			//Godzina rozp to timestamp//
			//String date = dt1.format(wd_data.getValue());
			java.sql.Date data = java.sql.Date.valueOf(w_data.getValue());
			if (w_data.getValue().isBefore(LocalDate.now()))
			{
				dataError();
				return;
			}
			String w_godzina_rozp_string = w_godzina_rozp.getValue().toString();
			//////////////////Czemu .format nie dziala?????///////////////
			final Timestamp timestamp_godz_rozp =
				    Timestamp.valueOf(
				        new SimpleDateFormat("yyyy-MM-dd ")
				        .format(data) // get the current date as String
				        .concat(w_godzina_rozp_string)        // and append the time
				    );
			System.out.println(timestamp_godz_rozp);

			//Dodanie wizyty
			wizyta.add(C.getInstance().addWizyta(pracownik, pacjencik, timestamp_godz_rozp, w_opis.getText()));
			
			informationWindow();
				
		}

			
	}
	public void informationWindow()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Komunikat");
		alert.setHeaderText(null);
		alert.setContentText("Pomyœlnie dodano wizytê.");

		alert.showAndWait();
	}
	public void dataWindow()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText(null);
		alert.setContentText("Niepoprawna data.");

		alert.showAndWait();
	}
	public void errorWindow()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText(null);
		alert.setContentText("Poprawnie uzupe³nij pola.");

		alert.showAndWait();
	}
	public void dataError()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText(null);
		alert.setContentText("Niepoprawna data.");

		alert.showAndWait();
	}
	public void godzinaError()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText(null);
		alert.setContentText("Godzina rozpoczecia jest wiêksza od zakonczenia.");

		alert.showAndWait();
	}
	//Implementacja metody potrzebnej w tym kontrolerze na dodanie Pacjenta do listy.

	public void setItems(ObservableList<Wizyta> lista_wizyt_domowych) {
		// TODO Auto-generated method stub
		this.wizyta = lista_wizyt_domowych;
	}
	public void setLekarze(ObservableList<PracownicyInformacje> items) {
		// TODO Auto-generated method stub
		this.lekarz = items;
		Iterator it = lekarz.iterator();
		while(it.hasNext())
		{
			PracownicyInformacje p = (PracownicyInformacje) it.next();
			w_lekarz.getItems().addAll(p.getId() + " : " +p.getImie() + " " +p.getNazwisko());
		}
	}
	public void setPacjenci(ObservableList<Pacjent> lista_pacjentow) {
		// TODO Auto-generated method stub
		this.pacjent = lista_pacjentow;
		Iterator it = pacjent.iterator();
		while(it.hasNext())
		{
			Pacjent p = (Pacjent) it.next();
			w_pacjent.getItems().addAll(p.getPesel() + " : " +p.getImie() + " " +p.getNazwisko());
		}
	}
	public void setIndex(int id, int i) {
		// TODO Auto-generated method stub
		
	}
}
