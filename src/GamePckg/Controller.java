package GamePckg;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import GamePckg.User;
 
import javafx.scene.control.TableColumn;
 
public class Controller {
 
    private ObservableList<User> usersData = FXCollections.observableArrayList();
 
    @FXML
    private TableView<User> tableUsers;
 
    @FXML
    private TableColumn<User, Integer> idColumn;
 
    @FXML
    private TableColumn<User, Integer> loginColumn;
 
    @FXML
    private TableColumn<User, Integer> passwordColumn;
 
    @FXML
    private void initialize() {
        initData();
 
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("password"));
 
        tableUsers.setItems(usersData);
    }
    
    private void initData() {
        for(int i = 0; i < Replay.nums.size(); i++) {
        	usersData.add(new User(Replay.nums.get(i), Replay.steps.get(i) / 240, Replay.shots.get(Replay.nums.get(i) - 1)));
        }
    }
 
}
