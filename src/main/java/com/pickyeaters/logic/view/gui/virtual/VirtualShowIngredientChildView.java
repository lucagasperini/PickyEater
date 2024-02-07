package com.pickyeaters.logic.view.gui.virtual;

import com.pickyeaters.logic.controller.application.IngredientController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.view.bean.IngredientBean;
import com.pickyeaters.logic.view.bean.IngredientTreeBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;

import java.util.*;

public abstract class VirtualShowIngredientChildView extends VirtualPaneView {

    final IngredientController controller = new IngredientController();

    protected VirtualShowIngredientChildView(String fxml, String resource, VirtualPaneView parent) {
        super(fxml,resource, parent);
    }

    protected void setupTreeIngredient() throws ControllerException {
        TreeItem<String> treeIngredientRoot = new TreeItem<>(i18nGlobal("RESTAURATEUR_ADDINGREDIENT_INGREDIENT"));

        List<IngredientTreeBean> treeBeanList = controller.getIngrendientTreeList();
        for(IngredientTreeBean tree : treeBeanList) {
            Deque<IngredientBean> beanStack = new LinkedList<>();
            Deque<TreeItem<String>> treeItemStack = new LinkedList<>();
            beanStack.push(tree.getRoot());
            TreeItem<String> subRoot = new TreeItem<>(tree.getRoot().getName());
            treeIngredientRoot.getChildren().add(subRoot);
            treeItemStack.push(subRoot);
            while(!beanStack.isEmpty()) {
                IngredientBean bean = beanStack.pop();
                TreeItem<String> item = treeItemStack.pop();
                for(IngredientBean i : bean.getChildList()) {
                    beanStack.push(i);
                    TreeItem<String> childItem = new TreeItem<>(i.getName());
                    treeItemStack.push(childItem);
                    item.getChildren().add(childItem);
                }
            }
        }

        treeIngredient.setRoot(treeIngredientRoot);
    }

    @FXML
    protected TreeView<String> treeIngredient;
    @FXML
    protected CheckBox checkBoxCooked;



}
