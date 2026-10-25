# Online-Course-Enrollment-System
Menu-driven Java console app for online course enrollment: courses, students, premium discounts, payments and receipts. Demonstrates OOP, validation and custom exceptions.
# Online Course Enrollment System (Java Console Application)

**Course:** 23CSR306 JAVA PROGRAMMING – Assignment II: OOP Mini Project
**Specification:** 3 – Online Course Enrollment System

## Problem Statement

Design and implement a Java console application for a Course Enrollment System that handles courses, instructors, students, enrollments, and payments.

**Class Requirements:** Course, Instructor, Student, PremiumStudent, Enrollment, Payment, Receipt

**Business Rules**

1. Students can only enroll in available courses.
2. Premium students may get fee discounts or access to extra materials.
3. Payment must be completed for enrollment confirmation.
4. Enrollment should update course capacity.
5. Each receipt must link to a student and course.

**Menu Options:** Add Course / Add Student / Enroll in Course / Make Payment / Display Courses / Exit

**Expected Output**

- Show enrollment confirmation and receipt.
- Update available course capacity.
- Show fee discounts for premium students.

---

## Project Structure

```
CourseEnrollmentSystem/
├── src/
│   ├── model/       Instructor, Course, Student, PremiumStudent,
│   │                Enrollment, EnrollmentStatus, Payment, PaymentMethod, Receipt
│   ├── service/     CourseEnrollmentService   (all business rules)
│   ├── util/        InputUtil, ValidationUtil, IdGenerator
│   ├── exception/   EnrollmentException (base), InvalidInputException,
│   │                RecordNotFoundException, DuplicateRecordException,
│   │                CourseFullException, PaymentException
│   └── main/        Main   (menu-driven console UI)
└── README.md
```

## OOP Concepts Used

| Concept | Where |
|---|---|
| **Encapsulation** | All fields are `private`; access through getters/setters with validation (e.g. `Course.setFee`, `Student.setEmail`). |
| **Inheritance** | `PremiumStudent extends Student`; all custom exceptions extend `EnrollmentException`. |
| **Aggregation** | `Course` has an `Instructor`; `Enrollment` has a `Student` and a `Course`; `Payment` has an `Enrollment`; `Receipt` links to a `Student`, `Course` and `Payment`. |
| **Polymorphism** | `getDiscountRate()`, `hasExtraMaterials()` and `getStudentType()` are overridden in `PremiumStudent`; `Enrollment` calls `student.calculateDiscount()` on a `Student` reference and the correct version runs at run time. |

## Business Rules Implemented

1. A student can enroll only if the course has a free seat (`CourseFullException` otherwise).
2. Premium students get a **20 % fee discount** and **extra course materials**; regular students pay the full fee.
3. An enrollment is created as `PENDING PAYMENT` and becomes `CONFIRMED` only after the exact payable amount is paid.
4. A seat is taken as soon as the student enrolls (seat is held while payment is pending), and the available capacity is shown after every enrollment and in *Display Courses*.
5. Every `Receipt` stores references to the `Student` and the `Course` (and the `Payment`).

## Input Validation and Exception Handling

- Menu choice, fee, capacity, amount: number and range checks (`NumberFormatException` is converted to `InvalidInputException`).
- Names, e-mail, course title: pattern checks; IDs: format check.
- Duplicate course title, duplicate student e-mail, duplicate enrollment are rejected.
- Unknown student / course / enrollment IDs → `RecordNotFoundException`.
- Wrong payment amount or double payment → `PaymentException`.
- The main loop catches every `EnrollmentException`, so the program never crashes on bad input.

## How to Compile and Run

### Using Eclipse
1. `File → New → Java Project`, name it `CourseEnrollmentSystem` (untick *Create module-info.java*).
2. Copy the five package folders (`model`, `service`, `util`, `exception`, `main`) into the project's `src` folder.
3. Right-click `src/main/Main.java → Run As → Java Application`. Type your inputs in the **Console** view.

### Using the command line (JDK 11 or later)

Linux / macOS:
```bash
mkdir -p bin
javac -d bin $(find src -name "*.java")
java -cp bin main.Main
```

Windows (Command Prompt):
```bat
mkdir bin
dir /s /b src\*.java > sources.txt
javac -d bin @sources.txt
java -cp bin main.Main
```

## Sample Run (short)

```
================= MAIN MENU =================
  1. Add Course
  2. Add Student
  3. Enroll in Course
  4. Make Payment
  5. Display Courses
  6. Exit
=============================================
Enter your choice (1-6): 3
Student ID (e.g. STU1001): STU1002
Course ID  (e.g. CRS101) : CRS101

======= ENROLLMENT REGISTERED - PAYMENT PENDING =======
Enrollment ID   : ENR5001
Student         : Anitha S (STU1002) [Premium]
Course          : Java Programming (CRS101)
Course Fee      : Rs. 5000.00
Premium Discount: Rs. 1000.00 (20% off)
Amount Payable  : Rs. 4000.00
Seats Remaining : 1 of 2
Status          : PENDING PAYMENT
```

IDs are generated automatically: `CRS101…` (courses), `STU1001…` (students), `ENR5001…` (enrollments), `PAY9001…` (payments), `RCP7001…` (receipts).
