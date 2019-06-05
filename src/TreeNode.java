public class TreeNode {

    private TreeNode LeftNode;
    private TreeNode RightNode;
    private String attName;
    private String ClassName;
    private double Probability;
    public boolean IsLeaf;
    public TreeNode(TreeNode leftNode, TreeNode rightNode, String attname, boolean leaf) {
        LeftNode = leftNode;
        RightNode = rightNode;
        attName = attname;
        IsLeaf = leaf;

    }

    public TreeNode(double probability, String className, boolean leaf) {
        Probability = probability;
        ClassName = className;
        IsLeaf = leaf;

    }

    public TreeNode getLeftNode() {
        return LeftNode;
    }

    public void setLeftNode(TreeNode leftNode) {
        LeftNode = leftNode;
    }

    public TreeNode getRightNode() {
        return RightNode;
    }

    public void setRightNode(TreeNode rightNode) {
        RightNode = rightNode;
    }
    public String getAttname(){
        return attName;
    }


    public String getClass1() {
        return ClassName;
    }

    public void setClass(String aClass) {
        ClassName = aClass;
    }

    public double getProbabilty() {
        return Probability;
    }

    public void setProbabilty(double probability) {
        Probability = probability;
    }




}




