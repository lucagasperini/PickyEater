package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.pickie.FindRestaurantController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.view.AppData;
import com.pickyeaters.logic.view.bean.CityBean;
import com.pickyeaters.logic.view.bean.RestaurantBean;
import com.pickyeaters.logic.view.bean.RestaurateurBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Map;

public class FindRestaurantView extends VirtualPaneView {
    private final FindRestaurantController controller = new FindRestaurantController();
    public FindRestaurantView(VirtualPaneView parent) {
        super("/form/pickie/FindRestaurant.fxml", "PICKY_FINDARESTAURANT", parent);
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    @Override
    protected void setup(Map<String, String> arg) {
        showTitle();

        try {
            List<CityBean> cityBeanList = controller.getCityList();
            for(CityBean city : cityBeanList) {
                comboboxCity.getItems().add(city.getName());
            }
        } catch (DAOException e) {
            showError(e);
        }
    }


    @FXML
    private Button buttonSearch;

    @FXML
    private CheckBox checkboxCategory1;

    @FXML
    private TableColumn<RestaurantBean, String> columnAddress;

    @FXML
    private TableColumn<RestaurantBean, String> columnName;

    @FXML
    private TableColumn<RestaurantBean, String> columnPhone;

    @FXML
    private ComboBox<String> comboboxCity;

    @FXML
    private TitledPane paneRestaurant;

    @FXML
    private TableView<RestaurantBean> tableRestaurant;

    @FXML
    private Text textCategory;

    @FXML
    private Text textCity;

    @FXML
    private Text textRestaurant;
    @FXML
    void clickButtonSearch(ActionEvent event) {
        tableRestaurant.getItems().clear();
        try {
            List<RestaurantBean> restaurantBeanList = controller.findRestaurant(
                    AppData.getInstance().getUser(),
                    new CityBean(comboboxCity.getValue())
            );
            for (RestaurantBean bean : restaurantBeanList) {
                tableRestaurant.getItems().add(bean);
            }
        } catch (DAOException e) {
            showError(e);
        }

    }

}
