package org.hni.user.service;

import java.util.List;

import javax.inject.Inject;

import org.hni.user.dao.ReportDao;
import org.hni.user.om.Report;
import org.springframework.stereotype.Component;

@Component
public class ReportServiceImpl implements ReportServices {
	@Inject
	private ReportDao reportDao;
	
	@Override
	public List<Report> getReportHeadings(Long role) {
		return reportDao.getReportHeadings(role);

	}

}
