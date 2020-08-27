import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application
{
   @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/LoggerView.fxml"));
        primaryStage.setTitle("Przychodnia Sunshine");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("Icons/app_icon.png")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String args[])
    {
        launch(args);
    }
}
