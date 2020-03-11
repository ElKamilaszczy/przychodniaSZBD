package application;

import java.io.IOException;
import java.util.Iterator;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;


public class Edytowanie_pacjentaController {
	@FXML
	private TextField p_pesel, p_imie, p_nazwisko, p_wiek, p_numer_domu, p_numer_mieszkania,p_ulica, p_kod_pocztowy, p_miejscowosc;
	@FXML
	public Button p_ok, p_anuluj;
	//Stworzenie instancji na pacjenta stworzonego w tym kontrolerze
	@FXML
	private ObservableList<Pacjent> pacjent;
	private Centrala C;
	private String poczatkowy_pesel;
	private String peselek;
	private int index;
	//Nale¿y j¹ wykonaæ, by nadaæ jakby eventy na poszczególne pola (wykonuje siê dla wszystkich FXML), jest to taka inicjalizacyjna
	//Inicjalizuje ona w³asciwoœci dla FXMLi
	@FXML
	public void initialize() throws NumberFormatException
	{

		p_pesel.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("([0-9]){0,11}")) {
		            int value = Integer.parseInt(newValue);
		        } else {
		            p_pesel.setText(oldValue);
		        }
		    }
		});
		p_imie.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("[A-Z][a-z]*")) {
		            int value = Integer.parseInt(newValue);
		        } else {
		            p_imie.setText("");
		        }
		    }
		});
		p_nazwisko.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("[A-Z][a-z]*")) {
		        	int value = Integer.parseInt(newValue);
		        } else {
		            p_nazwisko.setText("");
		        }
		    }
		});
		p_wiek.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("([0-9]){0,3}")) {
		            int value = Integer.parseInt(newValue);
		        } else {
		            p_wiek.setText(oldValue);
		        }
		    }
		});
		//Czy na pewno potrzeba nam walidacja numeru mieszkania i numeru domu (np. dom 11A)
		p_numer_domu.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("([0-9]){0,3}")) {
		            int value = Integer.parseInt(newValue);
		        } else {
		            p_numer_domu.setText(oldValue);
		        }
		    }
		});
		//Czy na pewno potrzeba nam walidacja numeru mieszkania i numeru domu (np. dom 11A)
		p_numer_mieszkania.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("([0-9]){0,3}")) {
		            int value = Integer.parseInt(newValue);
		        } else {
		            p_numer_mieszkania.setText(oldValue);
		        }
		    }
		});
		//Brak walidacji dla miejscowoœci (za du¿o k³opotów zwi¹zanych z kilku wyrazów, itp). W imionach rzadziej takie coœ.
		p_kod_pocztowy.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("([0-9]){0,2}\\-([0-9]){0,3}")) {
		            int value = Integer.parseInt(newValue);
		        } else {
		            p_kod_pocztowy.setText(oldValue);
		        }
		    }
		});
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
			if(p_pesel.getText().isEmpty() || p_pesel.getText().length() < 9 || p_imie.getText().isEmpty() || p_nazwisko.getText().isEmpty() ||
				p_wiek.getText().isEmpty() || p_numer_domu.getText().isEmpty() || p_numer_mieszkania.getText().isEmpty()
				|| p_ulica.getText().isEmpty() || p_miejscowosc.getText().isEmpty())
				{
					errorWindow();
				}
			if(poczatkowy_pesel.equals(p_pesel.getText()))
			{
			
			}
			else
			{
				String regex = "^[0-9]{2}-[0-9]{3}$";
				System.out.println(p_kod_pocztowy.getText().matches(regex));
				if (!p_kod_pocztowy.getText().matches(regex) || p_kod_pocztowy.getText().length() > 6)
				{
					kodPocztowyError();
					return;
				}
				Iterator it = pacjent.iterator();
				String pattern = "[0-9]{11}";
				while(it.hasNext())
				{
					Pacjent p = (Pacjent) it.next();
					if(!p_pesel.getText().matches(pattern))
					{
						peselError();
						return;
					}
					if(p_pesel.getText().equals(p.getPesel()))
					{
						peselError();
						return;
					}
					
				}
			}
			pacjent.set(index, (C.getInstance().updatePacjent(p_pesel.getText(), p_imie.getText(), p_nazwisko.getText(), Integer.parseInt(p_wiek.getText())
					, p_ulica.getText(), Integer.parseInt(p_numer_domu.getText()), Integer.parseInt(p_numer_mieszkania.getText())
					, p_miejscowosc.getText(), p_kod_pocztowy.getText())));
			informationWindow();
		}
	}

			
	public void informationWindow()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Komunikat");
		alert.setHeaderText(null);
		alert.setContentText("Pomyœlnie edytowano pacjenta.");

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
	public void kodPocztowyError()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText(null);
		alert.setContentText("B³êdny kod pocztowy.");

		alert.showAndWait();
	}
	public void peselError()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText(null);
		alert.setContentText("B³êdny pesel.");

		alert.showAndWait();
	}
	public void setIndex(String pesel, int i) {
		// TODO Auto-generated method stub
		this.peselek = pesel;
		this.index = i;
	}
	public void setItems(Pacjent p) {
		// TODO Auto-generated method stub
		p_pesel.setText(p.getPesel());
		p_imie.setText(p.getImie());
		p_nazwisko.setText(p.getNazwisko());
		p_wiek.setText(Integer.toString(p.getWiek()));
		p_miejscowosc.setText(p.getMiejscowosc());
		p_numer_mieszkania.setText(Integer.toString(p.getNr_mieszkania()));
		p_numer_domu.setText(Integer.toString(p.getNr_domu()));
		p_ulica.setText(p.getUlica());
		p_kod_pocztowy.setText(p.getKod_pocztowy());
		
		poczatkowy_pesel = p.getPesel();
		
		
	}
	public void setPacjenci(ObservableList<Pacjent> lista_pacjentow) {
		// TODO Auto-generated method stub
		this.pacjent = lista_pacjentow;
	}
}
