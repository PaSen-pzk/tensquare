package com.tensuqare.base.dao;

import com.tensuqare.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LabelDao extends JpaRepository<Label, String > , JpaSpecificationExecutor<Label> {
}
