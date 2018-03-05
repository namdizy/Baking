package nnamdi.android.bakingapp.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

/**
 * Created by theodore.johnsonkanu on 3/4/2018.
 */

public interface IngredientsColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement String ID = "id";
    @DataType(DataType.Type.INTEGER) @References(table = RecipeDB.Tables.RECIPE, column = RecipeColumns.ID) String RECIPE_ID =
            "recipe_id";
    @DataType(DataType.Type.INTEGER) @NotNull String QUANTITY = "quantity";
    @DataType(DataType.Type.TEXT) @NotNull String MEASURE = "measure";
    @DataType(DataType.Type.TEXT) @NotNull String INGREDIENT = "ingredient";
}
