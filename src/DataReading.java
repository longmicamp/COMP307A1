

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class DataReading {

    public ArrayList<Datafile> FlowerTrainingData = new ArrayList<Datafile>();
    public ArrayList<Datafile> FlowerTestData = new ArrayList<Datafile>();


    public DataReading(String file1, String file2) {

        readTrainingSet(file1);
        readTestSet(file2);
    }


    public void readTrainingSet(String file) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] a = line.split("  ");
                    Double SLength = Double.parseDouble(a[0]);
                    //   System.out.print(a[0] + " ");
                    Double SWidth = Double.parseDouble(a[1]);                   //Reads the training file and turns the data into an array of data tuples/objects
                    //   System.out.print(a[1] + " ");
                    Double PLength = Double.parseDouble(a[2]);
                    //   System.out.print(a[2] + " ");
                    Double PWidth = Double.parseDouble(a[3]);
                    //  System.out.print(a[3] + " ");
                    String Fclass = a[4];
                    //    System.out.println(Math.pow(5,2));
                    FlowerTrainingData.add(new Datafile(SLength, SWidth, PLength, PWidth, Fclass));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void readTestSet(String file2) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file2));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] a = line.split("  ");
                    Double SLength = Double.parseDouble(a[0]);
                    // System.out.print(a[0] + " ");
                    Double SWidth = Double.parseDouble(a[1]);
                    // System.out.print(a[1] + " ");
                    Double PLength = Double.parseDouble(a[2]);  //Reads the test data and turns it into an array of flower data tuples/objects
                    // System.out.print(a[2] + " ");
                    Double PWidth = Double.parseDouble(a[3]);
                    //  System.out.println(a[3] + " ");

                    //System.out.println(a[4] + " ");
                    FlowerTestData.add(new Datafile(SLength, SWidth, PLength, PWidth, findDistancek1(SLength, SWidth, PLength, PWidth)));

                }
            }


            reader.close();


            for (int t = 0; t < FlowerTestData.size(); t++) {
                System.out.println("ClassName: " + FlowerTestData.get(t).getFlowerClass());

            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public String findDistancek1(double Slength, double Swidth, double Plength, double Pwidth) {
        int k = 3;
        String ClassName = "";

        PriorityQueue<Datafile> pq = new PriorityQueue<Datafile>(1, new Comparator<Datafile>() {
            @Override
            public int compare(Datafile o1, Datafile o2) {
                return Double.compare(o1.getDistance(), o2.getDistance()); //creats a comparator for the priority queue.
            }
        });


        double PedalLMax = 0;
        double PedalLMin = 99;
        double PedalWMax = 0;
        double PedalWMin = 99;
        double SepalLMax = 0;
        double SepalLMin = 99;
        double SepalWMax = 0;
        double SepalWMin = 99;

        for (int a = 0; a < FlowerTrainingData.size(); a++) {
            if (FlowerTrainingData.get(a).PedalLength > PedalLMax) { // Calculate the range of the Width and Lengths for the Distance calculation
                PedalLMax = FlowerTrainingData.get(a).PedalLength;
            }
            if (FlowerTrainingData.get(a).PedalLength < PedalLMin) {
                PedalLMin = FlowerTrainingData.get(a).PedalLength;
            }

            if (FlowerTrainingData.get(a).PedalWidth > PedalWMax) {
                PedalWMax = FlowerTrainingData.get(a).PedalWidth;
            }
            if (FlowerTrainingData.get(a).PedalWidth < PedalWMin) {
                PedalLMin = FlowerTrainingData.get(a).PedalLength;
            }

            if (FlowerTrainingData.get(a).SepalLength > SepalLMax) {
                SepalLMax = FlowerTrainingData.get(a).SepalLength;
            }
            if (FlowerTrainingData.get(a).SepalLength < SepalLMin) {
                SepalLMin = FlowerTrainingData.get(a).SepalLength;
            }
            if (FlowerTrainingData.get(a).SepalWidth > SepalWMax) {
                SepalWMax = FlowerTrainingData.get(a).SepalWidth;
            }
            if (FlowerTrainingData.get(a).SepalWidth < SepalWMin) {
                SepalWMin = FlowerTrainingData.get(a).SepalWidth;
            }

            double PedalLRange = PedalLMax - PedalLMin;
            double PedalWRange = PedalWMax - PedalWMin;
            double SepalLRange = SepalLMax - SepalLMin;
            double SepalWRange = SepalLMax - SepalLMin;


            //Calculate distance and set the distance for that training tuple against the current test tuple


            double PLCalc = (Math.pow(Plength - (FlowerTrainingData.get(a).PedalLength), 2)) / Math.pow(PedalLRange, 2);
            double PWCalc = (Math.pow(Pwidth - (FlowerTrainingData.get(a).PedalWidth), 2)) / Math.pow(PedalWRange, 2);
            double SLCalc = (Math.pow(Slength - (FlowerTrainingData.get(a).SepalLength), 2)) / Math.pow(SepalLRange, 2);
            double SWCalc = (Math.pow(Swidth - (FlowerTrainingData.get(a).SepalWidth), 2)) / Math.pow(SepalWRange, 2);

            double distance = Math.sqrt(PLCalc + PWCalc + SLCalc + SWCalc);

            FlowerTrainingData.get(a).setDistance(distance);
//System.out.println(pq.size());
            pq.add(FlowerTrainingData.get(a));


        }

        ArrayList<String> k3 = new ArrayList<String>();

        k3.add(pq.poll().FlowerClass);
        k3.add(pq.poll().FlowerClass);
        k3.add(pq.poll().FlowerClass);


        if (k == 1) {
            ClassName = pq.poll().FlowerClass;
        } else if (k == 3) {

            int Set = 0;
            int Versico = 0;
            int Virg = 0;

            for (int i = 0; k3.size() > i; i++) {

                if (k3.get(i).equals("Iris-setosa")) {
                    Set++;
                } else if (k3.get(i).equals("Iris-versicolor")) {
                    Versico++;
                } else if (k3.get(i).equals("Iris-virginica")) {
                    Virg++;
                }

            }


            if (Set > Versico && Set > Virg) {
                ClassName = "Iris-setosa";
            } else if (Versico > Virg && Versico > Set) {
                ClassName = "Iris-versicolor";

            } else {
                ClassName = "Iris-virginica";
            }

        }


        return ClassName;
    }


    public static void main(String[] args) {


        if (args.length != 2) {

            System.out.print("You do not have 2 files in your arguments");
            System.exit(0);
        }
        new DataReading(args[0], args[1]);

    }


}


