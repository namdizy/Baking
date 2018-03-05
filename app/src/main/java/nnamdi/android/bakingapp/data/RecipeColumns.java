package nnamdi.android.bakingapp.data;

import android.support.annotation.Nullable;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;


public interface RecipeColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey String ID = "id";
    @DataType(DataType.Type.TEXT) @Nullable String IMAGE = "image";
    @DataType(DataType.Type.INTEGER) @Nullable String SERVINGS = "servings";
}
