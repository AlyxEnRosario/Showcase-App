package com.example.androidportfolio.ui.pokemon;

import java.util.List;

public class PokemonResponse {
    private String name;
    private int id;
    private List<Stat> stats;
    private List<Type> types;
    private Sprites sprites;

    public static class Stat {
        public int base_stat;
        public Stat_Detail stat;

        public static class Stat_Detail {
            public String name;
        }
    }

    public static class Type {
        public Type_Detail type;

        public static class Type_Detail {
            public String name;
        }
    }

    public static class Sprites {
        public String front_default;
    }

    public String getName() { return name; }
    public int getId() { return id; }
    public List<Stat> getStats() { return stats; }
    public List<Type> getTypes() { return types; }
    public Sprites getSprites() { return sprites; }
}
