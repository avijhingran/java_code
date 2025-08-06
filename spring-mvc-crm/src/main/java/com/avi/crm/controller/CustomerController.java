package com.avi.crm.controller;

import com.avi.crm.model.Customer;
import com.avi.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Home page
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Show list of all customers
    @GetMapping("/customers")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "list-customers";
    }

    // Show form to create new customer
    @GetMapping("/customers/new")
    public String showForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "save-customer";
    }

    // Save new or updated customer
    @PostMapping("/customers/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "redirect:/customers";
    }

    // Show form to update a customer
    @GetMapping("/customers/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        model.addAttribute("customer", customer);
        return "save-customer";
    }

    // Delete a customer
    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
        return "redirect:/customers";
    }
}
