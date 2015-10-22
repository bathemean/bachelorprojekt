package main.graph;

import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphLoader {

    final static Charset ENCODING = StandardCharsets.UTF_8;

    private ArrayList<String> vertices = new ArrayList<String>();
    private ArrayList<Object[]> edges = new ArrayList<Object[]>();

    public GraphLoader() {
        //System.out.println(loadCondMat2005());
    }

    public uwGraph loadCondMat2005() {

        try {
            readFile("/Users/blackapple/Dropbox/Uni/BP/graphs/cond-mat-2005.gml");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            uwGraph graph = new uwGraph(DefaultWeightedEdge.class);

            for (String v : this.vertices) {
                graph.addVertex(v);
            }

            for (Object[] e : this.edges) {
                graph.addEdge((String) e[0], (String) e[1]);
                Double weight = Double.valueOf((String) e[2]);
                graph.setEdgeWeight(graph.getEdge((String) e[0], (String) e[1]), weight);
            }

            return graph;
        }
    }

    void readFile(String aFileName) throws IOException {

        Path path = Paths.get(aFileName);

        Pattern nodePattern = Pattern.compile("^  node");
        Pattern edgePattern = Pattern.compile("^  edge");

        Scanner scanner = new Scanner(path, ENCODING.name());

        while (scanner.hasNextLine()) {
            //process each line in some way
            //log(scanner.nextLine());

            String line = scanner.nextLine(); // {Node|Edge}

            Matcher nodeMatcher = nodePattern.matcher(line);
            Matcher edgeMatcher = edgePattern.matcher(line);

            if (nodeMatcher.matches()) {

                scanner.nextLine(); // [
                String[] id = scanner.nextLine().trim().split(" "); // id x(int)
                String[] name = scanner.nextLine().trim().split(" "); // label name(String)
                scanner.nextLine(); // ]

                vertices.add(id[1]);

            } else if (edgeMatcher.matches()) {
                scanner.nextLine(); // [
                String[] source = scanner.nextLine().trim().split(" "); // source x(int)
                String[] target = scanner.nextLine().trim().split(" "); // target x(int)
                String[] weight = scanner.nextLine().trim().split(" "); // value x(double)

                if (!weight[1].equals("inf")) { // Don't include infinite edges
                    Object[] edge = new Object[3];
                    edge[0] = source[1];
                    edge[1] = target[1];
                    edge[2] = weight[1];

                    edges.add(edge);
                }
            }
        }
    }
}
