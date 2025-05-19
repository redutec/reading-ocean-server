package com.redutec.core.repository;

import com.redutec.core.entity.Popup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PopupRepository extends JpaRepository<Popup, Long>, JpaSpecificationExecutor<Popup> {
}