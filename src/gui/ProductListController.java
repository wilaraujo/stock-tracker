package gui;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Product;
import model.services.ProductService;

public class ProductListController implements Initializable, DataChangeListener {
	
	private ProductService service;

	@FXML
	private TableView<Product> tableViewProduct;

	@FXML
	private TableColumn<Product, Integer> tableColumnId;

	@FXML
	private TableColumn<Product, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	@FXML
	private Button btEdit;

	@FXML
	private Button btRemove;
	
	private ObservableList<Product> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Product obj = new Product();
		createDialogForm(obj, "/gui/ProductForm.fxml", parentStage);
	}
	
	public void onBtEditAction(ActionEvent event) {
		Product obj = tableViewProduct.getSelectionModel().getSelectedItem();
		if (obj == null) {
			return;
		}
		Stage parentStage = Utils.currentStage(event);
		createDialogForm(obj, "/gui/ProductForm.fxml", parentStage);
		tableViewProduct.refresh();
	}
	
	public void onBtRemoveAction(ActionEvent event) {
		Product obj = tableViewProduct.getSelectionModel().getSelectedItem();
		if (obj == null) {
			return;
		}
		removeEntity(obj);
		tableViewProduct.refresh();
	}
	
	private void removeEntity(Product obj) {
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

	public void setProductService(ProductService service) {
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
		tableViewProduct.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Product> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewProduct.setItems(obsList);
	}
	
	private void createDialogForm(Product obj, String absoluteName, Stage parentStage) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//			Pane pane = loader.load();
//			
//			ProductFormController controller = loader.getController();
//			controller.setProduct(obj);
//			controller.setProductService(new ProductService());
//			controller.subscribeDataChangeListener(this);
//			controller.updateFormData();
//			
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Enter Product data");
//			dialogStage.setScene(new Scene(pane));
//			dialogStage.setResizable(false);
//			dialogStage.initOwner(parentStage);
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.showAndWait();
//		} catch (IOException e) {
//			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
//		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

}
