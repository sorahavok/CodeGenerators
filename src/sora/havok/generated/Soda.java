package sora.havok.generated;

@javax.annotation.Generated("sora.havok.generators.pojo.PojoGen")
public class Soda{
private final String name;
private final int calories;
private final boolean diet;
private final Color color;
public Soda(final String name, final int calories, final boolean diet, final Color color){
this.name = name;
this.calories = calories;
this.diet = diet;
this.color = color;
}
public String name(){ return name; }
public int calories(){ return calories; }
public boolean diet(){ return diet; }
public Color color(){ return color; }
@Override public String toString(){return "Soda("+name+", "+calories+", "+diet+", "+color+")";}
public enum Color{Brown,Clear,Orange,RedBrown,Yellow}
}
