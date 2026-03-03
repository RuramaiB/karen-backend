package zw.co.hushsoft.properbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import zw.co.hushsoft.properbackend.assignmet.Assignment;
import zw.co.hushsoft.properbackend.assignmet.AssignmentRepository;
import zw.co.hushsoft.properbackend.assignmet.AssignmentStatus;
import zw.co.hushsoft.properbackend.course.Course;
import zw.co.hushsoft.properbackend.course.CourseRepository;
import zw.co.hushsoft.properbackend.material.Material;
import zw.co.hushsoft.properbackend.material.MaterialRepository;
import zw.co.hushsoft.properbackend.exam.Exam;
import zw.co.hushsoft.properbackend.exam.ExamRepository;
import zw.co.hushsoft.properbackend.question.Options;
import zw.co.hushsoft.properbackend.question.Question;
import zw.co.hushsoft.properbackend.user.Role;
import zw.co.hushsoft.properbackend.user.User;
import zw.co.hushsoft.properbackend.user.UserRepository;

import zw.co.hushsoft.properbackend.stream.StreamType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataBootstrapInitializer implements CommandLineRunner {

        private final UserRepository userRepository;
        private final CourseRepository courseRepository;
        private final AssignmentRepository assignmentRepository;
        private final ExamRepository examRepository;
        private final MaterialRepository materialRepository;
        private final PasswordEncoder passwordEncoder;
        private final zw.co.hushsoft.properbackend.stream.StreamServices streamServices;

        @Override
        public void run(String... args) throws Exception {
                System.out.println("Checking for data to bootstrap...");

                // 1. Ensure Default Lecturer exists
                User lecturer = User.builder()
                                .firstname("Master")
                                .lastname("Lecturer")
                                .email("lecturer@hit.ac.zw")
                                .password(passwordEncoder.encode("password"))
                                .role(Role.LECTURER)
                                .enabled(true)
                                .isVerified(true)
                                .isFirstTime(false)
                                .build();

                User finalLecturer = lecturer;
                lecturer = userRepository.findByEmail(lecturer.getEmail())
                                .orElseGet(() -> userRepository.save(finalLecturer));

                // 1b. Ensure Default Student exists
                User student = User.builder()
                                .firstname("Test")
                                .lastname("Student")
                                .email("student@hit.ac.zw")
                                .password(passwordEncoder.encode("password"))
                                .role(Role.STUDENT)
                                .enabled(true)
                                .isVerified(true)
                                .isFirstTime(false)
                                .build();
                User finalStudent = student;
                userRepository.findByEmail(student.getEmail())
                                .orElseGet(() -> userRepository.save(finalStudent));

                // Get all students for enrollment
                List<User> allStudents = userRepository.findAllByRole(Role.STUDENT);

                // 2. Ensure Courses exist
                String[] courseNames = {
                                "Introduction to Artificial Intelligence",
                                "Modern Software Engineering",
                                "Data Science & Analytics",
                                "Cloud Infrastructure Management",
                                "Cyber Security Principles"
                };
                String[] courseCodes = { "AI101", "SE202", "DS303", "CC404", "CS505" };

                for (int i = 0; i < courseNames.length; i++) {
                        String code = courseCodes[i];
                        String name = courseNames[i];
                        Course courseFromDb = courseRepository.findByCourseCode(code)
                                        .orElseGet(() -> {
                                                Course newCourse = new Course();
                                                newCourse.setCourseName(name);
                                                newCourse.setCourseCode(code);
                                                newCourse.setClassCode("CLASS-" + code);
                                                newCourse.setLecturer(finalLecturer);
                                                return courseRepository.save(newCourse);
                                        });

                        // Ensure all students are enrolled in each course
                        if (courseFromDb.getStudents() == null) {
                                courseFromDb.setStudents(new ArrayList<>());
                        }
                        boolean updated = false;
                        for (User s : allStudents) {
                                if (courseFromDb.getStudents().stream()
                                                .noneMatch(u -> u.getEmail().equals(s.getEmail()))) {
                                        courseFromDb.getStudents().add(s);
                                        updated = true;
                                }
                        }
                        if (updated) {
                                courseRepository.save(courseFromDb);
                        }

                        final Course course = courseFromDb;

                        // 3. Ensure Materials exist
                        if (materialRepository.findAllByCourse(course).isEmpty()) {
                                Material material = new Material();
                                material.setCourse(course);
                                material.setTitle("Lecture Notes: Introduction to " + code);
                                material.setComment("Please read through these notes before the next class.");
                                material.setPostedOn(LocalDate.now());
                                material = materialRepository.save(material);

                                streamServices.addStream(
                                                "New Material: " + material.getTitle(),
                                                course.getCourseID(),
                                                StreamType.material,
                                                material.getMaterialID());
                        }

                        // 4. Ensure Assignments exist
                        if (assignmentRepository.findAllByCourse(course).isEmpty()) {
                                Assignment assignment = new Assignment();
                                assignment.setCourse(course);
                                assignment.setTitle("Project Proposal - " + code);
                                assignment.setAssignmentNumber("A001");
                                assignment.setDueDate(LocalDateTime.now().plusDays(7));
                                assignment.setAssignmentStatus(AssignmentStatus.pending);
                                assignment.setPostedOn(LocalDate.now());
                                assignment = assignmentRepository.save(assignment);

                                streamServices.addStream(
                                                "New Assignment: " + assignment.getTitle(),
                                                course.getCourseID(),
                                                StreamType.assignment,
                                                assignment.getMaterialID());
                        }

                        // 5. Ensure Exams exist (Specific requirements for AI101)
                        final String currentCourseId = course.getCourseID();
                        if (course.getCourseCode().equals("AI101") && examRepository.count() < 2) {
                                // Create 5-question exam
                                createSpecificExam(course, "AI Foundations MCQ", "Foundational AI Assessment", 5);
                                // Create 15-question exam
                                createSpecificExam(course, "AI Comprehensive MCQ", "Comprehensive AI Final Assessment",
                                                15);
                        } else if (examRepository.findAll().stream()
                                        .noneMatch(e -> e.getCourse().getCourseID().equals(currentCourseId))) {
                                // Default exams for other courses if they don't have any
                                createSpecificExam(course, code + " Basic Assessment", "General knowledge test", 5);
                        }
                }
                System.out.println("Data bootstrapping check completed.");
        }

        private void createSpecificExam(Course course, String title, String description, int numQuestions) {
                Exam exam = Exam.builder()
                                .examTitle(title)
                                .examDescription(description)
                                .dueDate(LocalDate.now().plusDays(7))
                                .startTime(LocalDateTime.now().minusHours(1)) // Start 1 hour ago for testing
                                .duration("120")
                                .course(course)
                                .questions(generateMCQQuestions(course, numQuestions))
                                .build();
                exam = examRepository.save(exam);

                streamServices.addStream(
                                "New Exam: " + exam.getExamTitle(),
                                course.getCourseID(),
                                StreamType.exam,
                                exam.getExamID());
        }

        private List<Question> generateMCQQuestions(Course course, int count) {
                List<Question> questions = new ArrayList<>();
                String[] topics;
                String subject = course.getCourseName();

                switch (course.getCourseCode()) {
                        case "SE202":
                                topics = new String[] { "Design Patterns", "Agile", "Unit Testing", "CI/CD",
                                                "Version Control", "Refactoring", "Solid Principles" };
                                break;
                        case "DS303":
                                topics = new String[] { "Data Cleaning", "Statistics", "Visualization", "SQL",
                                                "Machine Learning", "Big Data", "Data Wrangling" };
                                break;
                        case "CC404":
                                topics = new String[] { "Virtualization", "Containers", "AWS", "Azure", "Serverless",
                                                "Microservices", "Load Balancing" };
                                break;
                        case "CS505":
                                topics = new String[] { "Encryption", "Firewalls", "VPNs", "Malware", "Phishing",
                                                "Penetration Testing", "Oauth2" };
                                break;
                        default:
                                topics = new String[] { "Neural Networks", "Deep Learning", "NLP", "Machine Learning",
                                                "Computer Vision", "Reinforcement Learning", "Decision Trees" };
                }

                for (int i = 0; i < count; i++) {
                        String topic = topics[i % topics.length];
                        Question q = new Question();
                        q.setTitle("What is the primary role of " + topic + " in " + subject + "?");
                        q.setType("MULTIPLE_CHOICE");
                        q.setOptions(List.of(
                                        new Options("Process optimization"),
                                        new Options("System automation"),
                                        new Options("Quality assurance"),
                                        new Options("Efficiency enhancement")));
                        questions.add(q);
                }
                return questions;
        }

}
