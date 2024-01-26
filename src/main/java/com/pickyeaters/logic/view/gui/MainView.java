package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.application.LoginController;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainView extends VirtualView {
    public static final String backgroundView = "/backgroundTemplate.fxml";
    private Stage stage;
    private Parent root;
    private URL fxml = null;
    public MainView(Stage primaryStage) {
        super(new MainController());
        this.fxml = getClass().getResource(backgroundView);
        this.stage = primaryStage;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.fxml);
            loader.setController(this);
            this.root = loader.load();
        } catch (IOException ex) {
            System.err.println("[FXML] FATAL ERROR: " + ex.getMessage());
            //TODO:
            System.exit(-1);
        }
    }
    private final String APP_NAME = "Picky Eater";
    private final int WINDOW_HEIGTH = 720;
    private final int WINDOW_WIDTH = 1280;

    @FXML
    protected BorderPane mainLayout;
    @FXML
    protected ImageView imageLogo;
    @FXML
    protected Text textNavbarUser;
    @FXML
    protected Text textNavbarWelcome;

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

        LoginView loginView = new LoginView(controller);
        loginView.show();

        if(!controller.getLoginController().isAuth()) {
            return;
        }

        textNavbarUser.setText(controller.getLoginController().getUser().getName());

        showHomeView();

        stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGTH));
        stage.show();
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
        PickieHomeView pickieHomeView = new PickieHomeView(controller, mainLayout);
        pickieHomeView.show();
    }

    private void showRestaurateurHomeView() {
        RestaurateurHomeView restaurateurHomeView = new RestaurateurHomeView(controller, mainLayout);
        restaurateurHomeView.show();
    }

    private void showAdministratorHomeView() {
        AdministratorHomeView administratorHomeView = new AdministratorHomeView(controller, mainLayout);
        administratorHomeView.show();
    }

    @FXML
    protected void clickLogoImage() {
        showHomeView();
    }
}
