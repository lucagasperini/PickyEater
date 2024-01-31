package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.pickie.ReviewDishController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;

import java.util.Map;

public class ReviewDishView extends VirtualPaneView {
    ReviewDishController controller;
    public ReviewDishView(ReviewDishController controller, VirtualPaneView parent) {
        super("/form/pickie/ReviewDish.fxml",parent);
        this.controller = controller;
    }

    @Override
    protected void setup(Map<String, String> arg) {
        throw new UnsupportedOperationException();
    }
}
