package seedu.address.ui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.pet.FoodCollection;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.Species;
import seedu.address.model.slot.Slot;

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

    private List<Rectangle> rectangles;

    public OverallStats(ObservableList<Pet> pets, ObservableList<Slot> slots, ObservableList<FoodCollection> foodList) {
        super(FXML);
        timeTablePlaceHolder.getChildren().clear();
        setPetStats(pets);
        hbox.prefWidthProperty().bind(this.getRoot().widthProperty().divide(2.5));
        setScheduleStats(slots);
        setFoodStats(foodList);
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
                createRectangles(s.getDateTime(), s.getDuration(), 0, false);
            } else if (s.getDate().equals(LocalDate.now().plusDays(1))) {
                createRectangles(s.getDateTime(), s.getDuration(),
                        timeTablePlaceHolder.getWidth() / 3.0, false);
            } else if (s.getDate().equals(LocalDate.now().plusDays(2))) {
                createRectangles(s.getDateTime(), s.getDuration(),
                        2.0 * timeTablePlaceHolder.getWidth() / 3.0, true);
            }
        }
    }

    /**
     * Create rectangles which represents appointments.
     * @param time start time of the appointment
     * @param duration duration of the appointment
     * @param xCoordinate x coordinate of the rectangle
     * @param isLastDay if the date is thrid day from now
     */
    public void createRectangles(LocalDateTime time, Duration duration, double xCoordinate, boolean isLastDay) {
        Rectangle rec = new Rectangle();
        long minutes = ChronoUnit.MINUTES.between(time,
                time.toLocalDate().plusDays(1).atStartOfDay());
        //set position of the rectangles
        rec.setX(xCoordinate);
        rec.setY(((double) minutes) / (60.0 * 24.0) * timeTablePlaceHolder.getHeight());
        //set style
        rec.setArcHeight(timeTablePlaceHolder.getWidth() / 10.0);
        rec.setArcWidth(timeTablePlaceHolder.getWidth() / 10.0);
        rec.setFill(Paint.valueOf("#ff8000"));
        //merge all conflicted slots together.
        rec.setStrokeWidth(0);
        //set size of rectangle
        rec.setWidth(timeTablePlaceHolder.getWidth() / 3.0);
        double height = duration.toMinutes() / (24.0 * 60.0) * timeTablePlaceHolder.getHeight();
        if (height > rec.getY()) {
            //consider the case where the appointment involves more than 1 day.
            rec.setHeight(rec.getY());
            if (!isLastDay) {
                if (time.toLocalDate().equals(LocalDate.now().plusDays(2))) {
                    createRectangles(time.toLocalDate().plusDays(1).atStartOfDay(),
                            Duration.ofMinutes((long) (1 - rec.getY() / height) * 24 * 60),
                            xCoordinate + timeTablePlaceHolder.getWidth() / 3.0, true);
                } else {
                    createRectangles(time.toLocalDate().plusDays(1).atStartOfDay(),
                            Duration.ofMinutes((long) (1 - rec.getY() / height) * 24 * 60),
                            xCoordinate + timeTablePlaceHolder.getWidth() / 3.0, false);
                }
            }
        } else {
            rec.setHeight(height);
        }
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
                + date1.substring(0, date2.length() - 5));
        thirdDay.setText(thirdDayOfWeek.substring(0, 3) + "\n"
                + date1.substring(0, date3.length() - 5));
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
                    f.getFoodCollectionName(), f.getFoodCollectionAmount()));
        }
        return barChartData;
    }
}
