package com.caomeiprincess;

import java.util.function.Supplier;

public class Car {
    public Car getCar(Supplier<Car> supplier){
       return supplier.get();
    }
}
