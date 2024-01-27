package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.application.LoginController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.factory.UserDAO;
import com.pickyeaters.logic.model.User;
import com.pickyeaters.logic.view.VirtualView;
import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.view.gui.pickie.PickieHomeView;
import com.pickyeaters.logic.view.gui.restaurateur.RestaurateurHomeView;
import com.pickyeaters.logic.view.gui.administrator.AdministratorHomeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainView extends VirtualPaneView {
    public static final String backgroundView = "/backgroundTemplate.fxml";
    private Stage stage;
    public MainView(Stage primaryStage) {
        super(new MainController(), "/backgroundTemplate.fxml", null);
        this.stage = primaryStage;
        stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGTH));
        VirtualPaneView.setMainLayout(mainLayout);
    }
    private final String APP_NAME = "Picky Eater";
    private final int WINDOW_HEIGTH = 720;
    private final int WINDOW_WIDTH = 1280;

    @FXML
    private BorderPane mainLayout;
    @FXML
    private ImageView imageLogo;
    @FXML
    private Text textNavbarUser;
    @FXML
    private Text textNavbarWelcome;
    @FXML
    private MenuItem menuItemProfile;
    @FXML
    private MenuItem menuItemLogout;

    @Override
    public void show() {
        controller.start();
        stage.setTitle(APP_NAME);

        try {
            controller.getInitController().loadFromFile();
        } catch (SettingsControllerException | DatabaseControllerException ex) {
            InitView initView = new InitView(controller);
            initView.show();
        }

        showApp();
    }

    private void showApp() {
        LoginView loginView = new LoginView(controller);
        loginView.show();

        if(!controller.getLoginController().isAuth()) {
            return;
        }

        setup();
        showHomeView();

        stage.show();
    }
    @Override
    protected void setup() {
        textNavbarUser.setText(controller.getLoginController().getUser().getName());
        menuItemProfile.setText(SettingsController.i18n("PICKY_GUI_UPDATEPROFILE_TEXT"));
        menuItemLogout.setText(SettingsController.i18n("PICKY_GUI_LOGOFF_TEXT"));
    }

    private void showHomeView() {
        try {
            switch (controller.getLoginController().getUserType()) {
                case PICKIE -> showPickieHomeView();
                case RESTAURATEUR -> showRestaurateurHomeView();
                case ADMIN -> showAdministratorHomeView();
            }
        } catch (LoginControllerException e) {
            throw new RuntimeException(e);
        }
    }

    private void showPickieHomeView() {
        textNavbarWelcome.setText(SettingsController.i18n("PICKY_GUI_HELLO_TEXT"));
        PickieHomeView pickieHomeView = new PickieHomeView(controller, this);
        pickieHomeView.show();
    }

    private void showRestaurateurHomeView() {
        textNavbarWelcome.setText(SettingsController.i18n("RESTAURATEUR_GUI_HELLO_TEXT"));
        RestaurateurHomeView restaurateurHomeView = new RestaurateurHomeView(controller, this);
        restaurateurHomeView.show();
    }

    private void showAdministratorHomeView() {
        textNavbarWelcome.setText(SettingsController.i18n("ADMINISTRATOR_GUI_HELLO_TEXT"));
        AdministratorHomeView administratorHomeView = new AdministratorHomeView(controller, this);
        administratorHomeView.show();
    }

    @FXML
    protected void clickLogoImage() {
        showHomeView();
    }

    @FXML
    private void clickLogout(ActionEvent event) {
        controller.getLoginController().logout();
        stage.close();
        showApp();
    }
}
