package net.minecraft.src;

public class StatCollector {
    private static final StringTranslate localizedName = StringTranslate.getInstance();

    /**
     * Translates a Stat name
     */
    public static String translateToLocal(String par0Str) {
        return localizedName.translateKey(par0Str);
    }

    /**
     * Translates a Stat name with format args
     */
    public static String translateToLocalFormatted(String par0Str, Object... par1ArrayOfObj) {
        return localizedName.translateKeyFormat(par0Str, par1ArrayOfObj);
    }

    public static boolean func_94522_b(String par0Str) {
        return localizedName.isKeyTranslated(par0Str);
    }
}
