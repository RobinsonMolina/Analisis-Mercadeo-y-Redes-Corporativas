package co.edu.uptc.view;

import co.edu.uptc.controller.GraphController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import co.edu.uptc.model.entities.Node;

import javafx.scene.control.TextField;

import java.io.File;
import java.util.Map;

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
        option.setStyle("-fx-background-color: #f0f8ff;");
        option.setPrefWidth(200);
        option.setPrefHeight(600); 
        option.setAlignment(Pos.CENTER);

        Label menuTitle = new Label("Menú");
        menuTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button createGraphButton = createStyledButton("Crear grafo");
        Button viewGraphButton = createStyledButton("Ver grafo");
        Button loadGraphButton = createStyledButton("Cargar grafo");
        Button centralidadButton = createStyledButton("Centralidad");
        Button comunityButton = createStyledButton("Comunidades");
        Button saleButton = createStyledButton("Similitudes");

        createGraphButton.setOnAction(e -> showCreateGraphMenu());
        viewGraphButton.setOnAction(e -> viewGraph());
        loadGraphButton.setOnAction(e -> loadGraph());
        centralidadButton.setOnAction(e -> centralidad());
        comunityButton.setOnAction(e -> comunity());
        saleButton.setOnAction(e -> sale());

        option.getChildren().addAll(
            menuTitle,
            new Separator(),
            createGraphButton,
            viewGraphButton,
            loadGraphButton,
            centralidadButton,
            comunityButton,
            saleButton
        );
        return option;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-font-size: 12px; " +
            "-fx-font-weight: bold; " +
            "-fx-background-color: #4CAF50; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5px; " +
            "-fx-padding: 10;"
        );
        button.setOnMouseEntered(e -> button.setStyle(
            "-fx-font-size: 12px; " +
            "-fx-font-weight: bold; " +
            "-fx-background-color: #45a049; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5px; " +
            "-fx-padding: 10;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
            "-fx-font-size: 12px; " +
            "-fx-font-weight: bold; " +
            "-fx-background-color: #4CAF50; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5px; " +
            "-fx-padding: 10;"
        ));
        return button;
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
        createGraphArea.setAlignment(Pos.CENTER);
        
        createGraphArea.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Opciones de creación");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button addNodeButton = createStyledButton("Añadir Nodo", "#4CAF50");
        Button addEdgeButton = createStyledButton("Añadir Arista", "#2196F3");
        Button removeNodeButton = createStyledButton("Eliminar Nodo", "#FF5722");
        Button removeEdgeButton = createStyledButton("Eliminar Arista", "#FFC107");
        Button updateNodeButton = createStyledButton("Actualizar Nodo", "#9C27B0");
        Button updateEdgeButton = createStyledButton("Actualizar Arista", "#3F51B5");

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
    
    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: white;" +
            "-fx-background-color: " + color + ";" +
            "-fx-background-radius: 5;" +
            "-fx-border-radius: 5;" +
            "-fx-padding: 10;"
        );
        button.setPrefWidth(200); // Establecer un ancho fijo para los botones
        return button;
    }

    private void viewGraph() {
        System.out.println("viewGraph");
        BorderPane root = new BorderPane();
        if (graphController.getGraph() != null && !graphController.getGraph().getNodes().isEmpty()) {
            GraphView graphView = new GraphView();
            GraphController.getInstance().getGraph();
            principal.setCenter(graphView.getGraphContainer());
        } else {
            Label noDataLabel = new Label("No hay datos en el grafo.");
            noDataLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            VBox noDataArea = new VBox(noDataLabel);
            noDataArea.setAlignment(Pos.CENTER);
            noDataArea.setPadding(new Insets(20));
            noDataArea.setStyle("-fx-background-color: #f0f8ff;");
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
        loadGraphArea.setStyle("-fx-background-color: #f0f8ff;");
        root.setCenter(loadGraphArea);
        principal.setCenter(root);
    }

    public void addNode() {
        System.out.println("Cargando la vista para agregar nodo...");
        BorderPane addNodePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Agregar Nodo");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("ID del nodo");

        TextField nameField = new TextField();
        nameField.setPromptText("Nombre del nodo");

        TextField typeField = new TextField();
        typeField.setPromptText("Tipo del nodo");

        Button submitButton = new Button("Agregar Nodo");
        styleButton(submitButton);
        submitButton.setOnAction(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String type = typeField.getText();

            if (id.isEmpty() || name.isEmpty() || type.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben ser completados.");
            }else if (graphController.isNodeIdAlreadyRegistered(id)) {
                showAlert(Alert.AlertType.ERROR, "Error", "El ID del nodo ya ha sido registrado.");
            } else if (graphController.isNodeNameAlreadyRegistered(name)) {
                showAlert(Alert.AlertType.ERROR, "Error", "El nombre del nodo ya ha sido registrado.");
            }else {
                graphController.addNode(id, name, type);
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Nodo agregado correctamente.");
            }
        });

        content.getChildren().addAll(title, idField, nameField, typeField, submitButton);
        addNodePane.setCenter(content);
        principal.setCenter(addNodePane);
    }
    
    

    private void styleButton(Button button) {
    	button.setStyle(
    		    "-fx-background-color: #2196F3; " + 
    		    "-fx-text-fill: white; " +         
    		    "-fx-font-weight: bold; " +        
    		    "-fx-background-radius: 5;"       
    		);
	}
    
    private void styleButtonDelete(Button button) {
    	button.setStyle(
    		    "-fx-background-color: #FF0000; " + 
    		    "-fx-text-fill: white; " +         
    		    "-fx-font-weight: bold; " +        
    		    "-fx-background-radius: 5;"       
    		);
	}

	public void addEdge() {
        System.out.println("Cargando la vista para agregar arista...");
        BorderPane addEdgePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Agregar Arista");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField sourceField = new TextField();
        sourceField.setPromptText("ID nodo fuente");

        TextField targetField = new TextField();
        targetField.setPromptText("ID nodo destino");

        TextField weightField = new TextField();
        weightField.setPromptText("Peso de la arista");

        Button submitButton = new Button("Agregar Arista");
        styleButton(submitButton);
        submitButton.setOnAction(e -> {
            String sourceId = sourceField.getText();
            String targetId = targetField.getText();
            String weightText = weightField.getText();

            if (sourceId.isEmpty() || targetId.isEmpty() || weightText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben ser completados.");
                return;
            }else if (sourceId.equalsIgnoreCase(targetId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "No se puede crear una relacion con la misma entidad");
                return;
            }
            
            try {
                double weight = Double.parseDouble(weightText);
                graphController.addEdge(sourceId, targetId, weight);
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Arista agregada correctamente.");
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "El peso debe ser un número válido.");
            }
        });

        content.getChildren().addAll(title, sourceField, targetField, weightField, submitButton);
        addEdgePane.setCenter(content);
        principal.setCenter(addEdgePane);
    }

    public void removeNode() {
        BorderPane removeNodePane = new BorderPane();
        VBox content = new VBox(10);
        boolean result = false; 
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Eliminar Nodo");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("ID del nodo a eliminar");

        Button submitButton = new Button("Eliminar Nodo");
        styleButtonDelete(submitButton);
        submitButton.setOnAction(e -> {
            String nodeId = idField.getText();

            if (nodeId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "El ID del nodo no puede estar vacío.");
                return;
            }

            GraphController.getInstance().removeNode(nodeId);

            if (result) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Nodo eliminado correctamente.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se encontró el nodo con el ID especificado.");
            }
        });

        content.getChildren().addAll(title, idField, submitButton);
        removeNodePane.setCenter(content);
        principal.setCenter(removeNodePane);
    }

    public void removeEdge() {
        System.out.println("Cargando la vista para eliminar arista...");

        BorderPane removeEdgePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Eliminar Arista");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField sourceField = new TextField();
        sourceField.setPromptText("ID nodo fuente");

        TextField targetField = new TextField();
        targetField.setPromptText("ID nodo destino");

        Button submitButton = new Button("Eliminar Arista");
        styleButtonDelete(submitButton);
        submitButton.setOnAction(e -> {
            String sourceId = sourceField.getText();
            String targetId = targetField.getText();

            if (sourceId.isEmpty() || targetId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben ser completados.");
                return;
            }else if (sourceId.equalsIgnoreCase(targetId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Los ID son los mismos.");
                return;
            }else if (!graphController.isNodeIdAlreadyRegistered(sourceId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "El nodo fuente no existe.");
                return;
            }else if (!graphController.isNodeIdAlreadyRegistered(targetId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "El nodo destino no existe.");
                return;
            }


            graphController.removeEdge(sourceId, targetId);
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Arista eliminada correctamente.");
        });

        content.getChildren().addAll(title, sourceField, targetField, submitButton);
        removeEdgePane.setCenter(content);

        principal.setCenter(removeEdgePane);
    }

    public void updateNode() {
        BorderPane updateNodePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Actualizar Nodo");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("ID del nodo a actualizar");

        TextField nameField = new TextField();
        nameField.setPromptText("Nuevo nombre del nodo");

        TextField typeField = new TextField();
        typeField.setPromptText("Nuevo tipo del nodo");

        Button submitButton = new Button("Actualizar Nodo");
        styleButton(submitButton);
        submitButton.setOnAction(e -> {
            String nodeId = idField.getText();
            String name = nameField.getText();
            String type = typeField.getText();

            if (nodeId.isEmpty() || name.isEmpty() || type.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben ser completados.");
                return;
            }

            boolean result = graphController.updateNode(nodeId, name, type);

            if (result) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Nodo actualizado correctamente.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se encontró el nodo con el ID especificado.");
            }
        });

        content.getChildren().addAll(title, idField, nameField, typeField, submitButton);
        updateNodePane.setCenter(content);
        principal.setCenter(updateNodePane);
    }

    public void updateEdge() {
        System.out.println("Cargando la vista para actualizar arista...");

        BorderPane updateEdgePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Actualizar Arista");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField sourceField = new TextField();
        sourceField.setPromptText("ID nodo fuente");

        TextField targetField = new TextField();
        targetField.setPromptText("ID nodo destino");

        TextField weightField = new TextField();
        weightField.setPromptText("Nuevo peso de la arista");

        Button submitButton = new Button("Actualizar Arista");
        styleButton(submitButton);
        submitButton.setOnAction(e -> {
            String sourceId = sourceField.getText();
            String targetId = targetField.getText();
            String weightText = weightField.getText();

            if (sourceId.isEmpty() || targetId.isEmpty() || weightText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben ser completados.");
                return;
            }

            try {
                double newWeight = Double.parseDouble(weightText);

                boolean result = graphController.updateEdge(sourceId, targetId, newWeight);

                if (result) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Arista actualizada correctamente.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se encontró la arista para actualizar.");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "El peso debe ser un número válido.");
            }
        });

        content.getChildren().addAll(title, sourceField, targetField, weightField, submitButton);
        updateEdgePane.setCenter(content);

        principal.setCenter(updateEdgePane);
    }

    private void centralidad() {
    VBox centralityMenu = new VBox(10);
    centralityMenu.setPadding(new Insets(20));
    centralityMenu.setAlignment(Pos.CENTER);

    Label title = new Label("Análisis de Centralidad");
    title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

    Button degreeCentralityButton = new Button("Centralidad de Grado");
    Button betweennessCentralityButton = new Button("Centralidad de Intermediación");
    Button closenessCentralityButton = new Button("Centralidad de Cercanía");

    TextArea resultsArea = new TextArea();
    resultsArea.setEditable(false);
    resultsArea.setPromptText("Resultados se mostrarán aquí...");

    degreeCentralityButton.setOnAction(e -> {
        Map<Node, Integer> degreeResults = graphController.calculateDegreeCentrality();
        StringBuilder results = new StringBuilder("Centralidad de Grado:\n");
        degreeResults.forEach((node, degree) -> 
            results.append(node.getName()).append(": ").append(degree).append("\n"));
        resultsArea.setText(results.toString());
    });

    betweennessCentralityButton.setOnAction(e -> {
        Map<Node, Double> betweennessResults = graphController.calculateBetweennessCentrality();
        StringBuilder results = new StringBuilder("Centralidad de Intermediación:\n");
        betweennessResults.forEach((node, betweenness) -> 
            results.append(node.getName()).append(": ").append(betweenness).append("\n"));
        resultsArea.setText(results.toString());
    });

    closenessCentralityButton.setOnAction(e -> {
        Map<Node, Double> closenessResults = graphController.calculateClosenessCentrality();
        StringBuilder results = new StringBuilder("Centralidad de Cercanía:\n");
        closenessResults.forEach((node, closeness) -> 
            results.append(node.getName()).append(": ").append(closeness).append("\n"));
        resultsArea.setText(results.toString());
    });

    centralityMenu.getChildren().addAll(title, degreeCentralityButton, betweennessCentralityButton, closenessCentralityButton, resultsArea);
    principal.setCenter(centralityMenu);
}


    private void comunity() {
        // Vista para comunidades
    }

    private void sale() {
        // Vista para los productos más vendidos
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    

}