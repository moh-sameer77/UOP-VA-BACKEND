package com.flatdevs.virtualassistant.student.api;
import com.flatdevs.virtualassistant.contract.Result;
import com.flatdevs.virtualassistant.student.entity.CourseEntity;
import com.flatdevs.virtualassistant.student.model.*;
import com.flatdevs.virtualassistant.student.repository.CoursesRepository;
import com.flatdevs.virtualassistant.student.repository.NotificationRepository;
import com.flatdevs.virtualassistant.student.repository.ReminderRepository;
import com.flatdevs.virtualassistant.student.service.StudentService;
import com.flatdevs.virtualassistant.user.entity.EnrolledStudentEntity;
import com.flatdevs.virtualassistant.user.repository.EnrolledStudentRepository;
import com.flatdevs.virtualassistant.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.flatdevs.virtualassistant.user.service.auth.JwtUtil;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/student")
public class StudentApi {

    private final StudentService studentService;
    private final EnrolledStudentRepository studentRepository;
    private final ReminderRepository reminderRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CoursesRepository coursesRepository;
    private final NotificationRepository notificationRepository;

    public StudentApi(StudentService studentService, EnrolledStudentRepository studentRepository,
                      ReminderRepository reminderRepository, JwtUtil jwtUtil, UserRepository userRepository, CoursesRepository coursesRepository,
                      NotificationRepository notificationRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.reminderRepository = reminderRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.coursesRepository = coursesRepository;
        this.notificationRepository = notificationRepository;
    }
    //student tested
    @PostMapping("/add/reminder")
    public ResponseEntity<Result> addReminder(@RequestHeader("Authorization") String token, @RequestBody Reminder reminder) {
        return ResponseEntity.ok().body(studentService.addReminder(reminder, jwtUtil.getTokenClaims(token).getSubject()));
    }
    @PostMapping("/add/notification")
    public ResponseEntity<Result> addNotification(@RequestHeader("Authorization") String token, @RequestBody Notification notification) {
        return ResponseEntity.ok().body(studentService.addNotification(notification));
    }
    @GetMapping("/get/notification")
    public ResponseEntity<Result> getNotification() {
        return ResponseEntity.ok().body(Result.builder().success(true).message(notificationRepository.findAll()).build());
    }

    @PostMapping("/add/PassedCourse")
    public ResponseEntity<Result> addPassedCourse(@RequestHeader("Authorization") String token, @RequestBody PassedCourse passedCourse) {
        return ResponseEntity.ok().body(studentService.addPassedCourse(passedCourse, jwtUtil.getTokenClaims(token).getSubject()));
    }


    //student tested
    @GetMapping("/get/reminders")
    public ResponseEntity<Result> getReminders(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(Result.builder().success(true).message(reminderRepository.findAllByUser(userRepository.findByEmail(jwtUtil.getTokenClaims(token).getSubject()), Pageable.ofSize(10))).build());
    }
    @GetMapping("/get/PassedCourses")
    public ResponseEntity<Result> getPassedCourses(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(Result.builder().success(true).message( studentService.getPassedCourses( jwtUtil.getTokenClaims(token).getSubject()) ).build());
    }

    //admin //tested
    @PostMapping("/createCourse")
    public ResponseEntity<Result> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok().body(studentService.createCourse(course));
    }

    // admin //notTestedYet
//    @GetMapping("/get/PrerequisitesOfCourse")
//    public ResponseEntity<Result> getPrerequisitesOfCourse(@RequestBody Long courseId) {
//        return ResponseEntity.ok().body(Result.builder().success(true).message(studentService.getPrerequisitesOfCourse(courseId)).build());
//    }

//    // admin // tested
//    @PostMapping("/add/PrerequisitesToCourse/{courseId}")
//    public ResponseEntity<Result> addPrerequisitesToCourse(@PathVariable Long courseId, @RequestBody List<Long> prerequisiteCourseIds) {
//        return ResponseEntity.ok().body(studentService.addPrerequisitesToCourse(courseId, prerequisiteCourseIds));
//    }
    // admin //tested
    @PostMapping("/createAcademicPlan")
    public ResponseEntity<Result> createAcademicPlan(@RequestBody AcademicPlan academicPlan) {
        return ResponseEntity.ok().body(studentService.createAcademicPlan(academicPlan));
    }
    //admin //tested
    @DeleteMapping("/delete/AcademicPlan/{id}")
    public ResponseEntity<Result> deleteAcademicPlan(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentService.deleteAcademicPlan(id));
    }



//    @PostMapping("/createEnrolledStudent")
//    public ResponseEntity<Result> createEnrolledStudent(@RequestHeader("Authorization") String token, @RequestBody Long id, @RequestBody BigDecimal academicYear) {
//            return ResponseEntity.ok().body(studentService.createEnrolledStudent(jwtUtil.getTokenClaims(token).getSubject(), id,academicYear));
//    }

//    @PostMapping("/add/passedCourse")
//    public ResponseEntity<Result> addPassedCourse(@RequestHeader("Authorization") String token, @RequestBody CourseEntity courseEntity, @RequestParam("grade") String grade) {
//        try {
//            EnrolledStudentEntity student = studentRepository.findByUser(userRepository.findByEmail(jwtUtil.getTokenClaims(token).getSubject()));
//            studentService.addPassedCourse(student, courseEntity, grade);
//            return ResponseEntity.ok().body(Result.builder().success(true).build());
//        } catch (Exception e) {
//            return ResponseEntity.ok().body(Result.builder().success(false).build());
//        }
//    }
//
    @GetMapping("/get/coursesByTypes/{id}")
    public ResponseEntity<Result> getCoursesByType(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(Result.builder().success(true).message(studentService.getCoursesByType(id)).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.builder().success(false).build());
        }
    }
//
//    @GetMapping("/get/FacultyCompReqs")
//    public ResponseEntity<Result> getFacultyCompReqs(@RequestHeader("Authorization") String token) {
//        try {
//            EnrolledStudentEntity student = studentRepository.findByUser(userRepository.findByEmail(jwtUtil.getTokenClaims(token).getSubject()));
//            return ResponseEntity.ok().body(Result.builder().success(true).message(studentService.getFacultyCompReqs()).build());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.builder().success(false).build());
//        }
//    }
//    @GetMapping("/get/DeptCompReqs")
//    public ResponseEntity<Result> getDeptCompReqs(@RequestHeader("Authorization") String token) {
//        try {
//            EnrolledStudentEntity student = studentRepository.findByUser(userRepository.findByEmail(jwtUtil.getTokenClaims(token).getSubject()));
//            return ResponseEntity.ok().body(Result.builder().success(true).message(studentService.getDeptCompReqs()).build());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.builder().success(false).build());
//        }
//    }
//    @GetMapping("/get/DeptElectiveReqs")
//    public ResponseEntity<Result> getDeptElectiveReqs(@RequestHeader("Authorization") String token) {
//        try {
//            EnrolledStudentEntity student = studentRepository.findByUser(userRepository.findByEmail(jwtUtil.getTokenClaims(token).getSubject()));
//            return ResponseEntity.ok().body(Result.builder().success(true).message(studentService.getDeptElectiveReqs()).build());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.builder().success(false).build());
//        }
//    }
//    @GetMapping("/get/DeptSupportiveCompReqs")
//    public ResponseEntity<Result> getDeptSupportiveCompReqs(@RequestHeader("Authorization") String token) {
//        try {
//            EnrolledStudentEntity student = studentRepository.findByUser(userRepository.findByEmail(jwtUtil.getTokenClaims(token).getSubject()));
//            return ResponseEntity.ok().body(Result.builder().success(true).message(studentService.getDeptSupportiveCompReqs()).build());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.builder().success(false).build());
//        }
//    }

    @GetMapping("/calculateGPA")
    public ResponseEntity<Result> calculateGPA(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok().body(Result.builder().success(true).message(studentService.calculateGPA(jwtUtil.getTokenClaims(token).getSubject())).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.builder().success(false).build());
        }
    }
    @GetMapping("/calculateAcademicProgress")
    public ResponseEntity<Result> calculateAcademicProgress(@RequestHeader("Authorization") String token) {
        try {
            System.out.println(studentService.calculateAcademicProgress(jwtUtil.getTokenClaims(token).getSubject()));
            return ResponseEntity.ok().body(Result.builder().success(true).message(studentService.calculateAcademicProgress(jwtUtil.getTokenClaims(token).getSubject())).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.builder().success(false).build());
        }
    }

    @GetMapping("/suggestSchedule")
    public ResponseEntity<Result> suggestSchedule(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok().body(Result.builder().success(true).message(studentService.suggestSchedule(jwtUtil.getTokenClaims(token).getSubject())).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.builder().success(false).build());
        }
    }
//
//    @GetMapping("/get/academicProgress")
//    public ResponseEntity<Result> getAcademicProgress(@RequestHeader("Authorization") String token) {
//        try {
//            EnrolledStudentEntity student = studentRepository.findByUser(userRepository.findByEmail(jwtUtil.getTokenClaims(token).getSubject()));
//            return ResponseEntity.ok().body(Result.builder().success(true).message(studentService.calculateAcademicProgress(student)).build());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.builder().success(false).build());
//        }
//    }


//    @GetMapping("/suggest/schedule")
//    public ResponseEntity<Result> suggestSchedule(@RequestHeader("Authorization") String token) {
//        try {
//            EnrolledStudentEntity student = studentRepository.findByUser(userRepository.findByEmail(jwtUtil.getTokenClaims(token).getSubject()));
//            return ResponseEntity.ok().body(Result.builder().success(true).message(studentService.suggestSchedule(student)).build());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.builder().success(false).build());
//        }
//    }









}
    