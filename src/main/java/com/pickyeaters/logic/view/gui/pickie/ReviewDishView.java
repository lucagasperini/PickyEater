package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.pickie.ReviewDishController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;

import java.util.Map;

public class ReviewDishView extends VirtualPaneView {
    private final ReviewDishController controller = new ReviewDishController();
    public ReviewDishView(VirtualPaneView parent) {
        super("/form/pickie/ReviewDish.fxml",parent);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        throw new UnsupportedOperationException();
    }
}
