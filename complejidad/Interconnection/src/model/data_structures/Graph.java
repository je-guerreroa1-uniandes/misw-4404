package model.data_structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph <K extends Comparable<K>, V extends Comparable<V>>{
    private Map<K, Vertex<K, V>> vertices;
    private ILista<Edge<K, V>> arcos;

    public Graph() {
        vertices = new HashMap<>();
        this.arcos= new ArregloDinamico<Edge<K, V>>(1);
    }

    public void addVertex(K key, V value) {
        vertices.put(key, new Vertex<>(key, value));
    }

    public Vertex<K, V> getVertex(K key) {
        return vertices.get(key);
    }

    public ILista<Edge<K,V>> edges()
    {
        return arcos;
    }

    public void addEdge( Edge<K,V> edge )
    {
        try {
            arcos.insertElement(edge, arcos.size() +1);
        } catch (PosException | NullException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean containsVertex(K id) {
        return vertices.containsKey(id);
    }

    public Edge<K,V> getEdge(K vertex)
    {
        Edge<K,V> retorno=null;
        for(int i=1; i<=arcos.size(); i++)
        {
            try
            {
                if(arcos.getElement(i).getDestination().getId().compareTo(vertex)==0)
                {
                    retorno= arcos.getElement(i);
                }
            }
            catch (PosException | VacioException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return retorno;

    }

    public ILista<Vertex<K,V>> vertices()
    {
        ILista<Vertex<K,V>> retorno=new ArregloDinamico<>(1);
        for(int i=1; i<=arcos.size(); i++)
        {
            try {
                retorno.insertElement(arcos.getElement(i).getDestination(), retorno.size()+1);
            } catch (PosException | NullException | VacioException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return retorno;
    }

    public ILista<Edge<K, V>> mstPrimLazy(K startVertexKey) {
        Vertex<K, V> startVertex = vertices.get(startVertexKey);
        if (startVertex == null) {
            return new ArregloDinamico<Edge<K, V>>(0);
        }

        ILista<Edge<K, V>> mst = new ArregloDinamico<Edge<K, V>>(vertices.size());
        MinPQ<Float, Edge<K, V>> cola = new MinPQ<Float, Edge<K, V>>(vertices.size());

        addEdgesToMinPQ(cola, startVertex);

        while (!cola.isEmpty()) {
            Edge<K, V> actual = cola.delMin().getValue();
            Vertex<K, V> dest = actual.getDestination();
            if (!dest.getMark()) {
                try {
                    mst.insertElement(actual, mst.size() + 1);
                } catch (PosException | NullException e) {
                    e.printStackTrace();  // Handle exception appropriately
                }
                addEdgesToMinPQ(cola, dest);
            }
        }
        return mst;
    }

    private void addEdgesToMinPQ(MinPQ<Float, Edge<K, V>> cola, Vertex<K, V> vertex) {
        vertex.mark();

        ILista<Edge<K, V>> allEdges = this.edges(); // Retrieve all edges from the Graph

        for (int i = 1; i <= allEdges.size(); i++) {
            try {
                Edge<K, V> edge = allEdges.getElement(i);
                if (edge.getSource().equals(vertex) || edge.getDestination().equals(vertex)) { // For undirected graph
                    cola.insert(edge.getWeight(), edge);
                }
            } catch (PosException | VacioException e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        }
    }

    public ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> minPathTree(K startVertexKey) {
        ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> shortestPaths = new TablaHashLinearProbing<>(vertices.size());
        MinPQIndexada<Float, K, Edge<K, V>> pq = new MinPQIndexada<>(vertices.size());

        shortestPaths.put(startVertexKey, new NodoTS<>(0f, null));
        pq.insert(0f, startVertexKey, null);

        while (!pq.isEmpty()) {
            NodoTS<Float, Edge<K, V>> nodo = pq.delMin();
            K vertexKey = nodo.getValue().getDestination().getId();
            relaxDijkstra(shortestPaths, pq, vertexKey, nodo.getKey());
        }

        return shortestPaths;
    }

    private void relaxDijkstra(ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> shortestPaths, MinPQIndexada<Float, K, Edge<K, V>> pq, K vertexKey, float accumulatedWeight) {
        // Assuming getEdgesFromVertex returns ILista<Edge<K, V>>
        ILista<Edge<K, V>> edgesFromVertex = getEdgesFromVertex(vertexKey);

        for (int i = 1; i <= edgesFromVertex.size(); i++) {
            try {
                Edge<K, V> edge = edgesFromVertex.getElement(i);
                Vertex<K, V> dest = edge.getDestination();
                float edgeWeight = edge.getWeight();
                float newWeight = accumulatedWeight + edgeWeight;

                NodoTS<Float, Edge<K, V>> currentDest = shortestPaths.get(dest.getId());
                if (currentDest == null || newWeight < currentDest.getKey()) {
                    shortestPaths.put(dest.getId(), new NodoTS<>(newWeight, edge));
                    pq.insert(newWeight, dest.getId(), edge);
                }
            } catch (PosException | VacioException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }


    public ILista<Edge<K, V>> getEdgesFromVertex(K vertexKey) {
        ILista<Edge<K, V>> edgesFromVertex = new ArregloDinamico<>(10);
        for (int i = 1; i <= arcos.size(); i++) { // Assuming indexing starts at 1
            try {
                Edge<K, V> edge = arcos.getElement(i);
                if (edge.getSource().getId().equals(vertexKey)) {
                    edgesFromVertex.insertElement(edge, edgesFromVertex.size() + 1); // Adjust as per your method signature
                }
            } catch (PosException | VacioException | NullException e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        }
        return edgesFromVertex;
    }

    public Edge<K, V> getEdgeBetween(K sourceKey, K destKey) {
        for (int i = 1; i <= arcos.size(); i++) {
            try {
                Edge<K, V> edge = arcos.getElement(i);
                if (edge.getSource().getId().equals(sourceKey) && edge.getDestination().getId().equals(destKey)) {
                    return edge;
                }
            } catch (PosException | VacioException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
        return null; // Return null if no edge found
    }

    public ILista<Vertex<K, V>> getAdjacentVertices(K vertexKey) {
        ILista<Vertex<K, V>> adjacentVertices = new ArregloDinamico<>(10); // Initialize with an expected capacity
        ILista<Edge<K, V>> allEdges = edges(); // Retrieve all edges from the Graph

        for (int i = 1; i <= allEdges.size(); i++) { // Iterate through all edges
            try {
                Edge<K, V> edge = allEdges.getElement(i);
                if (edge.getSource().getId().equals(vertexKey)) {
                    adjacentVertices.insertElement(edge.getDestination(), adjacentVertices.size() + 1); // Add adjacent vertex
                }
            } catch (PosException | VacioException | NullException e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        }

        return adjacentVertices;
    }

    public int numVertices() {
        return vertices.size();
    }

    // Method to unmark all vertices in the graph
    public void unmarkAllVertices() {
        for (Vertex<K, V> vertex : vertices.values()) {
            vertex.unmark();
        }
    }

    public int indegree(K vertexKey) {
        int indegree = 0;
        ILista<Edge<K, V>> allEdges = edges();
        for (int i = 1; i <= allEdges.size(); i++) {
            try {
                Edge<K, V> edge = allEdges.getElement(i);
                if (edge.getDestination().getId().equals(vertexKey)) {
                    indegree++;
                }
            } catch (PosException | VacioException e) {
                e.printStackTrace(); // Handle exception
            }
        }
        return indegree;
    }

    public int outdegree(K vertexKey) {
        int outdegree = 0;
        ILista<Edge<K, V>> allEdges = edges();
        for (int i = 1; i <= allEdges.size(); i++) {
            try {
                Edge<K, V> edge = allEdges.getElement(i);
                if (edge.getSource().getId().equals(vertexKey)) {
                    outdegree++;
                }
            } catch (PosException | VacioException e) {
                e.printStackTrace(); // Handle exception
            }
        }
        return outdegree;
    }

}
