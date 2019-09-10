package com.kata.robotsVsDinasaurs.combatGrid.domain;

import com.kata.robotsVsDinasaurs.enums.CartesianAxes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Coordinates {
    Map<CartesianAxes, Integer> coordinates = new HashMap<CartesianAxes, Integer>();

    public Coordinates(Integer xAxis, Integer yAxis) {
        setXAxis(xAxis);
        setYAxis(yAxis);
    }

    public void setXAxis(Integer xAxis) {
        coordinates.put(CartesianAxes.X, xAxis);
    }

    public void setYAxis(Integer xAxis) {
        coordinates.put(CartesianAxes.Y, xAxis);
    }

    public Integer getXAxis() {
        return coordinates.get(CartesianAxes.X);
    }

    public Integer getYAxis() {
        return coordinates.get(CartesianAxes.Y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
