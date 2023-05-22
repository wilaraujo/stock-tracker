package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Category;
import model.services.CategoryService;

public class CategoryListController implements Initializable{
	
	private CategoryService service;

	@FXML
	private TableView<Category> tableViewCategory;

	@FXML
	private TableColumn<Category, Integer> tableColumnId;

	@FXML
	private TableColumn<Category, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Category> obsList;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	public void setCategoryService(CategoryService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewCategory.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Category> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewCategory.setItems(obsList);
	}

}
