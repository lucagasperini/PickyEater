package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.Allergy;

import java.util.ArrayList;
import java.util.List;

public class AllergyDAO {
    public List<Allergy> getAll() {
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
            throw new RuntimeException(e);
        }
    }
}
