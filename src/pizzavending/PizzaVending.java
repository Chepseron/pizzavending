package PizzaVending;

import com.sun.javafx.scene.control.skin.LabeledText;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PizzaVending extends Application {

    //declaration for title pane
    protected final TitledPane MainTitledPane;
    //declaration for main anchor pane
    protected final AnchorPane MainAnchorPane;
    //declaration for confirm payment anchor pane
    protected final AnchorPane ConfirmPaymentAnchorPane;
    //declaration of observable list to display available pizza
    ObservableList<String> availablePizza;
    //declaration for list view to view available pizza
    protected final ListView AvailablePizzaList;
    //declaration for list view to view ordered pizza
    protected final ListView OrderedPizzaList;
    //declaration for add order button
    protected final Button AddOrderBtn;
    //declaration for remove order button
    protected final Button RemoveOrderBtn;
    //declaration for clear order button
    protected final Button ClearOrderBtn;
    //declaration for pizza vending machine label
    protected final Label VendingMachineLabel;
    //declaration for add order label
    protected final Label AddToOrderLabel;
    //declaration for pay button
    protected final Button PayBtn;
    //declaration for total payment label
    protected final Label TotalPayMainLabel;
    //declaration for confirm payment label
    protected final Label ConfirmPayLabel;
    //declaration for confirm total pay label on the confirm payment dialog
    protected final Label TotalPayConfirmLabel;
    //declaration for cancel payment button
    protected final Button CancelPaymentBtn;
    //declaration for confirm payment button
    protected final Button ConfirmPaymentBtn;
    //declaration of a list to populate the observable list to display available pizza
    List<String> availablePizzaTempList = new ArrayList<>();
    //declaration of default total price display as 0
    double currentTotalPrice = 0;
    //declaration of the progress bar on confirm payment
    protected final ProgressBar progressBar;
    //declaration of image view to display the image on confirm payment
    protected final ImageView imageView;

    public PizzaVending() {
        //instantiate the main anchor pane and main titled pane
        MainAnchorPane = new AnchorPane();
        MainTitledPane = new TitledPane();
        //instantiate the available pizza list view
        AvailablePizzaList = new ListView();
        //instantiate the ordered pizza list view
        OrderedPizzaList = new ListView();
        //instantiate the add order button
        AddOrderBtn = new Button();
        //instantiate the remove order button
        RemoveOrderBtn = new Button();
        //instantiate the clear order button
        ClearOrderBtn = new Button();
        //instantiate the vending machine label
        VendingMachineLabel = new Label();
        //instantiate add to order label
        AddToOrderLabel = new Label();
        //instantiate the pay button
        PayBtn = new Button();
        //instantiate the total pay sum label
        TotalPayMainLabel = new Label();
        //instantiate the confirm pay anchor pane
        ConfirmPaymentAnchorPane = new AnchorPane();
        //instantiate the confirm pay label
        ConfirmPayLabel = new Label();
        //instantiate the clear order button
        TotalPayConfirmLabel = new Label();
        //instantiate the cancel/ok buttons on confirm payment dialog
        CancelPaymentBtn = new Button();
        ConfirmPaymentBtn = new Button();
        //instantiate the image view on confirm payment dialog
        imageView = new ImageView();
        //instantiate the progress bar on confirm payment dialog
        progressBar = new ProgressBar();
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception ex) {
            Logger.getLogger(PizzaVending.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//function to add order on clicking the AddOrderBtn

    protected void addOrder(ActionEvent actionEvent) {

        try {
            //add available pizza(from available pizza list view) to a temporary list 
            availablePizzaTempList.add(AvailablePizzaList.getSelectionModel().getSelectedItem().toString());
            //get the content of available pizza temporary list and add to observable list
            availablePizza = FXCollections.observableArrayList(availablePizzaTempList);
            //populate the ordered list with the content of available pizza observable list
            OrderedPizzaList.setItems(availablePizza);
            //a series of activities to populate the total payment labels both on the main page and on the confirm page dialog
            //get the content of the selected value from available pizza list and convert to string
            String currentValue = AvailablePizzaList.getSelectionModel().getSelectedItem().toString();
            //split the selected item and obtain the second value from the array(to get the integer value for price)
            double currentPrice = Double.parseDouble(currentValue.split("-€")[1]);
            //get the sum of existing and add to the selected price above
            currentTotalPrice = currentPrice + currentTotalPrice;
            //set the total price both on the main page and on the confirm dialog
            TotalPayMainLabel.setText("Total : € " + currentTotalPrice);
            TotalPayConfirmLabel.setText("Total : € " + currentTotalPrice);
        } catch (Exception ex) {
        }
    }

    protected void removeOrder(ActionEvent actionEvent) {
        try {

            //check if the ordered list view is empty
            if (OrderedPizzaList.getItems().isEmpty()) {
                displayDialog("You have not ordered any pizza");
            } else {
                //Remove available pizza(from available pizza list view)
                availablePizzaTempList.remove(OrderedPizzaList.getSelectionModel().getSelectedItem().toString());
                //get the content of available pizza temporary list and add to observable list
                availablePizza = FXCollections.observableArrayList(availablePizzaTempList);
                //populate the ordered list with the content of available pizza observable list
                OrderedPizzaList.setItems(availablePizza);
                //a series of activities to populate the total payment labels both on the main page and on the confirm page dialog
                //get the content of the selected value from available pizza list and convert to string
                String currentValue = AvailablePizzaList.getSelectionModel().getSelectedItems().toString();
                //split the selected item and obtain the second value from the array(to get the integer value for price)
                double currentPrice = Double.parseDouble(currentValue.split("-€")[1].replaceAll("]", ""));
                //get the sum of existing and add to the selected price above
                currentTotalPrice = currentTotalPrice - currentPrice;
                //set the total price both on the main page and on the confirm dialog
                TotalPayMainLabel.setText("Total : € " + currentTotalPrice);
                TotalPayConfirmLabel.setText("Total : € " + currentTotalPrice);
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    protected void confirmPayment(ActionEvent actionEvent) {
        try {
            //check if the ordered list view is empty
            if (OrderedPizzaList.getItems().isEmpty()) {
                //display 'Please order pizza then pay' if someone hasnt ordered pizza but wants to pay
                displayDialog("Please order pizza then pay");
            } else {
                //make the confirm dialog pane visible
                MainTitledPane.setVisible(true);
            }
        } catch (Exception ex) {

        }
    }

    protected void cancelPayment(ActionEvent actionEvent) {
        try {
            //
            MainTitledPane.setVisible(false);
        } catch (Exception ex) {

        }
    }

    protected void clearOrder(ActionEvent actionEvent) {
        try {

            //check if the ordered list view is empty
            if (OrderedPizzaList.getItems().isEmpty()) {
                //display 'You have not ordered any pizza' if someone wants to clear an order that hasnt been ordered
                displayDialog("You have not ordered any pizza");
            } else {
                //Remove all available pizza(from available pizza list view)
                availablePizzaTempList.removeAll(availablePizza);
                //set ordered  pizza list to empty
                OrderedPizzaList.setItems(null);
                //set the text on both on the main page and on the confirm dialog to Total : € 0
                TotalPayMainLabel.setText("Total : € 0");
                TotalPayConfirmLabel.setText("Total : € 0");
            }
        } catch (Exception ex) {

        }
    }

    protected void updateProgress(ActionEvent actionEvent) {
        try {

            //set the progress bar to be visible
            progressBar.setVisible(true);
            //set the text on confirm payment dialog label to 'Preparing your order'
            TotalPayConfirmLabel.setText("Preparing your order");
            //set the progress bar to delay for 15 countable times
            int counter = 15;
            for (int i = 0; i < counter; i++) {
                try {
                    //set the progress on the progress bar
                    progressBar.setProgress(1.0 * i / (counter - 1));
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
        } catch (Exception ex) {

        }
    }

    //function to display a dialog based on the messages passed(this function is called from addOrder, removeOrder, and clearOrder
    protected void displayDialog(String message) {
        //Creating a dialog
        Dialog<String> dialog = new Dialog<String>();
        //Setting the title
        dialog.setTitle("Dialog");
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setContentText(message);
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);
        //show the dialog
        dialog.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //set the title for the main stage to Add to your order and press pay to take pizza
        primaryStage.setTitle("Add to your order and press pay to take pizza");
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 900, 750));
        //set the preffered height and width 
        MainTitledPane.setPrefHeight(600.0);
        MainTitledPane.setPrefWidth(800.0);
        //set the default text for display
        MainTitledPane.setText("Add to your order and press pay to take pizza");

        //set the position of X and Y axis in the layout
        AvailablePizzaList.setLayoutX(111.0);
        AvailablePizzaList.setLayoutY(63.0);
        //set the preffered height and width 
        AvailablePizzaList.setPrefHeight(276.0);
        AvailablePizzaList.setPrefWidth(200.0);
        //configure the List view to accept multiple selections
        AvailablePizzaList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //set the position of X and Y axis in the layout
        OrderedPizzaList.setLayoutX(453.0);
        OrderedPizzaList.setLayoutY(63.0);
        //set the preffered height and width 
        OrderedPizzaList.setPrefHeight(271.0);
        OrderedPizzaList.setPrefWidth(200.0);
        //set the position of X and Y axis in the layout
        AddOrderBtn.setLayoutX(321.0);
        AddOrderBtn.setLayoutY(63.0);
        //set the default text for display
        AddOrderBtn.setText("Add to order");
        //set the position of X and Y axis in the layout
        RemoveOrderBtn.setLayoutX(321.0);
        RemoveOrderBtn.setLayoutY(107.0);
        //set the default text for display
        RemoveOrderBtn.setText("Remove from order");
        //set the position of X and Y axis in the layout
        ClearOrderBtn.setLayoutX(321.0);
        ClearOrderBtn.setLayoutY(151.0);
        //set the default text for display
        ClearOrderBtn.setText("Clear order");
        //set the position of X and Y axis in the layout
        VendingMachineLabel.setLayoutX(112.0);
        VendingMachineLabel.setLayoutY(22.0);
        //set the default text for display
        VendingMachineLabel.setText("Pizza vending machine - create order");
        //set the position of X and Y axis in the layout
        AddToOrderLabel.setLayoutX(112.0);
        AddToOrderLabel.setLayoutY(355.0);
        //set the default text for display
        AddToOrderLabel.setText("Adding to order");
        //set the position of X and Y axis in the layout
        PayBtn.setLayoutX(622.0);
        PayBtn.setLayoutY(352.0);
        PayBtn.setText("Pay");
        //set the position of X and Y axis in the layout
        TotalPayMainLabel.setLayoutX(456.0);
        TotalPayMainLabel.setLayoutY(349.0);
        //set the default text for display
        TotalPayMainLabel.setText("Total: € 0");
        TotalPayMainLabel.setFont(new Font(24.0));
        //set the position of X and Y axis in the layout
        MainTitledPane.setLayoutX(79.0);
        MainTitledPane.setLayoutY(384.0);
        //set the preffered height and width 
        MainTitledPane.setPrefHeight(169.0);
        MainTitledPane.setPrefWidth(362.0);
        //set the default text for display
        MainTitledPane.setText("Confirm payment");
        MainTitledPane.setVisible(false);
        //set the preffered height and width 
        ConfirmPaymentAnchorPane.setPrefHeight(180.0);
        ConfirmPaymentAnchorPane.setPrefWidth(200.0);
        //set the position of X and Y axis in the layout
        ConfirmPayLabel.setLayoutX(26.0);
        ConfirmPayLabel.setLayoutY(22.0);
        //set the default text for display
        ConfirmPayLabel.setText("Please confirm to pay");
        //set the position of X and Y axis in the layout
        TotalPayConfirmLabel.setLayoutX(23.0);
        TotalPayConfirmLabel.setLayoutY(73.0);
        //set the default text for display
        TotalPayConfirmLabel.setText("Total: € 0");
        TotalPayConfirmLabel.setFont(new Font(24.0));
        //set the position of X and Y axis in the layout
        CancelPaymentBtn.setLayoutX(180.0);
        CancelPaymentBtn.setLayoutY(108.0);
        //set the preffered height and width 
        CancelPaymentBtn.setPrefHeight(25.0);
        CancelPaymentBtn.setPrefWidth(62.0);
        //set the default text for display
        CancelPaymentBtn.setText("Cancel");
        //set the position of X and Y axis in the layout
        ConfirmPaymentBtn.setLayoutX(252.0);
        ConfirmPaymentBtn.setLayoutY(108.0);
        //set the preffered height and width 
        ConfirmPaymentBtn.setPrefHeight(25.0);
        ConfirmPaymentBtn.setPrefWidth(64.0);
        //set the default text for display
        ConfirmPaymentBtn.setText("Ok");
        //set the position of X and Y axis in the layout
        progressBar.setLayoutX(21.0);
        progressBar.setLayoutY(112.0);
        //set the preffered height and width 
        progressBar.setPrefHeight(18.0);
        progressBar.setPrefWidth(141.0);
        progressBar.setProgress(0.0);
        progressBar.setVisible(false);

        imageView.setFitHeight(43.0);
        imageView.setFitWidth(74.0);
        //set the position of X and Y axis in the layout
        imageView.setLayoutX(242.0);
        imageView.setLayoutY(18.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        //set the default place holder image of the image holder on confirm payment dialog
        this.imageView.setImage(new Image(new File("C:\\Users\\asabul\\Documents\\NetBeansProjects\\PizzaVending\\src\\pizzavending\\pay.png").toURI().toString()));
        MainTitledPane.setContent(ConfirmPaymentAnchorPane);

        //add all the child elements to the main anchor pane
        MainAnchorPane.getChildren().add(AvailablePizzaList);
        MainAnchorPane.getChildren().add(OrderedPizzaList);
        MainAnchorPane.getChildren().add(AddOrderBtn);
        MainAnchorPane.getChildren().add(RemoveOrderBtn);
        MainAnchorPane.getChildren().add(ClearOrderBtn);
        MainAnchorPane.getChildren().add(VendingMachineLabel);
        MainAnchorPane.getChildren().add(AddToOrderLabel);
        MainAnchorPane.getChildren().add(PayBtn);
        MainAnchorPane.getChildren().add(TotalPayMainLabel);

        //add all the child elements to the confirma payment anchor pane
        ConfirmPaymentAnchorPane.getChildren().add(ConfirmPayLabel);
        ConfirmPaymentAnchorPane.getChildren().add(TotalPayConfirmLabel);
        ConfirmPaymentAnchorPane.getChildren().add(CancelPaymentBtn);
        ConfirmPaymentAnchorPane.getChildren().add(ConfirmPaymentBtn);
        ConfirmPaymentAnchorPane.getChildren().add(progressBar);
        ConfirmPaymentAnchorPane.getChildren().add(imageView);
        MainAnchorPane.getChildren().add(MainTitledPane);

        root.getChildren().add(MainAnchorPane);
        primaryStage.show();

    }

    @Override
    public void init() {
        try {
            //read the pizzamenu csv file
            BufferedReader bfr = new BufferedReader(new FileReader("C:\\Users\\asabul\\Documents\\NetBeansProjects\\PizzaVending\\src\\pizzavending\\pizzamenu.csv"));
            //declare a list to hold the read pizza list
            List<String> results = new ArrayList<>();
            //declare a string to hold the actual pizza titles
            String pizza = null;
            //loop through the file while adding the read pizza list to the list
            while ((pizza = bfr.readLine()) != null) {
                //break each line of the csv file to its elements
                results.add(pizza.replaceAll(",", "-€"));
            }
            //add the list above to the observable list 
            ObservableList<String> items = FXCollections.observableArrayList(results);
            //add the items to the list view
            AvailablePizzaList.setItems(items);
            //button to add order
            AddOrderBtn.setOnAction(this::addOrder);
            //button to remove order
            RemoveOrderBtn.setOnAction(this::removeOrder);
            //button to clear orders
            ClearOrderBtn.setOnAction(this::clearOrder);
            //button to confirm payment
            PayBtn.setOnAction(this::confirmPayment);
            //Button to cancel a payment
            CancelPaymentBtn.setOnAction(this::cancelPayment);
            //Button to confirm a payment
            ConfirmPaymentBtn.setOnAction(this::updateProgress);
            //handle the double click on Available pizza list view
            AvailablePizzaList.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
                            && (event.getTarget() instanceof LabeledText || ((GridPane) event.getTarget()).getChildren().size() > 0)) {
                        //call the add order event    
                        addOrder(null);
                    }
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
