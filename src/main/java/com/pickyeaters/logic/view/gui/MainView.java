package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.factory.IngredientDAO;
import com.pickyeaters.logic.model.Ingredient;
import com.pickyeaters.logic.view.gui.pickie.PickieHomeView;
import com.pickyeaters.logic.view.gui.restaurateur.RestaurateurHomeView;
import com.pickyeaters.logic.view.gui.administrator.AdministratorHomeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Stack;
import java.util.Vector;

public class MainView extends VirtualViewGUI {
    private Stage stage;
    private MainController controller = new MainController();
    StartView startView = new StartView();
    public MainView(Stage primaryStage) {
        super("/form/Background.fxml");
        this.stage = primaryStage;
        stage.setScene(new Scene(getRoot(), 1280, 720));
        VirtualPaneView.init(controller, this);
    }

    public BorderPane getMainLayout() {
        return mainLayout;
    }

    @FXML
    private BorderPane mainLayout;
    @FXML
    private ImageView imageLogo;
    @FXML
    private Text textNavbarUsername;
    @FXML
    private Text textNavbarWelcome;
    @FXML
    private MenuItem menuItemNavbarProfile;
    @FXML
    private MenuItem menuItemNavbarLogout;

    public void show() {
        VirtualPaneView.getMainController().start();
        mainLayout.getTop().setVisible(false);
        stage.setTitle("Picky Eater");

        try {
            VirtualPaneView.getMainController().getInit().loadFromFile();
        } catch (SettingsControllerException | DatabaseControllerException ex) {
            InitView initView = new InitView(VirtualPaneView.getMainController().getInit());
            initView.show();
        }

        stage.show();

        startView.show();
    }

    public void showNavbar() {
        mainLayout.getTop().setVisible(true);

        textNavbarUsername.setText(VirtualPaneView.getMainController().getLogin().getUser().getName());
        menuItemNavbarProfile.setText(SettingsController.i18n("NAVBAR_UPDATEPROFILE"));
        menuItemNavbarLogout.setText(SettingsController.i18n("NAVBAR_LOGOFF"));
    }

    public void showHomeView() {
        try {
            switch (controller.getLogin().getUserType()) {
                case PICKIE -> showPickieHomeView();
                case RESTAURATEUR -> showRestaurateurHomeView();
                case ADMIN -> showAdministratorHomeView();
            }
        } catch (LoginControllerException e) {
            throw new RuntimeException(e);
        }
    }

    private void showPickieHomeView() {
        //textNavbarWelcome.setText(SettingsController.i18n("PICKY_NAVBAR_HELLO"));
        PickieHomeView pickieHomeView = new PickieHomeView(
                VirtualPaneView.getMainController().getPickie(),
                startView
        );
        pickieHomeView.show();
    }

    private void showRestaurateurHomeView() {
        //textNavbarWelcome.setText(SettingsController.i18n("RESTAURATEUR_NAVBAR_HELLO"));
        RestaurateurHomeView restaurateurHomeView = new RestaurateurHomeView(
                controller.getRestaurateur(),
                startView
        );
        restaurateurHomeView.show();
    }

    private void showAdministratorHomeView() {
        //textNavbarWelcome.setText(SettingsController.i18n("ADMINISTRATOR_NAVBAR_HELLO"));
        AdministratorHomeView administratorHomeView = new AdministratorHomeView(
                controller.getAdministrator(),
                startView
        );
        administratorHomeView.show();
    }

    @FXML
    protected void clickImageLogo() {
        showHomeView();
    }

    @FXML
    private void clickMenuItemNavbarProfile(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

    @FXML
    private void clickMenuItemNavbarLogout(ActionEvent event) {
        controller.getLogin().logout();
        startView.show();
    }
}
