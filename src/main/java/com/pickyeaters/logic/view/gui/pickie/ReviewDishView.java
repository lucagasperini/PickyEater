package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.pickie.ReviewDishController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.utils.Grade;
import com.pickyeaters.logic.view.AppData;
import com.pickyeaters.logic.view.bean.CityBean;
import com.pickyeaters.logic.view.bean.DishBean;
import com.pickyeaters.logic.view.bean.RestaurantBean;
import com.pickyeaters.logic.view.bean.ReviewBean;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Map;

public class ReviewDishView extends VirtualPaneView {
    private Grade grade;
    private final ReviewDishController controller = new ReviewDishController();
    public ReviewDishView(VirtualPaneView parent) {
        super("/form/pickie/ReviewDish.fxml", "PICKY_REVIEWADISH", parent);
        try {
            List<CityBean> cityBeanList = controller.getCityList();
            for(CityBean city : cityBeanList) {
                comboboxCity.getItems().add(city);
            }
        } catch (DAOException e) {
            showError(e);
        }
        ToggleGroup toggleGroup = new ToggleGroup();

        grade = Grade.AVERAGE;
        radioAverage.fire();

        radioPoor.setToggleGroup(toggleGroup);
        radioFair.setToggleGroup(toggleGroup);
        radioAverage.setToggleGroup(toggleGroup);
        radioGood.setToggleGroup(toggleGroup);
        radioExcellent.setToggleGroup(toggleGroup);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        showTitle();
    }

    private void updateRadioButton(Grade newGrade) {
        grade = newGrade;
    }

    @FXML
    private Button buttonReport;

    @FXML
    private Button buttonSave;

    @FXML
    private ComboBox<CityBean> comboboxCity;

    @FXML
    private ComboBox<DishBean> comboboxDish;

    @FXML
    private ComboBox<RestaurantBean> comboboxRestaurant;

    @FXML
    private RadioButton radioAverage;

    @FXML
    private RadioButton radioExcellent;

    @FXML
    private RadioButton radioFair;

    @FXML
    private RadioButton radioGood;

    @FXML
    private RadioButton radioPoor;

    @FXML
    private Text textDish;

    @FXML
    private Text textDishHowGoodQuestion;

    @FXML
    private Text textDishWhatQuestion;

    @FXML
    private Text textReportQuestion;

    @FXML
    private Text textRestaurant;

    @FXML
    private Text textRestaurantWhatQuestion;

    @FXML
    private Text textRestaurantWhereQuestion;
    @FXML
    void changeComboBoxCity(ActionEvent event) {
        try {
            List<RestaurantBean> restaurantBeanList = controller.getRestaurantList(
                    comboboxCity.getValue()
            );
            for(RestaurantBean restaurant : restaurantBeanList) {
                comboboxRestaurant.getItems().add(restaurant);
            }
        } catch (DAOException e) {
            showError(e);
        }
    }

    @FXML
    void changeComboBoxRestaurant(ActionEvent event) {
        try {
            List<DishBean> dishBeanList = controller.getDishList(
                    comboboxRestaurant.getValue()
            );
            for(DishBean dish : dishBeanList) {
                comboboxDish.getItems().add(dish);
            }
        } catch (DAOException | BeanException e) {
            showError(e);
        }
    }
    @FXML
    void clickButtonReport(ActionEvent event) {
        if(comboboxDish.getValue() == null) {
            showError("NO_DISH_PROVIDED");
        } else {
            ReportDishView view = new ReportDishView(this, comboboxDish.getValue());
            view.show();
        }
    }

    @FXML
    void clickButtonSave(ActionEvent event) {
        try {
            controller.addReview(
                    new ReviewBean(comboboxDish.getValue().getID(), grade),
                    AppData.getInstance().getUser()
            );
        } catch (DAOException e) {
            showError(e);
        }
    }

    @FXML
    void clickRadioAverage(ActionEvent event) {
        updateRadioButton(Grade.AVERAGE);
    }

    @FXML
    void clickRadioExcellent(ActionEvent event) {
        updateRadioButton(Grade.EXCELLENT);
    }

    @FXML
    void clickRadioFair(ActionEvent event) {
        updateRadioButton(Grade.FAIR);
    }

    @FXML
    void clickRadioGood(ActionEvent event) {
        updateRadioButton(Grade.GOOD);
    }

    @FXML
    void clickRadioPoor(ActionEvent event) {
        updateRadioButton(Grade.POOR);
    }
}
