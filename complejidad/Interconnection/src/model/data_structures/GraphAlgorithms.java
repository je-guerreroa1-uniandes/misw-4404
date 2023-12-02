package model.data_structures;

public class GraphAlgorithms<K extends Comparable<K>, V extends Comparable<V>> {
    private Graph<K, V> graph; // Member variable for the graph instance
    // Constructor that takes a Graph instance
    public GraphAlgorithms(Graph<K, V> graph) {
        this.graph = graph;
    }

    public void bfs(K startVertexKey) {
        Vertex<K, V> startVertex = graph.getVertex(startVertexKey);
        if (startVertex == null) {
            return; // or handle this case appropriately
        }

        ColaEncadenada<Vertex<K, V>> queue = new ColaEncadenada<>();
        startVertex.mark();
        queue.enqueue(startVertex);

        while (queue.peek() != null) {
            Vertex<K, V> current = queue.dequeue();
            ILista<Edge<K, V>> edges = graph.getEdgesFromVertex(current.getId()); // Use the graph instance to get edges

            for (int i = 1; i <= edges.size(); i++) {
                try {
                    Edge<K, V> edge = edges.getElement(i);
                    Vertex<K, V> destination = edge.getDestination();
                    if (!destination.getMark()) {
                        destination.mark();
                        queue.enqueue(destination);
                    }
                } catch (PosException | VacioException e) {
                    e.printStackTrace(); // handle exception
                }
            }
        }
    }

    public void dfs(K startVertexKey) {
        Vertex<K, V> currentVertex = graph.getVertex(startVertexKey);
        if (currentVertex == null || currentVertex.getMark()) {
            return;
        }

        currentVertex.mark(); // Mark the vertex as visited

        ILista<Edge<K, V>> edges = graph.getEdgesFromVertex(currentVertex.getId()); // Use the graph instance to get edges

        for (int i = 1; i <= edges.size(); i++) {
            try {
                Edge<K, V> edge = edges.getElement(i);
                Vertex<K, V> destination = edge.getDestination();
                if (!destination.getMark()) {
                    dfs(destination.getId()); // Recursive DFS call with vertex key
                }
            } catch (PosException | VacioException e) {
                e.printStackTrace(); // Handle exception
            }
        }
    }

    public void topologicalOrder(ILista<Vertex<K, V>> vertices, ColaEncadenada<Vertex<K, V>> pre, ColaEncadenada<Vertex<K, V>> post, PilaEncadenada<Vertex<K, V>> reversePost) {
        for (int i = 0; i < vertices.size(); i++) {
            Vertex<K, V> vertex = null;
            try {
                vertex = vertices.getElement(i);
            } catch (PosException | VacioException e) {
                e.printStackTrace();
            }

            if (vertex != null && !vertex.getMark()) {
                dfsTopologicalOrder(vertex.getId(), pre, post, reversePost);
            }
        }
    }

    private void dfsTopologicalOrder(K vertexKey, ColaEncadenada<Vertex<K, V>> pre, ColaEncadenada<Vertex<K, V>> post, PilaEncadenada<Vertex<K, V>> reversePost) {
        Vertex<K, V> vertex = graph.getVertex(vertexKey);
        if (vertex == null || vertex.getMark()) {
            return;
        }

        vertex.mark();
        pre.enqueue(vertex);

        ILista<Edge<K, V>> edges = graph.getEdgesFromVertex(vertexKey); // Use the graph instance to get edges

        for (int i = 1; i <= edges.size(); i++) {
            try {
                Edge<K, V> edge = edges.getElement(i);
                Vertex<K, V> destination = edge.getDestination();
                if (!destination.getMark()) {
                    dfsTopologicalOrder(destination.getId(), pre, post, reversePost); // Recursive call with vertex key
                }
            } catch (PosException | VacioException e) {
                e.printStackTrace(); // Handle exception
            }
        }

        post.enqueue(vertex);
        reversePost.push(vertex);
    }


    public void getSCC(K vertexKey, ITablaSimbolos<K, Integer> sccTable, int idComponente) {
        Vertex<K, V> vertex = graph.getVertex(vertexKey);
        if (vertex == null || vertex.getMark()) {
            return;
        }

        vertex.mark();
        sccTable.put(vertexKey, idComponente);

        ILista<Edge<K, V>> edges = graph.getEdgesFromVertex(vertexKey); // Use the graph instance to get edges
        for (int i = 1; i <= edges.size(); i++) {
            try {
                Edge<K, V> edge = edges.getElement(i);
                Vertex<K, V> destination = edge.getDestination();
                if (!destination.getMark()) {
                    getSCC(destination.getId(), sccTable, idComponente); // Recursive call with vertex key
                }
            } catch (PosException | VacioException e) {
                e.printStackTrace(); // Handle exception
            }
        }
    }

//    public ILista<Edge<K, V>> mstPrimLazy(K startVertexKey) {
//        // Assuming you have a way to get a vertex by key
//        Vertex<K, V> startVertex = getVertex(startVertexKey);
//        if (startVertex == null) {
//            // Handle the case where the vertex is not found
//            return null;
//        }
//
//        MinPQ<Float, Edge<K, V>> cola = new MinPQ<Float, Edge<K, V>>();
//        addEdgesToMinPQ(cola, startVertex);
//        while(!cola.isEmpty())
//        {
//            Edge<K, V> actual= cola.delMin(). getValue();
//            Vertex<K, V> dest= actual.getDestination();
//            if(!dest.marked)
//            {
//                try {
//                    mst.insertElement(actual, mst.size()+1);
//                } catch (PosException | NullException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                addEdgesToMinPQ(cola, dest);
//            }
//        }
//        return mst;
//
//    }
//
//    private void addEdgesToMinPQ(MinPQ<Float, Edge<K, V>> cola, Vertex<K, V> inicio)
//    {
//        inicio.mark();
//
//        for(int i=1; i<= inicio.edges().size(); i++)
//        {
//            Edge<K, V> actual=null;
//            try {
//                actual = inicio.edges().getElement(i);
//            } catch (PosException | VacioException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            cola.insert(actual.getWeight(), actual);
//        }
//    }
}
