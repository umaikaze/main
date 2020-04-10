package clzzz.helper.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import clzzz.helper.commons.util.DateTimeUtil;
import clzzz.helper.model.foodcollection.FoodCollection;
import clzzz.helper.model.pet.Pet;
import clzzz.helper.model.pet.Species;
import clzzz.helper.model.slot.Slot;
import clzzz.helper.model.slot.SlotDuration;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Panel containing the overall statistics as charts and tables. The stats are displayed vertically.
 */
public class OverallStats extends UiPart<Region> {
    private static final String FXML = "OverallStats.fxml";

    @FXML
    private PieChart petStats;

    @FXML
    private BarChart<String, Number> foodStats;

    @FXML
    private Text petTitle;

    @FXML
    private Text foodTitle;

    @FXML
    private Text firstDay;

    @FXML
    private Text secondDay;

    @FXML
    private Text thirdDay;

    @FXML
    private Pane timeTablePlaceHolder;

    @FXML
    private HBox hbox;

    private ArrayList<Rectangle> rectangles;

    public OverallStats(ObservableList<Pet> pets, ObservableList<Slot> slots, ObservableList<FoodCollection> foodList) {
        super(FXML);
        setPaneProperty();;
        setPetStats(pets);
        setScheduleStats(slots);
        setFoodStats(foodList);
    }

    public void setPaneProperty() {
        //make sure size of timetable is same as charts
        hbox.prefWidthProperty().bind(this.getRoot().widthProperty().divide(2.5));
        timeTablePlaceHolder.prefWidthProperty().bind(this.getRoot().widthProperty().divide(2.5));
        //60 is slightly larger than the sum of height of header and text "Schedule"
        timeTablePlaceHolder.prefHeightProperty().bind(hbox.heightProperty().subtract(60));
    }

    public void setPetStats(ObservableList<Pet> pets) {
        petStats.setData(getPieChartData(pets));
        petStats.setLabelsVisible(false);
    }

    /**
     * Group the pets by their species and returns the count number as data for pie chart
     * @param pets list of pets
     * @return data for pie chart
     */
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

    /**
     * Fill up the time table in recent 3 days, each rectangle represents the time slots where there is an appointment.
     * @param slots The list of time slots
     */
    public void setScheduleStats(ObservableList<Slot> slots) {
        setHeader();
        rectangles = new ArrayList<>();
        getScheduleStats(slots);
        for (Rectangle rec : rectangles) {
            timeTablePlaceHolder.getChildren().add(rec);
        }
    }

    public void getScheduleStats(ObservableList<Slot> slots) {
        for (Slot s : slots) {
            if (s.getDate().equals(LocalDate.now())) {
                createRectangles(s.getTime(), s.getDuration(),
                        timeTablePlaceHolder.widthProperty().divide(10000000));
            } else if (s.getDate().equals(LocalDate.now().plusDays(1))) {
                createRectangles(s.getTime(), s.getDuration(),
                        timeTablePlaceHolder.widthProperty().divide(3));
            } else if (s.getDate().equals(LocalDate.now().plusDays(2))) {
                createRectangles(s.getTime(), s.getDuration(),
                        timeTablePlaceHolder.widthProperty().divide(3).multiply(2));
            }
        }
    }

    /**
     * Create rectangles which represents slots.
     * @param time start time of the appointment
     * @param duration duration of the appointment
     * @param xCoordinate x coordinate of the rectangle
     */
    public void createRectangles(LocalTime time, SlotDuration duration, DoubleBinding xCoordinate) {
        Rectangle rec = new Rectangle();
        //find start point of the rectangle
        long minutes = time.get(ChronoField.MINUTE_OF_DAY);
        //set position of the rectangles
        rec.xProperty().bind(xCoordinate);
        rec.yProperty().bind(timeTablePlaceHolder.heightProperty().multiply(minutes).divide(60 * 24));
        //set style
        rec.arcHeightProperty().bind(timeTablePlaceHolder.widthProperty().divide(10));
        rec.arcWidthProperty().bind(timeTablePlaceHolder.widthProperty().divide(10));
        rec.setFill(Paint.valueOf("#ff8000"));
        //merge all conflicted slots together.
        rec.setStrokeWidth(0);
        //set size of rectangle
        rec.widthProperty().bind(timeTablePlaceHolder.widthProperty().divide(3));
        rec.heightProperty().bind(timeTablePlaceHolder.heightProperty().multiply(duration.toMinutes()).divide(24 * 60));
        rectangles.add(rec);
    }

    /**
     * Set the header of time table to be recent dates.
     */
    public void setHeader() {
        String firstDayOfWeek = LocalDate.now().getDayOfWeek().toString().substring(0, 1)
                + LocalDate.now().getDayOfWeek().toString().substring(1).toLowerCase();
        String secondDayOfWeek = LocalDate.now().getDayOfWeek().plus(1).toString().substring(0, 1)
                + LocalDate.now().getDayOfWeek().plus(1).toString().substring(1).toLowerCase();
        String thirdDayOfWeek = LocalDate.now().getDayOfWeek().plus(2).toString().substring(0, 1)
                + LocalDate.now().getDayOfWeek().plus(2).toString().substring(1).toLowerCase();
        String date1 = LocalDate.now().format(DateTimeUtil.DATE_FORMAT);
        String date2 = LocalDate.now().plusDays(1).format(DateTimeUtil.DATE_FORMAT);
        String date3 = LocalDate.now().plusDays(2).format(DateTimeUtil.DATE_FORMAT);
        firstDay.setText(firstDayOfWeek.substring(0, 3) + "\n"
                + date1.substring(0, date1.length() - 5));
        secondDay.setText(secondDayOfWeek.substring(0, 3) + "\n"
                + date2.substring(0, date2.length() - 5));
        thirdDay.setText(thirdDayOfWeek.substring(0, 3) + "\n"
                + date3.substring(0, date3.length() - 5));
    }

    /**
     * create new bar chart and gather data
     * @param foodCollectionList list of food collections
     */
    public void setFoodStats(ObservableList<FoodCollection> foodCollectionList) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> tempFoodStats = new BarChart<>(xAxis, yAxis);
        tempFoodStats.getData().add(getBarChartData(foodCollectionList));
        foodStats.setData(tempFoodStats.getData());
    }

    /**
     * Get stats for food bar chart
     * @param foodCollectionList The list of all food collections
     * @return data for bar chart
     */
    public XYChart.Series<String, Number> getBarChartData(ObservableList<FoodCollection> foodCollectionList) {
        XYChart.Series<String, Number> barChartData = new XYChart.Series<>();
        for (FoodCollection f : foodCollectionList) {
            barChartData.getData().add(new XYChart.Data<>(
                    f.getName(), f.getAmount()));
        }
        return barChartData;
    }
}
