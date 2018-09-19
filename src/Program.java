import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Program extends Application {
    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage theStage){
        theStage.setTitle("Hello World GUI!");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(700, 400);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image earth = new Image("world.gif");
        Image stars = new Image("stars.jpeg");
        gc.drawImage(stars, 0, 0);
        new AnimationTimer(){
            public void handle(long currentNanoTime){

                gc.drawImage(earth, 200, 100);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        Text text = new Text();
        text.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 48));
        text.setFill(Color.SKYBLUE);
        text.setStrokeWidth(2);
        text.setStroke(Color.BLACK);
        text.setText("Hello World!");
        text.setX(200); text.setY(70);
        root.getChildren().add(text);

        theStage.show();
    }
}
