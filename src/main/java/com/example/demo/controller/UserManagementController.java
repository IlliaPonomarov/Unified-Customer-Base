package com.example.demo.controller;

import com.example.demo.model.Auction.AuctionProduct;
import com.example.demo.model.Login;
import com.example.demo.service.Factory.FactoryAuctionProduct;
import com.example.demo.service.detailsService.AuctionProductDetailService;
import com.example.demo.service.detailsService.LoginDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin/usermg")
public class UserManagementController {

    private LoginDetailsService loginDetailsService;
    private FactoryAuctionProduct factoryAuctionProduct;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private AuctionProductDetailService auctionProductDetailService;
    private AuctionProduct auctionProduct;
    //private ActionDetailsService actionDetailsService = null;

    //@Autowired
    //private ElectricDetailService electricDetailService;


    @Autowired
    public UserManagementController(LoginDetailsService loginDetailsService) {
        this.loginDetailsService = loginDetailsService;
    }


    /**
     *Getting a form to add a new auction
     * @param model
     * @return
     */
    @GetMapping("/add-section")
    public String addActionForm(Model model){

        factoryAuctionProduct = new FactoryAuctionProduct();
        auctionProduct = factoryAuctionProduct.createActionProduct();

        model.addAttribute("item", auctionProduct);


        return "/adminController/userManagementController/addAction";
    }

    /**
     *Adding a new auction to the database
     * @param auctionProduct
     * @param model
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/add-section")
    public String addActionSubmit(@ModelAttribute AuctionProduct auctionProduct,
                                  Model model,
                                  @RequestParam("image") MultipartFile multipartFile
    ) throws IOException{

        model.addAttribute("item", auctionProduct);

        if (multipartFile != null && !multipartFile.getOriginalFilename().isEmpty()) {


            File uploadDir = new File(uploadPath);


            if (!uploadDir.exists())
                uploadDir.mkdir();

            String uuid = UUID.randomUUID().toString();

            String resultFile = uuid + "." + multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(uploadPath+"/"+resultFile));

            auctionProduct.setPhoto(resultFile);


        }
        System.out.println(auctionProduct.getName());

        System.out.println(auctionProduct.getFinishDate());

        auctionProductDetailService.save(auctionProduct);



        return "redirect:/admin/usermg";
    }


    /**
     *
     * @param model
     * @return
     */

    @GetMapping("/add")
    public String userForm(Model model){
        model.addAttribute("user", new Login());
        return "/adminController/userManagementController/addUser";
    }

    /**
     *Add a new user to the database
     * @param login
     * @param model
     * @return
     */
    @PostMapping("/add")
    public String userSubmit(@ModelAttribute Login login, Model model){

        model.addAttribute("user", login);
        loginDetailsService.save(login);
        return "/adminController/userManagementController/addUser";
    }

    /**
     * List all registered users
     * @param model
     * @return
     */
    @GetMapping("/all")
    public String allPersons(Model model) {
        model.addAttribute("users", loginDetailsService.findAll());
        return ("/adminController/userManagementController/allUsers");
    }




}
