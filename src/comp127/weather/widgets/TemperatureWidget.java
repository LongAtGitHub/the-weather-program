package comp127.weather.widgets;

import comp127.weather.api.CurrentConditions;
import comp127.weather.api.WeatherData;
import edu.macalester.graphics.*;

/**
 * A widget that displays the current temperature, and the current conditions as an icon and a string.
 *
 * @author Original version created by by Daniel Kluver on 10/6/17.
 */
public class TemperatureWidget implements WeatherWidget {
    private final double size;
    private GraphicsGroup group;

    private GraphicsText label;
    private GraphicsText description;
    private Image icon;

    /**
     * Creates a temperature widget of dimensions size x size.
     */
    public TemperatureWidget(double size) {
        this.size = size;

        group = new GraphicsGroup();

        icon = new Image(0, 0);
        icon.setMaxWidth(size * 0.4);
        icon.setMaxHeight(size * 0.4);
        group.add(icon);

        label = new GraphicsText();
        label.setFont(FontStyle.BOLD, size * 0.1);
        group.add(label);

        description = new GraphicsText();
        description.setFont(FontStyle.PLAIN, size * 0.05);
        group.add(description);

        updateLayout();
    }

    @Override
    public GraphicsObject getGraphics() {
        return group;
    }

    /**
     * Update the the icon, label, and description
     */
    public void update(WeatherData data) {
        CurrentConditions currentConditions = data.getCurrentConditions();

        icon.setImagePath(currentConditions.getWeatherIcon());

        label.setText(
            FormattingHelpers.formatOneDecimal(currentConditions.getTemperature())
             + "\u2109");  // degree symbol

        description.setText(currentConditions.getWeatherDescription());
        updateLayout();
    }

    /** 
     * Put all elements in correct position
     */
    private void updateLayout() {
        icon.setCenter(size * 0.5, size * 0.5);
        label.setCenter(size * 0.5, size * 0.8);

        // Place the description directly underneath the label 
        description.setCenter(size * 0.5, size * 0.9);
    }

    @Override
    public void onHover(Point position) {
        // This widget is not interactive, so this method does nothing.    
    }
}
