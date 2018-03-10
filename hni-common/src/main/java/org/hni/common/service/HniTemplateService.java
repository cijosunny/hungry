package org.hni.common.service;

import java.util.List;

import org.hni.template.om.HniTemplate;

public interface HniTemplateService {

	HniTemplate getById(Long id);
	HniTemplate getByType(String type);
	List<HniTemplate> getAll();
}
