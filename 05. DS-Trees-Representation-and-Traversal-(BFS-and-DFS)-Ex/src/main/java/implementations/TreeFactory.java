package implementations;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class TreeFactory {
    private Map<Integer, Tree<Integer>> nodesByKeys;

    public TreeFactory() {
        this.nodesByKeys = new LinkedHashMap<>();
    }

    public Tree<Integer> createTreeFromStrings(String[] input) {
        for (String params : input) {
            int[] nodeValues = Arrays.stream(params.split("\\s+")).mapToInt(Integer::parseInt).toArray();
               addEdge(nodeValues[0], nodeValues[1]);
        }
        return getRoot();
    }

    private Tree<Integer> getRoot() {
        for (Tree<Integer> node : nodesByKeys.values()) {
            if (node.getParent() == null) {
                return node;
            }
        }
        return null;
    }

    public Tree<Integer> createNodeByKey(int key) {
        this.nodesByKeys.putIfAbsent(key, new Tree<Integer>(key));
        return this.nodesByKeys.get(key);
    }

    public void addEdge(int parent, int child) {
        Tree<Integer> parentTree = createNodeByKey(parent);
        Tree<Integer> childTree = createNodeByKey(child);

        parentTree.addChild(childTree);
        childTree.setParent(parentTree);

    }
}



