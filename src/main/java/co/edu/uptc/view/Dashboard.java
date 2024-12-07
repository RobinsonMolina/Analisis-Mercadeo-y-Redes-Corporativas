package co.edu.uptc.view;

import co.edu.uptc.controller.GraphController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javafx.scene.control.TextField;

import java.io.File;

public class Dashboard {

    private GraphController graphController;
    private VBox option;
    private VBox menu;
    private HBox hIcon;
    private BorderPane root;
    private BorderPane principal;

    public Dashboard(GraphController graphController) {
        this.graphController = graphController;
    }

    public Scene createScene() {
        principal = new BorderPane();
        menu = new VBox();
        option = new VBox(10);
        hIcon = new HBox();
        root = new BorderPane();

        createMenu();
        createMenuToggleButton();

        menu.getChildren().addAll(hIcon, option);

        principal.setLeft(menu);
        return new Scene(principal, 800, 600);
    }

    private VBox createMenu() {
        option.setPadding(new Insets(10));
        option.setStyle("-fx-background-color: #f0f0f0;");
        option.setPrefWidth(200);

        Label menuTitle = new Label("Menú");
        menuTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button createGraphButton = new Button("Crear grafo");
        Button viewGraphButton = new Button("Ver grafo");
        Button loadGraphButton = new Button("Cargar grafo");
        Button centralidadButton = new Button("Centralidad");
        Button comunityButton = new Button("Comunidades");
        Button saleButton = new Button("Ventas");

        createGraphButton.setOnAction(e -> showCreateGraphMenu());
        viewGraphButton.setOnAction(e -> viewGraph());
        loadGraphButton.setOnAction(e -> loadGraph());
        centralidadButton.setOnAction(e -> centralidad());
        comunityButton.setOnAction(e -> comunity());
        saleButton.setOnAction(e -> sale());

        option.getChildren().addAll(menuTitle, new Separator(), createGraphButton, viewGraphButton, loadGraphButton);
        return option;
    }

    private HBox createMenuToggleButton() {
        hIcon.setPadding(new Insets(5));
        hIcon.setAlignment(Pos.CENTER_LEFT);

        
        ImageView menuIcon = new ImageView(getClass().getResource("/Icon.png").toExternalForm());

        menuIcon.setFitWidth(24);
        menuIcon.setFitHeight(24);

        menuIcon.setOnMouseClicked(e -> option.setVisible(!option.isVisible()));

        hIcon.getChildren().add(menuIcon);
        return hIcon;
    }

    private void showCreateGraphMenu() {
        BorderPane root = new BorderPane();
        VBox createGraphArea = new VBox(10);
        createGraphArea.setPadding(new Insets(20));
        createGraphArea.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Opciones de creación");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button addNodeButton = new Button("Añadir Nodo");
        Button addEdgeButton = new Button("Añadir Arista");
        Button removeNodeButton = new Button("Eliminar Nodo");
        Button removeEdgeButton = new Button("Eliminar Arista");
        Button updateNodeButton = new Button("Actualizar Nodo");
        Button updateEdgeButton = new Button("Actualizar Arista");

        addNodeButton.setOnAction(e -> addNode());
        addEdgeButton.setOnAction(e -> addEdge());
        removeNodeButton.setOnAction(e -> removeNode());
        removeEdgeButton.setOnAction(e -> removeEdge());
        updateNodeButton.setOnAction(e -> updateNode());
        updateEdgeButton.setOnAction(e -> updateEdge());

        createGraphArea.getChildren().addAll(
                title,
                addNodeButton,
                addEdgeButton,
                removeNodeButton,
                removeEdgeButton,
                updateNodeButton,
                updateEdgeButton);

        principal.setCenter(createGraphArea);
    }

    private void viewGraph() {
        System.out.println("viewGraph");
        BorderPane root = new BorderPane();
        if (graphController.getGraph() != null && !graphController.getGraph().getNodes().isEmpty()) {
            GraphView graphView = new GraphView();
            graphView.setGraph(graphController.getGraph());
            principal.setCenter(graphView.getGraphContainer());
        } else {
            Label noDataLabel = new Label("No hay datos en el grafo.");
            noDataLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            VBox noDataArea = new VBox(noDataLabel);
            noDataArea.setAlignment(Pos.CENTER);
            noDataArea.setPadding(new Insets(20));
            principal.setCenter(noDataArea);
        }
    }

    private void loadGraph() {
        System.out.println("Entro al metodo");
        BorderPane root = new BorderPane();
        Label loadGraphLabel = new Label("Cargar Grafo");
        loadGraphLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Button loadButton = new Button("Seleccionar archivo");
        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo CSV");
            System.out.println("Antes de elegir el archivo");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                graphController.loadGraphFromCSV(file.getAbsolutePath());
                viewGraph(); // Actualizar la vista con el grafo cargado
            }
        });
        VBox loadGraphArea = new VBox(10, loadGraphLabel, loadButton);
        loadGraphArea.setAlignment(Pos.CENTER);
        loadGraphArea.setPadding(new Insets(20));
        root.setCenter(loadGraphArea);
        principal.setCenter(root);
    }

    public void addNode() {
    	System.out.println("Cargando la vista para agregar nodo...");

        // Crear el BorderPane para la vista de agregar nodo
        BorderPane addNodePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);

        Label title = new Label("Agregar Nodo");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("ID del nodo");

        TextField nameField = new TextField();
        nameField.setPromptText("Nombre del nodo");

        TextField typeField = new TextField();
        typeField.setPromptText("Tipo del nodo");

        Button submitButton = new Button("Agregar Nodo");
        submitButton.setOnAction(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String type = typeField.getText();

            
        });

        content.getChildren().addAll(title, idField, nameField, typeField, submitButton);
        addNodePane.setCenter(content);

        // Establecer la vista en la posición central
        principal.setCenter(addNodePane);
    }

    public void addEdge() {
    	
    }

    public void removeNode() {
        // Vista para eliminar nodo
    }

    public void removeEdge() {
        
    }


    public void updateNode() {
        // Vista para actualizar nodo
    }

    public void updateEdge() {
    	
    }

    private void centralidad() {
        // Vista para centralidad
    }

    private void comunity() {
        // Vista para comunidades
    }

    private void sale() {
        // Vista para los productos más vendidos
    }
    
    
}