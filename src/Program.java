import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

public class Program extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage theStage) throws InvalidMidiDataException, IOException, MidiUnavailableException {
        theStage.setTitle("Martin's World!");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(700, 400);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image earth = new Image("pictures/world.gif");
        Image stars = new Image("pictures/stars.jpeg");


        final int[] x = {200};
        final int[] y = {100};

        theScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                y[0] -= 2;
            } else if (event.getCode() == KeyCode.DOWN) {
                y[0] += 2;
            } else if (event.getCode() == KeyCode.LEFT) {
                x[0] -= 2;
            } else if (event.getCode() == KeyCode.RIGHT) {
                x[0] += 2;
            }
        });
        new AnimationTimer() {
            public void handle(long currentNanoTime) {

                gc.drawImage(stars, 0, 0);
                gc.drawImage(earth, x[0], y[0]);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();

        Sequence sequence = MidiSystem.getSequence(getClass().getResource("audio/wet.mid"));
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequencer.setSequence(sequence);
        sequencer.setTempoInBPM(85);
        sequencer.setLoopCount(100);
        sequencer.start();

        Text text = new Text();
        text.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 48));
        text.setFill(Color.SKYBLUE);
        text.setStrokeWidth(2);
        text.setStroke(Color.BLACK);
        text.setText("Hello World!");
        text.setX(200);
        text.setY(60);
        root.getChildren().add(text);

        Text subtext = new Text();
        subtext.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 24));
        subtext.setFill(Color.SKYBLUE);
        subtext.setStrokeWidth(2);
        subtext.setStroke(Color.BLACK);
        subtext.setText("Use the Arrow Keys to move the world!");
        subtext.setX(100);
        subtext.setY(95);
        root.getChildren().add(subtext);

        theStage.show();
        theStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }
    private void closeWindowEvent(WindowEvent event){
        System.exit(1);
    }
}
