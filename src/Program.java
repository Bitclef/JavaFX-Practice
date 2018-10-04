import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.sound.midi.*;
import java.io.IOException;

public class Program extends Application {

    private Sequencer sequencer;
    private int[] earthX = {200};
    private int[] earthY = {100};

    public static void main(String[] args) {
        System.setProperty("quantum.multithreaded", "false");
        Application.launch(args);
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Martin's World!");
        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);
        Canvas canvas = new Canvas(700, 400);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image earth = new Image("pictures/world.gif");
        Image stars = new Image("pictures/stars.jpeg");

        animationTimer(gc, earth, stars);

        midiSequencer("audio/emotion.mid", 100, 100);

        screenText(root, "Hello World!", 200, 60, 48);
        screenText(root, "Use the Arrow Keys to move the world!", 100, 95, 24);

        keyPressed(theScene);

        theStage.show();
        theStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }


    private void closeWindowEvent(WindowEvent event) {
        sequencer.close();
            midiSequencer("audio/163.mid", 150, 1);
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }


    private void animationTimer(GraphicsContext gc, Image image, Image image2) {
        new AnimationTimer() {
            public void handle(long currentNanoTime) {

                gc.drawImage(image2, 0, 0);
                gc.drawImage(image, earthX[0], earthY[0]);

            }
        }.start();
    }

    private void keyPressed(Scene theScene) {
        theScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                earthY[0] -= 2;
            } else if (event.getCode() == KeyCode.DOWN) {
                earthY[0] += 2;
            } else if (event.getCode() == KeyCode.LEFT) {
                earthX[0] -= 2;
            } else if (event.getCode() == KeyCode.RIGHT) {
                earthX[0] += 2;
            }
        });
    }

    private void midiSequencer(String location, int tempo, int loopCount) {
        try {
            sequencer = MidiSystem.getSequencer();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        Sequence sequence = null;
        try {
            sequence = MidiSystem.getSequence(getClass().getResource(location));
        } catch (InvalidMidiDataException | IOException e) {
            e.printStackTrace();
        }
        try {
            sequencer.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        try {
            sequencer.setSequence(sequence);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        sequencer.setTempoInBPM(tempo);
        sequencer.setLoopCount(loopCount);
        sequencer.start();


    }

    private void screenText(Group root, String visualText, int setX, int setY, int size) {
        Text text = new Text();
        text.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, size));
        text.setFill(Color.SKYBLUE);
        text.setStrokeWidth(2);
        text.setStroke(Color.BLACK);
        text.setText(visualText);
        text.setX(setX);
        text.setY(setY);
        root.getChildren().add(text);
    }
}
