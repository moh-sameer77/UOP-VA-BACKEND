package com.flatdevs.virtualassistant.student.service;

import com.flatdevs.virtualassistant.contract.Result;
import com.flatdevs.virtualassistant.student.entity.*;
import com.flatdevs.virtualassistant.student.model.*;
import com.flatdevs.virtualassistant.student.repository.*;
import com.flatdevs.virtualassistant.user.entity.EnrolledStudentEntity;
import com.flatdevs.virtualassistant.user.repository.UserRepository;
import com.flatdevs.virtualassistant.user.repository.EnrolledStudentRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StudentService {
    private final EnrolledStudentRepository studentRepository;
    private final AcademicPlanRepository academicPlanRepository;
    private final PassedCourseRepository passedCourseRepository;
    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;
    private final CoursesRepository coursesRepository;
    private final AcademicPlanCoursesRepository academicPlanCoursesRepository;
    private final TypeRepository typeRepository;
    private final NotificationRepository notificationRepository;


    public StudentService(EnrolledStudentRepository studentRepository, PassedCourseRepository passedCourseRepository, ReminderRepository reminderRepository, UserRepository userRepository, AcademicPlanRepository academicPlanRepository, CoursesRepository coursesRepository, AcademicPlanCoursesRepository academicPlanCoursesRepository, TypeRepository typeRepository, NotificationRepository notificationRepository) {
        this.studentRepository = studentRepository;
        this.passedCourseRepository = passedCourseRepository;
        this.reminderRepository = reminderRepository;
        this.userRepository = userRepository;
        this.academicPlanRepository = academicPlanRepository;
        this.coursesRepository = coursesRepository;
        this.academicPlanCoursesRepository = academicPlanCoursesRepository;
        this.typeRepository = typeRepository;
        this.notificationRepository = notificationRepository;
    }

    public Result addReminder(Reminder reminder, String email) {
        try {
            ReminderEntity reminderEntity = new ReminderEntity();
            reminderEntity.setUser(userRepository.findByEmail(email));
            log.info(userRepository.findByEmail(email).getEmail());
            reminderEntity.setFirstTime((LocalDateTime.parse(reminder.getFirstTime())));
            reminderEntity.setSecondTime((LocalDateTime.parse(reminder.getSecondTime())));
            reminderEntity.setDate(LocalDateTime.parse(reminder.getDate()));
            reminderEntity.setNote(reminder.getNote());
            reminderEntity.setTitle(reminder.getTitle());
            reminderEntity.setColor(reminder.getColor());
            reminderRepository.save(reminderEntity);
            return Result.builder().success(true).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.builder().success(false).build();
        }
    }

    public Result addNotification(Notification notification) {
        try {
            NotificationEntity notificationEntity = new NotificationEntity();
            notificationEntity.setTitle(notification.getTitle());
            notificationEntity.setDescription(notification.getDescription());
            notificationRepository.save(notificationEntity);
            return Result.builder().success(true).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.builder().success(false).build();
        }
    }


//
    public List<CourseEntity> getCoursesByType(Long type) {
        return coursesRepository.findByType(typeRepository.findById(type).get());
    }
//
//    public Page<CourseEntity> getUniversityReqs() {
//        return getCoursesByType("university requirements");
//    }
//
//    public Page<CourseEntity> getFacultyCompReqs() {
//        return getCoursesByType("Faculty Compulsory Requirement");
//    }
//
//    public Page<CourseEntity> getDeptCompReqs() {
//        return getCoursesByType("Department Compulsory Requirements");
//    }
//
//    public Page<CourseEntity> getDeptElectiveReqs() {
//        return getCoursesByType("Department Elective Requirements");
//    }
//
//    public Page<CourseEntity> getDeptSupportiveCompReqs() {
//        return getCoursesByType("Department Supportive Compulsory Requirement");
//    }

    public Result createAcademicPlan(AcademicPlan academicPlan) {
        try {
            AcademicPlanEntity academicPlanEntity = new AcademicPlanEntity();
            academicPlanEntity.setName(academicPlan.getName());
            academicPlanEntity.setCreditHours(academicPlan.getCreditHours());

            academicPlanRepository.save(academicPlanEntity);

            return Result.builder().success(true).message(academicPlanEntity).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.builder().success(false).build();
        }

    }
    public Result deleteAcademicPlan(Long id) {
        try {
            academicPlanRepository.deleteById(id);
            return Result.builder().success(true).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.builder().success(false).message(e.getMessage()).build();
        }
    }
//    public Result updateAcademicPlan(AcademicPlan academicPlan) {
//        try {
//            AcademicPlanEntity currentAcademicPlan =  academicPlanRepository.findUserById(academicPlan.getId());
//            if (currentAcademicPlan == null) {
//                return Result.builder().success(false).message("Academic plan not found").build();
//            }
//            currentAcademicPlan.setName(academicPlan.getName());
//            currentAcademicPlan.setCreditHours(academicPlan.getCreditHours());
//            //update other fields if you have any
//            academicPlanRepository.save(currentAcademicPlan);
//            return Result.builder().success(true).build();
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return Result.builder().success(false).message(e.getMessage()).build();
//        }
//    }


    //admin
    public Result createCourse(Course course) {
        try {
            CourseEntity courseEntity = new CourseEntity();

            courseEntity.setName(course.getName());
            courseEntity.setCreditHours(course.getCreditHours());
            List<CourseEntity> prerequisites = new ArrayList<>();
            course.getPrerequisites().forEach(s -> prerequisites.add(coursesRepository.findById(Long.parseLong(s)).get()));
            courseEntity.setPrerequisites(prerequisites);
            courseEntity.setType(typeRepository.findById(Long.parseLong(course.getType())).get());
            coursesRepository.save(courseEntity);
            coursesRepository.flush();
            AcademicPlanCoursesEntity academicPlanCoursesEntity = new AcademicPlanCoursesEntity();
            academicPlanCoursesEntity.setCourse(coursesRepository.findById(courseEntity.getId()).get());
            academicPlanCoursesEntity.setAcademicPlan(academicPlanRepository.findById(Long.parseLong(course.getAcademicPlan())).get());
            academicPlanCoursesRepository.save(academicPlanCoursesEntity);

            return Result.builder().success(true).message(courseEntity).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.builder().success(false).build();
        }

    }


    public Result addPassedCourse(PassedCourse passedCourse, String email) {
        try {
            CourseEntity courseEntity = coursesRepository.findById((long) passedCourse.getCourse()).get();
            PassedCourseEntity passedCourseEntity = new PassedCourseEntity();
            passedCourseEntity.setCourse(coursesRepository.findById((long) passedCourse.getCourse()).get());
            passedCourseEntity.setEnrolledStudent(studentRepository.findByUser(userRepository.findByEmail(email)));
            passedCourseEntity.setMark(passedCourse.getMark());
            passedCourseRepository.save(passedCourseEntity);
             return Result.builder().success(true).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.builder().success(false).build();

        }

    }
    public List<AcademicPlanCoursesEntity> getPassedCourses(String email) {
        EnrolledStudentEntity enrolledStudentEntity = studentRepository.findByUser(userRepository.findByEmail(email));

        return enrolledStudentEntity.getAcademicPlan().getAcademicPlanCoursesEntity();
    }


    public  BigDecimal calculateGPA(String email) {
        EnrolledStudentEntity enrolledStudentEntity = studentRepository.findByUser(userRepository.findByEmail(email));
        BigDecimal totalGradePoints = BigDecimal.ZERO;
        BigDecimal totalCreditHours = BigDecimal.ZERO;
        BigDecimal gpa = BigDecimal.ZERO;

        for (PassedCourseEntity course : enrolledStudentEntity.getPassedCourseEntities()) {
            BigDecimal gradePoints = course.getCourse().getCreditHours().multiply(BigDecimal.valueOf(course.getMark()));
            totalGradePoints = totalGradePoints.add(gradePoints);
            totalCreditHours = totalCreditHours.add(course.getCourse().getCreditHours());
        }
        gpa = totalGradePoints.divide(totalCreditHours, 2, RoundingMode.HALF_UP);
        enrolledStudentEntity.setGpa(gpa);
        studentRepository.save(enrolledStudentEntity);

        return gpa;
    }


    public BigDecimal calculateAcademicProgress(String email) {
        EnrolledStudentEntity enrolledStudentEntity = studentRepository.findByUser(userRepository.findByEmail(email));
        BigDecimal totalCreditHours = enrolledStudentEntity.getCreditHours();
        BigDecimal completedCreditHours = BigDecimal.ZERO;
        BigDecimal academicProgress = BigDecimal.ZERO;
        for (PassedCourseEntity course : enrolledStudentEntity.getPassedCourseEntities()) {
            completedCreditHours = completedCreditHours.add(course.getCourse().getCreditHours());
        }
        academicProgress= completedCreditHours.divide(totalCreditHours, 2, RoundingMode.HALF_UP);
        enrolledStudentEntity.setAcademicProgress(academicProgress);
        studentRepository.save(enrolledStudentEntity);
        return academicProgress;
    }

    public List<CourseEntity> suggestSchedule(String email) {
        EnrolledStudentEntity enrolledStudentEntity = studentRepository.findByUser(userRepository.findByEmail(email));
        List<CourseEntity> suggestedCourses = new ArrayList<>();
        List<AcademicPlanCoursesEntity> remainingCourses = new ArrayList<>(enrolledStudentEntity.getAcademicPlan().getAcademicPlanCoursesEntity());
        for (PassedCourseEntity passedCourse : enrolledStudentEntity.getPassedCourseEntities()) {
            remainingCourses.removeIf(academicPlanCoursesEntity -> academicPlanCoursesEntity.getCourse().equals(passedCourse.getCourse()));
        }
        for (AcademicPlanCoursesEntity course : remainingCourses) {
            boolean hasPrerequisites = true;
            for (CourseEntity prerequisite : course.getCourse().getPrerequisites()) {
                if (!enrolledStudentEntity.getPassedCourseEntities().contains(prerequisite)) {
                    hasPrerequisites = false;
                    break;
                }
            }

            if (hasPrerequisites) {
                suggestedCourses.add(course.getCourse());
            }
        }
        // Add courses that have passed prerequisites to the suggested courses list
        for (PassedCourseEntity passedCourse : enrolledStudentEntity.getPassedCourseEntities()) {
            for (AcademicPlanCoursesEntity course : remainingCourses) {
                if (course.getCourse().getPrerequisites().contains(passedCourse.getCourse())) {
                    suggestedCourses.add(course.getCourse());
                }
            }
        }
        return suggestedCourses;
    }


}