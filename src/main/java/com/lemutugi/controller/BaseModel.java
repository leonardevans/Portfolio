package com.lemutugi.controller;

import org.springframework.ui.Model;

public class BaseModel {
    public Model addPagingAttributes(Model model, int pageSize, int pageNo, int totalPages, long totalItems, String sortField, String sortDir){
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return model;
    }
}
