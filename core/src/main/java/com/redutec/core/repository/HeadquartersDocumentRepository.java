package com.redutec.core.repository;

import com.redutec.core.entity.HeadquartersDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HeadquartersDocumentRepository extends JpaRepository<HeadquartersDocument, Long>, JpaSpecificationExecutor<HeadquartersDocument> {
}