import java.awt.peer.SystemTrayPeer;
import java.security.PublicKey;
import java.util.*;
import java.io.*;

public class Part2DataReading {
    public int numCategories;
    public int numAtts;
    public ArrayList<String> categoryNames;
    public ArrayList<String> attNames;
    public List<Instance> allInstances;
    public ArrayList<TreeNode> Tree = new ArrayList<TreeNode>();


    public Part2DataReading(String fname) {

        readTrainingSet(fname);

        buildTree(allInstances, attNames);

//
//
//
//
    }


    public void readTrainingSet(String fname) {

        System.out.println("Reading data from file " + fname);
        try {

            Scanner din = new Scanner(new File(fname));

            categoryNames = new ArrayList<String>();
            for (Scanner s = new Scanner(din.nextLine()); s.hasNext(); ) categoryNames.add(s.next());
            numCategories = categoryNames.size();
            System.out.println(numCategories + " categories");

            attNames = new ArrayList<String>();
            for (Scanner s = new Scanner(din.nextLine()); s.hasNext(); ) attNames.add(s.next());
            numAtts = attNames.size();
            System.out.println(numAtts + " attributes");

            allInstances = readInstances(din);


            din.close();
        } catch (IOException e) {
            throw new RuntimeException("Data File caused IO exception");
        }
    }

    private List<Instance> readInstances(Scanner din) {
        /* instance = classname and space separated attribute values */
        List<Instance> instances = new ArrayList<Instance>();
        String ln;
        while (din.hasNext()) {
            Scanner line = new Scanner(din.nextLine());
            instances.add(new Instance(categoryNames.indexOf(line.next()), line));
        }
        System.out.println("Read " + instances.size() + " instances");
        return instances;
    }


    private class Instance {

        private int category;
        private List<Boolean> vals;

        public Instance(int cat, Scanner s) {
            category = cat;
            vals = new ArrayList<Boolean>();
            while (s.hasNextBoolean()) vals.add(s.nextBoolean());
        }

        public boolean getAtt(int index) {
            return vals.get(index);
        }

        public int getCategory() {
            return category;
        }

        public String toString() {
            StringBuilder ans = new StringBuilder(categoryNames.get(category));
            ans.append(" ");
            for (Boolean val : vals)
                ans.append(val ? "true  " : "false ");
            return ans.toString();
        }


    }

    public TreeNode buildTree(List<Instance> instances, ArrayList<String> attributes) {


        TreeNode left = null;
        TreeNode right = null;
        String bestAtt = "";
        List<Instance> bestInstsTrue = new ArrayList<Instance>();
        List<Instance> bestInstsFalse = new ArrayList<Instance>();
        int alive = 0;
        int dead = 0;
        String Class = "";
        double prob = 0;
        String PureClass = "";
        if (instances.size() != 0) {


            for (int a = 0; a < instances.size(); a++) {
                if (instances.get(a).getCategory() == 1) {
                    dead++;
                } else {
                    alive++;
                }
            }
        }
        if (instances.size() == 0) {
            if (alive < dead) {
                Class = "die";
                prob = 1;//dead / (double)instances.size();
            } else {
                Class = "live";
                prob = 1;// alive /(double) instances.size();
            }
            TreeNode empty = new TreeNode(prob, Class, true);
            return empty;

        }

        if (isPure(instances)) {

            if (instances.get(0).getCategory() == 0) {
                PureClass = "live";
            } else {
                PureClass = "die";
            }
            TreeNode pure = new TreeNode(1, PureClass, true);
            return pure;
        }

        if (attributes.size() == 0) {
            int dieList = 0;
            int liveList = 0;
            for (int a = 0; instances.size() > a; a++) {
                if (instances.get(a).getCategory() == 0) {
                    liveList++;
                } else {
                    dieList++;
                }
            }
            int proba = 0;
            String classes;
            if (dieList < liveList) {
                proba = 1 - (dieList / liveList);
                classes = "live";
            } else {
                proba = 1 - (liveList / dieList);
                classes = "die";
            }
            TreeNode node = new TreeNode(proba, classes, true);
            return node;
        } else {

            double CurrBestTrueWeight = 1;
            double CurrBestFalseWeight = 1;
            for (int b = 0; b < attributes.size(); b++) {
                List<Instance> TrueSet = new ArrayList<Instance>();
                List<Instance> FalseSet = new ArrayList<Instance>();
                for (int a = 0; a < instances.size(); a++) {
                    if (instances.get(a).getAtt(b) == true) {
                        TrueSet.add(instances.get(a));
                    } else {
                        FalseSet.add(instances.get(a));
                    }
                }
                int Truelive = 0;
                int Truedie = 0;
                int Falselive = 0;
                int Falsedie = 0;
                for (int z = 0; z < TrueSet.size(); z++) {
                    if (TrueSet.get(z).getCategory() == 0) {
                        Truelive++;
                    } else {
                        Truedie++;
                    }
                }
                for (int z = 0; z < FalseSet.size(); z++) {
                    if (FalseSet.get(z).getCategory() == 0) {
                        Falselive++;
                    } else {
                        Falsedie++;
                    }
                }


                double FalsePurity = ((double) Falselive / FalseSet.size()) * ((double) Falsedie / FalseSet.size());
                double TruePurity = ((double) Truelive / TrueSet.size()) * ((double) Truedie / TrueSet.size());
                double WeightFP = FalsePurity * ((double) FalseSet.size() / instances.size());
                double WeightTP = TruePurity * ((double) TrueSet.size() / instances.size());


                if (WeightFP < CurrBestFalseWeight && WeightTP < CurrBestTrueWeight) {
                    CurrBestFalseWeight = WeightFP;
                    CurrBestTrueWeight = WeightTP;
                    bestAtt = attributes.get(b);
                    bestInstsTrue = TrueSet;
                    bestInstsFalse = FalseSet;
                }

            }

            attributes.remove(bestAtt);


            left = buildTree(bestInstsTrue, attributes);
            right = buildTree(bestInstsFalse, attributes);
            //    System.out.println(right.getAttname());
        }

        TreeNode Node = new TreeNode(left, right, bestAtt, false);


        Tree.add(Node);
        return Node;
    }


    public void printTree(int side, TreeNode node, int depth) {


        if(node == null){
            return;
        }
               System.out.println(node.getAttname());

        if(side == 0){

                  System.out.println(node.getAttname());
                  System.out.println("");
        }
        if(node.IsLeaf){
            System.out.println(node.getClass1() + node.getProbabilty());
        }

        else{
            System.out.println(node.getAttname());
            System.out.println("");
        }
        if(node.getLeftNode()!=null) {
            System.out.println(node.getLeftNode().getAttname());
            printTree(1, node.getLeftNode(), 1);
        }
        if(node.getRightNode()!=null) {
            printTree(1, node.getLeftNode(), 1);
        }


    }


    public boolean isPure(List<Instance> instances) {
        int state = instances.get(0).getCategory();
        for (int a = 0; a < instances.size(); a++) {

            if (instances.get(a).getCategory() == 0 && state == 1) {
                return false;

            } else if (instances.get(a).getCategory() == 1 && state == 0) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {

        if(args.length !=1){
            System.out.print("You are missing the args file");
            System.exit(0);
        }

        new Part2DataReading(args[0]);


    }


}


