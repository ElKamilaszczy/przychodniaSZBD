package application;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javax.imageio.spi.RegisterableService;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;



public class Dodawanie_receptyController {
	@FXML
	private TextArea r_opis;
	@FXML
	private ComboBox r_pacjent;
	@FXML
	private Label r_lekarz;
	@FXML
	public Button p_ok, p_anuluj;
	private Centrala C;
	private Panel_lekarzaController p;
	private RejestracjaController r;
	private int id;
	private PracownicyInformacje lekarz;
	@FXML
	private ObservableList<Pacjent> pacjent;
	private ObservableList<Recepta> recepta;
	//Nale¿y j¹ wykonaæ, by nadaæ jakby eventy na poszczególne pola (wykonuje siê dla wszystkich FXML), jest to taka inicjalizacyjna
	//Inicjalizuje ona w³asciwoœci dla FXMLi

	public void initialize() {
		
	
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
			if(r_opis.getText().isEmpty() || r_pacjent.getSelectionModel().isEmpty())
			{
				errorWindow();
				return;
				
			}
			Iterator it = pacjent.iterator();
			Pacjent pacjent_obslugiwany = null;
			String peselek = r_pacjent.getSelectionModel().getSelectedItem().toString();
			String peselek1[] = peselek.split(" ", 2);
			while(it.hasNext())
			{
				Pacjent p = (Pacjent) it.next();
				if(peselek1[0].equals(p.getPesel()))
				{
					pacjent_obslugiwany = p;
					break;
				}
				
			}
			//PracownicyInformacje PI = C.getInstance().danyLekarz(id);
			recepta.add(C.getInstance().addRecepta(lekarz, pacjent_obslugiwany, r_opis.getText()));
			informationWindow();
		}

			
	}
	public void informationWindow()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Komunikat");
		alert.setHeaderText(null);
		alert.setContentText("Pomyœlnie dodano receptê.");

		alert.showAndWait();
	}
	public void errorWindow()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText(null);
		alert.setContentText("Uzupe³nij pola.");

		alert.showAndWait();
	}

	public void setItems(ObservableList<Recepta> items) {
		// TODO Auto-generated method stub
		this.recepta = items;
	}
	
	public void setPacjenci(ObservableList<Pacjent> lista_pacjentow_na_wizyty) {
		// TODO Auto-generated method stub
		System.out.println("setPacjenci");
		this.pacjent = lista_pacjentow_na_wizyty;
		Iterator it = pacjent.iterator();
		while(it.hasNext())
		{
			Pacjent p = (Pacjent) it.next();
			r_pacjent.getItems().addAll(p.getPesel()+" : "+p.getImie()+ " "+p.getNazwisko());
		}
		
	}
	public void setLekarz(PracownicyInformacje obecny_lekarz) {
		this.lekarz = obecny_lekarz;
		r_lekarz.setText(lekarz.getLekarz().getId_lekarza()+ ": " +lekarz.getImie() + " "
				+ lekarz.getNazwisko());
	}


}
