package comp127.weather.widgets;

import comp127.weather.api.CurrentConditions;
import comp127.weather.api.WeatherData;
import edu.macalester.graphics.*;

/**
 * A widget that displays current wind direction and windspeed
 *
 * @author Long Truong
 */
public class WidgetPt5 implements WeatherWidget {
    private final double size;
    private double iconSize;
    private GraphicsGroup group;

    private GraphicsText label, label2;
    private GraphicsText description;
    private Ellipse windIcon;
    private Line windDir;

    /**
     * Creates a temperature widget of dimensions size x size.
     */
    public WidgetPt5(double size) {
        this.size = size;
        this.iconSize = size * 0.3;
        group = new GraphicsGroup();

        Ellipse windIcon = new Ellipse(0, 0, iconSize, iconSize);
        group.add(windIcon);

        label = new GraphicsText();
        label.setFont(FontStyle.BOLD, size * 0.1);
        group.add(label);

        label2 = new GraphicsText();
        label2.setFont(FontStyle.PLAIN, size*0.1);
        group.add(label2);
        label2.setText("Wind direction:");

        description = new GraphicsText();
        description.setFont(FontStyle.PLAIN, size * 0.05);
        group.add(description);

        updateLayout();
    }

    @Override
    public GraphicsObject getGraphics() {
        return group;
    }

    public void update(WeatherData data) {
        CurrentConditions currentConditions = data.getCurrentConditions();
        Double windDirection = currentConditions.getWindDirectionInDegrees();
        Double windSpeed = currentConditions.getWindSpeed();
        constructIcon(windDirection);

        // formula to convert wind speed to radian unit 
        

        // icon.setImagePath(currentConditions.getWeatherIcon());

        // Double cloudCoverage = currentConditions.getWindDirectionInDegrees();
        // label.setText(
        //     FormattingHelpers.formatOneDecimal(cloudCoverage) + "%");
        // if (cloudCoverage != null) {
        //     description.setText(cloudDescription(cloudCoverage));
        // }
        updateLayout();
    }

    private void constructIcon(double windDirection) {
        double pi = Math.PI;
        double myRad = pi/2 + -pi/2 * 90 * windDirection; 
        double centerX = windIcon.getCenter().getX();
        double centerY = windIcon.getCenter().getY();

        double radius = iconSize / 2 ;
        double x2 = centerX + radius * Math.cos(myRad);
        double y2 = centerY + radius * Math.sin(myRad);
        windDir = new Line(centerX, centerY, x2, y2);
    
        
    }

    // private String cloudDescription(double cc) {
    //     if (cc <= 33) {
    //         return "(Not so cloudy !)";
    //     }
    //     else if (cc <= 66) {
    //         return "(Looking gray today !)";
    //     }
    //     else {
    //         return "(Remember umbrella !!!)";
    //     }
    // }

    private void updateLayout() {
        // icon.setCenter(size * 0.5, size * 0.4);
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
