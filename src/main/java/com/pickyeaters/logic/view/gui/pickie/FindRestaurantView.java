package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.pickie.FindRestaurantController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;

import java.util.Map;

public class FindRestaurantView extends VirtualPaneView {
    private final FindRestaurantController controller = new FindRestaurantController();
    public FindRestaurantView(VirtualPaneView parent) {
        super("/form/pickie/FindRestaurant.fxml", "", parent);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        throw new UnsupportedOperationException();
    }
}
