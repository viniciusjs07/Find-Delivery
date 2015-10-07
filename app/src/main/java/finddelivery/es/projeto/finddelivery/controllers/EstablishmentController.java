package finddelivery.es.projeto.finddelivery.controllers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import finddelivery.es.projeto.finddelivery.database.DAOEstablishment;
import finddelivery.es.projeto.finddelivery.models.Establishment;

/**
 * Created by Computação on 12/08/2015.
 */
public class EstablishmentController {

    private static DAOEstablishment establishmentDAO;
    private static EstablishmentController instance;
    private List<Establishment> establishmentsByName;
    private List<Establishment> establishmentsBySpeciality;
    private List<Establishment> myEstablishments = new ArrayList<>();

    public static boolean isSearchAdvanced = false;
    public static boolean isIsSearchAdvancedByName = true;

    // Construtor
    public static EstablishmentController getInstance(Context context) {
        if (instance == null) {
            instance = new EstablishmentController();
            establishmentDAO = new DAOEstablishment(context);

        }
        return instance;
    }

    // insert
    public void insert(Establishment establishment, String idUser) throws Exception {
        establishmentDAO.insert(establishment, idUser);
    }

    //update
    public void update(Establishment establishment, String idUser) throws Exception {
        establishmentDAO.update(establishment, idUser);
    }

    // listar estabelecimentos
    public List<Establishment> findAll() throws Exception {
        return establishmentDAO.findAll();
    }

    public Establishment getEstablishment(String name) {
        return establishmentDAO.findByName(name);
    }

    public List<Establishment> listMyEstablishments(String idUser) throws Exception {
        return establishmentDAO.findByUser(idUser);
    }


    //listar estabelecimentos encontrados pelo nome
    public List<Establishment> listByName() {
        return establishmentsByName;
    }

    public List<Establishment> listBySpeciality() {
        return establishmentsBySpeciality;
    }



    public void insertByName(String name) throws Exception {
        establishmentsByName = new ArrayList<>();
        for (Establishment est: findAll()) {
            if (est.getName().equals(name)) {
                establishmentsByName.add(est);
            }
        }
    }

    public void insertBySpeciality(String speciality) throws Exception {
        establishmentsBySpeciality = new ArrayList<>();
        for (Establishment est: findAll()) {
            if (est.getSpeciality().equals(speciality)) {
                establishmentsBySpeciality.add(est);
            }
        }
    }

    // Valida nome do estabelecimento
    public boolean validatesEstablishmentName(String name) throws Exception {
        if(name == null || name.trim().equals("")){
            return false;
        }
        for (Establishment establishment : findAll()){
            if (establishment.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    // valida o cadastro do estabelecimento
    public boolean validatesEstablishmentAddress (String address) throws  Exception {
        if (address == null || address.trim().equals("")) {
            return false;
        }
        return  true;
    }

    public boolean validatesEstablishmentBusinesseHour (String businessHour) throws  Exception {
        if (businessHour == null || businessHour.trim().equals("")) {
            return false;
        }
        return  true;
    }

    public  boolean validateEstablishmentPhone (String phone) throws Exception{
        return phone.matches(".([0][1-9][1-9].)[6-9][0-9]{3}-[0-9]{4}") || phone.matches(".([0][1-9][1-9].)[2-5][0-9]{3}-[0-9]{4}");
    }



}
