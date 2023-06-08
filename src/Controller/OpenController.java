package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class OpenController {
    public static ArrayList<String> nameList=new ArrayList<>();
    public TextField txt1;
    public Button btn1;

    public void btn1OnAction(ActionEvent actionEvent) throws IOException {
        nameList.add(txt1.getText());

      /*  FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/chatroom.fxml"));
        Parent load = null;
        try {
            load = fxmlLoader.load();
        } catch ( IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Group Chat");
        stage.setScene(new Scene(load));
        stage.centerOnScreen();
        stage.show();
        txt1.clear();*/
        Parent root=FXMLLoader.load(getClass().getResource("../view/chatroom.fxml"));
        Stage stage=new Stage();
        stage.setTitle(txt1.getText());
        stage.setScene(new Scene(root));
        stage.show();
    }
}
