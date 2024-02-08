package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.Allergy;
import com.pickyeaters.logic.model.Ingredient;
import com.pickyeaters.logic.model.User;
import com.pickyeaters.logic.utils.QueryProcedure;
import com.pickyeaters.logic.utils.QueryResultSet;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class IngredientDAO {

    public Ingredient get(String name) throws DAOException {
        try {
            QueryProcedure query = DatabaseController.getInstance().queryProcedure(
                    "CALL get_ingredient(?, ?)"
            );
            query.setString(name);
            query.registerOutString();

            query.execute();
            Ingredient out = new Ingredient(query.getString(), name);
            query.close();
            return out;
        } catch (DatabaseControllerException e) {
            throw new DAOException(e);
        }
    }
    public IngredientForest getAll() throws DAOException{
        try {
            Deque<IngredientTuple> nodes = new LinkedList<>();
            Deque<Ingredient> roots = new LinkedList<>();
            QueryResultSet query = DatabaseController.getInstance().queryResultSet("SELECT * FROM all_ingredient");

            query.execute();
            while(query.next()) {
                IngredientTuple tmp = new IngredientTuple(
                        new Ingredient(
                                query.getString(),
                                query.getString()
                        ),
                        query.getString()
                );
                if(tmp.isRoot()) {
                    roots.push(tmp.ingredient);
                } else {
                    nodes.push(tmp);
                }
            }
            query.close();

            return toForest(roots, nodes);
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public List<Ingredient> getIngredientListOfDish(String dishID) throws DAOException {
        try {
            LinkedList<Ingredient> out = new LinkedList<>();
            QueryResultSet query = DatabaseController.getInstance().queryResultSet(
                    "SELECT name, cooked, optional FROM all_dish_ingredient WHERE dish_id = ?"
            );
            query.setString(dishID);

            query.execute();
            while(query.next()) {
                Ingredient i = new Ingredient(
                        query.getString(),
                        query.getBoolean(),
                        query.getBoolean()
                );
                out.add(i);
            }
            query.close();

            return out;
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public List<Ingredient> getExcludedIngredientList(String userID) throws DAOException {
        try {
            LinkedList<Ingredient> out = new LinkedList<>();
            QueryResultSet query = DatabaseController.getInstance().queryResultSet(
                    "SELECT name, cooked FROM all_user_excluded_ingredient WHERE userid = ?"
            );
            query.setString(userID);

            query.execute();
            while(query.next()) {
                Ingredient i = new Ingredient(
                        query.getString(),
                        query.getBoolean(),
                        false
                );
                out.add(i);
            }
            query.close();

            return out;
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public void addUserExcludedIngredient(Ingredient ingredient, User user) throws DAOException {
        try {
            QueryResultSet query = DatabaseController.getInstance().queryResultSet(
                    "CALL add_user_excluded_ingredient(?, ?)"
            );
            query.setString(user.getID());
            query.setString(ingredient.getName());

            query.execute();
            query.close();
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    private IngredientForest toForest(Deque<Ingredient> roots, Deque<IngredientTuple> nodes) {
        IngredientForest forest = new IngredientForest();
        while(!roots.isEmpty()) {
            forest.addRoot(roots.pop());
        }
        while(!nodes.isEmpty()) {
            IngredientTuple tuple = nodes.pop();
            if(!forest.addNode(tuple)) {
                nodes.addLast(tuple);
            }
        }
        return forest;
    }

    private class IngredientTuple {
        private final Ingredient ingredient;
        private final String parentID;

        public IngredientTuple(Ingredient ingredient, String parentID) {
            this.ingredient = ingredient;
            this.parentID = parentID;
        }

        public boolean isRoot() {
            return parentID == null;
        }
    }

    public class IngredientForest {
        LinkedList<IngredientTree> list = new LinkedList<>();
        public List<String> getPaths() {
            ArrayList<String> tmp = new ArrayList<>();
            ArrayList<String> out = new ArrayList<>();
            for(IngredientTree tree : list) {
                Deque<IngredientNode> nodeStack = new LinkedList<>();
                nodeStack.push(tree.getRoot());
                tmp.clear();
                while(!nodeStack.isEmpty()) {
                    IngredientNode node = nodeStack.pop();
                    tmp.add(node.toString());
                    if(node.getChild().isEmpty()) {
                        StringBuilder builder = new StringBuilder();
                        for(String i : tmp) {
                            builder.append(i);
                            builder.append(".");
                        }
                        out.add(builder.substring(0, builder.length() - 1));
                        tmp.remove(tmp.size() - 1);
                    }
                    for (IngredientNode i : node.getChild()) {
                        nodeStack.push(i);
                    }
                }
            }
            return out;
        }
        public void addRoot(Ingredient ingredient) {
            list.add(new IngredientTree(new IngredientNode(ingredient)));
        }

        public boolean addNode(IngredientTuple tuple) {
            for(IngredientTree tree : list) {
                IngredientNode node = tree.searchNode(tuple.parentID);
                if(node != null) {
                    node.addChild(new IngredientNode(tuple.ingredient));
                    return true;
                }
            }
            return false;
        }

        public List<IngredientTree> getTreeList() {
            return list;
        }
    }

    public class IngredientTree {
        private final IngredientNode root;
        public IngredientTree(IngredientNode root) {
            this.root = root;
        }

        public IngredientNode getRoot() {
            return root;
        }

        public IngredientNode searchNode(String id) {
            Deque<IngredientNode> stack = new LinkedList<>();
            stack.push(root);
            IngredientNode cur = null;
            while(!stack.isEmpty()) {
                cur = stack.pop();
                if (cur.hasID(id)) {
                    return cur;
                } else {
                    for (IngredientNode child : cur.getChild()) {
                        stack.push(child);
                    }
                }
            }
            return null;
        }
    }

    public class IngredientNode {
        private final Ingredient value;
        private final LinkedList<IngredientNode> child;

        public IngredientNode(Ingredient value) {
            this.value = value;
            this.child = new LinkedList<>();
        }

        public boolean hasID(String id) {
            return value.getID().equals(id);
        }

        public Ingredient getValue() {
            return value;
        }

        public List<IngredientNode> getChild() {
            return child;
        }

        public void addChild(IngredientNode node) {
            child.add(node);
        }

        @Override
        public String toString() {
            return value.getName();
        }
    }
}
