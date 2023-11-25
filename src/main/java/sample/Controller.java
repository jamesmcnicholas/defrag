package sample;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Controller {
    @FXML
    private TextField sourceDirField;
    @FXML
    private TextField outDirField;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField fileNameField;
    @FXML
    private TextField fileDateField;
    @FXML
    private TextField fileSizeField;

    private File currentImageFile;
    private int currentImageIndex;
    private ArrayList<File> filesToProcess;
    private Image currentImage;
    private Random random;

    @FXML
    void onChooseSourceDir() {
        random = new Random();
        filesToProcess = new ArrayList<File>();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        File selectedFolder = directoryChooser.showDialog(new Stage());
        System.out.println(selectedFolder.getAbsolutePath());

        for (File f:selectedFolder.listFiles()) {
            String path = f.getAbsoluteFile().toString();
            if(path.contains(".jpg") || path.contains(".png") || path.contains(".svg")) {
                filesToProcess.add(f);
            }
        }
        this.currentImageFile = filesToProcess.get(0);
        this.currentImage = new Image("file:\\" + currentImageFile.getAbsolutePath());
        this.imageView.setImage(currentImage);
        sourceDirField.setText(selectedFolder.getAbsolutePath());
    }
//TODO. checks for out of images
    //todo. animations for actions (i.e. trashcan appears and fades out)
    // todo. metadata panel
    // todo. simplify animation code
    @FXML
    void onKeep(){
        TranslateTransition translateTransition1 = new TranslateTransition();
        translateTransition1.setDuration(Duration.millis(600));
        translateTransition1.setNode(imageView);
        translateTransition1.setByX(2000);
        translateTransition1.setCycleCount(1);

        ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(100), imageView);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(0);
        scaleTransition.setToY(0);
        scaleTransition.setCycleCount(1);

        TranslateTransition translateTransition2 = new TranslateTransition();
        translateTransition2.setDuration(Duration.millis(100));
        translateTransition2.setNode(imageView);
        translateTransition2.setByX(-2000);
        translateTransition2.setCycleCount(1);

        ScaleTransition scaleTransition2 =
                new ScaleTransition(Duration.millis(500), imageView);
        scaleTransition2.setFromX(0);
        scaleTransition2.setFromY(0);
        scaleTransition2.setToX(1);
        scaleTransition2.setToY(1);
        scaleTransition2.setCycleCount(1);

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                translateTransition1,
                scaleTransition,
                translateTransition2,
                scaleTransition2);
        sequentialTransition.setCycleCount(1);
        sequentialTransition.setAutoReverse(true);

        sequentialTransition.play();
        translateTransition2.setOnFinished(event -> {
            currentImageIndex++;
            updateImage();
        });
    }

    @FXML
    void onDelete(){
        TranslateTransition translateTransition3 = new TranslateTransition();
        translateTransition3.setDuration(Duration.millis(600));
        translateTransition3.setNode(imageView);
        translateTransition3.setByX(-2000);
        translateTransition3.setCycleCount(1);

        ScaleTransition scaleTransition3 =
                new ScaleTransition(Duration.millis(100), imageView);
        scaleTransition3.setFromX(1);
        scaleTransition3.setFromY(1);
        scaleTransition3.setToX(0);
        scaleTransition3.setToY(0);
        scaleTransition3.setCycleCount(1);

        TranslateTransition translateTransition4 = new TranslateTransition();
        translateTransition4.setDuration(Duration.millis(100));
        translateTransition4.setNode(imageView);
        translateTransition4.setByX(2000);
        translateTransition4.setCycleCount(1);

        ScaleTransition scaleTransition4 =
                new ScaleTransition(Duration.millis(500), imageView);
        scaleTransition4.setFromX(0);
        scaleTransition4.setFromY(0);
        scaleTransition4.setToX(1);
        scaleTransition4.setToY(1);
        scaleTransition4.setCycleCount(1);

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                translateTransition3,
                scaleTransition3,
                translateTransition4,
                scaleTransition4);
        sequentialTransition.setCycleCount(1);
        sequentialTransition.setAutoReverse(true);

        sequentialTransition.play();
        translateTransition4.setOnFinished(event -> {
            currentImageIndex++;
            updateImage();
        });

    }

    @FXML
    void onUndo(){

        this.currentImageIndex--;
        updateImage();
    }

    private void updateImage(){
        this.currentImageFile = filesToProcess.get(currentImageIndex);
        this.currentImage = new Image("file:\\" + currentImageFile.getAbsolutePath());
        this.imageView.setImage(currentImage);
        fileNameField.setText(currentImageFile.getName());
        fileDateField.setText("2020/01/01 12:20:00");
        fileSizeField.setText(random.nextInt(100) + " MB");

    }
}
