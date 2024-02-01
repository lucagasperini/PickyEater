package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.view.gui.pickie.PickieHomeView;
import com.pickyeaters.logic.view.gui.restaurateur.RestaurateurHomeView;
import com.pickyeaters.logic.view.gui.administrator.AdministratorHomeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainView extends VirtualViewGUI {
    private final Stage stage;
    private final MainController controller = new MainController();
    private final StartView startView = new StartView();

    private final Node nodeHeader;
    public MainView(Stage primaryStage) {
        super("/form/Background.fxml");
        this.stage = primaryStage;
        stage.setScene(new Scene(getRoot(), 1280, 720));
        nodeHeader = mainLayout.getTop();
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
    @FXML
    private Button buttonBack;
    @FXML
    protected Text textTitle;
    @FXML
    protected Text textSubtitle;

    public void show() {
        stage.setTitle("Picky Eater");
        buttonBack.setText(SettingsController.i18n("BACK"));

        stage.show();

        startView.show();
    }

    public void hideHeader() {
        mainLayout.setTop(null);
    }
    public void showHeader() {
        mainLayout.setTop(nodeHeader);
        buttonBack.setVisible(true);
        textTitle.setVisible(true);
        textSubtitle.setVisible(true);
    }

    public void showNavbar() {
        textNavbarUsername.setText(VirtualPaneView.getMainController().getLogin().getUser().getName());
        menuItemNavbarProfile.setText(SettingsController.i18n("NAVBAR_UPDATEPROFILE"));
        menuItemNavbarLogout.setText(SettingsController.i18n("NAVBAR_LOGOFF"));
    }

    public void showTitle(String title, String subtitle) {
        showHeader();
        textTitle.setText(title);
        textSubtitle.setText(subtitle);
    }
    public void showBack() {
        showHeader();
    }

    public void hideTitle() {
        textTitle.setVisible(false);
        textSubtitle.setVisible(false);
    }

    public void hideBack() {
        buttonBack.setVisible(false);
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
                controller.getPickie(),
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

    @FXML
    private void clickButtonBack(ActionEvent event) {
        VirtualPaneView.getActiveView().showParent();
    }
}
