package csci2020u.lab06;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class Main extends Application {



        // Bar chart sample data
        private static double[] avgHousingPricesByYear = {
                247381.0,264171.4,287715.3,294736.1, 308431.4,322635.9,340253.0,363153.7
        };
        private static double[] avgCommercialPricesByYear = {
                1121585.3,1219479.5,1246354.2,1295364.8,1335932.6,1472362.0,1583521.9,1613246.3
        };

        // Pie chart sample data
        private static String[] ageGroups = {
                "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
        };
        private static int[] purchasesByAgeGroup = {
                648, 1021, 2453, 3173, 1868, 2247
        };
        private static Color[] pieColours = {
                Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
        };

        @FXML
        Canvas mainCanvas;
        @FXML
        GraphicsContext gc;

        @FXML
        public void initialize() {
            gc = mainCanvas.getGraphicsContext2D();
            drawGraph(avgHousingPricesByYear, avgCommercialPricesByYear, 800, 600);
            drawPieGraph(purchasesByAgeGroup, pieColours, 800);
        }

        public void drawGraph(double[] data1, double[] data2, int w, int h) {
            // Offset between sets of bar chart data
            double dataPointOffset = 5.0;
            double xInc = (w / (data1.length + data2.length)) / 2;

            // Find the min and max
            double maxVal = data1[0];
            for (int i = 1; i < data1.length; i++) {
                if (data1[i] > maxVal) {
                    maxVal = data1[i];
                }
                if (data2[i] > maxVal) {
                    maxVal = data2[i];
                }
            }

            // Draw the bar chart

            for (int i = 0; i < data1.length; i++) {
                double height = (data1[i] / maxVal) * h;
                double x = (xInc * 2)*i;
                gc.setFill(Color.RED);
                gc.fillRect(x + dataPointOffset, (h - height )+100, xInc, height);
                height = (data2[i] / maxVal) * h * 0.85;
                gc.setFill(Color.BLUE);
                gc.fillRect(x + xInc + dataPointOffset, (h - height)+100, xInc, height);
                // Offset between sets of bar data
                dataPointOffset += 5;
            }
        }

        public void drawPieGraph(int[] purchases, Color[] colors, int w) {
            // stores angular arc extent
            double[] fraction = new double[purchases.length];

            // Get the total
            int total = 0;
            for (int i = 0; i < purchases.length; i++) {
                total += purchases[i];
            }

            // Get the fractions for each purchase
            for (int i = 0; i < purchases.length; i++) {
                fraction[i] = ((double) purchases[i] / (double) total) * 360;
            }

            // Draw the pie chart

            double startAngle = 0.0;
            for (int i = 0; i < purchases.length; i++) {
                gc.setFill(colors[i]);
                gc.fillArc(475,300,325,325,startAngle,fraction[i], ArcType.ROUND);
                startAngle += fraction[i];
            }
        }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Lab 6");
        primaryStage.setScene(new Scene(root, 850,800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}