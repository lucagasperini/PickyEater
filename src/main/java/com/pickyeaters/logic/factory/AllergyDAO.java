package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.Allergy;
import com.pickyeaters.logic.model.ExcludedGroup;
import com.pickyeaters.logic.model.User;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AllergyDAO {
    public Allergy get(String allergyName) throws DAOException {
        try {
            DatabaseController.Query query = DatabaseController.getInstance().query(
                    "CALL get_allergy(?, ?)"
            );
            query.setString(allergyName);
            query.registerOutParameter(Types.VARCHAR);

            query.execute();
            Allergy out = new Allergy(query.getString(), allergyName);
            query.close();
            return out;
        } catch (DatabaseControllerException e) {
            throw new DAOException(e);
        }
    }
    public List<Allergy> getAll() throws DAOException {
        try {
            List<Allergy> out = new ArrayList<>();
            DatabaseController.Query query = DatabaseController.getInstance().queryResultSet("SELECT * FROM all_allergy");
            query.execute();
            while(query.next()) {
                out.add(new Allergy(
                        query.getString(),
                        query.getString()
                ));
            }
            return out;
        } catch (DatabaseControllerException e) {
            throw new DAOException(e);
        }
    }

    public List<Allergy> getUserAllergy(String userID) throws DAOException {
        try {
            List<Allergy> out = new ArrayList<>();
            DatabaseController.Query query = DatabaseController.getInstance().queryResultSet(
                    "SELECT allergy_id, allergy_name FROM all_user_allergy WHERE userid = ?"
            );
            query.setString(userID);
            query.execute();
            while(query.next()) {
                out.add(new Allergy(
                        query.getString(),
                        query.getString()
                ));
            }
            return out;
        } catch (DatabaseControllerException e) {
            throw new DAOException(e);
        }
    }

    public void addUserAllergy(Allergy allergy, User user) throws DAOException {
        try {
            DatabaseController.Query query = DatabaseController.getInstance().queryResultSet(
                    "CALL add_user_allergy(?, ?)"
            );
            query.setString(user.getID());
            query.setString(allergy.getID());

            query.execute();
            query.close();
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }
}
