package io.github.seggan.sfcalc;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SFCalc extends JavaPlugin implements SlimefunAddon {
    FileConfiguration config = getConfig();

    private static SFCalc instance;

    final Set<RecipeType> blacklistedRecipes = new HashSet<>();
    final Set<String> blacklistedIds = new HashSet<>();

    final String headerString = ChatColor.translateAlternateColorCodes('&',
            Objects.requireNonNull(config.getString("header-string")));
    final String amountString = ChatColor.translateAlternateColorCodes('&',
            Objects.requireNonNull(config.getString("amount-string")));



    @Override
    public void onEnable() {
        getLogger().info("SFCalc enabled.");

        saveDefaultConfig();

        Objects.requireNonNull(getCommand("sfcalc")).setExecutor(new CalcExecutor(this));
        Objects.requireNonNull(getCommand("sfcalc")).setTabCompleter(new CalcCompleter());

        Objects.requireNonNull(getCommand("sfneeded")).setExecutor(new CalcExecutor(this));
        Objects.requireNonNull(getCommand("sfneeded")).setTabCompleter(new CalcCompleter());

        getServer().getPluginManager().registerEvents(new CalcHandler(), this);

        blacklistedRecipes.add(RecipeType.ORE_WASHER);
        blacklistedRecipes.add(RecipeType.GEO_MINER);
        blacklistedRecipes.add(RecipeType.GOLD_PAN);
        blacklistedRecipes.add(RecipeType.MOB_DROP);
        blacklistedRecipes.add(RecipeType.ORE_CRUSHER);
        blacklistedRecipes.add(RecipeType.NULL);

        blacklistedIds.add("uu_matter");
        blacklistedIds.add("silicon");

        instance = this;
    }

    @Override
    public void onDisable() {
        getLogger().info("SFCalc disabled.");
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Seggan/SFCalc/issues";
    }

    static SFCalc getInstance() {
        return instance;
    }

    static int getSlots(int c) {
        int n = 9;
        while (n < c) {
            n *= 2;
        }
        return n;
    }
}
