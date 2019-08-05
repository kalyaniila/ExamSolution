package com.exam.solution;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.exam.solution.model.Dates;


@Controller
public class HomePageController {

	@Value("${spring.application.name}")
	String appName;
	
	@Value("${date.wrong.entry.message}")
	String wrongEntryMessage;
	
	@Value("${date.error.message}")
	String errorMessage;
	

	@GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("dates", new Dates());
        return "HomePage";
    }
	
	@PostMapping("/submitdates")
    public String submitdates(@Valid Dates dates, BindingResult result, Model model) {
		try {
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = f.parse(dates.getStartDate());
		
        Date endDate = f.parse(dates.getEndDate());

        long difference =  (endDate.getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24;
        
        if (difference < 0) {
        	
        	FieldError err1 = new FieldError("dates", "startDate", wrongEntryMessage);
        	result.addError(err1);
        	
        	model.addAttribute("appName", appName);
            
        	return "HomePage";
        }
        
        model.addAttribute("difference", difference);
        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "Success";
    }
}
