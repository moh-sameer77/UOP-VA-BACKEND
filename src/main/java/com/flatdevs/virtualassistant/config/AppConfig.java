package com.flatdevs.virtualassistant.config;

import com.flatdevs.virtualassistant.student.repository.*;
import com.flatdevs.virtualassistant.student.service.StudentService;
import com.flatdevs.virtualassistant.user.api.UserApi;
import com.flatdevs.virtualassistant.user.repository.EnrolledStudentRepository;
import com.flatdevs.virtualassistant.user.repository.ProfileImageRepository;
import com.flatdevs.virtualassistant.user.repository.UserRepository;
import com.flatdevs.virtualassistant.user.service.UserService;
import com.flatdevs.virtualassistant.user.service.auth.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil(@Value("${jwt.secret-key}") String secretKey,
                           @Value("${jwt.expiry-time}") long tokenExpiryTime) {
        return new JwtUtil(secretKey, tokenExpiryTime);
    }

    @Bean
    public UserService userService(PasswordEncoder passwordEncoder, UserRepository userRepository, ProfileImageRepository profileImageRepository, JwtUtil jwtUtil,AcademicPlanRepository academicPlanRepository, EnrolledStudentRepository enrolledStudentRepository)
    {
        return new UserService(passwordEncoder, userRepository, profileImageRepository, jwtUtil, academicPlanRepository, enrolledStudentRepository);
    }

    @Bean
    public UserApi userApi(UserService userService, JwtUtil jwtUtil) {
        return new UserApi(userService, jwtUtil);
    }

    @Bean
    public StudentService studentService(EnrolledStudentRepository studentRepository, PassedCourseRepository passedCourseRepository, ReminderRepository reminderRepository, UserRepository userRepository, AcademicPlanRepository academicPlanRepository, CoursesRepository coursesRepository, AcademicPlanCoursesRepository academicPlanCoursesRepository, TypeRepository typeRepository, NotificationRepository notificationRepository)
    {
        return new StudentService( studentRepository, passedCourseRepository, reminderRepository,  userRepository,  academicPlanRepository,  coursesRepository, academicPlanCoursesRepository, typeRepository, notificationRepository);
    }

//    @Bean
//    public StudentApi studentApi(StudentService studentService, EnrolledStudentRepository studentRepository,
//                                 ReminderRepository reminderRepository, JwtUtil jwtUtil, UserRepository userRepository, CoursesRepository coursesRepository) {
//        return new StudentApi( studentService,  studentRepository,
//                 reminderRepository,  jwtUtil,  userRepository,  coursesRepository);
//    }
}