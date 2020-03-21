package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.address.model.pet.Food;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.Species;
import seedu.address.model.slot.Slot;

/**
 * Panel containing the overall statistics as charts and tables.
 */
public class OverallStats extends UiPart<Region> {
    private static final String FXML = "OverallStats.fxml";

    @FXML
    private PieChart petStats;

    /*@FXML
    private  Table  scheduleStats;*/

    @FXML
    private BarChart<String, Number> foodStats;

    public OverallStats(ObservableList<Pet> pets, ObservableList<Slot> slots) {
        super(FXML);
        setPetStats(pets);
        setFoodStats(pets);
    }

    public void setPetStats(ObservableList<Pet> pets) {
        petStats.setData(getPieChartData(pets));
        petStats.setLabelsVisible(false);
    }

    public ObservableList<PieChart.Data> getPieChartData(ObservableList<Pet> pets) {
        Map<Species, Integer> petsBySpecies = new HashMap<>();
        for (Pet p : pets) {
            if (!petsBySpecies.containsKey(p.getSpecies())) {
                petsBySpecies.put(p.getSpecies(), 0);
            }
            petsBySpecies.replace(p.getSpecies(), petsBySpecies.get(p.getSpecies()) + 1);
        }
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Species s : petsBySpecies.keySet()) {
            data.add(new PieChart.Data(s.toString(), petsBySpecies.get(s)));
        }
        return data;
    }

    public void setFoodStats(ObservableList<Pet> pets) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> tempFoodStats = new BarChart<>(xAxis, yAxis);
        tempFoodStats.getData().add(getBarChartData(pets));
        foodStats.setData(tempFoodStats.getData());
    }

    public XYChart.Series<String, Number> getBarChartData(ObservableList<Pet> pets) {
        XYChart.Series<String, Number> barChartData = new XYChart.Series<>();
        Map<String, Integer> listOfFood = new HashMap<>();
        for (Pet p : pets) {
            for (Food f : p.getFoodList()) {
                if (!listOfFood.containsKey(f.foodName)) {
                    listOfFood.put(f.foodName, 0);
                }
                listOfFood.replace(f.foodName, listOfFood.get(f.foodName) + f.foodAmount);
            }
        }
        for (String f : listOfFood.keySet()) {
            barChartData.getData().add(new XYChart.Data<>(f, listOfFood.get(f)));
        }
        return barChartData;
    }
}
