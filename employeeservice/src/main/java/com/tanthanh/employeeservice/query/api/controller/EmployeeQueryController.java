package com.tanthanh.employeeservice.query.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanthanh.commonservice.model.BookResponseCommonModel;
import com.tanthanh.commonservice.model.BorrowingResponseCommonModel;
import com.tanthanh.commonservice.query.GetListBookQuery;
import com.tanthanh.commonservice.query.GetListBorrowingByEmployee;
import com.tanthanh.employeeservice.query.api.model.EmployeeReponseModel;
import com.tanthanh.employeeservice.query.api.queries.GetAllEmployeeQuery;
import com.tanthanh.employeeservice.query.api.queries.GetEmployeesQuery;



@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {

	@Autowired 
	private QueryGateway queryGateway;
	
	@GetMapping("/{employeeId}")
    public EmployeeReponseModel getEmployeeDetail(@PathVariable String employeeId) {
        GetEmployeesQuery getEmployeesQuery = new GetEmployeesQuery();
        getEmployeesQuery.setEmployeeId(employeeId);

        EmployeeReponseModel employeeReponseModel =
        queryGateway.query(getEmployeesQuery,
                ResponseTypes.instanceOf(EmployeeReponseModel.class))
                .join();

        return employeeReponseModel;
    }
	@GetMapping
	public List<EmployeeReponseModel> getAllEmployee(){
		List<EmployeeReponseModel> list = queryGateway.query(new GetAllEmployeeQuery(), ResponseTypes.multipleInstancesOf(EmployeeReponseModel.class))
				.join();
		return list;
	}
	@GetMapping("/{employeeId}/books")
	public List<BookResponseCommonModel> getEmployeeBorrowedBook(@PathVariable String employeeId){
		
		
		//get List Borrowing 
		GetListBorrowingByEmployee getListBorrowingByEmployee = new GetListBorrowingByEmployee();
		getListBorrowingByEmployee.setEmployeeId(employeeId);
		List<BorrowingResponseCommonModel> listBorrowing = 
		 queryGateway.query(getListBorrowingByEmployee, ResponseTypes.multipleInstancesOf(BorrowingResponseCommonModel.class))
		 .join();
	
		//Get list book
		List<BookResponseCommonModel> listBook = 
				queryGateway.query(new GetListBookQuery(), ResponseTypes.multipleInstancesOf(BookResponseCommonModel.class))
				.join();
		
		
		
		List<BookResponseCommonModel> listTemp = new ArrayList<>();
		
		listTemp = listBook.stream()
				.filter(x -> listBorrowing.stream().anyMatch(y -> y.getBookId().equals(x.getBookId()))).toList();
		
		
		
		return listTemp;
	}
	
}
