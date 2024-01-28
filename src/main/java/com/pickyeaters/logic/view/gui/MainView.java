package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
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

public class MainView extends VirtualPaneView {
    private MainController controller = new MainController();
    public static final String backgroundView = "/backgroundTemplate.fxml";
    private Stage stage;
    public MainView(Stage primaryStage) {
        super("/backgroundTemplate.fxml", null);
        this.stage = primaryStage;
        stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGTH));
        VirtualPaneView.init(controller, mainLayout);
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
        getMainController().start();
        stage.setTitle(APP_NAME);

        try {
            getMainController().getInit().loadFromFile();
        } catch (SettingsControllerException | DatabaseControllerException ex) {
            InitView initView = new InitView(getMainController());
            initView.show();
        }

        showApp();
    }

    private void showApp() {
        LoginView loginView = new LoginView(getMainController());
        loginView.show();

        if(!getMainController().getLogin().isAuth()) {
            return;
        }

        setup();
        showHomeView();

        stage.show();
    }
    @Override
    protected void setup() {
        textNavbarUser.setText(getMainController().getLogin().getUser().getName());
        menuItemProfile.setText(SettingsController.i18n("PICKY_NAVBAR_UPDATEPROFILE"));
        menuItemLogout.setText(SettingsController.i18n("PICKY_NAVBAR_LOGOFF"));
    }

    private void showHomeView() {
        try {
            switch (getMainController().getLogin().getUserType()) {
                case PICKIE -> showPickieHomeView();
                case RESTAURATEUR -> showRestaurateurHomeView();
                case ADMIN -> showAdministratorHomeView();
            }
        } catch (LoginControllerException e) {
            throw new RuntimeException(e);
        }
    }

    private void showPickieHomeView() {
        textNavbarWelcome.setText(SettingsController.i18n("PICKY_NAVBAR_HELLO"));
        PickieHomeView pickieHomeView = new PickieHomeView(
                controller.getPickie(),
                this
        );
        pickieHomeView.show();
    }

    private void showRestaurateurHomeView() {
        textNavbarWelcome.setText(SettingsController.i18n("RESTAURATEUR_HELLO_TEXT"));
        RestaurateurHomeView restaurateurHomeView = new RestaurateurHomeView(
                controller.getRestaurateur(),
                this
        );
        restaurateurHomeView.show();
    }

    private void showAdministratorHomeView() {
        textNavbarWelcome.setText(SettingsController.i18n("ADMINISTRATOR_GUI_HELLO_TEXT"));
        AdministratorHomeView administratorHomeView = new AdministratorHomeView(
                controller.getAdministrator(),
                this
        );
        administratorHomeView.show();
    }

    @FXML
    protected void clickLogoImage() {
        showHomeView();
    }

    @FXML
    private void clickLogout(ActionEvent event) {
        controller.getLogin().logout();
        stage.close();
        showApp();
    }
}
