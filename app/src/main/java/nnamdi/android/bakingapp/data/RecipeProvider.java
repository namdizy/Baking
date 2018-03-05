package nnamdi.android.bakingapp.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;


@ContentProvider(authority = RecipeProvider.AUTHORITY, database = RecipeDB.class)
public class RecipeProvider {

    public static final String AUTHORITY = "nnamdi.android.bakingapp";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    interface Path {
        String RECIPE = "recipe";
        String INGREDIENTS = "ingredients";
        String STEPS = "steps";
        String TAGS = "tags";
    }


    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = RecipeDB.Tables.RECIPE) public static class RecipeTable {
        @ContentUri(
                path = Path.RECIPE,
                type = "vnd.android.cursor.dir/recipe",
                defaultSort = RecipeColumns.ID + " ASC")
        public static final Uri CONTENT_URL = buildUri(Path.RECIPE);

        @InexactContentUri(
                path = Path.RECIPE + "/#",
                name = "RECIPE_ID",
                type = "vnd.android.cursor.item/recipe",
                whereColumn = RecipeColumns.ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.RECIPE, String.valueOf(id));
        }
    }

    @TableEndpoint(table = RecipeDB.Tables.INGREDIENTS)  public static class IngredientsTable {

        @ContentUri(
                path = Path.INGREDIENTS,
                type = "vnd.android.cursor.dir/ingredients")
        public static final Uri CONTENT_URL = buildUri(Path.INGREDIENTS);

        @InexactContentUri(
                path = Path.INGREDIENTS + "/#",
                name = "INGREDIENTS_ID",
                type = "vnd.android.cursor.item/ingredients",
                whereColumn = IngredientsColumns.ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.INGREDIENTS, String.valueOf(id));
        }
    }

    @TableEndpoint(table = RecipeDB.Tables.STEPS)  public static class StepsTable {

        @ContentUri(
                path = Path.STEPS,
                type = "vnd.android.cursor.dir/ingredients")
        public static final Uri CONTENT_URL = buildUri(Path.STEPS);

        @InexactContentUri(
                path = Path.STEPS + "/#",
                name = "STEPS_ID",
                type = "vnd.android.cursor.item/steps",
                whereColumn = StepsColumns.ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.STEPS, String.valueOf(id));
        }
    }

}

