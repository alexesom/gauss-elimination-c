package pl.edu.pw.ee;

public class TreeNode implements Comparable<TreeNode> {
    Character character;
    int weight;
    TreeNode left;
    TreeNode right;

    TreeNode(Character character, int weight) {
        this.character = character;
        this.weight = weight;
        this.left = null;
        this.right = null;
    }

    TreeNode(Character character, int weight, TreeNode left, TreeNode right) {
        this.character = character;
        this.weight = weight;
        this.left = left;
        this.right = right;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public int compareTo(TreeNode o) {
        return o.weight - weight;
    }

    public String getCodeForCharacter(Character searchCharacter, String parentPath) {
        if (character == searchCharacter) {
            return  parentPath;
        } else {
            if (left != null) {
                String path = left.getCodeForCharacter(searchCharacter, parentPath + 0);
                if (path != null) {
                    return path;
                }
            }
            if (right != null) {
                String path = right.getCodeForCharacter(searchCharacter, parentPath + 1);
                if (path != null) {
                    return path;
                }
            }
        }
        return null;
    }
}