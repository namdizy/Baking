package nnamdi.android.bakingapp.data;

import android.support.annotation.Nullable;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

/**
 * Created by theodore.johnsonkanu on 3/4/2018.
 */

public interface StepsColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey String ID = "id";
    @DataType(DataType.Type.INTEGER) @References(table = RecipeDB.Tables.RECIPE, column = RecipeColumns.ID) String RECIPE_ID =
            "recipe_id";
    @DataType(DataType.Type.TEXT) @NotNull String SHORT_DESCRIPTION = "short_description";
    @DataType(DataType.Type.TEXT) @NotNull String DESCRIPTION = "description";
    @DataType(DataType.Type.TEXT) @Nullable String VIDEO_URL = "video_url";
    @DataType(DataType.Type.TEXT) @Nullable String THUMBNAIL_URL = "thumbnail_url";
}
