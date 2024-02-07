package com.pickyeaters.logic.view.gui.virtual;

import com.pickyeaters.logic.view.bean.DishIngredientBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.widget.IngredientListItemWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class VirtualShowIngredientView extends VirtualPaneView {
    @FXML
    protected VBox vboxIngredient;

    protected LinkedList<IngredientListItemWidget> ingredientListItemWidgets = new LinkedList<>();
    protected List<DishIngredientBean> ingredientBeanList = new ArrayList<>();

    protected VirtualShowIngredientView(String fxml, String resource, VirtualPaneView parent) {
        super(fxml, resource, parent);
    }

    protected void setupAddIngredient(DishIngredientBean bean) {
        ingredientBeanList.add(bean);
        ingredientListItemWidgets.add(new IngredientListItemWidget(this, bean));
        vboxIngredient.getChildren().add(ingredientListItemWidgets.getLast().getRoot());
    }
    protected void setupAddIngredient(String name, String cooked, String optional) {
        if(name != null) {
            boolean isCooked = false;
            boolean isOptional = false;
            if(cooked != null) {
                isCooked = cooked.equals("true");
            }
            if(optional != null) {
                isOptional = optional.equals("true");
            }

            setupAddIngredient(new DishIngredientBean(name, isCooked, isOptional));
        }
    }

    protected void setupRemoveIngredient(String name) {
        for(IngredientListItemWidget widget : ingredientListItemWidgets) {
            if(widget.getBean().getName().equals(name)) {
                vboxIngredient.getChildren().remove(widget.getRoot());
                ingredientListItemWidgets.remove(widget);
                return;
            }
        }
    }

    @FXML
    protected abstract void clickAddIngredient(ActionEvent event);
}
