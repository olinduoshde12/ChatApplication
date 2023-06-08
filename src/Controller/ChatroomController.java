package Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import service.client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ChatroomController {

    public TextField txt1;
    public ScrollPane scroll1;
    public VBox vbox1;
    public Button send1;
    public ImageView txticon;
    private client client;
    private String clientUserName;
    private Socket socket;

    public void initialize(){
        this.clientUserName=OpenController.nameList.get(OpenController.nameList.size()-1);

        try {
            System.out.println("name"+" "+OpenController.nameList.get(OpenController.nameList.size()-1));

            this.client=new client(new Socket("localhost",3006),clientUserName);
            System.out.println("New Client connected to group!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        client.receiveMessage(vbox1);

    }
    public void setClientUserName(String clientUserName){
        this.clientUserName=clientUserName;
    }

    public static void receiveMessage(String receivedMessage,VBox vbox){
        HBox hBox=new HBox();
        hBox.setPadding(new Insets(5,5,5,10));
        Text text=new Text(receivedMessage);
        TextFlow textFlow=new TextFlow(text);

        if(receivedMessage.startsWith("*")){
            hBox.setAlignment(Pos.CENTER);
            textFlow.setStyle("-fx-background-color: rgb(243,172,157);" +
                    "-fx-background-radius: 20px;");
        }else {
            hBox.setAlignment(Pos.CENTER_LEFT);
            textFlow.setStyle("-fx-background-color: rgb(233,233,235);" +
                    "-fx-background-radius: 20px;");

        }

        textFlow.setPadding(new Insets(5,10,5,10));

        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);
            }
        });
    }
    public void sentMessageOnAction(ActionEvent actionEvent) {

        String messageToSent=txt1.getText();

        /** ensure to text field not empty*/
        if(!messageToSent.isEmpty()){

            HBox hBox=new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5,5,5,10));

            Text text=new Text(messageToSent);
            TextFlow textFlow=new TextFlow(text);

            textFlow.setStyle("-fx-color: rgb(239,242,255);"+
                    "-fx-background-color: rgb(15,125,242);" +
                    "-fx-background-radius: 20px;");

            textFlow.setPadding(new Insets(5,10,5,10));
            text.setFill(Color.color(0.934,0.945,0.996));

            hBox.getChildren().add(textFlow);
            vbox1.getChildren().add(hBox);

            client.sentMessage(clientUserName+": "+messageToSent);
            txt1.clear();
        }
    }

    public void sendOnAction(ActionEvent actionEvent) {
        sentMessageOnAction(actionEvent);
    }


    public void clickonAction(MouseEvent mouseEvent) throws IOException, InterruptedException {
       /* Socket socket = new Socket("localhost", 3006);
        OutputStream outputStream = socket.getOutputStream();

        BufferedImage image = ImageIO.read(new File("D:\\New folder (8)\\test.jpg"));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);

        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        outputStream.write(size);
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
        System.out.println("Flushed: " + System.currentTimeMillis());

        Thread.sleep(120000);
        System.out.println("Closing: " + System.currentTimeMillis());
        socket.close();*/
    }
}
