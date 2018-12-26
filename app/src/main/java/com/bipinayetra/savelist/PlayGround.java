package com.bipinayetra.savelist;

import android.app.Activity;
import android.os.Bundle;
import com.bipinayetra.annotation.SaveList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayGround extends Activity {

    final String peoplekey = "peoplekey";
    final String animalkey = "animalkey";
    final String vehiclekey = "vehiclekey";

    @SaveList(key = peoplekey)
    List<Person> people = new ArrayList<>();
    @SaveList(key = animalkey)
    List<Animal> animals = new ArrayList<>();
    @SaveList(key = vehiclekey)
    List<Vehicle> vehicles = new ArrayList<>();



    List<Animal> newanimals = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_ground);

        new PlainOldJava(this);

        people.add(new Person("jack", 19));
        EscapeSQLBoiler.getEscapeSQLBoiler(this).saveMyList(peoplekey, people);
        people = EscapeSQLBoiler.getEscapeSQLBoiler(this).giveMyListSavedInKey(peoplekey);
        for (Person p : people) {
            System.out.println(p);
        }

//        animals.add(new Animal(true, "doggy1"));
//        animals.add(new Animal(true, "doggy2"));
//        animals.add(new Animal(true, "doggy3"));
//        animals.add(new Animal(true, "doggy4"));
//        EscapeSQLBoiler.getEscapeSQLBoiler(this).saveMyList(animalkey, animals);
        newanimals = EscapeSQLBoiler.getEscapeSQLBoiler(this).giveMyListSavedInKey(animalkey);
        for (Animal p : newanimals) {
            System.out.println(p);
        }

        vehicles.add(new Vehicle("truck", 3500));
        EscapeSQLBoiler.getEscapeSQLBoiler(this).saveMyList(vehiclekey, vehicles);
        vehicles = EscapeSQLBoiler.getEscapeSQLBoiler(this).giveMyListSavedInKey(vehiclekey);
        for (Vehicle p : vehicles) {
            System.out.println(p);
        }

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
