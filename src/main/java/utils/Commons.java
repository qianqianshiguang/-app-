package utils;

public class Commons {
    private String url = "";

    public String geturl() {
        return url;
    }

    public static double streamDouble(double pixRate) {
        double newPixRate = (double) Math.round(pixRate * 100) / 100;
        return newPixRate;
    }

}
