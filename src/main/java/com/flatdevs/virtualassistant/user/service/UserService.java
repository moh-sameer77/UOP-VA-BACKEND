package com.flatdevs.virtualassistant.user.service;

import com.flatdevs.virtualassistant.contract.Result;
import com.flatdevs.virtualassistant.student.repository.AcademicPlanRepository;
import com.flatdevs.virtualassistant.user.entity.EnrolledStudentEntity;
import com.flatdevs.virtualassistant.user.entity.ProfileImageEntity;
import com.flatdevs.virtualassistant.user.entity.UserEntity;
import com.flatdevs.virtualassistant.user.entity.UserEntity.Gender;
import com.flatdevs.virtualassistant.user.entity.UserEntity.StudentType;
import com.flatdevs.virtualassistant.user.model.EnrolledStudent;
import com.flatdevs.virtualassistant.user.model.User;
import com.flatdevs.virtualassistant.user.repository.EnrolledStudentRepository;
import com.flatdevs.virtualassistant.user.repository.ProfileImageRepository;
import com.flatdevs.virtualassistant.user.repository.UserRepository;
import com.flatdevs.virtualassistant.user.service.auth.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ProfileImageRepository profileImageRepository;
    private final AcademicPlanRepository academicPlanRepository;
    private final EnrolledStudentRepository enrolledStudentRepository;


    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, ProfileImageRepository profileImageRepository, JwtUtil jwtUtil, AcademicPlanRepository academicPlanRepository, EnrolledStudentRepository enrolledStudentRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.profileImageRepository = profileImageRepository;
        this.jwtUtil = jwtUtil;

        this.academicPlanRepository = academicPlanRepository;
        this.enrolledStudentRepository = enrolledStudentRepository;
    }

    public Result registerAdmin(User user) {
        try {
            UserEntity userEntity = userRepository.findByEmail(user.getEmail());
            if (nonNull(userEntity)) {
                user.setEmail("userAlreadyExists");
                user.setPassword(null);
                return Result.builder().success(false).message(user).build();
            }
            user.setIsAdmin(true);
            saveUser(user);
            user.setToken(jwtUtil.generateUserToken(user.getEmail(), user.getIsAdmin()));
            return Result.builder().success(true).message(user).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.builder().success(false).message(user).build();
        }
    }


    public Result register(User user) {
        try {
            UserEntity userEntity = userRepository.findByEmail(user.getEmail());
            if (nonNull(userEntity)) {
                user.setEmail("userAlreadyExists");
                user.setPassword(null);
                return Result.builder().success(false).message(user).build();
            }
            user.setIsAdmin(false);
            saveUser(user);
            user.setToken(jwtUtil.generateUserToken(user.getEmail(), user.getIsAdmin()));
            return Result.builder().success(true).message(user).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.builder().success(false).build();
        }
    }

    public Result login(User user) {
        try {
            UserEntity userEntity = userRepository.findByEmail(user.getEmail());

                if (nonNull(userEntity)) {
                if(passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {

                    return Result.builder().success(true).message(buildUser(userEntity)).build();
                }
                user.setPassword("incorrectPassword");
                return Result.builder().success(false).message(user).build();
            }
            user.setEmail("userDoesNotExist");
            return Result.builder().success(false).message(user).build();
        } catch (Exception e) {
            return Result.builder().success(false).build();
        }
    }

    public Result registerEnrolled(EnrolledStudent enrolledStudent, String email)
    {
        try {
            EnrolledStudentEntity enrolledStudentEntity = new EnrolledStudentEntity();

            enrolledStudentEntity.setUser(userRepository.findByEmail(email));
            enrolledStudentEntity.setAcademicYear(enrolledStudent.getAcademicYear());
            enrolledStudentEntity.setAcademicPlan(academicPlanRepository.findById( Long.parseLong(enrolledStudent.getAcademicPlan())).get());
            enrolledStudentEntity.setMajor(enrolledStudentEntity.getAcademicPlan().getName());
            enrolledStudentEntity.setCreditHours(enrolledStudentEntity.getAcademicPlan().getCreditHours());
            enrolledStudentRepository.save(enrolledStudentEntity);
            return Result.builder().success(true).message(enrolledStudent).build();
        } catch (Exception e) {
            return Result.builder().success(false).build();
        }
    }

    private User buildUser(UserEntity userEntity) {
        return User.builder().dateOfBirth(userEntity.getDateOfBirth().toString())
                .email(userEntity.getEmail())
                .studentType(userEntity.getStudentType().toString())
                .firstName(userEntity.getFirstName()).
                lastName(userEntity.getLastName()).
                gender(userEntity.getGender().toString())
                .token(jwtUtil.generateUserToken(userEntity.getEmail(), userEntity.getIsAdmin()))
                .isAdmin(userEntity.getIsAdmin())
                .build();
    }


    private void saveUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setGender(Gender.valueOf(user.getGender()));
        userEntity.setStudentType(StudentType.valueOf(user.getStudentType()));
        userEntity.setDateOfBirth(LocalDateTime.parse(user.getDateOfBirth()));
        userEntity.setIsAdmin(user.getIsAdmin());
        userRepository.saveAndFlush(userEntity);
    }


    public Result uploadProfileImage(MultipartFile file, String email) {
        try {
            ProfileImageEntity profileImageEntity = new ProfileImageEntity();
            profileImageEntity.setImage(ImageUtil.compressImage(file.getBytes()));
            profileImageEntity.setName(file.getName());
            profileImageEntity.setType(file.getContentType());
            UserEntity userEntity = userRepository.findByEmail(email);
            userEntity.setImage(profileImageRepository.save(profileImageEntity));
            return Result.builder().success(true).build();
        } catch (Exception e)
        {
            log.info(e.getMessage());
            return Result.builder().success(false).build();
        }
    }

    public Result getUser(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if(isNull(user.getImage()))
            user.getImage().setImage(ImageUtil.decompressImage(user.getImage().getImage()));
        return Result.builder().success(true).message(user).build();
    }
}
