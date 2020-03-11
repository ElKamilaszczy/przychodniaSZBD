package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class LogowanieController {
	public Button log;
	public TextField login;
	public PasswordField haslo;
	public void handle(ActionEvent event) throws IOException {
		//Centrala.getInstance().loadLekarze();
		switch(Centrala.getInstance().Logowanie(login.getText(), haslo.getText())) {
		
			case "":
				Alert alert = new Alert(AlertType.ERROR);
				//System.out.println("XD: "+ login.getText() + "haslo: "+haslo.getText());
				//System.out.println(": "+ Centrala.getInstance().Logowanie(login.getText(), haslo.getText()));
				alert.setTitle("B³¹d logowania");
				alert.setHeaderText(null);
				alert.setContentText("Nieprawid³owy login lub has³o");
				alert.showAndWait();
				break;
		
			case "admin":
				
					BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Rejestracja.fxml"));
					Scene scene = new Scene(root,900,700);
					//System.out.println("COstam");
				    Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
				    stageTheEventSourceNodeBelongs.setScene(scene);
				    stageTheEventSourceNodeBelongs.setTitle("Panel rejestracji");
				    break;
				
			default:
			    for(PracownicyInformacje a: Centrala.getInstance().getLekarze()) {
			    	System.out.println("COstam");
			    	if(a.getLogin().equals(login.getText())) {
								Panel_lekarzaController.id = a.getId();
					    		BorderPane root1 =FXMLLoader.load(getClass().getResource("Panel_lekarza.fxml"));
								//BorderPane root1 = (BorderPane)fxmlLoader1.load();
								Scene scene2 = new Scene(root1);
							    Stage stageTheEventSourceNodeBelongs2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
					    		stageTheEventSourceNodeBelongs2.setTitle("Panel lekarza; zalogowany: dr " + a.getImie() + " " + a.getNazwisko());
					  	
					    		stageTheEventSourceNodeBelongs2.setScene(scene2);
					    		break;
								
							
    		
			    	}
			    	
			    }  
			    break;
		}
		
		

	}
}
