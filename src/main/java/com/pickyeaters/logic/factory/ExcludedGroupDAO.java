package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.Allergy;
import com.pickyeaters.logic.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ExcludedGroupDAO {
    public List<Ingredient> getIngredientList(String groupID) {
        try {
            List<Ingredient> out = new ArrayList<>();
            DatabaseController.Query query = DatabaseController.getInstance().queryResultSet(
                    "SELECT name, cooked FROM all_excludedgroup_ingredient WHERE group_id = ?"
            );
            query.setString(groupID);
            query.execute();
            while(query.next()) {
                out.add(new Ingredient(
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
