package com.ekocbiyik.controller;

import com.ekocbiyik.MainStage;
import com.ekocbiyik.model.DockerImage;
import com.ekocbiyik.utils.DockerUtils;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * enbiya 16.07.2020
 */
public class VersionController implements IPanelController, Initializable {

    public TableView<DockerImage> tblVersion;
    public TableColumn colName;
    public TableColumn colDescription;
    public TableColumn colVersion;
    public TableColumn colDate;
    public TableColumn colSize;
    public TableColumn colHash;
    public MenuButton menuBtn;
    public TextField txtFilePath;
    public TextField txtSearch;
    public Button btnUpload;
    public Label lblInfo;
    private FilteredList<DockerImage> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCellFactories();
        loadTable();
    }

    private void initCellFactories() {
        tblVersion.setPlaceholder(new Label("Kayıt bulunamadı."));
        colDescription.setCellValueFactory(new PropertyValueFactory<DockerImage, String>("description"));
        colName.setCellValueFactory(new PropertyValueFactory<DockerImage, String>("name"));
        colVersion.setCellValueFactory(new PropertyValueFactory<DockerImage, String>("version"));
        colDate.setCellValueFactory(new PropertyValueFactory<DockerImage, String>("createdAt"));
        colSize.setCellValueFactory(new PropertyValueFactory<DockerImage, String>("size"));
        colHash.setCellValueFactory(new PropertyValueFactory<DockerImage, String>("hash"));
        addButtonToTable();
    }

    private void loadTable() {
        filteredList = new FilteredList<>(FXCollections.observableList(DockerUtils.getDockerImages()));
        tblVersion.setItems(filteredList);
    }

    public void txtSearchOnKeyPress() {
        String text = txtSearch.getText().toLowerCase();
        filteredList.setPredicate(s -> text.isEmpty()
                        ? true
                        : (s.getName().toLowerCase().contains(text)
                        || s.getVersion().toLowerCase().contains(text)
                        || s.getDescription().toLowerCase().contains(text)
                )
        );
    }

    private void addButtonToTable() {
        TableColumn<DockerImage, Void> colBtn = new TableColumn();
        Callback<TableColumn<DockerImage, Void>, TableCell<DockerImage, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<DockerImage, Void> call(final TableColumn<DockerImage, Void> param) {
                final TableCell<DockerImage, Void> cell = new TableCell<>() {
                    Button btn = new Button();

                    {
                        btn.setPrefWidth(50);
                        btn.setAlignment(Pos.CENTER);
                        btn.getStyleClass().add("btnUpgrade");
                        btn.setOnAction((ActionEvent event) -> {
                            btn.setDisable(true);
                            DockerUtils.runContainer(getTableView().getItems().get(getIndex()));
                            btn.setDisable(false);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : btn);
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        colBtn.setPrefWidth(80);
        tblVersion.getColumns().add(colBtn);
    }

    public void loadImageOnClick(MouseEvent e) {

        if (e.getSource() instanceof MenuButton) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Arşiv dosyası", "*.tar"));

            File file = fileChooser.showOpenDialog(MainStage.getMainStage());
            if (file == null) return;

            lblInfo.setText("");
            txtFilePath.setText(file.getPath());
            btnUpload.setDisable(false);
        }

        if (e.getSource() instanceof Button) {
            String version = DockerUtils.loadImage(txtFilePath.getText());
            btnUpload.setDisable(true);
            txtFilePath.setText("");
            lblInfo.setText(version);
            loadTable();
            Optional<DockerImage> item = tblVersion.getItems().stream().filter(i -> version.contains(i.getName() + ":" + i.getVersion())).findFirst();
            if (item.isPresent()) {
                tblVersion.requestFocus();
                tblVersion.getSelectionModel().select(item.get());
                tblVersion.getFocusModel().focus(tblVersion.getSelectionModel().getSelectedIndex());
            }
        }
    }

}
