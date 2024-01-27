package com.pickyeaters.logic.controller.application.pickie;

import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.application.VirtualController;

public class PickieController extends VirtualController {
    private FindRestaurantController findRestaurantController = new FindRestaurantController(main);
    public PickieController(MainController main) {
        super(main);
    }

    public FindRestaurantController getFindRestaurantController() {
        return findRestaurantController;
    }
}
