package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trading")

public class RoleController {

    @Autowired
    RoleService roleService;
    @RequestMapping(value = "/createrole",method = RequestMethod.POST)
    public String createRole(@RequestBody Role role){

        Role roleresponse=roleService.createRole(role.getRoleType());
        if(roleresponse!=null) return "role created succesfully";
        else return "role not created ";
    }


    @RequestMapping(value = "/getallroles",method=RequestMethod.GET)
    public List<Role> getAllRoles(){
        return  roleService.getAllRoles();
    }
}
