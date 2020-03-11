package application;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;

import javax.imageio.spi.RegisterableService;

import org.hibernate.Query;
import org.hibernate.Session;

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


public class Edytowanie_lekarzaController {
	@FXML
	private TextField l_login, l_haslo, l_imie, l_nazwisko, l_wiek, l_sala, l_telefon;
	@FXML
	public Button p_ok, p_anuluj;
	//Stworzenie instancji na pacjenta stworzonego w tym kontrolerze
	@FXML
	private ObservableList<PracownicyInformacje> lekarz;
	private ObservableList<PracownicyInformacje> pielegniarka;
	private Centrala C;
	private int index, miejsce_w_tablicy;
	//Nale¿y j¹ wykonaæ, by nadaæ jakby eventy na poszczególne pola (wykonuje siê dla wszystkich FXML), jest to taka inicjalizacyjna
	//Inicjalizuje ona w³asciwoœci dla FXMLi
	@FXML
	public void initialize() throws NumberFormatException
	{
		//Login Property nie jest potrzebne - ka¿da cyfra m
		l_login.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("^[0-9A-Za-z\\s-]+$")) {
		            int value = Integer.parseInt(newValue);
		        } else {
		           l_login.setText(oldValue);
		        }
		    }
		});
		l_haslo.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("^[0-9A-Za-z\\s-]+$")) {
		            int value = Integer.parseInt(newValue);
		        } else {
		           l_login.setText(oldValue);
		        }
		    }
		});
		l_imie.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("[A-Z][a-z]*")) {
		        	int value = Integer.parseInt(newValue);
		        } else {
		            l_imie.setText("");
		        }
		    }
		});
		l_nazwisko.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("[A-Z][a-z]*")) {
		            int value = Integer.parseInt(newValue);
		        } else {
		            l_imie.setText(oldValue);
		        }
		    }
		});

		l_wiek.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("([0-9]){0,3}")) {
		            int value = Integer.parseInt(newValue);
		        } else {
		            l_wiek.setText(oldValue);
		        }
		    }
		});
		l_sala.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("([0-9]){0,3}")) {
		            int value = Integer.parseInt(newValue);
		        } else {
		            l_sala.setText(oldValue);
		        }
		    }
		});
		l_telefon.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("([0-9]){0,9}")) {
		            int value = Integer.parseInt(newValue);
		        } else {
		            l_telefon.setText(oldValue);
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
			if(l_login.getText().isEmpty() || l_haslo.getText().isEmpty() || l_imie.getText().isEmpty() ||
				l_nazwisko.getText().isEmpty() || l_wiek.getText().isEmpty() || l_sala.getText().isEmpty()
				|| l_telefon.getText().isEmpty())
				{
					errorWindow();
				}
			Iterator it = lekarz.iterator();
			Iterator it1 = pielegniarka.iterator();
			while(it.hasNext())
			{
				 PracownicyInformacje lekarz1 = (PracownicyInformacje) it.next();
				 if(l_login.getText().equals(lekarz1.getLogin()))
				 {
					 loginError();
					 return;
				 }

				 if(Integer.parseInt(l_sala.getText()) == lekarz1.getLekarz().getSala())
				 {
					 salaError();
					 return;
				 }
			}
			while(it1.hasNext())
			{
				PracownicyInformacje lekarz1 = (PracownicyInformacje) it1.next();
				 if(l_telefon.getText().equals(lekarz1.getTelefon()))
				 {
					 telefonError();
					 return;
				 }
			}
		     
			//Dodanie do bazy + odœwie¿enie listy w tabeli
			lekarz.set(miejsce_w_tablicy, C.getInstance().updateLekarz(index, l_login.getText(), l_haslo.getText(), l_imie.getText(), l_nazwisko.getText(),
					Integer.parseInt(l_wiek.getText()), l_telefon.getText(), Integer.parseInt(l_sala.getText())));

			informationWindow();

			
		}

			
	}
	public void informationWindow()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Komunikat");
		alert.setHeaderText(null);
		alert.setContentText("Pomyœlnie dodano doktorka.");

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
	public void loginError()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText(null);
		alert.setContentText("Login jest zajêty.");

		alert.showAndWait();
	}
	public void telefonError()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText(null);
		alert.setContentText("Telefon jest zajêty.");

		alert.showAndWait();
	}
	public void salaError()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText(null);
		alert.setContentText("Sala jest ju¿ zajêta przez innego lekarza.");

		alert.showAndWait();
	}
	//Implementacja metody potrzebnej w tym kontrolerze na dodanie Pacjenta do listy.
	public void setItemsPielegniarki(ObservableList<PracownicyInformacje> pielegniarki) {
		this.pielegniarka = pielegniarki;
		
	}
	public void setItemsLekarze(ObservableList<PracownicyInformacje> lekarz) {
		// TODO Auto-generated method stub
		this.lekarz = lekarz;
	}
	public void setItemsWybrany(PracownicyInformacje selectedItem) {
		l_imie.textProperty().setValue(selectedItem.getImie());
		l_nazwisko.textProperty().setValue(selectedItem.getNazwisko());
		l_wiek.textProperty().setValue(Integer.toString(selectedItem.getWiek()));
		l_telefon.textProperty().setValue(selectedItem.getTelefon());
		l_haslo.textProperty().setValue(selectedItem.getHaslo());
		l_sala.textProperty().setValue(Integer.toString(selectedItem.getLekarz().getSala()));
		l_login.textProperty().setValue(selectedItem.getLogin());
	}
	public void setIndex(int id, int miejsceWtablicy) {
		// TODO Auto-generated method stub
		this.index = id;
		this.miejsce_w_tablicy = miejsceWtablicy;
	}

}
