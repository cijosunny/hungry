package org.hni.common.service;

import java.util.List;

import javax.inject.Inject;

import org.hni.common.dao.HniTemplateDAO;
import org.hni.template.om.HniTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class HniTemplateServiceImpl implements HniTemplateService {
	
	@Inject
	HniTemplateDAO hniTemplateDao;

	@Override
	public HniTemplate getById(Long id) {
		return hniTemplateDao.getById(id);
	}

	@Override
	public HniTemplate getByType(String type) {
		return hniTemplateDao.getByType(type);
	}

	@Override
	public List<HniTemplate> getAll() {
		return hniTemplateDao.getAll();
	}

}
