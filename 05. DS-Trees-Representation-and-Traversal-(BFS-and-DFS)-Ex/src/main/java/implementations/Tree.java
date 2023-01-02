package implementations;

import interfaces.AbstractTree;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {

    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key, Tree<E>... children) {
        this.key = key;
        this.children = new ArrayList<>();
        for (Tree<E> child : children) {
            child.setParent(this);
            this.children.add(child);
        }
    }

    public List<Tree<E>> getChildren() {
        return children;
    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;

    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;

    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public String getAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        traverseTreeWithRecurrence(stringBuilder, 0, this);
        return stringBuilder.toString().trim();
    }

    private void traverseTreeWithRecurrence(StringBuilder stringBuilder, int indent, Tree<E> tree) {
        stringBuilder
                .append(this.getPadding(indent))
                .append(tree.getKey())
                .append(System.lineSeparator());

        for (Tree<E> child : tree.children) {
            traverseTreeWithRecurrence(stringBuilder, indent + 2, child);
        }

    }

    private void traverseTreeWithRecurrence(List<Tree<E>> collection, Tree<E> tree) {
        collection.add(tree);
        for (Tree<E> child : tree.children) {
            traverseTreeWithRecurrence(collection, child);
        }
    }

    private String getPadding(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    @Override
    public List<E> getLeafKeys() {
        return traverseTreeWithBFS()
                .stream().filter(tree -> tree.children.size() == 0)
                .map(Tree::getKey).collect(Collectors.toList());

    }

    private List<Tree<E>> traverseTreeWithBFS() {
        StringBuilder sb = new StringBuilder();

        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        int indent = 0;
        List<Tree<E>> allNodes = new ArrayList<>();

        while (!queue.isEmpty()) {
            Tree<E> tree = queue.poll();
            allNodes.add(tree);
            for (Tree<E> child : tree.children) {
                queue.offer(child);
            }

        }
        return allNodes;
    }

    @Override
    public List<E> getMiddleKeys() {
        List<Tree<E>> allNodes = new ArrayList<>();
        this.traverseTreeWithRecurrence(allNodes, this);
        return allNodes.stream().filter(tree -> tree.parent != null && tree.children.size() > 0)
                .map(Tree::getKey).collect(Collectors.toList());
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        List<Tree<E>> trees = this.traverseTreeWithBFS();
        int maxPath = 0;

        Tree<E> deepestLeftmostNode = null;
        for (Tree<E> tree : trees) {
            if (tree.isLeaf()) {
                int currentPath = getStepsFromLeafToRoot(tree);
                if (currentPath > maxPath) {
                    maxPath = currentPath;
                    deepestLeftmostNode = tree;
                }
            }
        }
        return deepestLeftmostNode;
    }

    private int getStepsFromLeafToRoot(Tree<E> tree) {
        int counter = 0;
        Tree<E> current = tree;
        while (current.parent != null) {
            counter++;
            current = current.parent;
        }
        return counter;
    }

    private boolean isLeaf() {
        return this.parent != null && this.children.size() == 0;
    }

    @Override
    public List<E> getLongestPath() {
        List<Tree<E>> trees = this.traverseTreeWithBFS();
        int maxPath = 0;

        List<Tree<E>> longestPath = null;
        for (Tree<E> tree : trees) {
            if (tree.isLeaf()) {
                int currentPath = getTheLongestPath(tree).size();
                if (currentPath > maxPath) {
                    maxPath = currentPath;
                    longestPath = getTheLongestPath(tree);
                }
            }
        }
        return longestPath.stream().map(Tree::getKey).collect(Collectors.toList());

    }

    private List<Tree<E>>
    getTheLongestPath(Tree<E> tree) {
        Deque<Tree<E>> longestPath = new ArrayDeque<>();
        Tree<E> current = tree;
        while (current.parent != null) {
            longestPath.push(current);
            current = current.parent;
        }
        longestPath.push(current);
        return new ArrayList<>(longestPath);
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int searchedSum) {

        List<List<E>> result = new ArrayList<>();
        List<E> path = new ArrayList<>();
        dfsPathsOfSum(result, path, this, searchedSum);
        return result;

    }

    private void dfsPathsOfSum(List<List<E>> result, List<E> currentPath, Tree<E> tree, int searchedSum) {
        currentPath.add(tree.getKey());
        for (Tree<E> child : tree.children) {
            dfsPathsOfSum(result, currentPath, child, searchedSum);
        }
        if (tree.children.size() == 0) {
            int pathSum = currentPath.stream().mapToInt(e -> (int) e).sum();

            if (pathSum == searchedSum) {
                result.add(new ArrayList<>(currentPath));
            }
        }
        currentPath.remove(currentPath.size() - 1);
    }


    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        List<Tree<E>> result = new ArrayList<>();
        Deque<Tree<E>> deque = new ArrayDeque<>();
        deque.addAll(this.children);

        while (!deque.isEmpty()) {
            Tree<E> next = deque.poll();
            int childSum = getSubtreeSum(next);

            if (childSum == sum) {
                result.add(next);
            }
            deque.addAll(next.children);
        }

        return result;
    }

    private int getSubtreeSum(Tree<E> node) {
        int subtreeSum = 0;
        for (Tree<E> child : node.children) {
            subtreeSum += getSubtreeSum(child);
        }
        return subtreeSum + (int) node.key;
    }
}



