package comp127.weather.widgets;

import comp127.weather.api.CurrentConditions;
import comp127.weather.api.WeatherData;
import edu.macalester.graphics.*;
import net.aksingh.owmjapis.CurrentWeather.Sys;

/**
 * A widget that displays the current wind speed and wind direction
 * author Long Truong
 */

 
public class WindWidget implements WeatherWidget {
    private final double size;
    private GraphicsGroup group;

    private GraphicsText label, label2;
    private GraphicsGroup iconGroup;
    private Line line;

    /**
     * Creates a cloud widget of dimensions size x size.
     */
    public WindWidget(double size) {
        this.size = size;

        group = new GraphicsGroup();

        iconGroup = new GraphicsGroup();
        group.add(iconGroup);
        Ellipse circle = new Ellipse(0, 0, size * 0.4, size * 0.4);
        line = new Line(0, 0, 0,0);
        iconGroup.add(circle);
        iconGroup.add(line);

        label = new GraphicsText();
        label.setFont(FontStyle.BOLD, size * 0.1);
        group.add(label);

        label2 = new GraphicsText();
        label2.setFont(FontStyle.PLAIN, size*0.1);
        group.add(label2);

        updateLayout();
    }

    @Override
    public GraphicsObject getGraphics() {
        return group;
    }
    /**
     * Update the data, icon line and set text
     */
    public void update(WeatherData data) {
        CurrentConditions Conditions = data.getCurrentConditions();
        Double windDir = Conditions.getWindDirectionInDegrees();
        Double windSpeed = Conditions.getWindSpeed();
        String windDir_string = Conditions.getWindDirectionAsString();

        label.setText(FormattingHelpers.formatOneDecimal(windSpeed)+ " mi/s");
        if (windDir_string == null) {
            label2.setText("-");
        }
        if (!( windDir == null || windSpeed == null )) {
            iconGroup.remove(line);
            label2.setText(windDir_string);
            makeIcon(windDir);
        }

        updateLayout();
    }

    /**
     * Draw the line for the icon to point wind direction
     */
    private void makeIcon(double windDir) {
        // Convert from wind direction in degree to radian in cartesian system
        double pi = Math.PI;
        double myRad = pi / 2  + -pi/2 / 90 * windDir; 
        
        // Draw the line indicating wind direction
        double radius = size * 0.4 /2;
        double center = iconGroup.getWidth()/2;
        double x2 = center + radius * Math.cos(myRad);
        double y2 =  center + radius * Math.sin(myRad) * -1 ;
        line = new Line(center, center, x2, y2);
        iconGroup.add(line);
    }


    /**
     * Put all the elements in correct positions
     */
    private void updateLayout() {
        iconGroup.setCenter(size * 0.5, size * 0.4);
        label.setCenter(size * 0.5, size * 0.8);
        label2.setCenter(size*0.5,size*0.7);
        // Place the description directly underneath the label 
    }

    @Override
    public void onHover(Point position) {
        // This widget is not interactive, so this method does nothing.    
    }
}
