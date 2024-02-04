package com.pickyeaters.logic.controller.application;

import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.factory.IngredientDAO;
import com.pickyeaters.logic.model.Ingredient;
import com.pickyeaters.logic.view.bean.IngredientBean;
import com.pickyeaters.logic.view.bean.IngredientTreeBean;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class AddIngredientController extends VirtualController {
    public List<IngredientTreeBean> getIngrendientTreeList() throws ControllerException {
        List<IngredientTreeBean> out = new ArrayList<>();
        IngredientDAO.IngredientForest forest = IngredientDAO.getInstance().getAll();
        for(IngredientDAO.IngredientTree tree : forest.getTreeList()) {
            IngredientTreeBean treeBean = new IngredientTreeBean(
                    new IngredientBean(tree.getRoot().getValue())
            );
            out.add(treeBean);

            Deque<IngredientDAO.IngredientNode> nodeStack = new LinkedList<>();
            Deque<IngredientBean> beanStack = new LinkedList<>();
            beanStack.push(treeBean.getRoot());
            nodeStack.push(tree.getRoot());

            while(!nodeStack.isEmpty()) {
                IngredientDAO.IngredientNode node = nodeStack.pop();
                IngredientBean bean = beanStack.pop();
                for (IngredientDAO.IngredientNode i : node.getChild()) {
                    nodeStack.push(i);
                    IngredientBean child = new IngredientBean(i.getValue());
                    bean.addChild(child);
                    beanStack.push(child);
                }
            }
        }
        return out;
    }
}
