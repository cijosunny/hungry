package org.hni.user.dao;

import java.util.ArrayList;
import java.util.List;

import org.hni.common.dao.DefaultGenericDAO;
import org.hni.user.om.Report;
import org.hni.user.om.User;
import org.springframework.stereotype.Component;

@Component
public class ReportDao extends DefaultGenericDAO {

	public List<Report> getReportHeadings(Long role) {

		List<Report> headings =  em.createQuery("select  x from Report x where x.role=:roles")
				.setParameter("roles",role).getResultList();
		
		return headings;
	}

}
