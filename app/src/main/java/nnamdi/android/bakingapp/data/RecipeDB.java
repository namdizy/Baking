package nnamdi.android.bakingapp.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by theodore.johnsonkanu on 3/4/2018.
 */


@Database(version = RecipeDB.VERSION)
public final class RecipeDB {

    public static final int VERSION = 1;

    public static class Tables{
        @Table(RecipeColumns.class) public static final String RECIPE = "recipe";
        @Table(IngredientsColumns.class) public static final String INGREDIENTS = "ingredient";
        @Table(StepsColumns.class) public static final String STEPS ="steps";
    }


}
