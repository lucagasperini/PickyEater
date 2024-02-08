package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.Allergy;
import com.pickyeaters.logic.model.ExcludedGroup;
import com.pickyeaters.logic.model.Ingredient;
import com.pickyeaters.logic.model.User;
import com.pickyeaters.logic.utils.QueryProcedure;
import com.pickyeaters.logic.utils.QueryResultSet;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ExcludedGroupDAO {
    public ExcludedGroup get(String name) throws DAOException {
        String id = getID(name);
        List<Ingredient> ingredientList = getIngredientList(id);
        return new ExcludedGroup(id, name, ingredientList);
    }
    private String getID(String name) throws DAOException {
        try {
            QueryProcedure query = DatabaseController.getInstance().queryProcedure(
                    "CALL get_excluded_group_id(?,?)"
            );
            query.setString(name);
            query.registerOutString();
            query.execute();
            String id = query.getString();
            query.close();
            return id;
        }catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }
    private List<Ingredient> getIngredientList(String groupID) throws DAOException {
        try {
            List<Ingredient> out = new ArrayList<>();
            QueryResultSet query = DatabaseController.getInstance().queryResultSet(
                    "SELECT name, cooked FROM all_excludedgroup_ingredient WHERE group_id = ?"
            );
            query.setString(groupID);
            query.execute();
            while(query.next()) {
                out.add(new Ingredient(
                        query.getString(),
                        query.getBoolean(),
                        false
                ));
            }
            query.close();
            return out;
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public List<ExcludedGroup> getExcludedGroupOfUser(String userID) throws DAOException {
        try {
            List<ExcludedGroup> groupList = new ArrayList<>();
            QueryResultSet query = DatabaseController.getInstance().queryResultSet(
                    "SELECT groupid, groupname FROM all_user_excludedgroup WHERE userid = ?"
            );
            query.setString(userID);
            query.execute();
            while(query.next()) {
                String groupID = query.getString();
                String groupName = query.getString();
                groupList.add(new ExcludedGroup(
                        groupID,
                        groupName,
                        getIngredientList(groupID)
                ));
            }
            query.close();
            return groupList;
        }catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public void addUserExcludedGroup(ExcludedGroup group, User user) throws DAOException {
        try {
            QueryResultSet query = DatabaseController.getInstance().queryResultSet(
                    "CALL add_user_excluded_group(?, ?)"
            );
            query.setString(user.getID());
            query.setString(group.getID());

            query.execute();
            query.close();
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

}
