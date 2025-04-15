package com.redutec.core.meta;

/**
 * AdministratorRole 열거형은 시스템 관리자의 권한을 나타냅니다.
 * 각 역할은 시스템 내에서 수행할 수 있는 권한 범위를 세분화하여 정의합니다.
 */
public enum AdministratorRole {
    /**
     * 최고 수준의 관리자로, 시스템의 모든 기능에 대한 전면적인 접근 권한을 가집니다.
     */
    MASTER,
    /**
     * 일반 관리자 역할로, 주요 시스템 관리 기능을 수행할 수 있습니다.
     */
    CHIEF,
    /**
     * 특정 부서 또는 시스템 영역을 관리하는 역할입니다.
     */
    MANAGER,
    /**
     * 사용자나 콘텐츠를 모니터링하고 규제하는 역할입니다.
     */
    USER
}