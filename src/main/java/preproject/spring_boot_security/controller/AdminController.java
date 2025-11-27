package preproject.spring_boot_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import preproject.spring_boot_security.model.User;
import preproject.spring_boot_security.service.RoleService;
import preproject.spring_boot_security.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("userForm", new User());
        model.addAttribute("rolesList", roleService.getAllRoles());
        model.addAttribute("editing", false);
        return "admin";
    }

    @GetMapping("/edit")
    public String editUserForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("userForm", userService.getUserById(id));
        model.addAttribute("rolesList", roleService.getAllRoles());
        model.addAttribute("editing", true);
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user,
                          @RequestParam(required = false, name = "roleIds") List<Long> roleIds,
                          @RequestParam String password) {
        userService.createUser(user, roleIds, password);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam(required = false, name = "roleIds") List<Long> roleIds,
                             @RequestParam(required = false) String password) {
        userService.updateUser(user, roleIds, password);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}