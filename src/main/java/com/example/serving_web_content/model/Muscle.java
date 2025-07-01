package com.example.serving_web_content.model;

public enum Muscle {
    // Upper body
    CHEST("Chest"),
    UPPER_CHEST("Upper Chest"),
    LOWER_CHEST("Lower Chest"),
    BACK("Back"),
    UPPER_BACK("Upper Back"),
    LOWER_BACK("Lower Back"),
    LATS("Lats"),
    TRAPS("Trapezius"),
    SHOULDERS("Shoulders"),
    FRONT_DELTS("Front Deltoids"),
    SIDE_DELTS("Side Deltoids"),
    REAR_DELTS("Rear Deltoids"),
    BICEPS("Biceps"),
    TRICEPS("Triceps"),
    FOREARMS("Forearms"),

    // Core
    ABS("Abdominals"),
    OBLIQUES("Obliques"),
    LOWER_ABS("Lower Abdominals"),
    UPPER_ABS("Upper Abdominals"),

    // Lower body
    QUADS("Quadriceps"),
    HAMSTRINGS("Hamstrings"),
    GLUTES("Glutes"),
    CALVES("Calves"),
    ADDUCTORS("Adductors"),
    ABDUCTORS("Abductors");

    private final String displayName;

    Muscle(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}