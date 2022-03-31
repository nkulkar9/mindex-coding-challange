package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.NumberOfReportsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class NumberOfReportsServiceImpl implements NumberOfReportsService {

    private static final Logger LOG = LoggerFactory.getLogger(NumberOfReportsServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ReportingStructure getNoOfReports(String id) {
        LOG.debug("Reading employee with ID [{}]", id);

        ReportingStructure res = new ReportingStructure();

        Employee emp = employeeService.read(id);

        res.setEmployee(emp);

        res.setNumberOfReports(findNumberOfReports(emp));

        return res;
    }

    private Integer findNumberOfReports(Employee emp) {
        List<Employee> rp = new ArrayList<>();

        getAll(emp, rp);

        return rp.size() - 1;
    }

    public void getAll(Employee emp, List<Employee> rp){
        if(emp == null){
            return;
        }

        rp.add(emp);
        if(emp.getDirectReports() != null && !emp.getDirectReports().isEmpty()){
            for(Employee childEmployee: emp.getDirectReports()){
                Employee e = employeeService.read(childEmployee.getEmployeeId());
                getAll(e, rp);
            }
        }
    }
}
