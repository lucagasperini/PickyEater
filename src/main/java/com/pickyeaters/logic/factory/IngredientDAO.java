package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.Ingredient;

import java.util.*;

public class IngredientDAO {
    private static IngredientDAO instance = new IngredientDAO();

    private IngredientDAO() {}

    public static IngredientDAO getInstance() {
        return instance;
    }

    public IngredientForest getAll() throws DAOException{
        try {
            Stack<IngredientTuple> nodes = new Stack<>();
            Stack<Ingredient> roots = new Stack<>();
            DatabaseController.Query query = DatabaseController.getInstance().queryResultSet("SELECT * FROM all_ingredient");

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

    private IngredientForest toForest(Stack<Ingredient> roots, Stack<IngredientTuple> nodes) {
        IngredientForest forest = new IngredientForest();
        while(!roots.empty()) {
            forest.addRoot(roots.pop());
        }
        while(!nodes.empty()) {
            IngredientTuple tuple = nodes.pop();
            if(!forest.addNode(tuple)) {
                nodes.insertElementAt(tuple, 0);
            }
        }
        return forest;
    }

    private class IngredientTuple {
        private Ingredient ingredient;
        private String parentID;

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

        public LinkedList<IngredientTree> getTreeList() {
            return list;
        }
    }

    public class IngredientTree {
        private IngredientNode root;
        public IngredientTree(IngredientNode root) {
            this.root = root;
        }

        public IngredientNode getRoot() {
            return root;
        }

        public IngredientNode searchNode(String id) {
            Stack<IngredientNode> stack = new Stack<>();
            stack.push(root);
            IngredientNode cur = null;
            while(!stack.empty()) {
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
        private Ingredient value;
        private LinkedList<IngredientNode> child;

        public IngredientNode(Ingredient value) {
            // TODO: CC?
            this.value = new Ingredient(value);
            this.child = new LinkedList<IngredientNode>();
        }

        public boolean hasID(String id) {
            return value.getID().equals(id);
        }

        public Ingredient getValue() {
            return value;
        }

        public LinkedList<IngredientNode> getChild() {
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
