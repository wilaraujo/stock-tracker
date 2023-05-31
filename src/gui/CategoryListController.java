package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Category;
import model.services.CategoryService;

public class CategoryListController implements Initializable, DataChangeListener {
	
	private CategoryService service;

	@FXML
	private TableView<Category> tableViewCategory;

	@FXML
	private TableColumn<Category, Integer> tableColumnId;

	@FXML
	private TableColumn<Category, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	@FXML
	private Button btEdit;

	@FXML
	private Button btRemove;
	
	private ObservableList<Category> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Category obj = new Category();
		createDialogForm(obj, "/gui/CategoryForm.fxml", parentStage);
	}
	
	public void onBtEditAction(ActionEvent event) {
		Category obj = tableViewCategory.getSelectionModel().getSelectedItem();
		if (obj == null) {
			return;
		}
		Stage parentStage = Utils.currentStage(event);
		createDialogForm(obj, "/gui/CategoryForm.fxml", parentStage);
		tableViewCategory.refresh();
	}
	
	public void onBtRemoveAction(ActionEvent event) {
		Category obj = tableViewCategory.getSelectionModel().getSelectedItem();
		if (obj == null) {
			return;
		}
		removeEntity(obj);
		tableViewCategory.refresh();
	}
	
	private void removeEntity(Category obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
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
	
	private void createDialogForm(Category obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			CategoryFormController controller = loader.getController();
			controller.setCategory(obj);
			controller.setCategoryService(new CategoryService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Category data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

}
