package com.pickyeaters.logic.view.cli.administrator;

import com.pickyeaters.logic.view.cli.VirtualRequestView;

import java.util.Map;

public class DishReportView extends VirtualRequestView {
    protected DishReportView(String viewName, String resource) {
        super("", "");
    }

    @Override
    protected boolean request(String request) {
        return false;
    }

    @Override
    protected String requestHelp() {
        return null;
    }

    @Override
    public void show(Map<String, String> arg) {
        throw new UnsupportedOperationException();
    }
}
