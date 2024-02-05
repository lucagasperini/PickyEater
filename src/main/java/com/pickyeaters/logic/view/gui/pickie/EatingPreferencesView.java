package com.pickyeaters.logic.view.gui.pickie;

import com.pickyeaters.logic.controller.application.pickie.EatingPreferencesController;
import com.pickyeaters.logic.view.gui.VirtualPaneView;
import com.pickyeaters.logic.view.gui.virtual.VirtualShowIngredientView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Map;

public class EatingPreferencesView extends VirtualShowIngredientView {
    private final EatingPreferencesController controller = new EatingPreferencesController();
    public EatingPreferencesView(VirtualPaneView parent) {
        super("/form/pickie/EatingPreferences.fxml", parent);
    }

    @Override
    protected void setup(Map<String, String> arg) {
        if(arg != null) {
            setupAddIngredient(
                    arg.get("addIngredientName"),
                    arg.get("addIngredientCooked"),
                    arg.get("addIngredientOptional")
            );
            setupRemoveIngredient(arg.get("removeIngredient"));
        }
        showTitle("PICKY_PERSONALIZEEATINGPREFERENCES");
    }

    @FXML
    private Button buttonAddIngredient;

    @FXML
    private Button buttonSave;

    @FXML
    private CheckBox checkboxAllergen;

    @FXML
    private CheckBox checkboxHealthPregnant;

    @FXML
    private CheckBox checkboxLifestyleCarnivore;

    @FXML
    private CheckBox checkboxLifestylePescatarian;

    @FXML
    private CheckBox checkboxLifestyleVegan;

    @FXML
    private CheckBox checkboxLifestyleVegetarian;

    @FXML
    private CheckBox checkboxReligiousHalal;

    @FXML
    private CheckBox checkboxReligiousKosher;

    @FXML
    private Text textHealthAllergies;

    @FXML
    private Text textHealthNeeds;

    @FXML
    private Text textHealthOthers;

    @FXML
    private Text textLifeStyleNeeds;

    @FXML
    private Text textReligiousNeeds;

    @FXML
    private Text textTaste;

    @FXML
    private Text textTasteExplanation;

    @FXML
    private Text textTasteMyDislikedIngredients;

    @FXML
    private VBox vboxIngredient;

    @FXML
    protected void clickAddIngredient(ActionEvent event) {
        AddDislikedIngredientView view = new AddDislikedIngredientView(this);
        view.show();
    }

    @FXML
    void clickSaveChanges(ActionEvent event) {

    }

}
