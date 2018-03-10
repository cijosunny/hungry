package org.hni.user.service;

import java.util.List;

import org.hni.user.om.Report;
import org.hni.user.om.User;

public interface ReportServices {

	public List<Report> getReportHeadings(Long role);


}
