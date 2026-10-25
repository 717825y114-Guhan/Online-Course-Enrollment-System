package model;

import exception.CourseFullException;

/**
 * A course offered by an Instructor (aggregation: the Instructor exists
 * independently of the Course). Tracks seat capacity.
 */
public class Course {
    private final String courseId;
    private String title;
    private double fee;
    private int capacity;
    private int enrolledCount;
    private Instructor instructor;   // aggregation

    public Course(String courseId, String title, double fee, int capacity, Instructor instructor) {
        this.courseId = courseId;
        setTitle(title);
        setFee(fee);
        setCapacity(capacity);
        setInstructor(instructor);
        this.enrolledCount = 0;
    }

    // ---------- getters ----------
    public String getCourseId()      { return courseId; }
    public String getTitle()         { return title; }
    public double getFee()           { return fee; }
    public int getCapacity()         { return capacity; }
    public int getEnrolledCount()    { return enrolledCount; }
    public Instructor getInstructor() { return instructor; }

    // ---------- setters with validation ----------
    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Course title cannot be empty");
        }
        this.title = title;
    }

    public void setFee(double fee) {
        if (fee <= 0) {
            throw new IllegalArgumentException("Course fee must be positive");
        }
        this.fee = fee;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
    }

    public void setInstructor(Instructor instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor is required");
        }
        this.instructor = instructor;
    }

    // ---------- capacity logic ----------
    public int getAvailableSeats() {
        return capacity - enrolledCount;
    }

    public boolean isAvailable() {
        return getAvailableSeats() > 0;
    }

    /** Takes one seat; throws if the course is already full. */
    public void reserveSeat() throws CourseFullException {
        if (!isAvailable()) {
            throw new CourseFullException(title);
        }
        enrolledCount++;
    }

    @Override
    public String toString() {
        return title + " (" + courseId + ")";
    }
}
