package sora.havok.generated;

@javax.annotation.Generated("sora.havok.generators.pojo.PojoGen")
public class Human{
private final String firstName;
private final String lastName;
private final int age;
private final BloodType bloodType;
private final Gender gender;
public Human(final String firstName, final String lastName, final int age, final BloodType bloodType, final Gender gender){
this.firstName = firstName;
this.lastName = lastName;
this.age = age;
this.bloodType = bloodType;
this.gender = gender;
}
public String firstName(){ return firstName; }
public String lastName(){ return lastName; }
public int age(){ return age; }
public BloodType bloodType(){ return bloodType; }
public Gender gender(){ return gender; }
@Override public String toString(){return "Human("+firstName+", "+lastName+", "+age+", "+bloodType+", "+gender+")";}
public enum BloodType{AB,A,B,O,UNKNOWN}
public enum Gender{MALE,FEMALE,UNKNOWN}
}
