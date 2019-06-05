public class Datafile {
    public double SepalLength;
    public double SepalWidth;
    public double PedalLength;
    public double PedalWidth;
    public String FlowerClass;
    public double Distance;

    public Datafile(double sepalLength, double sepalWidth, double pedalLength, double pedalWidth, String aClass) {
        SepalLength = sepalLength;
        SepalWidth = sepalWidth;
        PedalLength = pedalLength;
        PedalWidth = pedalWidth;
        FlowerClass = aClass;
    }


    public double getSepalLength() {
        return SepalLength;
    }

    public void setSepalLength(double sepalLength) {
        SepalLength = sepalLength;
    }

    public double getSepalWidth() {
        return SepalWidth;
    }

    public void setSepalWidth(double sepalWidth) {
        SepalWidth = sepalWidth;
    }

    public double getPedalLength() {
        return PedalLength;
    }

    public void setPedalLength(double pedalLength) {
        PedalLength = pedalLength;
    }

    public double getPedalWidth() {
        return PedalWidth;
    }

    public void setPedalWidth(double pedalWidth) {
        PedalWidth = pedalWidth;
    }


    public String getFlowerClass() {
        return FlowerClass;
    }

    public void setClass(String aClass) {
        FlowerClass = aClass;
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }
}


