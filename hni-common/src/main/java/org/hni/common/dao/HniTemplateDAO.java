package org.hni.common.dao;

import java.util.List;

import org.hni.template.om.HniTemplate;

public interface HniTemplateDAO {

	HniTemplate getById(Long id);
	HniTemplate getByType(String type);
	List<HniTemplate> getAll();
}
