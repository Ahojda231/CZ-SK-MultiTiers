package com.tiers.misc;

import com.tiers.textures.Icons;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;

import java.util.Arrays;
import java.util.Locale;

public enum Mode {
    MCTIERS_VANILLA(Category.MCTIERS, "\uF000", "Vanilla"),
    MCTIERS_UHC(Category.MCTIERS, "\uF001", "UHC"),
    MCTIERS_POT(Category.MCTIERS, "\uF002", "Pot"),
    MCTIERS_NETH_OP(Category.MCTIERS, "\uF003", "Neth Op"),
    MCTIERS_SMP(Category.MCTIERS, "\uF004", "Smp"),
    MCTIERS_SWORD(Category.MCTIERS, "\uF005", "Sword"),
    MCTIERS_AXE(Category.MCTIERS, "\uF006", "Axe"),
    MCTIERS_MACE(Category.MCTIERS, "\uF007", "Mace"),

    PVPTIERS_CRYSTAL(Category.PVPTIERS, "\uF000", "Crystal"),
    PVPTIERS_SWORD(Category.PVPTIERS, "\uF005", "Sword"),
    PVPTIERS_UHC(Category.PVPTIERS, "\uF001", "UHC"),
    PVPTIERS_POT(Category.PVPTIERS, "\uF002", "Pot"),
    PVPTIERS_NETH_POT(Category.PVPTIERS, "\uF003", "Neth Pot"),
    PVPTIERS_SMP(Category.PVPTIERS, "\uF004", "Smp"),
    PVPTIERS_AXE(Category.PVPTIERS, "\uF006", "Axe"),
    PVPTIERS_MACE(Category.PVPTIERS, "\uF007", "Mace"),

    SUBTIERS_MINECART(Category.SUBTIERS, "\uF000", "Minecart"),
    SUBTIERS_DIAMOND_VANILLA(Category.SUBTIERS, "\uF001", "Diamond Vanilla"),
    SUBTIERS_DEBUFF(Category.SUBTIERS, "\uF002", "DeBuff"),
    SUBTIERS_ELYTRA(Category.SUBTIERS, "\uF003", "Elytra"),
    SUBTIERS_SPEED(Category.SUBTIERS, "\uF004", "Speed"),
    SUBTIERS_CREEPER(Category.SUBTIERS, "\uF005", "Creeper"),
    SUBTIERS_MANHUNT(Category.SUBTIERS, "\uF006", "Manhunt"),
    SUBTIERS_DIAMOND_SMP(Category.SUBTIERS, "\uF007", "Diamond Smp"),
    SUBTIERS_BOW(Category.SUBTIERS, "\uF008", "Bow"),
    SUBTIERS_BED(Category.SUBTIERS, "\uF009", "Bed"),
    SUBTIERS_OG_VANILLA(Category.SUBTIERS, "\uF00A", "OG Vanilla"),
    SUBTIERS_TRIDENT(Category.SUBTIERS, "\uF00B", "Trident"),

    CZSK_CRYSTAL(Category.CZSKTIERS, "\uF000", "Crystal"),
    CZSK_SWORD(Category.CZSKTIERS, "\uF005", "Sword"),
    CZSK_UHC(Category.CZSKTIERS, "\uF001", "UHC"),
    CZSK_POT(Category.CZSKTIERS, "\uF002", "Pot"),
    CZSK_NETH_POT(Category.CZSKTIERS, "\uF003", "Neth Pot"),
    CZSK_SMP(Category.CZSKTIERS, "\uF004", "Smp"),
    CZSK_AXE(Category.CZSKTIERS, "\uF006", "Axe"),
    CZSK_MACE(Category.CZSKTIERS, "\uF007", "Mace"),
    CZSK_SPEARMACE(Category.CZSKTIERS, "\uF007", "SpearMace"),

    CZSK_B0T_CRYSTAL(Category.CZSK_B0T, "\uF000", "Crystal"),
    CZSK_B0T_SWORD(Category.CZSK_B0T, "\uF005", "Sword"),
    CZSK_B0T_UHC(Category.CZSK_B0T, "\uF001", "UHC"),
    CZSK_B0T_POT(Category.CZSK_B0T, "\uF002", "Pot"),
    CZSK_B0T_NPOT(Category.CZSK_B0T, "\uF003", "Npot"),
    CZSK_B0T_SMP(Category.CZSK_B0T, "\uF004", "Smp"),
    CZSK_B0T_AXE(Category.CZSK_B0T, "\uF006", "Axe"),
    CZSK_B0T_DIASMP(Category.CZSK_B0T, "\uF004", "DiaSMP"),
    CZSK_B0T_MACE(Category.CZSK_B0T, "\uF007", "Mace"),

    CZSK_SUB_SPEED(Category.CZSK_SUBTIERS, "\uF004", "Speed"),
    CZSK_SUB_OGV(Category.CZSK_SUBTIERS, "\uF00A", "OG Vanilla"),
    CZSK_SUB_CART(Category.CZSK_SUBTIERS, "\uF000", "Minecart"),
    CZSK_SUB_CREEPER(Category.CZSK_SUBTIERS, "\uF005", "Creeper"),
    CZSK_SUB_DIAVANILLA(Category.CZSK_SUBTIERS, "\uF001", "Diamond Vanilla"),
    CZSK_SUB_TRIDENT(Category.CZSK_SUBTIERS, "\uF00B", "Trident"),
    CZSK_SUB_MANHUNT(Category.CZSK_SUBTIERS, "\uF006", "Manhunt"),
    CZSK_SUB_ELYTRA(Category.CZSK_SUBTIERS, "\uF003", "Elytra"),
    CZSK_SUB_BOW(Category.CZSK_SUBTIERS, "\uF008", "Bow"),
    CZSK_SUB_BED(Category.CZSK_SUBTIERS, "\uF009", "Bed"),
    CZSK_SUB_DEBUFF(Category.CZSK_SUBTIERS, "\uF002", "DeBuff");

    private final Category category;
    private final String unicode;
    private final String label;

    Mode(Category category, String unicode, String label) {
        this.category = category;
        this.unicode = unicode;
        this.label = label;
    }

    public enum Category {
        MCTIERS,
        PVPTIERS,
        SUBTIERS,
        CZSKTIERS,
        CZSK_B0T,
        CZSK_SUBTIERS
    }

    public Component getIcon() {
        ResourceLocation identifier = switch (category) {
            case MCTIERS -> Icons.identifierMCTiers;
            case PVPTIERS, CZSKTIERS, CZSK_B0T -> Icons.identifierPvPTiers;
            case SUBTIERS, CZSK_SUBTIERS -> Icons.identifierSubtiers;
        };
        return Component.literal(unicode).setStyle(Style.EMPTY.withFont(new FontDescription.Resource(identifier)).withColor(CommonColors.WHITE));
    }

    public Component getIconTag() {
        ResourceLocation identifier = switch (category) {
            case MCTIERS -> Icons.identifierMCTiersTags;
            case PVPTIERS, CZSKTIERS, CZSK_B0T -> Icons.identifierPvPTiersTags;
            case SUBTIERS, CZSK_SUBTIERS -> Icons.identifierSubtiersTags;
        };
        return Component.literal(unicode).setStyle(Style.EMPTY.withFont(new FontDescription.Resource(identifier)).withColor(CommonColors.WHITE));
    }

    public Component getTextLabel() {
        return Icons.colorText(label, name().toLowerCase(Locale.ROOT));
    }

    public static Mode[] getMCTiersValues() {
        return Arrays.stream(values()).filter(mode -> mode.toString().startsWith("MCTIERS")).toArray(Mode[]::new);
    }

    public static Mode[] getPvPTiersValues() {
        return Arrays.stream(values()).filter(mode -> mode.toString().startsWith("PVPTIERS")).toArray(Mode[]::new);
    }

    public static Mode[] getSubtiersValues() {
        return Arrays.stream(values()).filter(mode -> mode.toString().startsWith("SUBTIERS")).toArray(Mode[]::new);
    }

    public static Mode[] getCZSKTiersValues() {
        return Arrays.stream(values()).filter(mode -> mode.toString().startsWith("CZSK_") && !mode.toString().contains("B0T") && !mode.toString().contains("SUB")).toArray(Mode[]::new);
    }

    public static Mode[] getCZSKB0tValues() {
        return Arrays.stream(values()).filter(mode -> mode.toString().startsWith("CZSK_B0T")).toArray(Mode[]::new);
    }

    public static Mode[] getCZSKSubtiersValues() {
        return Arrays.stream(values()).filter(mode -> mode.toString().startsWith("CZSK_SUB")).toArray(Mode[]::new);
    }
}