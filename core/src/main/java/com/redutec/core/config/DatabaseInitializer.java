package com.redutec.core.config;

import com.redutec.core.dto.BranchDto;
import com.redutec.core.entity.*;
import com.redutec.core.mapper.BranchMapper;
import com.redutec.core.meta.SampleData;
import com.redutec.core.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
@Getter
@RequiredArgsConstructor
public class DatabaseInitializer {
    private final AdminUserRepository adminUserRepository;
    private final AdminMenuRepository adminMenuRepository;
    private final TeachingOceanMenuRepository teachingOceanMenuRepository;
    private final BranchRepository branchRepository;
    private final InstituteRepository instituteRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final BranchMapper branchMapper;

    @PostConstruct
    @Transactional
    public void initializeDatabase() {
        // Admin 서비스의 샘플 데이터 생성
        createSampleAdminUser();
        createSampleParentAdminMenu();
        createSampleChildrenAdminMenu();
        // TeachingOcean 서비스의 샘플 데이터 생성
        createSampleParentTeachingOceanMenu();
        createSampleChildrenTeachingOceanMenu();
        createSampleBranch();
        createSampleInstitute();
        createSampleTeacher();
    }

    // Admin 서비스용
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createSampleAdminUser() {
        if (adminUserRepository.count() == 0) {
            log.info("어드민 사용자 테이블에 데이터가 존재하지 않습니다. 샘플 어드민 사용자 데이터를 생성합니다.");
            var adminUsers = Arrays.stream(SampleData.SampleAdminUser.values())
                    .map(adminUser -> AdminUser.builder()
                            .accountId(adminUser.getAccountId())
                            .email(adminUser.getEmail())
                            .password(passwordEncoder.encode(adminUser.getPassword()))
                            .role(adminUser.getRole())
                            .authenticationStatus(adminUser.getAuthenticationStatus())
                            .nickname(adminUser.getNickname())
                            .failedLoginAttempts(adminUser.getFailedLoginAttempts())
                            .build())
                    .toList();
            adminUserRepository.saveAll(adminUsers);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createSampleParentAdminMenu() {
        if (adminMenuRepository.count() == 0) {
            log.info("어드민 메뉴 테이블에 상위 메뉴가 존재하지 않습니다. 샘플 어드민 상위 메뉴를 생성합니다.");
            var parentAdminMenus = Arrays.stream(SampleData.ParentAdminMenu.values())
                    .map(pam -> AdminMenu.builder()
                            .name(pam.getName())
                            .url(pam.getUrl())
                            .description(pam.getDescription())
                            .accessibleRoles(pam.getAccessibleRoles())
                            .depth(0)
                            .available(true)
                            .build())
                    .toList();
            adminMenuRepository.saveAll(parentAdminMenus);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createSampleChildrenAdminMenu() {
        if (!adminMenuRepository.existsByDepth(1)) {
            log.info("어드민 메뉴 테이블에 하위 메뉴가 존재하지 않습니다. 샘플 어드민 하위 메뉴를 생성합니다.");
            // 먼저 모든 상위 메뉴를 Map으로 변환 (이름 -> Menu)
            Map<String, AdminMenu> parentMenuMap = adminMenuRepository.findAll().stream()
                    .collect(Collectors.toMap(AdminMenu::getName, Function.identity()));
            var childrenAdminMenus = Arrays.stream(SampleData.ChildrenAdminMenu.values())
                    .map(cam -> {
                        var parent = parentMenuMap.get(cam.getParentAdminMenu().getName());
                        if (parent == null) {
                            throw new EntityNotFoundException("상위 메뉴를 찾을 수 없습니다. menuName: " + cam.getParentAdminMenu().getName());
                        }
                        return AdminMenu.builder()
                                .name(cam.getName())
                                .url(cam.getUrl())
                                .description(cam.getDescription())
                                .accessibleRoles(cam.getAccessibleRoles())
                                .depth(parent.getDepth() + 1)
                                .parent(parent)
                                .available(true)
                                .build();
                    })
                    .toList();
            adminMenuRepository.saveAll(childrenAdminMenus);
        }
    }

    // 티칭오션 용
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createSampleParentTeachingOceanMenu() {
        if (teachingOceanMenuRepository.count() == 0) {
            log.info("티칭오션 메뉴 테이블에 상위 메뉴가 없습니다. 샘플 티칭오션 상위 메뉴들을 생성합니다.");
            var parentTeachingOceanMenus = Arrays.stream(SampleData.ParentTeachingOceanMenu.values())
                    .map(ptom -> TeachingOceanMenu.builder()
                            .name(ptom.getName())
                            .url(ptom.getUrl())
                            .description(ptom.getDescription())
                            .accessibleRoles(ptom.getAccessibleRoles())
                            .depth(0)
                            .available(true)
                            .build())
                    .toList();
            teachingOceanMenuRepository.saveAll(parentTeachingOceanMenus);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createSampleChildrenTeachingOceanMenu() {
        if (!teachingOceanMenuRepository.existsByDepth(1)) {
            log.info("티칭오션 메뉴 테이블에 하위 메뉴가 없습니다. 샘플 티칭오션 하위 메뉴들을 생성합니다.");
            // 먼저 모든 상위 메뉴를 Map으로 변환 (이름 -> Menu)
            Map<String, TeachingOceanMenu> parentMenuMap = teachingOceanMenuRepository.findAll().stream()
                    .collect(Collectors.toMap(TeachingOceanMenu::getName, Function.identity()));
            var childrenTeachingOceanMenus = Arrays.stream(SampleData.ChildrenTeachingOceanMenu.values())
                    .map(ctom -> {
                        var parent = parentMenuMap.get(ctom.getParentTeachingOceanMenu().getName());
                        if (parent == null) {
                            throw new EntityNotFoundException("상위 메뉴를 찾을 수 없습니다. menuName: " + ctom.getParentTeachingOceanMenu().getName());
                        }
                        return TeachingOceanMenu.builder()
                                .name(ctom.getName())
                                .url(ctom.getUrl())
                                .description(ctom.getDescription())
                                .accessibleRoles(ctom.getAccessibleRoles())
                                .depth(parent.getDepth() + 1)
                                .parent(parent)
                                .available(true)
                                .build();
                    })
                    .toList();
            teachingOceanMenuRepository.saveAll(childrenTeachingOceanMenus);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createSampleBranch() {
        if (branchRepository.count() == 0) {
            log.info("지사 테이블에 데이터가 존재하지 않습니다. 샘플 지사 데이터를 생성합니다.");
            var branches = Arrays.stream(SampleData.SampleBranch.values())
                    .map(branch -> Branch.builder()
                            .region(branch.getRegion())
                            .name(branch.getName())
                            .status(branch.getStatus())
                            .businessArea(branch.getBusinessArea())
                            .contractFileName(branch.getContractFileName())
                            .contractDate(branch.getContractDate())
                            .renewalDate(branch.getRenewalDate())
                            .description(branch.getDescription())
                            .build())
                    .toList();
            branchRepository.saveAll(branches);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createSampleInstitute() {
        if (instituteRepository.count() == 0) {
            log.info("교육기관 테이블에 데이터가 존재하지 않습니다. 샘플 교육기관 데이터를 생성합니다.");
            var institutes = Arrays.stream(SampleData.SampleInstitute.values())
                    .map(institute -> Institute.builder()
                            .name(institute.getName())
                            .businessRegistrationName(institute.getBusinessRegistrationName())
                            .address(institute.getAddress())
                            .postalCode(institute.getPostalCode())
                            .phoneNumber(institute.getPhoneNumber())
                            .url(institute.getUrl())
                            .naverPlaceUrl(institute.getNaverPlaceUrl())
                            .type(institute.getType())
                            .managementType(institute.getManagementType())
                            .status(institute.getStatus())
                            .operationStatus(institute.getOperationStatus())
                            .branch(branchRepository.findByName(institute.getBranchName())
                                    .orElseThrow(() -> new EntityNotFoundException("지사 정보를 찾을 수 없습니다. branchName: " + institute.getBranchName())))
                            .build())
                    .toList();
            instituteRepository.saveAll(institutes);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createSampleTeacher() {
        // 샘플 교사 데이터 생성 후 INSERT
        if (teacherRepository.count() == 0) {
            log.info("교사 테이블에 데이터가 존재하지 않습니다. 샘플 교사 데이터를 생성합니다.");
            var teachers = Arrays.stream(SampleData.SampleTeacher.values())
                    .map(teacher -> Teacher.builder()
                            .accountId(teacher.getAccountId())
                            .password(passwordEncoder.encode(teacher.getPassword()))
                            .name(teacher.getName())
                            .phoneNumber(teacher.getPhoneNumber())
                            .email(teacher.getEmail())
                            .status(teacher.getStatus())
                            .role(teacher.getRole())
                            .authenticationStatus(teacher.getAuthenticationStatus())
                            .institute(instituteRepository.findByName(teacher.getInstituteName())
                                    .orElseThrow(() -> new EntityNotFoundException("교육기관 정보를 찾을 수 없습니다. instituteName: " + teacher.getInstituteName())))
                            .build())
                    .toList();
            teacherRepository.saveAll(teachers);
        }
        // 샘플 지사를 담당할 교사를 지정하여 지사 엔티티를 UPDATE
        Arrays.stream(SampleData.SampleBranch.values())
                .forEach(sampleBranch -> {
                    var branch = branchRepository.findByName(sampleBranch.getName())
                            .orElseThrow(() -> new EntityNotFoundException("지사 정보를 찾을 수 없습니다. branchName: " + sampleBranch.getName()));
                    var managerTeacher = teacherRepository.findByAccountId(sampleBranch.getManagerAccountId())
                            .orElseThrow(() -> new EntityNotFoundException("교사 정보를 찾을 수 없습니다. accountId: " + sampleBranch.getManagerAccountId()));
                    var updateBranchRequest = new BranchDto.UpdateBranchRequest(
                            managerTeacher.getId(),
                            sampleBranch.getRegion(),
                            sampleBranch.getName(),
                            sampleBranch.getStatus(),
                            sampleBranch.getBusinessArea(),
                            null,
                            sampleBranch.getContractDate(),
                            sampleBranch.getRenewalDate(),
                            sampleBranch.getDescription()
                            );
                    branchMapper.updateEntity(
                            branch,
                            updateBranchRequest,
                            managerTeacher,
                            sampleBranch.getContractFileName()
                    );
                    branchRepository.saveAndFlush(branch);
                });
    }
}