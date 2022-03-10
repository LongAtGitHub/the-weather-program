package comp127.weather.widgets;

import comp127.weather.api.CurrentConditions;
import comp127.weather.api.WeatherData;
import edu.macalester.graphics.*;

/**
 * A widget that displays the current cloud coverage
 *
 * @author Long Truong
 */
public class CloudWidget implements WeatherWidget {
    private final double size;
    private GraphicsGroup group;

    private GraphicsText label, label2;
    private GraphicsText description;
    private Image icon;

    /**
     * Creates a cloud widget of dimensions size x size.
     */
    public CloudWidget(double size) {
        this.size = size;

        group = new GraphicsGroup();

        icon = new Image(0, 0);
        icon.setMaxWidth(size * 0.3);
        icon.setMaxHeight(size * 0.3);
        group.add(icon);

        label = new GraphicsText();
        label.setFont(FontStyle.BOLD, size * 0.1);
        group.add(label);

        label2 = new GraphicsText();
        label2.setFont(FontStyle.PLAIN, size*0.1);
        group.add(label2);
        label2.setText("Cloud Coverage:");

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
     * Update the data and set text
     */
    public void update(WeatherData data) {
        CurrentConditions currentConditions = data.getCurrentConditions();

        icon.setImagePath(currentConditions.getWeatherIcon());

        Double cloudCoverage = currentConditions.getCloudCoverage();
        label.setText(
            FormattingHelpers.formatOneDecimal(cloudCoverage) + "%");
        if (cloudCoverage != null) {
            description.setText(cloudDescription(cloudCoverage));
        }
        updateLayout();
    }

    private String cloudDescription(double cc) {
        if (cc <= 33) {
            return "(Not so cloudy !)";
        }
        else if (cc <= 66) {
            return "(Looking gray today !)";
        }
        else {
            return "(Remember umbrella !!!)";
        }
    }

    /**
     * Put all the elements in correct positions
     */
    private void updateLayout() {
        icon.setCenter(size * 0.5, size * 0.4);
        label.setCenter(size * 0.5, size * 0.8);
        label2.setCenter(size*0.5,size*0.7);

        // Place the description directly underneath the label 
        description.setCenter(size * 0.5, size * 0.9);
    }

    @Override
    public void onHover(Point position) {
        // This widget is not interactive, so this method does nothing.    
    }
}
