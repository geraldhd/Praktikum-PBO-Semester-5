interface Behavior {
    default void dead(){
        System.out.println("DEATH");
    }

    default void specialSkill(){
        System.out.println("Special Skill");
    }
}
