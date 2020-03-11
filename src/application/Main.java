package application;
	
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	 private static Stage guiStage;
	
	 public static Stage getStage() {
	        return guiStage;
	    }
	 @Override
	public void start(Stage primaryStage) {
		try {
			guiStage = primaryStage;
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Rejestracja.fxml"));
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("Logowanie.fxml"));
			//Scene scene = new Scene(root,900,700);
			Scene scene = new Scene(root,400,160);

			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Logowanie");
	
			///Session session = HibernateUtil.getSessionFactory().openSession();
		  //  Transaction tx = null;
		      
		   //   try {
		      //   tx = session.beginTransaction();
		        // List employees = session.createQuery("FROM Pacjent").list(); 
		         //for (Iterator iterator = employees.iterator(); iterator.hasNext();){
		        //    Pacjent employee = (Pacjent) iterator.next(); 
		        //    System.out.print("Pesel: " + employee.getPesel()); 
		        //    System.out.print("Imie: " + employee.getImie()); 
		        //    System.out.print("Nazwisko: " + employee.getNazwisko()); 
		        //    System.out.println("Numer mieszkania " + employee.getNr_mieszkania()); 

		        // }
		         ///////////////////MAPOWANIE ODPOWIEDNIE DANEGO ID, WIEC TO ID WPISUJE TAM DO ODPOWIEDNIEGO POLA, OBVIOUS///////////
		        // CennikWizytDomowych cwd =  Centrala.getInstance().addCennikWizytDomowych(300, "Elosds");
		        //Centrala.getInstance().addWizytyDomowe(cwd);
		        // tx.commit();
		         //tx = session.beginTransaction();
		         //Centrala.getInstance().addWizytyDomowe();
		         
		         //CennikWizytDomowych cwd = new CennikWizytDomowych(12, "XD");
		         //CennikWizytDomowych cwd1 = new CennikWizytDomowych(100, "XDx100");
		        // String hq1 = "FROM CennikWizytDomowych WHERE typ_wizyty = 1";
		       //  Query q = session.createQuery(hq1);
		       //  List wynik =  q.list();
		        // WizytyDomowe wd = new WizytyDomowe();
		         //wd.setTyp_wizyty(wynik);
		         //session.save(cwd);
		         //session.save(cwd1);
		        // tx.commit();
		         //////////////////////////proba wizyt////////////////////////////////
		        // tx = session.beginTransaction();
		       //  Wizyta w = new Wizyta();
		       //  w.setOpis("Wizyta10");
		        // WizytyDomowe wd = new WizytyDomowe();
		         //Ustawienie obupólnej zale¿nosci - jeden obiekt do drugiego i vice-versa
		        // wd.setTyp_wizyty(cwd);
		        // wd.setWizyta(w);
		        // w.setWizyty_domowe(wd);
		        // session.save(w);
		        // tx.commit();
		         
		         ///////////////////proba - lekarze////////////////////////////////
		         /*
		         tx = session.beginTransaction();
		         PracownicyInformacje pc = new PracownicyInformacje();
		         pc.setImie("Kamil");
		         pc.setNazwisko("Kamilaszczy");
		         pc.setLogin("xd123");
		         pc.setHaslo("1234");
		         pc.setTelefon("123456789");
		         pc.setWiek(28);
		         Lekarz l = new Lekarz();
		         l.setSala(12);
		         pc.setLekarz(l);
		         l.setPracownicy_informacje(pc);
		         
		         session.save(pc);
		         tx.commit();
		         */
		         ////////////////proba - pielegniarki//////////////////////
		         /*
		         tx = session.beginTransaction();
		         PracownicyInformacje pc = new PracownicyInformacje();
		         pc.setImie("Kamila");
		         pc.setNazwisko("Kamilaszczya");
		         //pc.setLogin("xd123");
		        // pc.setHaslo("1234");
		         pc.setTelefon("123456786");
		         pc.setWiek(28);
		         Pielegniarka l = new Pielegniarka();
		         l.setCzy_stazystka(true);
		         pc.setPielegniarka(l);
		         l.setPracownicy_informacje(pc);
		         
		         session.save(pc);
		         tx.commit();
		         */
		         //////////////proba - dodanie wizyty przez lekarza dla pacjenta//////////
		         /*
		         tx = session.beginTransaction();
		         PracownicyInformacje pc = new PracownicyInformacje();
		         pc.setImie("Kamilooooosssss");
		         pc.setNazwisko("Kamylooooo");
		         //pc.setLogin("xd123");
		        // pc.setHaslo("1234");
		         pc.setTelefon("123456712");
		         pc.setWiek(28);
		         Lekarz l = new Lekarz();
		         l.setSala(18);
		         pc.setLekarz(l);
		         l.setPracownicy_informacje(pc);
		         
		         Pacjent p = new Pacjent();
		         p.setPesel("11111111112");
		         p.setImie("Kamis");
		         p.setNazwisko("Kamisio");
		         p.setNr_domu(12);
		         p.setMiejscowosc("Miejscowosc1");
		         p.setKod_pocztowy("12-001");
		         p.setNr_mieszkania(12);
		         p.setUlica("Makowa");
		         
		         Recepta r = new Recepta();
		         r.setLekarz(l);
		         r.setOpis("Opis1");
		         r.setPesel_pacjenta(p);
		         session.save(pc);
		         session.save(p);
		         session.save(r);
		         tx.commit();
		         */
		         ////////////proba - skierowanie dla pacjenta////////////
		         /*
		         tx = session.beginTransaction();
		         PracownicyInformacje pc = new PracownicyInformacje();
		         pc.setImie("Kamilooooosssss1212");
		         pc.setNazwisko("Kamylooooo1212");
		         //pc.setLogin("xd123");
		        // pc.setHaslo("1234");
		         pc.setTelefon("123456744");
		         pc.setWiek(28);
		         Lekarz l = new Lekarz();
		         l.setSala(20);
		         pc.setLekarz(l);
		         l.setPracownicy_informacje(pc);
		         
		         Pacjent p = new Pacjent();
		         p.setPesel("11111111113");
		         p.setImie("Kamis");
		         p.setNazwisko("Kamisio");
		         p.setWiek(20);
		         p.setNr_domu(12);
		         p.setMiejscowosc("Miejscowosc1");
		         p.setKod_pocztowy("12-001");
		         p.setNr_mieszkania(12);
		         p.setUlica("Makowa");
		         
		         Skierowanie s = new Skierowanie();
		         s.setLekarz(l);
		         s.setPesel_pacjenta(p);
		         s.setCel_skierowania("Cel skierowania1");
		         s.setOpis_skierowania("Opis skierowania1");
		         session.save(pc);
		         session.save(p);
		         session.save(s);
		         tx.commit();
		         */
		         ///////////proba - wizyta dla pacjenta////////////////
		         /*
		         tx = session.beginTransaction();
		         PracownicyInformacje pc = new PracownicyInformacje();
		         pc.setImie("Kam");
		         pc.setNazwisko("Kamy");
		         //pc.setLogin("xd123");
		        // pc.setHaslo("1234");
		         pc.setTelefon("123456744");
		         pc.setWiek(28);
		         Lekarz l = new Lekarz();
		         l.setSala(40);
		         pc.setLekarz(l);
		         l.setPracownicy_informacje(pc);
		         
		         Pacjent p = new Pacjent();
		         p.setPesel("11111111118");
		         p.setImie("Kamis");
		         p.setNazwisko("Kamisio");
		         p.setWiek(20);
		         p.setNr_domu(12);
		         p.setMiejscowosc("Miejscowosc1");
		         p.setKod_pocztowy("12-001");
		         p.setNr_mieszkania(12);
		         p.setUlica("Makowa");
		         
		         Wizyta s = new Wizyta();
		         s.setLekarz(l);
		         s.setPesel_pacjenta(p);
		         s.setOpis("Opis skierowania1");
		         session.save(pc);
		         session.save(p);
		         session.save(s);
		         tx.commit();
		         */
		     //    Centrala C = new Centrala();
		         //C.getInstance().loadCennikWizytDomowych();
		        //C.getInstance().loadLekarze();
		         //C.getInstance().loadWizyty();
		         //C.getInstance().loadRecepty();
		         //C.getInstance().loadSkierowania();
		      //   C.getInstance().loadRecepty();
		      //   C.getInstance().loadWizytyDomowe();
		     //    tx.commit();
		     // } catch (HibernateException e) {
		     //    if (tx!=null) tx.rollback();
		     //    e.printStackTrace(); 
		    // } finally {
		      //   session.close(); 
		     // }
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
