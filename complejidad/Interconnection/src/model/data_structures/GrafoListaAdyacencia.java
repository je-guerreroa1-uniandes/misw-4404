package model.data_structures;

public class GrafoListaAdyacencia <K extends Comparable<K> ,V extends Comparable <V>>
{
	private int numEdges;
	private GraphAlgorithms<K, V> graphAlgorithms;
	private Graph<K, V> graph;

	public GrafoListaAdyacencia(int numVertices)
	{
		graph = new Graph<>();
		graphAlgorithms = new GraphAlgorithms<>(graph);
		numEdges = 0;
	}

	public boolean containsVertex(K id) {
		return graph.containsVertex(id);
	}

	public int numVertices()
	{
		return graph.numVertices();
	}

	public int numEdges()
	{
		return numEdges;
	}

	public void insertVertex(K id, V value) {
		graph.addVertex(id, value);
	}

	public void addEdge(K source, K dest, float weight) {
		if (!graph.containsVertex(source)) {
			graph.addVertex(source, null); // Assuming default value is null
		}
		if (!graph.containsVertex(dest)) {
			graph.addVertex(dest, null); // Assuming default value is null
		}

		Edge<K, V> edge = new Edge<>(graph.getVertex(source), graph.getVertex(dest), weight);
		graph.addEdge(edge);
		numEdges++;
	}

	public Vertex<K, V> getVertex(K id) {
		return graph.getVertex(id);
	}

	public Edge<K, V> getEdge(K idS, K idD) {
		return graph.getEdgeBetween(idS, idD);
	}

	public ILista<Edge<K, V>> adjacentEdges(K id) {
		return graph.getEdgesFromVertex(id);
	}

	public ILista<Vertex<K, V>> adjacentVertex(K id) {
		return graph.getAdjacentVertices(id);
	}

	public int indegree(K vertex) {
		return graph.indegree(vertex);
	}

	public int outdegree(K vertex) {
		return graph.outdegree(vertex);
	}

	public ILista<Edge<K, V>> edges() {
		return graph.edges();
	}

	public ILista<Vertex<K, V>> vertices() {
		return graph.vertices();
	}

	public void unmark()
	{
		ILista<Vertex<K, V>> vertices= vertices();
		for(int i=1; i<=vertices.size(); i++)
		{
			try {
				vertices.getElement(i).unmark();
			} catch (PosException | VacioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void dfs(K id) {
		Vertex<K, V> startVertex = getVertex(id);
		if (startVertex != null) {
			graphAlgorithms.dfs(id);
			unmarkAllVertices();
		}
	}

	public void bfs(K id) {
		Vertex<K, V> startVertex = getVertex(id);
		if (startVertex != null) {
			graphAlgorithms.bfs(id);
			unmarkAllVertices();
		}
	}

	public void unmarkAllVertices() {
		graph.unmarkAllVertices();
	}

	public Edge<K, V>  arcoMin()
	{
		Edge<K, V> minimo=null;
		try
		{
			minimo=graph.edges().getElement(1);
			float min=graph.edges().getElement(1).getWeight();
			for(int i=2; i<=graph.edges().size(); i++)
			{
				if(graph.edges().getElement(i).getWeight()< min)
				{
					minimo=graph.edges().getElement(i);
					min=graph.edges().getElement(i).getWeight();
				}
			}
		}
		catch (PosException | VacioException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return minimo;

	}

	public Edge<K, V>  arcoMax()
	{
		Edge<K, V> maximo=null;
		try
		{
			float max=0;
			for(int i=2; i<=graph.edges().size(); i++)
			{
				if(graph.edges().getElement(i).getWeight()> max)
				{
					maximo=graph.edges().getElement(i);
					max=graph.edges().getElement(i).getWeight();
				}
			}
		}
		catch (PosException | VacioException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return maximo;

	}

//	public Edge<K, V> arcoMin() {
//		return graph.minEdge();
//	}

//	public Edge<K, V> arcoMax() {
//		return graph.maxEdge();
//	}

	public GrafoListaAdyacencia<K, V> reverse()
	{
		GrafoListaAdyacencia<K, V> copia= new GrafoListaAdyacencia<K, V>(numVertices());
		ILista<Vertex<K, V>> vertices2= vertices();
		ILista<Edge<K, V>> arcos= edges();

		for(int i=1; i<= vertices2.size(); i++)
		{

			Vertex<K, V> actual;
			try {
				actual = vertices2.getElement(i);
				copia.insertVertex(actual.getId(), actual.getInfo());
			} catch (PosException | VacioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for(int i=1; i<= arcos.size(); i++)
		{
			Edge<K, V> actual;
			try {
				actual = arcos.getElement(i);
				copia.addEdge(actual.getDestination().getId(), actual.getSource().getId(), actual.getWeight());
			} catch (PosException | VacioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return copia;

	}

	public ITablaSimbolos<K, Integer> getSSC() {
		// Reverse the graph
		GrafoListaAdyacencia<K, V> reversedGraph = this.reverse();

		// Create an empty table for SCC results
		int initialCapacity = 10; // Adjust this value based on expected number of elements
		ITablaSimbolos<K, Integer> sccTable = new TablaHashLinearProbing<>(initialCapacity);

		// Iterate through each vertex in the reversed graph
		ILista<Vertex<K, V>> verticesList = reversedGraph.graph.vertices();
		int componentId = 1;
		for (int i = 1; i <= verticesList.size(); i++) {
			try {
				Vertex<K, V> vertex = verticesList.getElement(i);
				// For each unmarked vertex, compute its SCC
				if (!vertex.getMark()) {
					graphAlgorithms.getSCC(vertex.getId(), sccTable, componentId++);
				}
			} catch (PosException | VacioException e) {
				e.printStackTrace();
			}
		}

		// Unmark all vertices after computation
		reversedGraph.unmark();

		return sccTable;
	}

//	public GrafoListaAdyacencia<K, V> reverse() {
//		return graph.reverse();
//	}
//
//	public ITablaSimbolos<K, Integer> getSSC() {
//		return graph.getSSC();
//	}

//	public PilaEncadenada<Vertex<K, V>> topologicalOrder() {
//		return graphAlgorithms.topologicalOrder(graph);
//	}

	public PilaEncadenada<Vertex<K, V>> topologicalOrder() {
		// Create the queues and stack for pre, post, and reverse postorder.
		ColaEncadenada<Vertex<K, V>> pre = new ColaEncadenada<>();
		ColaEncadenada<Vertex<K, V>> post = new ColaEncadenada<>();
		PilaEncadenada<Vertex<K, V>> reversePost = new PilaEncadenada<>();

		// Retrieve the list of vertices from the Graph instance.
		ILista<Vertex<K, V>> verticesList = graph.vertices();

		// Call the topologicalOrder method from GraphAlgorithms.
		graphAlgorithms.topologicalOrder(verticesList, pre, post, reversePost);

		// Reset the marks on vertices
		graph.unmarkAllVertices();

		return reversePost;
	}
	public ILista<Edge<K, V>> mstPrimLazy(K idOrigen) {
		return graph.mstPrimLazy(idOrigen);
	}
//	public ILista<Edge<K, V>> mstPrimLazy(K idOrigen)
//	{
//		ILista<Edge<K, V>> mst= getVertex(idOrigen).mstPrimLazy();
//		unmark();
//		return mst;
//	}

//	private ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> minPathTree(K idOrigen)
//	{
//		Vertex<K, V> inicio= getVertex(idOrigen);
//		return inicio.minPathTree();
//
//	}
	private ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> minPathTree(K idOrigen) {
		return graph.minPathTree(idOrigen); // Use the method from Graph instance
	}

	public PilaEncadenada<Edge<K, V>> minPath(K idOrigen, K idDestino)
	{
		ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> tree= minPathTree(idOrigen);

		PilaEncadenada<Edge <K, V>> path= new PilaEncadenada<Edge<K, V>>();
		K idBusqueda= idDestino;
		NodoTS<Float, Edge<K, V>> actual;

		while( (actual=tree.get(idBusqueda))!=null && actual.getValue()!=null)
		{
			path.push(actual.getValue());
			idBusqueda=actual.getValue().getSource().getId();
		}

		unmark();
		return path;
	}
//	public PilaEncadenada<Edge<K, V>> minPath(K idOrigen, K idDestino) {
//		// Implement the logic for finding the minimum path
//		// This could involve calling Dijkstra's algorithm or another pathfinding algorithm
//		return graphAlgorithms.minPath(graph, idOrigen, idDestino);
//	}
}
