Vision: To persist all java collection with one command and get the same with one command. Give the boring saving work this library.

Let's start with List

Save List
============

![Logo](website/floppy.png)

Java Collection declared in Fields can be annotated to help this library to generate boilerplate
code for you. This library uses annotation processor to generate code.

 * Eliminate SQLite implementation.
 * Saves the complete List of Class object with a key. Key should be unique and POJO Class should be Serializable
 * Gives back the Complete List associated with the key

```java
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
}
```


__Remember: Save List like [Object-Relational Mapping (ORM)][1] but this is like baby elephant.__



Download
--------

```groovy

dependencies {
    annotationProcessor 'com.bipinayetra:save-processor:1.0.0'
    implementation 'com.bipinayetra:save-annotation:1.0.0'
}
```


Library projects
--------------------

To use Save List as a library, add the plugin to your `buildscript`:

```groovy
allprojects {
    repositories {
        maven {
            url "https://dl.bintray.com/bipinayetra/maven"
        }
    }
}
```

Now make sure your class as Serializable type.

```java
class Vehicle implements Serializable {
    String type;
    int engineCC;
...
}
```

You can also view this information on Blog [GitHub Pages][2].

License
-------

    Copyright 2018 Bipin Ayetra

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



 [1]: https://en.wikipedia.org/wiki/Object-relational_mapping
 [2]: https://bipinayetra.github.io/SaveList/