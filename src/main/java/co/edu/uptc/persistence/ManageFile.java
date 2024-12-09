package co.edu.uptc.persistence;

import co.edu.uptc.model.entities.Edge;
import co.edu.uptc.model.entities.Graph;
import co.edu.uptc.model.entities.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ManageFile {

    public ManageFile() {
    }

    public Graph loadGraphFromCSV(String filePath) {
        Graph graph = new Graph();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); 
            System.out.println("Encabezado leído: " + line);

            while ((line = br.readLine()) != null) {
                System.out.println("Procesando línea: " + line);

                String[] values = line.split(","); 
                if (values.length >= 4) {
                    try {
                        String source = values[0].trim();
                        String target = values[1].trim();
                        String type = values[2].trim();
                        double weight = Double.parseDouble(values[3].replace(",", "."));

                        Node sourceNode = graph.getNodeById(source);
                        if (sourceNode == null) {
                            sourceNode = new Node(source, source, type);
                            graph.addNode(sourceNode);
                        }

                        Node targetNode = graph.getNodeById(target);
                        if (targetNode == null) {
                            targetNode = new Node(target, target, type);
                            graph.addNode(targetNode);
                        }

                        graph.addEdge(sourceNode, targetNode, weight);
                    } catch (NumberFormatException e) {
                        System.out.println("Error al convertir el peso en línea: " + line);
                    }
                } else {
                    System.out.println("Línea no válida (longitud incorrecta): " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        }

        return graph;
    }


    
    public void saveGraphToCSV(Graph graph, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Origen,Destino,Tipo,Peso\n");

            for (Edge edge : graph.getEdges()) {
                String source = edge.getSource().getId();
                String target = edge.getTarget().getId();
                String type = edge.getSource().getType(); 
                double weight = edge.getWeight();
                writer.append(String.format("%s,%s,%s,%.2f\n", source, target, type, weight));
            }

            System.out.println("Grafo guardado exitosamente en " + filePath);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo CSV: " + e.getMessage());
        }
    }


}
