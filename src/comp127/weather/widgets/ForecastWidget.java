package comp127.weather.widgets;

import comp127.weather.api.ForecastConditions;
import comp127.weather.api.WeatherData;
import edu.macalester.graphics.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A widget that displays the weather forecast cloud 
 */

public class ForecastWidget implements WeatherWidget {

    private final double size;
    private GraphicsGroup group;

    private GraphicsText label;
    private GraphicsText label2;
    private GraphicsText dateDisplay;
    private GraphicsText timeDisplay;
    private GraphicsText description;
    private Image icon;

    private GraphicsGroup boxGroup;  // Holds all the ForecastBox objects

    private List<ForecastBox> boxes = new ArrayList<>();

    public ForecastWidget(double size) {
        this.size = size;

        group = new GraphicsGroup();

        // Create the various text and image elements 

        // Date and Time
        dateDisplay = new GraphicsText();
        dateDisplay.setFont(FontStyle.BOLD, size*0.08);
        group.add(dateDisplay);
        
        timeDisplay = new GraphicsText();
        timeDisplay.setFont(FontStyle.BOLD, size*0.08);
        group.add(timeDisplay);

        icon = new Image(0, 0);
        icon.setMaxWidth(size * 0.3);
        icon.setMaxHeight(size * 0.3);
        group.add(icon);

        // Temperature Conditions
        label = new GraphicsText();
        label.setFont(FontStyle.BOLD, size * 0.08);
        group.add(label);

        label2 = new GraphicsText();
        label2.setFont(FontStyle.PLAIN, size * 0.08);
        label2.setFillColor(Color.GRAY);
        group.add(label2);

        description = new GraphicsText();
        description.setFont(FontStyle.PLAIN, size * 0.05);
        group.add(description);

        boxGroup = new GraphicsGroup();
        group.add(boxGroup);

        updateLayout();
    }

    @Override
    public GraphicsObject getGraphics() {
        return group;
    }

    /** Update the timeline data */
    public void update(WeatherData data) {
        // Remove all the existing elements from boxGroup.
        boxGroup.removeAll();

        boxes.clear();  // Remove all the old ForecastBoxes from our list

        // Construct the timeline bar
        double count = 0;
        double dy = size * 0.005;
        double dx = size *0.04;
        for (ForecastConditions condition : data.getForecasts()) {
            count+=1;
            if (count>20) {
                count = 1;
                dy+= 0.065*size; 
            }
            ForecastBox box = new ForecastBox(condition, (count+1)*dx, dy, 0.03 * size, 0.045 *size );
            boxGroup.add(box);
            boxes.add(box);
        }
        // Call selectForecast for first box
        selectForecast(boxes.get(0));
    }
    /**
     * Set the boxes and set text/ image for label, description and icon
     * @param box
     */
    private void selectForecast(ForecastBox box) {
        // Colorize green for selected box
        for (ForecastBox boxItem : boxes) {
            boxItem.setActive(false);
        }
        box.setActive(true);

        // Update the text and icon
        ForecastConditions forecast = box.getForecast();

        icon.setImagePath(forecast.getWeatherIcon());
        description.setText(forecast.getWeatherDescription());

        label.setText(FormattingHelpers.formatOneDecimal(forecast.getTemperature()) + "\u2109");
        label2.setText(
            FormattingHelpers.formatOneDecimal(forecast.getMinTemperature()) + "\u2109" +
            " | " +
            FormattingHelpers.formatOneDecimal(forecast.getMaxTemperature()) + "\u2109" 
            );
        dateDisplay.setText(FormattingHelpers.formatDate(forecast.getPredictionTime()));
        timeDisplay.setText(FormattingHelpers.formatTime(forecast.getPredictionTime()));
        updateLayout();
    }
    
    /** Place all elements in correct positions */
    private void updateLayout() {
        boxGroup.setCenter(0.5 * size, 0.9 * size);

        icon.setCenter(0.5 * size, 0.3 * size);
        label.setCenter(0.5 * size, 0.5 * size);
        label2.setCenter(0.5 * size, 0.6 * size);
        description.setCenter(0.5 * size, 0.68*size);

        dateDisplay.setAlignment(TextAlignment.LEFT);
        dateDisplay.setPosition(0.05 * size, 0.1 * size);
        timeDisplay.setAlignment(TextAlignment.RIGHT);
        timeDisplay.setPosition(0.95 * size, 0.1 * size);
    }

    /**
     * Given a position in the widget, this returns the ForecastBox at that position if one exists
     *
     * @param location pos to check
     * @return null if not over a forecast box
     */
    private ForecastBox getBoxAt(Point location) {
        GraphicsObject obj = group.getElementAt(location);
        if (obj instanceof ForecastBox) {
            return (ForecastBox) obj;
        }
        return null;
    }

    /**
     * Updates the currently displayed forecast information as the mouse moves over the widget.
     * If there is not a ForecastBox at that position, the display does not change.
     */
    @Override
    public void onHover(Point position) {
        if (getBoxAt(position) != null) {
            selectForecast(getBoxAt(position));

        }
    }
}
