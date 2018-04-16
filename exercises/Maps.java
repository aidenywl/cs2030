import java.util.HashMap;

/**
 * Maps class is used to test my learning from Lecture 5 of CS2030.
 * Hashmaps are used in this program.
 *
 * @author Low Yew Woei
 * @version 4th March 2018
 */

class Maps {
  public static void main(String args[]) {
    HashMap<Student, Teacher> schoolList = new HashMap<>();
    Teacher t1 = new Teacher("Yew Woei");
    Teacher t2 = new Teacher("Prof Ryan");
    Teacher t3 = new Teacher("Life");

    // creating array of students
    Student[] students = new Student[100];
    int nameInt = 65;
    for(int i = 0; i < 100; i++) {
      char studentName = (char) nameInt;
      Student currentStudent = new Student(i, Character.toString(studentName));
      students[i] = currentStudent;
    }
    for (int i = 0; i < 100; i++) {
      Student currentStudent = students[i];
      Teacher currentTeacher = null;
      nameInt++;
      int bin = i % 3;
      switch (bin) {
        case 0:
          currentTeacher = t1;
          break;
        case 1:
          currentTeacher = t2;
          break;
        case 2:
          currentTeacher = t3;
          break;
        default:
          System.out.println("computer fucked up");
      }
      schoolList.put(currentStudent, currentTeacher);
    }

    // end of for loop
    // trying out the hashmap values
    for (int i = 0; i < 100; i++) {
      Teacher currentTeacher = schoolList.get(students[i]);
      System.out.println(currentTeacher);
    }
  }
}

class Student {
  private final int matric;
  private final String name;
  
  Student(int matric, String name) {
    this.matric = matric;
    this.name = name;
  }

  @Override
  public String toString() {
    return "Student name: " + this.name + " Matric Number: " + matric;
  }
}

class Teacher {
  private final String name;

  Teacher(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Teacher's name is: " + this.name;
  }
}
