package com.bipinayetra.savelist;

import android.content.Context;
import com.bipinayetra.annotation.SaveList;

import java.util.ArrayList;
import java.util.List;

public class PlainOldJava {

    final String peoplekey = "peoplekey";

    @SaveList(key = peoplekey)
    List<Person> people = new ArrayList<>();

    public PlainOldJava(Context context) {
        people.add(new Person("Pojojack", 190));
        EscapeSQLBoiler.getEscapeSQLBoiler(context).saveMyList(peoplekey, people);
        people = EscapeSQLBoiler.getEscapeSQLBoiler(context).giveMyListSavedInKey(peoplekey);
        for (Person p : people) {
            System.out.println(p);
        }
    }
}
