package model;

public class Location {
    private int xCoordinate;
    private int yCoordinate;

    public Location(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public double travelTime(Location other) {
        int xDiff = this.xCoordinate - other.xCoordinate;
        int yDiff = this.yCoordinate - other.yCoordinate;

        return (Math.sqrt((xDiff * xDiff) + (yDiff * yDiff)) * 1.5);
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
