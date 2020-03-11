package application;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//siemka
//costam
//Jeszcze jedno
//asdasdasdasdsadasd
public class Centrala {
	private static Centrala instance;
	//private List<Lekarz> lekarze = new ArrayList<Lekarz>();
	private ArrayList<Pacjent> pacjenci = new ArrayList<Pacjent>();
	private List<Recepta> recepty = new ArrayList<Recepta>();
	private List<Dyzury> dyzury = new ArrayList<Dyzury>();
	private List<Skierowanie> skierowania = new ArrayList<Skierowanie>();
	private List<Wizyta> wizyty = new ArrayList<Wizyta>();
	//relacja jeden do jegnego. Ogolnie jest to typ wizyty
	private List<Wizyta> wizyty_domowe = new ArrayList<Wizyta>();
	private List<CennikWizytDomowych> cennik_wizyt_domowych = new ArrayList<CennikWizytDomowych>();
	private List<PracownicyInformacje>pracownicy_informacje_lekarze = new ArrayList<PracownicyInformacje>();
	private List<PracownicyInformacje>pracownicy_informacje_pielegniarki = new ArrayList<PracownicyInformacje>();
	public Centrala() {
	
	}
	
	public static Centrala getInstance() {
        if (instance == null) {
            instance = new Centrala();
        }
        
        return instance;
    }
	public List<Pacjent> pacjenci()
	{
		return this.pacjenci();
	}
	public PracownicyInformacje danyLekarz(int id)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    PracownicyInformacje lekarz = null;
	      try {
	         tx = session.beginTransaction();
	         Query query = session.createQuery("FROM PracownicyInformacje P WHERE P.id = :id");
	         query.setParameter("id", id);
	         List wynik = query.list();
	         for (Iterator iterator = wynik.iterator(); iterator.hasNext();){
	            lekarz = (PracownicyInformacje) iterator.next(); 
	         }	
	         tx.commit();
	        
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return lekarz;
	}
	public Skierowanie addSkierowanie(PracownicyInformacje pi, Pacjent p, String cel, String opis)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Skierowanie s = null;
		try{
			tx = session.beginTransaction();
			s = new Skierowanie();
			s.setCel_skierowania(cel);
			s.setOpis_skierowania(opis);
			s.setLekarz(pi.getLekarz());
			s.setPesel_pacjenta(p);
			session.save(s);
			tx.commit();
		}
		catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
		return s;
		
	}
	public Dyzury addDyzur(PracownicyInformacje PI, String dzien, Timestamp godz_rozp, Timestamp godz_zak)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Dyzury d = null;
		try
			{
			tx = session.beginTransaction();
			d = new Dyzury();
			d.setId_pracownika(PI);
			d.setDzien_tygodnia(dzien);
			d.setGodzina_rozpoczecia(godz_rozp);
			d.setGodzina_zakonczenia(godz_zak);
			session.save(d);
			tx.commit();
			}
		catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
		return d;
	}
	public Dyzury updateDyzur(int id, PracownicyInformacje PI, String dzien, Timestamp godz_rozp, Timestamp godz_zak)
	{
		 Session session = HibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      Dyzury cwd = null;
	      try {
	         tx = session.beginTransaction();
	         cwd = (Dyzury) session.get(Dyzury.class, id);
	         cwd.setDzien_tygodnia(dzien);
	         cwd.setGodzina_rozpoczecia(godz_rozp);
	         cwd.setGodzina_zakonczenia(godz_zak);
	         cwd.setId_pracownika(PI);
	         session.update(cwd); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return cwd;
	}
	///³adowanie pacjentow danego lekarza//
	public List<Pacjent> loadPacjenciLekarza(PracownicyInformacje l)
	{
		List<Pacjent> pacjenci1 = new ArrayList<Pacjent>();
		Iterator it_wizyty = wizyty.iterator();
		 System.out.println("loadPacjenciLekarza(");
		String pesel = null;
		while(it_wizyty.hasNext())
		{
			System.out.println("loadPacjenciLekarza pierwsza petla");
			Wizyta w = (Wizyta) it_wizyty.next();
			if(w.getLekarz().getId_lekarza() == l.getId())
			{
					System.out.println(pacjenci1.contains(w.getPesel_pacjenta()));
					if(pacjenci1.contains(w.getPesel_pacjenta()))
					{
						pesel = w.getPesel_pacjenta().getPesel();
						break;
					}
					else pacjenci1.add(w.getPesel_pacjenta());
			}
			
		}
		it_wizyty = wizyty_domowe.iterator();
		while(it_wizyty.hasNext())
		{System.out.println("loadPacjenciLekarza druga petla");
			Wizyta w = (Wizyta) it_wizyty.next();
			if(w.getLekarz().getId_lekarza() == l.getId())
			{
					if(pesel.equals(w.getPesel_pacjenta().getPesel()))
						break;
					else pacjenci1.add(w.getPesel_pacjenta());
			}
			
		}
		
		return pacjenci1;
	}
	///////////////Recepty load////////////
	public List<Recepta> loadRecepty()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         List <Object> recepty1 = session.createQuery("FROM Recepta R WHERE R.pesel_pacjenta is not null").list(); 
	         for (Iterator iterator = recepty1.iterator(); iterator.hasNext();){
	            Recepta recepty11 = (Recepta) iterator.next(); 
	            recepty.add(recepty11);
	           // System.out.println(recepty11.getOpis());
	         }	
	         tx.commit();
	        
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      
		return recepty;
	}
	public List<Pacjent> loadPacjenci()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      System.out.println("loadPacjenci(");
	      try {
	         tx = session.beginTransaction();
	         List <Object> employees = session.createQuery("FROM Pacjent").list(); 
	         for (Iterator iterator = employees.iterator(); iterator.hasNext();){
	            Pacjent employee = (Pacjent) iterator.next(); 
	            pacjenci.add(employee);

	         }
	         tx.commit();
	        
	      } catch (HibernateException e) {
	         //if (tx!=null) tx.rollback();
	         session.flush();
	         System.out.println(" ");
	        // e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
		return pacjenci;
	}
	//public Pacjent loadPacjentPesel(String )
	//Wczytanie dyzurow
	public List<Dyzury> loadDyzury()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         List <Object> dyzury1 = session.createQuery("FROM Dyzury D").list(); 
	           // System.out.println(dyzury1.size());
	         for (Iterator iterator = dyzury1.iterator(); iterator.hasNext();){
	            Dyzury dyzor = (Dyzury) iterator.next(); 
	            dyzury.add(dyzor);

	         }
	         tx.commit();
	        
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	    	  session.flush();
	         session.close(); 
	      }
		return dyzury;
	}
	public List<Wizyta> loadWizyty()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         final Timestamp timeStamp =
					    Timestamp.valueOf(
					        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ")
					        .format(new java.util.Date()) // get the current date as String
					              // and append the time
					    );
	          Query query = session.createQuery("SELECT W FROM Wizyta W WHERE W.data >= :timeStamp AND"
	          		+ " W.id NOT IN (SELECT WD.id_wizyty FROM WizytyDomowe WD) ");
	          query.setParameter("timeStamp", timeStamp);
	          List <Object> wizyty1 = query.list();
	        // System.out.println("Ilosc wizyt: "+ wizyty1.size());
	         for (Iterator iterator = wizyty1.iterator(); iterator.hasNext();){
	            Wizyta wizyta = (Wizyta) iterator.next(); 
	           
	            wizyty.add(wizyta);
	         }
	         tx.commit();
	       
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
		return wizyty;
	}
	///////////////Skierowania load////////////

	public List<Skierowanie> loadSkierowania()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         List <Object> skierowania1 = session.createQuery("SELECT S FROM Skierowanie S WHERE S.pesel_pacjenta is not null").list(); 
	        //Tutaj? System.out.println(skierowania1.size());
	         for (Iterator iterator = skierowania1.iterator(); iterator.hasNext();){
	            Skierowanie skierowania11 = (Skierowanie) iterator.next(); 
	            //System.out.println(skierowania11.getPesel_pacjenta().getPesel());
	            skierowania.add(skierowania11);
	         }
	         tx.commit();
	        
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	    	  session.flush();
	         session.close(); 
	      }
		return skierowania;
	}

	public List<Wizyta> loadWizytyDomowe()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         final Timestamp timeStamp =
					    Timestamp.valueOf(
					        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ")
					        .format(new java.util.Date()) // get the current date as String
					              // and append the time
					    );
	          Query query = session.createQuery("SELECT W FROM WizytyDomowe WD, Wizyta W WHERE WD.id_wizyty = W.id AND W.data >= :timeStamp ");
	          query.setParameter("timeStamp", timeStamp);
	          List <Object> wizyty_domowe1 = query.list();
	         //System.out.println("Wizyty domowe");
	         for (Iterator iterator = wizyty_domowe1.iterator(); iterator.hasNext();){
	            Wizyta wizyta = (Wizyta) iterator.next(); 
	            wizyty_domowe.add(wizyta);
	         }
	         
	         tx.commit();
	        
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	    	 // session.save(wizyty_domowe);
	         session.close(); 
	      }
	      //session.save(wizyty_domowe);
		return wizyty_domowe;
	}
	public List<CennikWizytDomowych> loadCennikWizytDomowych()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         List cennik = session.createQuery("FROM CennikWizytDomowych ORDER BY typ_wizyty ASC").list(); 
	         for (Iterator iterator = cennik.iterator(); iterator.hasNext();){
	            CennikWizytDomowych cennik1 = (CennikWizytDomowych) iterator.next(); 
	            cennik_wizyt_domowych.add(cennik1);

	         }
	         tx.commit();
	        
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
		return cennik_wizyt_domowych;
	}
	public List<PracownicyInformacje> loadLekarze()
	{
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	     Transaction tx = null;
	      try {
	    	  
	         tx = session.beginTransaction();
	         List <Object> lekarze = session.createQuery("SELECT PI FROM PracownicyInformacje PI join fetch PI.lekarz").list();
	        	 for (Iterator iterator = lekarze.iterator(); iterator.hasNext();){
	        		 PracownicyInformacje lekarz1 = (PracownicyInformacje) iterator.next(); 
	        		 System.out.println("Z load lekarze");
	        		 pracownicy_informacje_lekarze.add(lekarz1);

	         }
	         tx.commit();
	        
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
		return pracownicy_informacje_lekarze;
	}
	public List<PracownicyInformacje> loadPielegniarki()
	{
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	     Transaction tx = null;
	      try {
	    	  
	         tx = session.beginTransaction();
	         List <Object> pielegniarki = session.createQuery("SELECT PI FROM  PracownicyInformacje PI join fetch PI.pielegniarka").list(); 
        	 for (Iterator iterator = pielegniarki.iterator(); iterator.hasNext();){
        		 PracownicyInformacje pielegniarka1 = (PracownicyInformacje) iterator.next(); 
        		 //System.out.println(lekarz1.getLekarz().getSala());
        		 pracownicy_informacje_pielegniarki.add(pielegniarka1);


	         }
	         tx.commit();
	        
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
		return pracownicy_informacje_pielegniarki;
	}
	///////////////////////CUD CENNIK////////////////////////////
	public CennikWizytDomowych addCennikWizytDomowych(int cena, String opis)
	{
		  Session session = HibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      CennikWizytDomowych cwd = null;
	      try {
	         tx = session.beginTransaction();
	         cwd = new CennikWizytDomowych(cena, opis);
	         session.save(cwd); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return cwd;
	}
	
	public CennikWizytDomowych updateCennikWizytDomowych(Integer CennikID, int cena, String opis)
	{
		  Session session = HibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      CennikWizytDomowych cwd = null;
	      try {
	         tx = session.beginTransaction();
	         cwd = (CennikWizytDomowych) session.get(CennikWizytDomowych.class, CennikID);
	         cwd.setCena(cena);
	         cwd.setOpis(opis);
	         session.update(cwd); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return cwd;
	}
	//////////////////CUD PIELEGNIARKA///////////////////////////////////
	public PracownicyInformacje addPielegniarka(String imie, String nazwisko, int wiek, String telefon, boolean czy_stazystka)
	{
		  Session session = HibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      PracownicyInformacje pi = null;
	      Pielegniarka pie = null;
	      tx = session.beginTransaction();
	      try {

		        
		         pi = new PracownicyInformacje();
		         pie = new Pielegniarka();
		         pi.setImie(imie);
		         pi.setNazwisko(nazwisko);
		         pi.setTelefon(telefon);
		         pi.setWiek(wiek);
		         pie.setCzy_stazystka(czy_stazystka);
		         pi.setPielegniarka(pie);
		         pie.setPracownicy_informacje(pi);
		         session.save(pi); 
		         tx.commit();
	      } catch (NullPointerException | HibernateException e) {
	         if (tx!=null) tx.rollback();
	         
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return pi;
	}
	public PracownicyInformacje updatePielegniarka(Integer EmployeeID,String imie, String nazwisko, int wiek, String telefon, boolean czy_stazystka ){
	      Session session = HibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      PracownicyInformacje pracownica = null;
	      try {
	         tx = session.beginTransaction();
	         pracownica = (PracownicyInformacje)session.get(PracownicyInformacje.class, EmployeeID); 
	         Pielegniarka pielegniarka = (Pielegniarka)session.get(Pielegniarka.class, EmployeeID);
	         pracownica.setImie(imie);
	         pracownica.setNazwisko(nazwisko);
	         pracownica.setTelefon(telefon);
	         pracownica.setWiek(wiek);
	         pielegniarka.setCzy_stazystka(czy_stazystka);    
			 session.update(pracownica); 
			 session.update(pielegniarka);
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return pracownica;
	   }
	////////////CUD LEKARZ//////////////////////
	public PracownicyInformacje addLekarz(String login, String haslo, String imie, String nazwisko, int wiek, String telefon, int sala)
	{
		  Session session = HibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      PracownicyInformacje pi = null;
	      Lekarz l = null;
	      tx = session.beginTransaction();
	      try {

		        
		         pi = new PracownicyInformacje();
		         l = new Lekarz();
		         pi.setLogin(login);
		         pi.setHaslo(haslo);
		         pi.setImie(imie);
		         pi.setNazwisko(nazwisko);
		         pi.setTelefon(telefon);
		         pi.setWiek(wiek);
		         l.setSala(sala);
		         pi.setLekarz(l);
		         l.setPracownicy_informacje(pi);
		         session.save(pi); 
		         tx.commit();
	      } catch (NullPointerException | HibernateException e) {
	         if (tx!=null) tx.rollback();
	         
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return pi;
	}

	public PracownicyInformacje updateLekarz(Integer EmployeeID,String login, String haslo, String imie, String nazwisko,
		int wiek, String telefon, int sala ){
	      Session session = HibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      PracownicyInformacje pracownik = null;
	      try {
	         tx = session.beginTransaction();
	         pracownik = (PracownicyInformacje)session.get(PracownicyInformacje.class, EmployeeID); 
	         Lekarz lekarz = (Lekarz)session.get(Lekarz.class, EmployeeID);
	         pracownik.setImie(imie);
	         pracownik.setNazwisko(nazwisko);
	         pracownik.setTelefon(telefon);
	         pracownik.setWiek(wiek);
	         pracownik.setLogin(login);
	         pracownik.setHaslo(haslo);
	         lekarz.setSala(sala); 
			 session.update(pracownik); 
			 session.update(lekarz);
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return pracownik;
	   }  
	////////////////CUD PACJENT//////////////////////
	public Pacjent addPacjent(String pesel, String imie, String nazwisko, int wiek, String ulica, int numer_domu, int numer_mieszkania,
			String miejscowosc, String kod_pocztowy)
	{
		  Session session = HibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      Pacjent pi = null;
	      tx = session.beginTransaction();
	      try {		
	    	  	 pi = new Pacjent(pesel);
	    	  	// pi.setPesel(pesel);
		         pi.setImie(imie);
		         pi.setNazwisko(nazwisko);
		         pi.setWiek(wiek);
		         pi.setUlica(ulica);
		         pi.setKod_pocztowy(kod_pocztowy);
		         pi.setMiejscowosc(miejscowosc);
		         pi.setNr_domu(numer_domu);
		         pi.setNr_mieszkania(numer_mieszkania);
		         session.save(pi); 
		         tx.commit();
	      } catch (NullPointerException | HibernateException e) {
	         if (tx!=null) tx.rollback();
	         
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return pi;
	}
	public Pacjent updatePacjent(String pesel, String imie, String nazwisko, int wiek, String ulica, int numer_domu, int numer_mieszkania,
			String miejscowosc, String kod_pocztowy)
	{
		  Session session = HibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      Pacjent pi = null;
	      tx = session.beginTransaction();
	      try {		
	    	  	 pi = (Pacjent) session.get(Pacjent.class, pesel);
	    	  	// pi.setPesel(pesel);
	    	  	 pi.setPesel(pesel);
		         pi.setImie(imie);
		         pi.setNazwisko(nazwisko);
		         pi.setWiek(wiek);
		         pi.setUlica(ulica);
		         pi.setKod_pocztowy(kod_pocztowy);
		         pi.setMiejscowosc(miejscowosc);
		         pi.setNr_domu(numer_domu);
		         pi.setNr_mieszkania(numer_mieszkania);
		         session.update(pi); 
		         tx.commit();
	      } catch (NullPointerException | HibernateException e) {
	         if (tx!=null) tx.rollback();
	         
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return pi;
	}
	///Usun lekarza////
	public void usunLekarza(PracownicyInformacje l)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
	         tx = session.beginTransaction();
	         PracownicyInformacje employee = (PracownicyInformacje)session.get(PracownicyInformacje.class, l.getId()); 
	         session.delete(employee); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null)
	        	 {tx.rollback();
	         //System.out.println("tuta0j XD");
	         e.printStackTrace(); 
	        	 }
	      } finally {
	         session.close(); 
	      }	
		
			for(Iterator<Recepta> iter = recepty.iterator(); iter.hasNext();) {
				System.out.println("Hejooooooooooooo");
				Recepta a = iter.next();
				if(a.getLekarz().getId_lekarza() == l.getId()) 
					iter.remove();
					
				
			}
			for(Iterator<Skierowanie> iter = skierowania.iterator(); iter.hasNext();) {
				Skierowanie a = iter.next();
				if(a.getLekarz().getId_lekarza() == l.getId())
					iter.remove();
					
				
			}
			for(Iterator<Wizyta> iter = wizyty.iterator(); iter.hasNext();) {
				Wizyta a = iter.next();
				if(a.getLekarz().getId_lekarza() == l.getId()) 
					iter.remove();
					
				
			}
			for(Iterator<Dyzury> iter = dyzury.iterator(); iter.hasNext();) {
				Dyzury a = iter.next();
				if(a.getId_pracownika().getId() == l.getId()) 
					iter.remove();
		
				
			}
			for(Iterator<PracownicyInformacje> iter = pracownicy_informacje_lekarze.iterator(); iter.hasNext();)
			{
				PracownicyInformacje a = iter.next();
				if(a.getLekarz().getId_lekarza() == l.getId()) 
					iter.remove();
			}
			
	}
	//Usun dany dyzur///
	public void usunDyzur(Dyzury d)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
	         tx = session.beginTransaction();
	         Dyzury employee = (Dyzury)session.get(Dyzury.class, d.getId_dyzuru()); 
	         session.delete(employee); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null)
	        	 {tx.rollback();
	         //System.out.println("tuta0j XD");
	         e.printStackTrace(); 
	        	 }
	      } finally {
	         session.close(); 
	      }		
	}
	//Usun pielegniarke//
	public void usunPielegniarke(PracownicyInformacje l)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
	         tx = session.beginTransaction();
	         PracownicyInformacje employee = (PracownicyInformacje)session.get(PracownicyInformacje.class, l.getId()); 
	         session.delete(employee); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null)
	        	 {tx.rollback();
	        // System.out.println("tuta0j XD");
	         e.printStackTrace(); 
	        	 }
	      } finally {
	         session.close(); 
	      }		
			for(Iterator<PracownicyInformacje> iter = pracownicy_informacje_pielegniarki.iterator(); iter.hasNext();)
			{
				PracownicyInformacje a = iter.next();
				if(a.getId() == l.getId()) 
					iter.remove();
			}
			for(Iterator<Dyzury> iter = dyzury.iterator(); iter.hasNext();)
			{
				Dyzury a = iter.next();
				if(a.getId_pracownika().getId() == l.getId()) 
					iter.remove();
			}
			
	}
	////Usun pacjenta
	public void usunPacjenta(Pacjent p)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
	         tx = session.beginTransaction();
	         System.out.println("z usun pacjenta");
	         Pacjent employee = (Pacjent)session.get(Pacjent.class, p.getPesel()); 
	    
	         session.delete(employee); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null)
	        	 {tx.rollback();
	         System.out.println("tuta0j XD");
	         e.printStackTrace(); 
	        	 }
	      } finally {
	         session.close(); 
	      }
		System.out.println("DALEJ XD");
		
		for(Iterator<Recepta> iter = recepty.iterator(); iter.hasNext();) {
			Recepta a = iter.next();
			if(a.getPesel_pacjenta().getPesel().equals(recepty.contains(p.getPesel()))) 
				iter.remove();
				
			
		}
			for(Iterator<Skierowanie> iter = skierowania.iterator(); iter.hasNext();) {
				Skierowanie a = iter.next();
				if(a.getPesel_pacjenta().getPesel().equals(skierowania.contains(p.getPesel()))) 
					iter.remove();
					
				
			}
			for(Iterator<Wizyta> iter = wizyty.iterator(); iter.hasNext();) {
				Wizyta a = iter.next();
				if(a.getPesel_pacjenta().getPesel().equals(wizyty.contains(p.getPesel()))) 
					iter.remove();
					
				
			}
			for(Iterator<Pacjent> iter = pacjenci.iterator(); iter.hasNext();)
			{
				Pacjent a = iter.next();
				if(a.getPesel().equals(wizyty.contains(p.getPesel())))
					iter.remove();
			}
			
	}
	public Recepta addRecepta(PracownicyInformacje l, Pacjent p,String opis)
	{
		 Session session = HibernateUtil.getSessionFactory().openSession();
	     Transaction tx = null;
	     Recepta w = null;
	      
	      try {
	         tx = session.beginTransaction();
	         w = new Recepta();
	         w.setLekarz(l.getLekarz());
	         w.setOpis(opis);
	         w.setPesel_pacjenta(p);
	         session.save(w);
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return w;

	}
	///////////////////////////DODAWANIE WIZYTY//////////////////////////
	public Wizyta addWizyta(PracownicyInformacje PI, Pacjent P, Timestamp data, String opis)
	{
		 Session session = HibernateUtil.getSessionFactory().openSession();
	     Transaction tx = null;
	     Wizyta w = null;
	      
	      try {
	         tx = session.beginTransaction();
	         w = new Wizyta();
	         w.setLekarz(PI.getLekarz());
	         w.setPesel_pacjenta(P);
	         w.setOpis(opis);
	         w.setData(data);
	         session.save(w);
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return w;

	}
	/////////////////EDYTOWANIE WIZYTY/////////////////
	public Wizyta updateWizyta(int id, PracownicyInformacje PI, Pacjent P, Timestamp data, String opis)
	{
		 Session session = HibernateUtil.getSessionFactory().openSession();
	     Transaction tx = null;
	     Wizyta w = null;
	      
	      try {
	         tx = session.beginTransaction();
	         w = (Wizyta) session.get(Wizyta.class, id);
	         w.setLekarz(PI.getLekarz());
	         w.setPesel_pacjenta(P);
	         w.setOpis(opis);
	         w.setData(data);
	         session.update(w);
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return w;

	}
	/////////////////Usuwanie WIZYTY/////////////////
	public void usunWizyte(Wizyta w)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
	         tx = session.beginTransaction();
	         //System.out.println("z usun pacjenta");
	         Wizyta wiz = (Wizyta)session.get(Wizyta.class, w.getId()); 
	         session.delete(wiz); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null)
	        	 {tx.rollback();
	         System.out.println("tuta0j XD");
	         e.printStackTrace(); 
	        	 }
	      } finally {
	         session.close(); 
	      }
		for(Iterator<Wizyta> iter = wizyty.iterator(); iter.hasNext();) {
			Wizyta a = iter.next();
			if(a.getId() == w.getId()) 
				iter.remove();
				
			
		}
	}
	//////////CUD WIZYTY DOMOWE/////////////////////
	public Wizyta addWizytyDomowe(PracownicyInformacje PI, Pacjent P, Timestamp data, String opis, CennikWizytDomowych cwd)
	{
		 Session session = HibernateUtil.getSessionFactory().openSession();
	     Transaction tx = null;
	     Wizyta w = null;
	     WizytyDomowe wd = null;
	      
	      try {
	         tx = session.beginTransaction();
	         wd = new WizytyDomowe();
	         w = new Wizyta();
	         w.setLekarz(PI.getLekarz());
	         w.setPesel_pacjenta(P);
	         w.setOpis(opis);
	         w.setData(data);
	         wd.setTyp_wizyty(cwd);
	         w.setWizyty_domowe(wd);
	         wd.setWizyta(w);
	         session.save(w);
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return w;

	}
	public Wizyta updateWizytyDomowe(int id, PracownicyInformacje PI, Pacjent P, Timestamp data, String opis, CennikWizytDomowych cwd)
	{
		 Session session = HibernateUtil.getSessionFactory().openSession();
	     Transaction tx = null;
	     Wizyta w = null;
	     WizytyDomowe wd = null;
	      try {
	    	  
	         tx = session.beginTransaction();
	         w = (Wizyta) session.get(Wizyta.class, id);
	         wd = (WizytyDomowe) session.get(WizytyDomowe.class, id);
	         w.setLekarz(PI.getLekarz());
	         w.setPesel_pacjenta(P);
	         w.setOpis(opis);
	         w.setData(data);
	         wd.setTyp_wizyty(cwd);
	         session.update(w);
	         session.save(wd);
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return w;

	}
///////////////usun wizute domowa////
	public void usunWizyteDomowa(Wizyta w)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
	         tx = session.beginTransaction();
	         //System.out.println("z usun pacjenta");
	         Wizyta wiz = (Wizyta)session.get(Wizyta.class, w.getId()); 
	         session.delete(wiz); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null)
	        	 {tx.rollback();
	         //System.out.println("tuta0j XD");
	         e.printStackTrace(); 
	        	 }
	      } finally {
	         session.close(); 
	      }
		for(Iterator<Wizyta> iter = wizyty.iterator(); iter.hasNext();) {
			Wizyta a = iter.next();
			if(a.getId() == w.getId()) 
				iter.remove();
		}
		for(Iterator<Wizyta> iter = wizyty_domowe.iterator(); iter.hasNext();) {
			Wizyta a = iter.next();
			if(a.getId() == w.getId()) 
				iter.remove();
		}
			
	}

	
	public ArrayList<Pacjent> getPacjenci() {
		return pacjenci;
	}
	public List<Recepta> getRecepty() {
		return recepty;
	}
	public List<Skierowanie> getSkierowania() {
		return skierowania;
	}
	public List<Wizyta> getWizyty() {
		return wizyty;
	}
	public List<PracownicyInformacje> getLekarze()
	{
		return pracownicy_informacje_lekarze;
	}
	public String Logowanie(String login, String haslo)
	{
		
		if(login == null || haslo == null)
		{
			return "";
		}
		if(login.equals("admin") && haslo.equals("admin"))
		{
			return login;
		}
		Centrala.getInstance().loadLekarze();
		for (PracownicyInformacje a : pracownicy_informacje_lekarze)
		{
			System.out.println(a.getLogin() + " : "+a.getHaslo() + "z Logowanie");
			if(a.getLogin().equals(login) && a.getHaslo().equals(haslo))
				return login;
		}
		return "";
	}
	/*
	public void removeLekarz(int index) {
		

		for(Iterator<Recepta> iter = recepty.iterator(); iter.hasNext();) {
			Recepta a = iter.next();
			if(a.getId_lekarza()==lekarze.get(index).getId()) 
				iter.remove();
				
			
		}
			for(Iterator<Skierowanie> iter = skierowania.iterator(); iter.hasNext();) {
				Skierowanie a = iter.next();
				if(a.getId_lekarza()==lekarze.get(index).getId()) 
					iter.remove();
					
				
			}
			for(Iterator<Wizyta> iter = wizyty.iterator(); iter.hasNext();) {
				Wizyta a = iter.next();
				if(a.getId_lekarza()==lekarze.get(index).getId()) 
					iter.remove();
					
				
			}
			
			lekarze.remove(index);

	}
	*/
	public void addPacjent(Pacjent e) {
		pacjenci.add(e);
	}
	public void removePacjent(int index) {
		pacjenci.remove(index);
	}

	public void addRecepta(Recepta e) {
		recepty.add(e);
	}
	public void removeRecepta(int index) {
		recepty.remove(index);
	}
	public void addSkierowanie(Skierowanie e) {
		skierowania.add(e);
	}
	public void removeSkierowanie(int index) {
		skierowania.remove(index);
	}
	public void addWizyta(Wizyta e) {
		wizyty.add(e);
	}
	public void removeWizyta(int index) {
		wizyty.remove(index);
	}
	
}
