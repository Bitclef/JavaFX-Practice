import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Program extends Application {
    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage theStage){
        theStage.setTitle("Canvas Example");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(450, 250);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.SKYBLUE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font( "Georgia", FontWeight.BOLD, 48);
        gc.setFont(theFont);
        gc.fillText("Hello World!", 60, 50);
        gc.strokeText("Hello World!", 60, 50);

        Image earth = new Image("world.png");
        gc.drawImage(earth, 180, 75);

        theStage.show();
    }
}
