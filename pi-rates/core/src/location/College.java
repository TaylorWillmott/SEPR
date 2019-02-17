package location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class College {
    String name;
    private boolean bossAlive;

    public College(String name) {
        this.name = name;
        this.bossAlive = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBossAlive() {
        return bossAlive;
    }
    public void setBossAlive(boolean bossAlive) {
        this.bossAlive = bossAlive;
    }

    public static College Derwent = new College("Derwent");
    public static College Vanbrugh = new College("Vanbrugh");
    public static College James = new College("James");
    public static College Alcuin = new College("Alcuin");
    public static College Langwith = new College("Langwith");
    public static College Goodricke = new College("Goodricke");

    // List to check for game win condition
    // No Derwent as that is home college
    public static List<College> colleges = new ArrayList<College>(
            Arrays.asList(
                    Vanbrugh, James, Alcuin, Langwith, Goodricke
            )
    );
}
