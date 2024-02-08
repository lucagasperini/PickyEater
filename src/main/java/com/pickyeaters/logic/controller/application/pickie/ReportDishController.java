package com.pickyeaters.logic.controller.application.pickie;

import com.pickyeaters.logic.controller.application.VirtualController;
import com.pickyeaters.logic.factory.ReportDAO;
import com.pickyeaters.logic.view.bean.UserBean;

public class ReportDishController extends VirtualController {
    private final ReportDAO reportDAO = new ReportDAO();

    public void addReportMissingIngredient() {
        throw new UnsupportedOperationException();
    }
}
