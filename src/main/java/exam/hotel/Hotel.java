package exam.hotel;

import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Locale;

public class Hotel extends Application {
    TableView<Room> tableR;
    TableView<Guest> tableG;
    static ObservableList<Room> rooms;
    static ObservableList<Guest> guests;

    private TextField floorTF;
    private TextField numTF;
    private TextField bedTF;
    private CheckBox wcBox;
    private CheckBox showerBox;
    private TextField nameTF;
    private TextField surnameTF;
    private DatePicker birthP;


    @Override
    public void init() throws Exception {
        super.init();
        Locale.setDefault(new Locale("cs", "CZ"));
        rooms = FXCollections.observableArrayList();
        guests = FXCollections.observableArrayList();
        rooms.add(new Room(0, 20, 2, true, true));
        rooms.add(new Room(0, 19, 1, false, true));
        guests.add(new Guest("Albert", "Einstein", LocalDate.of(2000, 12, 20), "019"));
        guests.add(new Guest("Libor", "Vasa", LocalDate.of(1997, 4, 11), "020"));
    }

    @Override
    public void start(Stage stage) {
        BorderPane bp = new BorderPane();

        bp.setLeft(geLeftComponents());
        bp.setRight(getRightComponents());
        bp.setBottom(getBottomComponents());

        bp.setPadding(new Insets(8));

        Scene scene = new Scene(bp, 870, 700);
        stage.setMinHeight(700);
        stage.setMinWidth(870);
        stage.setScene(scene);
        stage.setTitle("Hotel");
        stage.show();
    }

    private Node getBottomComponents() {
        HBox hb = new HBox();
        Button btnAddR = new Button("Add new room");
        btnAddR.setOnAction(ev -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Add new room");
            alert.setTitle("New Room");
            alert.setHeaderText("New Room");
            floorTF = new TextField();
            floorTF.setPromptText("Floor");
            numTF = new TextField();
            numTF.setPromptText("Room number");
            bedTF = new TextField();
            bedTF.setPromptText("Bed count");
            HBox hb1 = new HBox(floorTF, numTF, bedTF);
            showerBox = new CheckBox("Shower");
            wcBox = new CheckBox("WC");
            HBox hb2 = new HBox(showerBox, wcBox);
            VBox vb = new VBox(hb1, hb2);
            alert.setGraphic(vb);
            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    int index = rooms.size();
                    if (floorTF.getText().equals("") || Integer.parseInt(floorTF.getText()) <= 0) {
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "Bad data format");
                        al.setContentText("Bad data format");
                        al.showAndWait().get();
                    } else if (numTF.getText().equals("") || Integer.parseInt(numTF.getText()) < 0) {
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "Bad data format");
                        al.setContentText("Bad data format");
                        al.showAndWait().get();
                    } else if (bedTF.getText().equals("") || Integer.parseInt(bedTF.getText()) < 0) {
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "Bad data format");
                        al.setContentText("Bad data format");
                        al.showAndWait().get();
                    } else {
                        rooms.add(index, new Room(Integer.parseInt(floorTF.getText()), Integer.parseInt(numTF.getText()),
                                Integer.parseInt(bedTF.getText()), wcBox.isSelected(), showerBox.isSelected()));
                        tableR.refresh();
                        tableG.refresh();
                    }
                } catch (Exception e) {
                    Alert al = new Alert(Alert.AlertType.INFORMATION, "Bad data format");
                    al.setContentText("Bad data format");
                    al.showAndWait().get();
                }
            }
        });

        Button btnEdit = new Button("Edit guest");
        btnEdit.setOnAction(evt -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User");
            alert.setHeaderText("Selected guest");
            int index = tableG.getSelectionModel().getSelectedIndex();
            TextField name;
            TextField surname;
            DatePicker info;
            if (index >= 0) {
                name = new TextField(guests.get(index).getName());
                surname = new TextField(guests.get(index).getSurname());
                info = new DatePicker(guests.get(index).getBirth());
            } else {
                alert.setHeaderText("Please select a user");

                name = new TextField("Not initialized");
                surname = new TextField("Not initialized");
                info = new DatePicker();
            }
            VBox vb = new VBox(name, surname, info);
            alert.setGraphic(vb);


            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    guests.get(index).setName(name.getText());
                    guests.get(index).setSurname(surname.getText());
                    guests.get(index).setBirth(info.getValue());
                } catch (Exception ignored) {
                }
            }


        });

        Button btnShow = new Button("Show");
        btnShow.setOnAction(evt -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            TextField tf = new TextField();
            tf.setPromptText("What floor do u want to see?");
            alert.setGraphic(tf);
            if (alert.showAndWait().get() == ButtonType.OK) {
                String floor = tf.getText();
                if (floor.equals("")) {
                    floor = "0";
                }
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                ObservableList<Room> current = FXCollections.observableArrayList();
                for (int i = 0; i < rooms.size(); i++) {
                    if (rooms.get(i).floorProperty().getValue().toString().equals(floor)) {
                        current.add(rooms.get(i));
                    }
                }


                TableView<Room> tableView = new TableView<>();
                tableView.getItems().addAll(current);
                tableView.setEditable(false);
                tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                tableView.setItems(current);
                TableColumn<Room, String> colN = new TableColumn<>("Name");
                colN.setCellValueFactory(param -> new StringBinding() {
                    {
                        super.bind(param.getValue().floorProperty(), param.getValue().numProperty());
                    }

                    @Override
                    protected String computeValue() {
                        return "" + param.getValue().floorProperty().get() + param.getValue().numProperty().get();
                    }
                });
                colN.setEditable(false);

                TableColumn<Room, Number> colBed = new TableColumn<>("Beds");
                colBed.setCellValueFactory(cellData -> cellData.getValue().bedProperty());
                colBed.setCellFactory(col -> new BedIntegerCell());

                TableColumn<Room, Number> colR = new TableColumn<>("Room N");
                colR.setCellValueFactory(cellData -> cellData.getValue().numProperty());
                colR.setCellFactory(col -> new IntegerEditingCell<>());

                TableColumn<Room, Boolean> colWc = new TableColumn<>("WC");
                colWc.setCellValueFactory(new PropertyValueFactory<>("wc"));
                colWc.setCellFactory(CheckBoxTableCell.forTableColumn(colWc));

                TableColumn<Room, Boolean> colSh = new TableColumn<>("Shower");
                colSh.setCellValueFactory(new PropertyValueFactory<>("shower"));
                colSh.setCellFactory(CheckBoxTableCell.forTableColumn(colSh));

                TableColumn<Room, Label> colT = new TableColumn<>("Taken");
                colT.setCellValueFactory(param -> new ObjectBinding<Label>() {
                    @Override
                    protected Label computeValue() {
                        Label label = new Label("No");
                        int count = 0;
                        label.setText("No");
                        String room = String.valueOf(param.getValue().floorProperty().get()) + param.getValue().numProperty().get();
                        for (Guest guest : guests) {
                            String gRoom = guest.getRoom();
                            if (room.equals(gRoom)) {
                                count++;
                            }
                        }
                        if (count == param.getValue().bedProperty().get())
                            label.setText("Yes");
                        return label;
                    }

                    {
                        super.bind(param.getValue().numProperty(), param.getValue().bedProperty());
                    }
                });
                colT.setEditable(false);

                tableView.getColumns().addAll(colN, colR, colBed, colWc, colSh, colT);


                al.setGraphic(tableView);
                al.showAndWait();
            }
        });

        Label text = new Label("To add a guest, click on the room and press \"A\". To remove them, press \"Delete\".");
        btnAddR.setPadding(new Insets(5));
        btnEdit.setPadding(new Insets(5));
        text.setPadding(new Insets(5));
        hb.getChildren().addAll(btnAddR, btnEdit, btnShow, text);
        return hb;
    }

    private Node getRightComponents() {
        tableG = new TableView<>();
        tableG.getItems().addAll(guests);
        tableG.setEditable(true);
        tableG.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableG.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableG.setItems(guests);
        tableG.setPadding(new Insets(10));
        tableG.setOnKeyPressed(o -> {
            if (o.getCode() == KeyCode.DELETE) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to delete guest?");
                if (alert.showAndWait().get() == ButtonType.OK)
                    myDeleteG();
            }
        });


        TableColumn<Guest, String> colName = new TableColumn<>("Full name");
        colName.setCellValueFactory(param -> new StringBinding() {
            {
                super.bind(param.getValue().nameProperty(), param.getValue().surnameProperty());
            }

            @Override
            protected String computeValue() {
                return param.getValue().nameProperty().get() + " " + param.getValue().surnameProperty().get();
            }
        });
        colName.setEditable(false);

        TableColumn<Guest, LocalDate> colB = new TableColumn<>("Birth");
        colB.setCellValueFactory(new PropertyValueFactory<>("birth"));
        colB.setCellFactory(param -> new DatePickerTableCell());
        colB.setEditable(false);

        TableColumn<Guest, String> colR = new TableColumn<>("Room");
        colR.setCellValueFactory(new PropertyValueFactory<>("room"));
        colR.setCellFactory(TextFieldTableCell.forTableColumn());
        colR.setEditable(false);

        TableColumn<Guest, Number> colA = new TableColumn<>("Age");
        colA.setCellValueFactory(cellData -> cellData.getValue().ageProperty());
        colA.setCellFactory(col -> new IntegerEditingCell<>());
        colA.setEditable(false);


        tableG.getColumns().addAll(colName, colB, colR, colA);

        return tableG;
    }

    private Node geLeftComponents() {
        tableR = new TableView<>();
        tableR.getItems().addAll(rooms);
        tableR.setEditable(true);
        tableR.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableR.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableR.setItems(rooms);
        tableR.setPadding(new Insets(10));
        /////////////////////////////////////////////////////////////////
        /*
        Key Listener for adding guests to room and deleting room DELETE and A
         */
        /////////////////////////////////////////////////////////////////
        tableR.setOnKeyPressed(o -> {
            if (o.getCode() == KeyCode.DELETE) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to delete room?");
                if (alert.showAndWait().get() == ButtonType.OK)
                    myDeleteR();
            }
            if (o.getCode() == KeyCode.A) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Add new guest");
                alert.setTitle("New Guest");
                alert.setHeaderText("New Guest");
                ObservableList<Room> sel = FXCollections.observableArrayList(tableR.getSelectionModel().getSelectedItems());

                String target = sel.get(0).floorProperty().getValue().toString() + sel.get(0).numProperty().getValue().toString();

                int count = 0;

                for (int i = 0; i < guests.size(); i++) {
                    if (guests.get(i).roomProperty().getValue().equals(target)) {
                        count++;
                    }
                }
                if (count == sel.get(0).bedProperty().getValue()) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setHeaderText("Room is full");
                    alert1.setContentText("You can not add any guest");
                    alert1.showAndWait();
                    return;
                }


                nameTF = new TextField();
                surnameTF = new TextField();
                nameTF.setPromptText("Name");
                surnameTF.setPromptText("Surname");
                birthP = new DatePicker();
                VBox vb = new VBox(nameTF, surnameTF);
                HBox hBox = new HBox(vb, birthP);
                alert.setGraphic(hBox);
                if (alert.showAndWait().get() == ButtonType.OK) {
                    try {
                        int index = guests.size();
                        String name, surname;
                        name = nameTF.getText();
                        surname = surnameTF.getText();
                        if (!name.equals("") && !surname.equals("")) {
                            guests.add(index, new Guest(name, surname, birthP.getValue(),
                                    sel.get(0).floorProperty().getValue().toString() + sel.get(0).numProperty().getValue().toString()));
                            tableR.refresh();
                            tableG.refresh();
                        } else {
                            Alert al = new Alert(Alert.AlertType.INFORMATION, "Bad data format");
                            al.setContentText("Bad data format");
                            al.showAndWait().get();
                        }
                    } catch (Exception e) {
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "Bad data format");
                        al.setContentText("Bad data format");
                        al.showAndWait().get();
                    }
                }
            }
        });

        TableColumn<Room, String> colN = new TableColumn<>("Name");
        colN.setCellValueFactory(param -> new StringBinding() {
            {
                super.bind(param.getValue().floorProperty(), param.getValue().numProperty());
            }

            @Override
            protected String computeValue() {
                return "" + param.getValue().floorProperty().get() + param.getValue().numProperty().get();
            }
        });
        colN.setEditable(true);

        TableColumn<Room, Number> colBed = new TableColumn<>("Beds");
        colBed.setCellValueFactory(cellData -> cellData.getValue().bedProperty());
        colBed.setCellFactory(col -> new BedIntegerCell());

        TableColumn<Room, Number> colR = new TableColumn<>("Room N");
        colR.setCellValueFactory(cellData -> cellData.getValue().numProperty());
        colR.setCellFactory(col -> new IntegerEditingCell<>());

        TableColumn<Room, Boolean> colWc = new TableColumn<>("WC");
        colWc.setCellValueFactory(new PropertyValueFactory<>("wc"));
        colWc.setCellFactory(CheckBoxTableCell.forTableColumn(colWc));

        TableColumn<Room, Boolean> colSh = new TableColumn<>("Shower");
        colSh.setCellValueFactory(new PropertyValueFactory<>("shower"));
        colSh.setCellFactory(CheckBoxTableCell.forTableColumn(colSh));

        TableColumn<Room, Label> colT = new TableColumn<>("Taken");
        colT.setCellValueFactory(param -> new ObjectBinding<Label>() {
            @Override
            protected Label computeValue() {
                Label label = new Label("No");
                int count = 0;
                label.setText("No");
                String room = String.valueOf(param.getValue().floorProperty().get()) + param.getValue().numProperty().get();
                for (Guest guest : guests) {
                    String gRoom = guest.getRoom();
                    if (room.equals(gRoom)) {
                        count++;
                    }
                }
                if (count == param.getValue().bedProperty().get())
                    label.setText("Yes");
                return label;
            }

            {
                super.bind(param.getValue().numProperty(), param.getValue().bedProperty());
            }
        });
        colT.setEditable(true);

        tableR.getColumns().addAll(colN, colR, colBed, colWc, colSh, colT);
        return tableR;
    }

    public static void main(String[] args) {
        launch();
    }

    private void myDeleteG() {
        ObservableList<Guest> sel = FXCollections.observableArrayList(tableG.getSelectionModel().getSelectedItems());
        if (sel.size() > 0) {
            tableG.getItems().removeAll(sel);
            guests.removeAll(sel);
        }
    }

    private void myDeleteR() {
        ObservableList<Room> sel = FXCollections.observableArrayList(tableR.getSelectionModel().getSelectedItems());
        if (sel.size() > 0) {
            String room = sel.get(0).floorProperty().getValue().toString() + sel.get(0).numProperty().getValue().toString();
            ObservableList<Guest> selG = FXCollections.observableArrayList();
            for (Guest guest : guests) {
                if (guest.getRoom().equals(room)) {
                    selG.add(guest);
                }
            }
            tableG.getItems().removeAll(selG);
            guests.removeAll(selG);
            tableR.getItems().removeAll(sel);
            rooms.removeAll(sel);
        }
    }
}