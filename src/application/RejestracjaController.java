package application;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
//import javafx.util.Callback;


public class RejestracjaController {
	
	static public boolean deci = true;
	public TextField pesel_filtr;
	@FXML
	public Tab tab_lekarze;
	@FXML
	public Tab tab_pielegniarki, tab_dyzury;
	public Label etykieta_filtr;
	public MenuBar bar;
	
	public ContextMenu c_menu;
	public ContextMenu c_menu1;
	public ContextMenu c_menu2;
	public ContextMenu c_menu3;
	public ContextMenu c_menu4;
	public ContextMenu c_menu5;
	public ContextMenu c_menu6;
	public ContextMenu c_menu7;
	public ContextMenu c_menu8;
	@FXML
	public TableView<PracownicyInformacje> lekarze;
	@FXML
	public TableView<PracownicyInformacje> pielegniarki;
	@FXML
	public TableView<Pacjent> pacjenci;
	@FXML
	public TableView<Wizyta> wizyty;
	@FXML
	public TableView<Wizyta> wizyty_domowe;
	@FXML
	public TableView<Skierowanie> skierowania;
	@FXML
	public TableView<Recepta> recepty;
	@FXML
	public TableView<CennikWizytDomowych> cennik;
	@FXML
	public TableView<Dyzury> dyzury;
	//public TableView<PracownicyInformacje> pracownicy_lekarze;
	//public TableView<PracownicyInformacje> pracownicy_pielegniarki;
	
	@FXML
	public TableColumn<Dyzury, Integer> d_id;
	@FXML
	public TableColumn<Dyzury, String> d_id_pracownika, d_dzien_tygodnia, d_godzina_rozpoczecia, d_godzina_zakonczenia;
	@FXML
	public TableColumn<Pacjent,String> p_pesel, p_imie, p_nazwisko, p_ulica, p_miejscowosc, p_kod_pocztowy;
	@FXML
	public TableColumn<Pacjent,Integer> p_wiek, p_nr_d, p_nr_m;
	@FXML
	public TableColumn<Wizyta,String> w_id_p, w_pesel, w_opis;
	@FXML
	public TableColumn<Wizyta,Integer> w_id;
	@FXML
	public TableColumn<Wizyta,Date> w_data;
	//wizyty domowe kolumny
	@FXML
	public TableColumn<Wizyta,String> wd_id_p, wd_pesel, wd_opis, wd_typ_wizyty;
	@FXML
	public TableColumn<Wizyta,Integer> wd_id;
	@FXML
	public TableColumn<Wizyta,Date> wd_data;
	@FXML
	//lekarze
	public TableColumn<PracownicyInformacje,String> l_login, l_haslo, l_imie, l_nazwisko, l_telefon, l_nr_sali;
	@FXML
	public TableColumn<PracownicyInformacje,Integer> l_id, l_wiek;
	
	//pielegniarki
	@FXML
	public TableColumn<PracownicyInformacje,String> pi_imie, pi_nazwisko, pi_czy_stazystka, pi_telefon;
	@FXML
	public TableColumn<PracownicyInformacje,Integer> pi_wiek, pi_id;
	@FXML
	public TableColumn<Skierowanie,String>  s_id_p, s_pesel, s_opis, s_cel;
	@FXML
	public TableColumn<Skierowanie,Integer>s_id;
	@FXML
	public TableColumn<Skierowanie,Date> s_data;
	@FXML
	public TableColumn<Recepta,String> r_id_p, r_pesel, r_opis;
	@FXML
	public TableColumn<Recepta,Integer> r_id;
	@FXML
	public TableColumn<Recepta,Date> r_data;
	@FXML
	public TableColumn<CennikWizytDomowych, Integer> c_id, c_cena;
	@FXML
	public TableColumn<CennikWizytDomowych, String> c_opis;
	
	private Centrala C;
	public ObservableList<Pacjent> lista_pacjentow;
	public ObservableList<Wizyta> lista_wizyt;
	public ObservableList<Skierowanie> lista_skierowan;
	public ObservableList<Recepta> lista_recept;
	//public ObservableList<Lekarz> lista4;
	public ObservableList<CennikWizytDomowych> lista_cennik;
	public ObservableList<Wizyta> lista_wizyt_domowych;
	public ObservableList<PracownicyInformacje> lista_pielegniarek;
	public ObservableList<PracownicyInformacje> lista_lekarzy;
	public ObservableList<Dyzury> lista_dyzurow;
	
	public void change() {
		if(tab_lekarze.isSelected())
			etykieta_filtr.setText("Nazwisko lekarza: ");
		else if(tab_pielegniarki.isSelected())
			etykieta_filtr.setText("Nazwisko pielêgniarki: ");
		else if(tab_dyzury.isSelected())
		{
			etykieta_filtr.setText("Nazwisko pracownika: ");
		}
		else
			etykieta_filtr.setText("Pesel pacjenta: ");

		
	}
	//////////////////////////////////////////////////////////////FILTR/////////////////////////////////////////////////////////////////////////////

	public void filtr(){
		List<Pacjent> tmp1 = new ArrayList<>();
		List<Wizyta> tmp2 = new ArrayList<>();
		List<Skierowanie> tmp3 = new ArrayList<>();
		List<Recepta> tmp4 = new ArrayList<>();
		List<Wizyta> tmp5 = new ArrayList<>();
		List<PracownicyInformacje> tmp6 = new ArrayList<>();
		List<PracownicyInformacje> tmp7 = new ArrayList<>();
		List<Dyzury> tmp8 = new ArrayList<>();
		
		if(!tab_lekarze.isSelected() && !tab_pielegniarki.isSelected() && !tab_dyzury.isSelected()) {

			for(Pacjent a: lista_pacjentow) {
				if(a.getPesel().startsWith(pesel_filtr.getText()))
					tmp1.add(a);
			
			}
			for(Wizyta a: lista_wizyt) {
				if(a.getPesel_pacjenta().getPesel().startsWith(pesel_filtr.getText()))
					tmp2.add(a);
			
			}
			for(Skierowanie a: lista_skierowan ) {
				if(a.getPesel_pacjenta().getPesel().startsWith(pesel_filtr.getText()))
					tmp3.add(a);
			
			}
			for(Recepta a: lista_recept) {
				if(a.getPesel_pacjenta().getPesel().startsWith(pesel_filtr.getText()))
					tmp4.add(a);
			
			}
			for(Wizyta a: lista_wizyt_domowych) {
				if(a.getPesel_pacjenta().getPesel().startsWith(pesel_filtr.getText()))
					tmp5.add(a);
			
			}

			ObservableList<Pacjent> lista = FXCollections.observableArrayList(tmp1);
			ObservableList<Wizyta> lista1 = FXCollections.observableArrayList(tmp2);
			ObservableList<Skierowanie> lista2 = FXCollections.observableArrayList(tmp3);
			ObservableList<Recepta> lista3 = FXCollections.observableArrayList(tmp4);
			ObservableList<Wizyta> lista4 = FXCollections.observableArrayList(tmp5);
			pacjenci.setItems(lista);
			wizyty.setItems(lista1);
			skierowania.setItems(lista2);
			recepty.setItems(lista3);
			wizyty_domowe.setItems(lista4);
			
			
		}
		
		else {
			
			for(PracownicyInformacje a: lista_lekarzy) {
				if(a.getNazwisko().startsWith(pesel_filtr.getText()))
					tmp6.add(a);
				
			}
			for(PracownicyInformacje a: lista_pielegniarek) {
				if(a.getNazwisko().startsWith(pesel_filtr.getText()))
					tmp7.add(a);
				
			}
			for(Dyzury a: lista_dyzurow) {
				if(a.getId_pracownika().getNazwisko().startsWith(pesel_filtr.getText()))
					tmp8.add(a);
			
			}
			
			ObservableList<PracownicyInformacje> lista = FXCollections.observableArrayList(tmp6);
			ObservableList<PracownicyInformacje> lista1 = FXCollections.observableArrayList(tmp7);
			ObservableList<Dyzury> lista2 = FXCollections.observableArrayList(tmp8);
			lekarze.setItems(lista);
			pielegniarki.setItems(lista1);
			dyzury.setItems(lista2);
			
		}
	}
	  
	public void initialize() {
									////////////INICJALIZACJA KOLUMN///////////////

		
		p_pesel.setCellValueFactory(new PropertyValueFactory<>("pesel"));
		p_imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
		p_nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
		p_wiek.setCellValueFactory(new PropertyValueFactory<>("wiek"));
		p_ulica.setCellValueFactory(new PropertyValueFactory<>("ulica"));
		p_nr_d.setCellValueFactory(new PropertyValueFactory<>("nr_domu"));
		p_nr_m.setCellValueFactory(new PropertyValueFactory<>("nr_mieszkania"));
		p_miejscowosc.setCellValueFactory(new PropertyValueFactory<>("miejscowosc"));
		p_kod_pocztowy.setCellValueFactory(new PropertyValueFactory<>("kod_pocztowy"));
		//Kolumny wizyty//
		w_pesel.setCellValueFactory(new Callback<CellDataFeatures<Wizyta,String>,ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Wizyta, String> param) {
				// TODO Auto-generated method stub
				 return new SimpleStringProperty(param.getValue().getPesel_pacjenta().getPesel());
			}
        });
		w_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		w_id_p.setCellValueFactory(new Callback<CellDataFeatures<Wizyta,String>,ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Wizyta, String> param) {
				// TODO Auto-generated method stub
				 return new SimpleStringProperty(Integer.toString(param.getValue().getLekarz().getId_lekarza()));
			}
        });
		w_opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
		w_data.setCellValueFactory(new PropertyValueFactory<>("data"));
		
		//Kolumny wizyt domowych
		wd_pesel.setCellValueFactory(new Callback<CellDataFeatures<Wizyta,String>,ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Wizyta, String> param) {
				// TODO Auto-generated method stub
				 return new SimpleStringProperty(param.getValue().getPesel_pacjenta().getPesel());
			}
        });
		wd_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		wd_id_p.setCellValueFactory(new Callback<CellDataFeatures<Wizyta,String>,ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Wizyta, String> param) {
				// TODO Auto-generated method stub
				 return new SimpleStringProperty(Integer.toString(param.getValue().getLekarz().getId_lekarza()));
			}
        });
		wd_typ_wizyty.setCellValueFactory(new Callback<CellDataFeatures<Wizyta,String>,ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Wizyta, String> param) {
				// TODO Auto-generated method stub
				 return new SimpleStringProperty(Integer.toString(param.getValue().getWizyty_domowe().getTyp_wizyty().getTyp_wizyty()));
			}
        });
		wd_opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
		wd_data.setCellValueFactory(new PropertyValueFactory<>("data"));

		
		
		s_id.setCellValueFactory(new PropertyValueFactory<>("nr_skierowania"));
		s_pesel.setCellValueFactory(new Callback<CellDataFeatures<Skierowanie,String>,ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skierowanie, String> param) {
				// TODO Auto-generated method stub
				if (param.toString().isEmpty())
				{
					return new SimpleStringProperty("Usuniety");
				}
				 return new SimpleStringProperty(param.getValue().getPesel_pacjenta().getPesel());
			}
        });

		s_id_p.setCellValueFactory(new Callback<CellDataFeatures<Skierowanie,String>,ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skierowanie, String> param) {
				// TODO Auto-generated method stub
				 return new SimpleStringProperty(Integer.toString(param.getValue().getLekarz().getId_lekarza()));
			}
        });
		s_data.setCellValueFactory(new PropertyValueFactory<>("data"));
		s_opis.setCellValueFactory(new PropertyValueFactory<>("opis_skierowania"));
		s_cel.setCellValueFactory(new PropertyValueFactory<>("cel_skierowania"));

		
		//Koluimny tabeli Recepta
		r_pesel.setCellValueFactory(new Callback<CellDataFeatures<Recepta,String>,ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Recepta, String> param) {
				// TODO Auto-generated method stub
				 return new SimpleStringProperty(param.getValue().getPesel_pacjenta().getPesel());
			}
        });
		r_id.setCellValueFactory(new PropertyValueFactory<>("nr_recepty"));
		r_id_p.setCellValueFactory(new Callback<CellDataFeatures<Recepta,String>,ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Recepta, String> param) {
				// TODO Auto-generated method stub
				if (param.toString().isEmpty())
				{
					return new SimpleStringProperty("Usuniety");
				}
				 return new SimpleStringProperty(Integer.toString(param.getValue().getLekarz().getId_lekarza()));
			}
        });
		r_opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
		r_data.setCellValueFactory(new PropertyValueFactory<>("data"));
		///Lekarze kolumny
		l_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		l_imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
		l_nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
		l_wiek.setCellValueFactory(new PropertyValueFactory<>("wiek"));
		l_login.setCellValueFactory(new PropertyValueFactory<>("login"));
		l_haslo.setCellValueFactory(new PropertyValueFactory<>("haslo"));
		l_telefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
		
		//Nale¿y przeprowadziæ modyfikacjê w celu uzupe³nienia zadanej kolumny wartoœciami odpowiadaj¹cymi wartoœciami dla danego lekarza//
		l_nr_sali.setCellValueFactory(new Callback<CellDataFeatures<PracownicyInformacje,String>,ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<PracownicyInformacje, String> param) {
				// TODO Auto-generated method stub
				 return new SimpleStringProperty(Integer.toString(param.getValue().getLekarz().getSala()));
			}
        });
		//Pielegniarki kolumny//
		pi_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		pi_imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
		pi_nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
		pi_wiek.setCellValueFactory(new PropertyValueFactory<>("wiek"));
	
		pi_telefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
		//Nale¿y przeprowadziæ modyfikacjê w celu uzupe³nienia zadanej kolumny wartoœciami odpowiadaj¹cymi wartoœciami dla danego lekarza//
		pi_czy_stazystka.setCellValueFactory(new Callback<CellDataFeatures<PracownicyInformacje,String>,ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<PracownicyInformacje, String> param) {
				// TODO Auto-generated method stub
				if (param.getValue().getPielegniarka().isCzy_stazystka())
				{
					 return new SimpleStringProperty("Tak");
				}
				else
					return new SimpleStringProperty("Nie");
				
			}
        });
		//Dyzury inicjalizacja kolumn
		d_id.setCellValueFactory(new PropertyValueFactory<>("id_dyzuru"));
		
		d_id_pracownika.setCellValueFactory(new Callback<CellDataFeatures<Dyzury,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Dyzury, String> param) {
				// TODO Auto-generated method stub
				return new SimpleStringProperty(param.getValue().getId_pracownika().getId() + " : "
						+ param.getValue().getId_pracownika().getImie() + " " + 
						param.getValue().getId_pracownika().getNazwisko());
			}
		});
		
		d_dzien_tygodnia.setCellValueFactory(new PropertyValueFactory<>("dzien_tygodnia"));
		d_godzina_rozpoczecia.setCellValueFactory(new Callback<CellDataFeatures<Dyzury,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Dyzury, String> param) {
				// TODO Auto-generated method stub
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				String godzina = sdf.format(param.getValue().getGodzina_rozpoczecia());
				return new SimpleStringProperty(godzina);
			}
		});
		d_godzina_zakonczenia.setCellValueFactory(new Callback<CellDataFeatures<Dyzury,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Dyzury, String> param) {
				// TODO Auto-generated method stub
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				String godzina = sdf.format(param.getValue().getGodzina_zakonczenia());
				return new SimpleStringProperty(godzina);
			}
		});
		//Cennik kolumny
		c_id.setCellValueFactory(new PropertyValueFactory<>("typ_wizyty"));
		c_cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
		c_opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
											////////////UZUPELNIENIE TABELEK///////////////
		lista_dyzurow =  FXCollections.observableArrayList(Centrala.getInstance().loadDyzury());
		//ObservableList<Dyzury> lista8 = FXCollections.observableArrayList(Centrala.getInstance().loadDyzury());
		lista_lekarzy = FXCollections.observableArrayList(Centrala.getInstance().loadLekarze());
		lista_pielegniarek = FXCollections.observableArrayList(Centrala.getInstance().loadPielegniarki());
		//ObservableList<PracownicyInformacje> lista7 = FXCollections.observableArrayList(Centrala.getInstance().loadPielegniarki());
		
		//ObservableList<Skierowanie> lista3 = FXCollections.observableArrayList(Centrala.getInstance().loadSkierowania());
		lista_skierowan =  FXCollections.observableArrayList(Centrala.getInstance().loadSkierowania());
		lista_wizyt_domowych = FXCollections.observableArrayList(Centrala.getInstance().loadWizytyDomowe());
		lista_wizyt = FXCollections.observableArrayList(Centrala.getInstance().loadWizyty());
		//ObservableList<Wizyta> lista1 = FXCollections.observableArrayList(Centrala.getInstance().loadWizyty());
		lista_cennik = FXCollections.observableArrayList(Centrala.getInstance().loadCennikWizytDomowych());
		
		lista_recept = FXCollections.observableArrayList(Centrala.getInstance().loadRecepty());
		//ObservableList<Recepta> lista4 = FXCollections.observableArrayList(Centrala.getInstance().loadRecepty());
		lista_pacjentow = FXCollections.observableArrayList(Centrala.getInstance().loadPacjenci());
		
		//lista_wizyt_domowych = FXCollections.observableArrayList(Centrala.getInstance().loadWizytyDomowe());
		
		
		
		pacjenci.setItems(lista_pacjentow);
		lekarze.setItems(lista_lekarzy);
		cennik.setItems(lista_cennik);
		wizyty.setItems(lista_wizyt);
		skierowania.setItems(lista_skierowan);
		recepty.setItems(lista_recept);
		wizyty_domowe.setItems(lista_wizyt_domowych);
		pielegniarki.setItems(lista_pielegniarek);
		dyzury.setItems(lista_dyzurow);
		
								////////////URUCHOMIENIE SZCZEGOL PO PODWOJNYM KLIKNIECIU MYSZY///////////////

		pacjenci.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            	try {
					p_szczegoly();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		
		wizyty.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                try {
					w_szczegoly();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		skierowania.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                try {
					s_szczegoly();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		recepty.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                try {
					r_szczegoly();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		lekarze.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                try {
					l_szczegoly();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}
	//////////////////////////////////////////////////////////////WYLOGOWANIE/////////////////////////////////////////////////////////////////////////////

	public void wyloguj() throws IOException {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Potwierdzenie wylogowania");
		alert.setHeaderText(null);
		alert.setContentText("Czy wylogowaæ?");

		ButtonType tak = new ButtonType("Tak");
		ButtonType nie = new ButtonType("Nie");
		alert.getButtonTypes().setAll(tak, nie);

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == tak){

			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("Logowanie.fxml"));
			Scene scene = new Scene(root,400,220);
		    Stage stageTheEventSourceNodeBelongs = (Stage) ((Stage)bar.getScene().getWindow());
		    stageTheEventSourceNodeBelongs.setScene(scene);
		    stageTheEventSourceNodeBelongs.setTitle("Logowanie");
			
			
		} 
		else if (result.get() == nie);
		
	}	
	//////////////////////////////////////////////////////////////WYJSCIE/////////////////////////////////////////////////////////////////////////////

	public void wyjdz() throws IOException {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Potwierdzenie wyjœcia");
		alert.setHeaderText(null);
		alert.setContentText("Czy wyjœæ?");

		ButtonType tak = new ButtonType("Tak");
		ButtonType nie = new ButtonType("Nie");
		alert.getButtonTypes().setAll(tak, nie);

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == tak){
		    Stage stageTheEventSourceNodeBelongs = (Stage) ((Stage)bar.getScene().getWindow());
		    stageTheEventSourceNodeBelongs.close();
		} 
		else if (result.get() == nie);
		
	}	
	//////////////////////////////////////////////////////////////DLA PACJENTA/////////////////////////////////////////////////////////////////////////////
	public void p_dodaj() {
		System.out.println("bbbbb");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dodawanie_pacjenta.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Dodawanie_pacjentaController controller = fxmlLoader.getController();
		    controller.setItems(pacjenci.getItems());
			stage.setScene(scene);
		    stage.setTitle("Dodaj pacjenta");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void p_edytuj() {
		//System.out.println(pacjenci.getSelectionModel().getSelectedItem().getPesel());
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Edytowanie_pacjenta.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Edytowanie_pacjentaController controller = fxmlLoader.getController();
			//Odbiór i przekazanie danych
			controller.setIndex(pacjenci.getSelectionModel().getSelectedItem().getPesel(), pacjenci.getSelectionModel().selectedIndexProperty().get());
		    controller.setItems(pacjenci.getSelectionModel().getSelectedItem());
		    controller.setPacjenci(lista_pacjentow);
			stage.setScene(scene);
		    stage.setTitle("Edytuj pacjenta");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void p_usun() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Potwierdzenie usuniêcia pacjenta");
		alert.setHeaderText(null);
		alert.setContentText("Czy usun¹æ pacjenta?");

		ButtonType tak = new ButtonType("Tak");
		ButtonType nie = new ButtonType("Nie");
		alert.getButtonTypes().setAll(tak, nie);

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == tak){
			//Centrala.getInstance().usunPacjenta(pacjenci.getSelectionModel().getSelectedItem().getPesel());
			Centrala.getInstance().usunPacjenta(pacjenci.getSelectionModel().getSelectedItem());
			//Centrala.getInstance().removePacjent(pacjenci.getSelectionModel().getSelectedIndex());
			
			for(Iterator<Recepta> iter = lista_recept.iterator(); iter.hasNext();) {
				Recepta a = iter.next();
				//System.out.println("a.getPesel_pacjenta().getPesel(): "+a.getPesel_pacjenta().getPesel());
				//System.out.println("pacjenci.getSelectionModel().getSelectedItem().getPesel(): "+pacjenci.getSelectionModel().getSelectedItem().getPesel());
				if(a.getPesel_pacjenta().getPesel().equals(pacjenci.getSelectionModel().getSelectedItem().getPesel())) 
				{
					iter.remove();
				}
				
				}
			for(Iterator<Skierowanie> iter = lista_skierowan.iterator(); iter.hasNext();) {
				Skierowanie a = iter.next();
				if(a.getPesel_pacjenta().getPesel().equals(pacjenci.getSelectionModel().getSelectedItem().getPesel())) 
				{
					iter.remove();
				}
				
				}
			for(Iterator<Wizyta> iter = lista_wizyt.iterator(); iter.hasNext();) {
				Wizyta a = iter.next();
				if(a.getPesel_pacjenta().getPesel().equals(pacjenci.getSelectionModel().getSelectedItem().getPesel())) 
				{
					iter.remove();
				}
				
				}
			for(Iterator<Wizyta> iter = lista_wizyt_domowych.iterator(); iter.hasNext();) {
				Wizyta a = iter.next();
				if(a.getPesel_pacjenta().getPesel().equals(pacjenci.getSelectionModel().getSelectedItem().getPesel())) 
				{
					iter.remove();
				}
				
				}
			pacjenci.getItems().remove(pacjenci.getSelectionModel().getSelectedIndex());
			//pielegniarki.getItems().remove(lekarze.getSelectionModel().getSelectedIndex());
		} 
		
		else if (result.get() == nie);
		
	}
	public void p_szczegoly() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Szczegoly_pacjenta.fxml"));
		Pane root = (Pane)fxmlLoader.load();
		Szczegoly_pacjentaController controller = fxmlLoader.<Szczegoly_pacjentaController>getController();
		controller.set_labels(pacjenci.getSelectionModel().getSelectedItem());
		Scene scene = new Scene(root);
		System.out.println("COstam");
		Stage stage = new Stage();
		stage.setScene(scene);
	    stage.setTitle("Szczegó³y pacjenta");	
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
	    
	    stage.show();
	    
	}
	////////////////////////////////////////////////////////////DLA PIELEGNIARKI/////////////////////////////////////////////////////////
	public void pi_dodaj() {
		System.out.println("bbbbb");
		//System.out.println(pielegniarki.getItems());
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dodawanie_pielegniarki.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Dodawanie_pielegniarkiController controller = fxmlLoader.getController();
		    controller.setItems(pielegniarki.getItems());
			stage.setScene(scene);
		    stage.setTitle("Dodaj pielêgniarkê");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void pi_edytuj() {
		//pielegniarki.refresh();
		System.out.println("XDDDDD sdsd");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Edytowanie_pielegniarki.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Edytowanie_pielegniarkiController controller = fxmlLoader.getController();

			//Odbiór i przekazanie danych
			//controller.setTabelka(pielegniarki);
			controller.setIndex(pielegniarki.getSelectionModel().getSelectedItem().getId(), pielegniarki.getSelectionModel().selectedIndexProperty().get());
		    controller.setItems(pielegniarki.getSelectionModel().getSelectedItem());
		    controller.setPielegniarki(lista_pielegniarek);
			stage.setScene(scene);
		    stage.setTitle("Edytuj pielêgniarkê");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void pi_usun() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Potwierdzenie usuniêcia pielêgniarki");
		alert.setHeaderText(null);
		alert.setContentText("Czy usun¹æ pielêgniarkê?");

		ButtonType tak = new ButtonType("Tak");
		ButtonType nie = new ButtonType("Nie");
		alert.getButtonTypes().setAll(tak, nie);

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == tak){
			Centrala.getInstance().usunPielegniarke(pielegniarki.getSelectionModel().getSelectedItem());
			//Centrala.getInstance().removePacjent(pacjenci.getSelectionModel().getSelectedIndex());

			
			//lista_recept = FXCollections.observableArrayList(Centrala.getInstance().loadRecepty());
			for(Iterator<Dyzury> iter = lista_dyzurow.iterator(); iter.hasNext();) {
				Dyzury a = iter.next();
				if(a.getId_pracownika().getId() == pielegniarki.getSelectionModel().getSelectedItem().getId()) 
				{
					iter.remove();
				}
				
				}
			pielegniarki.getItems().remove(pielegniarki.getSelectionModel().getSelectedIndex());
			//System.out.println("Po usuwaniu l_usuun");

		} 
		else if (result.get() == nie);
		
	}
	/////////////////////////////////////////DYZURY//////////////////////////////
	public void d_dodaj() {
		System.out.println("bbbbb");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dodawanie_dyzuru.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Dodawanie_dyzuruController controller = fxmlLoader.getController();
		    controller.setItems(dyzury.getItems());
		    controller.setLekarze(lista_lekarzy);
		    controller.setPielegniarki(lista_pielegniarek);
			stage.setScene(scene);
		    stage.setTitle("Dodaj dy¿ur");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//edycja dyzurow
	public void d_edytuj() {
		//System.out.println(wizyty.getSelectionModel().getSelectedItem().getPesel_pacjenta());
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Edytowanie_dyzuru.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Edytowanie_dyzuruController controller = fxmlLoader.getController();
			//Odbiór i przekazanie danych
			controller.setIndex(dyzury.getSelectionModel().getSelectedItem().getId_dyzuru(), dyzury.getSelectionModel().selectedIndexProperty().get());
		    controller.setItems(dyzury.getSelectionModel().getSelectedItem());
		    controller.setItems(lista_dyzurow);
			controller.setPielegniarki(lista_pielegniarek);
		    controller.setLekarze(lista_lekarzy);
			stage.setScene(scene);
		    stage.setTitle("Edytuj wizytê");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void d_usun() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Potwierdzenie usuniêcia dyzuru");
		alert.setHeaderText(null);
		alert.setContentText("Czy usun¹æ dy¿ur?");

		ButtonType tak = new ButtonType("Tak");
		ButtonType nie = new ButtonType("Nie");
		alert.getButtonTypes().setAll(tak, nie);

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == tak){
			Centrala.getInstance().usunDyzur(dyzury.getSelectionModel().getSelectedItem());
			//Centrala.getInstance().removePacjent(pacjenci.getSelectionModel().getSelectedIndex());

			dyzury.getItems().remove(dyzury.getSelectionModel().getSelectedIndex());

		} 
		else if (result.get() == nie);
		
	}
	//////////////////////////////////////////////////////////////DLA WIZYT/////////////////////////////////////////////////////////////////////////////

	public void w_dodaj() {
		System.out.println("bbbbb");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dodawanie_wizyty.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Dodawanie_wizytyController controller = fxmlLoader.getController();
		    controller.setItems(lista_wizyt);
		    controller.setLekarze(lista_lekarzy);
		    controller.setPacjenci(lista_pacjentow);
			stage.setScene(scene);
		    stage.setTitle("Dodaj wizytê");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void w_edytuj() {
		//System.out.println(wizyty.getSelectionModel().getSelectedItem().getPesel_pacjenta());
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Edytowanie_wizyty.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Edytowanie_wizytyController controller = fxmlLoader.getController();
		    controller.setWybrany(wizyty.getSelectionModel().getSelectedItem());
		    controller.setItems(lista_wizyt);
		    controller.setLekarze(lista_lekarzy);
		    controller.setPacjenci(lista_pacjentow);
		    controller.setIndex(wizyty.getSelectionModel().getSelectedItem().getId(), wizyty.getSelectionModel().selectedIndexProperty().get());

		   
			stage.setScene(scene);
		    stage.setTitle("Dodaj wizytê");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void w_usun() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Potwierdzenie usuniêcia wizyty");
		alert.setHeaderText(null);
		alert.setContentText("Czy usun¹æ wizytê?");

		ButtonType tak = new ButtonType("Tak");
		ButtonType nie = new ButtonType("Nie");
		alert.getButtonTypes().setAll(tak, nie);

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == tak){
			Centrala.getInstance().usunWizyte(wizyty.getSelectionModel().getSelectedItem());
			wizyty.getItems().remove(wizyty.getSelectionModel().getSelectedIndex());
		    
		   
		} 
		else if (result.get() == nie);
		
	}
	public void w_szczegoly() throws IOException {
		System.out.println("cccc");
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Szczegoly_wizyty.fxml"));
		Pane root = (Pane)fxmlLoader.load();
		Szczegoly_wizytyController controller = fxmlLoader.<Szczegoly_wizytyController>getController();
		controller.set_labels(wizyty.getSelectionModel().getSelectedItem());
		Scene scene = new Scene(root);
		System.out.println("COstam");
		Stage stage = new Stage();
		stage.setScene(scene);
	    stage.setTitle("Szczegó³y wizyty");	
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
	    
	    stage.show();
		
	}
	///////////////////////////////////////WIZYTY DOMOWE//////////////////////////////////
	public void wd_dodaj() {
		System.out.println("bbbbb");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dodawanie_WizytyDomowej.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Dodawanie_WizytyDomowejController controller = fxmlLoader.getController();
		    controller.setItems(lista_wizyt_domowych);
		    controller.setTyp(lista_cennik);
		    controller.setLekarze(lista_lekarzy);
		    controller.setPacjenci(lista_pacjentow);
			stage.setScene(scene);
		    stage.setTitle("Dodaj wizytê");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void wd_edytuj() {
		System.out.println("bbbbb");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Edytowanie_WizytyDomowej.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Edytowanie_WizytyDomowejController controller = fxmlLoader.getController();

		   
		    controller.setTyp(lista_cennik);
		   // controller.setWybrany(wizyty_domowe.getSelectionModel().getSelectedItem());
		    controller.setItems(lista_wizyt_domowych);
		    controller.setLekarze(lista_lekarzy);
		    controller.setPacjenci(lista_pacjentow);
		    controller.setIndex(wizyty_domowe.getSelectionModel().getSelectedItem().getId(),
		    		wizyty_domowe.getSelectionModel().selectedIndexProperty().get(),
		    		wizyty_domowe.getSelectionModel().getSelectedItem());

		   
			stage.setScene(scene);
		    stage.setTitle("Dodaj wizytê");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void wd_usun() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Potwierdzenie usuniêcia wizyty domowej");
		alert.setHeaderText(null);
		alert.setContentText("Czy usun¹æ wizytê?");

		ButtonType tak = new ButtonType("Tak");
		ButtonType nie = new ButtonType("Nie");
		alert.getButtonTypes().setAll(tak, nie);

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == tak){
			Centrala.getInstance().usunWizyteDomowa(wizyty_domowe.getSelectionModel().getSelectedItem());
			wizyty_domowe.getItems().remove(wizyty_domowe.getSelectionModel().getSelectedIndex());
		    
		   
		} 
		else if (result.get() == nie);
		
	}
	//////////////////////////////////////////////////////////////DLA SKIEROWAN/////////////////////////////////////////////////////////////////////////////

	
	
	public void s_usun() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Potwierdzenie usuniêcia skierowania");
		alert.setHeaderText(null);
		alert.setContentText("Czy usun¹æ skierowanie?");

		ButtonType tak = new ButtonType("Tak");
		ButtonType nie = new ButtonType("Nie");
		alert.getButtonTypes().setAll(tak, nie);

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == tak){
			Centrala.getInstance().removeSkierowanie(skierowania.getSelectionModel().getSelectedIndex());
			skierowania.getItems().remove(skierowania.getSelectionModel().getSelectedIndex());
		    
		   
		} 
		else if (result.get() == nie);
		
	}
	public void s_szczegoly() throws IOException {
		System.out.println("cccc");
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Szczegoly_skierowania.fxml"));
		Pane root = (Pane)fxmlLoader.load();
		Szczegoly_skierowaniaController controller = fxmlLoader.<Szczegoly_skierowaniaController>getController();
		controller.set_labels(skierowania.getSelectionModel().getSelectedItem());
		Scene scene = new Scene(root);
		System.out.println("COstam");
		Stage stage = new Stage();
		stage.setScene(scene);
	    stage.setTitle("Szczegó³y skierowania");	
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
	    
	    stage.show();
	    
		
	}
	//////////////////////////////////////////////////////////////DLA RECEPT/////////////////////////////////////////////////////////////////////////////

	
	public void r_usun() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Potwierdzenie usuniêcia recepty");
		alert.setHeaderText(null);
		alert.setContentText("Czy usun¹æ receptê?");

		ButtonType tak = new ButtonType("Tak");
		ButtonType nie = new ButtonType("Nie");
		alert.getButtonTypes().setAll(tak, nie);

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == tak){
			Centrala.getInstance().removeRecepta(recepty.getSelectionModel().getSelectedIndex());
			recepty.getItems().remove(recepty.getSelectionModel().getSelectedIndex());
		    
		   
		} 
		else if (result.get() == nie);
		
	}
	public void r_szczegoly() throws IOException {
		System.out.println("cccc");
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Szczegoly_recepty.fxml"));
		Pane root = (Pane)fxmlLoader.load();
		Szczegoly_receptyController controller = fxmlLoader.<Szczegoly_receptyController>getController();
		controller.set_labels(recepty.getSelectionModel().getSelectedItem());
		Scene scene = new Scene(root);
		System.out.println("COstam");
		Stage stage = new Stage();
		stage.setScene(scene);
	    stage.setTitle("Szczegó³y recepty");	
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
	    
	    stage.show();
		
	}
	//////////////////////////////////////////////////////////////DLA LEKARZY/////////////////////////////////////////////////////////////////////////////
	public void l_dodaj() {
		//System.out.println("bbbbb");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dodawanie_lekarza.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Dodawanie_lekarzaController controller = fxmlLoader.getController();
			controller.setItemsPielegniarki(pielegniarki.getItems());
		    controller.setItemsLekarze(lekarze.getItems());
			stage.setScene(scene);
		    stage.setTitle("Dodaj lekarza");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void l_edytuj() {
		//System.out.println(lekarze.getSelectionModel().getSelectedItem().getNazwisko());
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Edytowanie_lekarza.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Edytowanie_lekarzaController controller = fxmlLoader.getController();
			//Odbiór i przekazanie danych
			controller.setIndex(lekarze.getSelectionModel().getSelectedItem().getId(), lekarze.getSelectionModel().selectedIndexProperty().get());
			controller.setItemsLekarze(lekarze.getItems());
			controller.setItemsPielegniarki(pielegniarki.getItems());
		    controller.setItemsWybrany(lekarze.getSelectionModel().getSelectedItem());
			stage.setScene(scene);
		    stage.setTitle("Edytuj lekarza");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void l_usun() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Potwierdzenie usuniêcia lekarza");
		alert.setHeaderText(null);
		alert.setContentText("Czy usun¹æ lekarza?");

		ButtonType tak = new ButtonType("Tak");
		ButtonType nie = new ButtonType("Nie");
		alert.getButtonTypes().setAll(tak, nie);

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == tak){
			Centrala.getInstance().usunLekarza(lekarze.getSelectionModel().getSelectedItem());
			//Centrala.getInstance().removePacjent(pacjenci.getSelectionModel().getSelectedIndex());

			
			//lista_recept = FXCollections.observableArrayList(Centrala.getInstance().loadRecepty());
			for(Iterator<Recepta> iter = lista_recept.iterator(); iter.hasNext();) {
				Recepta a = iter.next();
				if(a.getLekarz().getId_lekarza() == lekarze.getSelectionModel().getSelectedItem().getId()) 
				{
					iter.remove();
				}
			//recepty.getItems().remove(lekarze.getSelectionModel().getSelectedIndex());
		   
				} 
			for(Iterator<Skierowanie> iter = lista_skierowan.iterator(); iter.hasNext();) {
				Skierowanie a = iter.next();
				if(a.getLekarz().getId_lekarza() == lekarze.getSelectionModel().getSelectedItem().getId()) 
					iter.remove();
				}

			for(Iterator<Wizyta> iter = lista_wizyt.iterator(); iter.hasNext();) {
				Wizyta a = iter.next();
				if(a.getLekarz().getId_lekarza() == lekarze.getSelectionModel().getSelectedItem().getId()) 
					iter.remove();
				}
			for(Iterator<Wizyta> iter = lista_wizyt_domowych.iterator(); iter.hasNext();) {
				Wizyta a = iter.next();
				if(a.getLekarz().getId_lekarza() == lekarze.getSelectionModel().getSelectedItem().getId()) 
					iter.remove();
				}
			for(Iterator<Dyzury> iter = lista_dyzurow.iterator(); iter.hasNext();) {
				Dyzury a = iter.next();
				//a.getId_pracownika().getId() == dyzury.getSelectionModel().getSelectedItem().getId_pracownika().getId()
				if(a.getId_pracownika().getId() == lekarze.getSelectionModel().getSelectedItem().getId()) 
					iter.remove();
				}
			lekarze.getItems().remove(lekarze.getSelectionModel().getSelectedIndex());
			System.out.println("Po usuwaniu l_usuun");
		
				}
		else if (result.get() == nie);
		
	}
	public void l_szczegoly() throws IOException {
		System.out.println("cccc");
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Szczegoly_lekarza.fxml"));
		Pane root = (Pane)fxmlLoader.load();
		Szczegoly_lekarzeController controller = fxmlLoader.<Szczegoly_lekarzeController>getController();
		controller.set_labels(lekarze.getSelectionModel().getSelectedItem());
		Scene scene = new Scene(root);
		System.out.println("COstam");
		Stage stage = new Stage();
		stage.setScene(scene);
	    stage.setTitle("Szczegó³y lekarza");	
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
	    
	    stage.show();
		
	}
	////////////////////////////////////////////////////CENNIK//////////////////////////////////////////////
	public void c_dodaj() {
		System.out.println("bbbbb");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dodawanie_CennikWizytDomowych.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Dodawanie_CennikWizytDomowychController controller = fxmlLoader.getController();
			controller.setItemsCennik(cennik.getItems());
			stage.setScene(scene);
		    stage.setTitle("Dodaj wpis w cenniku");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void c_edytuj() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Edytowanie_CennikWizytDomowych.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Edytowanie_CennikWizytDomowychController controller = fxmlLoader.getController();
			//Odbiór i przekazanie danych
			controller.setIndex(cennik.getSelectionModel().getSelectedItem().getTyp_wizyty(), cennik.getSelectionModel().selectedIndexProperty().get());
			controller.setItemsCennik(cennik.getItems());
		    controller.setItemsWybrany(cennik.getSelectionModel().getSelectedItem());
			stage.setScene(scene);
		    stage.setTitle("Edytuj lekarza");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	//////////////////////////////////////////////////////////////BLOKADA CONTEXT MENU/////////////////////////////////////////////////////////////////////////////

	public void test() {
		try {
			if(pacjenci.getSelectionModel().isEmpty())
				c_menu.hide();
			if(wizyty.getSelectionModel().isEmpty())
				c_menu1.hide();
			if(skierowania.getSelectionModel().isEmpty())
				c_menu2.hide();
			if(recepty.getSelectionModel().isEmpty())
				c_menu3.hide();
			if(lekarze.getSelectionModel().isEmpty())
				c_menu4.hide();
			if(wizyty_domowe.getSelectionModel().isEmpty())
				c_menu5.hide();
			if(pielegniarki.getSelectionModel().isEmpty())
				c_menu6.hide();
			if(cennik.getSelectionModel().isEmpty())
				c_menu7.hide();
			if(dyzury.getSelectionModel().isEmpty())
				c_menu8.hide();
		} catch (NullPointerException e) {
			
			System.out.println("");
			
			
		}
	}
	
	
	

}
