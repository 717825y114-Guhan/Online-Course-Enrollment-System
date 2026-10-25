package model;

/**
 * An instructor who teaches a course.
 */
public class Instructor {
    private final String instructorId;
    private String name;
    private String specialization;

    public Instructor(String instructorId, String name, String specialization) {
        this.instructorId = instructorId;
        setName(name);
        setSpecialization(specialization);
    }

    public String getInstructorId() { return instructorId; }
    public String getName()         { return name; }
    public String getSpecialization() { return specialization; }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Instructor name cannot be empty");
        }
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.isBlank()) {
            throw new IllegalArgumentException("Specialization cannot be empty");
        }
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return name + " (" + specialization + ")";
    }
}
