package application;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
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


public class Panel_lekarzaController {
	
	static public int id;
	static public boolean deci = true;
	public TextField pesel_filtr;
	public Label etykieta_filtr;
	public MenuBar bar;
	private Centrala C;
	public ContextMenu c_menu;
	public ContextMenu c_menu1;
	public ContextMenu c_menu2;
	public ContextMenu c_menu3;

	@FXML
	public TableView<Pacjent> pacjenci;
	@FXML
	public TableView<Wizyta> wizyty;
	@FXML
	public TableView<Skierowanie> skierowania;
	@FXML
	public TableView<Recepta> recepty;
	@FXML
	public TableView<Wizyta> wizyty_domowe;
	@FXML
	public TableView<CennikWizytDomowych> cennik;

	public TableColumn<Pacjent,String> p_pesel, p_imie, p_nazwisko, p_ulica, p_miejscowosc, p_kod_pocztowy;
	public TableColumn<Pacjent,Integer> p_wiek, p_nr_d, p_nr_m;
	public TableColumn<Wizyta,String> w_pesel, w_opis;
	public TableColumn<Wizyta,Integer> w_id;
	public TableColumn<Wizyta,Date> w_data;
	public TableColumn<Skierowanie,String> s_id_p, s_pesel, s_opis, s_cel;
	public TableColumn<Skierowanie,Integer> s_id;
	public TableColumn<Skierowanie,Date> s_data;
	public TableColumn<Recepta,String>  r_id_p,r_pesel, r_opis;
	public TableColumn<Recepta,Integer> r_id;
	public TableColumn<Recepta,Date> r_data;
	//cennik
	@FXML
	public TableColumn<CennikWizytDomowych, Integer> c_id, c_cena;
	@FXML
	public TableColumn<CennikWizytDomowych, String> c_opis;
	
	//wizyty domowe kolumny
	@FXML
	public TableColumn<Wizyta,String> wd_pesel, wd_opis, wd_typ_wizyty;
	@FXML
	public TableColumn<Wizyta,Integer> wd_id;
	@FXML
	public TableColumn<Wizyta,Date> wd_data;
	
	public ObservableList<CennikWizytDomowych> lista_cennik;
	public ObservableList<Pacjent> lista_pacjentow = FXCollections.observableArrayList();
	public ObservableList<Wizyta> lista_wizyt = FXCollections.observableArrayList();
	public ObservableList<Wizyta> lista_wizyt_domowych = FXCollections.observableArrayList();
	public ObservableList<Skierowanie> lista_skierowan =FXCollections.observableArrayList();
	public ObservableList<Recepta> lista_recept = FXCollections.observableArrayList();
	public ObservableList<PracownicyInformacje> lista_lekarzy = FXCollections.observableArrayList();
	public ObservableList<Pacjent> lista_pacjentow_na_wizyty = FXCollections.observableArrayList();
	public PracownicyInformacje obecny_lekarz;
	
	
	//////////////////////////////////////////////////////////////FILTR/////////////////////////////////////////////////////////////////////////////

	public void filtr(){
		List<Pacjent> tmp1 = new ArrayList<>();
		List<Wizyta> tmp2 = new ArrayList<>();
		List<Skierowanie> tmp3 = new ArrayList<>();
		List<Recepta> tmp4 = new ArrayList<>();
		List<Wizyta> tmp5 = new ArrayList<>();

		

			for(Pacjent a: lista_pacjentow) {
				if(a.getPesel().startsWith(pesel_filtr.getText()))
					tmp1.add(a);
			
			}
			for(Wizyta a: lista_wizyt) {
				if(a.getPesel_pacjenta().getPesel().startsWith(pesel_filtr.getText()) && a.getLekarz().getId_lekarza() == id)
					tmp2.add(a);
			
			}
			for(Skierowanie a: lista_skierowan ) {
				if(a.getPesel_pacjenta().getPesel().startsWith(pesel_filtr.getText()) && a.getLekarz().getId_lekarza() == id)
					tmp3.add(a);
			
			}
			for(Recepta a: lista_recept) {
				if(a.getPesel_pacjenta().getPesel().startsWith(pesel_filtr.getText()) && a.getLekarz().getId_lekarza() == id)
					tmp4.add(a);
			
			}
			for(Wizyta a: lista_wizyt_domowych) {
				if(a.getPesel_pacjenta().getPesel().startsWith(pesel_filtr.getText()) && a.getLekarz().getId_lekarza() == id)
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
		w_pesel.setCellValueFactory(new Callback<CellDataFeatures<Wizyta,String>,ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Wizyta, String> param) {
				// TODO Auto-generated method stub

				 return new SimpleStringProperty(param.getValue().getPesel_pacjenta().getPesel());
			}
        });
		w_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		w_opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
		w_data.setCellValueFactory(new PropertyValueFactory<>("data"));
		
		
		
		s_id.setCellValueFactory(new PropertyValueFactory<>("nr_skierowania"));
		s_pesel.setCellValueFactory(new Callback<CellDataFeatures<Skierowanie,String>,ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skierowanie, String> param) {
				// TODO Auto-generated method stub

				 return new SimpleStringProperty(param.getValue().getPesel_pacjenta().getPesel());
			}
        });

		
		s_data.setCellValueFactory(new PropertyValueFactory<>("data"));
		s_opis.setCellValueFactory(new PropertyValueFactory<>("opis_skierowania"));
		s_cel.setCellValueFactory(new PropertyValueFactory<>("cel_skierowania"));

		
		r_pesel.setCellValueFactory(new Callback<CellDataFeatures<Recepta,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Recepta, String> param) {
				// TODO Auto-generated method stub
				return new SimpleStringProperty(param.getValue().getPesel_pacjenta().getPesel());
			}
		});
		r_id.setCellValueFactory(new PropertyValueFactory<>("nr_recepty"));
		
		r_opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
		r_data.setCellValueFactory(new PropertyValueFactory<>("data"));

		//Kolumny wizyt domowych
				wd_pesel.setCellValueFactory(new Callback<CellDataFeatures<Wizyta,String>,ObservableValue<String>>(){

					@Override
					public ObservableValue<String> call(CellDataFeatures<Wizyta, String> param) {
						// TODO Auto-generated method stub
						 return new SimpleStringProperty(param.getValue().getPesel_pacjenta().getPesel());
					}
		        });
				wd_id.setCellValueFactory(new PropertyValueFactory<>("id"));
				wd_typ_wizyty.setCellValueFactory(new Callback<CellDataFeatures<Wizyta,String>,ObservableValue<String>>(){

					@Override
					public ObservableValue<String> call(CellDataFeatures<Wizyta, String> param) {
						// TODO Auto-generated method stub
						 return new SimpleStringProperty(Integer.toString(param.getValue().getWizyty_domowe().getTyp_wizyty().getTyp_wizyty()));
					}
		        });
				wd_opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
				wd_data.setCellValueFactory(new PropertyValueFactory<>("data"));
				
				//Cennik kolumny
				c_id.setCellValueFactory(new PropertyValueFactory<>("typ_wizyty"));
				c_cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
				c_opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
				
				
////////////UZUPELNIENIE TABELEK///////////////
		lista_cennik = FXCollections.observableArrayList(C.getInstance().loadCennikWizytDomowych());
		//ObservableList<Wizyta> lista1 =  FXCollections.observableArrayList();
		for(Wizyta a:C.getInstance().loadWizytyDomowe()) {
			if(a.getLekarz().getId_lekarza() == id)
				lista_wizyt_domowych.add(a);
					
				}
		for(Wizyta a:C.getInstance().loadWizyty()) {
			if(a.getLekarz().getId_lekarza() == id)
				lista_wizyt.add(a);
			
		}
		//ObservableList<Pacjent> lista = FXCollections.observableArrayList(Centrala.getInstance().loadPacjenci());
		//ObservableList<Skierowanie> lista3 =  FXCollections.observableArrayList();
		for(Skierowanie a:C.getInstance().loadSkierowania()) {
			if(a.getLekarz().getId_lekarza() == id)
				lista_skierowan.add(a);
			
		}
		obecny_lekarz = C.getInstance().danyLekarz(id);
		lista_pacjentow_na_wizyty = FXCollections.observableArrayList(Centrala.getInstance().loadPacjenciLekarza(obecny_lekarz));
		//ObservableList<Recepta> lista4 =  FXCollections.observableArrayList();
		for(Recepta a: C.getInstance().loadRecepty()) {
			if(a.getLekarz().getId_lekarza()== id)
			{
				lista_recept.add(a);
				
			}
			
		}

		lista_pacjentow = FXCollections.observableArrayList(Centrala.getInstance().loadPacjenci());

		
		cennik.setItems(lista_cennik);
		wizyty_domowe.setItems(lista_wizyt_domowych);
		pacjenci.setItems(lista_pacjentow);
		wizyty.setItems(lista_wizyt);
		skierowania.setItems(lista_skierowan);
		recepty.setItems(lista_recept);
		
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
			cennik.getItems().clear();
			lista_cennik.clear();
			lista_recept = null;
			lista_skierowan = null;
			lista_pacjentow = null;
			lista_lekarzy = null;
			lista_wizyt_domowych = null;
			lista_wizyt = null;
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("Logowanie.fxml"));
			Scene scene = new Scene(root,400,220);
			System.out.println("COstam");
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
	//////////////////////////////////////////////////////////////DLA WIZYT/////////////////////////////////////////////////////////////////////////////

	
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
	//////////////////////////////////////////////////////////////DLA SKIEROWAN/////////////////////////////////////////////////////////////////////////////

	public void s_dodaj() {
		System.out.println("bbbbb");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dodawanie_skierowania.fxml"));
			Pane root = (Pane)fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			Dodawanie_skierowaniaController controller = fxmlLoader.getController();
		    controller.setItems(skierowania.getItems());
		    controller.setPacjenci(lista_pacjentow_na_wizyty);
		    controller.setLekarz(obecny_lekarz);
			stage.setScene(scene);
		    stage.setTitle("Dodaj skierowanie");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));

		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	@FXML
	public void r_dodaj() {
		System.out.println("bbbbb");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dodawanie_recepty.fxml"));
			Pane root = fxmlLoader.load();
			Dodawanie_receptyController controller = fxmlLoader.<Dodawanie_receptyController>getController();
			controller.setLekarz(obecny_lekarz);
			controller.setPacjenci(lista_pacjentow_na_wizyty);
		    controller.setItems(recepty.getItems());
		    //System.out.println("XDDDDDDDDDD" +lista_pacjentow_na_wizyty);

		 
	

			//Nalezy stworzyæ referencjê do drugiego kontrolera w celu przekazania istniejacej listy//
			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
		    stage.setTitle("Dodaj receptê");	
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((Stage) ((Stage)bar.getScene().getWindow()));
		    
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
			
		} catch (NullPointerException e) {
			
			System.out.println("");
			
			
		}
	}

	
	

}
