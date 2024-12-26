import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;

public class GraphGUI extends JFrame {

    private Graph<String> graph;
    private JTextArea outputArea;
    private JTextField vertexField1, vertexField2;
    private GraphPanel graphPanel;
    private JFrame graphFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphGUI gui = new GraphGUI();
            gui.setVisible(true);
        });
    }

    public GraphGUI() {
        // Инициализация графа и GUI
        graph = new ListGraph<>();
        setTitle("Граф");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Создание текстовой области для вывода
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Создание панели для кнопок и полей ввода
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        // Поля для ввода вершин
        vertexField1 = new JTextField(10);
        vertexField2 = new JTextField(10);
        inputPanel.add(new JLabel("Вершина 1:"));
        inputPanel.add(vertexField1);
        inputPanel.add(new JLabel("Вершина 2:"));
        inputPanel.add(vertexField2);

        // Создание кнопок с ActionListener
        JButton addPairButton = new JButton("Добавить пару вершин");
        addPairButton.addActionListener(e -> {
            addPair();
            updateGraphPanel();
        });
        inputPanel.add(addPairButton);

        JButton addVertexButton = new JButton("Добавить вершину");
        addVertexButton.addActionListener(e -> {
            addVertex();
            updateGraphPanel();
        });
        inputPanel.add(addVertexButton);

        JButton addEdgeButton = new JButton("Добавить связь");
        addEdgeButton.addActionListener(e -> {
            addEdge();
            updateGraphPanel();
        });
        inputPanel.add(addEdgeButton);

        JButton getNeighborsButton = new JButton("Получить соседей");
        getNeighborsButton.addActionListener(e -> getNeighbors());
        inputPanel.add(getNeighborsButton);

        JButton dfsButton = new JButton("DFS");
        dfsButton.addActionListener(e -> performDFS());
        inputPanel.add(dfsButton);

        JButton bfsButton = new JButton("BFS");
        bfsButton.addActionListener(e -> performBFS());
        inputPanel.add(bfsButton);

        JButton getAllVerticesButton = new JButton("Все вершины");
        getAllVerticesButton.addActionListener(e -> getAllVertices());
        inputPanel.add(getAllVerticesButton);

        JButton removeVertexButton = new JButton("Удалить вершину");
        removeVertexButton.addActionListener(e -> {
            removeVertex();
            updateGraphPanel();
        });
        inputPanel.add(removeVertexButton);

        JButton showGraphButton = new JButton("Показать граф");
        showGraphButton.addActionListener(e -> showGraphVisualization());
        inputPanel.add(showGraphButton);

        JButton checkConnectionButton = new JButton("Проверить связь");
        checkConnectionButton.addActionListener(e -> checkConnection());
        inputPanel.add(checkConnectionButton);



        add(inputPanel, BorderLayout.NORTH);
    }

    private void updateGraphPanel() {
        if (graphPanel != null) {
            graphPanel.updateGraph(graph);
        }
    }

    private void addPair() {
        String vertex1 = vertexField1.getText().trim();
        String vertex2 = vertexField2.getText().trim();
        if (vertex1.isEmpty() || vertex2.isEmpty()) {
            outputArea.append("Введите обе вершины.\n");
            return;
        }
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        outputArea.append("Добавлены вершины: " + vertex1 + " и " + vertex2 + "\n");
        vertexField1.setText("");
        vertexField2.setText("");
    }

    private void addVertex() {
        String vertex = vertexField1.getText().trim();
        if (vertex.isEmpty()) {
            outputArea.append("Введите вершину.\n");
            return;
        }
        graph.addVertex(vertex);
        outputArea.append("Добавлена вершина: " + vertex + "\n");
        vertexField1.setText("");
    }

    private void addEdge() {
        String vertex1 = vertexField1.getText().trim();
        String vertex2 = vertexField2.getText().trim();
        if (vertex1.isEmpty() || vertex2.isEmpty()) {
            outputArea.append("Введите обе вершины для связи.\n");
            return;
        }
        try {
            graph.addEdge(vertex1, vertex2);
            outputArea.append("Связаны вершины: " + vertex1 + " и " + vertex2 + "\n");
        } catch (IllegalArgumentException e) {
            outputArea.append(e.getMessage() + "\n");
        }
        vertexField1.setText("");
        vertexField2.setText("");
    }

    private void getNeighbors() {
        String vertex = vertexField1.getText().trim();
        if (vertex.isEmpty()) {
            outputArea.append("Введите вершину.\n");
            return;
        }
        try {
            List<String> neighbors = graph.getNeighbors(vertex);
            outputArea.append("Соседи вершины " + vertex + ": " + neighbors + "\n");
        } catch (IllegalArgumentException e) {
            outputArea.append(e.getMessage() + "\n");
        }
        vertexField1.setText("");
    }

    private void performDFS() {
        String startVertex = vertexField1.getText().trim();
        if (startVertex.isEmpty()) {
            outputArea.append("Введите начальную вершину для DFS.\n");
            return;
        }
        try {
            List<String> dfsResult = graph.depthFirstSearch(startVertex);
            outputArea.append("DFS обход: " + dfsResult + "\n");
        } catch (IllegalArgumentException e) {
            outputArea.append(e.getMessage() + "\n");
        }
        vertexField1.setText("");
    }

    private void performBFS() {
        String startVertex = vertexField1.getText().trim();
        if (startVertex.isEmpty()) {
            outputArea.append("Введите начальную вершину для BFS.\n");
            return;
        }
        try {
            List<String> bfsResult = graph.breadthFirstSearch(startVertex);
            outputArea.append("BFS обход: " + bfsResult + "\n");
        } catch (IllegalArgumentException e) {
            outputArea.append(e.getMessage() + "\n");
        }
        vertexField1.setText("");
    }

    private void getAllVertices() {
        List<String> vertices = graph.getAllVertices();
        outputArea.append("Все вершины: " + vertices + "\n");
    }

    private void removeVertex() {
        String vertex = vertexField1.getText().trim();
        if (vertex.isEmpty()) {
            outputArea.append("Введите вершину для удаления.\n");
            return;
        }
        try {
            graph.removeVertex(vertex);
            outputArea.append("Удалена вершина: " + vertex + "\n");
        } catch (IllegalArgumentException e) {
            outputArea.append(e.getMessage() + "\n");
        }
        vertexField1.setText("");
    }

    private void checkConnection() {
        String vertex1 = vertexField1.getText().trim();
        String vertex2 = vertexField2.getText().trim();
        if (vertex1.isEmpty() || vertex2.isEmpty()) {
            outputArea.append("Введите обе вершины для проверки связи.\n");
            return;
        }
        try {
            boolean isConnected = graph.hasEdge(vertex1, vertex2);
            outputArea.append("Связь между " + vertex1 + " и " + vertex2 + ": " + isConnected + "\n");
        } catch (IllegalArgumentException e) {
            outputArea.append(e.getMessage() + "\n");
        }
        vertexField1.setText("");
        vertexField2.setText("");
    }

    private void showGraphVisualization() {
        if (graphFrame == null || !graphFrame.isVisible()) {
            graphPanel = new GraphPanel(graph);
            graphFrame = new JFrame("Визуализация графа");
            graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            graphFrame.getContentPane().add(graphPanel);
            graphFrame.pack();
            graphFrame.setLocationRelativeTo(null); // Центрируем окно
            graphFrame.setVisible(true);
        }
        graphPanel.updateGraph(graph); // Обновляем граф
        graphFrame.repaint();
    }

    // Интерфейс графа
    interface Graph<T> {
        void addVertex(T vertex);

        void addEdge(T vertex1, T vertex2);

        List<T> getNeighbors(T vertex);

        List<T> depthFirstSearch(T startVertex);

        List<T> breadthFirstSearch(T startVertex);

        List<T> getAllVertices();

        void removeVertex(T vertex);

        boolean hasEdge(T vertex1, T vertex2);

    }

    // Реализация графа на списках смежности
    static class ListGraph<T> implements Graph<T> {
        private Map<T, List<T>> adjacencyList;

        public ListGraph() {
            adjacencyList = new HashMap<>();
        }

        @Override
        public void addVertex(T vertex) {
            adjacencyList.putIfAbsent(vertex, new ArrayList<>());
        }

        @Override
        public void addEdge(T vertex1, T vertex2) {
            if (!adjacencyList.containsKey(vertex1) || !adjacencyList.containsKey(vertex2)) {
                throw new IllegalArgumentException("Одной или обеих вершин не существует в графе.");
            }
            adjacencyList.get(vertex1).add(vertex2);
            adjacencyList.get(vertex2).add(vertex1);  // Для неориентированного графа
        }

        @Override
        public List<T> getNeighbors(T vertex) {
            if (!adjacencyList.containsKey(vertex)) {
                throw new IllegalArgumentException("Вершины " + vertex + " не существует в графе.");
            }
            return adjacencyList.getOrDefault(vertex, Collections.emptyList());
        }

        @Override
        public List<T> depthFirstSearch(T startVertex) {
            if (!adjacencyList.containsKey(startVertex)) {
                throw new IllegalArgumentException("Вершины " + startVertex + " не существует в графе.");
            }
            List<T> visited = new ArrayList<>();
            Set<T> visitedSet = new HashSet<>();
            dfs(startVertex, visited, visitedSet);
            return visited;
        }

        private void dfs(T vertex, List<T> visited, Set<T> visitedSet) {
            visitedSet.add(vertex);
            visited.add(vertex);
            for (T neighbor : adjacencyList.get(vertex)) {
                if (!visitedSet.contains(neighbor)) {
                    dfs(neighbor, visited, visitedSet);
                }
            }
        }

        @Override
        public List<T> breadthFirstSearch(T startVertex) {
            if (!adjacencyList.containsKey(startVertex)) {
                throw new IllegalArgumentException("Вершины " + startVertex + " не существует в графе.");
            }
            List<T> visited = new ArrayList<>();
            Set<T> visitedSet = new HashSet<>();
            Queue<T> queue = new LinkedList<>();
            queue.add(startVertex);
            visitedSet.add(startVertex);
            while (!queue.isEmpty()) {
                T vertex = queue.poll();
                visited.add(vertex);
                for (T neighbor : adjacencyList.get(vertex)) {
                    if (!visitedSet.contains(neighbor)) {
                        queue.add(neighbor);
                        visitedSet.add(neighbor);
                    }
                }
            }
            return visited;
        }

        @Override
        public List<T> getAllVertices() {
            return new ArrayList<>(adjacencyList.keySet());
        }

        @Override
        public void removeVertex(T vertex) {
            if (!adjacencyList.containsKey(vertex)) {
                throw new IllegalArgumentException("Вершины " + vertex + " не существует в графе.");
            }
            adjacencyList.remove(vertex);
            for (List<T> neighbors : adjacencyList.values()) {
                neighbors.remove(vertex);
            }
        }

        @Override
        public boolean hasEdge(T vertex1, T vertex2) {
            if (!adjacencyList.containsKey(vertex1) || !adjacencyList.containsKey(vertex2)) {
                throw new IllegalArgumentException("Одной или обеих вершин не существует в графе.");
            }
            return adjacencyList.get(vertex1).contains(vertex2);
        }
    }

    static class GraphPanel extends JPanel {
        private Graph<String> graph;
        private Map<String, Point> vertexPositions = new HashMap<>();
        private static final int VERTEX_RADIUS = 20;

        public GraphPanel(Graph<String> graph) {
            this.graph = graph;
            setPreferredSize(new Dimension(600, 400));
        }

        public void updateGraph(Graph<String> graph) {
            this.graph = graph;
            this.vertexPositions.clear(); // Очищаем старые позиции
            if (graph != null) {
                calculateVertexPositions(); // пересчитываем
            }
            repaint(); // вызываем перерисовку
        }

        private void calculateVertexPositions() {
            if (graph == null || graph.getAllVertices().isEmpty()) {
                return; // если вершин нет
            }
            List<String> vertices = graph.getAllVertices();
            int numVertices = vertices.size();
            double angleIncrement = 2 * Math.PI / numVertices; // Угол для каждой вершины

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int radius = Math.min(getWidth(), getHeight()) / 2 - 50; // Радиус окружности для расположения вершин

            for (int i = 0; i < numVertices; i++) {
                String vertex = vertices.get(i);
                double angle = i * angleIncrement;
                int x = centerX + (int) (radius * Math.cos(angle));
                int y = centerY + (int) (radius * Math.sin(angle));
                vertexPositions.put(vertex, new Point(x, y));
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (graph == null || vertexPositions.isEmpty()) {
                return; // если нечего рисовать
            }

            // Рисуем ребра
            for (String vertex1 : graph.getAllVertices()) {
                Point p1 = vertexPositions.get(vertex1);
                if (p1 == null) continue; // если вершина не имеет позиции
                for (String vertex2 : graph.getNeighbors(vertex1)) {
                    Point p2 = vertexPositions.get(vertex2);
                    if (p2 == null) continue; // если соседняя вершина не имеет позиции
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }

            // Рисуем вершины
            for (Map.Entry<String, Point> entry : vertexPositions.entrySet()) {
                String vertex = entry.getKey();
                Point point = entry.getValue();
                Ellipse2D.Double circle = new Ellipse2D.Double(point.x - VERTEX_RADIUS, point.y - VERTEX_RADIUS, 2 * VERTEX_RADIUS, 2 * VERTEX_RADIUS);
                g2d.setColor(Color.WHITE);
                g2d.fill(circle);
                g2d.setColor(Color.BLACK);
                g2d.draw(circle);
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(vertex);
                int textHeight = fm.getHeight();
                g2d.drawString(vertex, point.x - textWidth / 2, point.y + textHeight / 4);
            }
        }
    }
}