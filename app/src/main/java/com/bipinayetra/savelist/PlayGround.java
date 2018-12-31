package com.bipinayetra.savelist;

import android.app.Activity;
import android.os.Bundle;
import com.bipinayetra.annotation.SaveList;

import java.util.ArrayList;
import java.util.List;

public class PlayGround extends Activity {

    final String vehiclekey = "vehiclekey";

    @SaveList(key = vehiclekey)
    List<Vehicle> westCoastCars = new ArrayList<>();

    List<Vehicle> eastCoastCars = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_ground);

        westCoastCars.add(new Vehicle("BMW", 3500));
        westCoastCars.add(new Vehicle("AUDI", 3500));
        westCoastCars.add(new Vehicle("TESLA", 3500));
        EscapeSQLBoiler.getEscapeSQLBoiler(this).saveMyList(vehiclekey, westCoastCars);

        eastCoastCars = EscapeSQLBoiler.getEscapeSQLBoiler(this).giveMyListSavedInKey(vehiclekey);
        for (Vehicle p : eastCoastCars) {
            System.out.println(p);
        }

    }

    @Override
    protected void onDestroy() {
        new PlainOldJava(this);
        super.onDestroy();
    }
}
